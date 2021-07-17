package com.example.demoproject.service.worker;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

import static android.content.Context.SENSOR_SERVICE;

public class SaveDataWorker extends Worker {

    private static final String TAG = "Save Data Worker";
    final SensorManager sensorManager;

    public SaveDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.d(TAG, "SaveDataWorker: Call");
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    @NonNull
    @Override
    public Result doWork() {
        //get the sensor accelerometer
        Log.e(TAG, "Work do BackgroundWork");
        AccelerometerBackgroundService accelerometerBackgroundService =
                AccelerometerBackgroundService.getAccelerometerBackground(getApplicationContext());
        accelerometerBackgroundService.StartListener();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Run your task here
                Toast.makeText(getApplicationContext(), "Worker Save data cache", Toast.LENGTH_SHORT).show();
            }
        }, 1000 );        return Result.success();
    }

}
