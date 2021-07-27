package com.example.demoproject.api;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoproject.service.sensor_service.AccelerometerBackgroundService;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

public class RestApi {

    String URL = "https://androidsensorread.herokuapp.com/posts";

    private static final String TAG = "RestApi";

    //Get method to get data from database (End point)
    public void sendRequestGet(Context context) {
        Log.d(TAG, "sendRequestGet");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> Log.e("Response - ", response.toString()),
                error -> {

                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    //Post method to sent data to data (End point)
    public void sendRequestPost(Context context, HashMap<String, String> params) {
        JSONObject object = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                object,
                response -> Log.e("Post Response - ", response.toString()),
                error -> {
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
