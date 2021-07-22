package com.example.demoproject.model;

public class SensorModel {
    private float accelerometerXValue;
    private float accelerometerYValue;
    private float accelerometerZValue;
    private float magnetometerXValue;
    private float magnetometerYValue;
    private float magnetometerZValue;
    private float gyroscopeXValue;
    private float gyroscopeYValue;
    private float gyroscopeZValue;
    private float proximity;

    private static SensorModel sensorModel;

    public SensorModel() {
    }

    public static SensorModel getSensorModel() {
        if (sensorModel == null) {
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

    public float getMagnetometerXValue() {
        return magnetometerXValue;
    }

    public void setMagnetometerXValue(float magnetometerXValue) {
        this.magnetometerXValue = magnetometerXValue;
    }

    public float getMagnetometerYValue() {
        return magnetometerYValue;
    }

    public void setMagnetometerYValue(float magnetometerYValue) {
        this.magnetometerYValue = magnetometerYValue;
    }

    public float getMagnetometerZValue() {
        return magnetometerZValue;
    }

    public void setMagnetometerZValue(float magnetometerZValue) {
        this.magnetometerZValue = magnetometerZValue;
    }

    public static void setSensorModel(SensorModel sensorModel) {
        SensorModel.sensorModel = sensorModel;
    }

    public float getGyroscopeXValue() {
        return gyroscopeXValue;
    }

    public void setGyroscopeXValue(float gyroscopeXValue) {
        this.gyroscopeXValue = gyroscopeXValue;
    }

    public float getGyroscopeYValue() {
        return gyroscopeYValue;
    }

    public void setGyroscopeYValue(float gyroscopeYValue) {
        this.gyroscopeYValue = gyroscopeYValue;
    }

    public float getGyroscopeZValue() {
        return gyroscopeZValue;
    }

    public void setGyroscopeZValue(float gyroscopeZValue) {
        this.gyroscopeZValue = gyroscopeZValue;
    }
}
