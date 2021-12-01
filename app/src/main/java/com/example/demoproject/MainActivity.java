package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;
import com.example.demoproject.utill.AppKey;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    // Create object of ViewPager2
    private ViewPager2 viewPager2;

    //Tab layout
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager_2);

        FragmentManager fm = getSupportFragmentManager();
        //Fragment Adapter
        FragmentAdapter adapter = new FragmentAdapter(fm, getLifecycle());

        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_sensor));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_settings));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 15, TimeUnit.MINUTES)
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