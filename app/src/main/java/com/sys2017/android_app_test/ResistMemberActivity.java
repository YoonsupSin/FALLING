package com.sys2017.android_app_test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResistMemberActivity extends AppCompatActivity {

    EditText editText_id;
    EditText editText_pass;
    EditText editText_name;
    EditText editText_age;
    RadioGroup radioGroup;

    String id;
    String pass;

    String[] randNum = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    String[] arr = new String[10];
    String arr2 = "";

    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resist_member);

        editText_id = ( EditText ) findViewById(R.id.editView_id);
        editText_pass = ( EditText ) findViewById(R.id.editView_password);
        editText_name = ( EditText ) findViewById(R.id.editView_name);
        editText_age = ( EditText ) findViewById(R.id.editView_age);
        radioGroup = ( RadioGroup ) findViewById(R.id.gerderRADIOGROUP);

    }

    public void CheckID(View v){
        id = editText_id.getText().toString();
        checkID(id);
    }

    public void CheckPASSWORD(View v){
        pass = editText_pass.getText().toString();
        checkPASS(pass);
    }

    public void ClickNext(View v){
        Intent intent = new Intent(ResistMemberActivity.this,MainActivity.class);
        startActivity(intent);
    }

    void checkID(String id){
        myThread myThread = new myThread();
        myThread.start();
    }

    void checkPASS(String pass){
        if ( pass.length() <= 8 ){
            //TODO 이거하면됩니다!!!!
        }
    }

    class myThread extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                StringBuffer stringBuffer = new StringBuffer();

                while (line != null){
                    stringBuffer.append(line);
                    line = bufferedReader.readLine();
                }

                if ( !stringBuffer.toString().contains(id)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ResistMemberActivity.this,"OK", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
