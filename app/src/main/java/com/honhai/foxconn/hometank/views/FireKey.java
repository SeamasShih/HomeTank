package com.honhai.foxconn.hometank.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FireKey extends View {
    public FireKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        head = new Path();
        body = new Path();
        base = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.FILL);
        red = new Paint();
        red.setColor(Color.RED);
        red.setAntiAlias(true);
        red.setStyle(Paint.Style.FILL);
        black = new Paint();
        black.setColor(Color.LTGRAY);
        black.setAntiAlias(true);
        black.setStyle(Paint.Style.STROKE);
        black.setStrokeWidth(d);
    }

    Path head;
    Path body;
    Path base;
    Paint red;
    Paint white;
    Paint black;
    int d = 10;

    private void setPath() {
        int w = getWidth()/4;
        int h = getHeight()/4;
        h *= 1.4 ;
        head.reset();
        head.moveTo(w/2,0);
        head.arcTo(-w*3/2,-h,w/2,h,0,-60,false);
        head.arcTo(-w/2,-h,w*3/2,h,-120,-60,false);
        head.close();

        h = getHeight()/4;
        body.reset();
        body.addRect(-w/2,-h,w/2,h*2, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setPath();

        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(-45);

        canvas.save();
        canvas.translate(0,-getHeight()/4);
        canvas.scale(1,1.4f);
        canvas.drawPath(head,red);
        canvas.restore();

        canvas.drawPath(body,white);
        canvas.restore();
    }
}
