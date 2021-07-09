package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class GyroscopeSensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView yValueView;
    private TextView xValueView;
    private TextView zValueView;
    private int xValue;
    private int yValue;
    private int zValue;

    public GyroscopeSensorManage(SensorManager sensorManager, TextView yValue, TextView xValue, TextView zValue) {
        this.sensorManager = sensorManager;
        this.yValueView = yValue;
        this.xValueView = xValue;
        this.zValueView = zValue;
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscope != null) {
            //register listener sensor manager
            sensorManager.registerListener(GyroscopeSensorManage.this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            xValue.setText("Gyroscope not support...");
            yValue.setText("Gyroscope not support...");
            zValue.setText("Gyroscope not support...");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //set accelerometer value to textView
        xValueView.setText("X value:" + event.values[0]);
        yValueView.setText("Y value:" + event.values[1]);
        zValueView.setText("Z value:" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
