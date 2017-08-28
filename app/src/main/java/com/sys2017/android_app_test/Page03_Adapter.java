package com.sys2017.android_app_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page03_Adapter extends RecyclerView.Adapter {

    ArrayList<WishListItem> items;
    Context context;

    public Page03_Adapter(ArrayList<WishListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.wishlist_item,parent,false);
        }

        return new Page03_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(items.get(position).getImg()).into(((ViewHolder)holder).imageView);
        ((ViewHolder)holder).textView_wish.setText(items.get(position).getWish().toString());
        ((ViewHolder)holder).textView_date.setText(items.get(position).getDate().toString());
        ((ViewHolder)holder).textView_memo.setText(items.get(position).getMemo().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_wish;
        TextView textView_date;
        TextView textView_memo;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = ( ImageView ) itemView.findViewById(R.id.imageView_wishList);
            textView_wish = ( TextView ) itemView.findViewById(R.id.textView_wishList_wish);
            textView_date = ( TextView ) itemView.findViewById(R.id.textView_wishList_date);
            textView_memo = ( TextView ) itemView.findViewById(R.id.textView_wishList_memo);

        }
    }
}
