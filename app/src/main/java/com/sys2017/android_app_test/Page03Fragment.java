package com.sys2017.android_app_test;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page03Fragment extends Fragment {

    Button button_add;
    RecyclerView recyclerView;
    Page03_Adapter page03_adapter;
    ArrayList<WishListItem> wishListItems = new ArrayList<>();

    String wish;
    String date;
    String memo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page03_fragment, container, false);

        Log.e("위시",((MainActivity)getActivity()).wish+((MainActivity)getActivity()).date+((MainActivity)getActivity()).memo+"입니다");

        button_add = (Button) view.findViewById(R.id.btn_addWishList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_wishList);
        page03_adapter = new Page03_Adapter(wishListItems, getContext());

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(page03_adapter);


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WishActivity.class);
                startActivityForResult(intent, 20);
//                recyclerView.setAdapter(page03_adapter);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == 20 ){
            wish = data.getStringExtra("wish");
            date = data.getStringExtra("date");
            memo = data.getStringExtra("memo");

            wishListItems.add(new WishListItem("",wish,date,memo));
            page03_adapter.notifyDataSetChanged();
            Log.e("로그그그그",wish+date+memo+"입니다");

        }

    }
}
