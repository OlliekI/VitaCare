package com.example.vitacare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalTime;
import java.util.ArrayList;

public class AppointmentDataSource {
    SQLiteDatabase database;
    VitaCareDBHelper dbHelper;

    public AppointmentDataSource(Context context) {
        dbHelper = new VitaCareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertAppointment(Appointment a) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(dbHelper.getFkDoctorId(), a.getDoctorID());
            initialValues.put(dbHelper.getAppointmentPatientName(), a.getPatientName());
            initialValues.put(dbHelper.getAppointmentDate(), a.getAppointmentDate());
            initialValues.put(dbHelper.getStatus(), a.getStatus());
            initialValues.put(dbHelper.getAppointmentTime(), a.getTime().toString());
            initialValues.put(dbHelper.getAppointmentClinicName(), a.getClinicName());
            didSucceed = database.insert(dbHelper.getAppointmentsTable(), null, initialValues) > 0;
        } catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        String query = "SELECT * FROM " + dbHelper.getAppointmentsTable();
        Cursor cursor = database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(cursor.getInt(0));
                appointment.setDoctorID(cursor.getInt(1));
                appointment.setPatientName(cursor.getString(6));
                appointment.setAppointmentDate(cursor.getString(3));
                appointment.setClinicName(cursor.getString(5));
                appointment.setTime(LocalTime.parse(cursor.getString(7)));
                appointments.add(appointment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public boolean deleteAppointment(int appointmentID) {
        boolean didSucceed = false;
        try {
            didSucceed = database.delete(dbHelper.getAppointmentsTable(), dbHelper.getAppointmentId() + "=" + appointmentID, null) > 0;
        } catch (Exception e) {
            didSucceed = false;
        }
        return didSucceed;
    }
}
