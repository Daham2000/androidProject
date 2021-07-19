package com.example.demoproject.model;

public class SensorModel {
    private float accelerometerXValue;
    private float accelerometerYValue;
    private float accelerometerZValue;
    private float proximity;

    private static SensorModel sensorModel;

    public static SensorModel getSensorModel() {
        if(sensorModel==null){
            sensorModel = new SensorModel();
        }
        return sensorModel;
    }

    public float getAccelerometerXValue() {
        return accelerometerXValue;
    }

    public float getAccelerometerYValue() {
        return accelerometerYValue;
    }

    public float getAccelerometerZValue() {
        return accelerometerZValue;
    }

    public void setAccelerometerXValue(float accelerometerXValue) {
        this.accelerometerXValue = accelerometerXValue;
    }

    public void setAccelerometerYValue(float accelerometerYValue) {
        this.accelerometerYValue = accelerometerYValue;
    }

    public void setAccelerometerZValue(float accelerometerZValue) {
        this.accelerometerZValue = accelerometerZValue;
    }

    public float getProximity() {
        return proximity;
    }

    public void setProximity(float proximity) {
        this.proximity = proximity;
    }
}
