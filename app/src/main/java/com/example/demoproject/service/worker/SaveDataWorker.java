package com.example.demoproject.service.worker;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.util.Log;

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
        Log.d(TAG, "doWork: Work is done.");
        //get the sensor accelerometer
        Intent intentBackground = new Intent(getApplicationContext(), AccelerometerBackgroundService.class);
        getApplicationContext().startService(intentBackground);
        return Result.success();
    }

}
