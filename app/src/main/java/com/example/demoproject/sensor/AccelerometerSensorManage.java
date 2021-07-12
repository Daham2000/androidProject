package com.example.demoproject.sensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import com.example.demoproject.controller.DataHandle;
import com.example.demoproject.model.SensorModel;

public class AccelerometerSensorManage implements SensorEventListener {

    private static final String TAG = "AccelerometerSensor";
    private SensorManager sensorManager;
    private TextView yValueView;
    private TextView xValueView;
    private TextView zValueView;
    private float xValue;
    private float yValue;
    private float zValue;
    private Context context;
    private Sensor accelerometer;
    public AccelerometerSensorManage(SensorManager sensorManager, TextView xValue, TextView yValue, TextView zValue, Context context) {
        this.sensorManager = sensorManager;
        this.yValueView = yValue;
        this.xValueView = xValue;
        this.zValueView = zValue;
        this.context = context;
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            //register listener sensor manager
            sensorManager.registerListener(AccelerometerSensorManage.this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            xValue.setText("Accelerometer not support...");
            yValue.setText("Accelerometer not support...");
            zValue.setText("Accelerometer not support...");
        }
    }

    public AccelerometerSensorManage(SensorManager sensorManager, Context context) {
        this.sensorManager = sensorManager;
        this.context = context;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            //register listener sensor manager
            sensorManager.registerListener(AccelerometerSensorManage.this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (yValueView != null) {
            //set accelerometer value to textView
            xValueView.setText("X value:" + event.values[0]);
            yValueView.setText("Y value:" + event.values[1]);
            zValueView.setText("Z value:" + event.values[2]);
        }
        xValue = event.values[0];
        yValue = event.values[1];
        zValue = event.values[2];

        DataHandle dataHandle = DataHandle.getDataHandle();
        SensorModel sensorModel = SensorModel.getSensorModel();
        sensorModel.setAccelerometerXValue(xValue);
        sensorModel.setAccelerometerYValue(yValue);
        sensorModel.setAccelerometerZValue(zValue);
        dataHandle.saveInShared(context, sensorModel);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
