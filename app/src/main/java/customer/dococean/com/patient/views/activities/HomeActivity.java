package customer.dococean.com.patient.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.interfaces.BookingConfirm;
import customer.dococean.com.patient.interfaces.GetAddress;
import customer.dococean.com.patient.interfaces.GoogleLocationServiceListener;
import customer.dococean.com.patient.interfaces.MapReadyListener;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.location.LocationEngine;
import customer.dococean.com.patient.model.DOPlace;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.requests.GetExpertRequest;
import customer.dococean.com.patient.network.response.getexpertresponse.GetExpertResponse;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.network.response.service_response.Service;
import customer.dococean.com.patient.presenters.ExpertListPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.utils.DocOceanLogger;
import customer.dococean.com.patient.views.custom.DocOceanButton;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import customer.dococean.com.patient.views.fragments.BookingFragment;
import customer.dococean.com.patient.views.fragments.HomeListFragment;
import customer.dococean.com.patient.views.fragments.HomeMapFragment;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class HomeActivity extends NavigationBarActivity
        implements
        View.OnClickListener,
        GoogleLocationServiceListener,
        UIUpdateListener,
        GetAddress,
        MapReadyListener,
        BookingConfirm {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int USER_ADDRESS = 100;
    /**
     * below variable has information about user that he has selected doctor to come to his home or he will go to
     * doctor place
     */
    private String mUserSelectedPlace;
    private DocOceanButton mBookNowButton;
    private DocOceanButton mScheduleLaterButton;
    private LinearLayout mAddressLL;
    private LinearLayout mAddressLayout;
    private LinearLayout mBookingOptionLayout;
    //private LinearLayout mPricingLayout;
    private DococeanTextView mUserAddressTv;
    private DococeanTextView mPatientPlaceChargesTV;
    private DococeanTextView mExpertPlaceChargesTV;
    private DococeanTextView mSelectedAddressTV;
    private LinearLayout mPatientPlaceChargesLL;
    private LinearLayout mExpertPlaceChargesLL;
    private boolean isMapSelected = true;
    private boolean mAllowOnlyOneFragmentChange = true;
    private String mFragmentName;
    private String mCurrentUserAddress;
    private double mCurrentLatitude, mCurrentLongitude;
    private Subscription addressFromLocationSubscription;
    private Location mLocation;
    private ExpertListPresenter mExpertListPresenter;
    private int mCategoryId = -1;
    private Profession mProfession;
    private ArrayList<Service> mOptedServiceList;
    private String mTotalCost;
    private AlertDialog mScheduleAlertDialog;
    private View mScheduleDialogView;
    private DocOceanButton mSchduleTimeButton;
    private Bundle mDataBundle;
    private String mScheduledBookingDateTime;
    private DOPlace mDoPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbarTitle(getString(R.string.action_bar_title_home_page));
        initViews();
        scheduleDateTimeInit();
        mDataBundle = new Bundle();
        mExpertListPresenter = new ExpertListPresenter();
        mExpertListPresenter.bindView(this);
        LocationEngine.getInstance().initGoogleApi(this);
        Bundle extras = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        if (extras != null) {
            mFragmentName = extras.getString(DocOceanConstants.IntentConstants.FRAGMENT_NAME);
            mProfession = extras.getParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA);
            mCategoryId = extras.getInt(DocOceanConstants.IntentConstants.CATEGORY_ID);
            mOptedServiceList = extras.getParcelableArrayList(DocOceanConstants.IntentConstants.SELECTED_SERVICES);
        }

    }

    private void manageFragment(String fragmentName) {
        if (fragmentName != null) {
            changeFragment(fragmentName);
        } else {
            changeFragment(HomeMapFragment.class.getSimpleName());
        }
    }

    public Location getLocation() {
        return mLocation;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        LocationEngine.getInstance().connectGoogleApiClient(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        LocationEngine.getInstance().stopLocationUpdates();
        LocationEngine.getInstance().disconnectGoogleApiClient();
        if (addressFromLocationSubscription != null && !addressFromLocationSubscription.isUnsubscribed()) {
            addressFromLocationSubscription.unsubscribe();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mExpertListPresenter.unbindView(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TODO commented code for list view display for experts , it will be implemented in future
//        DocOceanLogger.d(TAG, " called even before ");
//        if (id == R.id.action_flip_view) {
//            if (isMapSelected) {
//                DocOceanLogger.e(TAG, " Google Maps Is Map Activity value is = " + isMapSelected);
//                item.setIcon(getResources().getDrawable(R.drawable.ic_google_maps));
//                changeFragment(HomeListFragment.class.getSimpleName());
//                isMapSelected = false;
//            } else {
//                DocOceanLogger.e(TAG, "List Item Is Map Activity value is = " + isMapSelected);
//                item.setIcon(getResources().getDrawable(R.drawable.ic_format_list_bulleted));
//                changeFragment(HomeMapFragment.class.getSimpleName());
//                isMapSelected = true;
//            }
//        }
        if (id == R.id.action_fliter) {
            ActivityManager.startSelectCategoryActivity(this, null);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mAddressLL = (LinearLayout) findViewById(R.id.address_layout);
        mBookingOptionLayout = (LinearLayout) findViewById(R.id.option_layout);
        //mPricingLayout = (LinearLayout) findViewById(R.id.expert_place_charges_linear_layout);
        mUserAddressTv = (DococeanTextView) findViewById(R.id.user_address_text_view);
        mScheduleLaterButton = (DocOceanButton) findViewById(R.id.schedule_later_button);
        mBookNowButton = (DocOceanButton) findViewById(R.id.book_now_button);
        mPatientPlaceChargesTV = (DococeanTextView) findViewById(R.id.indoor_charges_TV);
        mExpertPlaceChargesTV = (DococeanTextView) findViewById(R.id.outdoor_charges_TV);
        mPatientPlaceChargesLL = (LinearLayout) findViewById(R.id.patient_place_charges_linear_layout);
        mExpertPlaceChargesLL = (LinearLayout) findViewById(R.id.expert_place_charges_linear_layout);
        mSelectedAddressTV = (DococeanTextView) findViewById(R.id.selected_address_tag);
        setListeners();
    }

    private void setListeners() {
        mBookNowButton.setOnClickListener(this);
        mScheduleLaterButton.setOnClickListener(this);
        mPatientPlaceChargesLL.setOnClickListener(this);
        mExpertPlaceChargesLL.setOnClickListener(this);
        mAddressLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.book_now_button:
                mScheduledBookingDateTime = null;
                validateAndStartBookingActivity(BookingFragment.class.getSimpleName(), false);
                break;

            case R.id.schedule_later_button:
                validateAndStartBookingActivity(BookingFragment.class.getSimpleName(), true);
                break;

            case R.id.patient_place_charges_linear_layout:
                Log.d(TAG, " clicked patient place experts ");
                showExpertsOnMapForPatientPlace();
                mUserSelectedPlace = DocOceanConstants.PATIENT_PLACE;
                view.setBackgroundColor(getResources().getColor(R.color.button_background));
                mExpertPlaceChargesLL.setBackgroundColor(getResources().getColor(R.color.primary_light));
                break;

            case R.id.expert_place_charges_linear_layout:
                Log.d(TAG, " clicked expert place experts ");
                showExpertsOnMapForExpertPlace();
                mUserSelectedPlace = DocOceanConstants.EXPERT_PLACE;
                view.setBackgroundColor(getResources().getColor(R.color.button_background));
                mPatientPlaceChargesLL.setBackgroundColor(getResources().getColor(R.color.primary_light));
                break;

            case R.id.address_layout:
                Intent addressSearchIntent = new Intent(this, AddressSearchActivity.class);
                startActivityForResult(addressSearchIntent, USER_ADDRESS);
                break;
        }
    }


    private void validateAndStartBookingActivity(String fragmentName, boolean isSchedule) {

        if (mUserSelectedPlace == null) {
            showShortToast("Please select service place");
            return;
        }
        if (mUserAddressTv.getText() == null || mUserAddressTv.getText().toString().isEmpty()) {
            showShortToast("Address can't be blank");
            return;
        }

        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible() && !isSchedule) {
            if (homeMapFragment.noExpertsAvailableView().getText().toString().equalsIgnoreCase(getString(R.string.text_no_experts_available))) {
                showShortToast("Experts not available, please try again later");
                return;
            }
        }

        if (mUserSelectedPlace.equalsIgnoreCase(DocOceanConstants.EXPERT_PLACE)) {
            mTotalCost = mExpertPlaceChargesTV.getText().toString();
        } else if (mUserSelectedPlace.equalsIgnoreCase(DocOceanConstants.PATIENT_PLACE)) {
            mTotalCost = mPatientPlaceChargesTV.getText().toString();
        }
        //Bundle bundle = new Bundle();
        mDataBundle.putString(DocOceanConstants.IntentConstants.FRAGMENT_NAME, fragmentName);
        mDataBundle.putString(DocOceanConstants.IntentConstants.WILL_GO_TO_PATIENT_PLACE_OR_NOT, mUserSelectedPlace);
        if (mDoPlace == null) {
            mDoPlace = new DOPlace();
            mDoPlace.setAddress(mCurrentUserAddress);
            mDoPlace.setLatLng(new LatLng(mCurrentLatitude, mCurrentLongitude));
        } else {
            mDoPlace.setAddress(mCurrentUserAddress);
            mDoPlace.setLatLng(new LatLng(mCurrentLatitude, mCurrentLongitude));
        }
        mDataBundle.putParcelable(DocOceanConstants.IntentConstants.USER_ADDRESS, mDoPlace);
        mDataBundle.putString(DocOceanConstants.IntentConstants.TOTAL_COST, mTotalCost);
        mDataBundle.putParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA, mProfession);
        mDataBundle.putParcelableArrayList(DocOceanConstants.IntentConstants.OPTED_SERVICE_LIST, mOptedServiceList);
        mDataBundle.putInt(DocOceanConstants.IntentConstants.CATEGORY_ID, mCategoryId);
        if (isSchedule) {
            mScheduleAlertDialog.show();
        } else {
            ActivityManager.startBookingActivity(this, mDataBundle);
        }

    }


    private void changeFragment(String fragmentName) {
        if (fragmentName.equalsIgnoreCase(HomeMapFragment.class.getSimpleName())) {
            mAddressLL.setVisibility(View.VISIBLE);
            mBookingOptionLayout.setVisibility(View.VISIBLE);
            mExpertPlaceChargesLL.setVisibility(View.VISIBLE);
            changeFragmentWithOutBackStack(R.id.flContent, HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
        } else if (fragmentName.equalsIgnoreCase(HomeListFragment.class.getSimpleName())) {
            mAddressLL.setVisibility(View.GONE);
            mBookingOptionLayout.setVisibility(View.GONE);
            mExpertPlaceChargesLL.setVisibility(View.GONE);
            changeFragmentWithOutBackStack(R.id.flContent, HomeListFragment.newInstance(1), HomeListFragment.class.getSimpleName());
        }
    }


    public void setupMapForExperts() {
        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        HomeListFragment homeListFragment = (HomeListFragment) getSupportFragmentManager().findFragmentByTag(HomeListFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible()) {
            // Send data to home map fragment
            homeMapFragment.giveMeExperts(mProfession);
        } else if (homeListFragment != null && homeListFragment.isVisible()) {
            // send data to Home List Fragment
            homeListFragment.giveMeExperts(mProfession);
        }
    }

    public void showExpertsOnMapForPatientPlace() {
        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        HomeListFragment homeListFragment = (HomeListFragment) getSupportFragmentManager().findFragmentByTag(HomeListFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible()) {
            // Send data to home map fragment
            homeMapFragment.showPatientPlaceExperts();
        } else if (homeListFragment != null && homeListFragment.isVisible()) {
            // send data to Home List Fragment
            homeListFragment.showPatientPlaceExperts();
        }
    }

    public void showExpertsOnMapForExpertPlace() {
        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        HomeListFragment homeListFragment = (HomeListFragment) getSupportFragmentManager().findFragmentByTag(HomeListFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible()) {
            // Send data to home map fragment
            homeMapFragment.showExpertPlaceExperts();
        } else if (homeListFragment != null && homeListFragment.isVisible()) {
            // send data to Home List Fragment
            homeListFragment.showExpertPlaceExperts();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        DocOceanLogger.d(TAG, " Location data is = " + location.getLatitude());
        mLocation = location;
        if (mAllowOnlyOneFragmentChange) {
            manageFragment(mFragmentName);
            mAllowOnlyOneFragmentChange = false;
            // Call Expert API once Fragment transation is done so calling her
        }
    }

    @Override
    public void onPlayServiceConnected() {
        LocationEngine.getInstance().startLocationUpdates();
    }

    @Override
    public void showLoading(boolean isLoading) {
        super.showLoading(isLoading);
    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof GetExpertResponse) {
                GetExpertResponse getExpertResponse = (GetExpertResponse) items;
                if (getExpertResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    if (mScheduledBookingDateTime != null) {
                        ActivityManager.startBookingActivity(HomeActivity.this, mDataBundle);
                    } else {
                        mPatientPlaceChargesTV.setText(String.valueOf(DocOceanConstants.RUPPIE_UNICODE + getExpertResponse.getAppointmentCosts().getPATIENTPLACE()));
                        mExpertPlaceChargesTV.setText(String.valueOf(DocOceanConstants.RUPPIE_UNICODE + getExpertResponse.getAppointmentCosts().getEXPERTPLACE()));
                        sendDataToFragments(items);
                    }
                } else {
                    mPatientPlaceChargesTV.setText("--");
                    mExpertPlaceChargesTV.setText("--");
                    sendDataToFragments(items);
                    //showShortToast(getExpertResponse.getStatus().getMessage());
                }
            }

        }
        super.showContent(items);
    }

    @Override
    public void showError(Throwable error) {
        //TODO send error to fragments
        cancelProgressDialog();
        super.showError(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                mDoPlace = data.getParcelableExtra("place");
                DocOceanLogger.d(TAG, " Address received is = " + mDoPlace.getAddress() + mDoPlace.getCity());
                setUserAddress(String.format("%s", mDoPlace.getAddress()));
                positionCameraOnMap(mDoPlace.getLatLng());
            }
        }
    }

    @Override
    public void fetchAddress(double latitude, double longitude) {
        getAddressesFromLocation(latitude, longitude);
    }


    private void positionCameraOnMap(LatLng latLng) {
        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible()) {
            homeMapFragment.changeCameraWhenAddressChanges(latLng);
        }
    }

    private void sendDataToFragments(Object expertResponse) {
        HomeMapFragment homeMapFragment = (HomeMapFragment) getSupportFragmentManager().findFragmentByTag(HomeMapFragment.class.getSimpleName());
        HomeListFragment homeListFragment = (HomeListFragment) getSupportFragmentManager().findFragmentByTag(HomeListFragment.class.getSimpleName());
        if (homeMapFragment != null && homeMapFragment.isVisible()) {
            // Send data to home map fragment
            homeMapFragment.sendData(expertResponse, mProfession);
        } else if (homeListFragment != null && homeListFragment.isVisible()) {
            // send data to Home List Fragment
            homeListFragment.sendData(expertResponse, mProfession);
        }
    }


    private void getAddressesFromLocation(final double lat, final double lon) {
        addressFromLocationSubscription = getAddressListFromLocation(lat, lon).debounce(1, TimeUnit.SECONDS).subscribe(new Subscriber<List<Address>>() {
            @Override
            public void onCompleted() {
                // mPickupTextView.setText(mCurrentUserAddress);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Address> addresses) {
                processAddressList(addresses, lat, lon);
            }
        });
    }

    private void processAddressList(List<Address> addresses, double lat, double lon) {
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0);
            String subLocality = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String knownName = addresses.get(0).getFeatureName();
            mCurrentUserAddress = address + "," + subLocality + "," + city + "," + state;
            mCurrentUserAddress = mCurrentUserAddress.replaceAll("(?<=[,.!?;:])(?!$)", " ");
            setUserAddress(mCurrentUserAddress);
            mCurrentLongitude = lon;
            mCurrentLatitude = lat;
            fetchNearestExperts(new LatLng(lat, lon), false);
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
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        return geocoder.getFromLocation(latitude, longitude, 1);
    }

    private void setUserAddress(String address) {
        mUserAddressTv.setText(address);
    }

    @Override
    public void mapIsReady() {
        fetchNearestExperts(null, false);
    }

    private void fetchNearestExperts(LatLng latLng, boolean isScheduleBooking) {
        ArrayList<Integer> optedServiceIds = new ArrayList<>();
        for (Service service : mOptedServiceList) {
            optedServiceIds.add(service.getId());
        }
        GetExpertRequest expertRequest = new GetExpertRequest();
        expertRequest.setCategoryId(mCategoryId);
        if (latLng == null) {
            expertRequest.setLatitude(getLocation().getLatitude());
            expertRequest.setLongitude(getLocation().getLongitude());
        } else {
            expertRequest.setLatitude(latLng.latitude);
            expertRequest.setLongitude(latLng.longitude);
        }

        expertRequest.setProfessionId(mProfession.getId());
        expertRequest.setOptedServiceIds(optedServiceIds);
        if (isScheduleBooking) {
            expertRequest.setScheduleTime(mScheduledBookingDateTime);
        }
        mExpertListPresenter.getExpertLists(this, expertRequest);
    }

    @Override
    public void onBookingConfirmResponse(Object data) {

    }

    private void scheduleDateTimeInit() {
        mScheduleDialogView = View.inflate(this, R.layout.date_time_picker, null);
        mScheduleAlertDialog = new AlertDialog.Builder(this).create();
        mSchduleTimeButton = (DocOceanButton) mScheduleDialogView.findViewById(R.id.set_booking_time_button);
        final DatePicker datePicker = (DatePicker) mScheduleDialogView.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        final TimePicker timePicker = (TimePicker) mScheduleDialogView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        mSchduleTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
                calendar.getTimeInMillis();
                mScheduledBookingDateTime = datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" +
                        datePicker.getYear() + " " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
                Log.d(TAG, " date = " + mScheduledBookingDateTime);
                mScheduleAlertDialog.dismiss();
                mDataBundle.putString(DocOceanConstants.IntentConstants.SCHEDULED_TIME, mScheduledBookingDateTime);
                showProgressDialog(getString(R.string.text_schedule_expert_check_message));
                fetchNearestExperts(null, true);
            }
        });
        mScheduleAlertDialog.setView(mScheduleDialogView);
    }
}

