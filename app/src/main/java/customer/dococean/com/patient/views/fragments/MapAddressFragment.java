package customer.dococean.com.patient.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.model.AddressModel;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.activities.AddressActivity;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by nagendrasrivastava on 26/11/16.
 */

public class MapAddressFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "SignupFragmentStepThree";
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;
    private View mMapView, mHelperView;
    private Subscription addressFromLocationSubscription;
    private DocOceanButton mPickLocationButton;
    private DococeanTextView mPreviousAddressTV;
    private DococeanTextView mPreviousAddressTextTV;
    private AddressActivity mAddressActivity;
    private customer.dococean.com.patient.network.response.getaddress.Address mSelectedAddress;
    private ImageView mMyLocationImageView;
    private AddressModel mAddressModel = new AddressModel();


    public MapAddressFragment() {
        // Required empty public constructor
    }


    public static MapAddressFragment newInstance(customer.dococean.com.patient.network.response.getaddress.Address address) {
        MapAddressFragment fragment = new MapAddressFragment();
        Bundle args = new Bundle();
        if (address != null) {
            args.putParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS, address);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedAddress = getArguments().getParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_address, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListeners();
        awesomeMapTrick();
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddressActivity)
            mAddressActivity = (AddressActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (addressFromLocationSubscription != null && !addressFromLocationSubscription.isUnsubscribed()) {
            addressFromLocationSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void init(View view) {
        mSupportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mMapView = mSupportMapFragment.getView();
        mHelperView = view.findViewById(R.id.helperView);
        mPickLocationButton = (DocOceanButton) view.findViewById(R.id.pick_location_button);
        mMyLocationImageView = (ImageView) view.findViewById(R.id.my_location_button);
        mPreviousAddressTV = (DococeanTextView) view.findViewById(R.id.previous_address_TV);
        mPreviousAddressTextTV = (DococeanTextView) view.findViewById(R.id.previous_address_text_TV);
        if (mSelectedAddress != null) {
            mPreviousAddressTV.setVisibility(View.VISIBLE);
            mPreviousAddressTextTV.setVisibility(View.VISIBLE);
            mPreviousAddressTextTV.setText(mSelectedAddress.getAddressLine());
        } else {
            mPreviousAddressTV.setVisibility(View.GONE);
            mPreviousAddressTextTV.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mPickLocationButton.setOnClickListener(this);
        mMyLocationImageView.setOnClickListener(this);
    }


    private void awesomeMapTrick() {
        mHelperView.setOnTouchListener(new View.OnTouchListener() {
            private float scaleFactor = 1f;

            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (simpleGestureDetector.onTouchEvent(motionEvent)) { // Double tap
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn()); // Fixed zoom in
                } else if (motionEvent.getPointerCount() == 1) { // Single tap
                    mMapView.dispatchTouchEvent(motionEvent); // Propagate the event to the map (Pan)
                } else if (scaleGestureDetector.onTouchEvent(motionEvent)) { // Pinch zoom
                    mGoogleMap.moveCamera(CameraUpdateFactory.zoomBy( // Zoom the map without panning it
                            (mGoogleMap.getCameraPosition().zoom * scaleFactor
                                    - mGoogleMap.getCameraPosition().zoom) / 5));
                }

                return true; // Consume all the gestures
            }

            // Gesture detector to manage double tap gestures
            private GestureDetector simpleGestureDetector = new GestureDetector(
                    getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return true;
                }
            });

            // Gesture detector to manage scale gestures
            private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(
                    getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    scaleFactor = detector.getScaleFactor();
                    return true;
                }
            });
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setupMap();
    }

    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.setBuildingsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        //mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                decorateMap();
            }
        });
    }

    private void decorateMap() {
        if (mGoogleMap != null) {
            int padding = 100; // offset from edges of the map in pixels
            LatLng latLng = null;
//            if (mGoogleMap.getMyLocation() == null) {
//                latLng = new LatLng(mHomeActivity.getLocation().getLatitude(), mHomeActivity.getLocation().getLongitude());
//            } else {
            if (mGoogleMap.getMyLocation() != null) {
                latLng = new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude());
            }
            //}
            positionCamera(latLng, DocOceanConstants.DEFAULT_ZOOM_VALUE, false);
            mGoogleMap.setOnCameraChangeListener(cameraChangedListener);
        }
    }

    private void positionCamera(LatLng location, int zoomValue, boolean isAnimationRequired) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(zoomValue)
                .bearing(0)
                .tilt(0)
                .build();
        if (isAnimationRequired)
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        else
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private GoogleMap.OnCameraChangeListener cameraChangedListener = new GoogleMap.OnCameraChangeListener() {

        private double mPreLatitude;
        private double mPreLongitude;

        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            mPreLatitude = mLatitude;
            mPreLongitude = mLongitude;
            mLatitude = cameraPosition.target.latitude;
            mLongitude = cameraPosition.target.longitude;
            if (mPreLatitude != mLatitude && mPreLongitude != mLongitude) {
                getAddressesFromLocation(mLatitude, mLongitude);
            }
        }
    };

    private void getAddressesFromLocation(double lat, double lon) {
        addressFromLocationSubscription = getAddressListFromLocation(lat, lon).debounce(1, TimeUnit.SECONDS).subscribe(new Subscriber<List<Address>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Address> addresses) {
                processAddressList(addresses);
            }
        });
    }


    private void processAddressList(List<Address> addresses) {
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0);
            String subLocality = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String knownName = addresses.get(0).getFeatureName();
            String currentUserAddress = address + "," + subLocality + "," + city + "," + state;
            currentUserAddress = currentUserAddress.replaceAll("(?<=[,.!?;:])(?!$)", " ");
            mAddressModel.setAddress(currentUserAddress);
            mAddressModel.setLatitude(mLatitude);
            mAddressModel.setLongitude(mLongitude);
            setupToolBar(currentUserAddress);
            //AddressModel addressModel = new AddressModel(currentUserAddress, mLatitude, mLongitude);
            //mAddressActivity.changeFragmentNoBackstack(R.id.flContent, AddAddressFragment.newInstance(addressModel));


        }
    }

    private Observable<List<Address>> getAddressListFromLocation(final double lat, final double lon) {
        Observable<List<Address>> addressObs = Observable.defer(new Func0<Observable<List<Address>>>() {
            @Override
            public Observable<List<Address>> call() {
                try {
                    return Observable.just(getAddresses(lat, lon));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
        return addressObs;
    }

    private List<Address> getAddresses(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        return geocoder.getFromLocation(latitude, longitude, 1);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pick_location_button:
                //getAddressesFromLocation(mLatitude, mLongitude);
                mAddressActivity.changeFragmentNoBackstack(R.id.flContent, AddAddressFragment.newInstance(mAddressModel));
                break;
            case R.id.my_location_button:
                repositionUserOnMap(DocOceanConstants.DEFAULT_ZOOM_VALUE);
                break;
        }
    }

    private void repositionUserOnMap(int zoomValue) {
        LatLng latLng = new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude());
        positionCamera(latLng, zoomValue, true);

    }

    private void setupToolBar(String address) {
        if (mAddressActivity != null) {
            mAddressActivity.setToolbarTitle(getString(R.string.text_selected_address));
            mAddressActivity.setToolbarSubTitle(address);
        }
    }
}
