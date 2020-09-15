package customer.dococean.com.patient.views.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DococeanTextView;

public class ReferFriendActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private CoordinatorLayout mCoordinatorLayout;
    private DocOceanButton mShareButton;
    private DococeanTextView mShareTextTv;
    //private CommonPresenter mCommonPresenter;
    private DococeanTextView mReferalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friend);
        init();
        setHomeAsUpEnabled(true);
        setToolbarTitle(getString(R.string.text_refer_friend));
        setListeners();
    }

    private void init() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.clReferFriend);
        mShareButton = (DocOceanButton) findViewById(R.id.tv_refer_share);
        mShareTextTv = (DococeanTextView) findViewById(R.id.tv_refer_on_board);
        mReferalCode = (DococeanTextView) findViewById(R.id.tv_referal_code);
    }

    private void setListeners() {
        mShareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {

    }

    @Override
    public void showError(Throwable error) {

    }
}
