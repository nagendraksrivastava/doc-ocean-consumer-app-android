package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.MyBookingsRecyclerViewAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.myappointment.Appointment;
import customer.dococean.com.patient.network.response.myappointment.MyAppointmentResponse;
import customer.dococean.com.patient.presenters.AppointmentPresenter;

public class MyBookingsActivity extends ToolbarActivity implements UIUpdateListener {

    private AppointmentPresenter mAppointmentPresenter;
    private List<Appointment> mAppointmentList;
    private MyBookingsRecyclerViewAdapter mMyBookingsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        setToolbarTitle("My Bookings");
        setHomeAsUpEnabled(true);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mAppointmentPresenter.getAllAppointments(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        mAppointmentList = new ArrayList<>();
        mMyBookingsRecyclerViewAdapter = new MyBookingsRecyclerViewAdapter(mAppointmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyBookingsRecyclerViewAdapter);
    }


    @Override
    protected void onDestroy() {
        mAppointmentPresenter.unbindView(this);
        mAppointmentPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        cancelProgressDialog();
        if (items != null) {
            MyAppointmentResponse appointmentResponse = (MyAppointmentResponse) items;
            if (appointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                mAppointmentList.addAll(appointmentResponse.getAppointments());
                mMyBookingsRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                showShortToast(appointmentResponse.getStatus().getMessage());
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }
}
