package com.example.demoproject.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.example.demoproject.utill.AppKey;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class RestApi {

//    String URL = "https://androidsensorread.herokuapp.com/posts";

    private static final String TAG = "RestApi";

    //Get method to get data from database (End point)
    public void sendRequestGet(Context context) {
        Log.d(TAG, "sendRequestGet");
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                response -> Log.e("Response - ", response.toString()),
//                error -> {}
//        );
//        requestQueue.add(jsonObjectRequest);
    }

    Gson gson = new Gson();
    Cipher cipher;

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

    public static byte[] compressZ(String stringIn) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos);
        dos.write(stringIn.getBytes());
        dos.flush();
        dos.close();
        return baos.toByteArray();
    }

    //Post method to sent data to data (End point)
    public void sendRequestPost(Context context, HashMap<String, String> params) throws Exception {
        SecretKey secret = generateKey();
        URL url = new URL("https://androidsensorread.herokuapp.com/posts");

        String jsonObject = gson.toJson(params);
        byte[] encriped = encryptMsg(jsonObject, secret);
        byte[] compressedData = compressZ(encriped.toString());
        String json = gson.toJson(compressedData);

        BufferedReader reader = null;
        try {
            //Sent post data
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            Log.e(TAG, "sendRequestPost: " + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
    }

}

