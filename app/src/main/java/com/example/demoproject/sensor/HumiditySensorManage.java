package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class HumiditySensorManage implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView valueView;
    private double value;

    public HumiditySensorManage(SensorManager sensorManager, TextView humidityValueView) {
        this.sensorManager = sensorManager;
        this.valueView = humidityValueView;
        Sensor humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        if (humidity != null) {
            //register listener sensor manager
            sensorManager.registerListener(HumiditySensorManage.this, humidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            valueView.setText("Humidity sensor not support...");
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
