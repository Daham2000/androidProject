package com.example.demoproject.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.demoproject.api.RestApi;

import java.util.HashMap;

public class DataHandle {
    private static DataHandle dataHandle;
    private static RestApi restApi;

    public DataHandle() {
    }

    DataHandle getInstance() {
        if (dataHandle == null) {
            return dataHandle;
        } else {
            return dataHandle;
        }
    }

    void callRestAPI(Context context, HashMap<String, String> params) {
        SharedPreferences sp = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        params.put("accelerometerX", sp.getString("accelerometerX", ""));
        params.put("accelerometerY", sp.getString("accelerometerY", ""));
        params.put("accelerometerZ", sp.getString("accelerometerZ", ""));
        params.put("gyroscopeX", sp.getString("gyroscopeX", ""));
        params.put("gyroscopeY", sp.getString("gyroscopeY", ""));
        params.put("gyroscopeZ", sp.getString("gyroscopeZ", ""));
        restApi.sendRequestPost(context, params);
    }



}
