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

public class WorkPeriodManage extends Worker {

    private static final String TAG = "WorkPeriodManage";
    private final Context context;
    final SensorManager sensorManager;

    public WorkPeriodManage(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        Log.d(TAG, "WorkPeriodManage: Call");
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work is done.");
        //get the sensor accelerometer
        Intent intentBackground = new Intent(context, AccelerometerBackgroundService.class);
        context.startService(intentBackground);
        return Result.success();
    }

}
