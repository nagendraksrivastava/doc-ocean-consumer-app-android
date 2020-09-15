package customer.dococean.com.patient.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.CameraMapChange;
import customer.dococean.com.patient.interfaces.FromActivityToFragment;
import customer.dococean.com.patient.interfaces.GetAddress;
import customer.dococean.com.patient.interfaces.MapReadyListener;
import customer.dococean.com.patient.interfaces.ProfessionChangeInformer;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.getexpertresponse.Expert;
import customer.dococean.com.patient.network.response.getexpertresponse.GetExpertResponse;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.activities.HomeActivity;
import customer.dococean.com.patient.views.custom.DococeanTextView;


public class HomeMapFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener, FromActivityToFragment, ProfessionChangeInformer, CameraMapChange {

    private static final String TAG = "HomeMapFragment";
    private GetAddress mGetAddress;
    private MapReadyListener mMapReadyListener;
    private HomeActivity mHomeActivity;
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;
    private View mMapView, mHelperView;
    private DococeanTextView mNoExpertAvailable;
    private ImageView mMyLocationImageView;
    private HashMap<String, Expert> mExpertMap = new HashMap<>();
    private HashMap<String, Expert> mPatientPlaceExpertMap = new HashMap<>();
    private HashMap<String, Expert> mOnlyExpertPlaceExpertMap = new HashMap<>();
    private Profession mProfession;


