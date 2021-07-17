package com.example.demoproject.service.worker;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.service.api_service.ApiService;

public class ApiCallWorker extends Worker {
    private static final String TAG = "Api Call Worker";
    public ApiCallWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work is done.");
        DataHandle dataHandle = DataHandle.getDataHandle();
        dataHandle.callRestAPI(getApplicationContext());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Run your task here
                Toast.makeText(getApplicationContext(), "Worker Send data", Toast.LENGTH_SHORT).show();
            }
        }, 1000 );


        return Result.success();
    }
}
