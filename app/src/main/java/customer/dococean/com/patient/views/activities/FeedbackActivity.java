package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.rating.PendingRatingAppointmentResponse;
import customer.dococean.com.patient.network.response.rating.RatingRequest;
import customer.dococean.com.patient.network.response.rating.RatingResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;
import customer.dococean.com.patient.views.custom.DococeanTextView;

public class FeedbackActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private ImageView mExpertImage;
    private DococeanTextView mExpertName;
    private DococeanTextView mExpertDegree;
    private DococeanTextView mExpertSpecialization;
    private RatingBar mRatingBar;
    private DocOceanEditText mCommentsTE;
    private DocOceanButton mConfirmButton;
    private AppointmentPresenter mAppointmentPresenter;
    private String mAppointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setUpToolBar();
        setToolbarTitle("Feedback");
        setHomeAsUpEnabled(true);
        init();
        setListeners();
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mAppointmentPresenter.pendingRatingAppointment(this);
    }

    private void init() {
        mExpertImage = (ImageView) findViewById(R.id.expert_image_IV);
        mExpertName = (DococeanTextView) findViewById(R.id.expert_name_TV);
        mExpertDegree = (DococeanTextView) findViewById(R.id.expert_degree_TV);
        mExpertSpecialization = (DococeanTextView) findViewById(R.id.expert_specialist_TV);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mCommentsTE = (DocOceanEditText) findViewById(R.id.comments_TE);
        mConfirmButton = (DocOceanButton) findViewById(R.id.feedback_confirm);
    }

    private void setListeners() {
        mConfirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_confirm:
                showProgressDialog(getString(R.string.text_please_wait));
                mAppointmentPresenter.appointmentRating(this, mAppointmentNumber, buildRatingRequest());
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
            if (items instanceof PendingRatingAppointmentResponse) {
                PendingRatingAppointmentResponse pendingRatingAppointmentResponse = (PendingRatingAppointmentResponse) items;
                if (pendingRatingAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mExpertName.setText(pendingRatingAppointmentResponse.getData().getExpert().getName());
                    mExpertDegree.setText(pendingRatingAppointmentResponse.getData().getExpert().getDegreeList());
                    mExpertSpecialization.setText(pendingRatingAppointmentResponse.getData().getExpert().getSpecializationList());
                    mAppointmentNumber = pendingRatingAppointmentResponse.getData().getNumber();
                    String expertImageURL = pendingRatingAppointmentResponse.getData().getExpert().getImageUrl();
                    if (expertImageURL != null && !expertImageURL.isEmpty()) {
                        Glide.with(this).load(expertImageURL).into(mExpertImage);
                    }
                }
            } else if (items instanceof RatingResponse) {
                RatingResponse ratingResponse = (RatingResponse) items;
                if (ratingResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    ActivityManager.startSelectCategoryActivity(this, null);
                    this.finish();
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private RatingRequest buildRatingRequest() {
        RatingRequest request = new RatingRequest();
        request.setValue(mRatingBar.getRating());
        request.setComments(mCommentsTE.getText().toString());
        return request;
    }
}
