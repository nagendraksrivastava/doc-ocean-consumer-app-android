package customer.dococean.com.patient.views.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.NothingSelectedSpinnerAdapter;
import customer.dococean.com.patient.db.DBHelper;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.SignUpRequest;
import customer.dococean.com.patient.network.requests.User;
import customer.dococean.com.patient.network.response.signupresponse.SignupResponse;
import customer.dococean.com.patient.presenters.AuthPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanUtils;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;
import customer.dococean.com.patient.views.custom.DococeanTextView;

public class SignupActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener, AdapterView.OnItemSelectedListener {

    private DocOceanEditText mFirstNameEt;
    private DocOceanEditText mLastNameEt;
    private DocOceanEditText mEmailEt;
    private DocOceanEditText mMobileEt;
    private DocOceanEditText mPasswordEt;
    private DocOceanEditText mReTypePasswordEt;
    private DocOceanEditText mDateOfBirthEt;
    private DocOceanButton mSignupButton;
    private LinearLayout mAlreadyAccountLL;
    private TextInputLayout mDateOfBirthTIL;
    private DococeanTextView mAcceptTermsAndConditionTv;
    private AuthPresenter mAuthPresenter;
    private String mDateOfBirthErrorMessage;
    private Spinner mGenderSpinner;
    private String mPatientGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        setupSpannableForTermsAndCondition();
        setListeners();
        initPresenters();
        bindPresenterWithView();
        setToolbarTitle("Register With Us ");
        setHomeAsUpEnabled(true);
        addDataToGenderSpinner();
    }

    @Override
    protected void onDestroy() {
        mAuthPresenter.unbindView(this);
        super.onDestroy();
    }

    private void initViews() {
        mFirstNameEt = (DocOceanEditText) findViewById(R.id.first_name_et);
        mLastNameEt = (DocOceanEditText) findViewById(R.id.last_name_et);
        mDateOfBirthEt = (DocOceanEditText) findViewById(R.id.date_of_birth_name_et);
        mDateOfBirthTIL = (TextInputLayout) findViewById(R.id.date_of_birth_name_TIL);
        mEmailEt = (DocOceanEditText) findViewById(R.id.email_et);
        mMobileEt = (DocOceanEditText) findViewById(R.id.phone_no_et);
        mSignupButton = (DocOceanButton) findViewById(R.id.signup_button);
        mAlreadyAccountLL = (LinearLayout) findViewById(R.id.already_account_LL);
        mAcceptTermsAndConditionTv = (DococeanTextView) findViewById(R.id.accept_terms_and_condition);
        mPasswordEt = (DocOceanEditText) findViewById(R.id.password_et);
        mReTypePasswordEt = (DocOceanEditText) findViewById(R.id.re_password_et);
        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);

    }

    private void setListeners() {
        mSignupButton.setOnClickListener(this);
        mAlreadyAccountLL.setOnClickListener(this);
        mAcceptTermsAndConditionTv.setOnClickListener(this);
        mDateOfBirthEt.setOnClickListener(this);
        mGenderSpinner.setOnItemSelectedListener(this);
    }


    private void addDataToGenderSpinner() {
        List<String> genderList = new ArrayList<>();
        genderList.add("MALE");
        genderList.add("FEMALE");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, this, getResources().getString(R.string.select_gender)));
    }

    private void initPresenters() {
        mAuthPresenter = new AuthPresenter();
    }


    private void bindPresenterWithView() {
        mAuthPresenter.bindView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                validateFieldsAndRegister();
                return;
            case R.id.already_account_LL:
                ActivityManager.startLoginActivity(this, null);
                this.finish();
                return;
            case R.id.accept_terms_and_condition:
                return;
            case R.id.date_of_birth_name_et:
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
            SignupResponse signupResponse = (SignupResponse) items;
            if (signupResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                DBHelper.saveToNormalUserTable(this, signupResponse.getData().getName(), signupResponse.getData().getEmail(), signupResponse.getData().getPhoneNo(), signupResponse.getData().getAuthToken());
                ActivityManager.startAddressActivity(this, null);
                this.finish();
            } else {
                showShortToast(signupResponse.getStatus().getMessage());
            }

        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void validateFieldsAndRegister() {
        if (mFirstNameEt.getText() == null || mFirstNameEt.getText().toString().isEmpty()) {
            mFirstNameEt.setError("Please enter first Name ");
            return;
        }

        if (mLastNameEt.getText() == null || mLastNameEt.getText().toString().isEmpty()) {
            mLastNameEt.setError("Please enter last Name ");
            return;
        }

        if (mPatientGender == null) {
            showShortToast("Please select gender");
            return;
        }

        if (mDateOfBirthEt.getText() == null || mDateOfBirthEt.getText().toString().isEmpty()) {
            mDateOfBirthEt.setError("Please enter your date of birth ");
        }

        if (mEmailEt.getText() == null || mEmailEt.getText().toString().isEmpty()) {
            mEmailEt.setError(" Please enter email Id ");
            return;
        } else if (!DocOceanUtils.isValidMail(mEmailEt.getText().toString().trim())) {
            mEmailEt.setError(" Please enter a valid email address ");
            return;
        }

        if (mMobileEt.getText() == null || mMobileEt.getText().toString().isEmpty()) {
            mMobileEt.setError(" Please enter mobile no ");
            return;
        } else if (!DocOceanUtils.isValidMobile(mMobileEt.getText().toString().trim())) {
            mMobileEt.setError(" Please enter a valid mobile no ");
            return;
        }

        if (mPasswordEt.getText() == null || mPasswordEt.getText().toString().isEmpty()) {
            mPasswordEt.setError(" Please enter password ");
            return;
        }

        if (mReTypePasswordEt.getText() == null || mReTypePasswordEt.getText().toString().isEmpty()) {
            mReTypePasswordEt.setError(" Please re enter password ");
            return;
        }

        if (!mPasswordEt.getText().toString().trim().equals(mReTypePasswordEt.getText().toString().trim())) {
            mReTypePasswordEt.setError(" Password did not match ");
            return;
        }

        if (mDateOfBirthErrorMessage != null) {
            mDateOfBirthTIL.setError(mDateOfBirthErrorMessage);
            return;
        }

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setDeviceId(AppUtils.getDeviceId(this));
        User user = new User();
        user.setName(mFirstNameEt.getText().toString().trim() + " " + mLastNameEt.getText().toString().trim());
        user.setEmail(mEmailEt.getText().toString().trim());
        user.setPassword(mPasswordEt.getText().toString().trim());
        user.setPhoneNo(mMobileEt.getText().toString().trim());
        user.setGender(mPatientGender);
        user.setType(DocOceanConstants.USER_TYPE);
        user.setDateOfBirth(mDateOfBirthEt.getText().toString().trim());
        signUpRequest.setUser(user);
        showProgressDialog(getString(R.string.text_please_wait));
        AppUtils.hideKeyboard(this);
        mAuthPresenter.signUp(signUpRequest);

    }


    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        int userAge = 0;
        try {
            userAge = AppUtils.getAge(myCalendar.getTime());
            if (userAge >= 18) {
                mDateOfBirthEt.setText(sdf.format(myCalendar.getTime()));
                mDateOfBirthErrorMessage = null;
            } else {
                mDateOfBirthErrorMessage = getString(R.string.at_least_18_year_old_declaration);
                mDateOfBirthTIL.setError(getString(R.string.at_least_18_year_old_declaration));
            }

        } catch (IllegalArgumentException ex) {
            mDateOfBirthErrorMessage = getString(R.string.at_least_18_year_old_declaration);
            mDateOfBirthTIL.setError(getString(R.string.at_least_18_year_old_declaration));
        }
    }

    private void setupSpannableForTermsAndCondition() {
        String clickableText = "By signing up you are accepting our Terms and Conditions ";
        SpannableString termsAndConditionSpannable = new SpannableString(clickableText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ActivityManager.startTermsAndConditionsActivity(SignupActivity.this, null);
            }
        };
        termsAndConditionSpannable.setSpan(clickableSpan, 35, clickableText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsAndConditionSpannable.setSpan(new ForegroundColorSpan(Color.RED), 35, clickableText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAcceptTermsAndConditionTv.setText(termsAndConditionSpannable);
        mAcceptTermsAndConditionTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getItemAtPosition(position) != null) {
            mPatientGender = adapterView.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
