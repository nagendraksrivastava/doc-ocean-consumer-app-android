package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.response.profession_response.ProfessionResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 27/09/16.
 */

public class ProfessionPresenter extends BasePresenter<UIUpdateListener> {

    public void getProfessions(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<ProfessionResponse> getProfessionObs = DocOceanApiObservable.getInstance().getProfessions(context);
            Subscription getProfessionsSubscription = getProfessionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getProfessionsSubscription);
        }
    }
}
