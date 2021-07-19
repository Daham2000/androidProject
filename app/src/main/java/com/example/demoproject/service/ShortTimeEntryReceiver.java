package com.example.demoproject.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.demoproject.service.api_service.ApiService;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

public class ShortTimeEntryReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // Your activity name
//            Intent intentBackground = new Intent(context, AccelerometerBackgroundService.class);
//            context.startService(intentBackground);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}