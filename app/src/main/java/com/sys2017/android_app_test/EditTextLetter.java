package com.sys2017.android_app_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by N552VW on 2017-08-17.
 */

public class EditTextLetter extends android.support.v7.widget.AppCompatEditText {

    Rect rect;
    Paint paint;

    public EditTextLetter(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);

//        setMaxWidth();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cnt = getLineCount();
        for ( int i = 0 ; i < cnt ; i++ ){
            canvas.drawLine(rect.left,getLineBounds(i,rect)+10,rect.right,getLineBounds(i,rect)+10,paint);

        }
    }
}
