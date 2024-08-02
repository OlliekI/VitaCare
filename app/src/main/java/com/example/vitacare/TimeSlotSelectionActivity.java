package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.time.LocalTime;

public class TimeSlotSelectionActivity extends AppCompatActivity {

    EditText patientName;
    RadioGroup availableTimeSlots;
    RadioButton firstTimeSlot, secondTimeSlot, thirdTimeSlot, fourthTimeSlot;
    Button reserve;
    LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_slot_selection);
        initLayout();
        initListeners();
    }

    private void initListeners() {
        availableTimeSlots.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (firstTimeSlot.isChecked()) {
                    time = LocalTime.of(11, 0);
                } else if (secondTimeSlot.isChecked()) {
                    time = LocalTime.of(11, 30);
                } else if (thirdTimeSlot.isChecked()) {
                    time = LocalTime.of(12, 0);
                } else if (fourthTimeSlot.isChecked()) {
                    time = LocalTime.of(12, 30);
                }
            }
        });
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment.getInstance().setTime(time);
                if (patientName.getText().toString().isEmpty()) {
                    Toast.makeText(TimeSlotSelectionActivity.this, "Please enter patient name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Appointment.getInstance().setPatientName(patientName.getText().toString());
                FragmentManager fm = getSupportFragmentManager();
                AppointmentRegisteredDialog dialog = new AppointmentRegisteredDialog();
                dialog.show(fm, "AppointmentRegisteredDialog");
            }
        });
    }

    private void initLayout() {
        patientName = findViewById(R.id.editTextPatientName);
        availableTimeSlots = findViewById(R.id.radioGroupTImeSlots);
        firstTimeSlot = findViewById(R.id.radioButton1100);
        secondTimeSlot = findViewById(R.id.radioButton1130);
        thirdTimeSlot = findViewById(R.id.radioButton1200);
        fourthTimeSlot = findViewById(R.id.radioButton1230);
        reserve = findViewById(R.id.buttonFinish);
    }
}