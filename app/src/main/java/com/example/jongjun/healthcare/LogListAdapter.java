package com.example.jongjun.healthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jongjun on 2015-06-04.
 */
public class LogListAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ExerciseLog> list;

    public LogListAdapter(Context context, ArrayList<ExerciseLog> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_list,parent,false);
        }
        ((TextView)view.findViewById(R.id.log_list_day_text)).setText(list.get(position).day);
        return view;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
