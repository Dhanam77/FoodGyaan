package com.example.foodgyan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgyan.R;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemSelectedListener {

    private TextView steps, stepCounter;

    private float stepsCount;
    private SensorManager sensorManager;
    private LocalDate lastCheck = null;
    private Spinner stepSpinner;

    private boolean isRunning = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int previousSteps;
    private Button  totalStepsButton;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private static final double COIN_CONVERT_FRACTION = 1.25;   //Convert steps to coins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        isRunning = true;
        InitializeField();

        SetupSpinner();

       // previousSteps = Integer.parseInt(stepCounter.getText().toString());



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);

            }
        });
    }

    //On Swipe Refresh, update coins
  /* private void refreshCoins() {

    }
    */

    private void SetupSpinner() {

        String[] spinnerItems = {"Today", "Yesterday", "This Week", "Last Week", "This month"};


        stepSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, spinnerItems);
       // aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        stepSpinner.setAdapter(aa);
    }


    //Resume step counting on re-opening the app
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            sensorManager.registerListener(StepCounterActivity.this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(StepCounterActivity.this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    //Stop the step counting on closing app
    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        sensorManager.unregisterListener(this);
    }

    private void InitializeField() {
        steps = (TextView) findViewById(R.id.steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSpinner = (Spinner)findViewById(R.id.step_spinner);
        stepCounter = (TextView)findViewById(R.id.step_counter);
        totalStepsButton = (Button) findViewById(R.id.total_steps_counter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

    }

    //Count steps
    @Override
    public void onSensorChanged(SensorEvent event) {


        if (isRunning) {
            stepCounter.setText(String.valueOf(decimalFormat.format(event.values[0])));


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
