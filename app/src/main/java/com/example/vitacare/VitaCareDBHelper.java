package com.example.vitacare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VitaCareDBHelper extends SQLiteOpenHelper {
    private static final String Vita_Care = "vita_care";
    private static final int DATABASE_VERSION = 1;

    // Patient table
    private static final String PATIENT_TABLE_NAME = "Patient";
    private static final String PATIENT_ID = "PatientID";
    private static final String PATIENT_FIRST_NAME = "FirstName";
    private static final String PATIENT_FATHER_NAME = "FatherName";
    private static final String PATIENT_LAST_NAME = "LastName";
    private static final String PATIENT_MOTHER_NAME = "MotherFullName";
    private static final String PATIENT_DATE_OF_BIRTH = "DateOfBirth";
    private static final String PATIENT_GENDER = "Gender";
    private static final String PATIENT_PHONE_NUMBER = "PhoneNumber";
    private static final String PATIENT_EMAIL = "Email";
    private static final String PATIENT_USERNAME = "Username";
    private static final String PATIENT_PASSWORD = "Password";
    private static final String PATIENT_PROFILE_PIC = "patientProfilePic";

    // Doctors table
    private static final String DOCTORS_TABLE_NAME = "Doctors";
    private static final String DOCTOR_ID = "DoctorID";
    private static final String DOCTORS_FIRST_NAME = "FirstName";
    private static final String DOCTORS_FATHER_NAME = "FatherName";
    private static final String DOCTORS_LAST_NAME = "LastName";
    private static final String DOCTORS_PHONE_NUMBER = "PhoneNumber";
    private static final String DOCTORS_SPECIALTY = "Specialty";
    private static final String FKCLINIC_ID = "FKClinicID";

    // Appointments table
    private static final String APPOINTMENTS_TABLE_NAME = "Appointments";
    private static final String APPOINTMENT_ID = "AppointmentID";
    private static final String FKDOCTOR_ID = "FKDoctorID";
    private static final String FKPATIENT_ID = "FKPatientID";
    private static final String APPOINTMENT_DATE = "AppointmentDate";
    private static final String STATUS = "Status";
    private static final String Appointment_ClinicName = "AppointmentClinicName";
    private static final String APPOINTMENT_PATIENT_NAME = "AppointmentPatientName";
    private static final String APPOINTMENT_TIME = "AppointmentTime";

    // Clinic table
    private static final String CLINIC_TABLE_NAME = "Clinic";
    private static final String CLINIC_COLUMN_ID = "ClinicID";
    private static final String CLINIC_COLUMN_LOCATION = "Location";
    private static final String CLINIC_COLUMN_NAME = "ClinicName";
    private static final String CLINIC_COLUMN_PHONE_NUMBER = "PhoneNumber";

    // ClinicDoctors table
    private static final String CLINIC_DOCTORS_TABLE_NAME = "ClinicDoctors";
    private static final String CLINIC_DOCTORS_COLUMN_CLINIC_ID = "ClinicID";
    private static final String CLINIC_DOCTORS_COLUMN_DOCTOR_ID = "DoctorID";

    // Create table statements
    private static final String CREATE_TABLE_PATIENT = "CREATE TABLE " + PATIENT_TABLE_NAME + " (" +
            PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PATIENT_FIRST_NAME + " TEXT NOT NULL," +
            PATIENT_FATHER_NAME + " TEXT NOT NULL," +
            PATIENT_LAST_NAME + " TEXT NOT NULL," +
            PATIENT_MOTHER_NAME + " TEXT NOT NULL," +
            PATIENT_DATE_OF_BIRTH + " TEXT NOT NULL," +
            PATIENT_GENDER + " TEXT NOT NULL," +
            PATIENT_PHONE_NUMBER + " TEXT NOT NULL," +
            PATIENT_EMAIL + " TEXT NOT NULL," +
            PATIENT_USERNAME + " TEXT NOT NULL," +
            "blob," +
            PATIENT_PASSWORD + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_DOCTORS = "CREATE TABLE " + DOCTORS_TABLE_NAME + " (" +
            DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DOCTORS_FIRST_NAME + " TEXT NOT NULL," +
            DOCTORS_FATHER_NAME + " TEXT NOT NULL," +
            DOCTORS_LAST_NAME + " TEXT NOT NULL," +
            DOCTORS_PHONE_NUMBER + " TEXT NOT NULL," +
            DOCTORS_SPECIALTY + " TEXT NOT NULL," +
            FKCLINIC_ID + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_APPOINTMENTS = "CREATE TABLE " + APPOINTMENTS_TABLE_NAME + " (" +
            APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FKDOCTOR_ID + " INTEGER NOT NULL," +
            FKPATIENT_ID + " INTEGER," +
            APPOINTMENT_DATE + " TEXT NOT NULL," +
            STATUS + " INTEGER NOT NULL," +
            Appointment_ClinicName + " TEXT NOT NULL," +
            APPOINTMENT_PATIENT_NAME + " TEXT NOT NULL," +
            APPOINTMENT_TIME + " TEXT NOT NULL," +
            "FOREIGN KEY (" + FKPATIENT_ID + ") REFERENCES " + PATIENT_TABLE_NAME + "(" + PATIENT_ID + ")," +
            "FOREIGN KEY (" + FKDOCTOR_ID + ") REFERENCES " + DOCTORS_TABLE_NAME + "(" + DOCTOR_ID + "));";


    private static final String CREATE_TABLE_CLINIC = "CREATE TABLE " + CLINIC_TABLE_NAME + " (" +
            CLINIC_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CLINIC_COLUMN_LOCATION + " TEXT NOT NULL," +
            CLINIC_COLUMN_NAME + " TEXT NOT NULL," +
            CLINIC_COLUMN_PHONE_NUMBER + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_CLINIC_DOCTORS = "CREATE TABLE " + CLINIC_DOCTORS_TABLE_NAME + " (" +
            CLINIC_DOCTORS_COLUMN_CLINIC_ID + " INTEGER NOT NULL," +
            CLINIC_DOCTORS_COLUMN_DOCTOR_ID + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + CLINIC_DOCTORS_COLUMN_CLINIC_ID + ", " + CLINIC_DOCTORS_COLUMN_DOCTOR_ID + ")," +
            "FOREIGN KEY (" + CLINIC_DOCTORS_COLUMN_CLINIC_ID + ") REFERENCES " + CLINIC_TABLE_NAME + "(" + CLINIC_COLUMN_ID + ")," +
            "FOREIGN KEY (" + CLINIC_DOCTORS_COLUMN_DOCTOR_ID + ") REFERENCES " + DOCTORS_TABLE_NAME + "(" + DOCTOR_ID + "));";

    public VitaCareDBHelper(Context context) {
        super(context, Vita_Care, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(CREATE_TABLE_PATIENT);
        _db.execSQL(CREATE_TABLE_DOCTORS);
        _db.execSQL(CREATE_TABLE_APPOINTMENTS);
        _db.execSQL(CREATE_TABLE_CLINIC);
        _db.execSQL(CREATE_TABLE_CLINIC_DOCTORS);

        seedClinic(_db);
        seedDoctors(_db);
        seedClinicDoctors(_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
        dropAllTables(_db);
        onCreate(_db);
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DOCTORS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CLINIC_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CLINIC_DOCTORS_TABLE_NAME);
    }

    private void seedClinic(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        String[] locations = {"Beirut", "Baalbeck", "Nabatieh", "Sour", "Saida"};
        String[] names = {"BeirutClinic", "BaalbeckClinic", "NabatiehClinic", "SourClinic", "SaidaClinic"};
        String[] phoneNumbers = {"70123456", "71123456", "761234546", "78123456", "79123456"};

        for (int i = 0; i < locations.length; i++) {
            values.clear();
            values.put(CLINIC_COLUMN_LOCATION, locations[i]);
            values.put(CLINIC_COLUMN_NAME, names[i]);
            values.put(CLINIC_COLUMN_PHONE_NUMBER, phoneNumbers[i]);
            db.insert(CLINIC_TABLE_NAME, null, values);
        }
    }

    public void seedDoctors(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        String[] specialties = {"Dentist", "Surgeon", "Cardiologist", "Generalist", "Neurologist"};
        String[] firstNames = {"Doctor1", "Doctor2", "Doctor3", "Doctor4", "Doctor5"};
        String[] fatherNames = {"Father1", "Father2", "Father3", "Father4", "Father5"};
        String[] lastNames = {"Last1", "Last2", "Last3", "Last4", "Last5"};
        String[] phoneNumbers = {"70123456", "71123456", "761234546", "79123456", "78123456"};
        int[] clinicIDs = {1, 2, 3, 4, 5};

        for (int i = 0; i < specialties.length; i++) {
            values.clear();
            values.put(DOCTORS_SPECIALTY, specialties[i]);
            values.put(DOCTORS_FIRST_NAME, firstNames[i]);
            values.put(DOCTORS_FATHER_NAME, fatherNames[i]);
            values.put(DOCTORS_LAST_NAME, lastNames[i]);
            values.put(DOCTORS_PHONE_NUMBER, phoneNumbers[i]);
            values.put(FKCLINIC_ID, clinicIDs[i]);
            db.insert(DOCTORS_TABLE_NAME, null, values); // Correct table name
        }
    }

    public void seedClinicDoctors(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        int[] clinicIDs = {1, 2, 3, 4, 5};
        int[] doctorIDs = {1, 2, 3, 4, 5};

        for (int i = 0; i < clinicIDs.length; i++) {
            values.clear();
            values.put(CLINIC_DOCTORS_COLUMN_CLINIC_ID, clinicIDs[i]);
            values.put(CLINIC_DOCTORS_COLUMN_DOCTOR_ID, doctorIDs[i]);
            db.insert(CLINIC_DOCTORS_TABLE_NAME, null, values);
        }
    }


    // Getters for table and column names
    public String getPatientTable() {
        return PATIENT_TABLE_NAME;
    }

    public String getPatientId() {
        return PATIENT_ID;
    }

    public String getPatientFirstName() {
        return PATIENT_FIRST_NAME;
    }

    public String getPatientFatherName() {
        return PATIENT_FATHER_NAME;
    }

    public String getPatientLastName() {
        return PATIENT_LAST_NAME;
    }

    public String getPatientMotherName() {
        return PATIENT_MOTHER_NAME;
    }

    public String getPatientDateOfBirth() {
        return PATIENT_DATE_OF_BIRTH;
    }

    public String getPatientGender() {
        return PATIENT_GENDER;
    }

    public String getPatientPhoneNumber() {
        return PATIENT_PHONE_NUMBER;
    }

    public String getPatientEmail() {
        return PATIENT_EMAIL;
    }

    public String getPatientUsername() {
        return PATIENT_USERNAME;
    }

    public String getPatientPassword() {
        return PATIENT_PASSWORD;
    }

    public String getDoctorsTable() {
        return DOCTORS_TABLE_NAME;
    }

    public String getDoctorId() {
        return DOCTOR_ID;
    }

    public String getDoctorsTableName() {
        return DOCTORS_TABLE_NAME;
    }

    public String getDoctorsFirstName() {
        return DOCTORS_FIRST_NAME;
    }

    public String getDoctorsFatherName() {
        return DOCTORS_FATHER_NAME;
    }

    public String getDoctorsLastName() {
        return DOCTORS_LAST_NAME;
    }

    public String getDoctorsPhoneNumber() {
        return DOCTORS_PHONE_NUMBER;
    }

    public String getDoctorsSpecialty() {
        return DOCTORS_SPECIALTY;
    }

    public String getFkClinicId() {
        return FKCLINIC_ID;
    }

    public String getAppointmentsTable() {
        return APPOINTMENTS_TABLE_NAME;
    }

    public String getAppointmentId() {
        return APPOINTMENT_ID;
    }

    public String getFkDoctorId() {
        return FKDOCTOR_ID;
    }

    public String getFkPatientId() {
        return FKPATIENT_ID;
    }

    public String getAppointmentClinicName() {
        return Appointment_ClinicName;
    }
    public String getAppointmentPatientName() {
        return APPOINTMENT_PATIENT_NAME;
    }
    public String getAppointmentTime() {
        return APPOINTMENT_TIME;
    }

    public String getAppointmentDate() {
        return APPOINTMENT_DATE;
    }

    public String getStatus() {
        return STATUS;
    }

    public String getClinicTableName() {
        return CLINIC_TABLE_NAME;
    }

    public String getClinicColumnId() {
        return CLINIC_COLUMN_ID;
    }

    public String getClinicColumnLocation() {
        return CLINIC_COLUMN_LOCATION;
    }

    public String getClinicColumnName() {
        return CLINIC_COLUMN_NAME;
    }

    public String getClinicColumnPhoneNumber() {
        return CLINIC_COLUMN_PHONE_NUMBER;
    }

    public String getClinicDoctorsTableName() {
        return CLINIC_DOCTORS_TABLE_NAME;
    }

    public String getClinicDoctorsColumnClinicId() {
        return CLINIC_DOCTORS_COLUMN_CLINIC_ID;
    }

    public String getClinicDoctorsColumnDoctorId() {
        return CLINIC_DOCTORS_COLUMN_DOCTOR_ID;
    }
    public String getPatientProfilePic(){
        return PATIENT_PROFILE_PIC;
    }
}
