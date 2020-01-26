package com.example.foodgyan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodgyan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText userName, userEmail, userPassword, userHeight, userWeight, userAge;
    private Button SignUpButton;
    private ImageView SignUpLogo;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String Email, Password, Name, currentUserID, height, weight, age, gender;
    private ProgressBar loadingBar;
    private DatabaseReference Ref;
    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitializeFields();
        
        setupSpinner();
        loadingBar.setVisibility(View.INVISIBLE);


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCredentials();
            }
        });
    }

    private void setupSpinner() {
        String[] spinnerItems = {"Select", "Male", "Female"};


        genderSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(SignUpActivity.this, android.R.layout.simple_spinner_item, spinnerItems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        genderSpinner.setAdapter(aa);
    }

    private void CheckCredentials() {

        Email = userEmail.getText().toString();
        Password = userPassword.getText().toString();
        Name = userName.getText().toString();
        height = userHeight.getText().toString();
        weight = userWeight.getText().toString();
        age = userAge.getText().toString();


        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password) && TextUtils.isEmpty(Name)
                && TextUtils.isEmpty(height) && TextUtils.isEmpty(weight) && TextUtils.isEmpty(age)) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }

        else if(genderSpinner.getSelectedItem().toString().equals("Select"))
        {
            Toast.makeText(this, "Please select your gender!", Toast.LENGTH_SHORT).show();

        }
        else {
            loadingBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, String> profileMap = new HashMap<>();
                                profileMap.put("username", Name);
                                profileMap.put("password", Password);
                                profileMap.put("email", Email);
                                profileMap.put("height", height);
                                profileMap.put("weight", weight);
                                profileMap.put("gender", genderSpinner.getSelectedItem().toString());
                                profileMap.put("age", age);
                                currentUser = mAuth.getCurrentUser();
                                currentUserID = mAuth.getCurrentUser().getUid();
                                Ref.child("Users").child(currentUserID).setValue(profileMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        loadingBar.setVisibility(View.INVISIBLE);

                                    }
                                });

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(SignUpActivity.this, "Error:" + message, LENGTH_SHORT).show();
                                loadingBar.setVisibility(View.INVISIBLE);

                            }
                        }


                    });
        }

    }

    private void InitializeFields() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Ref = FirebaseDatabase.getInstance().getReference();
        userName = (EditText) findViewById(R.id.full_name);
        userHeight = (EditText) findViewById(R.id.height);
        userWeight= (EditText) findViewById(R.id.weight);
        userAge = (EditText) findViewById(R.id.age);
        userEmail = (EditText) findViewById(R.id.user_email);
        userPassword = (EditText) findViewById(R.id.user_password);
        SignUpButton = (Button) findViewById(R.id.signup_button);
        SignUpLogo = (ImageView) findViewById(R.id.signUp_logo);
        loadingBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        genderSpinner = (Spinner)findViewById(R.id.gender);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

