package customer.dococean.com.patient.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.profession_response.Category;
import customer.dococean.com.patient.network.response.service_response.Service;
import customer.dococean.com.patient.views.activities.SelectServiceActivity;

/**
 * Created by nagendrasrivastava on 27/09/16.
 */

public class SelectServiceListRecyclerViewAdapter extends RecyclerView.Adapter<SelectServiceListRecyclerViewAdapter.SelectServiceViewHolder> {

    private static final String TAG = "SelectServiceListRecycl";
    private List<Service> mServiceList;
    private SelectServiceActivity mSelectionServiceActivity;
    private HashMap<Integer, Service> mSelectedServiceHashmap = new HashMap<>();

    public SelectServiceListRecyclerViewAdapter(List<Service> categories, SelectServiceActivity activity) {
        this.mServiceList = categories;
        mSelectionServiceActivity = activity;
    }


    @Override
    public SelectServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectServiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_service_list_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectServiceViewHolder holder, int position) {
        Service service = mServiceList.get(position);
        holder.mCheckedTextview.setText(service.getName());
        holder.mCheckedTextview.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mServiceList.size();
    }

    class SelectServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckedTextView mCheckedTextview;

        SelectServiceViewHolder(View itemView) {
            super(itemView);
            mCheckedTextview = (CheckedTextView) itemView.findViewById(R.id.select_service_checked_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) mCheckedTextview.getTag();
            if (mCheckedTextview.isChecked()) {
                Log.d(TAG, " Checked value is = " + mServiceList.get(position).getName());
                mCheckedTextview.setChecked(false);
                Service service = mServiceList.get(position);
                mSelectedServiceHashmap.remove(service.getId());
            } else {
                Log.d(TAG, " Unchecked value is = " + mServiceList.get(position).getName());
                mCheckedTextview.setChecked(true);
                Service service = mServiceList.get(position);
                mSelectedServiceHashmap.put(service.getId(), service);
            }
            mSelectionServiceActivity.onDataReceived(mSelectedServiceHashmap);

        }
    }
}
