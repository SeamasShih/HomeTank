package com.honhai.foxconn.hometank.views.plate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LifeBarView extends View {

    int life = 100;
    Paint white = new Paint();

    public LifeBarView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);

        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth(),getHeight()*100/life,white);
    }
}
