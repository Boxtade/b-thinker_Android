package com.boxtade.b_thinker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kvins on 08/07/2016.
 */
public class FragmentTaskPrevious extends Fragment {

    SharedPreferences pref;
    TextView titleView,task;
    String []data;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_previous, container, false);

        Log.e("ID task", String.valueOf(this.getArguments().getInt("id")));
        pref = this.getActivity().getSharedPreferences("AppPref", this.getActivity().MODE_PRIVATE);
        token = pref.getString("token", "");

        Response.Listener<JSONObject> Tasks = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(final JSONObject response) {
                try{

                    if(data == null)
                        data = response.getString("task").split("[*][/][*]");

                    titleView = (TextView)getActivity().findViewById(R.id.taskPreviousTitle);

                    if(data[0] == ""){
                        titleView.setMaxLines(0);
                    }
                    else{
                        titleView.setMaxLines(1);
                        titleView.setText(data[0]);
                    }

                    task = (TextView)getActivity().findViewById(R.id.taskPreviousTask);
                    task.setText(data[1]);

                }
                catch (JSONException e){
                    Log.e("JSON",e.getLocalizedMessage());
                }
            }
        };

        TasksRequest sr = new TasksRequest(this.getActivity());
        sr.getTasks(Tasks,this.getArguments().getInt("id"));
        return view;
    }
}
