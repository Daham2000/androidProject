package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.service.BackgroundService;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //define TextView values
    private TextView yValue;
    private TextView xValue;
    private TextView zValue;
    private TextView gyXValue;
    private TextView gYValue;
    private TextView gyZValue;
    private TextView maXValue;
    private TextView maYValue;
    private TextView maZValue;
    private TextView lightValue;
    private TextView pressureValue;
    private TextView tempValue;
    private TextView humidityValue;
    private TextView proximityValue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button stopBtn = findViewById(R.id.stopBtnID);

        //initialize texView values
        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);

        gyXValue = findViewById(R.id.xGyroValue);
        gYValue = findViewById(R.id.yGyroValue);
        gyZValue = findViewById(R.id.zGyroValue);

        maXValue = findViewById(R.id.xMagnetoMeterValue);
        maYValue = findViewById(R.id.yMagnetoMeterValue);
        maZValue = findViewById(R.id.zMagnetoMeterValue);

        lightValue = findViewById(R.id.light);
        pressureValue = findViewById(R.id.pressure);
        tempValue = findViewById(R.id.temp);
        humidityValue = findViewById(R.id.humidity);
        proximityValue = findViewById(R.id.proximity);

        //initialize sensor manager
        //sensor manager references
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        //declare sensor variables

        //get the sensor Gyroscope

        //get the sensor Magnetometer

        //get the sensor Light

        //get the Pressure

        //get the Temperature sensor

        //get the Humidity sensor

        //get the proximity sensor

        //used to save data in phone memory
        SharedPreferences sharedPreferences = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);

        //Background service here
        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        stopBtn.setOnClickListener(v -> stopService(intent));
    }

}