package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.NothingSelectedSpinnerAdapter;
import customer.dococean.com.patient.adapters.PatientSpinnerArrayAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.model.DOPlace;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.getpatient.GetPatientResponse;
import customer.dococean.com.patient.network.response.getpatient.Patient;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.network.response.service_response.Service;
import customer.dococean.com.patient.presenters.PatientPresenter;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.fragments.BookingFragment;

public class BookingActivity extends ToolbarActivity implements AdapterView.OnItemSelectedListener, UIUpdateListener {

    public static final String TAG = BookingActivity.class.getSimpleName();

    private String mFragmentName;
    private Spinner mSelectPatientSpinner;
    private ArrayList<Patient> mPatientList;
    private PatientPresenter mPatientPresenter;
    private String mPlaceDecider;
    //private String mSelectedUserAddress;
    //private String mSelectedAddressId;
    private DOPlace mDOPlace;
    private String mTotalCost;
    private ArrayList<Service> mOptedServiceList;
    private Profession mProfession;
    private int mCategoryId = -1;
    private String mScheduleTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setHomeAsUpEnabled(true);
        setToolbarTitle("Booking Confirmation");
        init();
        setListeners();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFragmentName = extras.getString(DocOceanConstants.IntentConstants.FRAGMENT_NAME);
            mPlaceDecider = extras.getString(DocOceanConstants.IntentConstants.WILL_GO_TO_PATIENT_PLACE_OR_NOT);
            mDOPlace = extras.getParcelable(DocOceanConstants.IntentConstants.USER_ADDRESS);
            mTotalCost = extras.getString(DocOceanConstants.IntentConstants.TOTAL_COST);
            mOptedServiceList = extras.getParcelableArrayList(DocOceanConstants.IntentConstants.OPTED_SERVICE_LIST);
            mProfession = extras.getParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA);
            mCategoryId = extras.getInt(DocOceanConstants.IntentConstants.CATEGORY_ID);
            mScheduleTime = extras.getString(DocOceanConstants.IntentConstants.SCHEDULED_TIME);
            Log.d(TAG, " Schedule Time is = " + mScheduleTime);
        }
        changeFragment(mFragmentName);
    }


    @Override
    protected void onDestroy() {
        if (mPatientPresenter != null) {
            mPatientPresenter.unbindView(this);
            mPatientPresenter = null;
        }
        mScheduleTime = null;
        super.onDestroy();
    }

    private void init() {
        mSelectPatientSpinner = (Spinner) findViewById(R.id.select_patient);
        mPatientPresenter = new PatientPresenter();
        mPatientPresenter.bindView(this);
        mPatientPresenter.getPatients(this);
        showProgressDialog(getString(R.string.text_fetching_patient_list));
    }

    /**
     * This method is used to get the address in Booking Fragment
     *
     * @return
     */
    public DOPlace getSelectedUserAddress() {
        return mDOPlace;
    }


    /**
     * This method is used to get the total cost in Booking fragment
     *
     * @return
     */
    public String getTotalCost() {
        return mTotalCost;
    }

    /**
     * This method is used to get the optedServices list in Booking fragment
     *
     * @return
     */
    public ArrayList<Service> getOptedServiceList() {
        return mOptedServiceList;
    }

    /**
     * This methid is used to get Profession in Booking fragment
     *
     * @return
     */
    public Profession getProfession() {
        return mProfession;
    }

    /**
     * This method is used to get category id in Booking Fragmnet
     *
     * @return
     */
    public int getCategoryId() {
        return mCategoryId;
    }

    /**
     * Getting scheduled time
     *
     * @return
     */
    public String getScheduleTime() {
        return mScheduleTime;
    }

    private void setListeners() {
        mSelectPatientSpinner.setOnItemSelectedListener(this);
    }

    private void addDataToSpinner() {
        PatientSpinnerArrayAdapter patientSpinnerArrayAdapter = new PatientSpinnerArrayAdapter(this, mPatientList);
        patientSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectPatientSpinner.setAdapter(new NothingSelectedSpinnerAdapter(patientSpinnerArrayAdapter, android.R.layout.simple_spinner_dropdown_item, this, getResources().getString(R.string.text_select_patient)));
    }

    private void changeFragment(String fragment) {
        if (fragment.equalsIgnoreCase(BookingFragment.class.getSimpleName())) {
            changeFragmentWithOutBackStack(R.id.fragmentContainer, BookingFragment.newInstance(mPlaceDecider), BookingFragment.class.getSimpleName());
        }
    }

    private void informRespectiveFragments(Patient data) {
        BookingFragment docFragment = (BookingFragment) getSupportFragmentManager().findFragmentByTag(BookingFragment.class.getSimpleName());
        docFragment.informToFragment(data);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        DocOceanLogger.d(TAG, " position is = " + position);
        DocOceanLogger.d(TAG, "Value is = " + adapterView.getItemAtPosition(position));
        if (adapterView.getItemAtPosition(position) != null) {
            informRespectiveFragments((Patient) adapterView.getItemAtPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof GetPatientResponse) {
                cancelProgressDialog();
                GetPatientResponse getPatientResponse = (GetPatientResponse) items;
                if (getPatientResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mPatientList = new ArrayList<>();
                    for (Patient patient : getPatientResponse.getPatients()) {
                        mPatientList.add(patient);
                    }
                    Patient patient = new Patient();
                    patient.setName(DocOceanConstants.OTHER_PATIENT);
                    mPatientList.add(patient);
                    addDataToSpinner();
                } else {
                    showShortToast(getPatientResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }
}
