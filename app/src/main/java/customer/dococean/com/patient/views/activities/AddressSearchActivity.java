package customer.dococean.com.patient.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.AddressListRVAdapter;
import customer.dococean.com.patient.adapters.PlaceAutocompleteAdapter;
import customer.dococean.com.patient.interfaces.AddressFromList;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.model.DOPlace;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.getaddress.Address;
import customer.dococean.com.patient.network.response.getaddress.GetAddressResponse;
import customer.dococean.com.patient.presenters.AddressPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.custom.ClearableAutoCompleteTextView;
import customer.dococean.com.patient.views.custom.DocOceanButton;

public class AddressSearchActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemClickListener, TextWatcher, View.OnClickListener, UIUpdateListener, AddressFromList {

    private static final String TAG = "AddressSearchActivity";

    private static final CharacterStyle STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);

    protected GoogleApiClient mGoogleApiClient;
    private ClearableAutoCompleteTextView mAutocompleteView;
    private PlaceAutocompleteAdapter mPlacesAdapter;
    private AutocompleteFilter autocompleteFilter;
    private RelativeLayout mDropLocationRL;
    private AddressPresenter mAddressPresenter;
    private List<Address> mAddressList;
    private RecyclerView mAddressRV;
    private RecyclerView.LayoutManager mLinearLayoutManager;
    private AddressListRVAdapter mAddressListRVAdapter;
    private DocOceanButton mAddAddressButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);
        init();
        String location = getIntent().getStringExtra(DocOceanConstants.IntentConstants.LOCATION);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mAutocompleteView = (ClearableAutoCompleteTextView)
                findViewById(R.id.auto_complete_view);
        mDropLocationRL = (RelativeLayout) findViewById(R.id.fragmentContainer);
        mDropLocationRL.setOnClickListener(this);
        mAutocompleteView.setThreshold(0);
        mAutocompleteView.addTextChangedListener(this);
        mAutocompleteView.setOnItemClickListener(this);
        autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();
        mPlacesAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, null, autocompleteFilter);
        mAutocompleteView.setAdapter(mPlacesAdapter);

        if (location != null && location.length() > 0) {
            mAutocompleteView.setText(location);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPlacesAdapter.getFilter().filter(null);
                showKeyboard();
            }
        }, 500);
    }

    private void init() {
        mAddressList = new ArrayList<>();
        mAddAddressButton = (DocOceanButton) findViewById(R.id.add_address);
        mAddressRV = (RecyclerView) findViewById(R.id.addres_list_RV);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAddressRV.setHasFixedSize(true);
        mAddressRV.setLayoutManager(mLinearLayoutManager);
        mAddressPresenter = new AddressPresenter();
        mAddressPresenter.bindView(this);
        mAddressListRVAdapter = new AddressListRVAdapter(this, mAddressList);
        mAddressRV.setAdapter(mAddressListRVAdapter);
        mAddAddressButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressDialog(getString(R.string.text_please_wait));
        mAddressPresenter.getAddress(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        DocOceanLogger.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
        showShortToast("Could not connect to Google API Client: Error " + connectionResult.getErrorCode());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final AutocompletePrediction item = mPlacesAdapter.getItem(position);
        //TODO insert into Database
        // DBHelper.insertToAddress(this, item.getPrimaryText(STYLE_NORMAL).toString(), item.getSecondaryText(STYLE_NORMAL).toString(), item.getPlaceId(), 0, 0, AddressType.SELECTED_ADDRESS.getAddressType(), System.currentTimeMillis());
        final String placeId = item.getPlaceId();
        final CharSequence primaryText = item.getPrimaryText(null);
        DocOceanLogger.i(TAG, "Autocomplete item selected: " + primaryText);
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        DocOceanLogger.i(TAG, "Called getPlaceById to get Place details for " + placeId);
    }


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                DocOceanLogger.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            DOPlace doPlace = new DOPlace();
            if (place.getAddress() != null) {
                DocOceanLogger.e(TAG, place.getName() + "," + place.getAddress().toString());
                doPlace.setAddress(place.getName() + "," + place.getAddress().toString());
            }
            if (place.getAttributions() != null)
                doPlace.setAttributions(place.getAttributions().toString());
            doPlace.setId(place.getId());
            doPlace.setLatLng(place.getLatLng());
            doPlace.setName(place.getName().toString());
            if (place.getLocale() != null)
                doPlace.setLocale(place.getLocale());
            doPlace.setRating(place.getRating());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("place", doPlace);
            setResult(Activity.RESULT_OK, returnIntent);
            places.release();
            finish();
        }
    };

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAutocompleteView.getWindowToken(), 0);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mAutocompleteView, 0);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragmentContainer:
                finish();
                break;
            case R.id.add_address:
                ActivityManager.startAddressActivity(this, null);
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        cancelProgressDialog();
        if (items != null) {
            if (items instanceof GetAddressResponse) {
                GetAddressResponse addressResponse = (GetAddressResponse) items;
                if (addressResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mAddressList.clear();
                    mAddressList.addAll(addressResponse.getAddresses());
                    mAddressListRVAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(addressResponse.getStatus().getMessage());
                }
            }

        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    @Override
    public void selectedAddress(Object address) {
        if (address != null) {
            Address selectedAddress = (Address) address;
            DOPlace doPlace = new DOPlace();
            if (selectedAddress.getAddressLine() != null) {
                doPlace.setAddress(selectedAddress.getAddressLine());
            }

            doPlace.setId(String.valueOf(selectedAddress.getId()));
            LatLng latLng = new LatLng(selectedAddress.getLatitude(), selectedAddress.getLongitude());
            doPlace.setLatLng(latLng);
            if (selectedAddress.getLocality() != null)
                doPlace.setName(selectedAddress.getLocality());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("place", doPlace);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }
}
