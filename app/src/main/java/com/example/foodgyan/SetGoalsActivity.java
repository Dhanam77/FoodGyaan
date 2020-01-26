package com.example.foodgyan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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
    }



    private void InitializeFields() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        AddGoals = (Button) findViewById(R.id.save);
        Steps = (EditText) findViewById(R.id.steps_edit);
        Calories = (EditText) findViewById(R.id.calories_edit);
        Water = (ElegantNumberButton) findViewById(R.id.waterbtn);
    }
}
