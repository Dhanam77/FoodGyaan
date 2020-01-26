package com.example.foodgyan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.foodgyan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private FirebaseUser currentUser;
    private TextView age, gender, height, weight, name;
    private DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Initialize();

        Display();


    }

    private void Display() {

        Ref.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gender.setText(dataSnapshot.child("gender").getValue().toString());
                age.setText(dataSnapshot.child("age").getValue().toString());
                height.setText(dataSnapshot.child("height").getValue().toString());
                weight.setText(dataSnapshot.child("weight").getValue().toString());
                name.setText(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Initialize() {

        SetupToolbar();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Ref = FirebaseDatabase.getInstance().getReference();
        height = (TextView) findViewById(R.id.heightText);
        age = (TextView) findViewById(R.id.ageText);
        gender = (TextView) findViewById(R.id.genderText);
        weight = (TextView) findViewById(R.id.weightText);
        name = (TextView) findViewById(R.id.name_display);

    }

    private void SetupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.main_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FoodGyan");


    }

}

