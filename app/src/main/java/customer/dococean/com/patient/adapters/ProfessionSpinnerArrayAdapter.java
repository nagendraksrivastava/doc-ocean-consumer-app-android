package customer.dococean.com.patient.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.views.custom.DococeanTextView;

/**
 * Created by nagendrasrivastava on 02/09/16.
 */
public class ProfessionSpinnerArrayAdapter extends ArrayAdapter<Profession> {


    private List<Profession> mProfessionList;
    private Activity mActivity;

    public ProfessionSpinnerArrayAdapter(Activity context, List<Profession> professionList) {
        super(context, android.R.layout.simple_list_item_1, professionList);
        this.mProfessionList = professionList;
        this.mActivity = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);

        if (v == null) {
            v = new TextView(mActivity);
        }
        v.setTextColor(Color.BLACK);
        v.setText(mProfessionList.get(position).getName());
        return v;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.profession_spinner_row, parent, false);
        }
        DococeanTextView patientName = (DococeanTextView) view.findViewById(R.id.spinner_profession_name);
        patientName.setText(mProfessionList.get(position).getName());
        return view;
    }

    @Override
    public Profession getItem(int position) {
        return mProfessionList.get(position);
    }
}
