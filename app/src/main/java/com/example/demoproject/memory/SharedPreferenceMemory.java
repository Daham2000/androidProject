package com.example.demoproject.memory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.SensorModel;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.google.gson.Gson;

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

    public void saveInShared(SensorModel sm,String key) {
        SharedPreferences sp = getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        String sensorObject = gson.toJson(sm);
        prefsEditor.remove(key);
        prefsEditor.putString(key, sensorObject);
        prefsEditor.commit();
        Log.e(TAG, "Data saved in shared");
    }
}
