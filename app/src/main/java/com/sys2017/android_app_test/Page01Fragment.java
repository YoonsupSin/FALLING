package com.sys2017.android_app_test;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Page01Fragment extends Fragment {

    ArrayList<AlbumItem> albumItems = new ArrayList<>();
    Page01_Adapter page01_adapter;
    RecyclerView recyclerView;

    int year;
    int month;
    int day;

    int start_Year = 2015;
    int start_Month = 12;
    int start_Day = 7;

    long start_day;
    long current_day;
    long result;

    TextView textView;

    String serverURL = "http://imgenius0136.dothome.co.kr/FALLING/loadDB.php";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumItems.add(new AlbumItem("http://imgenius0136.dothome.co.kr/FALLING/photo.jpg","안녕","호이"));

        MyThread myThread = new MyThread();
        myThread.start();

        page01_adapter = new Page01_Adapter(albumItems,getContext());

        Calendar calendar_start = Calendar.getInstance();   //시작하고 싶은 날짜!
        calendar_start.set(start_Year,start_Month-1,start_Day);

        Calendar calendar_current = Calendar.getInstance(); // 현재 날짜!

        start_day = calendar_start.getTimeInMillis();
        current_day = calendar_current.getTimeInMillis();
        result = (current_day-start_day)/(24*60*60*1000);


        year = calendar_current.get(Calendar.YEAR);
        month = calendar_current.get(Calendar.MONTH);
        day = calendar_current.get(Calendar.DAY_OF_MONTH);

        MainActivity mainActivity = ((MainActivity)getActivity());

        if ( mainActivity.getIntent() != null ){
            albumItems.add(new AlbumItem(mainActivity.getIntent().getStringExtra("img"),mainActivity.getIntent().getStringExtra("memo"),mainActivity.getIntent().getStringExtra("date")));
            page01_adapter.notifyDataSetChanged();
        }

    }

    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);

                InputStream inputStream = url.openStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                albumItems.clear();

                while (line!=null){
                    String[] row = line.split("&");


                    if ( row.length == 3 ){
                        String img = row[0];
                        String memo = row[1];
                        String date = row[2];

                        Log.e("img",img);
                        Log.e("img",memo);
                        Log.e("img",date);

                        albumItems.add(new AlbumItem(img,memo,date));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                page01_adapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(page01_adapter.getItemCount()-1);
                            }
                        });
                    }
                    line = bufferedReader.readLine();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page01_fragment,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(page01_adapter);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        textView = (TextView)view.findViewById(R.id.textView_fragment01_dday);
        textView.setText(result+"♥");

        return view;
    }


}
