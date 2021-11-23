package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;
import com.example.demoproject.utill.AppKey;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.spec.MGF1ParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    // Create object of ViewPager2
    private ViewPager2 viewPager2;

    //Tab layout
    private TabLayout tabLayout;

    //Fragment Adapter
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager_2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());

        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Sensor"));
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void settingUpPeriodicWorkSaveData() {
        String workTag = AppKey.SaveDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(workTag)
                        .build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    //Start worker method for call API send data to database
    private void settingUpPeriodicWorkSendData() {
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        String workTagTwo = AppKey.SendDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 30, TimeUnit.MINUTES)
                        .addTag(workTagTwo)
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTagTwo, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
        super.onDestroy();
    }

}