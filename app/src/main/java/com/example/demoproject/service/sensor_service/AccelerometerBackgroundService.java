package com.example.demoproject.service.sensor_service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.sensor.AccelerometerSensorManage;

import java.util.concurrent.TimeUnit;

public class AccelerometerBackgroundService extends Service implements SensorEventListener {

    private static final String TAG = "Accelerometer Service";
    private SensorManager sensorManager;
    private Sensor sensor ;
    private static float xValue;
    private static float yValue;
    private static float zValue;
    private DataHandle dataHandle;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Started...");
        dataHandle = DataHandle.getDataHandle();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        new SensorEventLoggerTask().execute(event);
        // stop the service
        stopSelf();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            xValue = event.values[0];
            yValue = event.values[1];
            zValue = event.values[2];

            DataHandle dataHandle = DataHandle.getDataHandle();
            SensorModel sensorModel = SensorModel.getSensorModel();
            sensorModel.setAccelerometerXValue(xValue);
            sensorModel.setAccelerometerYValue(yValue);
            sensorModel.setAccelerometerZValue(zValue);
            dataHandle.saveInShared(getApplicationContext(), sensorModel);
            return null;
        }
    }
}
