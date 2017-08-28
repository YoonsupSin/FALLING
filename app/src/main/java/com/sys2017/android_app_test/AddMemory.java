package com.sys2017.android_app_test;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class AddMemory extends AppCompatActivity{

    ImageView imageView;
    EditText editText;
    FloatingActionButton floatingActionButton;
    TextView textView;

    final int Add_PIC = 20;

    Uri tmp_uri;
    String path;
    String absolutePath = null;

    String saveDB = "http://imgenius0136.dothome.co.kr/FALLING/"+"saveDB.php";
    Intent intent;
    String table;
//    String saveIMAGE = "http://imgenius0136.dothome.co.kr/FALLING/imageUpload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);

        intent = getIntent();
        table = intent.getStringExtra("table");
        Log.e("테이블",table);
        imageView = ( ImageView ) findViewById(R.id.imageView_Pic);
        editText = ( EditText ) findViewById(R.id.editText_memo);
        floatingActionButton =( FloatingActionButton ) findViewById(R.id.floating_OK);
        textView = (TextView) findViewById(R.id.textView_Touch);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //volley
                RequestQueue requestQueue = Volley.newRequestQueue(AddMemory.this);
                SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, saveDB, new Response.Listener<String>() {
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

                Calendar calendar_current = Calendar.getInstance();

                String date = calendar_current.get(Calendar.YEAR)+"년-"+calendar_current.get(Calendar.MONTH)+"월-"+calendar_current.get(Calendar.DATE)+"일";

                simpleMultiPartRequest.addFile("upload",absolutePath);
                simpleMultiPartRequest.addStringParam("table",table);
                simpleMultiPartRequest.addStringParam("img","http://imgenius0136.dothome.co.kr/FALLING/"+Uri.parse(absolutePath).getLastPathSegment());
                simpleMultiPartRequest.addStringParam("memo",editText.getText().toString());
                simpleMultiPartRequest.addStringParam("date",date);

                requestQueue.add(simpleMultiPartRequest);


                Intent intent1 = new Intent(AddMemory.this,MainActivity.class);
                intent1.putExtra("img","http://imgenius0136.dothome.co.kr/FALLING/"+Uri.parse(absolutePath).getLastPathSegment());
                intent1.putExtra("memo",editText.getText().toString());
                intent1.putExtra("date",date);

                startActivity(intent1);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Add_PIC);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Add_PIC);
            }
        });

    }

//    class myThread extends Thread{
//        @Override
//        public void run() {
//
//            Calendar calendar_current = Calendar.getInstance();
//
//            String date = calendar_current.get(Calendar.YEAR)+"년-"+calendar_current.get(Calendar.MONTH)+"월-"+calendar_current.get(Calendar.DATE)+"일";
//
//            try {
//                URL url = new URL(saveDB);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setUseCaches(false);
//
//                final String data = "img="+absolutePath+"&memo="+editText.getText().toString()+"&date="+date;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(AddMemory.this, data.toString()+"", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(data.getBytes());
//
//                outputStream.flush();
//                outputStream.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                final StringBuffer stringBuffer = new StringBuffer();
//                String line = bufferedReader.readLine();
//
//                while (line != null){
//                    stringBuffer.append(line);
//                    line = bufferedReader.readLine();
//                }
//
//
////                Toast.makeText(AddMemory.this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("데이터",stringBuffer.toString());
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == Add_PIC && resultCode == RESULT_OK ){
            tmp_uri = data.getData();



            Glide.with(this).load(tmp_uri).into(imageView);

            absolutePath = tmp_uri.toString();

            String path = absolutePath;

            Toast.makeText(this, tmp_uri+"", Toast.LENGTH_SHORT).show();

            if ( absolutePath.contains("content://") ){
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(tmp_uri,null,null,null,null,null);
                if ( cursor != null && cursor.getColumnCount() != 0 ){
                    cursor.moveToFirst();
                    absolutePath = cursor.getString(cursor.getColumnIndex("_data"));

                }
            }


            textView.setVisibility(View.GONE);

        }


    }

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
}
