package com.honhai.foxconn.hometank.gameplay.tankdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

public class HeavyTank extends TankPrototype {

    private Picture BasePicture = new Picture();
    private Picture GunPicture = new Picture();

    public HeavyTank(){
        recordingBase();
        recordingGun();
    }

    private void recordingGun() {
        Canvas canvas = GunPicture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int w = 20;
        int h = (int) (w/2*1.732);
        int l = 5;

        Path path = new Path();
        path.moveTo(-w,0);
        path.lineTo(-w/2,h);
        path.lineTo(w/2,h);
        path.lineTo(w,0);
        path.lineTo(w/2,-h);
        path.lineTo(-w/2,-h);
        path.close();
        path.addRect(-l,-50,l,0, Path.Direction.CCW);

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

        Path path = new Path();
        path.addRect(-30,-40,30,40, Path.Direction.CCW);
        path.moveTo(15,-40);
        path.lineTo(10,-30);
        path.lineTo(-10,-30);
        path.lineTo(-15,-40);
        path.close();
        path.moveTo(-10,30);
        path.lineTo(10,30);
        path.lineTo(15,40);
        path.lineTo(-15,40);
        path.close();

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
