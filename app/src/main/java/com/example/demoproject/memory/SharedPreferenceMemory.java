package com.example.demoproject.memory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.DateTimeModel;
import com.example.demoproject.model.GyroModel;
import com.example.demoproject.model.ProximityModel;
import com.example.demoproject.model.SensorDataListModel;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.utill.AppKey;
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
        if (key.equals(AppKey.Accelerometer)) {
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
            Log.e(TAG, "saveInShared: " + sensorDataListModel.getSensorModelList());
        } else if (key.equals(AppKey.Proximity)) {
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
            Log.e(TAG, "saveInShared: " + proximity.getProximity());
        } else if (key == AppKey.Gyroscope) {
            GyroModel.Gyroscope gyroscope = new GyroModel.Gyroscope();
            gyroscope.setGyroscopeX(sm.getGyroscopeXValue());
            gyroscope.setGyroscopeY(sm.getGyroscopeYValue());
            gyroscope.setGyroscopeZ(sm.getGyroscopeZValue());
            GyroModel gyroModel = gson.fromJson(sensorJsonData, GyroModel.class);
            if (gyroModel == null) {
                gyroModel = new GyroModel();
                ArrayList<GyroModel.Gyroscope> gyroscopeArrayList = new ArrayList<>();
                gyroscopeArrayList.add(gyroscope);
                gyroModel.setGyroscopeArrayList(gyroscopeArrayList);
                jsonData = gson.toJson(gyroModel);
            } else {
                gyroModel.getGyroscopeArrayList().add(gyroscope);
            }
        }
        SharedPreferences.Editor prefsEditor = sp.edit();
        if (jsonData != "") {
            prefsEditor.remove(key).apply();
            prefsEditor.putString(key, jsonData);
            prefsEditor.apply();
            Log.e(TAG, "Data saved in shared");
        }
    }

    public void saveDataUploadTime(String time) {
        //Save last data upload time
        String jsonData = "";
        SharedPreferences lastDataUploadTimeS = getSharedPreferences(AppKey.LastDataUploadTime, Context.MODE_PRIVATE);
        String uploadTime = lastDataUploadTimeS.getString("UploadTime", "");
        DateTimeModel dateTimeModel = gson.fromJson(uploadTime, DateTimeModel.class);
        if (dateTimeModel == null) {
            dateTimeModel = new DateTimeModel();
            ArrayList<String> dateList = new ArrayList<>();
            dateList.add(time);
            dateTimeModel.setLastDataUploadTimeList(dateList);
            jsonData = gson.toJson(dateTimeModel);
        } else {
            dateTimeModel.getLastDataUploadTimeList().add(time);
            jsonData = gson.toJson(dateTimeModel);
        }

        SharedPreferences.Editor editor = lastDataUploadTimeS.edit();
        editor.remove("UploadTime").apply();
        editor.putString("UploadTime", jsonData).apply();

        String uploadTimeInShared = lastDataUploadTimeS.getString("UploadTime", "");
        DateTimeModel uploadTimeInSharedData = gson.fromJson(uploadTimeInShared, DateTimeModel.class);
        if (uploadTimeInSharedData != null) {
            Log.e(TAG, "Upload Time InShared: " + uploadTimeInSharedData.getLastDataUploadTimeList());
        }
    }
}
