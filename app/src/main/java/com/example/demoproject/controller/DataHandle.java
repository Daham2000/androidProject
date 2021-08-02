package com.example.demoproject.controller;

import android.annotation.SuppressLint;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DataHandle {
    private static final String TAG = "Data Controller";
    private static RestApi restApi = new RestApi();
    private HashMap<String, String> params = new HashMap<>();
    private static DataHandle dataHandle;
    Gson gson = new Gson();
    Cipher cipher;

    public static DataHandle getDataHandle() {
        if (dataHandle == null) {
            dataHandle = new DataHandle();
        }
        return dataHandle;
    }

    public SecretKey generateKey()
            throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(AppKey.Algorithm);
        keygen.init(256);
        return keygen.generateKey();
    }

    @SuppressLint("GetInstance")
    public byte[] encryptMsg(String message, SecretKey secret)
            throws Exception {
        /* Encrypt the message. */
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] input = message.getBytes();
        cipher.update(input);
        byte[] encryptedData = cipher.doFinal();
        return compressZ(encryptedData.toString());
    }

    @SuppressLint("GetInstance")
    public String decryptMsg(byte[] cipherText, SecretKey secret)
            throws Exception {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    public static byte[] compressZ(String stringIn) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos);
        dos.write(stringIn.getBytes());
        dos.flush();
        dos.close();
        return baos.toByteArray();
    }

    public void callRestAPI(Context context) throws Exception {
        SharedPreferences sp = context.getSharedPreferences("SensorDetail", Context.MODE_PRIVATE);
        String sensorJsonData = sp.getString(AppKey.Accelerometer, "");
        String proximityJsonData = sp.getString(AppKey.Proximity, "");
        SharedPreferences.Editor prefsEditor = sp.edit();
        SensorDataListModel sensorModelData = gson.fromJson(sensorJsonData, SensorDataListModel.class);
        ProximityModel proximity = gson.fromJson(proximityJsonData, ProximityModel.class);
        if (sensorModelData != null) {
            Log.e(TAG, "Data in Cache..." + sensorModelData.getSensorModelList().size());
            for (int i = 0; i < sensorModelData.getSensorModelList().size(); i++) {
                Log.e(TAG, "Call RestAPI: " + sensorModelData.getSensorModelList().get(i).getAccelerometerXValue());
                params.put("accelarometerX", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerXValue()));
                params.put("accelarometerY", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerYValue()));
                params.put("accelarometerZ", String.valueOf(sensorModelData.getSensorModelList().get(i).getAccelerometerZValue()));
                if (proximity != null) {
                    params.put("proximity", String.valueOf(sensorModelData.getSensorModelList().get(i).getProximity()));
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
