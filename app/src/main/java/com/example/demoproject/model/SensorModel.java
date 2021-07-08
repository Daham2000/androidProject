package com.example.demoproject.model;

public class SensorModel {
    private String xValue;
    private String yValue;
    private String zValue;
    private String gyXValue;
    private String gYValue;
    private String gyZValue;

    public SensorModel(String xValue, String yValue, String zValue, String gyXValue, String gYValue, String gyZValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
        this.gyXValue = gyXValue;
        this.gYValue = gYValue;
        this.gyZValue = gyZValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public void setyValue(String yValue) {
        this.yValue = yValue;
    }

    public void setzValue(String zValue) {
        this.zValue = zValue;
    }

    public void setGyXValue(String gyXValue) {
        this.gyXValue = gyXValue;
    }

    public void setgYValue(String gYValue) {
        this.gYValue = gYValue;
    }

    public void setGyZValue(String gyZValue) {
        this.gyZValue = gyZValue;
    }

    public String getxValue() {
        return xValue;
    }

    public String getyValue() {
        return yValue;
    }

    public String getzValue() {
        return zValue;
    }

    public String getGyXValue() {
        return gyXValue;
    }

    public String getgYValue() {
        return gYValue;
    }

    public String getGyZValue() {
        return gyZValue;
    }
}
