package com.honhai.foxconn.hometank.views.tanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeightTank;

public class HeightTankView extends View {
    HeightTank heightTank = new HeightTank();

    public HeightTankView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPicture(heightTank.getBasePicture(),new Rect(0,0,getWidth(),getHeight()));
        canvas.drawPicture(heightTank.getGunPicture(),new Rect(0,0,getWidth(),getHeight()));
        canvas.drawPicture(heightTank.getPicture(),new Rect(0,0,getWidth(),getHeight()));
    }
}
