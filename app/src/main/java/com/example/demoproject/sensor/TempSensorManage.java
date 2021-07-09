package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class TempSensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView valueView;
    private int lightValue;

    public TempSensorManage(SensorManager sensorManager, TextView pressureValueView) {
        this.sensorManager = sensorManager;
        this.valueView = pressureValueView;
        Sensor temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (temp != null) {
            //register listener sensor manager
            sensorManager.registerListener(TempSensorManage.this, temp,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            pressureValueView.setText("Temperature sensor not support...");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //set light value to textView
        valueView.setText("Light value:" + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
