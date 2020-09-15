package customer.dococean.com.patient.views.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.crashlytics.android.Crashlytics;

import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.db.DBHelper;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.InitAfterLogin.InitAfterLoginResponse;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;
import customer.dococean.com.patient.presenters.SettingsPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import customer.dococean.com.patient.views.fragments.BookingConfirmedFragment;
import customer.dococean.com.patient.views.fragments.HomeMapFragment;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements UIUpdateListener {

    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();
    private SettingsPresenter mSettingsPresenter;
    private AppointmentPresenter mAppointmentPresenter;
    private Subscription mBookingStatusSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DococeanTextView dococeanTextView = (DococeanTextView) findViewById(R.id.dococean);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        dococeanTextView.startAnimation(animation);
        mSettingsPresenter = new SettingsPresenter();
        mAppointmentPresenter = new AppointmentPresenter();
        mSettingsPresenter.bindView(this);
        mAppointmentPresenter.bindView(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    requestRequiredPermissions();
                } else {
                    navigate();
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        mSettingsPresenter.unbindView(this);
        mAppointmentPresenter.unbindView(this);
        mSettingsPresenter = null;
        mAppointmentPresenter = null;
        super.onDestroy();
    }

    private void requestRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, DocOceanConstants.REQUIRED_PERMISSION.PERMISSION, DocOceanConstants.RUNTIME_PERMISSION.PERMISSION);
        } else {
            navigate();
        }
    }

    private void navigate() {
        if (DBHelper.getUserAuthKey(this) != null) {
            mSettingsPresenter.initAfterLogin(this);
        } else {
            ActivityManager.startLoginActivity(SplashActivity.this, null);
            this.finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case DocOceanConstants.RUNTIME_PERMISSION.PERMISSION:
                if (grantResults.length == 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED
                            ) {
                        navigate();
                    } else {
                        showShortToast(" Please give us required permission to use our app ");
                        Crashlytics.setUserIdentifier(AppUtils.getDeviceId(this));
                        Crashlytics.log(DocOceanConstants.CrashlyticsConstants.PERMISSION_DENIED, AppUtils.getDeviceId(this), " Not giving permissions at Splash Activity ");
                    }
                }
        }
    }


    private void callHome() {
        Bundle bundle = new Bundle();
        bundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, HomeMapFragment.class.getSimpleName());
        ActivityManager.startHomeActivity(this, bundle);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof InitAfterLoginResponse) {
                InitAfterLoginResponse initAfterLoginResponse = (InitAfterLoginResponse) items;
                if (initAfterLoginResponse.getData().getOnDemandAppointmentNumber() != null) {
                    pollBookingStatus(initAfterLoginResponse.getData().getOnDemandAppointmentNumber());
                } else {
                    if (initAfterLoginResponse.getData().getRatingPending()) {
                        ActivityManager.startFeedbackActivity(this, null);
                    } else {
                        ActivityManager.startSelectCategoryActivity(this, null);
                    }
                    finish();
                }

            } else if (items instanceof TrackBookingResponse) {
                Log.d(TAG, " Inside Track booking response ");
                TrackBookingResponse trackBookingResponse = (TrackBookingResponse) items;
                if (trackBookingResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    setupOnceConfirmed(trackBookingResponse);
                } else {
                    showShortToast(trackBookingResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        if (error != null)
            showShortToast(error.getMessage());
    }

    private void pollBookingStatus(final String bookingId) {
        mBookingStatusSubscription = Schedulers.io().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                if (mAppointmentPresenter == null) {
                    mAppointmentPresenter = new AppointmentPresenter();
                    mAppointmentPresenter.bindView(SplashActivity.this);
                }
                Log.d(TAG, "Calling track booking status ");
                mAppointmentPresenter.trackBookingStatus(SplashActivity.this, bookingId);
            }
        }, 0, DocOceanConstants.IntegerConstants.PERIOIDIC_TRACK, TimeUnit.SECONDS);
    }

    private void unsubscribeBookingStatusPolling() {
        if (!mBookingStatusSubscription.isUnsubscribed())
            mBookingStatusSubscription.unsubscribe();
    }


    private void setupOnceConfirmed(TrackBookingResponse trackBookingResponse) {
        Log.d(TAG, " tracking status response is = " + trackBookingResponse.getAppointment().getStatus());
        if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.CONFIRMED)) {
            unsubscribeBookingStatusPolling();
            Bundle bundle = new Bundle();
            bundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, BookingConfirmedFragment.class.getSimpleName());
            bundle.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, trackBookingResponse);
            ActivityManager.startBookingConfirmedActivityWithNewTask(SplashActivity.this, bundle);
        } else if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.CANCELLED)) {
            unsubscribeBookingStatusPolling();
            showLongToast("Our experts are busy right now, please try after some time ");
            callHome();
        }
    }

}
