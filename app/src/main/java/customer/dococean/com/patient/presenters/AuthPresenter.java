package customer.dococean.com.patient.presenters;

import android.content.Context;

import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.observables.DocOceanApiObservable;
import customer.dococean.com.patient.network.requests.ForgotPasswordRequest;
import customer.dococean.com.patient.network.requests.LoginRequest;
import customer.dococean.com.patient.network.requests.SignUpRequest;
import customer.dococean.com.patient.network.response.forgotpassword.ForgotPasswordResponse;
import customer.dococean.com.patient.network.response.loginresponse.LoginResponse;
import customer.dococean.com.patient.network.response.logoutresponse.LogoutResponse;
import customer.dococean.com.patient.network.response.signupresponse.SignupResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class AuthPresenter extends BasePresenter<UIUpdateListener> {

    public void signIn(LoginRequest loginRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<LoginResponse> signInObs = DocOceanApiObservable.getInstance().signInObservable(loginRequest);
            Subscription signInSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signInSubscription);
        }
    }


    public void signUp(SignUpRequest signUpRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SignupResponse> signInObs = DocOceanApiObservable.getInstance().signUpObservable(signUpRequest);
            Subscription signUpSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signUpSubscription);
        }
    }


    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<ForgotPasswordResponse> forgotPasswordObs = DocOceanApiObservable.getInstance().forgotpasswordObservable(forgotPasswordRequest);
            Subscription forgotPasswordSubscription = forgotPasswordObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(forgotPasswordSubscription);
        }
    }


    public void logout(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<LogoutResponse> logoutObs = DocOceanApiObservable.getInstance().logoutObservable(context);
            Subscription logoutSubscription = logoutObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(logoutSubscription);
        }
    }
}
