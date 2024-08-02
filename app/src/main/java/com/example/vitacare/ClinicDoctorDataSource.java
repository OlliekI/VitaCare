package com.example.vitacare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
public class ClinicDoctorDataSource {
    SQLiteDatabase database;
    VitaCareDBHelper dbHelper;

    public ClinicDoctorDataSource(Context context) {
        dbHelper = new VitaCareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public boolean insertClinicDoctor(ClinicDoctor cd) {
//        boolean didSucceed = false;
//        try {
//            ContentValues initialValues = new ContentValues();
//            initialValues.put("ClinicID", cd.getClinicID());
//            initialValues.put("DoctorID", cd.getDoctorID());
//            didSucceed = database.insert("ClinicDoctors", null, initialValues) > 0;
//        } catch (Exception e) {
//            Log.d("My Database", "Something went wrong!");
//        }
//        return didSucceed;
//    }
public ArrayList<Doctors> getDoctorsByClinicId(int clinicId, String specialty) {
    String query = "SELECT " + dbHelper.getDoctorsFirstName() + ", " + dbHelper.getDoctorsLastName() + ", " + dbHelper.getDoctorsPhoneNumber() +
            " FROM " + dbHelper.getClinicDoctorsTableName() + " as c" +
            " JOIN " + dbHelper.getDoctorsTableName() + " as d ON " + "c." + dbHelper.getClinicDoctorsColumnDoctorId() + " = d." + dbHelper.getDoctorId() +
            " WHERE " + dbHelper.getClinicDoctorsColumnClinicId() + " = " + clinicId + " AND " + dbHelper.getDoctorsSpecialty() + " = " + specialty ;
    ArrayList<Doctors> doctors = new ArrayList<>();
    try {
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Doctors d = new Doctors();
            d.setFirstName(cursor.getString(1));
            d.setLastName(cursor.getString(3));
            d.setPhoneNumber(cursor.getString(4));
            doctors.add(d);
            cursor.moveToNext();
            doctors.add(d);
        }
        cursor.close();
    }catch (Exception e){

    }
    return doctors;
}

}
