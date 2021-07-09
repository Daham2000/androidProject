package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class PressureSensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView pressureValueView;
    private int lightValue;

    public PressureSensorManage(SensorManager sensorManager, TextView pressureValueView) {
        this.sensorManager = sensorManager;
        this.pressureValueView = pressureValueView;
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (light != null) {
            //register listener sensor manager
            sensorManager.registerListener(PressureSensorManage.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            pressureValueView.setText("Pressure sensor not support...");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //set light value to textView
        pressureValueView.setText("Light value:" + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
