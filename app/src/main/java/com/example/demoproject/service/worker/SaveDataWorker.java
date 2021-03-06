package com.example.demoproject.service.worker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.MainActivity;
import com.example.demoproject.R;
import com.example.demoproject.service.ServiceAlarm;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class SaveDataWorker extends Worker {

    private static final String TAG = "Save Data Worker";
    final SensorManager sensorManager;
    private String workTag = "SaveData";

    public SaveDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.d(TAG, "SaveDataWorker: Call");
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }
    Handler handler = new Handler(Looper.getMainLooper());

    @NonNull
    @Override
    public Result doWork() {
        //get the sensor accelerometer
        Log.e(TAG, "Work do BackgroundWork");
        Intent serviceIntent = new Intent(getApplicationContext(), AccelerometerBackgroundService.class);

        ContextCompat.startForegroundService(getApplicationContext(),serviceIntent);
        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.e(TAG, "Worker Stopped");
        settingUpPeriodicWorkSaveData();
        super.onStopped();
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void settingUpPeriodicWorkSaveData() {
        // Create Network constraint
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(workTag)
                        .build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP,periodicSendDataWork);
    }
}
