package com.sys2017.android_app_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    Timer timer;
    SharedPreferences sharedPreferences;
    String serial;  boolean isSerial = false;
    String id;      boolean isId = false;
    String pass;    boolean isPass = false;
    String loadTable ="http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";

    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        sharedPreferences = getSharedPreferences("serial",MODE_PRIVATE);
        serial = sharedPreferences.getString("serial","");
        id = sharedPreferences.getString("id","");
        pass = sharedPreferences.getString("pass","");
        Log.e("프리페어",id);
        Log.e("프리페어",pass);
        Log.e("프리페어",serial);
        timer = new Timer();
        timer.schedule(timerTask,3000);



    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            if ( serial == "" || id == "" || pass == "" ){
                Intent intent = new Intent(IntroActivity.this,Intro2Activity.class);
                startActivity(intent);
                finish();
            }else {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(loadTable);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setUseCaches(false);
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);

                            InputStream inputStream = httpURLConnection.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            String line = bufferedReader.readLine();

                            while ( line != null ){
                                if ( line.contains(id) ){
                                    isId = true;
                                }
                                if ( line.contains(pass) ){
                                    isPass = true;
                                }
                                if ( line.contains(serial) ){
                                    isSerial = true;
                                    cnt++;
                                }
                                line = bufferedReader.readLine();
                            }

                            if ( cnt == 2 && isId && isPass && isSerial ){
                                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                                intent.putExtra("serial",serial);
                                startActivity(intent);
                                finish();
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    };



}
