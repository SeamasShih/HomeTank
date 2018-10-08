package com.honhai.foxconn.hometank.gameplay.tankdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

public class LightTank extends TankPrototype {
    private Picture BasePicture = new Picture();
    private Picture GunPicture = new Picture();

    public LightTank(){
        recordingBase();
        recordingGun();
    }

    private void recordingGun() {
        Canvas canvas = GunPicture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int r = 15;
        int l = 5;

        Path path = new Path();
        path.addCircle(0,0,r,Path.Direction.CCW);
        path.addRect(-l,-30,l,0, Path.Direction.CCW);

        canvas.translate(50,50);
        canvas.rotate(90);
        canvas.drawPath(path,paint);
        GunPicture.endRecording();
    }

    private void recordingBase() {
        Canvas canvas = BasePicture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x15,0x4b,0x14));
        paint.setAntiAlias(true);

        int h = 30;
        int w = 25;

        Path path = new Path();
        path.addRect(-w,-h,w,h, Path.Direction.CCW);
        path.addRect(-15,-h,15,-22,Path.Direction.CW);
        path.addRect(-15,22,15,h,Path.Direction.CW);
        path.addArc(-w-5,-10,-w+5,10,-90,180);
        path.addArc(w-5,-10,w+5,10,90,180);

        canvas.translate(50,50);
        canvas.rotate(90);
        canvas.drawPath(path,paint);
        BasePicture.endRecording();
    }

    @Override
    public Picture getBasePicture() {
        return BasePicture;
    }

    @Override
    public Picture getGunPicture() {
        return GunPicture;
    }
}
