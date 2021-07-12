package com.example.demoproject.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.MainActivity;
import com.example.demoproject.R;
import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.sensor.AccelerometerSensorManage;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.TimeUnit;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class WorkPeriodManage extends Worker {

    private static final String TAG = "WorkPeriodManage";
    private Context context;
    DataHandle dataHandle = new DataHandle();

    public WorkPeriodManage(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        Log.d(TAG, "WorkPeriodManage: Call");
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work is done.");
        //get the sensor accelerometer

        Intent intentBackground = new Intent(context, BackgroundService.class);
        context.startService(intentBackground);

        return Result.success();
    }

}
