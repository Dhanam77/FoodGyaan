package com.example.foodgyan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class OptimumCalories extends AppCompatActivity {

    private DatabaseReference Ref;
    private String currentUserID;
    private double Bmr;
    private TextView BMR;
    private TextView Val;
    private int Age;
    private int Height;
    private int Weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ref = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //BMR = (TextView) findViewById(R.id.bmr);
        //BMR.setText("hi");
        setContentView(R.layout.activity_optimum_calories);
        Ref.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("age"))
                {
                    Age = Integer.valueOf(dataSnapshot.child("age").getValue().toString());
                }

                if(dataSnapshot.hasChild("height"))
                {
                    Height = Integer.valueOf(dataSnapshot.child("height").getValue().toString());
                }

                if(dataSnapshot.hasChild("weight"))
                {
                    Weight = Integer.valueOf(dataSnapshot.child("weight").getValue().toString());
                }

                Bmr = 66.5 + (13.75*Weight)+(5.003*Height)- (6.755*Age);
                //String bmr = String.valueOf(Bmr);
                String bmr = "HI";
                //BMR.setText("hi");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    private void InitializeFields() {

        Ref = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //BMR = (TextView) findViewById(R.id.);


    }

}

