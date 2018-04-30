package com.example.ninja.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ninja on 19/03/2018.
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<Message> arrayList = new ArrayList<>();
    Context ctx;
    public CustomAdapter(ArrayList<Message> arrayList,Context ctx) {
        this.arrayList = arrayList;
        this.ctx= ctx;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Message getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder =null;
        if (view==null){
            view = LayoutInflater.from(ctx).inflate(R.layout.row_layout,viewGroup,false);
            holder = new viewHolder(view);
            view.setTag(holder);
        }else{
            holder = (viewHolder) view.getTag();
        }
        Message message = arrayList.get(i);
        holder.sndrtx.setText(message.getSender());
        holder.conttx.setText(message.getContent());
        return view;
    }
    public static class viewHolder{
        TextView sndrtx,conttx;
        public viewHolder(View v){
            sndrtx = v.findViewById(R.id.sndr);
            conttx=v.findViewById(R.id.msg);
        }
    }
}
