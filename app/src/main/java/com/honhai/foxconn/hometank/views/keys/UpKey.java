package com.honhai.foxconn.hometank.views.keys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class UpKey extends View {
    public UpKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
    }

    Path path;
    Paint white;

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void setPath() {
        int w = getWidth()/2;
        int h = getHeight()/2;
        path.reset();
        path.moveTo(0,-h);
        path.lineTo(-w,0);
        path.lineTo(w,0);
        path.close();
        path.addRect(-w/3,0,w/3,h, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setPath();

        canvas.save();
        canvas.translate(getWidth()/2,getHeight()*2/3);
        canvas.scale(0.5f,0.9f);
        canvas.drawPath(path,white);
        canvas.restore();
    }
}
