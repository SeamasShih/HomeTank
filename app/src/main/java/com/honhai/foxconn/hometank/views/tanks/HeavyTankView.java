package com.honhai.foxconn.hometank.views.tanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;

public class HeavyTankView extends View {

    HeavyTank heavyTank = new HeavyTank();

    public HeavyTankView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPicture(heavyTank.getBasePicture(),new Rect(0,0,getWidth(),getHeight()));
        canvas.drawPicture(heavyTank.getGunPicture(),new Rect(0,0,getWidth(),getHeight()));
    }
}
