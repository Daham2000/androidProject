package com.example.demoproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BackgroundService extends Service {

    private final Intent intent;
    private BackgroundService backgroundService;

    public BackgroundService(Intent intent) {
        this.intent = intent;
    }

    BackgroundService getInstance(){
        if(backgroundService==null){
            return new BackgroundService(intent);
        }else{
            return backgroundService;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopSelf();
        stopService(intent);
        super.onDestroy();
    }
}
