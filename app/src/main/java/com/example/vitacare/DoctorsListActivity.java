package com.example.vitacare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorsListActivity extends AppCompatActivity {
    RecyclerView doctorRecyclerView;
    DoctorsAdapter adapter;
    ArrayList<Doctors> doctorsData;
    String specialty;
    String clinicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctors_list);
        clinicName = getIntent().getStringExtra("clinicName");
        specialty = getIntent().getStringExtra("specialty");
        doctorRecyclerView = findViewById(R.id.recyclerViewDoctors);
        doctorsData = new ArrayList<>();
        initRecyclerView();
    }

    private void initRecyclerView() {
        DoctorsDataSource ds = new DoctorsDataSource(this);
        try {
            ds.open();
            doctorsData = ds.getAllDoctors(clinicName, specialty);
            ds.close();
            doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DoctorsAdapter(doctorsData, this);
            doctorRecyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error Retrieving Restaurant Data", Toast.LENGTH_LONG).show();
        }
    }
}