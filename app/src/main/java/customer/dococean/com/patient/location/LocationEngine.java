package customer.dococean.com.patient.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import customer.dococean.com.patient.interfaces.GoogleLocationServiceListener;

/**
 * Created by nagendrasrivastava on 11/07/16.
 */
public class LocationEngine implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final int LOCATION_UPDATE_INTERVAL = 10000;
    private final int FASTEST_UPDATE_INTERVAL = 5000;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsGoogleApiClientInitCalled;
    private GoogleLocationServiceListener mGooglePlayServiceListener;
    private Context mContext;
    private static LocationEngine ourInstance = new LocationEngine();

    public static LocationEngine getInstance() {
        return ourInstance;
    }

    private LocationEngine() {
    }

    public void initGoogleApi(Context context) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mIsGoogleApiClientInitCalled = true;
        }
    }

    public void connectGoogleApiClient(GoogleLocationServiceListener googleLocationServiceListener) {
        if (!mIsGoogleApiClientInitCalled) {
            throw new IllegalStateException(" First Call initGoogleApi ");
        } else {
            mGooglePlayServiceListener = googleLocationServiceListener;
            mGoogleApiClient.connect();
        }
    }

    public void disconnectGoogleApiClient() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGooglePlayServiceListener.onPlayServiceConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            if (mGooglePlayServiceListener != null)
                connectGoogleApiClient(mGooglePlayServiceListener);
        } catch (IllegalStateException ilex) {
            if (mContext != null) {
                initGoogleApi(mContext);
                connectGoogleApiClient(mGooglePlayServiceListener);
            }
        }
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);
    }


    public void stopLocationUpdates() {
        if (mGoogleApiClient != null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mGooglePlayServiceListener.onLocationChanged(location);
    }
}
