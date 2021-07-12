package com.example.demoproject.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.demoproject.sensor.AccelerometerSensorManage;

import java.util.concurrent.TimeUnit;

public class BackgroundService extends Service {

    private static final String TAG = "Background Service";
    final SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Started...");
        Log.d(TAG, "Started calling...");
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager,this);
        return START_STICKY;
    }
}
