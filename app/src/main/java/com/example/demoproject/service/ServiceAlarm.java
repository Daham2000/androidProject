package com.example.demoproject.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.demoproject.MainActivity;
import com.example.demoproject.R;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

import java.util.Timer;

public class ServiceAlarm extends Service {
    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BackgroundServiceBinder();
    }

    public class BackgroundServiceBinder extends Binder {
        public ServiceAlarm getService() {
            return ServiceAlarm.this;
        }
    }

    @Override
    public void onDestroy() {
        restartService();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        restartService();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        restartService();
        return super.onUnbind(intent);
    }

    private void restartService(){
        AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intentTwo = new Intent(this, ShortTimeEntryReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intentTwo, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),10000, pendingIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Service Started...");
        AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intentTwo = new Intent(this, AccelerometerBackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intentTwo, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),10000, pendingIntent);
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),pendingIntent);
        return START_STICKY;
    }
}
