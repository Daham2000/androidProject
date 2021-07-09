package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class ProximitySensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView valueView;
    private double value;

    public ProximitySensorManage(SensorManager sensorManager, TextView humidityValueView) {
        this.sensorManager = sensorManager;
        this.valueView = humidityValueView;
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximity != null) {
            //register listener sensor manager
            sensorManager.registerListener(ProximitySensorManage.this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            valueView.setText("Proximity sensor not support...");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //set light value to textView
        valueView.setText("Proximity value:" + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
