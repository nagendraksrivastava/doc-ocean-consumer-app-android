package customer.dococean.com.patient.presenters;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class BasePresenter<T> {

    private final CompositeSubscription subscriptionQueue = new CompositeSubscription();

    private T uiUpdateListener;

    /**
     * Call this when View is being created
     * <p>
     * This binds the view to the listener
     *
     * @param updateListener
     */
    public void bindView(T updateListener) {
        final T previousListener = uiUpdateListener;
        if (previousListener != null) {
            throw new IllegalStateException("A view is already bound to this listener, please unbind it before binding any other view = " + previousListener);
        }
        uiUpdateListener = updateListener;
    }

    public void queueSubscriptionForRemoval(Subscription subscription) {
        subscriptionQueue.add(subscription);
    }

    public void queueSubscriptionsForRemoval(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            subscriptionQueue.add(subscription);
        }
    }

    /**
     * Call this when View is being finished or destroyed
     *
     * @param updateListener
     */
    public void unbindView(T updateListener) {
        final T previousListener = uiUpdateListener;
        if (previousListener == updateListener) {
            this.uiUpdateListener = null;
        } else {
            throw new IllegalStateException("No such listener was bound previously.");
        }
        subscriptionQueue.clear();
    }

    public void clearSubscriptionQueue() {
        subscriptionQueue.clear();
    }

    public T getUiUpdateListener() {
        return uiUpdateListener;
    }

    public void showError(UIUpdateListener listener, Throwable t) {
        try {
            listener.showLoading(false);
            listener.showError(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showContent(UIUpdateListener listener, Object data) {
        try {
            listener.showLoading(false);
            listener.showContent(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected <E extends Object> Subscriber<E> getSubscriber(final UIUpdateListener listener) {
        return new Subscriber<E>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    showError(listener, e);
                }
            }

            @Override
            public void onNext(E next) {
                if (listener != null) {
                    showContent(listener, next);
                }
            }
        };
    }

}
