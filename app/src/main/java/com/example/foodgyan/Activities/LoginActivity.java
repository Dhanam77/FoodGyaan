package com.example.foodgyan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgyan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button LoginButton;
    private EditText loginEmail, loginPassword;
    private ProgressBar loadingBar;
    private TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeFields();
        CheckIfAlreadyLoggedIn();


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    private void Login() {
        String Email = loginEmail.getText().toString();
        String Password = loginPassword.getText().toString();

        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)) {
            Toast.makeText(LoginActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SendUserToMainActivity();
                                loadingBar.setVisibility(View.INVISIBLE);

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error:" + message, LENGTH_SHORT).show();
                                loadingBar.setVisibility(View.INVISIBLE);

                            }

                        }
                    });
        }
    }



    private void CheckIfAlreadyLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            SendUserToMainActivity();
        }

    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
        finish();
    }

    private void InitializeFields() {
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        LoginButton = (Button) findViewById(R.id.login_button);
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        newUser = (TextView)findViewById(R.id.new_user);
    }
}


