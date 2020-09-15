package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.response.profile.UserProfileResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 19/11/16.
 */

public class ProfilePresenter extends BasePresenter<UIUpdateListener> {

    public void getUserProfile(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<UserProfileResponse> profileObs = DocOceanApiObservable.getInstance().getUserProfile(context);
            Subscription profileSubscription = profileObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(profileSubscription);
        }
    }
}
