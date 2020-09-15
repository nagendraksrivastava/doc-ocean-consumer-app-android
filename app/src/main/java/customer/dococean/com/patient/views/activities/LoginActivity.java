package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.view.View;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.db.DBHelper;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.LoginRequest;
import customer.dococean.com.patient.network.response.loginresponse.LoginResponse;
import customer.dococean.com.patient.presenters.AuthPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanUtils;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import customer.dococean.com.patient.views.fragments.HomeMapFragment;

public class LoginActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private DocOceanEditText mUserNameEt;
    private DocOceanEditText mPasswordEt;
    private DocOceanButton mLoginButton;
    private DococeanTextView mForgotPasswordTv;
    private DococeanTextView mCreateNewAccountTv;
    private AuthPresenter mAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setOnClickListeners();
        setToolbarTitle(getString(R.string.action_bar_login));
        setHomeAsUpEnabled(false);
        mAuthPresenter = new AuthPresenter();
        mAuthPresenter.bindView(this);
    }


    private void initViews() {
        mUserNameEt = (DocOceanEditText) findViewById(R.id.username_et);
        mPasswordEt = (DocOceanEditText) findViewById(R.id.password_et);
        mLoginButton = (DocOceanButton) findViewById(R.id.login);
        mForgotPasswordTv = (DococeanTextView) findViewById(R.id.forgot_password);
        mCreateNewAccountTv = (DococeanTextView) findViewById(R.id.create_new_account);
    }


    private void setOnClickListeners() {
        mLoginButton.setOnClickListener(this);
        mForgotPasswordTv.setOnClickListener(this);
        mCreateNewAccountTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                validateAndLogin();
                break;
            case R.id.forgot_password:
                ActivityManager.startForgotPasswordActivity(this, null);
                break;
            case R.id.create_new_account:
                ActivityManager.startSignupActivity(this, null);
                finish();
                break;
        }
    }

    private void validateAndLogin() {
        if (mUserNameEt.getText() == null || mUserNameEt.getText().toString().isEmpty()) {
            mUserNameEt.setError("Email can't be blank");
            return;
        }

        if (!DocOceanUtils.isValidMail(mUserNameEt.getText().toString().trim())) {
            mUserNameEt.setError("Please enter a valid email");
            return;
        }

        if (mPasswordEt.getText() == null || mPasswordEt.getText().toString().isEmpty()) {
            mPasswordEt.setError("Password can't be blank");
            return;
        }

        AppUtils.hideKeyboard(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mUserNameEt.getText().toString().trim());
        loginRequest.setPassword(mPasswordEt.getText().toString().trim());
        loginRequest.setType(DocOceanConstants.USER_TYPE);
        loginRequest.setDeviceId(AppUtils.getDeviceId(this));
        mAuthPresenter.signIn(loginRequest);
        showProgressDialog(getString(R.string.text_please_wait));


    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            cancelProgressDialog();
            LoginResponse loginResponse = (LoginResponse) items;
            if (loginResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                DBHelper.saveToNormalUserTable(this, loginResponse.getData().getName(), loginResponse.getData().getEmail(), loginResponse.getData().getPhoneNo(), loginResponse.getData().getAuthToken());
                ActivityManager.startSelectCategoryActivity(this, null);
                this.finish();
            } else {
                showShortToast(loginResponse.getStatus().getMessage());
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void callHome() {
        Bundle bundle = new Bundle();
        bundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, HomeMapFragment.class.getSimpleName());
        ActivityManager.startHomeActivity(this, bundle);
    }
}
