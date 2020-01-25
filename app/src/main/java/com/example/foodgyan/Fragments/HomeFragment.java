package com.example.foodgyan.Fragments;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgyan.Activities.StepCounterActivity;
import com.example.foodgyan.Activities.WaterCounterActivity;
import com.example.foodgyan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, SensorEventListener {

    private Toolbar mToolbar;
    private View mView;
    private Spinner homeSpinner;
    private SensorManager sensorManager;
    private TextView stepCount, waterCount;
    private RelativeLayout stepLayout, waterLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView =  inflater.inflate(R.layout.fragment_home, container, false);

        InitializeFields();
        SetupSpinner();

        stepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StepCounterActivity.class));
            }
        });

        waterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WaterCounterActivity.class));
            }
        });

        return mView;

    }


    private void SetupSpinner() {

        String[] spinnerItems = {"Today", "Yesterday", "This Week", "Last Week", "This month"};


        homeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        homeSpinner.setAdapter(aa);
    }


    private void InitializeFields() {

        homeSpinner = (Spinner)mView.findViewById(R.id.home_spinner);
        stepCount = (TextView) mView.findViewById(R.id.step_count);
        waterCount = (TextView) mView.findViewById(R.id.water_count);
        stepLayout = (RelativeLayout) mView.findViewById(R.id.step_layout);
        waterLayout = (RelativeLayout) mView.findViewById(R.id.water_layout);

      //  sensorManager = (SensorManager) mView.getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
