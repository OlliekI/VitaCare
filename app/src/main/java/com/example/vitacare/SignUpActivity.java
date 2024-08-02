package com.example.vitacare;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    EditText firstName, lastName, username, password, phoneNum, gender, birthdate, email, fatherName, motherFullName;
    Button confirm;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initLayout();
        initEditTextsWatchers();
        initButtons();
    }

    private void initEditTextsWatchers() {
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setFirstName(s.toString());
            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setLastName(s.toString());
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setUsername(s.toString());
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setPassword(s.toString());
            }
        });
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setPhoneNumber(s.toString());
            }
        });
        gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setGender(s.toString());
            }
        });
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog dialog = new DatePickerDialog();
                dialog.show(fm, "DatePicker");
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setEmail(s.toString());
            }
        });
        fatherName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setFatherName(s.toString());
            }
        });
        motherFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                patient.setMotherFullName(s.toString());
            }
        });
    }

    private void initButtons() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = false;
                if (patient.getFirstName() == null && patient.getLastName() == null && patient.getUsername() == null && patient.getPassword() == null && patient.getPhoneNumber() == null && patient.getGender() == null && patient.getDateOfBirth() == null && patient.getEmail() == null && patient.getFatherName() == null && patient.getMotherFullName() == null) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        PatientDataSource ds = new PatientDataSource(SignUpActivity.this);
                        ds.open();
                        success = ds.insertPatientUser(patient);
                        ds.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (success){
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("username", patient.getUsername());
                    startActivity(intent);
                }
            }
        });
    }

    private void initLayout() {
        patient = new Patient();
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        phoneNum = findViewById(R.id.editTextPhoneNumber);
        gender = findViewById(R.id.editTextGender);
        email = findViewById(R.id.editTextEmail);
        birthdate = findViewById(R.id.editTextBirthdate);
        birthdate.setFocusable(false);
        birthdate.setFocusableInTouchMode(false);
        birthdate.setClickable(true);
        fatherName = findViewById(R.id.editTextFatherName);
        motherFullName = findViewById(R.id.editTextMotherFullName);
        confirm = findViewById(R.id.signUpButton);
    }

    public void onSignInClick(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public void didFinishDatePickerDialog(Calendar selectedDate) {
        birthdate.setText(DateFormat.format("dd/MM/yyyy", selectedDate));
        patient.setDateOfBirth(selectedDate);
    }
}
