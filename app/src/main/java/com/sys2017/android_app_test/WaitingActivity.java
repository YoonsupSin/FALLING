package com.sys2017.android_app_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WaitingActivity extends AppCompatActivity {

    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";
    String createUrl = "http://imgenius0136.dothome.co.kr/FALLING/createTable.php";
    int cnt = 0;

    String table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        myAsync myAsync = new myAsync();
        myAsync.execute();

        Log.e("시리얼",getIntent().getStringExtra("serial"));

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

            Log.e("진행상황","1");

            Intent intent = getIntent();
            table = intent.getStringExtra("serial");
            Log.e("시리얼",table);

            try {
                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setUseCaches(false);

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();

                Log.e("진행상황","2");

                while ( line != null ){
                    Log.e("진행상황","2.5");
                    if ( line.contains(table) ){
                        cnt++;
                    }
                    if ( cnt == 2 ){
                        break;
                    }
                    line = bufferedReader.readLine();
                }

                Log.e("CNT",cnt+"");

                Log.e("진행상황","3");

                if ( cnt == 2 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(WaitingActivity.this);
                    SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, createUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("성공","성공"+response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("에러","에러");
                        }
                    });

                    simpleMultiPartRequest.addStringParam("table_name",getIntent().getStringExtra("serial"));
                    requestQueue.add(simpleMultiPartRequest);

                    Intent intent2 = new Intent(WaitingActivity.this,MainActivity.class);
                    intent2.putExtra("serial",table);
                    startActivity(intent2);

                }


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
