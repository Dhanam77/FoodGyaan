package com.example.foodgyan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodgyan.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SetGoalsActivity extends AppCompatActivity {

    private DatabaseReference Ref;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private FirebaseUser currentUser;
    private Button AddGoals;
    private EditText Steps;
    private EditText Calories;
    private ElegantNumberButton Water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        Water=(ElegantNumberButton)findViewById(R.id.waterbtn);

        InitializeFields();



        AddGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(Steps.getText().toString()))
                {
                    Ref.child("Goals").child(currentUserID).child("StepGoals").setValue(Steps.getText().toString());

                }

                String n = Water.getNumber();

                if(!n.isEmpty())
                {
                    Ref.child("Goals").child(currentUserID).child("WaterGoals").setValue(n);

                }

                if(!TextUtils.isEmpty(Calories.getText().toString()))
                {
                    Ref.child("Goals").child(currentUserID).child("CalorieGoals").setValue(Calories.getText().toString());

                }

            }
        });






    }







    private void InitializeFields() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        AddGoals = (Button) findViewById(R.id.save);
        Steps = (EditText) findViewById(R.id.steps_edit);
        Calories = (EditText) findViewById(R.id.calories_edit);
    }
}