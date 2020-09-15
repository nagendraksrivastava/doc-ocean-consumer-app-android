package customer.dococean.com.patient.network.observables;

import android.content.Context;

import customer.dococean.com.patient.db.DBHelper;
import customer.dococean.com.patient.network.api.DocOceanRestApi;
import customer.dococean.com.patient.network.api.RetrofitProvider;
import customer.dococean.com.patient.network.requests.AddressRequest;
import customer.dococean.com.patient.network.requests.AppointmentRequest;
import customer.dococean.com.patient.network.requests.AppointmentStatusRequest;
import customer.dococean.com.patient.network.requests.CreatePatientRequest;
import customer.dococean.com.patient.network.requests.ForgotPasswordRequest;
import customer.dococean.com.patient.network.requests.GetExpertRequest;
import customer.dococean.com.patient.network.requests.LoginRequest;
import customer.dococean.com.patient.network.requests.SendGcmRequest;
import customer.dococean.com.patient.network.requests.SignUpRequest;
import customer.dococean.com.patient.network.response.InitAfterLogin.InitAfterLoginResponse;
import customer.dococean.com.patient.network.response.myappointment.MyAppointmentResponse;
import customer.dococean.com.patient.network.response.createappointment.CreateAppointmentResponse;
import customer.dococean.com.patient.network.response.appointmentstatus.AppointmentStatusResponse;
import customer.dococean.com.patient.network.response.create_patient_response.CreatePatientResponse;
import customer.dococean.com.patient.network.response.firebasetoken.FirebaseTokenResponse;
import customer.dococean.com.patient.network.response.forgotpassword.ForgotPasswordResponse;
import customer.dococean.com.patient.network.response.get_patient_relationship.GetPatientRelationshipResponse;
import customer.dococean.com.patient.network.response.getaddress.GetAddressResponse;
import customer.dococean.com.patient.network.response.getexpertresponse.GetExpertResponse;
import customer.dococean.com.patient.network.response.getpatient.GetPatientResponse;
import customer.dococean.com.patient.network.response.loginresponse.LoginResponse;
import customer.dococean.com.patient.network.response.logoutresponse.LogoutResponse;
import customer.dococean.com.patient.network.response.rating.PendingRatingAppointmentResponse;
import customer.dococean.com.patient.network.response.profession_response.ProfessionResponse;
import customer.dococean.com.patient.network.response.profile.UserProfileResponse;
import customer.dococean.com.patient.network.response.rating.RatingRequest;
import customer.dococean.com.patient.network.response.rating.RatingResponse;
import customer.dococean.com.patient.network.response.saveaddress.SaveAddressResponse;
import customer.dococean.com.patient.network.response.service_response.ServiceResponse;
import customer.dococean.com.patient.network.response.signupresponse.SignupResponse;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class DocOceanApiObservable {
    private static DocOceanApiObservable ourInstance = new DocOceanApiObservable();

    private DocOceanRestApi mDocOceanRestApi;

    public static DocOceanApiObservable getInstance() {
        return ourInstance;
    }

    private DocOceanApiObservable() {
        if (mDocOceanRestApi == null) {
            mDocOceanRestApi = RetrofitProvider.getInstance().getRestApi();
        }
    }

    public Observable<LoginResponse> signInObservable(LoginRequest loginRequest) {
        return mDocOceanRestApi.signIn(loginRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<SignupResponse> signUpObservable(SignUpRequest signUpRequest) {
        return mDocOceanRestApi.signUp(signUpRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<UserProfileResponse> getUserProfile(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getUserProfile(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<LogoutResponse> logoutObservable(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.logout(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<GetExpertResponse> getExpertObservable(Context context, GetExpertRequest expertRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getExperts(authKey, expertRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<GetPatientResponse> getPatients(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getPatient(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<ProfessionResponse> getProfessions(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getProfessions(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<GetAddressResponse> getAddress(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getAddress(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<SaveAddressResponse> sendAddressObservable(Context context, AddressRequest addressRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.sendAddress(authKey, addressRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }



    public Observable<ServiceResponse> getServices(Context context, int professionid, int categoryId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getServices(authKey, professionid, categoryId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<CreatePatientResponse> createPatients(Context context, CreatePatientRequest createPatientRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.createPatient(authKey, createPatientRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<CreateAppointmentResponse> createAppointment(Context context, AppointmentRequest appointmentRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.createAppointment(authKey, appointmentRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<FirebaseTokenResponse> registerTokenObservable(SendGcmRequest sendGcmRequest) {
        return mDocOceanRestApi.registerToken(sendGcmRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }


    public Observable<GetPatientRelationshipResponse> getPatientRelationshipObservable(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getPatientRelationships(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<InitAfterLoginResponse> initAfterLogin(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.initAfterLogin(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<MyAppointmentResponse> getAllAppointmentData(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getAllAppointments(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<TrackBookingResponse> trackBookingStatus(Context context, String bookingId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.trackBooking(authKey, bookingId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<AppointmentStatusResponse> appointmentActionObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.appointmentAction(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<ForgotPasswordResponse> forgotpasswordObservable(ForgotPasswordRequest forgotPasswordRequest) {
        return mDocOceanRestApi.forgotPassword(forgotPasswordRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<PendingRatingAppointmentResponse> pendingRatingAppointmentObservable(Context context){
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.pendingRatingAppointment(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn((Schedulers.newThread()));
    }

    public Observable<RatingResponse> appointmentRatingObservable(Context context, String id, RatingRequest ratingRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.appointmentRating(authKey, id, ratingRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<Object> getBookingHistoryObservable() {
        return mDocOceanRestApi.getBookingHistory().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<Object> reportIssuesObservable() {
        return mDocOceanRestApi.reportIssue().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<Object> addPatientObservable() {
        return mDocOceanRestApi.addPatient().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


}
