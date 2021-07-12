package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class LightSensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView lightValueView;
    private double lightValue;

    public LightSensorManage(SensorManager sensorManager, TextView lightValueView) {
        this.sensorManager = sensorManager;
        this.lightValueView = lightValueView;
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (light != null) {
            //register listener sensor manager
            sensorManager.registerListener(LightSensorManage.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            lightValueView.setText("Light not support...");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //set light value to textView
        lightValueView.setText("Light value:" + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
