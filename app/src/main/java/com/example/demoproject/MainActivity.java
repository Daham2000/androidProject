package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoproject.api.RestApi;
import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.service.BackgroundService;

import java.util.HashMap;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //RestAPI model object
    private final RestApi restApi = new RestApi();

    private final HashMap<String, String> params = new HashMap<>();

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

    //for scheduled tasked
    private SharedPreferenceMemory preferenceMemory;

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
        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, magnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            maXValue.setText("Magnetometer not support...");
            maYValue.setText("Magnetometer not support...");
            maZValue.setText("Magnetometer not support...");
        }

        //get the sensor Light
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (light != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            lightValue.setText("Light not support...");
        }

        //get the Pressure
        Sensor pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressure != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, pressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            pressureValue.setText("Pressure not support...");
        }

        //get the Temperature sensor
        Sensor temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (temp != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, temp,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tempValue.setText("Temperature not support...");
        }

        //get the Humidity sensor
        Sensor humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (humidity != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, humidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            humidityValue.setText("Humidity not support...");
        }

        //get the proximity sensor
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximity != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            proximityValue.setText("Proximity not support...");
        }

        //used to save data in phone memory
        SharedPreferences sharedPreferences = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        preferenceMemory = new SharedPreferenceMemory(sharedPreferences);

        //Background service here
        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        stopBtn.setOnClickListener(v -> stopService(intent));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {

        } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //set MAGNETIC_FIELD value to textView
            maXValue.setText("X value:" + event.values[0]);
            maYValue.setText("Y value:" + event.values[1]);
            maZValue.setText("Z value:" + event.values[2]);
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            //set LIGHT value to textView
            lightValue.setText("lightValue:" + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            //set PRESSURE value to textView
            pressureValue.setText("pressureValue:" + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            //set TEMPERATURE value to textView
            tempValue.setText("tempValue:" + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            //set HUMIDITY value to textView
            humidityValue.setText("humidityValue:" + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            //set proximity value to textView
            proximityValue.setText("Proximity Value:" + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}