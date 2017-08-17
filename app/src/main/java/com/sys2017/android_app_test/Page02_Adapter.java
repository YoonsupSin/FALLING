package com.sys2017.android_app_test;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page02_Adapter extends RecyclerView.Adapter {

    ArrayList<item> items;
    Context context;

    public Page02_Adapter(ArrayList<item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.page02_item,parent,false);
        }

        return new Page02_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).textView_title.setText(items.get(position).getTitle().toString());
        ((ViewHolder)holder).textView_data.setText(items.get(position).getData().toString());
        ((ViewHolder)holder).textView_date.setText(items.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_title;
        ImageView imageView;
        TextView textView_data;
        TextView textView_date;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_title = (TextView)itemView.findViewById(R.id.textView_title);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_hello);
            textView_data = (TextView)itemView.findViewById(R.id.textView_data);
            textView_date = (TextView)itemView.findViewById(R.id.textView_date);

        }
    }
}
