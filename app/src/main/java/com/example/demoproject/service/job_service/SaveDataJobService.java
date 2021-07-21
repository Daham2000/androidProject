package com.example.demoproject.service.job_service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class SaveDataJobService extends JobService {
    private static final String TAG  = "Save data Job Service";
    private MyJobExacter exacter;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started");
        Toast.makeText(getApplicationContext(),"Job Started",Toast.LENGTH_LONG).show();
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(JobParameters params) {
        exacter = new MyJobExacter() {
            @Override
            protected void onPostExecute(String s) {
                Log.e(TAG, "Job do BackgroundWork");
                jobFinished(params, false);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                AccelerometerBackgroundService accelerometerBackgroundService =
                        AccelerometerBackgroundService.getAccelerometerBackground(getApplicationContext());
            }
        };
        exacter.execute();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Canceled before completion");
        exacter.cancel(true);
        return false;
    }
}
