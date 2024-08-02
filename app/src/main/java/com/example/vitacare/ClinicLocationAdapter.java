package com.example.vitacare;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClinicLocationAdapter extends RecyclerView.Adapter {
    private ArrayList<Clinic> clinicsData;
    private Context context;

    private View.OnClickListener onClinicClickListener;
    public ClinicLocationAdapter(ArrayList<Clinic> clinicsData, Context context) {
        this.clinicsData = clinicsData;
        this.context = context;
        onClinicClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                        v.getTag();
                int position = holder.getAdapterPosition();
                int clinicId = clinicsData.get(position).getClinicID() + 1;
                String clinicName = clinicsData.get(position).getClinicName();
                Intent intent = new Intent(context, AvailableMajorsListActivity.class);
                intent.putExtra("clinicId", clinicId);
                intent.putExtra("clinicName", clinicName);
                Appointment.getInstance().setClinicName(clinicName);
                context.startActivity(intent);
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.clinic_appointment_list, parent, false);
        return new ClinicAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ClinicAppointmentViewHolder clinicVH = (ClinicAppointmentViewHolder) holder;
        clinicVH.getClinicName().setText(clinicsData.get(position).getClinicName());
        clinicVH.getClinicPhone().setText(clinicsData.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return clinicsData.size();
    }

    public class ClinicAppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView clinicName, clinicPhone;

        public ClinicAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            clinicName = itemView.findViewById(R.id.textViewClinicNameApp);
            clinicPhone = itemView.findViewById(R.id.textViewPhoneNumberApp);
            itemView.setTag(this);
            itemView.setOnClickListener(onClinicClickListener);
        }

        public TextView getClinicName() {
            return clinicName;
        }
        public TextView getClinicPhone() {
            return clinicPhone;
        }
    }
}
