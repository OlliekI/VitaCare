package com.example.vitacare;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
public class ClinicDataSource {
    SQLiteDatabase database;
    VitaCareDBHelper dbHelper;

    public ClinicDataSource(Context context) {
        dbHelper = new VitaCareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public boolean insertClinic(Clinic c) {
//        boolean didSucceed = false;
//        try {
//            ContentValues initialValues = new ContentValues();
//            initialValues.put("Location", c.getLocation());
//            initialValues.put("ClinicName", c.getClinicName());
//            initialValues.put("PhoneNumber", c.getPhoneNumber());
//            didSucceed = database.insert("Clinic", null, initialValues) > 0;
//        } catch (Exception e) {
//            Log.d("My Database", "Something went wrong!");
//        }
//        return didSucceed;
//    }
    public ArrayList<Clinic> getAllClinics(String location) {
        String query = "SELECT * FROM Clinic Where " + dbHelper.getClinicColumnLocation() + " = '" + location + "';";
        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Clinic c = new Clinic();
                c.setLocation(cursor.getString(1));
                c.setClinicName(cursor.getString(2));
                c.setPhoneNumber(cursor.getString(3));
                cursor.moveToNext();
                clinics.add(c);
            }
            cursor.close();
        }catch (Exception e){

        }
        return clinics;
    }
}
