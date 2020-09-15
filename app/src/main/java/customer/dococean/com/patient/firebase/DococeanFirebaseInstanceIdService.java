package customer.dococean.com.patient.firebase;


import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import customer.dococean.com.patient.BuildConfig;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.api.DocOceanRestApi;
import customer.dococean.com.patient.network.api.RetrofitProvider;
import customer.dococean.com.patient.network.requests.SendGcmRequest;
import customer.dococean.com.patient.network.response.firebasetoken.FirebaseTokenResponse;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.utils.DocOceanConstants;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nagendrasrivastava on 13/08/16.
 */
public class DococeanFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "InstanceIdService";
    private DocOceanRestApi mDocOceanRestApi = RetrofitProvider.getInstance().getRestApi();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SendGcmRequest sendGcmRequest = new SendGcmRequest();
        String deviceId = AppUtils.getDeviceId(this);
        sendGcmRequest.setDeviceId(deviceId);
        sendGcmRequest.setPushNotificationRegId(refreshedToken);
        sendGcmRequest.setChecksum(AppUtils.getSHA256Checksum(deviceId + BuildConfig.KEY));
        sendRegistrationToServer(sendGcmRequest);
    }

    private void sendRegistrationToServer(SendGcmRequest sendGcmRequest) {
        registerFirebaseTokenObservable(sendGcmRequest).subscribe(new Subscriber<FirebaseTokenResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(FirebaseTokenResponse firebaseTokenResponse) {
                if (firebaseTokenResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.SUCCESS, AppUtils.getDeviceId(getBaseContext()), " FCM registration ID successfully registered to server ");
                } else {
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.FAILURE, AppUtils.getDeviceId(getBaseContext()), " FCM registration ID failed to register to server ");
                }
            }
        });
    }

    private Observable<FirebaseTokenResponse> registerFirebaseTokenObservable(SendGcmRequest sendGcmRequest) {
        return mDocOceanRestApi.registerToken(sendGcmRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}
