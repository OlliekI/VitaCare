package com.example.vitacare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    EditText logInUsername, logInPass;
    Button logInButton;
    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
        initEditTextsWatchers();
        initButtons();
        initStartUp();
    }

    private void initStartUp() {
        PatientDataSource ds = new PatientDataSource(this);
        ds.open();
        int patientId = ds.getPatientId();
        ds.close();
        if (patientId != 0) {
            ds.open();
            String username = ds.getPatientFirstName(patientId);
            ds.close();
            Appointment.getInstance().setPatientID(patientId);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("username", username);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show();
        }
    }


    private void initEditTextsWatchers() {
        logInUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
            }
        });
        logInPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });
    }

    private void initButtons() {
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = false;
                if (username == null && password == null){
                    Toast.makeText(LogInActivity.this, "Username or password is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    PatientDataSource ds = new PatientDataSource(getApplicationContext());
                    ds.open();
                    success = ds.getLoginCredentials(username, password);
                    ds.close();
                }
                if (success){
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(LogInActivity.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initLayout() {
        logInButton = findViewById(R.id.signInButton);
        logInUsername = findViewById(R.id.editTextLogInUsername);
        logInPass = findViewById(R.id.editTextLogInPassword);
    }

    public void onSignUpClick(View view) {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
