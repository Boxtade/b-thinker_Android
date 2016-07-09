package com.boxtade.b_thinker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import org.json.JSONException;
import org.json.JSONObject;


public class ActivityLogin extends Activity {
    EditText email,password,res_email,code,newpass;
    Button login,cont,cont_code,cancel,cancel1,register,forpass;
    String emailtxt,passwordtxt,email_res_txt,code_txt,npass_txt;
    SharedPreferences pref;
    Dialog reset;
    UsersRequest ur;

    Response.Listener<JSONObject> Token = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(final JSONObject json) {
            if (json != null) {
                try {
                    if (json.getBoolean("res")) {

                        Intent profactivity = new Intent(ActivityLogin.this, ActivityMain.class);
                        startActivity(profactivity);
                        finish();
                    }

                } catch (JSONException e) {
                    Log.e("JSON POST TOKEN",e.getLocalizedMessage());
                }
            }
        }
    };

    Response.Listener<JSONObject> Login = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(final JSONObject json) {
            if(json != null){
                try{
                    if(json.getBoolean("res")){
                        String token = json.getString("token");
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("token", token);
                        edit.commit();
                        Intent profactivity = new Intent(ActivityLogin.this,ActivityMain.class);
                        startActivity(profactivity);
                        finish();
                    }

                    Toast.makeText(getApplication(),json.getString("response"),Toast.LENGTH_LONG).show();

                }catch (JSONException e) {
                    Log.e("JSON POST LOGIN",e.getLocalizedMessage());
                }
            }
        }
    };

    Response.Listener<JSONObject> Code = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(final JSONObject json) {
            if (json != null) {
                try {
                    String jsonstr = json.getString("response");
                    if(json.getBoolean("res")){
                        reset.dismiss();
                        Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    Log.e("JSON POST CODE",e.getLocalizedMessage());
                }
            }
        }
    };

    Response.Listener<JSONObject> Forget = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(final JSONObject json) {
            if (json != null) {
                try {
                    String jsonstr = json.getString("response");
                    if(json.getBoolean("res")){
                        Log.e("JSON", jsonstr);
                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                        reset.setContentView(R.layout.reset_pass_code);
                        cont_code = (Button)reset.findViewById(R.id.conbtn);
                        code = (EditText)reset.findViewById(R.id.code);
                        newpass = (EditText)reset.findViewById(R.id.npass);
                        cancel1 = (Button)reset.findViewById(R.id.cancel);
                        cancel1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                reset.dismiss();
                            }
                        });
                        cont_code.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                code_txt = code.getText().toString();
                                npass_txt = newpass.getText().toString();
                                JSONObject params = new JSONObject();
                                try {
                                    params.put("email", email_res_txt);
                                    params.put("code", code_txt);
                                    params.put("newpass", npass_txt);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ur.postCode(Code,params);
                            }
                        });
                    }else{
                        Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ur = new UsersRequest(ActivityLogin.this);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
        String token = pref.getString("token", "");
        if(token != ""){
            JSONObject params = null;
            try {
                params = new JSONObject().put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ur.postToken(Token,params);
        }
        setContentView(R.layout.activity_login);


        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.loginbtn);
        register = (Button)findViewById(R.id.register);
        forpass = (Button)findViewById(R.id.forgotpass);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regactivity = new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(regactivity);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
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

                ur.postLogin(Login,params);
            }
        });

        forpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                reset = new Dialog(ActivityLogin.this);
                reset.setTitle("Reset Password");
                reset.setContentView(R.layout.reset_pass_init);
                cont = (Button)reset.findViewById(R.id.resbtn);
                cancel = (Button)reset.findViewById(R.id.cancelbtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reset.dismiss();
                    }
                });
                res_email = (EditText)reset.findViewById(R.id.email);

                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email_res_txt = res_email.getText().toString();

                        JSONObject params = new JSONObject();
                        try {
                            params.put("email", email_res_txt);
                        } catch (JSONException e) {
                            Log.e("JSON",e.getLocalizedMessage());
                        }

                        ur.postForget(Forget,params);

                    }
                });

                reset.show();
            }
        });
    }




}