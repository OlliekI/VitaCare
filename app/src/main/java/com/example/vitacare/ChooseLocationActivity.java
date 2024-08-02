package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseLocationActivity extends AppCompatActivity {
    CardView nabatieh, beirut, baalbeck, saida, sour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_locations_list);
        initLayout();
        initListeners();
    }

    private void initListeners() {
        nabatieh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivity.this, ClinicListNewAppointment.class);
                intent.putExtra("location", "Nabatieh");
                startActivity(intent);
            }
        });
        beirut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivity.this, ClinicListNewAppointment.class);
                intent.putExtra("location", "Beirut");
                startActivity(intent);
            }
        });
        baalbeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivity.this, ClinicListNewAppointment.class);
                intent.putExtra("location", "Baalbeck");
                startActivity(intent);
            }
        });
        saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivity.this, ClinicListNewAppointment.class);
                intent.putExtra("location", "Saida");
                startActivity(intent);
            }
        });
        sour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivity.this, ClinicListNewAppointment.class);
                intent.putExtra("location", "Sour");
                startActivity(intent);
            }
        });
    }

    private void initLayout() {
        nabatieh = findViewById(R.id.cardViewNabatiehApp);
        beirut = findViewById(R.id.cardViewBeirutApp);
        baalbeck = findViewById(R.id.cardViewBaalbeckApp);
        saida = findViewById(R.id.cardViewSaidaApp);
        sour = findViewById(R.id.cardViewSourApp);
    }
}


