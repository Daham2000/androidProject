package com.example.demoproject.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.controller.DataHandle;

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
        DataHandle dataHandle = DataHandle.getDataHandle();
        dataHandle.callRestAPI(context);
        return Result.success();
    }


}
