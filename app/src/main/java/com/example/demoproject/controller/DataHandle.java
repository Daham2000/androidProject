package com.example.demoproject.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.example.demoproject.api.RestApi;
import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.model.SensorModel;
import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.google.gson.Gson;

import java.util.HashMap;

public class DataHandle {
    private static final String TAG = "Data Controller";
    private static RestApi restApi = new RestApi();
    private HashMap<String, String> params = new HashMap<>();
    private static DataHandle dataHandle;
    Gson gson = new Gson();

    public static DataHandle getDataHandle() {
        if (dataHandle == null) {
            dataHandle = new DataHandle();
        }
        return dataHandle;
    }

    public void callRestAPI(Context context) {
        SharedPreferences sp = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        String sensorJsonData = sp.getString("AccelerometerKey", "");
        SensorModel sensorModelData = gson.fromJson(sensorJsonData, SensorModel.class);
        if (sensorModelData != null) {
            params.put("accelerometerX", String.valueOf(sensorModelData.getAccelerometerXValue()));
            params.put("accelerometerY", String.valueOf(sensorModelData.getAccelerometerYValue()));
            params.put("accelerometerZ", String.valueOf(sensorModelData.getAccelerometerZValue()));
        }
        restApi.sendRequestPost(context, params);
        sp.edit().remove("AccelerometerKey");
    }

    public void saveInShared(Context context, SensorModel sensorModel, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        SharedPreferenceMemory memory = SharedPreferenceMemory.getSharedPreferenceMemory();
        memory.setSharedPreferences(sharedPreferences);
        memory.saveInShared(sensorModel, key);
    }

}
