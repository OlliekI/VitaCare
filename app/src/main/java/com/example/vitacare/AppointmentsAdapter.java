package com.example.vitacare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Appointment> appointmentsData;

    public AppointmentsAdapter(Context context, ArrayList<Appointment> appointmentsData) {
        this.context = context;
        this.appointmentsData = appointmentsData;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_my_appointments, parent, false);
        return new AppointmentsAdapter.AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AppointmentViewHolder appointmentVH = (AppointmentViewHolder) holder;
        DoctorsDataSource ds = new DoctorsDataSource(context);
        ds.open();
        String doctorName = ds.getDoctorNameById(appointmentsData.get(position).getDoctorID());
        ds.close();
        appointmentVH.getAppointmentDoctor().setText(doctorName);
        appointmentVH.getAppointmentDate().setText(appointmentsData.get(position).getAppointmentDate());
        appointmentVH.getAppointmentTime().setText(appointmentsData.get(position).getTime().toString());
        appointmentVH.getAppointmentPatient().setText(appointmentsData.get(position).getPatientName());
        appointmentVH.getAppointmentClinic().setText(appointmentsData.get(position).getClinicName());
        appointmentVH.getDeleteAppointment().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(holder.getAdapterPosition());
                Toast.makeText(context, "Appointment Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsData.size();
    }
    private void deleteItem(int position) {
        AppointmentDataSource ds = new AppointmentDataSource(context);
        try {
            ds.open();
            int appointmentID = appointmentsData.get(position).getAppointmentID();
            boolean didDelete = ds.deleteAppointment(appointmentID);
            ds.close();
            if (didDelete) {
                appointmentsData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
        }
    }
    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView appointmentDate, appointmentTime, appointmentDoctor, appointmentPatient, appointmentClinic;
        Button deleteAppointment;
        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentDate = itemView.findViewById(R.id.date);
            appointmentDoctor = itemView.findViewById(R.id.doctorName);
            appointmentPatient = itemView.findViewById(R.id.patientName);
            appointmentTime = itemView.findViewById(R.id.time);
            appointmentClinic = itemView.findViewById(R.id.clinic);
            deleteAppointment = itemView.findViewById(R.id.buttonDeleteAppointment);
            itemView.setTag(this);
        }

        public TextView getAppointmentPatient() {
            return appointmentPatient;
        }

        public TextView getAppointmentDate() {
            return appointmentDate;
        }

        public TextView getAppointmentTime() {
            return appointmentTime;
        }

        public TextView getAppointmentDoctor() {
            return appointmentDoctor;
        }

        public TextView getAppointmentClinic() {
            return appointmentClinic;
        }
        public Button getDeleteAppointment() {
            return deleteAppointment;
        }
    }
}
