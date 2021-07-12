package com.example.demoproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.demoproject.controller.DataHandle;

public class ApiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DataHandle dataHandle = DataHandle.getDataHandle();
        dataHandle.callRestAPI(getApplicationContext());
        return START_STICKY;
    }
}
