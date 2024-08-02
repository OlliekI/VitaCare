package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClinicListNewAppointment extends AppCompatActivity {

    RecyclerView clinicRecyclerView;
    ClinicLocationAdapter adapter;
    ArrayList<Clinic> clinicData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clinic_list);
        clinicRecyclerView = findViewById(R.id.recyclerViewClinic);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        } else {
            String location = extras.getString("location");
            ClinicDataSource ds = new ClinicDataSource(this);
            try {
                ds.open();
                clinicData = ds.getAllClinics(location);
                ds.close();
                clinicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new ClinicLocationAdapter(clinicData, this);
                clinicRecyclerView.setAdapter(adapter);

            } catch (Exception e) {
                Toast.makeText(this, "Error Retrieving Restaurant Data", Toast.LENGTH_LONG).show();
            }
        }
    }
}