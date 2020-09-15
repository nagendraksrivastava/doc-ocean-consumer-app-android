package customer.dococean.com.patient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.myappointment.Appointment;
import customer.dococean.com.patient.network.response.myappointment.OptedService;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.custom.DococeanTextView;


public class MyBookingsRecyclerViewAdapter extends RecyclerView.Adapter<MyBookingsRecyclerViewAdapter.ViewHolder> {

    private final List<Appointment> mAppointmentList;

    public MyBookingsRecyclerViewAdapter(List<Appointment> appointmentList) {
        mAppointmentList = appointmentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_my_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appointment appointment = mAppointmentList.get(position);
        if (appointment.getExpert() != null) {
            holder.mExpertNameTV.setText(appointment.getExpert().getName());
            holder.mExpertTypeTV.setText(appointment.getExpert().getProfessionCode());
        }
        StringBuilder optedServicesBuilder = new StringBuilder();
        for (OptedService optedService : appointment.getOptedServices()) {
            optedServicesBuilder.append(optedService.getName());
        }
        holder.mPaymentModeTV.setText(appointment.getPaymentMode());
        holder.mPaymentCostTV.setText(DocOceanConstants.RUPPIE_UNICODE + appointment.getTotalCost());
        holder.mServiceOptedTV.setText(optedServicesBuilder.toString());
        holder.mBookingIdTv.setText(appointment.getNumber());
        holder.mBookingTimeTv.setText(appointment.getCreatedAt());
        holder.mPatientNameTv.setText(appointment.getPatientName());
        holder.mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        if (appointment.getPatientGender() != null)
            holder.mPatientGenderTv.setText(appointment.getPatientGender());
        if (appointment.getSymptoms() != null)
            holder.mSymptomTv.setText(appointment.getSymptoms());
        if (appointment.getInstructions() != null)
            holder.mInstructionTv.setText(appointment.getInstructions());
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private DococeanTextView mBookingIdTv;
        private DococeanTextView mBookingTimeTv;
        private DococeanTextView mPatientNameTv;
        private DococeanTextView mPatientAgeTv;
        private DococeanTextView mPatientGenderTv;
        private DococeanTextView mSymptomTv;
        private DococeanTextView mInstructionTv;
        private DococeanTextView mExpertTypeTV;
        private DococeanTextView mExpertNameTV;
        private DococeanTextView mPaymentCostTV;
        private DococeanTextView mPaymentModeTV;
        private DococeanTextView mServiceOptedTV;


        ViewHolder(View view) {
            super(view);
            mExpertTypeTV = (DococeanTextView) view.findViewById(R.id.expert_type_TV);
            mExpertNameTV = (DococeanTextView) view.findViewById(R.id.expert_name_TV);
            mPaymentCostTV = (DococeanTextView) view.findViewById(R.id.payment_cost_TV);
            mPaymentModeTV = (DococeanTextView) view.findViewById(R.id.payment_mode_type_TV);
            mServiceOptedTV = (DococeanTextView) view.findViewById(R.id.service_opted_TV);
            mBookingIdTv = (DococeanTextView) view.findViewById(R.id.booking_id_TV);
            mBookingTimeTv = (DococeanTextView) view.findViewById(R.id.booked_at_TV);
            mPatientNameTv = (DococeanTextView) view.findViewById(R.id.patient_name_TV);
            mPatientAgeTv = (DococeanTextView) view.findViewById(R.id.patient_age_TV);
            mSymptomTv = (DococeanTextView) view.findViewById(R.id.symptoms_TV);
            mInstructionTv = (DococeanTextView) view.findViewById(R.id.instruction_TV);
            mPatientGenderTv = (DococeanTextView) view.findViewById(R.id.patient_gender_TV);
        }
    }
}
