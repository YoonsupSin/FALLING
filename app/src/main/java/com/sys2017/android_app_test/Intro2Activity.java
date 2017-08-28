package com.sys2017.android_app_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Intro2Activity extends AppCompatActivity {

    Button button_resist;
    Button button_login;
    Button cardView_btn_login;
    Button cardView_btn_cancel;
    EditText cardView_editText_id;
    EditText cardView_editText_pass;

    ImageView imageView_back;
    ViewPager viewPager;

    Fragment_Adapter3 fragment_adapter3;

    boolean confirm = false;
    boolean inputKey = false;

    CardView cardView;

    boolean isLogin = false;

    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        button_resist = ( Button ) findViewById(R.id.button_Resist);
        button_login = ( Button ) findViewById(R.id.button_login);

        imageView_back = ( ImageView ) findViewById(R.id.imageView_confirm_back);
        viewPager = ( ViewPager ) findViewById(R.id.viewPager_confirm);

        cardView = ( CardView ) findViewById(R.id.cardView_login);
        cardView_btn_login = ( Button ) findViewById(R.id.btn_login);
        cardView_btn_cancel = ( Button ) findViewById(R.id.btn_cancel);
        cardView_editText_id = ( EditText ) findViewById(R.id.editText_id);
        cardView_editText_pass = ( EditText ) findViewById(R.id.editText_pass);
        fragment_adapter3 = new Fragment_Adapter3(getSupportFragmentManager());
        viewPager.setAdapter(fragment_adapter3);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ( position == 0 ){
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(500);
                    imageView_back.startAnimation(animation);
                    imageView_back.setImageResource(R.drawable.viewpage00);
                }

                if ( position == 1 ){
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(500);
                    imageView_back.startAnimation(animation);
                    imageView_back.setImageResource(R.drawable.viewpage01);
                }

                if ( position == 2 ){
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(500);
                    imageView_back.startAnimation(animation);
                    imageView_back.setImageResource(R.drawable.viewpage02);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void ResistMember(View v){
        Intent intent = new Intent(Intro2Activity.this,ResistMemberActivity.class);
        startActivity(intent);
        finish();
    }


    public void LoginMember(View v){
        if ( isLogin == false ){
            isLogin = true;
            button_login.setClickable(false);
            button_resist.setClickable(false);
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(300);
            cardView.startAnimation(animation);
            cardView.setVisibility(View.VISIBLE);

        }
    }

    public void LoginClick(View v){
        final String id = cardView_editText_id.getText().toString();
        final String pass = cardView_editText_pass.getText().toString();

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);

                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = bufferedReader.readLine();

                    while (line != null){
                        if ( line.contains(id) && line.contains(pass) ){
                            String[] data = line.split("&");
                            String serial = data[6];
                            Log.e("serial",serial+"입니다");

                            SharedPreferences sharedPreferences2 = getSharedPreferences("serial",MODE_PRIVATE);
                            sharedPreferences2.edit().putString("id",id).commit();
                            sharedPreferences2.edit().putString("pass",pass).commit();
                            sharedPreferences2.edit().putString("serial",serial).commit();

                            Intent intent = new Intent(Intro2Activity.this,MainActivity.class);
                            startActivity(intent);

                            break;
                        }
                        line = bufferedReader.readLine();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void CancelClick(View v){
        isLogin = false;
        button_login.setClickable(true);
        button_resist.setClickable(true);
        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(300);
        cardView.startAnimation(animation);
        cardView.setVisibility(View.GONE);
    }

}
