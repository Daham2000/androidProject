package com.example.demoproject.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BackgroundService extends Service {

    private static final String TAG = "Background Service";
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Started...");
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WorkPeriodManage.class, 1, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);

        Log.d(TAG, "Started calling...");
        return START_STICKY;
    }
}
