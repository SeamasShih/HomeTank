package com.honhai.foxconn.hometank.views.keys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RaiseKey extends View {
    public RaiseKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.STROKE);
        white.setStrokeWidth(d);
    }

    Path path;
    Paint white;
    int d = 10;

    private void setPath() {
        int w = getWidth()/2;
        int h = getHeight()/2;
        path.reset();
        path.moveTo(-w/2,h/6);
        path.lineTo(0,-h/6);
        path.lineTo(w/2,h/6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setPath();

        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawPath(path,white);
        canvas.translate(0,d*2);
        canvas.drawPath(path,white);
        canvas.translate(0,-d*4);
        canvas.drawPath(path,white);
        canvas.restore();
    }
}
