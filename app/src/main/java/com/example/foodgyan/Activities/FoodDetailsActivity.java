package com.example.foodgyan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodgyan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodDetailsActivity extends AppCompatActivity {
    private String foodName, foodImage;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressBar loadingBar;
    private DatabaseReference Ref;
    private CircleImageView foodimage;
    private Button addMeal;
    String calorieCount;

    private TextView calorieCountT, carbCount, proteinCount, vitaminsCount, foodname, fatsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);


        Initialize();

        ShowDetails();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        final String dateData = formatter.format(date);



        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.child("Calories").child(currentUser.getUid()).child(dateData).child("calorieCount").setValue(calorieCount);
            }
        });


    }

    private void ShowDetails() {

        Ref.child("FoodData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.child("Name").getValue().toString().equals(foodName) &&
                            dataSnapshot1.child("ImageLink").getValue().toString().equals(foodImage))
                    {
                        vitaminsCount.setText(dataSnapshot1.child("Nutrition").child("Fiber").getValue().toString());
                        fatsCount.setText(dataSnapshot1.child("Nutrition").child("Fat").getValue().toString());
                        proteinCount.setText(dataSnapshot1.child("Nutrition").child("Proteins").getValue().toString());
                        carbCount.setText(dataSnapshot1.child("Nutrition").child("Carbs").getValue().toString());
                        calorieCount = dataSnapshot1.child("Calories").getValue().toString();
                        calorieCountT.setText(calorieCount);
                        foodname.setText(dataSnapshot1.child("Name").getValue().toString());
                        String image = dataSnapshot1.child("ImageLink").getValue().toString();

                        Glide.with(FoodDetailsActivity.this)
                                .load(image)
                                .into(foodimage);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Initialize() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Ref = FirebaseDatabase.getInstance().getReference();

        calorieCountT = (TextView)findViewById(R.id.calories_count_display);
        carbCount = (TextView)findViewById(R.id.carbs_count);
        vitaminsCount = (TextView)findViewById(R.id.vitamins_count);
        proteinCount = (TextView)findViewById(R.id.proteins_count);
        fatsCount = (TextView)findViewById(R.id.fats_count);
        foodname = (TextView)findViewById(R.id.food_name);
        addMeal = (Button)findViewById(R.id.add_meal);
        foodName = getIntent().getStringExtra("foodName");
        foodImage = getIntent().getStringExtra("foodImage");

        foodimage = (CircleImageView)findViewById(R.id.food_image);

    }
}
