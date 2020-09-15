package customer.dococean.com.patient.views.activities;

import android.os.Bundle;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.getaddress.Address;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.fragments.MapAddressFragment;

public class AddressActivity extends ToolbarActivity {

    private Address mSelectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setToolbarTitle("Add Address");
        Bundle bundle = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        if (bundle != null)
            mSelectedAddress = bundle.getParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS);
        changeFragmentNoBackstack(R.id.flContent, MapAddressFragment.newInstance(mSelectedAddress));

    }
}
