package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.requests.CreatePatientRequest;
import customer.dococean.com.patient.network.response.create_patient_response.CreatePatientResponse;
import customer.dococean.com.patient.network.response.get_patient_relationship.GetPatientRelationshipResponse;
import customer.dococean.com.patient.network.response.getpatient.GetPatientResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 07/08/16.
 */
public class PatientPresenter extends BasePresenter<UIUpdateListener> {

    public void getPatients(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<GetPatientResponse> getPatientResponseObservable = DocOceanApiObservable.getInstance().getPatients(context);
            Subscription getPatientSubscription = getPatientResponseObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getPatientSubscription);
        }
    }


    public void createPatients(Context context, CreatePatientRequest createPatientRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<CreatePatientResponse> createPatientResponseObservable = DocOceanApiObservable.getInstance().createPatients(context, createPatientRequest);
            Subscription createPatientSubscription = createPatientResponseObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(createPatientSubscription);
        }
    }


    public void getPatientRelationship(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<GetPatientRelationshipResponse> getPatientResponseObservable = DocOceanApiObservable.getInstance().getPatientRelationshipObservable(context);
            Subscription getPatientRelationshipSubscription = getPatientResponseObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getPatientRelationshipSubscription);
        }
    }

}
