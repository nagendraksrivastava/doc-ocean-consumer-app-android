package customer.dococean.com.patient.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.ActionBarTitleUpdator;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.AppointmentStatusRequest;
import customer.dococean.com.patient.network.response.appointmentstatus.AppointmentStatusResponse;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.fragments.BookingConfirmedFragment;

public class BookingConfirmedActivity extends NavigationBarActivity implements ActionBarTitleUpdator, UIUpdateListener {

    private String mFragmentName;
    private AppointmentPresenter mAppointmentPresenter;
    private TrackBookingResponse mTrackAppointmentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);
        setToolbarTitle(getString(R.string.action_bar_title_booking_confirmed));
        Bundle extras = getIntent().getExtras();
        mFragmentName = extras.getString(DocOceanConstants.IntentConstants.FRAGMENT_NAME);
        changeFragment(mFragmentName, extras);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mTrackAppointmentResponse = extras.getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
    }

    @Override
    protected void onDestroy() {
        if (mAppointmentPresenter != null) {
            mAppointmentPresenter.unbindView(this);
            mAppointmentPresenter = null;
        }
        super.onDestroy();
    }

    private void changeFragment(String fragment, Bundle bundle) {
        if (fragment.equalsIgnoreCase(BookingConfirmedFragment.class.getSimpleName())) {
            changeFragmentWithOutBackStack(R.id.flContent, BookingConfirmedFragment.newInstance(bundle), BookingConfirmedFragment.class.getSimpleName());
        }
    }

    @Override
    public void updateTitle(String title) {
        if (title != null)
            setToolbarTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_confirmed_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cancel) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(boolean isLoading) {
        super.showLoading(isLoading);
    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof AppointmentStatusResponse) {
                cancelProgressDialog();
                AppointmentStatusResponse appointmentStatusResponse = (AppointmentStatusResponse) items;
                if (appointmentStatusResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.text_appointment_cancelled));
                    ActivityManager.startSelectCategoryActivity(this, null);
                    this.finish();
                } else {
                    showShortToast(appointmentStatusResponse.getStatus().getMessage());
                }
            }
        }
        super.showContent(items);
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
        super.showError(error);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookingConfirmedActivity.this);
        builder.setTitle(getString(R.string.dialog_booking_cancel_confirmation_title));
        builder.setMessage(getString(R.string.dialog_booking_cancel_confirmation_message));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppointmentStatusRequest appointmentStatusRequest = new AppointmentStatusRequest();
                appointmentStatusRequest.setStatus(DocOceanConstants.APPOINTMENT_CANCEL_REQUEST);
                mAppointmentPresenter.appointmemtStatus(BookingConfirmedActivity.this, mTrackAppointmentResponse.getAppointment().getNumber(), appointmentStatusRequest);
                showProgressDialog(getString(R.string.text_please_wait));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
