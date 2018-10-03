package com.honhai.foxconn.hometank.views.keys;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class DownKey extends UpKey {
    public DownKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(180,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }
}
