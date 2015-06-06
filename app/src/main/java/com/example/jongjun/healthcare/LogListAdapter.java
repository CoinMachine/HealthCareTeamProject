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
        if(convertView==null){
            view =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_list,parent,false);
        }
        ((TextView)view.findViewById(R.id.log_list_day_text)).setText(list.get(position).day);
        ((TextView)view.findViewById(R.id.log_list_weight_text)).setText(list.get(position).weight);
        //메모가 너무 길면 잘라서 보여준다.
        if(list.get(position).memo.length()>16) {
            ((TextView) view.findViewById(R.id.log_list_memo_text)).setText(String.copyValueOf(
                    list.get(position).memo.toCharArray(), 0, 15));
            ((TextView) view.findViewById(R.id.log_list_memo_text)).append("...");
        }else{
            ((TextView) view.findViewById(R.id.log_list_memo_text)).setText(list.get(position).memo);
        }
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
