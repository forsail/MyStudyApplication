package com.example.administrator.mystudyapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/16.
 */

public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
