package customer.dococean.com.patient.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.trackresponse.OptedService;
import customer.dococean.com.patient.network.response.trackresponse.TrackBookingResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.activities.BookingConfirmedActivity;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class BookingConfirmedFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, UIUpdateListener {

    private static final String TAG = "BookingConfirmedFragment";
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;
    private DococeanTextView mExpertNameTV;
    private DococeanTextView mExpertDegreeTV;
    private DococeanTextView mExpertDetailsTV;
    private DococeanTextView mExpertAddressTV;
    private DococeanTextView mBookingIdTV;
    private DococeanTextView mExpertSpecialities;
    private DococeanTextView mExpertRating;
    private TableLayout mOptedServicesTableLayout;
    private TrackBookingResponse mTrackAppointmentResponse;
    private Subscription mBookingStatusSubscription;
    private AppointmentPresenter mAppointmentPresenter;
    private BookingConfirmedActivity mBookingConfirmedActivity;


    public static BookingConfirmedFragment newInstance(Bundle bundle) {
        BookingConfirmedFragment fragment = new BookingConfirmedFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO get the bundle data
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_confirmed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mTrackAppointmentResponse = getArguments().getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
        mSupportMapFragment.getMapAsync(this);
    }

    private void init(View view) {
        mSupportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mExpertNameTV = (DococeanTextView) view.findViewById(R.id.expert_name_TV);
        mExpertDegreeTV = (DococeanTextView) view.findViewById(R.id.expert_degree_TV);
        mExpertDetailsTV = (DococeanTextView) view.findViewById(R.id.expert_details_TV);
        mExpertAddressTV = (DococeanTextView) view.findViewById(R.id.expert_address_TV);
        mExpertRating = (DococeanTextView) view.findViewById(R.id.expert_rating);
        mBookingIdTV = (DococeanTextView) view.findViewById(R.id.booking_id_TV);
        mExpertSpecialities = (DococeanTextView) view.findViewById(R.id.expert_specialist_TV);
        mOptedServicesTableLayout = (TableLayout) view.findViewById(R.id.confirmed_opted_services_table_layout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookingConfirmedActivity) {
            mBookingConfirmedActivity = (BookingConfirmedActivity) context;
        }
    }

    @Override
    public void onDetach() {
        unsubscribeBookingStatusPolling();
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        unsubscribeBookingStatusPolling();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        DocOceanLogger.d(TAG, " On Map ready ");
        mExpertNameTV.setText(mTrackAppointmentResponse.getAppointment().getExpert().getName());
        mExpertDegreeTV.setText(mTrackAppointmentResponse.getAppointment().getExpert().getDegreeList());
        if (mTrackAppointmentResponse.getAppointment().getExpert().getAddress() != null) {
            mExpertAddressTV.setVisibility(View.VISIBLE);
            String completeAddress = mTrackAppointmentResponse.getAppointment().getExpert().getAddress().getAddressLine() +
                    mTrackAppointmentResponse.getAppointment().getExpert().getAddress().getCity();
            mExpertAddressTV.setText(completeAddress);
        } else {
            mExpertAddressTV.setVisibility(View.GONE);
        }
        mBookingIdTV.setText(String.format("%s %s", "Booking Id: ", mTrackAppointmentResponse.getAppointment().getNumber()));
        mExpertSpecialities.setText(mTrackAppointmentResponse.getAppointment().getExpert().getSpecializationList());
        if (mTrackAppointmentResponse.getAppointment().getOptedServices() != null) {
            setupOptedServicesViews();
        }
        setupMap();
    }


    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.setBuildingsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.setOnMapLoadedCallback(this);
    }

    private void decorateMap(LatLng latLng) {
        if (mGoogleMap != null) {
            positionCamera(latLng, 18);
        }
    }


    private void addExpertmarker(double latitude, double longitude) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_doctor_map_marker)));
    }

    @Override
    public void onMapLoaded() {
        DocOceanLogger.d(TAG, "Expert name is = " + mTrackAppointmentResponse.getAppointment().getExpert().getName());
        LatLng latLng = new LatLng(mTrackAppointmentResponse.getAppointment().getExpert().getAddress().getLatitude(), mTrackAppointmentResponse.getAppointment().getExpert().getAddress().getLongitude());
        decorateMap(latLng);
        addExpertmarker(latLng.latitude, latLng.longitude);
        pollBookingStatus(mTrackAppointmentResponse.getAppointment().getNumber());
    }

    private void positionCamera(LatLng location, int zoomValue) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(zoomValue)
                .bearing(0)
                .tilt(0)
                .build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setupOptedServicesViews() {
        List<OptedService> optedServiceList = mTrackAppointmentResponse.getAppointment().getOptedServices();
        if (optedServiceList.size() > 0) {
            mOptedServicesTableLayout.setVisibility(View.VISIBLE);
            TableRow tableHeaderRow = new TableRow(getContext());
            TextView serviceHeaderTV = new DococeanTextView(getContext());
            serviceHeaderTV.setText("Services");
            serviceHeaderTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            serviceHeaderTV.setTextColor(getResources().getColor(R.color.black));
            serviceHeaderTV.setGravity(Gravity.CENTER);
            tableHeaderRow.addView(serviceHeaderTV);
            TextView servicecostHeaderTV = new DococeanTextView(getContext());
            servicecostHeaderTV.setText("Charges");
            servicecostHeaderTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            servicecostHeaderTV.setTextColor(getResources().getColor(R.color.black));
            servicecostHeaderTV.setGravity(Gravity.CENTER);
            tableHeaderRow.addView(servicecostHeaderTV);
            mOptedServicesTableLayout.addView(tableHeaderRow);
            for (OptedService service : optedServiceList) {
                TableRow serviceRow = new TableRow(getContext());
                DococeanTextView serviceNameTV = new DococeanTextView(getContext());
                serviceNameTV.setText(service.getName());
                serviceNameTV.setGravity(Gravity.CENTER);
                serviceRow.addView(serviceNameTV);
                DococeanTextView serviceCostTV = new DococeanTextView(getContext());
                serviceCostTV.setText(String.valueOf(service.getCost()));
                serviceCostTV.setGravity(Gravity.CENTER);
                serviceRow.addView(serviceCostTV);
                mOptedServicesTableLayout.addView(serviceRow);
            }
        } else {
            mOptedServicesTableLayout.setVisibility(View.GONE);
        }
    }

    private void pollBookingStatus(final String bookingId) {
        mBookingStatusSubscription = Schedulers.io().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                mAppointmentPresenter.trackBookingStatus(getContext(), bookingId);
            }
        }, 0, DocOceanConstants.IntegerConstants.PERIOIDIC_TRACK, TimeUnit.SECONDS);
    }

    private void unsubscribeBookingStatusPolling() {
        if (mBookingStatusSubscription != null) {
            if (!mBookingStatusSubscription.isUnsubscribed())
                mBookingStatusSubscription.unsubscribe();
        }
    }


    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof TrackBookingResponse) {
                TrackBookingResponse trackBookingResponse = (TrackBookingResponse) items;
                if (trackBookingResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    updateUiForStatus(trackBookingResponse);
                } else {
                    //TODO Crashlytics LOG
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    private void updateUiForStatus(TrackBookingResponse trackBookingResponse) {
        if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.COMPLETED)) {
            unsubscribeBookingStatusPolling();
            ActivityManager.startFeedbackActivity(getContext(), null);
        } else if (trackBookingResponse.getAppointment().getStatus().equalsIgnoreCase(DocOceanConstants.BookingStatus.ENROUTE)) {
            clearMarkers();
            mBookingConfirmedActivity.updateTitle(trackBookingResponse.getAppointment().getStatus());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(trackBookingResponse.getAppointment().getExpert().getCurrentLocation().getLatitude(),
                            trackBookingResponse.getAppointment().getExpert().getCurrentLocation().getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_doctor_map_marker)));
        }
    }

    private void clearMarkers() {
        if (mGoogleMap != null)
            mGoogleMap.clear();
    }


}
