package com.honhai.foxconn.hometank.collision;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;

public class Box {

    public PointF leftTop = new PointF();
    public PointF rightTop = new PointF();
    public PointF leftBottom = new PointF();
    public PointF rightBottom = new PointF();
    public PointF centre = new PointF();
    public float theta;
    public float width;
    public float height;

    public Box(float width, float height){
        leftTop.set(-width/2,-height/2);
        rightTop.set(width/2,-height/2);
        leftBottom.set(-width/2,height/2);
        rightBottom.set(width/2,height/2);
        this.width = width;
        this.height = height;
    }

    public void offset(float x , float y){
        leftTop.offset(x, y);
        rightTop.offset(x, y);
        leftBottom.offset(x, y);
        rightBottom.offset(x, y);
        centre.offset(x,y);
    }

    public void littleStep(){
        Matrix matrix = new Matrix();
        matrix.setRotate(theta);
        float[] p = new float[]{1,0};
        matrix.mapPoints(p);
        offset(p[0],p[1]);
    }

    public void littleStepRotation(){
        theta++;
    }

    public void littleBackStep(){
        Matrix matrix = new Matrix();
        matrix.setRotate(theta);
        float[] p = new float[]{1,0};
        matrix.mapPoints(p);
        offset(-p[0],-p[1]);
    }

    public void littleStepBackRotation(){
        theta--;
    }

    public void set(float x , float y){
        leftTop.set(x-width/2, y-height/2);
        rightTop.set(x+width/2, y-height/2);
        leftBottom.set(x-width/2, y+height/2);
        rightBottom.set(x+width/2, y+height/2);
        centre.set(x,y);
    }

    public void rotation(float theta){
        this.theta = theta;
    }

    public float getLeftMin(){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta,centre.x,centre.y);
        float[] p1 = new float[]{leftTop.x,leftTop.y};
        float[] p2 = new float[]{rightTop.x,rightTop.y};
        float[] p3 = new float[]{leftBottom.x,leftBottom.y};
        float[] p4 = new float[]{rightBottom.x,rightBottom.y};
        matrix.mapPoints(p1);
        matrix.mapPoints(p2);
        matrix.mapPoints(p3);
        matrix.mapPoints(p4);
        float min = Math.min(p1[0],p2[0]);
        min = Math.min(min,p3[0]);
        min = Math.min(min,p4[0]);
        return min;
    }

    public float getTopMin(){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta,centre.x,centre.y);
        float[] p1 = new float[]{leftTop.x,leftTop.y};
        float[] p2 = new float[]{rightTop.x,rightTop.y};
        float[] p3 = new float[]{leftBottom.x,leftBottom.y};
        float[] p4 = new float[]{rightBottom.x,rightBottom.y};
        matrix.mapPoints(p1);
        matrix.mapPoints(p2);
        matrix.mapPoints(p3);
        matrix.mapPoints(p4);
        float min = Math.min(p1[1],p2[1]);
        min = Math.min(min,p3[1]);
        min = Math.min(min,p4[1]);
        return min;
    }

    public float getRightMax(){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta,centre.x,centre.y);
        float[] p1 = new float[]{leftTop.x,leftTop.y};
        float[] p2 = new float[]{rightTop.x,rightTop.y};
        float[] p3 = new float[]{leftBottom.x,leftBottom.y};
        float[] p4 = new float[]{rightBottom.x,rightBottom.y};
        matrix.mapPoints(p1);
        matrix.mapPoints(p2);
        matrix.mapPoints(p3);
        matrix.mapPoints(p4);
        float max = Math.max(p1[0],p2[0]);
        max = Math.max(max,p3[0]);
        max = Math.max(max,p4[0]);
        return max;
    }

    public float getBottomMax(){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta,centre.x,centre.y);
        float[] p1 = new float[]{leftTop.x,leftTop.y};
        float[] p2 = new float[]{rightTop.x,rightTop.y};
        float[] p3 = new float[]{leftBottom.x,leftBottom.y};
        float[] p4 = new float[]{rightBottom.x,rightBottom.y};
        matrix.mapPoints(p1);
        matrix.mapPoints(p2);
        matrix.mapPoints(p3);
        matrix.mapPoints(p4);
        float max = Math.max(p1[1],p2[1]);
        max = Math.max(max,p3[1]);
        max = Math.max(max,p4[1]);
        return max;
    }

    public boolean contain(PointF pointF){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta);
        float[] p = new float[]{pointF.x,pointF.y};
        matrix.mapPoints(p);
        return (leftTop.x != rightBottom.x || leftTop.y != rightBottom.y) &&
                p[0] > leftTop.x &&
                p[0] < rightBottom.x &&
                p[1] > leftTop.y &&
                p[1] < rightBottom.y;
    }

}
