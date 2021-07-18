package com.example.demoproject.api;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Handler;

public class RestApi {

    String URL = "https://jsonplaceholder.typicode.com/posts/";

    private static final String TAG = "RestApi";

    public void sendRequestGet(Context context){
        Log.d(TAG, "sendRequestGet");
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> Log.e("Response - ",response.toString()),
                error -> {

                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void sendRequestPost(Context context, HashMap<String, String> params){

         FirebaseDatabase db = FirebaseDatabase.getInstance();
         DatabaseReference root = db.getReference().child("Sensor Data");

        Log.d(TAG, "sendRequestPost");
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                new JSONObject(params),
                response -> Log.e("Post Response - ",response.toString()),
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);

        HashMap<String, String> list = new HashMap<>();
        list.put("Accelerometer X", params.get("accelerometerX"));
        list.put("Accelerometer Y", params.get("accelerometerY"));
        list.put("Accelerometer Z", params.get("accelerometerZ"));

        root.push().setValue(list);
    }
}
