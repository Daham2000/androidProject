package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.ServiceAlarm;
import com.example.demoproject.service.ShortTimeEntryReceiver;
import com.example.demoproject.service.job_service.MyJobService;
import com.example.demoproject.service.job_service.SaveDataJobService;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private JobInfo jobInfo;
    private JobScheduler scheduler;
    private String workTag = "SaveData";
    private String workTagTwo = "SendData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize texView values
        TextView xValue = findViewById(R.id.xValue);
        //define TextView values
        TextView yValue = findViewById(R.id.yValue);
        TextView zValue = findViewById(R.id.zValue);

        TextView gyXValue = findViewById(R.id.xGyroValue);
        TextView gYValue = findViewById(R.id.yGyroValue);
        TextView gyZValue = findViewById(R.id.zGyroValue);

        TextView maXValue = findViewById(R.id.xMagnetoMeterValue);
        TextView maYValue = findViewById(R.id.yMagnetoMeterValue);
        TextView maZValue = findViewById(R.id.zMagnetoMeterValue);

        TextView lightValue = findViewById(R.id.light);
        TextView pressureValue = findViewById(R.id.pressure);
        TextView tempValue = findViewById(R.id.temp);
        TextView humidityValue = findViewById(R.id.humidity);
        TextView proximityValue = findViewById(R.id.proximity);

        //initialize sensor manager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager, xValue, yValue, zValue, this);
        //get the sensor Gyroscope
        GyroscopeSensorManage gyroscopeSensorManage = new GyroscopeSensorManage(
                sensorManager, gyXValue, gYValue, gyZValue);
        //get the sensor Magnetometer
        MagnetometerSensorManage magnetometerSensorManage = new MagnetometerSensorManage(
                sensorManager, maXValue, maYValue, maZValue);
        //get the sensor Light
        LightSensorManage lightSensorManage = new LightSensorManage(
                sensorManager, lightValue);
        //get the Pressure
        PressureSensorManage pressureSensorManage = new PressureSensorManage(
                sensorManager, pressureValue);
        //get the Temperature sensor
        TempSensorManage tempSensorManage = new TempSensorManage(
                sensorManager, tempValue);
        //get the Humidity sensor
        HumiditySensorManage humiditySensorManage = new HumiditySensorManage(
                sensorManager, humidityValue);
        //get the proximity sensor
        ProximitySensorManage proximitySensorManage = new ProximitySensorManage(
                sensorManager, proximityValue);

        //This field is used to perform alarm manager
//        Intent intentBackground = new Intent(getApplication(), ServiceAlarm.class);
//        startService(intentBackground);
//        sendDataJobSchedule();

        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void settingUpPeriodicWorkSaveData() {
        // Create Network constraint
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(workTag)
                        .build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP,periodicSendDataWork);
    }

    //Start worker method for call API send data to database
    private void settingUpPeriodicWorkSendData() {
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 16, TimeUnit.MINUTES)
                        .addTag(workTagTwo)
                        .setInitialDelay(1,TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTagTwo,ExistingPeriodicWorkPolicy.KEEP,periodicSendDataWork);
    }

    //This method for start Job service (Click event method)
    public void jobSchedule(View view) {
//        ComponentName componentName = new ComponentName(this, MyJobService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(123, componentName);
//        builder.setPeriodic(20 * 60000);
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setPersisted(true);
//        jobInfo = builder.build();
//
//        scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        scheduler.schedule(jobInfo);
//        Log.d(TAG, "Send data Job Scheduled");
//        Toast.makeText(getApplicationContext(), "Send data Job Scheduled", Toast.LENGTH_LONG).show();
    }

    public void sendDataJobSchedule() {
        ComponentName componentName = new ComponentName(this, SaveDataJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(122, componentName);
        builder.setPeriodic(15 * 60000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setPersisted(true);
        jobInfo = builder.build();

        scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);
        Log.d(TAG, "Save data Job Scheduled");
        Toast.makeText(getApplicationContext(), "Save data Job Scheduled", Toast.LENGTH_LONG).show();
    }

    //This method for stop Job service (Click event method)
    public void jobCancel(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        scheduler.cancel(122);
        Toast.makeText(getApplicationContext(), "Job Canceled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "job Canceled");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
        super.onDestroy();
    }
}