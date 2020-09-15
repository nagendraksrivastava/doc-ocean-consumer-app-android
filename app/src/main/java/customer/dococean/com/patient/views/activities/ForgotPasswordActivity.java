package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.ForgotPasswordRequest;
import customer.dococean.com.patient.network.response.forgotpassword.ForgotPasswordResponse;
import customer.dococean.com.patient.presenters.AuthPresenter;
import customer.dococean.com.patient.utils.DocOceanUtils;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;

public class ForgotPasswordActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {


    private DocOceanEditText mEmailEditText;
    private DocOceanButton mSubmitButton;
    private TextInputLayout mForgotPasswordTIL;
    private AuthPresenter mAuthPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        setListeners();
        setToolbarTitle("Forgot Password");
        setHomeAsUpEnabled(true);
        mAuthPresenter = new AuthPresenter();
        mAuthPresenter.bindView(this);
    }


    @Override
    protected void onDestroy() {
        if (mAuthPresenter != null) {
            mAuthPresenter.unbindView(this);
            mAuthPresenter = null;
        }
        super.onDestroy();

    }

    private void initViews() {
        mEmailEditText = (DocOceanEditText) findViewById(R.id.password_et);
        mForgotPasswordTIL = (TextInputLayout) findViewById(R.id.forgot_password_TIL);
        mSubmitButton = (DocOceanButton) findViewById(R.id.submit_button);
    }

    private void setListeners() {
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_button:
                validateAndAct();
                break;

        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        cancelProgressDialog();
        if (items != null) {
            if (items instanceof ForgotPasswordResponse) {
                ForgotPasswordResponse forgotPasswordResponse = (ForgotPasswordResponse) items;
                if (forgotPasswordResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(forgotPasswordResponse.getStatus().getMessage());
                    this.finish();
                } else {
                    showShortToast(forgotPasswordResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void validateAndAct() {
        if (mEmailEditText.getText() == null || mEmailEditText.getText().toString().isEmpty()) {
            mForgotPasswordTIL.setError("Email can not be blank");
        }

        if (!DocOceanUtils.isValidMail(mEmailEditText.getText().toString()) || DocOceanUtils.isValidMobile(mEmailEditText.getText().toString())) {
            mForgotPasswordTIL.setError("Please enter a valid email id");
            return;
        }
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(mEmailEditText.getText().toString());
        mAuthPresenter.forgotPassword(forgotPasswordRequest);
        showProgressDialog(getString(R.string.text_please_wait));
    }
}

