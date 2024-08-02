package com.example.vitacare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class PatientDataSource {
    SQLiteDatabase database;
    VitaCareDBHelper dbHelper;

    public PatientDataSource(Context context) {
        dbHelper = new VitaCareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertPatientUser(Patient p) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(dbHelper.getPatientFirstName(), p.getFirstName());
            initialValues.put(dbHelper.getPatientFatherName(), p.getFatherName());
            initialValues.put(dbHelper.getPatientLastName(), p.getLastName());
            initialValues.put(dbHelper.getPatientMotherName(), p.getMotherFullName());
            initialValues.put(dbHelper.getPatientDateOfBirth(),String.valueOf(p.getDateOfBirth().getTimeInMillis()));
            initialValues.put(dbHelper.getPatientGender(), p.getGender());
            initialValues.put(dbHelper.getPatientPhoneNumber(), p.getPhoneNumber());
            initialValues.put(dbHelper.getPatientEmail(), p.getEmail());
            initialValues.put(dbHelper.getPatientUsername(), p.getUsername());
            initialValues.put(dbHelper.getPatientPassword(), p.getPassword());
            didSucceed = database.insert(dbHelper.getPatientTable(), null, initialValues) > 0;
            if (p.getContactPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                p.getContactPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                initialValues.put(dbHelper.getPatientProfilePic(), photo);
            }
        } catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }

    public boolean updatePatient(Patient p) {
        boolean didSucceed = false;
        try {
            long rowID =p.getPatientID();
            ContentValues updatedValues = new ContentValues();
            updatedValues.put(dbHelper.getPatientUsername(), p.getUsername());
            updatedValues.put(dbHelper.getPatientPhoneNumber(), p.getPhoneNumber());
            updatedValues.put(dbHelper.getPatientEmail(), p.getEmail());
            if (p.getContactPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                p.getContactPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                updatedValues.put(dbHelper.getPatientProfilePic(), photo);
            }
            didSucceed = database.update(dbHelper.getPatientTable(), updatedValues, "_id = " + rowID, null) > 0;
        } catch (Exception ignored) {
        }
        return didSucceed;
    }

    public boolean getLoginCredentials(String username, String password) {
        boolean didSucceed = false;
        String query = "SELECT * FROM " + dbHelper.getPatientTable() + " WHERE " + dbHelper.getPatientUsername() + " = ? AND " + dbHelper.getPatientPassword() + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username, password});
        if (cursor.getCount() > 0) {
            didSucceed = true;
        }
        cursor.close();
        return didSucceed;
    }
    public int getPatientId() {
        int id = -1;
        String query = "SELECT MAX(" + dbHelper.getPatientId() +") FROM " + dbHelper.getPatientTable();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
    public String getPatientFirstName(int id){
        String patientName = "";
        String query = "SELECT " + dbHelper.getPatientFirstName() + " FROM " + dbHelper.getPatientTable() + " WHERE " + dbHelper.getPatientId() + " = " + id;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            patientName = cursor.getString(0);
        }
        return patientName;
    }
}
