package com.example.demoproject.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.SensorModel;
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

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void saveInShared(SensorModel sm) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String sensorObject = gson.toJson(sm);
        prefsEditor.putString("SensorDetailKey", sensorObject);
        prefsEditor.commit();
    }
}
