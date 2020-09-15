package customer.dococean.com.patient.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.custom.DococeanTextView;

/**
 * Created by arifnadeem on 07/03/16.
 */
public class PlaceAutocompleteAdapter extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private static final String TAG = "PlaceAutocompleteAdapter";
    private static final CharacterStyle STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);
    private List<AutocompletePrediction> mResultList;
    private List<AutocompletePrediction> mSearchHistoryList;
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mBounds;
    private AutocompleteFilter mPlaceFilter;
    private LayoutInflater mLayoutInflater;

    public PlaceAutocompleteAdapter(Context context, GoogleApiClient googleApiClient,
                                    LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, R.layout.item_drop_location_search, R.id.tvDropLocation);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;
        mPlaceFilter = filter;
        mLayoutInflater = LayoutInflater.from(context);
        //mSearchHistoryList = DBHelper.getAddress(RoadRunnrApplication.getAppContext());
    }

    public void setBounds(LatLngBounds bounds) {
        mBounds = bounds;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        AutoCompleteViewHolder viewHolder;
        if (convertView == null) {
            v = mLayoutInflater.inflate(R.layout.item_drop_location_search, null);
            viewHolder = new AutoCompleteViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (AutoCompleteViewHolder) v.getTag();
        }
        AutocompletePrediction item = getItem(position);
        viewHolder.mTvLocation.setText(item.getPrimaryText(STYLE_NORMAL));
        viewHolder.mTvLocationDetails.setText(item.getSecondaryText(STYLE_NORMAL));
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AutocompletePrediction> list = null;
                if (constraint == null || (constraint != null && constraint.length() < 2)) {
//                    if (mResultList != null && mResultList.size() > 0)
//                        mResultList.clear();
                    list = mSearchHistoryList;
                    if (list != null && list.size() > 0) {
                        results.values = list;
                        results.count = list.size();
                    }
                }
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null && constraint.length() >= 2) {
                    // Query the autocomplete API for the (constraint) search string.
                    if (list != null && list.size() > 0)
                        list.clear();
                    list = getAutocomplete(constraint);
                    if (list != null) {
                        // The API successfully returned results.
                        results.values = list;
                        results.count = list.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResultList = (List<AutocompletePrediction>) results.values;
//                    RoadRunnrLogger.e(TAG, new Gson().toJson(results));
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };

    }

    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        if (mGoogleApiClient.isConnected()) {
            DocOceanLogger.i(TAG, "Starting autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                                    mBounds, mPlaceFilter);
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                DocOceanLogger.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }
            DocOceanLogger.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }
        DocOceanLogger.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }

    class AutoCompleteViewHolder {
        public DococeanTextView mTvLocation, mTvLocationDetails;

        public AutoCompleteViewHolder(View base) {
            mTvLocation = (DococeanTextView) base.findViewById(R.id.tvDropLocation);
            mTvLocationDetails = (DococeanTextView) base.findViewById(R.id.tvDropLocationDetails);
        }
    }

}

