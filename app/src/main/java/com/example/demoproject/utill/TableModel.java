package com.example.demoproject.utill;
import java.io.Serializable;

public class TableModel implements Serializable {

    private String time;

    private double accelerometerX;

    private double accelerometerY;

    private double accelerometerZ;

    public TableModel() {
    }

    public TableModel(String time, double accelerometerX, double accelerometerY, double accelerometerZ) {
        this.time = time;
        this.accelerometerX = accelerometerX;
        this.accelerometerY = accelerometerY;
        this.accelerometerZ = accelerometerZ;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAccelerometerX() {
        return accelerometerX;
    }

    public void setAccelerometerX(double accelerometerX) {
        this.accelerometerX = accelerometerX;
    }

    public double getAccelerometerY() {
        return accelerometerY;
    }

    public void setAccelerometerY(double accelerometerY) {
        this.accelerometerY = accelerometerY;
    }

    public double getAccelerometerZ() {
        return accelerometerZ;
    }

    public void setAccelerometerZ(double accelerometerZ) {
        this.accelerometerZ = accelerometerZ;
    }
}