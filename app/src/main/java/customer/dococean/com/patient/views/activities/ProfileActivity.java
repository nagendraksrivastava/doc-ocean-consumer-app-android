package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.profile.UserProfileResponse;
import customer.dococean.com.patient.presenters.ProfilePresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private CircleImageView mUserImageIV;
    private DococeanTextView mUserNameTV;
    private DococeanTextView mUserCountryTV;
    private DococeanTextView mUserCityTV;
    private DococeanTextView mUserEmailTV;
    private DococeanTextView mUserPhoneNoTv;
    private DocOceanButton mSeePatientsListButton;
    private ProfilePresenter mProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbarTitle("Profile");
        setHomeAsUpEnabled(true);
        init();
        setListeners();
        mProfilePresenter = new ProfilePresenter();
        mProfilePresenter.bindView(this);
        showProgressDialog(getString(R.string.text_please_wait));
        mProfilePresenter.getUserProfile(this);
    }

    private void init() {
        mUserImageIV = (CircleImageView) findViewById(R.id.user_image);
        mUserNameTV = (DococeanTextView) findViewById(R.id.profile_user_name);
        mUserCountryTV = (DococeanTextView) findViewById(R.id.profile_country_name_TV);
        mUserCityTV = (DococeanTextView) findViewById(R.id.profile_city_name_TV);
        mUserEmailTV = (DococeanTextView) findViewById(R.id.profile_user_email_TV);
        mUserPhoneNoTv = (DococeanTextView) findViewById(R.id.profile_user_phone_TV);
        mSeePatientsListButton = (DocOceanButton) findViewById(R.id.see_patient_list_button);
    }

    private void setListeners() {
        mSeePatientsListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.see_patient_list_button:
                ActivityManager.startPatientListActivity(this, null);
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
            if (items instanceof UserProfileResponse) {
                UserProfileResponse userProfileResponse = (UserProfileResponse) items;
                if (userProfileResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    infalteDataToUi(userProfileResponse);
                } else {
                    showShortToast(userProfileResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void infalteDataToUi(UserProfileResponse userProfileResponse) {
        mUserEmailTV.setText(userProfileResponse.getDetails().getEmail());
        mUserNameTV.setText(userProfileResponse.getDetails().getName());
        mUserPhoneNoTv.setText(userProfileResponse.getDetails().getPhoneNo());
        mUserCityTV.setText(userProfileResponse.getDetails().getCity());
        mUserCountryTV.setText(userProfileResponse.getDetails().getCountry());
        if (userProfileResponse.getDetails().getImageUrl() != null)
            Glide.with(this).load(userProfileResponse.getDetails().getImageUrl()).into(mUserImageIV);
    }
}
