package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class AppointmentBookingActivity extends AppCompatActivity {
    LinearLayout clinicApp, homeApp, onlineCon, sympChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_booking);
        initLayout();
        initButtons();
    }

    private void initButtons() {
        clinicApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentBookingActivity.this, ChooseLocationActivity.class);
                startActivity(intent);
            }
        });
        homeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        onlineCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sympChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initLayout() {
        clinicApp = findViewById(R.id.clinicApp);
        homeApp = findViewById(R.id.homeApp);
        onlineCon = findViewById(R.id.OnlineCon);
        sympChecker = findViewById(R.id.SympChecker);
    }
}
