package com.boxtade.b_thinker;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentTasks extends Fragment {

    SharedPreferences pref;
    String token,grav,oldpasstxt,newpasstxt;
    int orientation;
    GridView gridView;
    ListView listView;

    @Override
    public void onDestroy() {

        if(orientation == getResources().getConfiguration().orientation)
            if(!((ActivityMain)getActivity()).isQuit())
                getActivity().onBackPressed();
        super.onDestroy();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_view, container, false);
        orientation = getResources().getConfiguration().orientation;
        pref = this.getActivity().getSharedPreferences("AppPref", this.getActivity().MODE_PRIVATE);
        token = pref.getString("token", "");
        grav = pref.getString("grav", "");

        Response.Listener<JSONObject> Tasks = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(final JSONObject response) {
                try{
                    JSONArray data = response.getJSONArray("tasks");
                    renderTasks(data);
                }
                catch (JSONException e){
                    Log.e("JSON",e.getLocalizedMessage());
                }
            }
        };

        TasksRequest sr = new TasksRequest(this.getActivity());
        sr.getTasks(Tasks);
        return view;
    }

    public void renderTasks(JSONArray data) throws JSONException {

        if(orientation == 2) {
            gridView = (GridView) this.getActivity().findViewById(R.id.grid);

            gridView.setAdapter(new TaskContentAdapter(this.getActivity(), data));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    int id_task = 0;
                    try {
                        id_task = ((JSONObject)parent.getAdapter().getItem(position)).getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bundle args = new Bundle();
                    args.putInt("id", id_task);
                    ((ActivityMain)getActivity()).openFragment("task_previous",args);

                    Toast.makeText(
                            getActivity(),
                            ((TextView) v.findViewById(R.id.title))
                                    .getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }
        else{
            listView = (ListView) this.getActivity().findViewById(R.id.list);
            listView.setAdapter(new TaskContentAdapter(this.getActivity(),data));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    int id_task = 0;
                    try {
                        id_task = ((JSONObject)parent.getAdapter().getItem(position)).getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bundle args = new Bundle();
                    args.putInt("id", id_task);
                    ((ActivityMain)getActivity()).openFragment("task_previous",args);
                    Toast.makeText(
                            getActivity(),
                            ((TextView) v.findViewById(R.id.title))
                                    .getText(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}
