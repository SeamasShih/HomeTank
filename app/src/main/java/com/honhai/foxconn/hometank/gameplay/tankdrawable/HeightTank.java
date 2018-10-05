package com.honhai.foxconn.hometank.gameplay.tankdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

public class HeightTank extends TankPrototype {
    private Picture BasePicture = new Picture();
    private Picture GunPicture = new Picture();
    private Picture picture = new Picture();

    public HeightTank(){
        recordingBase();
        recordingGun();
        recording();
    }

    private void recording() {
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int w = 2;

        canvas.translate(50,50);
        canvas.rotate(90);
        canvas.drawRect(-w,-50,w,0,paint);
    }

    private void recordingGun() {
        Canvas canvas = GunPicture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int w = 15;
        int h = 8;
        int m = 8;

        Path path = new Path();
        path.addRect(-w,-h,w,h,Path.Direction.CW);
        path.addRect(-m,-h,m,0,Path.Direction.CCW);

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

        int h = 60;
        int w = 40;
        int k = 8;
        int l = 20;
        int m = 5;

        Path path = new Path();
        path.addRect(-w/2,-h/2,w/2,h/2, Path.Direction.CW);
        path.moveTo(-w/2,-h/2+m);
        path.lineTo(-w/2+(w-l)/2,-h/2);
        path.lineTo(-w/2,-h/2);
        path.close();
        path.addRect(-w/2+(w-l)/2,-h/2,w/2-(w-l)/2,-h/2+k,Path.Direction.CCW);
        path.moveTo(w/2,-h/2);
        path.lineTo(w/2-(w-l)/2,-h/2);
        path.lineTo(w/2,-h/2+m);
        path.close();
        path.moveTo(-w/2,h/2);
        path.lineTo(-w/2+(w-l)/2,h/2);
        path.lineTo(-w/2,h/2-m);
        path.close();
        path.addRect(-w/2+(w-l)/2,h/2-k,w/2-(w-l)/2,h/2,Path.Direction.CCW);
        path.moveTo(w/2,h/2);
        path.lineTo(w/2,h/2-m);
        path.lineTo(w/2-(w-l)/2,h/2);
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

    public Picture getPicture() {
        return picture;
    }
}
