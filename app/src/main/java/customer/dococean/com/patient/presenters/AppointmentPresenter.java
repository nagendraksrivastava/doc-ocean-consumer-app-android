package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.requests.AppointmentRequest;
import customer.dococean.com.patient.network.requests.AppointmentStatusRequest;
import customer.dococean.com.patient.network.response.myappointment.MyAppointmentResponse;
import customer.dococean.com.patient.network.response.createappointment.CreateAppointmentResponse;
import customer.dococean.com.patient.network.response.appointmentstatus.AppointmentStatusResponse;
import customer.dococean.com.patient.network.response.rating.PendingRatingAppointmentResponse;
import customer.dococean.com.patient.network.response.rating.RatingRequest;
import customer.dococean.com.patient.network.response.rating.RatingResponse;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 16/07/16.
 */
public class AppointmentPresenter extends BasePresenter<UIUpdateListener> {

    public void createBooking(Context context, AppointmentRequest appointmentRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<CreateAppointmentResponse> createAppointmentObs = DocOceanApiObservable.getInstance().createAppointment(context, appointmentRequest);
            Subscription createAppointmentSubscription = createAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(createAppointmentSubscription);
        }
    }

    public void getAllAppointments(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<MyAppointmentResponse> allAppointmentObs = DocOceanApiObservable.getInstance().getAllAppointmentData(context);
            Subscription allAppointmentSubscription = allAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(allAppointmentSubscription);
        }
    }


    public void trackBookingStatus(Context context, String bookingId) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<TrackBookingResponse> createAppointmentObs = DocOceanApiObservable.getInstance().trackBookingStatus(context, bookingId);
            Subscription createAppointmentSubscription = createAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(createAppointmentSubscription);
        }
    }

    public void appointmemtStatus(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<AppointmentStatusResponse> appointmentStatusObs = DocOceanApiObservable.getInstance().appointmentActionObservable(context, id, appointmentStatusRequest);
            Subscription appointmentStatusSubscription = appointmentStatusObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentStatusSubscription);
        }
    }

    public void pendingRatingAppointment(Context context){
        final UIUpdateListener listener = getUiUpdateListener();
        if(listener != null) {
            listener.showLoading(true);
            Observable<PendingRatingAppointmentResponse> pendingRatingAppointmentObs = DocOceanApiObservable.getInstance().pendingRatingAppointmentObservable(context);
            Subscription pendingRatingAppointmentSubscription = pendingRatingAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(pendingRatingAppointmentSubscription);
        }
    }

    public void appointmentRating(Context context, String id, RatingRequest ratingRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<RatingResponse> ratingResponseObs = DocOceanApiObservable.getInstance().appointmentRatingObservable(context, id, ratingRequest);
            Subscription ratingResponseSubscription = ratingResponseObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(ratingResponseSubscription);
        }
    }


}
