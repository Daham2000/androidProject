package com.example.demoproject.service.job_service;

import android.os.AsyncTask;
import android.widget.Toast;

public class MyJobExacter extends AsyncTask<Void,Void,String> {

    @Override
    protected String doInBackground(Void... voids) {
        return "Background Running task...";
    }
}
