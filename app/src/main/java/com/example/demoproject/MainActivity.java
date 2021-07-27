package com.example.demoproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;
import com.example.demoproject.utill.AppKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.spec.MGF1ParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize texView values
        TextView xValue = findViewById(R.id.xValue);
        //define TextView values
        TextView yValue = findViewById(R.id.yValue);
        TextView zValue = findViewById(R.id.zValue);

        TextView gyXValue = findViewById(R.id.xGyroValue);
        TextView gYValue = findViewById(R.id.yGyroValue);
        TextView gyZValue = findViewById(R.id.zGyroValue);

        TextView maXValue = findViewById(R.id.xMagnetoMeterValue);
        TextView maYValue = findViewById(R.id.yMagnetoMeterValue);
        TextView maZValue = findViewById(R.id.zMagnetoMeterValue);

        TextView lightValue = findViewById(R.id.light);
        TextView pressureValue = findViewById(R.id.pressure);
        TextView tempValue = findViewById(R.id.temp);
        TextView humidityValue = findViewById(R.id.humidity);
        TextView proximityValue = findViewById(R.id.proximity);

        //initialize sensor manager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager, xValue, yValue, zValue, this);
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

        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void settingUpPeriodicWorkSaveData() {
        String workTag = AppKey.SaveDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(workTag)
                        .build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    //Start worker method for call API send data to database
    private void settingUpPeriodicWorkSendData() {
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        String workTagTwo = AppKey.SendDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 30, TimeUnit.MINUTES)
                        .addTag(workTagTwo)
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTagTwo, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
        super.onDestroy();
    }

    public void compressData(View view) throws IOException {
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.cancelAllWorkByTag(AppKey.SaveDataTag);
        workManager.cancelAllWorkByTag(AppKey.SendDataTag);
        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
    }
}