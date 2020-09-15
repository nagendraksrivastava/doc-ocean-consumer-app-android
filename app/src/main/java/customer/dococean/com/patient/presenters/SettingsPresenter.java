package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.response.InitAfterLogin.InitAfterLoginResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 27/08/16.
 */
public class SettingsPresenter extends BasePresenter<UIUpdateListener> {


    public void initAfterLogin(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<InitAfterLoginResponse> initAfterLoginResponseObservable = DocOceanApiObservable.getInstance().initAfterLogin(context);
            Subscription initAfterLoginSubscription = initAfterLoginResponseObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(initAfterLoginSubscription);
        }
    }

}
