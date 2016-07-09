package com.boxtade.b_thinker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by kvins on 05/07/2016.
 */
public class TaskContentAdapter extends BaseAdapter {

    private Context context;
    private JSONArray tasks;

    public TaskContentAdapter(Context context,JSONArray tasks) throws JSONException {

        this.context = context;
        this.tasks = tasks;
        for (int i = 0;i<tasks.length();i++)
        {
            Log.e("Data task",tasks.getJSONObject(i).getString("task"));
        }
    }

    @Override
    public int getCount() {
        return tasks.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return tasks.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        try{
            return tasks.getJSONObject(position).getLong("id");
        }
        catch (JSONException je){
            Log.e("JSON",je.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        Log.e("Position", String.valueOf(position));
        if (convertView == null) {
            // get layout from mobile.xml
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.task_content, null);
        }
        else {
            view = convertView;
        }

        String temp = "";

        try {
            temp = tasks.getJSONObject(position).getString("task");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String []data = temp.split("[*][/][*]");
        Log.d("Data 0",data[0]);
        Log.d("Data 1",data[1]);

        // set value into textview
        TextView title = (TextView)  view
                .findViewById(R.id.title);

        if(data[0] == ""){
            title.setMaxLines(0);
        }
        else{
            title.setMaxLines(1);
            title.setText(data[0]);
        }

        TextView task = (TextView)  view
                .findViewById(R.id.task);
        task.setText(data[1]);

        return  view;
    }
}
