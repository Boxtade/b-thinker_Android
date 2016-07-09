package com.boxtade.b_thinker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ActivityRegister extends Activity {
    EditText email,password;
    Button login,register;
    SharedPreferences pref;
    UsersRequest ur;
    String emailtxt,passwordtxt;

    Response.Listener<JSONObject> Register = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(final JSONObject json) {
        if(json != null){
            try{
                String jsonstr = json.getString("response");

                Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();
                Intent regactivity = new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(regactivity);
                finish();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.registerbtn);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regactivity = new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(regactivity);
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                JSONObject params = new JSONObject();

                try {
                    params.put("email", emailtxt);
                    params.put("password", passwordtxt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ur.postRegister(Register,params);
            }
        });
    }




}