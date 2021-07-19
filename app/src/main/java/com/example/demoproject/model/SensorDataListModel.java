package com.example.demoproject.model;

import java.util.HashSet;
import java.util.List;

public class SensorDataListModel {
    private HashSet<SensorModel> sensorModelList;

    public HashSet<SensorModel> getSensorModelList() {
        return sensorModelList;
    }

    public void setSensorModelList(HashSet<SensorModel> sensorModelList) {
        this.sensorModelList = sensorModelList;
    }
}
