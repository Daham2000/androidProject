package com.example.demoproject.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.example.demoproject.api.RestApi;
import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.model.SensorDataListModel;
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
        String sensorJsonData = sp.getString("SensorKey", "");
        SensorDataListModel sensorModelData = gson.fromJson(sensorJsonData, SensorDataListModel.class);
        if (sensorModelData != null) {
            for(SensorModel sensorModel:sensorModelData.getSensorModelList()){
                params.put("accelerometerX", String.valueOf(sensorModel.getAccelerometerXValue()));
                params.put("accelerometerY", String.valueOf(sensorModel.getAccelerometerYValue()));
                params.put("accelerometerZ", String.valueOf(sensorModel.getAccelerometerZValue()));
                params.put("proximity", String.valueOf(sensorModel.getProximity()));
                restApi.sendRequestPost(context, params);
                params.clear();
            }
        }
        sp.edit().remove("SensorKey");
    }

    public void saveInShared(Context context, SensorModel sensorModel, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        SharedPreferenceMemory memory = SharedPreferenceMemory.getSharedPreferenceMemory();
        memory.setSharedPreferences(sharedPreferences);
        memory.saveInShared(sensorModel, key);
    }

}
