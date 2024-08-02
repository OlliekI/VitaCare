package com.example.vitacare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DoctorsDataSource {

    SQLiteDatabase database;
    VitaCareDBHelper dbHelper;

    public DoctorsDataSource(Context context) {
        dbHelper = new VitaCareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public boolean insertDoctor(Doctors d) {
//        boolean didSucceed = false;
//        try {
//            ContentValues initialValues = new ContentValues();
//            initialValues.put(dbHelper.getDoctorsFirstName(), d.getFirstName());
//            initialValues.put(dbHelper.getDoctorsFatherName(), d.getFatherName());
//            initialValues.put(dbHelper.getDoctorsLastName(), d.getLastName());
//            initialValues.put(dbHelper.getDoctorsPhoneNumber(), d.getPhoneNumber());
//            initialValues.put(dbHelper.getDoctorsSpecialty(), d.getSpecialty());
//            initialValues.put(dbHelper.getFkClinicId(), d.getClinicID());
//            didSucceed = database.insert(dbHelper.getDoctorsTable(), null, initialValues) > 0;
//        } catch (Exception e) {
//            Log.d("My Database", "Something went wrong!");
//        }
//        return didSucceed;
//    }
//public ArrayList<Doctors> getAllDoctors(int clinicID, String specialty) {
//    String query = "SELECT * FROM " + dbHelper.getDoctorsTable() + " Where " + dbHelper.getFkClinicId() + " = " + clinicID + " And " + dbHelper.getDoctorsSpecialty() + " = ?" + specialty;
//    ArrayList<Doctors> doctors = new ArrayList<>();
//    try {
//        Cursor cursor = database.rawQuery(query, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            Doctors d = new Doctors();
//            d.setFirstName(cursor.getString(1));
//            d.setPhoneNumber(cursor.getString(4));
//            d.setSpecialty(cursor.getString(5));
//            d.setClinicID(cursor.getInt(6));
//            cursor.moveToNext();
//            doctors.add(d);
//        }
//        cursor.close();
//    }catch (Exception e){
//
//    }
//    return doctors;
//}

@SuppressLint("Range")
public ArrayList<Doctors> getAllDoctors(String clinicName, String specialty) {
    String query = "SELECT d.* FROM " + dbHelper.getDoctorsTable() + " as d " +
            "JOIN " + dbHelper.getClinicTableName() + " as c " +
            "ON d." + dbHelper.getFkClinicId() + " = c." + dbHelper.getClinicColumnId() + " " +
            "WHERE c." + dbHelper.getClinicColumnName() + " = ? AND d." + dbHelper.getDoctorsSpecialty() + " = ?";
    ArrayList<Doctors> doctors = new ArrayList<>();
    Cursor cursor = null;

    try {
        cursor = database.rawQuery(query, new String[]{clinicName, specialty});
        if (cursor.moveToFirst()) {
            do {
                Doctors d = new Doctors();
                d.setFirstName(cursor.getString(cursor.getColumnIndex(dbHelper.getDoctorsFirstName())));
                d.setLastName(cursor.getString(cursor.getColumnIndex(dbHelper.getDoctorsLastName())));
                d.setPhoneNumber(cursor.getString(cursor.getColumnIndex(dbHelper.getDoctorsPhoneNumber())));
                d.setSpecialty(cursor.getString(cursor.getColumnIndex(dbHelper.getDoctorsSpecialty())));
                d.setClinicID(cursor.getInt(cursor.getColumnIndex(dbHelper.getFkClinicId())));
                doctors.add(d);
            } while (cursor.moveToNext());
        }
    } catch (Exception e) {
        Log.e("Database Error", "Error while fetching doctors", e);
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
    return doctors;
}


    public int getDoctorIdByName(String doctorName) {
        int doctorId = -1;
        String query = "SELECT " + dbHelper.getDoctorId() + " FROM " + dbHelper.getDoctorsTable() +
                " WHERE " + dbHelper.getDoctorsFirstName() + " || ' ' || " +
                dbHelper.getDoctorsLastName() + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{doctorName});
        if (cursor.moveToFirst()) {
            doctorId = cursor.getInt(0);
        }
        cursor.close();
        return doctorId;
    }
    public String getDoctorNameById(int doctorId) {
        String doctorName = "";
        String query = "SELECT " + dbHelper.getDoctorsFirstName() + " || ' ' || " + dbHelper.getDoctorsLastName() + " FROM " + dbHelper.getDoctorsTable() + " WHERE " + dbHelper.getDoctorId() + " = " + doctorId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            doctorName = cursor.getString(0);
        }
        cursor.close();
        return doctorName;
    }
}
