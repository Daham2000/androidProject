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
    private float pressure;
    private float light;
    private float temperature;
    private float humidity;

    private static SensorModel sensorModel;

    public SensorModel() {
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public static SensorModel getSensorModel() {
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
