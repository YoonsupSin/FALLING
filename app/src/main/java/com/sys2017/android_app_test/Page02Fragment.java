package com.sys2017.android_app_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page02Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    Fragment_Adapter2 fragment_adapter2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity mainActivity = ((MainActivity)getActivity());
        fragment_adapter2 = new Fragment_Adapter2(mainActivity.getSupportFragmentManager());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page02_fragment,container,false);
        tabLayout = ( TabLayout ) view.findViewById(R.id.fragment2_tabLayout);
        viewPager = ( ViewPager ) view.findViewById(R.id.fragment2_viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("page01").setTag("tag01"));
        tabLayout.addTab(tabLayout.newTab().setText("page02").setTag("tag02"));
        tabLayout.addTab(tabLayout.newTab().setText("page03").setTag("tag03"));
        viewPager.setAdapter(fragment_adapter2);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }



}
