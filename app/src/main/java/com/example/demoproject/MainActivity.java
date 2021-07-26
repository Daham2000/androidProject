package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.service.worker.ApiCallWorker;
import com.example.demoproject.service.worker.SaveDataWorker;
import com.example.demoproject.utill.AppKey;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize texView values
        TextView xValue = findViewById(R.id.xValue);
        //define TextView values
        TextView yValue = findViewById(R.id.yValue);
        TextView zValue = findViewById(R.id.zValue);

        TextView gyXValue = findViewById(R.id.xGyroValue);
        TextView gYValue = findViewById(R.id.yGyroValue);
        TextView gyZValue = findViewById(R.id.zGyroValue);

        TextView maXValue = findViewById(R.id.xMagnetoMeterValue);
        TextView maYValue = findViewById(R.id.yMagnetoMeterValue);
        TextView maZValue = findViewById(R.id.zMagnetoMeterValue);

        TextView lightValue = findViewById(R.id.light);
        TextView pressureValue = findViewById(R.id.pressure);
        TextView tempValue = findViewById(R.id.temp);
        TextView humidityValue = findViewById(R.id.humidity);
        TextView proximityValue = findViewById(R.id.proximity);

        //initialize sensor manager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the sensor accelerometer
        AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                sensorManager, xValue, yValue, zValue, this);
        //get the sensor Gyroscope
        GyroscopeSensorManage gyroscopeSensorManage = new GyroscopeSensorManage(
                sensorManager, gyXValue, gYValue, gyZValue);
        //get the sensor Magnetometer
        MagnetometerSensorManage magnetometerSensorManage = new MagnetometerSensorManage(
                sensorManager, maXValue, maYValue, maZValue);
        //get the sensor Light
        LightSensorManage lightSensorManage = new LightSensorManage(
                sensorManager, lightValue);
        //get the Pressure
        PressureSensorManage pressureSensorManage = new PressureSensorManage(
                sensorManager, pressureValue);
        //get the Temperature sensor
        TempSensorManage tempSensorManage = new TempSensorManage(
                sensorManager, tempValue);
        //get the Humidity sensor
        HumiditySensorManage humiditySensorManage = new HumiditySensorManage(
                sensorManager, humidityValue);
        //get the proximity sensor
        ProximitySensorManage proximitySensorManage = new ProximitySensorManage(
                sensorManager, proximityValue);

        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
    }

    //Start worker method for get Data from Sensor and save it on shared memory
    private void settingUpPeriodicWorkSaveData() {
        String workTag = AppKey.SaveDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(SaveDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(workTag)
                        .build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    //Start worker method for call API send data to database
    private void settingUpPeriodicWorkSendData() {
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        String workTagTwo = AppKey.SendDataTag;
        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(ApiCallWorker.class, 30, TimeUnit.MINUTES)
                        .addTag(workTagTwo)
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork(workTagTwo, ExistingPeriodicWorkPolicy.KEEP, periodicSendDataWork);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        settingUpPeriodicWorkSaveData();
        settingUpPeriodicWorkSendData();
        super.onDestroy();
    }

    public void encryptData(View view) throws Exception {
        SecretKey secret = generateKey();
        byte[] encryptedData = encryptMsg("1qwerREWQ2", secret);
        String decryptedData = decryptMsg(encryptedData, secret);
        Log.d(TAG, "Encrypted Data: " + encryptedData);
        Log.d(TAG, "Decrypted Data: " + decryptedData);
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
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    @SuppressLint("GetInstance")
    public String decryptMsg(byte[] cipherText, SecretKey secret)
            throws Exception {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    public void compressData(View view) {
        try {
            // Encode a String into bytes
            //This line repreasant the data that we want to
            String inputString = "blahblahblahddddddd ddddddddddd qqqqqqqqqqqq dddddddddddddddddddd" +
                    "wwwwwwwwwww wwwwwwwwww zzzzzzzzz x xssaw         ggggggggg";
            byte[] input = inputString.getBytes("UTF-8");

            // Compress the bytes
            byte[] output = new byte[100];
            Deflater compresser = new Deflater();
            compresser.setInput(input);
            compresser.finish();
            int compressedDataLength = compresser.deflate(output);
            Log.d(TAG, "Compressed Data length: "+compressedDataLength);
            compresser.end();

            // Decompress the bytes
            Inflater deCompressor = new Inflater();
            deCompressor.setInput(output, 0, compressedDataLength);
            byte[] result = new byte[inputString.length()];
            int resultLength = deCompressor.inflate(result);
            Log.d(TAG, "Decompressed Data length: "+resultLength);
            deCompressor.end();

            // Decode the bytes into a String
            String outputString = new String(result, 0, resultLength, StandardCharsets.UTF_8);
            Log.d(TAG, "Final output:- "+outputString);
        } catch(java.io.UnsupportedEncodingException ex) {
            // handle
        } catch (java.util.zip.DataFormatException ex) {
            // handle
        }
    }
}