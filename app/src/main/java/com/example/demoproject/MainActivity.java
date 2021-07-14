package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button stopBtn = findViewById(R.id.stopBtnID);

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

        //Start worker for get Data from Sensor and save it on shared memory
        SettingUpPeriodicWorkSaveData();
        //Start worker for call API
        SettingUpPeriodicWorkSendData();
//        stopBtn.setOnClickListener(v -> stopService(intent));
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void SettingUpPeriodicWorkSaveData() {
        // Create Network constraint
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 1, TimeUnit.MINUTES)
                        .addTag("SaveData")
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(periodicSendDataWork);
    }

    //Start worker method for call API/ send data to database
    private void SettingUpPeriodicWorkSendData() {
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 2, TimeUnit.MINUTES)
                        .addTag("SendData")
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(periodicSendDataWork);
    }
}