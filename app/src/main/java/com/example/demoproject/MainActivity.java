package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoproject.api.RestApi;
import com.example.demoproject.service.ForegroundService;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    //sensor manager references
    private SensorManager sensorManager;
    //RestAPI model object
    RestApi restApi = new RestApi();

    //declare sensor variables
    private Sensor accelerometer, gyroscope, magnetometer, light, pressure, temp, humidity, proximity;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get request sent
        restApi.sendRequestGet(this);

        stopBtn = (Button) findViewById(R.id.stopBtnID);

        //initialize texView values
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        gyXValue = (TextView) findViewById(R.id.xGyroValue);
        gYValue = (TextView) findViewById(R.id.yGyroValue);
        gyZValue = (TextView) findViewById(R.id.zGyroValue);

        maXValue = (TextView) findViewById(R.id.xMagnetoMeterValue);
        maYValue = (TextView) findViewById(R.id.yMagnetoMeterValue);
        maZValue = (TextView) findViewById(R.id.zMagnetoMeterValue);

        lightValue = (TextView) findViewById(R.id.light);
        pressureValue = (TextView) findViewById(R.id.pressure);
        tempValue = (TextView) findViewById(R.id.temp);
        humidityValue = (TextView) findViewById(R.id.humidity);
        proximityValue = (TextView) findViewById(R.id.proximity);

        //initialize sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
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
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (light != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            lightValue.setText("Light not support...");
        }

        //get the Pressure
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressure != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, pressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            pressureValue.setText("Pressure not support...");
        }

        //get the Temperature sensor
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (temp != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, temp,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tempValue.setText("Temperature not support...");
        }

        //get the Humidity sensor
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (humidity != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, humidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            humidityValue.setText("Humidity not support...");
        }

        //get the proximity sensor
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximity != null) {
            //register listener sensor manager
            sensorManager.registerListener(MainActivity.this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            proximityValue.setText("Proximity not support...");
        }

        sharedPreferences = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);

        //post request sent
        startTimer(this);

        //Notification service here
        Intent intent = new Intent(this, ForegroundService.class);
        ContextCompat.startForegroundService(this, intent);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopService(intent);
            }
        });
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

    void saveInShared() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accelerometerX", xValue.getText().toString());
        editor.putString("accelerometerY", xValue.getText().toString());
        editor.putString("accelerometerZ", xValue.getText().toString());
        editor.putString("gyroscopeX", gyXValue.getText().toString());
        editor.putString("gyroscopeY", gYValue.getText().toString());
        editor.putString("gyroscopeZ", gyZValue.getText().toString());
        editor.commit();
        Log.d(TAG, "Saved in Shared");
    }

    private void startTimer(Context ctx) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                callRestAPI();
            }
        };
        timerTaskShared = new TimerTask() {
            @Override
            public void run() {
                saveInShared();
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