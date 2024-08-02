package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorsScheduleActivity extends AppCompatActivity {

    RadioGroup availableTimesRadioGroup;
    RadioButton monday, wednesday, friday;
    TextView intro;
    Button next;
    String date;
    String doctorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctors_schedule);
        initLayout();
        initRadioGroup();
    }

    private void initRadioGroup() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        doctorName = extras.getString("doctorName");
        intro.setText(getResources().getString(R.string.dr_1_s_is_available_on_monday_wednesday_and_friday, doctorName));
        availableTimesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (monday.isChecked()){
                    date = "Monday";
                } else if (wednesday.isChecked()) {
                    date = "Wednesday";
                }
                else if (friday.isChecked()) {
                    date = "Friday";
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment.getInstance().setAppointmentDate(date);
                Intent intent = new Intent(DoctorsScheduleActivity.this, TimeSlotSelectionActivity.class);
                startActivity(intent);
            }
        });
        Appointment.getInstance().setDoctorID(doctorId(doctorName));
    }

    private int doctorId(String doctorName) {
        DoctorsDataSource ds = new DoctorsDataSource(this);
        ds.open();
        int doctorId = ds.getDoctorIdByName(doctorName);
        ds.close();
        return doctorId;
    }

    private void initLayout() {
        availableTimesRadioGroup = findViewById(R.id.radioGroupAvailableTimes);
        monday = findViewById(R.id.radioButtonMonday);
        wednesday = findViewById(R.id.radioButtonWednesday);
        friday = findViewById(R.id.radioButtonFriday);
        intro = findViewById(R.id.textViewIntro);
        next = findViewById(R.id.buttonNext);
    }
}