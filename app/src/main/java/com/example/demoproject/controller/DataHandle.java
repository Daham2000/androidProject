package com.example.demoproject.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.example.demoproject.api.RestApi;
import com.example.demoproject.memory.SharedPreferenceMemory;
import com.example.demoproject.model.ProximityModel;
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
import com.example.demoproject.utill.AppKey;
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
        String sensorJsonData = sp.getString(AppKey.Accelerometer, "");
        String proximityJsonData = sp.getString(AppKey.Proximity, "");
        SharedPreferences.Editor prefsEditor = sp.edit();
        SensorDataListModel sensorModelData = gson.fromJson(sensorJsonData, SensorDataListModel.class);
        ProximityModel proximity = gson.fromJson(proximityJsonData, ProximityModel.class);
        if (sensorModelData != null) {
            Log.e(TAG, "Data in Cache..."+sensorModelData.getSensorModelList().size());
            for(int i=0; i<sensorModelData.getSensorModelList().size();i++){
                Log.e(TAG, "Call RestAPI: "+ sensorModelData.getSensorModelList().get(i).getAccelerometerXValue());
                params.put("accelarometerX", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerXValue()));
                params.put("accelarometerY", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerYValue()));
                params.put("accelarometerZ", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerZValue()));
                if(proximity!=null){
                    params.put("pressure", String.valueOf(proximity.getProximityModelList().get(i).getProximity()));
                }
                restApi.sendRequestPost(context, params);
                params.clear();
            }
        }
        prefsEditor.remove(AppKey.Accelerometer).apply();
        prefsEditor.remove(AppKey.Proximity).apply();
        prefsEditor.remove(AppKey.Gyroscope).apply();
        prefsEditor.remove(AppKey.MagneticField).apply();
        Log.e(TAG, "API call done...");
    }

    public void saveInShared(Context context, SensorModel sensorModel, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        SharedPreferenceMemory memory = SharedPreferenceMemory.getSharedPreferenceMemory();
        memory.setSharedPreferences(sharedPreferences);
        memory.saveInShared(sensorModel, key);
    }

}
