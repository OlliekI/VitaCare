package com.example.vitacare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClinicListActivity extends AppCompatActivity {

    RecyclerView clinicRecyclerView;
    ClinicAdapter adapter;
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
                adapter = new ClinicAdapter(clinicData, this);
                clinicRecyclerView.setAdapter(adapter);

            } catch (Exception e) {
                Toast.makeText(this, "Error Retrieving Restaurant Data", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ClinicAdapter.PERMISSION_REQUEST_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted! You can now make phone calls.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied! You cannot make phone calls.", Toast.LENGTH_LONG).show();
            }
        }
    }
}