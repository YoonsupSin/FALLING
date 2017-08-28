package com.sys2017.android_app_test;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WishActivity extends AppCompatActivity {

    Button button_ok;
    Button button_cancel;

    EditText editText_wish;
    EditText editText_date;
    EditText editText_memo;

    TextView textView_wish;
    TextView textView_date;
    TextView textView_memo;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        setTitle("WishList");


        button_ok = ( Button ) findViewById(R.id.btn_wishOK);
        button_cancel = ( Button ) findViewById(R.id.btn_wishCANCEL);

        editText_wish = ( EditText ) findViewById(R.id.editText_wishList_wish);
        editText_date  = ( EditText ) findViewById(R.id.editText_wishList_date);
        editText_memo = ( EditText ) findViewById(R.id.editText_wishList_memo);

        textView_wish = ( TextView ) findViewById(R.id.textView_wishList_wish);
        textView_date  = ( TextView ) findViewById(R.id.textView_wishList_date);
        textView_memo = ( TextView ) findViewById(R.id.textView_wishList_memo);

        imageView = ( ImageView ) findViewById(R.id.imageView_wishList);

        textView_wish.setVisibility(View.GONE);
        textView_date.setVisibility(View.GONE);
        textView_memo.setVisibility(View.GONE);
        editText_wish.setVisibility(View.VISIBLE);
        editText_date.setVisibility(View.VISIBLE);
        editText_memo.setVisibility(View.VISIBLE);

    }

    public void ClickOK(View v){
        String wish = editText_wish.getText().toString();
        String date = editText_date.getText().toString();
        String memo = editText_memo.getText().toString();

        Intent intent_result = getIntent();
        intent_result.putExtra("wish",wish);
        intent_result.putExtra("date",date);
        intent_result.putExtra("memo",memo);
        setResult(20,intent_result);
        finish();

    }
    public void ClickCANCEL(View v){

    }
}
