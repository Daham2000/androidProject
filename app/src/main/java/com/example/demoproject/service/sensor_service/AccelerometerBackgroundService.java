package com.example.demoproject.service.sensor_service;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class AccelerometerBackgroundService  extends Service implements SensorEventListener {

    private static final String TAG = "Accelerometer Service";
    private static AccelerometerBackgroundService accelerometerBackground;
    private SensorManager sensorManager;
    private Sensor sensor ;
    private Sensor proximity ;
    private static float xValue;
    private static float yValue;
    private static float zValue;
    private static float proximityValue;
    private DataHandle dataHandle;
    private Context context;
    Handler handler = new Handler(Looper.getMainLooper());

    public AccelerometerBackgroundService(Context context){
        this.context = context;
    }

    public AccelerometerBackgroundService(){};

    public static AccelerometerBackgroundService getAccelerometerBackground(Context context) {
        if(accelerometerBackground==null){
            accelerometerBackground = new AccelerometerBackgroundService(context);
        }
        return accelerometerBackground;
    }

    public void StartListener(){
        Log.e(TAG, "StartListener started");
        dataHandle = DataHandle.getDataHandle();
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximity,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        new SensorEventLoggerTask().execute(event);
        sensorManager.unregisterListener(this);
        // stop the service
        stopSelf();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        return START_STICKY;
    }

    //Save data in Shared and get data from sensor
    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                xValue = event.values[0];
                yValue = event.values[1];
                zValue = event.values[2];
            }else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                proximityValue = event.values[0];
            }
            DataHandle dataHandle = DataHandle.getDataHandle();
            SensorModel sensorModel = SensorModel.getSensorModel();
            sensorModel.setAccelerometerXValue(xValue);
            sensorModel.setAccelerometerYValue(yValue);
            sensorModel.setAccelerometerZValue(zValue);
            sensorModel.setProximity(proximityValue);
            dataHandle.saveInShared(context, sensorModel,"AccelerometerKey");
            Log.e(TAG, "Save Data in Shared");
            handler.postDelayed(() -> {
                // Run your task here
                Toast.makeText(context, "Worker Save data cache", Toast.LENGTH_SHORT).show();
            }, 2000 );
            return null;
        }
    }
}
