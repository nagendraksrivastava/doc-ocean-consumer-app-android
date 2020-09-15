package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.SelectServiceListRecyclerViewAdapter;
import customer.dococean.com.patient.interfaces.SendDataFromAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.network.response.service_response.Service;
import customer.dococean.com.patient.network.response.service_response.ServiceResponse;
import customer.dococean.com.patient.presenters.SelectServicePresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.fragments.HomeMapFragment;

public class SelectServiceActivity extends ToolbarActivity implements View.OnClickListener, SendDataFromAdapter, UIUpdateListener {

    private static final String TAG = "SelectServiceActivity";
    private RecyclerView mServiceRv;
    private DocOceanButton mContinueButton;
    private SelectServiceListRecyclerViewAdapter mSelectServiceListRecyclerViewAdapter;
    private List<Service> mServiceList;
    private ArrayList<Service> mSelectedServiceArrayList;
    private SelectServicePresenter mSelectServicePresenter;
    private int mCategoryId = -1;
    private Profession mProfession = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_services);
        init();
        setToolbarTitle("Select desired services");
        setHomeAsUpEnabled(true);
        Bundle extras = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        if (extras != null) {
            mProfession = extras.getParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA);
            mCategoryId = extras.getInt(DocOceanConstants.IntentConstants.CATEGORY_ID);
        }
        mServiceList = new ArrayList<>();
        mSelectedServiceArrayList = new ArrayList<>();
        mSelectServiceListRecyclerViewAdapter = new SelectServiceListRecyclerViewAdapter(mServiceList, this);
        mServiceRv.setHasFixedSize(true);
        mServiceRv.setLayoutManager(new LinearLayoutManager(this));
        mServiceRv.setAdapter(mSelectServiceListRecyclerViewAdapter);
        mSelectServicePresenter = new SelectServicePresenter();
        mSelectServicePresenter.bindView(this);
        mSelectServicePresenter.getServices(this, mProfession.getId(), mCategoryId);
        showProgressDialog(getString(R.string.text_please_wait));

    }

    @Override
    protected void onDestroy() {
        if (mSelectServicePresenter != null) {
            mSelectServicePresenter.unbindView(this);
            mSelectServicePresenter = null;
        }
        super.onDestroy();
    }

    private void init() {
        mServiceRv = (RecyclerView) findViewById(R.id.select_service_list);
        mContinueButton = (DocOceanButton) findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.continue_button:
                validateAndStartHome();
                break;
        }
    }

    @Override
    public void onDataReceived(Object data) {
        if (data != null && data instanceof HashMap) {
            HashMap<Integer, Service> hashMapService = (HashMap<Integer, Service>) data;
            mSelectedServiceArrayList.clear();
            mSelectedServiceArrayList.addAll(hashMapService.values());
            Log.d(TAG, " size for selected service list is = " + mSelectedServiceArrayList.size());
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof ServiceResponse) {
                cancelProgressDialog();
                ServiceResponse serviceResponse = (ServiceResponse) items;
                if (serviceResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mServiceList.addAll(serviceResponse.getServices());
                    mSelectServiceListRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(serviceResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void validateAndStartHome() {

        if (mSelectedServiceArrayList.size() <= 0) {
            showShortToast("Please select at least one service ");
            return;
        }
        callHome();
    }

    private void callHome() {
        Bundle bundle = new Bundle();
        bundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, HomeMapFragment.class.getSimpleName());
        bundle.putInt(DocOceanConstants.IntentConstants.CATEGORY_ID, mCategoryId);
        bundle.putParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA, mProfession);
        bundle.putParcelableArrayList(DocOceanConstants.IntentConstants.SELECTED_SERVICES, mSelectedServiceArrayList);
        ActivityManager.startHomeActivity(this, bundle);
    }

}
