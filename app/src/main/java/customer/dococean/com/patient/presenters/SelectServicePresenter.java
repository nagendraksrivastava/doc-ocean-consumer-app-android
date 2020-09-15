package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.response.service_response.ServiceResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 01/10/16.
 */

public class SelectServicePresenter extends BasePresenter<UIUpdateListener> {

    public void getServices(Context context, int professionId, int categoryId) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<ServiceResponse> getServiceObs = DocOceanApiObservable.getInstance().getServices(context, professionId, categoryId);
            Subscription getProfessionsSubscription = getServiceObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getProfessionsSubscription);
        }
    }
}
