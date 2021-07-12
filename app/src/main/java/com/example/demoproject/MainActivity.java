package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.BackgroundService;
import com.example.demoproject.service.WorkPeriodManage;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
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
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager, xValue, yValue, zValue,this);
        //get the sensor Gyroscope
        GyroscopeSensorManage gyroscopeSensorManage = new GyroscopeSensorManage(
                sensorManager, gyXValue, gYValue, gyZValue);
        //get the sensor Magnetometer
        MagnetometerSensorManage magnetometerSensorManage = new MagnetometerSensorManage(
                sensorManager, maXValue, maYValue, maZValue);
        //get the sensor Light
        LightSensorManage lightSensorManage = new LightSensorManage(
                sensorManager, lightValue);
        //get the Pressure
        PressureSensorManage pressureSensorManage = new PressureSensorManage(
                sensorManager, pressureValue);
        //get the Temperature sensor
        TempSensorManage tempSensorManage = new TempSensorManage(
                sensorManager, tempValue);
        //get the Humidity sensor
        HumiditySensorManage humiditySensorManage = new HumiditySensorManage(
                sensorManager, humidityValue);
        //get the proximity sensor
        ProximitySensorManage proximitySensorManage = new ProximitySensorManage(
                sensorManager, proximityValue);

        //Background service here
        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);
        stopBtn.setOnClickListener(v -> stopService(intent));

        //used to save data in phone memory


    }


}