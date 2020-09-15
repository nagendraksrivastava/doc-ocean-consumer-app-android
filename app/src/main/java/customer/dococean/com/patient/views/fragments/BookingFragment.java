package customer.dococean.com.patient.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.NothingSelectedSpinnerAdapter;
import customer.dococean.com.patient.interfaces.FragmentCommunicator;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.AppointmentRequest;
import customer.dococean.com.patient.network.requests.Patient;
import customer.dococean.com.patient.network.response.createappointment.Address;
import customer.dococean.com.patient.network.response.createappointment.CreateAppointmentResponse;
import customer.dococean.com.patient.network.response.get_patient_relationship.GetPatientRelationshipResponse;
import customer.dococean.com.patient.network.response.service_response.Service;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;
import customer.dococean.com.patient.presenters.PatientPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.activities.BookingActivity;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class BookingFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, FragmentCommunicator, UIUpdateListener {

    private static final String TAG = BookingFragment.class.getSimpleName();

    private LinearLayout mMainLinearLayout;
    private DocOceanEditText mPatientNameEt;
    private DocOceanEditText mPatientAgeEt;
    private DocOceanEditText mPatientSymtomEt;
    private DococeanTextView mRelationshipWithPatientTV;
    private DococeanTextView mPatientGenderTV;
    private DococeanTextView mPatientAgeTV;
    private DococeanTextView mPatientAddressTv;
    private DococeanTextView mChangePatientAddressTv;
    private DococeanTextView mTotalCostTextView;
    private DocOceanButton mBookingConfirmButton;
    private TableLayout mOptedServicesTableLayout;
    private CardView mOptedServicesCardView;
    private Spinner mRelationshipSpinner;
    private Spinner mGenderSpinner;
    private CardView mPatientDetailsCardView;
    private LinearLayout mPatentInformationLL;
    private String mSelectedPatientData = null;
    private String mRelationshipWithPatient;
    private List<String> mRelationShipList;
    private List<String> mGenderList;
    private String mPatientGender;
    private customer.dococean.com.patient.network.response.getpatient.Patient mPatientData;
    private BookingActivity mBookingActivity;
    private String mPlaceDecider;
    private Subscription mBookingStatusSubscription;

    private AppointmentPresenter mAppointmentPresenter;
    private PatientPresenter mPatientPresenter;


    public BookingFragment() {
        // Required empty public constructor
    }


    public static BookingFragment newInstance(String placeDecider) {
        BookingFragment fragment = new BookingFragment();
        fragment.mPlaceDecider = placeDecider;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mPatientPresenter = new PatientPresenter();
        mPatientPresenter.bindView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mAppointmentPresenter != null) {
            mAppointmentPresenter.unbindView(this);
            mAppointmentPresenter = null;
        }
        if (mPatientPresenter != null) {
            mPatientPresenter.unbindView(this);
            mPatientPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiViews(view);
        setListeners();
        mPatientPresenter.getPatientRelationship(getContext());
        showProgressDialog(getString(R.string.text_please_wait), mBookingActivity);
        mPatientAddressTv.setText(mBookingActivity.getSelectedUserAddress().getAddress());
        mTotalCostTextView.setText(mBookingActivity.getTotalCost());
        setupOptedServicesViews();

    }


    private void setupOptedServicesViews() {
        if (mBookingActivity.getOptedServiceList().size() > 0) {
            mOptedServicesCardView.setVisibility(View.VISIBLE);
            TableRow tableHeaderRow = new TableRow(getContext());
            TextView serviceHeaderTV = new DococeanTextView(getContext());
            serviceHeaderTV.setText("Services");
            serviceHeaderTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            serviceHeaderTV.setTextColor(getResources().getColor(R.color.black));
            serviceHeaderTV.setGravity(Gravity.CENTER);
            tableHeaderRow.addView(serviceHeaderTV);
            TextView servicecostHeaderTV = new DococeanTextView(getContext());
            servicecostHeaderTV.setText("Charges");
            servicecostHeaderTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            servicecostHeaderTV.setTextColor(getResources().getColor(R.color.black));
            servicecostHeaderTV.setGravity(Gravity.CENTER);
            tableHeaderRow.addView(servicecostHeaderTV);
            mOptedServicesTableLayout.addView(tableHeaderRow);
            for (Service service : mBookingActivity.getOptedServiceList()) {
                TableRow serviceRow = new TableRow(getContext());
                DococeanTextView serviceNameTV = new DococeanTextView(getContext());
                serviceNameTV.setText(service.getName());
                serviceNameTV.setGravity(Gravity.CENTER);
                serviceRow.addView(serviceNameTV);
                DococeanTextView serviceCostTV = new DococeanTextView(getContext());
                serviceCostTV.setText(DocOceanConstants.RUPPIE_UNICODE + String.valueOf(service.getCost()));
                serviceCostTV.setGravity(Gravity.CENTER);
                serviceRow.addView(serviceCostTV);
                mOptedServicesTableLayout.addView(serviceRow);
            }
        } else {
            mOptedServicesCardView.setVisibility(View.GONE);
        }
    }


    private void intiViews(View view) {
        mPatentInformationLL = (LinearLayout) view.findViewById(R.id.patient_information_LL);
        mPatientDetailsCardView = (CardView) view.findViewById(R.id.parent_details_CV);
        mMainLinearLayout = (LinearLayout) view.findViewById(R.id.doc_booking_main_layout);
        mPatientNameEt = (DocOceanEditText) view.findViewById(R.id.booking_confirm_patient_name);
        mPatientAgeEt = (DocOceanEditText) view.findViewById(R.id.booking_confirm_patient_age);
        mPatientAddressTv = (DococeanTextView) view.findViewById(R.id.booking_address_TV);
        mPatientSymtomEt = (DocOceanEditText) view.findViewById(R.id.booking_confirm_patient_symtom);
        mBookingConfirmButton = (DocOceanButton) view.findViewById(R.id.booking_confirm);
        mRelationshipSpinner = (Spinner) view.findViewById(R.id.relationship_with_patient);
        mGenderSpinner = (Spinner) view.findViewById(R.id.booking_confirm_patient_gender);
        mTotalCostTextView = (DococeanTextView) view.findViewById(R.id.total_cost_TV);
        mPatientGenderTV = (DococeanTextView) view.findViewById(R.id.booking_confirm_patient_gender_TV);
        mRelationshipWithPatientTV = (DococeanTextView) view.findViewById(R.id.booking_confirm_patient_relationship_TV);
        mPatientAgeTV = (DococeanTextView) view.findViewById(R.id.booking_confirm_patient_age_TV);
        mOptedServicesTableLayout = (TableLayout) view.findViewById(R.id.confirmed_opted_services_table_layout);
        mOptedServicesCardView = (CardView) view.findViewById(R.id.confirmed_opted_services_card_view);
        mRelationShipList = new ArrayList<>();
        mGenderList = new ArrayList<>();
        mGenderList.addAll(Arrays.asList("MALE", "FEMALE", "OTHER"));
    }

    private void setListeners() {
        mBookingConfirmButton.setOnClickListener(this);
        mRelationshipSpinner.setOnItemSelectedListener(this);
        mGenderSpinner.setOnItemSelectedListener(this);
    }

    private void addDataToSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mRelationShipList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRelationshipSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_relationship)));

        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mGenderList);
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter((new NothingSelectedSpinnerAdapter(genderArrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_gender))));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBookingActivity = (BookingActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.booking_confirm:
                confirmBooking();
                break;
        }
    }

    private void confirmBooking() {

        if (mSelectedPatientData == null) {
            showShortToast("Please select patient");
            return;
        }

        if (mSelectedPatientData.equalsIgnoreCase(DocOceanConstants.OTHER_PATIENT)) {
            if (mPatientNameEt.getText() != null && mPatientNameEt.getText().toString().isEmpty()) {
                mPatientNameEt.setError("Patient name can't be blank");
                return;
            }

            if (mPatientAgeEt.getText() != null && mPatientAgeEt.getText().toString().isEmpty()) {
                mPatientAgeEt.setError("Patient age can't be blank");
                return;
            }

            if (mGenderSpinner.getSelectedItem() == null) {
                Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mRelationshipSpinner.getSelectedItem() == null) {
                Toast.makeText(getContext(), "Please select relationship", Toast.LENGTH_SHORT).show();
                return;
            }
        }

//        if (mPatientSymtomEt.getText() != null && mPatientSymtomEt.getText().toString().isEmpty()) {
//            mPatientSymtomEt.setError(" Please enter patient symptoms ");
//            return;
//        }

        AppUtils.hideKeyboard(mBookingActivity);
        ArrayList<Integer> optedServiceIds = new ArrayList<>();
        for (Service service : mBookingActivity.getOptedServiceList()) {
            optedServiceIds.add(service.getId());
        }
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setProfessionId(mBookingActivity.getProfession().getId());
        appointmentRequest.setCategoryId(mBookingActivity.getCategoryId());
        appointmentRequest.setOptedServiceIds(optedServiceIds);
        if (mSelectedPatientData.equalsIgnoreCase(DocOceanConstants.OTHER_PATIENT)) {
            Patient patient = new Patient();
            patient.setName(mPatientNameEt.getText().toString());
            patient.setAge(Integer.parseInt(mPatientAgeEt.getText().toString()));
            patient.setGender(mPatientGender);
            patient.setRelationship(mRelationshipWithPatient);
            appointmentRequest.setPatient(patient);
        } else {
            appointmentRequest.setPatientId(mPatientData.getId());
        }
        if (mBookingActivity.getSelectedUserAddress() != null) {
            try {
                if (mBookingActivity.getSelectedUserAddress().getId() != null)
                    appointmentRequest.setPatientAddressId(Integer.parseInt(mBookingActivity.getSelectedUserAddress().getId()));
                setAddressRequest(appointmentRequest);
            } catch (NumberFormatException nforEx) {
                setAddressRequest(appointmentRequest);
            }
        }


        Log.d(TAG, " Schedule Time is = " + mBookingActivity.getScheduleTime());
        if (mBookingActivity.getScheduleTime() != null) {
            appointmentRequest.setScheduleTime(mBookingActivity.getScheduleTime());
        }
        appointmentRequest.setSymptoms(mPatientSymtomEt.getText().toString());
        appointmentRequest.setServicePlace(mPlaceDecider);
        mAppointmentPresenter.createBooking(getContext(), appointmentRequest);
        showProgressDialog(getString(R.string.text_we_are_contacting_our_experts), mBookingActivity);
    }

    private void setAddressRequest(AppointmentRequest appointmentRequest) {
        Address address = new Address();
        address.setAddressLine(mBookingActivity.getSelectedUserAddress().getAddress());
        address.setLatitude(mBookingActivity.getSelectedUserAddress().getLatLng().latitude);
        address.setLongitude(mBookingActivity.getSelectedUserAddress().getLatLng().longitude);
        appointmentRequest.setPatientAddress(address);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView.getItemAtPosition(position) != null) {
            switch (adapterView.getId()) {
                case R.id.relationship_with_patient:
                    mRelationshipWithPatient = adapterView.getItemAtPosition(position).toString();
                    break;
                case R.id.booking_confirm_patient_gender:
                    mPatientGender = adapterView.getItemAtPosition(position).toString();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void informToFragment(Object items) {
        if (mMainLinearLayout.getVisibility() == View.GONE)
            mMainLinearLayout.setVisibility(View.VISIBLE);
        if (items != null) {
            customer.dococean.com.patient.network.response.getpatient.Patient data = (customer.dococean.com.patient.network.response.getpatient.Patient) items;
            mPatientData = data;
            mSelectedPatientData = data.getName();
            if (data.getName().equalsIgnoreCase(DocOceanConstants.OTHER_PATIENT)) {
                arrangementForOthers();
                mRelationshipWithPatientTV.setText("NA");
                mPatientAgeTV.setText("NA");
                mPatientGenderTV.setText("NA");

            } else {
                makeMainLayoutVisible();
                mRelationshipWithPatientTV.setText(data.getRelationship());
                mPatientAgeTV.setText(data.getDateOfBirth());
                mPatientGenderTV.setText(data.getGender());
            }
        }
    }

    private void makeMainLayoutVisible() {
        if (mPatentInformationLL.getVisibility() == View.VISIBLE)
            mPatentInformationLL.setVisibility(View.GONE);
        if (mPatientDetailsCardView.getVisibility() == View.GONE)
            mPatientDetailsCardView.setVisibility(View.VISIBLE);

    }

    /**
     * Hide and show views if patient information is not availble
     */
    private void arrangementForOthers() {
        if (mPatentInformationLL.getVisibility() == View.GONE)
            mPatentInformationLL.setVisibility(View.VISIBLE);
        if (mPatientDetailsCardView.getVisibility() == View.VISIBLE)
            mPatientDetailsCardView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {

        if (items != null) {
            if (items instanceof GetPatientRelationshipResponse) {
                cancelProgressDialog();
                Log.d(TAG, " Cancel progress dialog 1 ");
                GetPatientRelationshipResponse getPatientRelationship = (GetPatientRelationshipResponse) items;
                mRelationShipList = getPatientRelationship.getRelationships();
                addDataToSpinner();
            } else if (items instanceof CreateAppointmentResponse) {
                cancelProgressDialog();
                Log.d(TAG, " Cancel progress dialog 2 ");
                CreateAppointmentResponse appointmentResponse = (CreateAppointmentResponse) items;
                if (appointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    if (appointmentResponse.getData().getScheduledAt() != null) {
                        showLongToast("Your appointment has been scheduled , we will keep notifying you about status ");
                        mBookingActivity.finish();
                    } else {
                        pollBookingStatus(appointmentResponse.getData().getNumber());
                    }
                } else {
                    showShortToast(appointmentResponse.getStatus().getMessage());
                }

            } else if (items instanceof TrackBookingResponse) {
                TrackBookingResponse trackBookingResponse = (TrackBookingResponse) items;
                if (trackBookingResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    setupOnceConfirmed(trackBookingResponse);
                } else {
                    showShortToast(trackBookingResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
        Log.d(TAG, " Cancel progress dialog 3 ");
    }


    private void pollBookingStatus(final String bookingId) {
        showProgressDialog(getString(R.string.text_we_are_contacting_our_experts), mBookingActivity);
        mBookingStatusSubscription = Schedulers.io().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                mAppointmentPresenter.trackBookingStatus(getContext(), bookingId);
            }
        }, 0, DocOceanConstants.IntegerConstants.PERIOIDIC_TRACK, TimeUnit.SECONDS);
    }

    private void unsubscribeBookingStatusPolling() {
        if (!mBookingStatusSubscription.isUnsubscribed())
            mBookingStatusSubscription.unsubscribe();
    }


    private void setupOnceConfirmed(TrackBookingResponse trackBookingResponse) {
        if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.CONFIRMED)) {
            cancelProgressDialog();
            unsubscribeBookingStatusPolling();
            Bundle bundle = new Bundle();
            bundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, BookingConfirmedFragment.class.getSimpleName());
            bundle.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, trackBookingResponse);
            ActivityManager.startBookingConfirmedActivityWithNewTask(getContext(), bundle);
        } else if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.ASSIGN_FAILED)) {
            unsubscribeBookingStatusPolling();
            showLongToast(getString(R.string.text_experts_busy));
            cancelProgressDialog();
        }
    }


}
