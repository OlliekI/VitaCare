package com.example.vitacare;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClinicAdapter extends RecyclerView.Adapter {
    private ArrayList<Clinic> clinicsData;
    Context context;
    protected static final int PERMISSION_REQUEST_PHONE = 102;
    public ClinicAdapter(ArrayList<Clinic> clinicsData, Context context) {
        this.clinicsData = clinicsData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.clinic_list, parent, false);
        return new ClinicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ClinicViewHolder clinicVH = (ClinicViewHolder) holder;
        int id = holder.getAdapterPosition();
        clinicVH.getClinicName().setText(clinicsData.get(id).getClinicName());
        clinicVH.getClinicAddress().setText(clinicsData.get(id).getLocation());
        clinicVH.getClinicPhone().setText(clinicsData.get(id).getPhoneNumber());
        clinicVH.getClinicPhone().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhonePermission(clinicsData.get(id).getPhoneNumber());
            }
        });
        clinicVH.clinicAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps(clinicsData.get(id).getLocation());
            }
        });
    }

    @Override
    public int getItemCount() {
        return clinicsData.size();
    }

    public class ClinicViewHolder extends RecyclerView.ViewHolder {
        TextView clinicName, clinicAddress, clinicPhone;

        public ClinicViewHolder(@NonNull View itemView) {
            super(itemView);
            clinicName = itemView.findViewById(R.id.textViewClinicName);
            clinicAddress = itemView.findViewById(R.id.textViewLocation);
            clinicPhone = itemView.findViewById(R.id.textViewPhoneNumber);
            itemView.setTag(this);
        }

        public TextView getClinicName() {
            return clinicName;
        }

        public TextView getClinicAddress() {
            return clinicAddress;
        }

        public TextView getClinicPhone() {
            return clinicPhone;
        }
    }
    private void checkPhonePermission(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                                "Vitacare requires this permission to place a call from the app.",
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        PERMISSION_REQUEST_PHONE);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_PHONE);
            }
        } else {
            callClinic(phoneNumber);
        }
    }

    private void callClinic(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent);
        }
    }
    private void openGoogleMaps(String cityName) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(cityName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();

                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps is not installed.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Location not found.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
