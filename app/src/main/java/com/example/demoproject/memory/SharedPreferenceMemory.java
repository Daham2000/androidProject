package com.example.demoproject.memory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.ProximityModel;
import com.example.demoproject.model.SensorDataListModel;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SharedPreferenceMemory {

    private static final String TAG = "SharedPreferenceMemory";
    Gson gson = new Gson();

    private static SharedPreferenceMemory sharedPreferenceMemory;
    private SharedPreferences sharedPreferences;

    public static SharedPreferenceMemory getSharedPreferenceMemory() {
        if (sharedPreferenceMemory == null) {
            sharedPreferenceMemory = new SharedPreferenceMemory();
        }
        return sharedPreferenceMemory;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences(String sensorDetail, int modePrivate) {
        return sharedPreferences;
    }

    //Save data in phone memory (SharedPreferences save data)
    public void saveInShared(SensorModel sm, String key) {
        SharedPreferences sp = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        String sensorJsonData = sp.getString(key, "");
        String jsonData = "";
        if (key == "Accelerometer") {
            SensorDataListModel sensorDataListModel = gson.fromJson(sensorJsonData, SensorDataListModel.class);
            if (sensorDataListModel == null) {
                sensorDataListModel = new SensorDataListModel();
                ArrayList<SensorModel> sensorModelList = new ArrayList<>();
                sensorModelList.add(sm);
                sensorDataListModel.setSensorModelList(sensorModelList);
                jsonData = gson.toJson(sensorDataListModel);
            } else {
                sensorDataListModel.getSensorModelList().add(sm);
            }
        } else if (key == "Proximity") {
            ProximityModel.Proximity proximity = new ProximityModel.Proximity();
            proximity.setProximity(sm.getProximity());
            ProximityModel proximityList = gson.fromJson(sensorJsonData, ProximityModel.class);
            if (proximityList == null) {
                proximityList = new ProximityModel();
                ArrayList<ProximityModel.Proximity> proximityHashSet = new ArrayList<>();
                proximityHashSet.add(proximity);
                proximityList.setProximityModelList(proximityHashSet);
                jsonData = gson.toJson(proximityList);
            } else {
                proximityList.getProximityModelList().add(proximity);
            }
            Log.e(TAG, "saveInShared: "+proximity.getProximity());
        }
        SharedPreferences.Editor prefsEditor = sp.edit();
        if (jsonData != "") {
            prefsEditor.remove(key).apply();
            prefsEditor.putString(key, jsonData);
            prefsEditor.apply();
            Log.e(TAG, "Data saved in shared");
        }
    }
}
