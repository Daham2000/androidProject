package com.example.demoproject.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RestApi {

    String URL = "https://jsonplaceholder.typicode.com/posts/";

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

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = db.getReference().child("Sensor Data");
    //Post method to sent data to data (End point)
    public void sendRequestPost(Context context, HashMap<String, String> params) {
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                URL,
//                new JSONObject(params),
//                response -> Log.e("Post Response - ", response.toString()),
//                error -> {
//
//                }
//        );
//        requestQueue.add(jsonObjectRequest);

        //Use to sent data to Firebase database
        HashMap<String, String> list = new HashMap<>();
        list.put("Accelerometer X", params.get("accelerometerX"));
        list.put("Accelerometer Y", params.get("accelerometerY"));
        list.put("Accelerometer Z", params.get("accelerometerZ"));
        list.put("Proximity Value", params.get("proximity"));

        Task<Void> voidTask = mDatabase.child(dateFormat.format(date)).setValue(list);
        if(voidTask.isSuccessful()){
            Log.d(TAG, "sendRequestPost");
            android.os.Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Run your task here
                    Toast.makeText(context, "Worker Send data", Toast.LENGTH_SHORT).show();
                }
            }, 2000 );
        }

    }
}
