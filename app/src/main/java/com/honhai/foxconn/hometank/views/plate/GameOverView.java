package com.honhai.foxconn.hometank.views.plate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameOverView extends View {
    private String mText = "text";
    private Paint mPaint;
    private float adjY;

    public GameOverView(Context context , AttributeSet attributeSet) {
        super(context , attributeSet);
        mPaint = new Paint();
        mPaint.setTextSize(100);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.RED);
        Paint.FontMetrics fontMatrix = mPaint.getFontMetrics();
        adjY = (fontMatrix.bottom - fontMatrix.top)/2 - fontMatrix.bottom;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setColor(boolean win){
        if (win)
            mPaint.setColor(Color.RED);
        else
            mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText,getWidth()/2,getHeight()/2 + adjY,mPaint);
    }
}
