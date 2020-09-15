package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.requests.AddressRequest;
import customer.dococean.com.patient.network.response.getaddress.GetAddressResponse;
import customer.dococean.com.patient.network.response.saveaddress.SaveAddressResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 26/11/16.
 */

public class AddressPresenter extends BasePresenter<UIUpdateListener> {

    public void getAddress(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<GetAddressResponse> getAddressObs = DocOceanApiObservable.getInstance().getAddress(context);
            Subscription getAddressSubscription = getAddressObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getAddressSubscription);
        }
    }

    public void sendAddress(Context context, AddressRequest addressRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SaveAddressResponse> sendAddressObservable = DocOceanApiObservable.getInstance().sendAddressObservable(context, addressRequest);
            Subscription sendAddressSubscription = sendAddressObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(sendAddressSubscription);
        }
    }


}
