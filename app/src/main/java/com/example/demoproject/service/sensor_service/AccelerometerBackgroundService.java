package com.example.demoproject.service.sensor_service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.demoproject.MainActivity;
import com.example.demoproject.R;
import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.utill.AppKey;

import java.util.concurrent.TimeUnit;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class AccelerometerBackgroundService extends Service implements SensorEventListener {

    private static final String TAG = "Accelerometer Service";
    private static AccelerometerBackgroundService accelerometerBackground;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor proximity;
    private Sensor magnetometer;
    private Sensor gyroscope;
    private Sensor light;
    private Sensor temperature;
    private Sensor humidity;
    private Sensor pressure;
    private Context context;
    private String CHANNEL_ID = "101";
    DataHandle dataHandle = DataHandle.getDataHandle();
    SensorModel sensorModel = new SensorModel();

    public AccelerometerBackgroundService(Context context) {
        this.context = context;
    }

    public AccelerometerBackgroundService() {
    }

    public static AccelerometerBackgroundService getAccelerometerBackground(Context context) {
        if (accelerometerBackground == null) {
            accelerometerBackground = new AccelerometerBackgroundService(context);
        }
        return accelerometerBackground;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        new SensorEventLoggerTask().execute(event);
        Log.e(TAG, "onSensorChanged service");

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
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Saving data")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, humidity,
                SensorManager.SENSOR_DELAY_NORMAL);
        Log.e(TAG, "Service End...");
        // stop the service
        stopForeground(true);
        stopSelf();
        return START_STICKY;
    }

    void stopAccelerometer() {
        sensorManager.unregisterListener(this, sensor);
    }

    void stopGyro() {
        sensorManager.unregisterListener(this, gyroscope);
    }

    void stopMagnetometer() {
        sensorManager.unregisterListener(this, magnetometer);
    }

    void stopProximity() {
        sensorManager.unregisterListener(this, proximity);
    }

    void stopPressure() {
        sensorManager.unregisterListener(this, pressure);
    }

    void stopLight() {
        sensorManager.unregisterListener(this, light);
    }

    void stopTemp() {
        sensorManager.unregisterListener(this, temperature);
    }

    void stopHumidity() {
        sensorManager.unregisterListener(this, humidity);
    }

    //Save data in Shared and get data from sensor
    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                sensorModel.setAccelerometerXValue(event.values[0]);
                sensorModel.setAccelerometerYValue(event.values[1]);
                sensorModel.setAccelerometerZValue(event.values[2]);
                stopAccelerometer();
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Accelerometer);
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                stopMagnetometer();
                sensorModel.setMagnetometerXValue(event.values[0]);
                sensorModel.setMagnetometerYValue(event.values[1]);
                sensorModel.setMagnetometerZValue(event.values[2]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.MagneticField);
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                stopGyro();
                sensorModel.setGyroscopeXValue(event.values[0]);
                sensorModel.setGyroscopeYValue(event.values[1]);
                sensorModel.setGyroscopeZValue(event.values[2]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Gyroscope);
            } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                stopProximity();
                sensorModel.setProximity(event.values[0]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Proximity);
            }else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                stopPressure();
                sensorModel.setPressure(event.values[0]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Pressure);
            }else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                stopLight();
                sensorModel.setLight(event.values[0]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Light);
            }else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                stopTemp();
                sensorModel.setTemperature(event.values[0]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Temperature);
            }else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                stopHumidity();
                sensorModel.setHumidity(event.values[0]);
                dataHandle.saveInShared(getApplicationContext(), sensorModel, AppKey.Humidity);
            }
            return null;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "Destroyed Sensor service");
        Log.d(TAG, "onDestroy Data " + sensorModel.getAccelerometerZValue());
        super.onDestroy();
    }
}
