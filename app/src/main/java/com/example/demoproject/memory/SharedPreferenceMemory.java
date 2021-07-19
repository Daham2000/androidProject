package com.example.demoproject.memory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.SensorDataListModel;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.google.gson.Gson;

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
    public void saveInShared(SensorModel sm,String key) {
        SharedPreferences sp = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        String sensorJsonData = sp.getString("SensorKey", "");
        SensorDataListModel sensorDataListModel = gson.fromJson(sensorJsonData, SensorDataListModel.class);
        if(sensorDataListModel==null){
            sensorDataListModel = new SensorDataListModel();
            HashSet<SensorModel> sensorModelList = new HashSet<>();
            sensorModelList.add(sm);
            sensorDataListModel.setSensorModelList(sensorModelList);
        }
        sensorDataListModel.getSensorModelList().add(sm);
        SharedPreferences.Editor prefsEditor = sp.edit();
        String sensorObject = gson.toJson(sensorDataListModel);
        prefsEditor.remove(key);
        prefsEditor.putString(key, sensorObject);
        prefsEditor.apply();
        Log.e(TAG, "Data saved in shared");
    }
}
