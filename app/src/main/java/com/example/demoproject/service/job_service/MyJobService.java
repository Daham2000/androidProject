package com.example.demoproject.service.job_service;
import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Configuration;

import com.example.demoproject.service.api_service.ApiService;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class MyJobService extends JobService {
    private static final String TAG  = "My Job Service";
    private boolean jobCanceled = false;
    private MyJobExacter exacter;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started");
        Toast.makeText(getApplicationContext(),"Job Started",Toast.LENGTH_LONG).show();
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(JobParameters params) {

        exacter = new MyJobExacter(){
            @Override
            protected void onPostExecute(String s) {
                jobFinished(params,false);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Intent intentBackground = new Intent(getApplicationContext(), AccelerometerBackgroundService.class);
                getApplicationContext().startService(intentBackground);

                Intent intentBackgroundApi = new Intent(getApplicationContext(), ApiService.class);
                getApplicationContext().startService(intentBackgroundApi);
            }
        };

        exacter.execute();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Canceled before completion");
        exacter.cancel(true);
        return true;
    }
}
