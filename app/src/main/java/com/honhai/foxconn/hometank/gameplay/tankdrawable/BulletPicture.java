package com.honhai.foxconn.hometank.gameplay.tankdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

public class BulletPicture {

    private Picture picture = new Picture();
    private Path head;
    private Path body;
    private Path base;
    private Paint red;
    private Paint white;
    private Paint black;
    private int d = 10;
    private int width = 100;
    private int height = 100;

    public BulletPicture(){
        recording();
    }

    public Picture getPicture(){
        return picture;
    }

    private void recording() {
        Canvas canvas = picture.beginRecording(width,height);

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

        int w = width/4;
        int h = height/4;
        h *= 1.4 ;
        head.reset();
        head.moveTo(w/2,0);
        head.arcTo(-w*3/2,-h,w/2,h,0,-60,false);
        head.arcTo(-w/2,-h,w*3/2,h,-120,-60,false);
        head.close();

        h = height/4;
        body.reset();
        body.addRect(-w/2,-h,w/2,h*2, Path.Direction.CCW);

        canvas.save();
        canvas.translate(width/2,height/2);
        canvas.rotate(90);

        canvas.save();
        canvas.translate(0,-height/4);
        canvas.scale(1,1.4f);
        canvas.drawPath(head,red);
        canvas.restore();

        canvas.drawPath(body,white);
        canvas.restore();
        picture.endRecording();
    }
}
