package com.sys2017.android_app_test;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;

    TabLayout tabLayout;

    ImageView collapsing_image;

    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingActionButton01;
    FloatingActionButton floatingActionButton02;
    Boolean isFAB = false;
    Animation FAB_open, FAB_close, rotate_foward, rotate_backward;

    Fragment_Adapter fragment_adapter;

    ViewPager viewPager;


    final int PICK_FROM_ALBUM = 1;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor;

    String serverUrl = "http://imgenius0136.dothome.co.kr/imageUpload.php";
    Uri tmp_uri = null;
    String absolutePath;

    final int TIME_OUT = 20;
    final String POST_METHOD = "POST";

    String table;
    String wish;
    String date;
    String memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("page01").setTag("tag01"));
        tabLayout.addTab(tabLayout.newTab().setText("page02").setTag("tag02"));
        tabLayout.addTab(tabLayout.newTab().setText("page03").setTag("tag03"));

        collapsing_image = (ImageView)findViewById(R.id.collapsing_image);

        sharedPreferences= getSharedPreferences("datas", MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("serial",MODE_PRIVATE);

        String t_img=sharedPreferences.getString("title_image", "empty");
        table = sharedPreferences2.getString("serial","empty");

        Log.e("테이블",table);

        if ( !t_img.equals("empty") ){
            Uri uri= Uri.parse(t_img);
            Glide.with(this).load(uri.toString()).into(collapsing_image);
//            try {
//
//                Bitmap bm=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//
//                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
//                collapsing_image.setImageBitmap(bm);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
           // Glide.with(this).load(Integer.parseInt(sharedPreferences.getString("title_image",""))).into(collapsing_image);
        }
//        Glide.with(this).load(R.drawable.mjy_sys).into(collapsing_image);


        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating);
        floatingActionButton01 = (FloatingActionButton) findViewById(R.id.floating01);
        floatingActionButton02 = (FloatingActionButton) findViewById(R.id.floating02);
        FAB_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FAB_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_foward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_foward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_background);

        floatingActionButton.setOnClickListener(this);
        floatingActionButton01.setOnClickListener(this);
        floatingActionButton02.setOnClickListener(this);

        floatingActionButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermission();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_FROM_ALBUM);
            }
        });

        floatingActionButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(MainActivity.this,AddMemory.class);
                intent2.putExtra("table",table);
                startActivity(intent2);

            }
        });


        fragment_adapter = new Fragment_Adapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.pager);

        viewPager.setOnPageChangeListener(onPageChangeListener);

        viewPager.setAdapter(fragment_adapter);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK ){
            tmp_uri = data.getData();

            Glide.with(this).load(tmp_uri).into(collapsing_image);

            absolutePath = tmp_uri.toString();
            Toast.makeText(this, tmp_uri+"", Toast.LENGTH_SHORT).show();

            if ( absolutePath.contains("content://") ){
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(tmp_uri,null,null,null,null,null);
                if ( cursor != null && cursor.getColumnCount() != 0 ){
                    cursor.moveToFirst();
                    absolutePath = cursor.getString(cursor.getColumnIndex("_data"));
                }
            }

            Toast.makeText(this, absolutePath+"", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onClick(View v) {

        if( v.getId() == R.id.floating ){
            if ( isFAB == false ){
                floatingActionButton.startAnimation(rotate_foward);
                floatingActionButton01.startAnimation(FAB_open);
                floatingActionButton02.startAnimation(FAB_open);
                floatingActionButton01.setVisibility(View.VISIBLE);
                floatingActionButton02.setVisibility(View.VISIBLE);
                isFAB = true;
            }else{
                floatingActionButton.startAnimation(rotate_backward);
                floatingActionButton01.startAnimation(FAB_close);
                floatingActionButton02.startAnimation(FAB_close);
                floatingActionButton01.setVisibility(View.GONE);
                floatingActionButton02.setVisibility(View.GONE);
                isFAB = false;
            }

        }else if( v.getId() == R.id.floating01){

        }else{

        }

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if ( position == 0 ){
                if ( isFAB == true ){
                    isFAB = false;
                }
                FAB_open.setFillAfter(true);
                FAB_close.setFillAfter(true);
                rotate_foward.setFillAfter(true);
                rotate_backward.setFillAfter(true);
                floatingActionButton.setVisibility(View.VISIBLE);
            }

            if ( position == 1 ){
                FAB_open.setFillAfter(false);
                FAB_close.setFillAfter(false);
                rotate_foward.setFillAfter(false);
                rotate_backward.setFillAfter(false);
                floatingActionButton.setVisibility(View.GONE);
                floatingActionButton01.setVisibility(View.GONE);
                floatingActionButton02.setVisibility(View.GONE);
            }

            if ( position == 2 ) {
                FAB_open.setFillAfter(false);
                FAB_close.setFillAfter(false);
                rotate_foward.setFillAfter(false);
                rotate_backward.setFillAfter(false);
                floatingActionButton.setVisibility(View.GONE);
                floatingActionButton01.setVisibility(View.GONE);
                floatingActionButton02.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( requestCode ==10 ){
            if ( grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED ){
                Toast.makeText(this, "허가해야 볼수 있슴!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void BtnAdd(View v){
        Log.e("로그","로그");
    }



}
