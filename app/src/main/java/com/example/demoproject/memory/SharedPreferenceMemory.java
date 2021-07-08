package com.example.demoproject.memory;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.demoproject.model.SensorModel;

public class SharedPreferenceMemory {

    private static final String TAG = "SharedPreferenceMemory";

    final SharedPreferences sharedPreferences;

    public SharedPreferenceMemory(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveInShared(SensorModel sm) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accelerometerX", sm.getxValue());
        editor.putString("accelerometerY", sm.getyValue());
        editor.putString("accelerometerZ", sm.getzValue());
        editor.putString("gyroscopeX", sm.getGyXValue());
        editor.putString("gyroscopeY", sm.getgYValue());
        editor.putString("gyroscopeZ", sm.getGyZValue());
        editor.commit();
        Log.d(TAG, "Saved in Shared");
    }
}
