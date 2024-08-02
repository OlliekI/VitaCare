package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AvailableMajorsListActivity extends AppCompatActivity {

    CardView dentist, surgeon, generalist, neurologist, cardiologist;
    ClinicDoctor doctor;
    String clinicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_available_majors_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clinicName = extras.getString("clinicName");
        }
        initLayout();
        initListeners();
    }

    private void initListeners() {
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableMajorsListActivity.this, DoctorsListActivity.class);
                intent.putExtra("specialty", "Dentist");
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
            }
        });
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableMajorsListActivity.this, DoctorsListActivity.class);
                intent.putExtra("specialty", "Surgeon");
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
            }
        });
        generalist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableMajorsListActivity.this, DoctorsListActivity.class);
                intent.putExtra("specialty", "Generalist");
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
            }
        });
        neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctor.setSpecialty("Neurologist");
                Intent intent = new Intent(AvailableMajorsListActivity.this, DoctorsListActivity.class);
                intent.putExtra("specialty", "Neurologist");
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
            }
            });
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctor.setSpecialty("Cardiologist");
                Intent intent = new Intent(AvailableMajorsListActivity.this, DoctorsListActivity.class);
                intent.putExtra("specialty", "Cardiologist");
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
            }
        });
    }

    private void initLayout() {
        doctor = new ClinicDoctor();
        dentist = findViewById(R.id.cardViewDentist);
        surgeon = findViewById(R.id.cardViewSurgeon);
        generalist = findViewById(R.id.cardViewGeneralist);
        neurologist = findViewById(R.id.cardViewNeurologist);
        cardiologist = findViewById(R.id.cardViewCardiologist);
    }
}