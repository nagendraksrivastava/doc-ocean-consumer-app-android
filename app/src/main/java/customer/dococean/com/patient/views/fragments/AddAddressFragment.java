package customer.dococean.com.patient.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.model.AddressModel;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.AddressRequest;
import customer.dococean.com.patient.network.response.saveaddress.SaveAddressResponse;
import customer.dococean.com.patient.presenters.AddressPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.activities.AddressActivity;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;
import customer.dococean.com.patient.views.custom.DococeanTextView;

public class AddAddressFragment extends BaseFragment implements View.OnClickListener, UIUpdateListener {

    private static final String TAG = "AddAddressFragment";
    private static final String HOME_ADDRESS_TAG = "home";
    private static final String WORK_ADDRESS_TAG = "work";
    private static final String CLINIC_ADDRESS_TAG = "clinic";
    private AddressModel mSelectedAddress;
    private DocOceanEditText mSelectedAddressEditText;
    private DocOceanEditText mBuildingNumberET;
    private DocOceanEditText mLandmarkEt;
    private DocOceanButton mSaveAddressButton;
    private LinearLayout mHomeAddressLL;
    private LinearLayout mWorkAddressLL;
    private LinearLayout mClinicAddressLL;
    private DococeanTextView mHomeAddressTV;
    private DococeanTextView mWorkAddressTV;
    private DococeanTextView mClinicAddressTV;

    private String mSelectedAddressTag;

    private AddressPresenter mAddressPresenter;
    private AddressActivity mAddressActivity;
    //private String mDiplayAddress;


    public AddAddressFragment() {
        // Required empty public constructor
    }


    public static AddAddressFragment newInstance(AddressModel selectedAddress) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS, selectedAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedAddress = getArguments().getParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS);
        }
        mAddressPresenter = new AddressPresenter();
        mAddressPresenter.bindView(this);
    }

    @Override
    public void onDestroy() {
        if (mAddressPresenter != null) {
            mAddressPresenter.unbindView(this);
            mAddressPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setOnClickListeners();
        mSelectedAddressEditText.setText(mSelectedAddress.getAddress());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddressActivity)
            mAddressActivity = (AddressActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(View view) {
        mSelectedAddressEditText = (DocOceanEditText) view.findViewById(R.id.address_Et);
        mBuildingNumberET = (DocOceanEditText) view.findViewById(R.id.building_flat_no_Et);
        mLandmarkEt = (DocOceanEditText) view.findViewById(R.id.landmark_Et);
        mSaveAddressButton = (DocOceanButton) view.findViewById(R.id.save_address);
        mHomeAddressLL = (LinearLayout) view.findViewById(R.id.home_address_layout);
        mWorkAddressLL = (LinearLayout) view.findViewById(R.id.work_address_layout);
        mClinicAddressLL = (LinearLayout) view.findViewById(R.id.clinic_address_layout);
        mHomeAddressTV = (DococeanTextView) view.findViewById(R.id.home_address_TV);
        mWorkAddressTV = (DococeanTextView) view.findViewById(R.id.work_address_TV);
        mClinicAddressTV = (DococeanTextView) view.findViewById(R.id.clinic_address_TV);
    }

    private void setOnClickListeners() {
        mSaveAddressButton.setOnClickListener(this);
        mHomeAddressLL.setOnClickListener(this);
        mWorkAddressLL.setOnClickListener(this);
        mClinicAddressLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_address:
                validateAndSendData();
                break;
            case R.id.home_address_layout:
                mSelectedAddressTag = HOME_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.black));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.work_address_layout:
                mSelectedAddressTag = WORK_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.black));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.clinic_address_layout:
                mSelectedAddressTag = CLINIC_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.black));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.black));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
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
            if (items instanceof SaveAddressResponse) {
                SaveAddressResponse saveAddressResponse = (SaveAddressResponse) items;
                if (saveAddressResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast("Address successfully added ");
                    ActivityManager.startSelectCategoryActivity(mAddressActivity, null);
                    mAddressActivity.finish();
                } else {
                    showShortToast(saveAddressResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void validateAndSendData() {
//        if (mBuildingNumberET.getText() == null || mBuildingNumberET.getText().toString().isEmpty()) {
//            mBuildingNumberET.setError("Enter building no ");
//            return;
//        }

        AddressRequest addressRequest = new AddressRequest();
        String diplayAddress = mBuildingNumberET.getText().toString() + ", " + mSelectedAddress.getAddress();
        addressRequest.setAddressLine(diplayAddress);
        addressRequest.setLatitude(mSelectedAddress.getLatitude());
        addressRequest.setLongitude(mSelectedAddress.getLongitude());
        if (mLandmarkEt.getText() != null)
            addressRequest.setLandmark(mLandmarkEt.getText().toString().trim());
        if (mSelectedAddressTag != null) {
            addressRequest.setTag(mSelectedAddressTag);
        }
        mAddressPresenter.sendAddress(getContext(), addressRequest);
        showProgressDialog(getString(R.string.text_please_wait), mAddressActivity);

    }
}
