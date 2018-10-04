package com.honhai.foxconn.hometank.views.plate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LifeBarView extends View {

    private int life = 100;
    private int[] fullLifeColor = new int[]{0x10,0xE0,0x10};
    private int[] noLifeColor = new int[]{0xFF,0x00,0x00};
    private int[] nowLifeColor = new int[]{0x00,0xFF,0x00};
    private Paint white;

    public LifeBarView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);

        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.FILL);
    }

    public void setLife(int life) {
        this.life = life;
        float r = (float) life/100;
        for (int i = 0 ; i < noLifeColor.length ; i++){
            nowLifeColor[i] = (int) (fullLifeColor[i]*r + noLifeColor[i] *(1-r));
        }
        white.setColor(Color.rgb(nowLifeColor[0],nowLifeColor[1],nowLifeColor[2]));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth()*life/100,getHeight(),white);
    }
}