    public HomeMapFragment() {
        // Required empty public constructor
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
                mGetAddress.fetchAddress(mLatitude, mLongitude);
            }

        }
    };


    public static HomeMapFragment newInstance() {
        return new HomeMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_map, container, false);
    }

    private void init(View view) {
        mSupportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mMapView = mSupportMapFragment.getView();
        mHelperView = view.findViewById(R.id.helperView);
        mNoExpertAvailable = (DococeanTextView) view.findViewById(R.id.no_medical_professional_layout);
        mMyLocationImageView = (ImageView) view.findViewById(R.id.my_location_button);
        setListeners();
        mNoExpertAvailable.setVisibility(View.VISIBLE);
    }

    public DococeanTextView noExpertsAvailableView() {
        return mNoExpertAvailable;
    }

    private void setListeners() {
        mNoExpertAvailable.setOnClickListener(this);
        mMyLocationImageView.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        awesomeMapTrick();
        mSupportMapFragment.getMapAsync(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeActivity = (HomeActivity) context;

        if (context != null) {
            mMapReadyListener = (MapReadyListener) context;
            mGetAddress = (GetAddress) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGetAddress = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DocOceanLogger.d("Map Redy ", " Map is ready ");
        mGoogleMap = googleMap;
        mMapReadyListener.mapIsReady();
        setupMap();
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
                    mHomeActivity, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return true;
                }
            });

            // Gesture detector to manage scale gestures
            private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(
                    mHomeActivity, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    scaleFactor = detector.getScaleFactor();
                    return true;
                }
            });
        });
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
            if (mGoogleMap.getMyLocation() == null) {
                latLng = new LatLng(mHomeActivity.getLocation().getLatitude(), mHomeActivity.getLocation().getLongitude());
            } else {
                latLng = new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude());
            }
            positionCamera(latLng, DocOceanConstants.DEFAULT_ZOOM_VALUE, false);
            mGoogleMap.setOnCameraChangeListener(cameraChangedListener);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_medical_professional_layout:
                break;
            case R.id.my_location_button:
                repostionUserOnMap(DocOceanConstants.DEFAULT_ZOOM_VALUE);
                break;
        }
    }


    @Override
    public void sendData(Object data, Object anotherData) {
        Profession profession = (Profession) anotherData;
        if (data != null) {
            GetExpertResponse getExpertResponse = (GetExpertResponse) data;
            if (getExpertResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                if (getExpertResponse.getExperts() != null) {
                    Log.d(TAG, " Expert size list from server is = " + getExpertResponse.getExperts().size());
                    clearMaps();
                    if (getExpertResponse.getExperts().size() > 0) {
                        for (Expert expert : getExpertResponse.getExperts()) {
                            mExpertMap.put(expert.getLicenseNo(), expert);
                            if (expert.getServicePlace().equalsIgnoreCase(DocOceanConstants.PATIENT_PLACE) ||
                                    expert.getServicePlace().equalsIgnoreCase(DocOceanConstants.ALL_PLACE)) {
                                mPatientPlaceExpertMap.put(expert.getLicenseNo(), expert);
                            }
                            if (expert.getServicePlace().equalsIgnoreCase(DocOceanConstants.EXPERT_PLACE) ||
                                    expert.getServicePlace().equalsIgnoreCase(DocOceanConstants.ALL_PLACE)) {
                                mOnlyExpertPlaceExpertMap.put(expert.getLicenseNo(), expert);
                            }

                        }

                    } else {
                        //it means no doctor at this point of time so clear the map
                        clearMaps();
                    }
                    mHomeActivity.setupMapForExperts();
                }
            } else {
                clearMaps();
                clearAllMarker();
                mNoExpertAvailable.setText(getExpertResponse.getStatus().getMessage());
            }
        }
    }

    private void clearMaps() {
        mExpertMap.clear();
        mPatientPlaceExpertMap.clear();
        mOnlyExpertPlaceExpertMap.clear();
    }

    private void clearAllMarker() {
        if (mGoogleMap != null) {
            mGoogleMap.clear();
        }
    }

    @Override
    public void sendError(Object data) {

    }

    @Override
    public void giveMeExperts(Object data) {
        mProfession = (Profession) data;
        clearAllMarker();
        if (mExpertMap.size() > 0) {
            mNoExpertAvailable.setText(" BOOK A " + mProfession.getName());
            Log.d(TAG, " All expert size is  = " + mExpertMap.size());
            for (Map.Entry<String, Expert> entry : mExpertMap.entrySet()) {
                Expert expert = entry.getValue();
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(expert.getAddress().getLatitude(), expert.getAddress().getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_doctor_map_marker)));
            }
        } else {
            clearAllMarker();
            mNoExpertAvailable.setText(getString(R.string.text_no_experts_available));
        }
    }

    @Override
    public void showPatientPlaceExperts() {
        if (mPatientPlaceExpertMap.size() > 0) {
            clearAllMarker();
            mNoExpertAvailable.setText(" BOOK A " + mProfession.getName());
            Log.d(TAG, " Patient expert size is  =============== " + mPatientPlaceExpertMap.size());
            addPlacesToMap(mPatientPlaceExpertMap);
        } else {
            noExpertsAvailableToMap();
        }
    }

    private void addPlacesToMap(HashMap<String, Expert> expertHashMap) {
        for (Map.Entry<String, Expert> entry : expertHashMap.entrySet()) {
            Expert expert = entry.getValue();
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(expert.getAddress().getLatitude(), expert.getAddress().getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_doctor_map_marker)));
        }
    }

    @Override
    public void showExpertPlaceExperts() {
        if (mOnlyExpertPlaceExpertMap.size() > 0) {
            clearAllMarker();
            mNoExpertAvailable.setText(" BOOK A " + mProfession.getName());
            Log.d(TAG, " Only Expert place experts size =================== " + mOnlyExpertPlaceExpertMap.size());
            addPlacesToMap(mOnlyExpertPlaceExpertMap);
        } else {
            noExpertsAvailableToMap();
        }
    }

    private void noExpertsAvailableToMap() {
        clearAllMarker();
        mNoExpertAvailable.setText(getString(R.string.text_no_experts_available));
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


    @Override
    public void changeCameraWhenAddressChanges(LatLng latLng) {
        positionCamera(latLng, DocOceanConstants.DEFAULT_ZOOM_VALUE, false);
    }

    private void repostionUserOnMap(int zoomValue) {
        LatLng latLng = new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude());
        positionCamera(latLng, zoomValue, true);

    }
}