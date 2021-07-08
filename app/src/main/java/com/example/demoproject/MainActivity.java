package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.example.demoproject.service.ForegroundService;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //RestAPI model object
    RestApi restApi = new RestApi();

    HashMap<String, String> params = new HashMap<>();
    //define TextView values
    TextView xValue, yValue, zValue, gyXValue, gYValue, gyZValue, maXValue, maYValue, maZValue,
            lightValue, pressureValue, tempValue, humidityValue, proximityValue;

    Button stopBtn;

    //for scheduled tasked
    Timer timer = new Timer();
    TimerTask timerTask;
    TimerTask timerTaskShared;
    //used to save data in phone memory
    SharedPreferences sharedPreferences;
    SharedPreferenceMemory preferenceMemory;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get request sent
        restApi.sendRequestGet(this);

        stopBtn = findViewById(R.id.stopBtnID);

        //initialize texView values
        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);

        gyXValue = findViewById(R.id.xGyroValue);
        gYValue =  findViewById(R.id.yGyroValue);
        gyZValue = findViewById(R.id.zGyroValue);

        maXValue = findViewById(R.id.xMagnetoMeterValue);
        maYValue = findViewById(R.id.yMagnetoMeterValue);
        maZValue =  findViewById(R.id.zMagnetoMeterValue);

        lightValue = findViewById(R.id.light);
        pressureValue =findViewById(R.id.pressure);
        tempValue = findViewById(R.id.temp);
        humidityValue = findViewById(R.id.humidity);
        proximityValue =  findViewById(R.id.proximity);

        //initialize sensor manager
        //sensor manager references
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        //declare sensor variables
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            xValue.setText("Accelerometer not support...");
            yValue.setText("Accelerometer not support...");
            zValue.setText("Accelerometer not support...");
        }

        //get the sensor Gyroscope
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            gyXValue.setText("Gyroscope not support...");
            gYValue.setText("Gyroscope not support...");
            gyZValue.setText("Gyroscope not support...");
        }

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

        sharedPreferences = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        preferenceMemory = new SharedPreferenceMemory(sharedPreferences);
        //post request sent
        startTimer();

        //Notification service here
        Intent intent = new Intent(this, ForegroundService.class);
        ContextCompat.startForegroundService(this, intent);

        stopBtn.setOnClickListener(v -> stopService(intent));
    }

    void callRestAPI() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        params.put("accelerometerX", sp.getString("accelerometerX", ""));
        params.put("accelerometerY", sp.getString("accelerometerY", ""));
        params.put("accelerometerZ", sp.getString("accelerometerZ", ""));
        params.put("gyroscopeX", sp.getString("gyroscopeX", ""));
        params.put("gyroscopeY", sp.getString("gyroscopeY", ""));
        params.put("gyroscopeZ", sp.getString("gyroscopeZ", ""));
        restApi.sendRequestPost(this, params);
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                callRestAPI();
            }
        };
        timerTaskShared = new TimerTask() {
            @Override
            public void run() {
                preferenceMemory.saveInShared(new SensorModel(
                        xValue.getText().toString(),
                        xValue.getText().toString(),
                        xValue.getText().toString(),
                        gyXValue.getText().toString(),
                        gYValue.getText().toString(),
                        gyZValue.getText().toString()
                ));
            }
        };
        timer.scheduleAtFixedRate(timerTaskShared, 0, 10000);
        timer.scheduleAtFixedRate(timerTask, 0, 20000);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //set accelerometer value to textView
            xValue.setText("X value:" + event.values[0]);
            yValue.setText("Y value:" + event.values[1]);
            zValue.setText("Z value:" + event.values[2]);
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //set GYROSCOPE value to textView
            gyXValue.setText("X value:" + event.values[0]);
            gYValue.setText("Y value:" + event.values[1]);
            gyZValue.setText("Z value:" + event.values[2]);
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