package com.boxtade.b_thinker;



import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class TasksRequest {

    private Activity activity;
    private String url = "http://192.99.169.49:5000/";

    public TasksRequest(Activity activity) {
        this.activity = activity;

    }

    RequestQueue requestQueue;
    RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }
        return requestQueue;
    }

    public void getTasks(Response.Listener<JSONObject> jobs){
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+"tasks", null,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("TasksRequest",e.getLocalizedMessage());
        }
    }

    public void getTasks(Response.Listener<JSONObject> jobs,int id){
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+"tasks/"+id, null,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("TasksRequest",e.getLocalizedMessage());
        }
    }

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(activity,"ERROR JSON", Toast.LENGTH_LONG).show();
            Log.d("REQUEST", error.toString());
        }
    };


}
