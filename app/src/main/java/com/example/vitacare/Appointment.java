package com.example.vitacare;

import java.time.LocalTime;

public class Appointment {
    private int appointmentID;
    private int doctorID;
    private int patientID;
    private String appointmentDate;
    private int status;
    private LocalTime time;
    private String clinicName;
    private String patientName;
    private static Appointment instance;

    public Appointment() {}

    public Appointment(int appointmentID, int doctorID, int patientID, String appointmentDate,
                       int status, LocalTime time, String clinicName, String patientName) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.time = time;
        this.clinicName = clinicName;
        this.patientName = patientName;
    }
    public static Appointment getInstance() {
        if (instance == null) {
            instance = new Appointment();
        }
        return instance;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
