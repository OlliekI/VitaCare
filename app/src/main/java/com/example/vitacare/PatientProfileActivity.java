package com.example.vitacare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class PatientProfileActivity extends AppCompatActivity {
    ImageButton uploadPic;
    EditText changeUsername, changeEmail, changePhoneNum;
    ImageView profilePic;
    Button saveButton;
    Patient patient;
    final int PERMISSION_REQUEST_CAMERA = 103;
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap photo = (Bitmap) data.getExtras().get("data");
                                float density = PatientProfileActivity.this.getResources().getDisplayMetrics().density;
                                int dp = 140;
                                int pixels = (int) ((dp * density) + 0.5);
                                Bitmap scaledPhoto = Bitmap.createScaledBitmap(
                                        photo, pixels, pixels, true);
                                profilePic.setImageBitmap(scaledPhoto);
                                patient.setContactPhoto(scaledPhoto);
                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_profile);
        initLayout();
        patient = new Patient();
        initTextWatchers();
        initCameraButton();
        initSaveButton();
    }

    private void initSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                PatientDataSource ds = new PatientDataSource(PatientProfileActivity.this);
                try {
                    ds.open();
                        wasSuccessful = ds.updatePatient(patient);
                } catch (Exception ignored) {
                }
            }
        });
    }

    private void initLayout() {
        uploadPic = findViewById(R.id.imageButtonUploadPic);
        changeUsername = findViewById(R.id.usernameChange);
        changeEmail = findViewById(R.id.emailChange);
        changePhoneNum = findViewById(R.id.phoneNumChange);
        profilePic = findViewById(R.id.imageViewProfilePic);
        saveButton = findViewById(R.id.buttonSaveProfile);
    }

    private void initTextWatchers(){
        changeEmail.addTextChangedListener(new TextWatcher() {
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
        changeUsername.addTextChangedListener(new TextWatcher() {
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
        changePhoneNum.addTextChangedListener(new TextWatcher() {
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
    }
    private void initCameraButton() {
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PatientProfileActivity.this,
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            PatientProfileActivity.this, android.Manifest.permission.CAMERA)) {
                        Snackbar.make(findViewById(R.id.activity_patient_profile),
                                        "The app needs permission to take photo",
                                        Snackbar.LENGTH_INDEFINITE)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("Camera permission", "");
                                        ActivityCompat.requestPermissions(PatientProfileActivity.this,
                                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                    }
                                }).show();
                    } else {
                        Log.d("Camera permission", "");
                        ActivityCompat.requestPermissions(PatientProfileActivity.this,
                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }
                }
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }

}