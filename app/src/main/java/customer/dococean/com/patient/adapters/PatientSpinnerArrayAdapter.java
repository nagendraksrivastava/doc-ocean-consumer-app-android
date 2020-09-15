package customer.dococean.com.patient.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.getpatient.Patient;
import customer.dococean.com.patient.views.custom.DococeanTextView;

/**
 * Created by nagendrasrivastava on 13/08/16.
 */
public class PatientSpinnerArrayAdapter extends ArrayAdapter<Patient> {

    private Activity mActivity;
    private List<Patient> mPatientList;

    public PatientSpinnerArrayAdapter(Activity activity, List<Patient> patient) {
        super(activity, android.R.layout.simple_list_item_1, patient);
        this.mActivity = activity;
        this.mPatientList = patient;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);

        if (v == null) {
            v = new TextView(mActivity);
        }
        v.setTextColor(Color.BLACK);
        v.setText(mPatientList.get(position).getName());
        return v;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.patient_spinner_row, parent, false);
        }
        DococeanTextView patientName = (DococeanTextView) view.findViewById(R.id.spinner_patient_name);
        patientName.setText(mPatientList.get(position).getName());
        return view;
    }

    @Override
    public Patient getItem(int position) {
        return mPatientList.get(position);
    }


}
