package com.sys2017.android_app_test;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
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

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    CircularImageView circularImageView;
    TextView textView_memo;
    TextView textView_date;
    FloatingActionButton floatingActionButton;
    Boolean click = false;

    Timer timer;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        circularImageView = (CircularImageView) findViewById(R.id.circular_imageView);
        textView_memo = ( TextView ) findViewById(R.id.textView_memo);
        textView_date = ( TextView ) findViewById(R.id.textView_date);
        floatingActionButton = ( FloatingActionButton ) findViewById(R.id.floating_save);



        Intent intent = getIntent();

        if ( intent != null ){
            String imgUrl = intent.getStringExtra("pic");
            String memo = intent.getStringExtra("memo");
            String date = intent.getStringExtra("date");

            Glide.with(this).load(imgUrl).into(circularImageView);
            textView_memo.setText(memo);
            textView_date.setText(date);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.save,null);

                builder = new AlertDialog.Builder(getBaseContext());
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                timer = new Timer();
                timer.schedule(timerTask,1000);


            }
        });


    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            alertDialog.dismiss();
        }
    };

    public void clickBackground(View v){
        if ( click == false ){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            click = true;
        }
    }

}
