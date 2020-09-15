package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.PatientListRVAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.getpatient.GetPatientResponse;
import customer.dococean.com.patient.network.response.getpatient.Patient;
import customer.dococean.com.patient.presenters.PatientPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.views.custom.DocOceanButton;

public class PatientListActivity extends ToolbarActivity implements UIUpdateListener, View.OnClickListener {

    private DocOceanButton mAddPatientButton;
    private RecyclerView mPatientRv;
    private PatientPresenter mPatientPresenter;
    private List<Patient> mPatientList = new ArrayList<>();
    private PatientListRVAdapter mPatientListRvAdapter;
    private RecyclerView.LayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_acivity);
        init();
        setHomeAsUpEnabled(true);
        setToolbarTitle("Patient List");
        mPatientPresenter = new PatientPresenter();
        mPatientPresenter.bindView(this);
        setListeners();
        mPatientListRvAdapter = new PatientListRVAdapter(mPatientList);
        mPatientRv.setAdapter(mPatientListRvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPatientPresenter.getPatients(this);
    }

    private void setListeners() {
        mAddPatientButton.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        mPatientPresenter.unbindView(this);
        mPatientPresenter = null;
        super.onDestroy();
    }

    private void init() {
        mAddPatientButton = (DocOceanButton) findViewById(R.id.add_patient_button);
        mPatientRv = (RecyclerView) findViewById(R.id.patient_list_RV);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mPatientRv.setHasFixedSize(true);
        mPatientRv.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            GetPatientResponse getPatientResponse = (GetPatientResponse) items;
            if (getPatientResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                mPatientList.clear();
                mPatientList.addAll(getPatientResponse.getPatients());
                mPatientListRvAdapter.notifyDataSetChanged();
            } else {
                showShortToast(getPatientResponse.getStatus().getMessage());
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_patient_button:
                ActivityManager.startAddPatientActivity(this, null);
                break;
        }
    }
}
