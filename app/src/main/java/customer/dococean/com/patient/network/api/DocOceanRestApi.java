package customer.dococean.com.patient.network.api;

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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public interface DocOceanRestApi {


    @POST("/api/login")
    Observable<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("/api/forgot_password")
    Observable<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("api/register")
    Observable<SignupResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("/api/profile")
    Observable<UserProfileResponse> getUserProfile(@Header("Authorization") String token);

    @POST("/api/logout")
    Observable<LogoutResponse> logout(@Header("Authorization") String token);

    @POST("/api/experts")
    Observable<GetExpertResponse> getExperts(@Header("Authorization") String token,
                                             @Body GetExpertRequest expertRequest);

    @POST("/api/appointments")
    Observable<CreateAppointmentResponse> createAppointment(@Header("Authorization") String token,
                                                            @Body AppointmentRequest appointmentRequest);

    @POST("api/addresses")
    Observable<SaveAddressResponse> sendAddress(@Header("Authorization") String token,
                                                @Body AddressRequest addressRequest);

    @GET("/api/appointments")
    Observable<MyAppointmentResponse> getAppointments(@Header("Authorization") String token,
                                                      @Query("status") String bookingStatus);

    @GET("/api/appointments")
    Observable<MyAppointmentResponse> getAllAppointments(@Header("Authorization") String token);

    @GET("/api/professions")
    Observable<ProfessionResponse> getProfessions(@Header("Authorization") String token);


    @GET("/api/addresses")
    Observable<GetAddressResponse> getAddress(@Header("Authorization") String token);


    @GET("/api/expert/services")
    Observable<ServiceResponse> getServices(@Header("Authorization") String token,
                                            @Query("profession_id") int professionId,
                                            @Query("category_id") int categoryId);


    @GET("/api/patients")
    Observable<GetPatientResponse> getPatient(@Header("Authorization") String token);


    @GET("api/user_relationships")
    Observable<GetPatientRelationshipResponse> getPatientRelationships(@Header("Authorization") String token);

    @POST("/api/patients")
    Observable<CreatePatientResponse> createPatient(@Header("Authorization") String token, @Body CreatePatientRequest createPatientRequest);

    @POST("api/user_devices/add_reg_id")
    Observable<FirebaseTokenResponse> registerToken(@Body SendGcmRequest sendGcmRequest);


    @GET("/api/init/after_login")
    Observable<InitAfterLoginResponse> initAfterLogin(@Header("Authorization") String token);

    @GET("/api/appointments/pending_rating")
    Observable<PendingRatingAppointmentResponse> pendingRatingAppointment(@Header("Authorization") String token);

    @POST("/api/appointments/{number}/your_rating")
    Observable<RatingResponse> appointmentRating(@Header("Authorization") String token,
                                                 @Path("number") String number,
                                                 @Body RatingRequest ratingRequest);

    @POST("api/appointments/{number}/update_status")
    Observable<AppointmentStatusResponse> appointmentAction(@Header("Authorization") String token,
                                                            @Path("number") String id,
                                                            @Body AppointmentStatusRequest appointmentStatusRequest);

    /**
     * Will be add this feature into the app later
     *
     * @return
     */
    @GET("/veterinary")
    Observable<Object> getveterinary();


    @GET("/api/appointments/{booking_id}/track")
    Observable<TrackBookingResponse> trackBooking(@Header("Authorization") String token,
                                                  @Path("booking_id") String bookingId);


    @GET("/booking_history")
    Observable<Object> getBookingHistory();


    @POST("/issue")
    Observable<Object> reportIssue();

    @POST("/add_patient")
    Observable<Object> addPatient();
}
