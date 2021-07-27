package com.example.demoproject.service.worker;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.service.api_service.ApiService;

import java.util.concurrent.TimeUnit;

public class ApiCallWorker extends Worker {
    private static final String TAG = "Api Call Worker";
    public ApiCallWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    private String workTagTwo = "SendData";

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work is done.");
        DataHandle dataHandle = DataHandle.getDataHandle();
        try {
            dataHandle.callRestAPI(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.e(TAG, "Worker Stopped");
        settingUpPeriodicWorkSendData();
        super.onStopped();
    }

    //Start worker method for call API send data to database
    private void settingUpPeriodicWorkSendData() {
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 16, TimeUnit.MINUTES)
                        .addTag(workTagTwo)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager.enqueueUniquePeriodicWork(workTagTwo, ExistingPeriodicWorkPolicy.KEEP,periodicSendDataWork);
    }
}
