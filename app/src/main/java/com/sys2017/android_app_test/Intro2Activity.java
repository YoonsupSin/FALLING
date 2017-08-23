package com.sys2017.android_app_test;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class Intro2Activity extends AppCompatActivity {

    Button button_resist;
    Button button_login;

    ImageView imageView_back;
    ViewPager viewPager;

    Fragment_Adapter3 fragment_adapter3;

    boolean confirm = false;
    boolean inputKey = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        button_resist = ( Button ) findViewById(R.id.button_Resist);
        button_login = ( Button ) findViewById(R.id.button_login);

        imageView_back = ( ImageView ) findViewById(R.id.imageView_confirm_back);
        viewPager = ( ViewPager ) findViewById(R.id.viewPager_confirm);
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
                    imageView_back.setImageResource(R.drawable.back00);
                }

                if ( position == 1 ){
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(500);
                    imageView_back.startAnimation(animation);
                    imageView_back.setImageResource(R.drawable.back02);
                }

                if ( position == 2 ){
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(500);
                    imageView_back.startAnimation(animation);
                    imageView_back.setImageResource(R.drawable.back03);
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
    }


    public void LoginMember(View v){

    }



}
