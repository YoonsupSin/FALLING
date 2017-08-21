package com.sys2017.android_app_test;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    CircularImageView circularImageView;
    TextView textView_memo;
    TextView textView_date;
    EditTextLetter editTextLetter;
    FloatingActionButton floatingActionButton;
    Boolean click = false;


    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/diarySaveDB.php";

    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        circularImageView = (CircularImageView) findViewById(R.id.circular_imageView);
        textView_memo = ( TextView ) findViewById(R.id.textView_memo);
        textView_date = ( TextView ) findViewById(R.id.textView_date);
        editTextLetter = ( EditTextLetter ) findViewById(R.id.editTextLetter_diary);
        floatingActionButton = ( FloatingActionButton ) findViewById(R.id.floating_save);

        Intent intent = getIntent();

        if ( intent != null ){
            id = intent.getStringExtra("id");
            String imgUrl = intent.getStringExtra("pic");
            String memo = intent.getStringExtra("memo");
            String date = intent.getStringExtra("date");

            Log.e("아이디",id.toString());

            Glide.with(this).load(imgUrl).into(circularImageView);
            textView_memo.setText(memo);
            textView_date.setText(date);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SecondActivity.this, "저장했어요.", Toast.LENGTH_SHORT).show();
                insertDiary();

            }
        });


    }



    public void clickBackground(View v){
        if ( click == false ){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            click = true;
        }
    }

    void insertDiary(){
        RequestQueue requestQueue = Volley.newRequestQueue(SecondActivity.this);
        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("데이터베이스",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("실패!!!","실패!!!");
            }
        });


        simpleMultiPartRequest.addStringParam("id",id.toString());
        simpleMultiPartRequest.addStringParam("diary",editTextLetter.getText().toString());

        requestQueue.add(simpleMultiPartRequest);




    }

}
