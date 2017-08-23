package com.sys2017.android_app_test;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WaitingActivity extends AppCompatActivity {

    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        myAsync myAsync = new myAsync();
        myAsync.execute();
    }

    class myAsync extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog = new ProgressDialog(WaitingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("기다려주세요...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setUseCaches(false);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
