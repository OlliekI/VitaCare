package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class AppointmentRegisteredDialog extends DialogFragment {

    TextView appointmentInfo;
    Button complete, cancel;
    String patientName, appointmentDate, appointmentTime, clinicName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_registered_dialog, container, false);
        initStrings();
        initLayout(view);
        initButtons();
        return view;
    }

    private void initButtons() {
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentDataSource ds = new AppointmentDataSource(getContext());
                ds.open();
                ds.insertAppointment(Appointment.getInstance());
                ds.close();
                Intent intent = new Intent(getContext(), LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initStrings() {
        patientName = Appointment.getInstance().getPatientName();
        appointmentDate = Appointment.getInstance().getAppointmentDate();
        appointmentTime = Appointment.getInstance().getTime().toString();
        clinicName = Appointment.getInstance().getClinicName();
    }

    private void initLayout(View view) {
        appointmentInfo = view.findViewById(R.id.textViewApprovement);
        appointmentInfo.setText(getResources().getString(R.string.appointment_booked,
                patientName,
                appointmentDate,
                appointmentTime,
                clinicName));
        complete = view.findViewById(R.id.buttonComplete);
        cancel = view.findViewById(R.id.buttonCancelApp);
    }
}
