package com.example.vitacare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private ImageView accountIcon, newAppointment, medicalRecord, locations, myAppointments, contactUs;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initLayout();
        initButtons();
    }

    private void initButtons() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String username = extras.getString("username");
        welcomeMessage.setText(getResources().getString(R.string.welcome, username));
        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PatientProfileActivity.class);
                startActivity(intent);
            }
        });
        newAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AppointmentBookingActivity.class);
                startActivity(intent);
            }
        });
        medicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LocationsActivity.class);
                startActivity(intent);
            }
        });
        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyAppointmentsActivity.class);
                startActivity(intent);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initLayout() {
        accountIcon = findViewById(R.id.imageViewAccountIcon);
        newAppointment = findViewById(R.id.imageViewNewAppointment);
        medicalRecord = findViewById(R.id.imageViewMedicalRecord);
        locations = findViewById(R.id.imageViewLocations);
        myAppointments = findViewById(R.id.imageViewMyAppointments);
        contactUs = findViewById(R.id.imageViewContactUs);
        welcomeMessage = findViewById(R.id.textViewWelcome);
    }
}