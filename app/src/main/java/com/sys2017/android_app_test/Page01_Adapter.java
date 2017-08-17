package com.sys2017.android_app_test;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page01_Adapter extends RecyclerView.Adapter {

    ArrayList<AlbumItem> albumItems;
    Context context;

    String server = "http://imgenius0136.dothome.co.kr/FALLING/";

    public Page01_Adapter(ArrayList<AlbumItem> movies, Context context) {
        this.albumItems = movies;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_item,parent,false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((ViewHolder)holder).imageView.setImageResource(movies.get(position).getImg());
        if (albumItems.get(position).getImgUrl() != null) {

            Glide.with(context).load(albumItems.get(position).getImgUrl()).into(((ViewHolder)holder).imageView);

        }

//        ((ViewHolder)holder).textView_name.setText(movies.get(position).getName().toString());
        ((ViewHolder)holder).textView_date.setText(albumItems.get(position).getDate().toString());
        ((ViewHolder)holder).textView_memo.setText(albumItems.get(position).getMemo().toString());

//        ((ViewHolder)holder).textView_date.setText(movies.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return albumItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_date;
        TextView textView_memo;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_memo = (TextView) itemView.findViewById(R.id.textView_memo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SecondActivity.class);
                    intent.putExtra("pic",albumItems.get(getLayoutPosition()).getImgUrl());
                    intent.putExtra("memo",albumItems.get(getLayoutPosition()).getMemo());
                    intent.putExtra("date",albumItems.get(getLayoutPosition()).getDate());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)context,new Pair<View, String>(imageView,"pic"));
                        context.startActivity(intent,activityOptions.toBundle());
                    }else{
                        context.startActivity(intent);
                    }
                }
            });

        }


    }
}
