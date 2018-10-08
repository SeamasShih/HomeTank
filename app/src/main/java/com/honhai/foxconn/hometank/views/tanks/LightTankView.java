package com.honhai.foxconn.hometank.views.tanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.LightTank;

public class LightTankView extends View {

    LightTank lightTank = new LightTank();

    public LightTankView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPicture(lightTank.getBasePicture(),new Rect(0,0,getWidth(),getHeight()));
        canvas.drawPicture(lightTank.getGunPicture(),new Rect(0,0,getWidth(),getHeight()));
    }

}
