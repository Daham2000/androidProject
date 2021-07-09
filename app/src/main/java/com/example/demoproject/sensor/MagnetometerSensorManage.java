package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MagnetometerSensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView yValueView;
    private TextView xValueView;
    private TextView zValueView;
    private int xValue;
    private int yValue;
    private int zValue;

    public MagnetometerSensorManage(SensorManager sensorManager, TextView yValue, TextView xValue, TextView zValue) {
        this.sensorManager = sensorManager;
        this.yValueView = yValue;
        this.xValueView = xValue;
        this.zValueView = zValue;
        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (magnetometer != null) {
            //register listener sensor manager
            sensorManager.registerListener(MagnetometerSensorManage.this, magnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            xValueView.setText("Magnetometer not support...");
            yValueView.setText("Magnetometer not support...");
            zValueView.setText("Magnetometer not support...");
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
