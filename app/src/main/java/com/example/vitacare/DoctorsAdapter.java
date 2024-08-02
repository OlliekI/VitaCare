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

public class DoctorsAdapter extends RecyclerView.Adapter {

    private ArrayList<Doctors> doctorsData;
    private Context context;
    private View.OnClickListener onDoctorClickListener;
    String doctorName;

    public DoctorsAdapter(ArrayList<Doctors> doctorsData, Context context) {
        this.doctorsData = doctorsData;
        this.context = context;
        onDoctorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorsScheduleActivity.class);
                intent.putExtra("doctorName", doctorName);
                context.startActivity(intent);
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.doctor_simple_list, parent, false);
        return new DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DoctorsViewHolder doctorsViewHolder = (DoctorsViewHolder) holder;
        doctorName = doctorsData.get(position).getFirstName() + " " + doctorsData.get(position).getLastName();
        doctorsViewHolder.getDoctorName().setText(doctorName);
        doctorsViewHolder.getDoctorPhoneNumber().setText(doctorsData.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return doctorsData.size();
    }

    public class DoctorsViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, doctorPhoneNumber;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.textViewDoctorName);
            doctorPhoneNumber = itemView.findViewById(R.id.textViewDoctorPhoneNumber);
            itemView.setTag(this);
            itemView.setOnClickListener(onDoctorClickListener);
        }

        public TextView getDoctorName() {
            return doctorName;
        }

        public TextView getDoctorPhoneNumber() {
            return doctorPhoneNumber;
        }
    }
}
