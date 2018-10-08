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

    public boolean checkCircleCollision(CircleBox box){
        Matrix matrix = new Matrix();
        matrix.setRotate(-theta,centre.x,centre.y);
        float[] lt = new float[]{leftTop.x,leftTop.y};
        float[] rb = new float[]{rightBottom.x,rightBottom.y};
        float[] c = new float[]{box.x,box.y};
        matrix.mapPoints(lt);
        matrix.mapPoints(rb);
        matrix.mapPoints(c);
        if (c[0] < lt[0]){
            if (c[1] < lt[1])
                return box.contain(lt);
            else if (c[1] > rb[1])
                return box.contain(lt[0],rb[1]);
            else
                return Math.abs(c[0] - lt[0]) <= box.radius;
        }else if (c[0] > rb[0]){
            if (c[1] < lt[1])
                return box.contain(rb[0], lt[1]);
            else if (c[1] > rb[1])
                return box.contain(rb);
            else
                return Math.abs(c[0] - rb[0]) <= box.radius;

        }else if (c[1] < lt[1])
            return Math.abs(c[1] - lt[1]) <= box.radius;
        else if (c[1] > rb[1])
            return Math.abs(c[1] - rb[1]) <= box.radius;
        return true;
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

    public boolean contain(float[] b1 , float[] b2){
        return pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[0] , b2[1]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[2] , b2[3]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[4] , b2[5]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[6] , b2[7]);
    }

    public boolean pointContain(float l , float t , float r , float b , float x , float y){
        return (x >= l) && (x <= r) && (y >= t) && (y <= b);
    }

    public boolean isCollision(Box box2){
        Matrix matrix1 = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrixF1 = new Matrix();
        Matrix matrixF2 = new Matrix();

        matrix1.setRotate(this.theta,this.centre.x,this.centre.y);
        matrix2.setRotate(box2.theta,box2.centre.x,box2.centre.y);
        matrixF1.setRotate(-this.theta);
        matrixF2.setRotate(-box2.theta);
        float[] p1 = new float[]{
                this.leftTop.x,
                this.leftTop.y,
                this.rightTop.x,
                this.rightTop.y,
                this.leftBottom.x,
                this.leftBottom.y,
                this.rightBottom.x,
                this.rightBottom.y
        };
        float[] p2 = new float[]{
                box2.leftTop.x,
                box2.leftTop.y,
                box2.rightTop.x,
                box2.rightTop.y,
                box2.leftBottom.x,
                box2.leftBottom.y,
                box2.rightBottom.x,
                box2.rightBottom.y
        };
        matrix1.mapPoints(p1);
        matrix2.mapPoints(p2);
        float[] pF1 = new float[8];
        float[] pF2 = new float[8];
        matrixF1.mapPoints(pF1,p1);
        matrixF1.mapPoints(pF2,p2);
        if (contain(pF1,pF2))
            return true;
        matrixF2.mapPoints(pF1,p1);
        matrixF2.mapPoints(pF2,p2);
        return contain(pF2,pF1);
    }

}
