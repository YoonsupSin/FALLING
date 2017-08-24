package com.sys2017.android_app_test;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class ResistMemberActivity extends AppCompatActivity {

    EditText editText_id;
    EditText editText_pass;
    EditText editText_name;
    EditText editText_age;
    RadioGroup radioGroup;
    RadioButton radioButtonMen;
    RadioButton radioButtonWomen;
    RadioButton radioButtonTrans;

    RadioGroup radioGroup_alert;
    RadioButton radioButton_inputKey;
    RadioButton radioButton_create;
    TextView textView_alert;
    TextView textView_helper;
    EditText editText_inputKey;

    String id;      boolean checkIdOK = false;
    String pass;    boolean checkPassOK = false;
    String name;    boolean clickOK = false;
    String age;     boolean oneTime = false;
    String gender;

    int cnt = 0;

    boolean rndCreate = false;


    String[] randNum = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    String[] arr = new String[10];
    String arr2 = "";

    String serverUrl = "http://imgenius0136.dothome.co.kr/FALLING/loadDB_profile.php";
    String insertDB = "http://imgenius0136.dothome.co.kr/FALLING/insertDB_profile.php";
    String updateSerial = "http://imgenius0136.dothome.co.kr/FALLING/updateSerial.php";

    StringBuffer stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resist_member);

        editText_id = ( EditText ) findViewById(R.id.editView_id);
        editText_pass = ( EditText ) findViewById(R.id.editView_password);
        editText_name = ( EditText ) findViewById(R.id.editView_name);
        editText_age = ( EditText ) findViewById(R.id.editView_age);
        radioGroup = ( RadioGroup ) findViewById(R.id.gerderRADIOGROUP);
        radioButtonMen = ( RadioButton ) findViewById(R.id.radioMEN);
        radioButtonWomen = ( RadioButton ) findViewById(R.id.radioWOMEN);
        radioButtonTrans = ( RadioButton ) findViewById(R.id.radioTRANS);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if ( checkedId == R.id.radioMEN ){
                    gender = radioButtonMen.getText().toString();
                    Log.e("성별",gender);
                }

                if ( checkedId == R.id.radioWOMEN ){
                    gender = radioButtonWomen.getText().toString();
                    Log.e("성별",gender);
                }

                if ( checkedId == R.id.radioTRANS ){
                    gender = radioButtonTrans.getText().toString();
                    Log.e("성별",gender);
                }
            }
        });

    }

    public void CheckID(View v){
        id = editText_id.getText().toString();
        checkID(id);
    }

    public void CheckPASSWORD(View v){
        pass = editText_pass.getText().toString();
        checkPASS(pass);
    }

    public void ClickCancel(View v){

    }

    public void ClickOk(View v){
        if ( clickOK == false ){
            clickOK = true;

            Log.e("클릭ok","클릭ok");

            name = editText_name.getText().toString();
            age = editText_age.getText().toString();

            if (  checkIdOK == true && checkPassOK == true ){
                RequestQueue requestQueue = Volley.newRequestQueue(ResistMemberActivity.this);
                SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, insertDB, new Response.Listener<String>() {
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

                simpleMultiPartRequest.addStringParam("id",id);
                simpleMultiPartRequest.addStringParam("pass",pass);
                simpleMultiPartRequest.addStringParam("name",name);
                simpleMultiPartRequest.addStringParam("age",age);
                simpleMultiPartRequest.addStringParam("gender",gender);
                simpleMultiPartRequest.addStringParam("serial",arr2);
                requestQueue.add(simpleMultiPartRequest);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.resist_key,null);
                textView_alert = ( TextView ) view.findViewById(R.id.textView_alert);
                textView_helper = ( TextView ) view.findViewById(R.id.textView_helper);
                radioGroup = ( RadioGroup ) view.findViewById(R.id.radioGroup_alert);
                editText_inputKey = ( EditText ) view.findViewById(R.id.editText_inputKey);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if ( group.getCheckedRadioButtonId() == R.id.radioButton_create ){
                            editText_inputKey.setVisibility(View.GONE);
                            textView_alert.setVisibility(View.VISIBLE);
                            textView_alert.setText(arr2);
                        }

                        if ( group.getCheckedRadioButtonId() == R.id.radioButton_inputKey ){
                            textView_alert.setVisibility(View.GONE);
                            editText_inputKey.setVisibility(View.VISIBLE);
                            textView_helper.setText("인증키를 입력하세요!");
                        }
                    }
                });



                builder.setView(view);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ( radioGroup.getCheckedRadioButtonId() == 0 ){
                            Toast.makeText(ResistMemberActivity.this, "체크를 진행해주세요.", Toast.LENGTH_SHORT).show();
                        }

                        if ( radioGroup.getCheckedRadioButtonId() == R.id.radioButton_create ){
                            Toast.makeText(ResistMemberActivity.this, "인증키를 생성합니다.", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("serial",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id",id);
                            editor.putString("pass",pass);
                            editor.putString("serial",arr2);
                            editor.commit();

                            Intent intent = new Intent(ResistMemberActivity.this,WaitingActivity.class);
                            intent.putExtra("serial",arr2);
                            startActivity(intent);

                        }

                        if ( radioGroup.getCheckedRadioButtonId() == R.id.radioButton_inputKey ){

                            Toast.makeText(ResistMemberActivity.this, "인증키를 검사합니다.", Toast.LENGTH_SHORT).show();
                            RequestQueue requestQueue = Volley.newRequestQueue(ResistMemberActivity.this);
                            SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, updateSerial, new Response.Listener<String>() {
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
                            Log.e("이름",name);
                            simpleMultiPartRequest.addStringParam("name",name);
                            simpleMultiPartRequest.addStringParam("serial",editText_inputKey.getText().toString());
                            requestQueue.add(simpleMultiPartRequest);

                            SharedPreferences sharedPreferences = getSharedPreferences("serial",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id",id);
                            editor.putString("pass",pass);
                            editor.putString("serial",editText_inputKey.getText().toString());
                            editor.commit();

                            Intent intent = new Intent(ResistMemberActivity.this,WaitingActivity.class);
                            intent.putExtra("serial",editText_inputKey.getText().toString());
                            startActivity(intent);

                        }


                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }else {
                Toast.makeText(this, "중복확인을 진행해주세요!...", Toast.LENGTH_SHORT).show();
            }

        }


    }

    void checkID(final String id){

        new Thread(){
            @Override
            public void run() {
                try {

                    URL url = new URL(serverUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);

                    Log.e("들어왔따","들어왔따");

                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = bufferedReader.readLine();
                    stringBuffer = new StringBuffer();

                    while (line != null){
                        stringBuffer.append(line);
                        line = bufferedReader.readLine();
                    }

                    RandomNum();

                    if ( !stringBuffer.toString().contains(id)){
                        Log.e("오케이","오케이");
                        checkIdOK = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResistMemberActivity.this,"OK", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Log.e("노우","노우");
                        checkIdOK = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResistMemberActivity.this, "중복된 아이디이거나 아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    void checkPASS(String pass){

        if ( pass.length() >= 8 ){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            checkPassOK = true;
        }else{
            Toast.makeText(this, "비밀번호는 8글자 이상으로 지정해주세요!", Toast.LENGTH_SHORT).show();
            checkPassOK = false;
        }
    }





    void RandomNum(){

        Random random = new Random();
        for ( int i = 0 ; i < arr.length ; i ++ ){
            arr[i] = randNum[random.nextInt(randNum.length)];
            arr2 += arr[i];
        }



        if ( stringBuffer.toString().contains(arr2) ){
            RandomNum();
        }else {
            Log.e("시리얼",arr2.toString());
        }

    }


}
