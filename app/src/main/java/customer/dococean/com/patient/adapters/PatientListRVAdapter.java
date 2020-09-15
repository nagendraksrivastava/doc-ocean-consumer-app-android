package customer.dococean.com.patient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.getpatient.Patient;
import customer.dococean.com.patient.views.custom.DococeanTextView;

/**
 * Created by nagendrasrivastava on 07/08/16.
 */
public class PatientListRVAdapter extends RecyclerView.Adapter<PatientListRVAdapter.PatientViewHolder> {

    private List<Patient> mPatientList;

    public PatientListRVAdapter(List<Patient> mPatientList) {
        this.mPatientList = mPatientList;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_patient_list_layout, parent, false);
        PatientViewHolder viewHolder = new PatientViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        Patient patient = mPatientList.get(position);
        if (patient.getName() != null)
            holder.mPatientNameTV.setText(patient.getName());
        if (patient.getDateOfBirth() != null)
            holder.mPatientAgeTv.setText(patient.getDateOfBirth());
        if (patient.getRelationship() != null)
            holder.mPatientRelationshipTv.setText(patient.getRelationship());
    }

    @Override
    public int getItemCount() {
        return mPatientList.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {

        private DococeanTextView mPatientNameTV;
        private DococeanTextView mPatientAgeTv;
        private DococeanTextView mPatientRelationshipTv;


        public PatientViewHolder(View itemView) {
            super(itemView);
            mPatientNameTV = (DococeanTextView) itemView.findViewById(R.id.row_patient_name);
            mPatientAgeTv = (DococeanTextView) itemView.findViewById(R.id.row_patient_age);
            mPatientRelationshipTv = (DococeanTextView) itemView.findViewById(R.id.row_patient_relationship);
        }
    }
}
