package customer.dococean.com.patient.views.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.NothingSelectedSpinnerAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.CreatePatientRequest;
import customer.dococean.com.patient.network.response.create_patient_response.CreatePatientResponse;
import customer.dococean.com.patient.presenters.PatientPresenter;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DocOceanEditText;

public class AddPatientInformationActivity extends ToolbarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, UIUpdateListener {

    private static final String TAG = "AddPatientInformationAc";
    private static DocOceanEditText mPatientDateOfBirthET;
    private DocOceanEditText mFirstNameEt;
    private TextInputLayout mFirstNameTIL;
    private DocOceanEditText mEmailEt;
    private DocOceanEditText mPhoneNoEt;
    private TextInputLayout mPhoneNoTIL;
    private TextInputLayout mDateOfBirthTIL;
    private Spinner mRelationshipSpinner;
    private Spinner mGenderSpinner;
    private DocOceanEditText mExtraInformationEt;
    private DocOceanButton mSubmitButton;
    private String mRelationshipWithPatient;
    private String mPatientGender;
    private PatientPresenter mPatientPresenter;
    private String mDateOfBirthErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_information);
        setToolbarTitle("Add Patient");
        setHomeAsUpEnabled(true);
        initViews();
        setListeners();
        addDataToRelationshipSpinner();
        addDataToGenderSpinner();
        mPatientPresenter = new PatientPresenter();
        mPatientPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPatientPresenter != null) {
            mPatientPresenter.unbindView(this);
        }
        super.onDestroy();
    }

    private void initViews() {
        mFirstNameEt = (DocOceanEditText) findViewById(R.id.first_name_et);
        mFirstNameTIL = (TextInputLayout) findViewById(R.id.first_name_TIL);
        mEmailEt = (DocOceanEditText) findViewById(R.id.email_et);
        mPhoneNoEt = (DocOceanEditText) findViewById(R.id.phone_no_et);
        mPhoneNoTIL = (TextInputLayout) findViewById(R.id.phone_no_TIL);
        mPatientDateOfBirthET = (DocOceanEditText) findViewById(R.id.patient_date_of_birth_et);
        mDateOfBirthTIL = (TextInputLayout) findViewById(R.id.patient_date_of_birth_TIL);
        mRelationshipSpinner = (Spinner) findViewById(R.id.relationship_spinner);
        mExtraInformationEt = (DocOceanEditText) findViewById(R.id.extra_information_et);
        mSubmitButton = (DocOceanButton) findViewById(R.id.submit_button);
        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);
    }


    private void setListeners() {
        mSubmitButton.setOnClickListener(this);
        mPatientDateOfBirthET.setOnClickListener(this);
        mRelationshipSpinner.setOnItemSelectedListener(this);
        mGenderSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_button:
                validateData();
                break;

            case R.id.patient_date_of_birth_et:
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void validateData() {
        if (mFirstNameEt.getText() == null || mFirstNameEt.getText().toString().isEmpty()) {
            mFirstNameTIL.setError("Full name can't be blank");
            return;
        }

        if (mRelationshipWithPatient == null) {
            showShortToast("Relationship can't be blank");
            return;
        }
        if (mPatientGender == null) {
            showShortToast("Please select gender");
            return;
        }

        createPatient();
    }

    private void createPatient() {
        CreatePatientRequest createPatientRequest = new CreatePatientRequest();
        createPatientRequest.setName(mFirstNameEt.getText().toString());
        createPatientRequest.setRelationship(mRelationshipWithPatient);
        createPatientRequest.setDateOfBirth(mPatientDateOfBirthET.getText().toString());
        createPatientRequest.setGender(mPatientGender);

        if (mEmailEt.getText() != null && !mEmailEt.getText().toString().isEmpty()) {
            createPatientRequest.setEmail(mEmailEt.getText().toString());
        }

        if (mPhoneNoEt.getText() != null && !mPhoneNoEt.getText().toString().isEmpty()) {
            createPatientRequest.setPhoneNo(mPhoneNoEt.getText().toString());
        }

        if (mExtraInformationEt.getText() != null && !mExtraInformationEt.getText().toString().isEmpty()) {
            createPatientRequest.setChronicHealthProblem(mExtraInformationEt.getText().toString());
        }
        if (mDateOfBirthErrorMessage != null) {
            mDateOfBirthTIL.setError(mDateOfBirthErrorMessage);
            return;
        }
        mPatientPresenter.createPatients(this, createPatientRequest);

    }

    private void addDataToRelationshipSpinner() {
        List<String> relationShip = new ArrayList<>();
        relationShip.add("Daughter");
        relationShip.add("Son");
        relationShip.add("Mother");
        relationShip.add("Father");
        relationShip.add("Uncle");
        relationShip.add("Brother");
        relationShip.add("Sister");
        relationShip.add("Friend");
        relationShip.add("Relative");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, relationShip);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRelationshipSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, this, getResources().getString(R.string.select_relationship)));
    }


    private void addDataToGenderSpinner() {
        List<String> genderList = new ArrayList<>();
        genderList.add("MALE");
        genderList.add("FEMALE");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, this, getResources().getString(R.string.select_gender)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.relationship_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mRelationshipWithPatient = adapterView.getItemAtPosition(position).toString();
                }
                break;
            case R.id.gender_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mPatientGender = adapterView.getItemAtPosition(position).toString();
                }
                break;
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
            if (items instanceof CreatePatientResponse) {
                CreatePatientResponse createPatientResponse = (CreatePatientResponse) items;
                if (createPatientResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast("Patient has been created successfully");
                    finish();
                } else {
                    showShortToast(createPatientResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dayString, monthString;
            if (day < 10) {
                dayString = 0 + String.valueOf(day);
            } else {
                dayString = String.valueOf(day);
            }

            if (month < 10) {
                monthString = 0 + String.valueOf(month);
            } else {
                monthString = String.valueOf(month);
            }


            String stringBuilder = dayString +
                    "-" +
                    monthString +
                    "-" +
                    year;
            mPatientDateOfBirthET.setText(stringBuilder);
        }
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
        try {
            mPatientDateOfBirthET.setText(sdf.format(myCalendar.getTime()));
            mDateOfBirthErrorMessage = null;

        } catch (IllegalArgumentException ex) {
            mDateOfBirthErrorMessage = getString(R.string.born_in_future);
            mDateOfBirthTIL.setError(getString(R.string.born_in_future));
        }
    }
}
