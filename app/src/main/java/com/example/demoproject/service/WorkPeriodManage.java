package com.example.demoproject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
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
        Log.e(TAG, "WorkPeriodManage: Call");
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: Work is done.");
        final SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager,context);

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
                .setContentTitle("Notification!") // title for notification
                .setContentText("Hello word") // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        return Result.success();
    }
}
