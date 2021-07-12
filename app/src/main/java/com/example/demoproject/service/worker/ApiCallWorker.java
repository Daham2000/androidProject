package com.example.demoproject.service.worker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.service.api_service.ApiService;

public class ApiCallWorker extends Worker {
    private static final String TAG = "Api Call Worker";
    private final Context context;

    public ApiCallWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work is done.");
        Intent intentBackground = new Intent(context, ApiService.class);
        context.startService(intentBackground);
        return Result.success();
    }
}