package com.honhai.foxconn.hometank.collision;

import android.graphics.Matrix;

import com.honhai.foxconn.hometank.map.MapData;
import com.honhai.foxconn.hometank.map.MapFunction;

public class BoxSet {

    public Box[][] mapBox;
    public Box[] playerBox;
    public MapData[][] mapData;
    private float interval = 120;

    public void setMapBox(MapData[][] mapData , float interval) {
        this.mapData = mapData;
        mapBox = new Box[mapData.length][mapData[0].length];
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (MapFunction.isRiver(mapData[i][j])){
                    mapBox[i][j] = new Box(interval*.8f,interval*.8f);
                    mapBox[i][j].offset(i*interval,j*interval);
                }
                else if(mapData[i][j] == MapData.BRICK){
                    mapBox[i][j] = new Box(interval,interval);
                    mapBox[i][j].offset(i*interval,j*interval);
                }
            }
        }
    }

    public boolean checkCollision(Box box){
        return (onCheckWall(box) || onCheckMapBox(box));
    }

    public boolean checkBulletCollision(Box box){
        return (onCheckWall(box) || onCheckBulletMapBox(box));
    }

    private boolean onCheckWall(Box box){
        float l = box.getLeftMin();
        float t = box.getTopMin();
        float r = box.getRightMax();
        float b = box.getBottomMax();
        if (l < -interval/2 || r > interval*(mapBox.length) - interval/2 ||
                t < -interval/2 || b > interval*(mapBox[0].length) - interval/2)
            return true;
        return false;
    }

    private boolean onCheckBulletMapBox(Box box){
        int x = (int)((box.centre.x-interval/2)/interval);
        int y = (int)((box.centre.y-interval/2)/interval);
        for (int i = x-2 ; i <= x+2 ; i++){
            if (i < 0 || i >= mapBox.length) continue;
            for (int j = y-2 ; j <= y+2 ; j++){
                if (j < 0 || j >= mapBox[0].length || mapBox[i][j] == null ||
                        MapFunction.isRiver(mapData[i][j])) ;
                else if (onBoxCollision(box,mapBox[i][j])){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onCheckMapBox(Box box) {
        int x = (int)((box.centre.x-interval/2)/interval);
        int y = (int)((box.centre.y-interval/2)/interval);
        for (int i = x-2 ; i <= x+2 ; i++){
            if (i < 0 || i >= mapBox.length) continue;
            for (int j = y-2 ; j <= y+2 ; j++){
                if (j < 0 || j >= mapBox[0].length || mapBox[i][j] == null) ;
                else if (onBoxCollision(box,mapBox[i][j])){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onBoxCollision(Box box1 , Box box2){
        Matrix matrix1 = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrixF1 = new Matrix();
        Matrix matrixF2 = new Matrix();

        matrix1.setRotate(box1.theta,box1.centre.x,box1.centre.y);
        matrix2.setRotate(box2.theta,box2.centre.x,box2.centre.y);
        matrixF1.setRotate(-box1.theta);
        matrixF2.setRotate(-box2.theta);
        float[] p1 = new float[]{
                box1.leftTop.x,
                box1.leftTop.y,
                box1.rightTop.x,
                box1.rightTop.y,
                box1.leftBottom.x,
                box1.leftBottom.y,
                box1.rightBottom.x,
                box1.rightBottom.y
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

    public boolean contain(float[] b1 , float[] b2){
        return pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[0] , b2[1]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[2] , b2[3]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[4] , b2[5]) ||
                pointContain(b1[0] , b1[1] , b1[6] , b1[7] , b2[6] , b2[7]);
    }

    public boolean pointContain(float l , float t , float r , float b , float x , float y){
        return (x >= l) && (x <= r) && (y >= t) && (y <= b);
    }


}
