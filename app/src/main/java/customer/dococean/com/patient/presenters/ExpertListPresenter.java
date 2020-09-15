package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.requests.GetExpertRequest;
import customer.dococean.com.patient.network.response.getexpertresponse.GetExpertResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 16/07/16.
 */
public class ExpertListPresenter extends BasePresenter<UIUpdateListener> {

    public void getExpertLists(Context context, GetExpertRequest expertRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<GetExpertResponse> expertObs = DocOceanApiObservable.getInstance().getExpertObservable(context, expertRequest);
            Subscription expertSubscription = expertObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(expertSubscription);
        }
    }
}
