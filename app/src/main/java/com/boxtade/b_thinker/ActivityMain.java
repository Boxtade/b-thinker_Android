package com.boxtade.b_thinker;

import android.app.ActionBar;
import android.app.Dialog;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.ActionBar;

import android.support.v7.app.AppCompatActivity;

import java.util.List;


public class ActivityMain extends BFragmentActivity {


    String nameFragment = "tasks";
    Bundle args = null;
    SharedPreferences pref;
    String token, grav, oldpasstxt, newpasstxt;
    LinearLayout linearLayout;
    Button chgpass, chgpassfr, cancel, logout;
    Dialog dlg;
    EditText oldpass, newpass;
    Boolean quit;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("nameFragment", this.nameFragment);
        outState.putAll(this.args);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        this.quit = true;
        super.onDestroy();
    }

    public Boolean isQuit() {
        return quit;
    }

    @Override
    protected void onResume() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == 2) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);

        }
        super.onResume();
    }

    public void openFragment(String fragment, Bundle bundle) {
        this.nameFragment = fragment;
        this.args = bundle;

        switch (fragment) {
            case "tasks":
                FragmentTasks fragmentTasks = new FragmentTasks();
                loadFragment(fragmentTasks);
                break;
            case "task_previous":
                FragmentTaskPrevious fragmentTaskPrevious = new FragmentTaskPrevious();
                fragmentTaskPrevious.setArguments(bundle);
                loadFragment(fragmentTaskPrevious);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.quit = false;

//        chgpass = (Button)findViewById(R.id.chgbtn);
        ImageView logout = (ImageView) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = getSharedPreferences("AppPref", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                //Storing Data using SharedPreferences
                edit.putString("token", "");
                edit.commit();
                Intent loginactivity = new Intent(ActivityMain.this, ActivityLogin.class);
                startActivity(loginactivity);
                finish();
            }
        });

        this.args = new Bundle();
        if (savedInstanceState != null) {
            this.nameFragment = savedInstanceState.getString("nameFragment");
            this.args.putInt("id", savedInstanceState.getInt("id"));
        } else {
            openFragment(nameFragment, this.args);
        }


//        chgpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dlg = new Dialog(ActivityMain.this);
//                dlg.setContentView(R.layout.chgpassword_frag);
//                dlg.setTitle("Change Password");
//                chgpassfr = (Button)dlg.findViewById(R.id.chgbtn);
//
//                chgpassfr.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        oldpass = (EditText)dlg.findViewById(R.id.oldpass);
//                        newpass = (EditText)dlg.findViewById(R.id.newpass);
//                        oldpasstxt = oldpass.getText().toString();
//                        newpasstxt = newpass.getText().toString();
//                        params = new ArrayList<NameValuePair>();
//                        params.add(new BasicNameValuePair("oldpass", oldpasstxt));
//                        params.add(new BasicNameValuePair("newpass", newpasstxt));
//                        params.add(new BasicNameValuePair("id", token));
//                        ServerRequest sr = new ServerRequest();
//                        //    JSONObject json = sr.getJSON("http://192.168.56.1:8080/api/chgpass",params);
//                        JSONObject json = sr.getJSON("http://192.99.169.49:8080/api/chgpass",params);
//                        if(json != null){
//                            try{
//                                String jsonstr = json.getString("response");
//                                if(json.getBoolean("res")){
//
//                                    dlg.dismiss();
//                                    Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_SHORT).show();
//
//                                }
//                            }catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                });
//                cancel = (Button)dlg.findViewById(R.id.cancelbtn);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dlg.dismiss();
//                    }
//                });
//                dlg.show();
//            }
//        });

    }
}