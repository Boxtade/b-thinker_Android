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

import java.util.Map;

/**
 * Created by kvins on 07/07/2016.
 */
public class UsersRequest {
    private Activity activity;
    private String url = "http://192.99.169.49:8080/";

    public UsersRequest(Activity activity) {
        this.activity = activity;

    }

    RequestQueue requestQueue;
    RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }
        return requestQueue;
    }

    public void postLogin(Response.Listener<JSONObject> jobs, JSONObject params){
        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url+"login", params,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("postLogin",e.getLocalizedMessage());
        }
    }

    public void postToken(Response.Listener<JSONObject> jobs, JSONObject params){
        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url+"api/token", params,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("postToken",e.getLocalizedMessage());
        }
    }

    public void postForget(Response.Listener<JSONObject> jobs, JSONObject params){
        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url+"api/resetpass", params,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("postForget",e.getLocalizedMessage());
        }
    }

    public void postCode(Response.Listener<JSONObject> jobs, JSONObject params){
        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url+"api/resetpass/chg", params,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("postCode",e.getLocalizedMessage());
        }
    }

    public void postRegister(Response.Listener<JSONObject> jobs, JSONObject params){
        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url+"register", params,jobs,errorListener);
            getRequestQueue().add(request);
        }
        catch (Exception e) {
            Log.e("postCode",e.getLocalizedMessage());
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
