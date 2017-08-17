package com.sys2017.android_app_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

    ArrayList<item> items = new ArrayList<>();
    Page02_Adapter page02_adapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page02_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        page02_adapter = new Page02_Adapter(items,getContext());
        recyclerView.setAdapter(page02_adapter);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        ReadRSS();

        return view;
    }

    public void ReadRSS(){

        try {
            URL url = new URL("http://rss.ohmynews.com/rss/travel.xml");

            RSSFeedTask rssFeedTask = new RSSFeedTask();
            rssFeedTask.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    class RSSFeedTask extends AsyncTask<URL,Void,String>{
        

        //로딩..메소드?
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {

            URL url = params[0];

            try {
                XmlPullParserFactory factor = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factor.newPullParser();

                InputStream inputStream = url.openStream();
                xmlPullParser.setInput(inputStream,"UTF-8");
                int eventType = xmlPullParser.next();

                String tag = "";
                String text = "";

                item item = null;


                while (eventType != XmlPullParser.START_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            tag = xmlPullParser.getName();
                            if ( tag.equals("item") ){
                                item = new item("","","");
                            }else if( tag.equals("pubDate") ){
                                xmlPullParser.next();
                                if ( item != null )item.setDate(xmlPullParser.getText());
                            }else if( tag.equals("description") ){
                                xmlPullParser.next();
                                if ( item != null )item.setData(xmlPullParser.getText());
                            }else if( tag.equals("title") ){
                                xmlPullParser.next();
                                if ( item != null )item.setTitle(xmlPullParser.getText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tag = xmlPullParser.getName();
                            if ( tag.equals("item")){
                                items.add(item);
                                publishProgress();
                                item = null;
                            }
                            break;
                    }
                    eventType = xmlPullParser.next();
                }//while

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "종료..";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            page02_adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Snackbar.make(recyclerView,s,Snackbar.LENGTH_SHORT).show();
        }
    }


}
