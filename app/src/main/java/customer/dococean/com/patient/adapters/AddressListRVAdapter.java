package customer.dococean.com.patient.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.getaddress.Address;
import customer.dococean.com.patient.network.response.getpatient.Patient;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.activities.AddressSearchActivity;
import customer.dococean.com.patient.views.custom.DococeanTextView;

/**
 * Created by nagendrasrivastava on 07/08/16.
 */
public class AddressListRVAdapter extends RecyclerView.Adapter<AddressListRVAdapter.AddressViewHolder> {

    private List<Address> mAddressList;
    private AddressSearchActivity mAddressSearchActivity;

    public AddressListRVAdapter(Context context, List<Address> mPatientList) {
        this.mAddressList = mPatientList;
        this.mAddressSearchActivity = (AddressSearchActivity) context;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_address_list_layout, parent, false);
        return new AddressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        Address address = mAddressList.get(position);
        if (address.getTag() != null) {
            holder.mAddressTagNameTV.setText(address.getTag().toUpperCase());
            if (address.getTag().equalsIgnoreCase(DocOceanConstants.AddressTag.HOME)) {
                holder.mAddressTagImageView.setImageResource(R.drawable.ic_home_grey_500_24dp);
            } else if (address.getTag().equalsIgnoreCase(DocOceanConstants.AddressTag.WORK)) {
                holder.mAddressTagImageView.setImageResource(R.drawable.ic_work_grey_500_24dp);
            }
        }

        if (address.getAddressLine() != null)
            holder.mAddressDetailsAgeTV.setText(address.getAddressLine());
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DococeanTextView mAddressTagNameTV;
        private DococeanTextView mAddressDetailsAgeTV;
        private ImageView mAddressEditImageView;
        private ImageView mAddressTagImageView;


        AddressViewHolder(View itemView) {
            super(itemView);
            mAddressTagNameTV = (DococeanTextView) itemView.findViewById(R.id.address_tag_name_TV);
            mAddressDetailsAgeTV = (DococeanTextView) itemView.findViewById(R.id.address_details);
            mAddressEditImageView = (ImageView) itemView.findViewById(R.id.address_edit_IV);
            mAddressTagImageView = (ImageView) itemView.findViewById(R.id.address_tag_IV);
            mAddressEditImageView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.address_edit_IV:
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS, mAddressList.get(getAdapterPosition()));
                    ActivityManager.startAddressActivity(mAddressSearchActivity, bundle);
                    break;
                default:
                    mAddressSearchActivity.selectedAddress(mAddressList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
