package com.example.vitacare;

import android.database.SQLException;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAppointmentsActivity extends AppCompatActivity {
    RecyclerView myAppointmentsRecyclerView;
    AppointmentsAdapter appointmentsAdapter;
    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_appointments_list);
        myAppointmentsRecyclerView = findViewById(R.id.recyclerViewAppointments);
        appointments = new ArrayList<>();
        initRecyclerView();
    }

    private void initRecyclerView() {
        AppointmentDataSource ds = new AppointmentDataSource(this);
        try {
            ds.open();
            appointments = ds.getAllAppointments();
            ds.close();
            myAppointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            appointmentsAdapter = new AppointmentsAdapter(this, appointments);
            myAppointmentsRecyclerView.setAdapter(appointmentsAdapter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}