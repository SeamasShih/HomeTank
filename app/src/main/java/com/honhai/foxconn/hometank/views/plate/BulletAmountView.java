package com.honhai.foxconn.hometank.views.plate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BulletAmountView extends View {

    private Path head = new Path();
    private Path body = new Path();
    private Path addPath = new Path();
    private Paint red;
    private Paint white;
    private Paint black;
    private float addTime = 100;
    private int amount = 4;

    public BulletAmountView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.FILL);
        red = new Paint();
        red.setColor(Color.RED);
        red.setAntiAlias(true);
        red.setStyle(Paint.Style.FILL);
        black = new Paint();
        black.setColor(Color.DKGRAY);
    }

    public void setAddTime(float addTime){
        this.addTime = addTime;
        invalidate();
    }

    public void setAmount(int amount) {
        this.amount = amount;
        invalidate();
    }

    private void setPath() {
        int w = getWidth()/12;
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

        addPath.reset();
        addPath.addRect(-w/2,-h*2.6f,w/2,h*2 - h*4.6f*addTime/100, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPath();
        canvas.translate(getWidth()*7/8,getHeight()*.6f);
        canvas.scale(1,.8f);
        for (int i = 0 ; i < amount ; i++){
            canvas.save();
            canvas.translate(0,-getHeight()/4);
            canvas.scale(1,1.4f);
            canvas.drawPath(head,red);
            canvas.restore();
            canvas.drawPath(body,white);
            canvas.translate(-getWidth()/4,0);
        }
        if (amount < 4){
            canvas.save();
            canvas.translate(0,-getHeight()/4);
            canvas.scale(1,1.4f);
            canvas.drawPath(head,red);
            canvas.restore();
            canvas.drawPath(body,white);
            canvas.drawPath(addPath,black);
        }

    }
}
