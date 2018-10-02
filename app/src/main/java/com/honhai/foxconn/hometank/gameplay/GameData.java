package com.honhai.foxconn.hometank.gameplay;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.honhai.foxconn.hometank.gameplay.tankdrawable.BulletPicture;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.TankPrototype;
import com.honhai.foxconn.hometank.map.MapData;
import com.honhai.foxconn.hometank.collision.Box;
import com.honhai.foxconn.hometank.collision.BoxSet;

import java.util.ArrayList;

public class GameData {

    private static GameData gameData = new GameData();
    private BoxSet boxSet = new BoxSet();
    private MapData[][] mapData;
    private Player mine = new Player();
    private float interval = 120;
    private Box myBox = new Box(interval*.8f,interval*.6f);
    private Bullet[] bullets = new Bullet[15];
    private BulletPicture bulletPicture = new BulletPicture();
    private float bulletL = interval*2/5;
    private int bulletSite = 0;


    private GameData(){

        initialTestMap();

        myBox.rotation(0);
    }

    private void initialTestMap(){
        mapData = new MapData[19][15];
        for (int i = 0 ; i < mapData.length ; i++){
            for (int j = 0 ; j < mapData[0].length ; j++) {
                if (i%2 == 0 || j%2 == 0)
                    mapData[i][j] = MapData.TEST_ROAD;
                else
                    mapData[i][j] = MapData.TEST_PILLAR;
            }
        }
        boxSet.setMapBox(mapData , interval);
    }

    public static GameData getInstance(){
        return gameData;
    }

    public float getX(){
        return mine.x;
    }

    public float getY(){
        return mine.y;
    }

    public void setMySite(float x , float y){
        mine.x = x;
        mine.y = y;
    }

    public void littleStepMySite(){
        mine.littleStep();
    }

    public void littleStepRotationMySite(){
        mine.littleStepRotation();
    }

    public void littleStepBackMySite(){
        mine.littleBackStep();
    }

    public void littleBackStepRotationMySite(){
        mine.littleBackStepRotation();
    }

    public long getMySpeed(){
        return mine.speed;
    }

    public boolean checkCollision(){
        return boxSet.checkCollision(myBox);
    }

    public void offsetMyBox(float x , float y){
        myBox.offset(x , y );
    }

    public void littleStepMyBox(){
        myBox.littleStep();
    }

    public void littleStepRotationMyBox(){
        myBox.littleStepRotation();
    }

    public void littleStepBackMyBox(){
        myBox.littleBackStep();
    }

    public void littleStepBackRotationMyBox(){
        myBox.littleStepBackRotation();
    }

    public void setMyBox(float x , float y){
        myBox.set(x , y );
    }

    public void drawMap(Canvas canvas) {
        canvas.save();
        canvas.translate(-interval/2,-interval/2);
        canvas.translate(-mine.x,-mine.y);
        Paint one = new Paint();
        one.setColor(Color.CYAN);
        Paint two = new Paint();
        two.setColor(Color.BLUE);
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                switch (mapData[i][j]) {
                    case TEST_ROAD:
                        canvas.drawRect(i * interval, j * interval, (i + 1) * interval, (j + 1) * interval, one);
                        break;
                    case TEST_PILLAR:
                        canvas.drawRect(i * interval, j * interval, (i + 1) * interval, (j + 1) * interval, two);
                        break;
                }
            }
        }
        canvas.restore();
    }

    public void gunRight(){
        mine.gunTheta++;
    }

    public void gunLeft(){
        mine.gunTheta--;
    }

    public void drawMyself(Canvas canvas) {
        mine.draw(canvas);
    }

    public void drawBullet(Canvas canvas){
        for (int i = 0 ; i < bullets.length ; i++){
            if (bullets[i] != null) {
                float siteX = bullets[i].x - getX();
                float siteY = bullets[i].y - getY();
                canvas.save();
                canvas.rotate(bullets[i].box.theta,siteX,siteY);
                canvas.drawPicture(bulletPicture.getPicture(),new RectF(siteX-bulletL/2,siteY-bulletL/2,siteX+bulletL/2,siteY+bulletL/2));
                canvas.restore();
            }
        }
    }

    public void addBullet(Player player){
        while (bullets[bulletSite] != null){
            bulletSite = (bulletSite +1) % bullets.length ;
        }
        float[] f = new float[]{interval/2*1.414f , 0};
        Matrix matrix = new Matrix();
        matrix.setRotate(player.gunTheta);
        matrix.mapPoints(f);
        bullets[bulletSite] = new Bullet(player.type , player.x+f[0] , player.y+f[1] ,
                System.currentTimeMillis() , player.gunTheta , bulletSite);
    }

    public void nullBullet(int site){
        bullets[site] = null;
    }

    public Player getMySelf(){
        return mine;
    }

    private class Bullet extends Thread{
        public int site;
        public float ix;
        public float iy;
        public float ax;
        public float ay;
        public float x;
        public float y;
        public int type;
        public long it;
        public long at;
        public long speed = 1000;
        public float distance;
        private boolean go = true;
        public Box box = new Box(bulletL/4,bulletL);

        public Bullet(int type ,float ix , float iy , long it ,float theta , int site){
            box.theta = theta;
            this.type = type;
            this.ix = ix;
            this.iy = iy;
            Matrix matrix = new Matrix();
            matrix.setRotate(theta);
            switch (type){
                case 0:
                    distance = interval*3;
                    break;
                case 1:
                    distance = interval*4;
                    break;
                case 2:
                    distance = interval*7;
                    break;
            }
            float[] f = new float[]{distance , 0};
            matrix.mapPoints(f);
            ax = ix + f[0];
            ay = iy + f[1];
            this.it = it;
            this.at = it + speed;
            this.site = site;
            this.start();
        }

        @Override
        public void run() {
            super.run();
            long ct = System.currentTimeMillis();
            while (go && ct <= at){
                float r = ((float) (at - ct)) / ((float) speed);
                x = r * ix + (1 - r) * ax;
                y = r * iy + (1 - r) * ay;
                box.set(x,y);
                if (boxSet.checkCollision(box)){
                    go = false;
                }
                ct = System.currentTimeMillis();
            }
            nullBullet(site);
        }
    }

    private class Player{

        public float x;
        public float y;
        public float theta = 0;
        public float gunTheta = 0;
        public long speed = 10;
        public int type = 1;
        public TankPrototype tank = new HeavyTank();

        public void offset(float x , float y){
            this.x += x;
            this.y += y;
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

        public void littleBackStepRotation(){
            theta--;
        }

        public void draw(Canvas canvas){
            canvas.save();
            canvas.rotate(theta+90);
            canvas.drawPicture(tank.getBasePicture(),new RectF(-interval/2,-interval/2,interval/2,interval/2));
            canvas.restore();
            canvas.save();
            canvas.rotate(gunTheta+90);
            canvas.drawPicture(tank.getGunPicture(),new RectF(-interval/2,-interval/2,interval/2,interval/2));
            canvas.restore();
        }
    }
}
