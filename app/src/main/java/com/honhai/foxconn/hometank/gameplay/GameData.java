package com.honhai.foxconn.hometank.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.honhai.foxconn.hometank.GameActivity;
import com.honhai.foxconn.hometank.collision.Box;
import com.honhai.foxconn.hometank.collision.BoxSet;
import com.honhai.foxconn.hometank.collision.CircleBox;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.BulletPicture;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeightTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.LightTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.TankPrototype;
import com.honhai.foxconn.hometank.map.MapData;
import com.honhai.foxconn.hometank.map.MapPicture;

import java.util.Arrays;

public class GameData {

    private final String TAG = "GameData";
    private static GameData gameData = new GameData();
    private BoxSet boxSet = new BoxSet();
    private MapData[][] mapData;
    private Player[] players;
    private ServerPlayerStatus[] newServerPlayers;
    private ServerPlayerStatus[] oldServerPlayers;
    private int myOrder = -1;
    private float interval = 120;
    private Bullet[] bullets = new Bullet[15];
    private Boom[] booms = new Boom[15];
    private BulletPicture bulletPicture = new BulletPicture();
    private float bulletL = interval * 2 / 5;
    private int bulletSite = 0;
    private int boomSite = 0;
    private Bitmap bitmap;
    private int boomL = (int) interval / 4;
    private Picture map = new Picture();
    private int mapW = 27;
    private int mapH = 19;
    private int roadNum = mapW * mapH / 3;
    private GameActivity activity;
    private MyAnimation receiveAnimation = new MyAnimation();

    public int getMyType() {
        return getMySelf().type;
    }

    private GameData(){
    }

    public void setPlayerInfoByServer(int order , float x , float y , float theta , float gunTheta , float gunLength){
        newServerPlayers[order].x = x;
        newServerPlayers[order].y = y;
        newServerPlayers[order].theta = theta;
        newServerPlayers[order].gunTheta = gunTheta;
        newServerPlayers[order].gunLength = gunLength;
    }

    public void startAnimation(){
        for (int i = 0 ; i < oldServerPlayers.length ; i++){
            oldServerPlayers[i].set(players[i]);
        }
        receiveAnimation = new MyAnimation();
        receiveAnimation.start();
    }

    public void stopAnimation(){
        receiveAnimation.halt();
    }

    private void setSiteByServer(float r) {
        for (int i = 0 ; i < players.length ; i++){
            if (i == myOrder) continue;
            players[i].setSiteByRate(r,oldServerPlayers[i], newServerPlayers[i]);
        }
    }

    public void setActivity(GameActivity activity) {
        this.activity = activity;
    }

    public void setMapData(MapData[][] data) {
        this.mapData = data;

        boxSet.setMapBox(mapData, interval);
        map = MapPicture.createMap(mapData, interval);
    }

    public float[] getMySite(){
        return new float[]{getMySelf().x , getMySelf().y};
    }

    public float getMyGunLength() {
        if (getMyType() == 2) {
            return getMySelf().getGunLength();
        }
        return 0.0f;
    }

    public void setGunLength(int order, float length) {
        getPlayer(order).setGunLength(length);
    }

    public static GameData getInstance() {
        return gameData;
    }

    public void createPlayers(int amount) {
        if (players != null)
            return;
        players = new Player[amount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        
        newServerPlayers = new ServerPlayerStatus[amount];
        for (int i = 0; i < newServerPlayers.length; i++) {
            newServerPlayers[i] = new ServerPlayerStatus();
        }

        oldServerPlayers = new ServerPlayerStatus[amount];
        for (int i = 0; i < oldServerPlayers.length; i++) {
            oldServerPlayers[i] = new ServerPlayerStatus();
        }
    }

    public float getMyTheta() {
        return getMySelf().theta;
    }

    public int getPlayerAmount() {
        return players.length;
    }

    public void setPlayerTankType(int order, int type) {
        getPlayer(order).setType(type);
    }

    public void setMyTankType(int type) {
        getMySelf().setType(type);
    }

    public void setMyOrder(int myOrder) {
        this.myOrder = myOrder;
    }

    public int getMyOrder() {
        return myOrder;
    }

    public float getX() {
        return getMySelf().x;
    }

    public float getY() {
        return getMySelf().y;
    }

    public void setMySite(float x, float y) {
        getMySelf().x = x;
        getMySelf().y = y;
    }

    public void setPlayersSite(int order, float x, float y, float theta) {
        getPlayer(order).set(x, y, theta);
    }

    public void setPlayersSite(int order, float x, float y) {
        getPlayer(order).set(x, y);
    }

    public void initialPlayerSite(int order, float x, float y) {
        getPlayer(order).set(x, y);
        oldServerPlayers[order].x = x;
        oldServerPlayers[order].y = y;
        newServerPlayers[order].x = x;
        newServerPlayers[order].y = y;
    }

    public void setPlayersSite(int order, float theta) {
        getPlayer(order).set(theta);
    }

    public void setPlayerAlive(int order , boolean isAlive){
        getPlayer(order).isAlive = isAlive;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void littleStepMySite() {
        getMySelf().littleStep();
    }

    public void littleStepRotationMySite() {
        getMySelf().littleStepRotation();
    }

    public void littleStepBackMySite() {
        getMySelf().littleBackStep();
    }

    public void littleBackStepRotationMySite() {
        getMySelf().littleBackStepRotation();
    }

    public Player getPlayer(int order) {
        if (order < players.length && order >= 0)
            return players[order];
        else
            return null;
    }

    public Box getPlayerBox(int order) {
        if (order < players.length && order >= 0)
            return players[order].box;
        else
            return null;
    }

    public Box getMyBox() {
        return getMySelf().box;
    }

    public long getMySpeed() {
        return getMySelf().speed;
    }

    public boolean checkCollision() {
        return boxSet.checkCollision(getMyBox());
    }

    public void offsetMyBox(float x, float y) {
        getMyBox().offset(x, y);
    }

    public void littleStepMyBox() {
        getMyBox().littleStep();
    }

    public void littleStepRotationMyBox() {
        getMyBox().littleStepRotation();
    }

    public void littleStepBackMyBox() {
        getMyBox().littleBackStep();
    }

    public void littleStepBackRotationMyBox() {
        getMyBox().littleStepBackRotation();
    }

    public void setMyBox(float x, float y) {
        getMyBox().set(x, y);
    }

    public void drawMap(Canvas canvas) {
        canvas.save();
        canvas.translate(-interval / 2, -interval / 2);
        canvas.translate(-getMySelf().x, -getMySelf().y);
        canvas.drawPicture(map);
        canvas.restore();
    }

    public void addBoom(float x, float y) {
        while (booms[boomSite] != null) {
            boomSite = (boomSite + 1) % booms.length;
        }
        booms[boomSite] = new Boom(x, y, boomSite);
    }

    public boolean amIAlive(){
        return getMySelf().isAlive;
    }

    public void gunRight() {
        getMySelf().gunRight();
    }

    public void gunLeft() {
        getMySelf().gunLeft();
    }

    public void setPlayerGunTheta(int order, float gunTheta) {
        getPlayer(order).gunTheta = gunTheta;
    }

    public int getMyLife(){
        return getMySelf().getLife();
    }

    public float getMyGunTheta() {
        return getMySelf().gunTheta;
    }

    public void drawBoom(Canvas canvas) {
        for (Boom boom : booms) {
            if (boom != null) {
                int dx = (int) (boom.x - getX());
                int dy = (int) (boom.y - getY());
                canvas.drawBitmap(bitmap,
                        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                        new Rect(dx - boomL, dy - boomL, dx + boomL, dy + boomL),
                        new Paint());
            }
        }

    }

    public void drawMyself(Canvas canvas) {
        getMySelf().draw(canvas);
    }

    public void drawTank(Canvas canvas) {
        Arrays.stream(players).forEach(player -> {
            player.draw(canvas);
        });
        if (getMySelf().type == 2)
            getMySelf().drawPrediction(canvas);
    }

    public void drawBullet(Canvas canvas) {
        for (Bullet bullet : bullets) {
            if (bullet != null) {
                float siteX = bullet.x - getX();
                float siteY = bullet.y - getY();
                canvas.save();
                canvas.rotate(bullet.box.theta, siteX, siteY);
                canvas.drawPicture(bulletPicture.getPicture(), new RectF(siteX - bulletL / 2, siteY - bulletL / 2, siteX + bulletL / 2, siteY + bulletL / 2));
                canvas.restore();
            }
        }
    }

    public void addBullet(Player player) {
        while (bullets[bulletSite] != null) {
            bulletSite = (bulletSite + 1) % bullets.length;
        }
        if (player.type == 2) {
            float[] f = new float[]{interval / 2 * 1.414f, 0};
            Matrix matrix = new Matrix();
            matrix.setRotate(player.gunTheta);
            matrix.mapPoints(f);

            float[] a = new float[]{(2 + (player.getGunLength() - 30) * 5 / 70) * interval, 0};
            matrix.mapPoints(a);

            bullets[bulletSite] = new Bullet(player.type, player.x + f[0], player.y + f[1],
                    System.currentTimeMillis(), player.x + a[0], player.y + a[1], player.gunTheta, bulletSite);
        } else {
            float[] f = new float[]{interval / 2 * 1.414f, 0};
            Matrix matrix = new Matrix();
            matrix.setRotate(player.gunTheta);
            matrix.mapPoints(f);

            bullets[bulletSite] = new Bullet(player.type, player.x + f[0], player.y + f[1],
                    System.currentTimeMillis(), player.gunTheta, bulletSite);
        }

    }

    public void addBullet(int playerType, float x, float y, long it, float gunLength, float gunTheta) {
        while (bullets[bulletSite] != null) {
            bulletSite = (bulletSite + 1) % bullets.length;
        }
        float[] f = new float[]{interval / 2 * 1.414f, 0};
        Matrix matrix = new Matrix();
        matrix.setRotate(gunTheta);
        matrix.mapPoints(f);

        float[] a = new float[]{(2 + (gunLength - 30) * 5 / 70) * interval, 0};
        matrix.mapPoints(a);

        bullets[bulletSite] = new Bullet(playerType, x + f[0], y + f[1],
                it, x + a[0], y + a[1], gunTheta, bulletSite);
    }

    public void addBullet(int playerType, float x, float y, long it, float gunTheta) {
        while (bullets[bulletSite] != null) {
            bulletSite = (bulletSite + 1) % bullets.length;
        }
        float[] f = new float[]{interval / 2 * 1.414f, 0};
        Matrix matrix = new Matrix();
        matrix.setRotate(gunTheta);
        matrix.mapPoints(f);

        bullets[bulletSite] = new Bullet(playerType, x + f[0], y + f[1],
                it, gunTheta, bulletSite);
    }

    public void gunRaise() {
        getMySelf().gunRaise();
    }

    public void gunLower() {
        getMySelf().gunLower();
    }

    public String getBulletInfo(int type) {
        Player p = getMySelf();
        if (type == 2) {
            return " " + p.type
                    + " " + p.x
                    + " " + p.y
                    + " " + p.getGunLength()
                    + " " + p.gunTheta;
        } else {
            return " " + p.type
                    + " " + p.x
                    + " " + p.y
                    + " " + p.gunTheta;
        }
    }

    public void nullBullet(int site) {
        bullets[site] = null;
    }

    public void nullBoom(int site) {
        booms[site] = null;
    }

    public Player getMySelf() {
        return getPlayer(myOrder);
    }

    private class Boom extends Thread {
        public float x;
        public float y;
        public int site;

        public Boom(float x, float y, int site) {
            this.x = x;
            this.y = y;
            this.site = site;
            start();
        }

        @Override
        public void run() {
            super.run();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nullBoom(site);
        }
    }

    private class Bullet extends Thread {
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
        public long heightSpeed = 1500;
        public float distance;
        private boolean go = true;
        public Box box = new Box(bulletL / 4, bulletL);

        public Bullet(int type, float ix, float iy, long it, float ax, float ay, float theta, int site) {
            this.type = type;
            this.ix = ix;
            this.iy = iy;
            switch (type) {
                case 0:
                    distance = interval * 3;
                    break;
                case 1:
                    distance = interval * 4;
                    break;
            }
            this.ax = ax;
            this.ay = ay;
            this.it = it;
            this.at = it + heightSpeed;
            this.site = site;
            this.box.theta = theta;
            this.start();
        }

        public Bullet(int type, float ix, float iy, long it, float theta, int site) {
            box.theta = theta;
            this.type = type;
            this.ix = ix;
            this.iy = iy;
            Matrix matrix = new Matrix();
            matrix.setRotate(theta);
            switch (type) {
                case 0:
                    distance = interval * 2.5f;
                    break;
                case 1:
                    distance = interval * 4;
                    break;
            }
            float[] f = new float[]{distance, 0};
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
            long ct;
            switch (type) {
                case 0:
                case 1:
                    ct = System.currentTimeMillis();
                    while (go && ct <= at) {
                        float r = ((float) (at - ct)) / ((float) speed);
                        x = r * ix + (1 - r) * ax;
                        y = r * iy + (1 - r) * ay;
                        box.set(x, y);
                        if (boxSet.checkBulletCollision(box)) {
                            go = false;
                        }
                        for (Player player : players) {
                            if (box.isCollision(player.box)) {
                                go = false;
                                break;
                            }
                        }
                        ct = System.currentTimeMillis();
                    }
                    if (getMyBox().checkCircleCollision(new CircleBox(x, y, boomL)))
                        getMySelf().beHurt(type == 0 ? 20 : 50);
                    addBoom(x, y);
                    nullBullet(site);
                    break;
                case 2:
                    ct = System.currentTimeMillis();
                    while (go && ct <= at) {
                        float r = ((float) (at - ct)) / ((float) heightSpeed);
                        x = r * ix + (1 - r) * ax;
                        y = r * iy + (1 - r) * ay;
                        box.set(x, y);

                        ct = System.currentTimeMillis();
                    }
                    if (getMyBox().checkCircleCollision(new CircleBox(x, y, boomL/3)))
                        getMySelf().beHurt(100);
                    else if (getMyBox().checkCircleCollision(new CircleBox(x, y, boomL)))
                        getMySelf().beHurt(30);
                    addBoom(x, y);
                    nullBullet(site);
                    break;

            }
        }
    }

    private class Player {

        public Box box = new Box(interval * .8f, interval * .6f);
        public float x;
        public float y;
        public float theta = 0;
        public float gunTheta = 0;
        public long speed = 10;
        public int type = 1;
        public float gunLength = 100;
        public TankPrototype tank = new HeavyTank();
        public int life = 100;
        public boolean isAlive = true;
        Paint paint = new Paint();
        Paint gunPaint = new Paint();
        Matrix matrix = new Matrix();

        public Player() {
            paint.setAntiAlias(true);
            paint.setColor(Color.argb(80, 255, 0, 0));
            gunPaint.setAntiAlias(true);
            gunPaint.setColor(Color.rgb(0x20, 0x6b, 0x20));
        }

        public void setSiteByRate(float r , ServerPlayerStatus o , ServerPlayerStatus n){
            x = ((100-r) * o.x + r * n.x) / 100;
            y = ((100-r) * o.y + r * n.y) / 100;
            theta = ((100-r) * o.theta + r * n.theta) / 100;
            gunTheta = ((100-r) * o.gunTheta + r * n.gunTheta) / 100;
            gunLength = ((100-r) * o.gunLength + r * n.gunLength) / 100;
        }

        public void beHurt(int damage) {
            life -= damage;
            if (life <= 0) {
                isAlive = false;
                life = 0;
                activity.iAmDead();
            }
            activity.setLife(life);
        }

        public int getLife(){
            return life;
        }

        public float getGunLength() {
            return gunLength;
        }

        public void setGunLength(float length) {
            gunLength = length;
        }

        public void gunRight() {
            gunTheta++;
            setMatrix();
        }

        public void gunLeft() {
            gunTheta--;
            setMatrix();
        }

        public void setType(int type) {
            this.type = type;
            switch (type) {
                case 0:
                    tank = new LightTank();
                    box = new Box(interval * .6f, interval * .5f);
                    box.set(x, y);
                    speed = 4;
                    break;
                case 1:
                    tank = new HeavyTank();
                    box = new Box(interval * .8f, interval * .6f);
                    box.set(x, y);
                    speed = 10;
                    break;
                case 2:
                    tank = new HeightTank();
                    box = new Box(interval * .6f, interval * .4f);
                    box.set(x, y);
                    speed = 7;
                    break;
            }
        }

        public void setSiteByBox() {
            x = box.centre.x;
            y = box.centre.y;
        }

        public void gunRaise() {
            if (gunLength >= 100) {
                gunLength = 100;
                return;
            }
            gunLength++;
        }

        public void gunLower() {
            if (gunLength <= 30) {
                gunLength = 30;
                return;
            }
            gunLength--;
        }

        public void setRotation() {
            theta = box.theta;
        }

        private void setMatrix() {
            matrix.setRotate(gunTheta);
        }

        public void offset(float x, float y) {
            this.x += x;
            this.y += y;
        }

        public void littleStep() {
            Matrix matrix = new Matrix();
            matrix.setRotate(theta);
            float[] p = new float[]{1, 0};
            matrix.mapPoints(p);
            offset(p[0], p[1]);
        }

        public void littleStepRotation() {
            theta++;
        }

        public void littleBackStep() {
            Matrix matrix = new Matrix();
            matrix.setRotate(theta);
            float[] p = new float[]{1, 0};
            matrix.mapPoints(p);
            offset(-p[0], -p[1]);
        }

        public void littleBackStepRotation() {
            theta--;
        }

        public void draw(Canvas canvas) {
            if (!this.isAlive) return;
            float dx = x - getX();
            float dy = y - getY();

            canvas.save();
            canvas.translate(dx, dy);
            canvas.rotate(theta);
            canvas.drawPicture(tank.getBasePicture(), new RectF(
                    -interval / 2, -interval / 2,
                    interval / 2, interval / 2));
            canvas.restore();
            canvas.save();
            canvas.translate(dx, dy);
            canvas.rotate(gunTheta);
            canvas.drawPicture(tank.getGunPicture(), new RectF(
                    -interval / 2, -interval / 2,
                    interval / 2, interval / 2));
            if (type == 2) {
                canvas.rotate(90);
                canvas.drawRect(-interval / 2*.02f, -interval / 2 * gunLength/100,
                        interval / 2 *.02f, 0,gunPaint);
            }
            canvas.restore();
        }

        public void drawPrediction(Canvas canvas){
            canvas.save();
            canvas.rotate(90+gunTheta);
            float l = (2 + (gunLength-30)*5/70)*interval;
            canvas.drawCircle(0,-l,boomL,paint);
            canvas.restore();
        }

        public void set(float x, float y, float theta) {
            this.x += x;
            this.y += y;
            box.set(x, y);
            this.theta = theta;
            box.theta = theta;
        }

        public void set(float x, float y) {
            this.x = x;
            this.y = y;
            box.set(x, y);
        }

        public void set(float theta) {
            this.theta = theta;
            box.theta = theta;
        }
    }
    
    private class ServerPlayerStatus{
        public float x;
        public float y;
        public float theta;
        public float gunTheta;
        public float gunLength;

        public void set(Player s){
            x = s.x;
            y = s.y;
            theta = s.theta;
            gunTheta = s.gunTheta;
            gunLength = s.gunLength;
        }
    }

    private class MyAnimation extends Thread{
        private float duration = 100;
        private long beginTime;
        private float r;
        private boolean go = true;

        @Override
        public void run() {
            r = getRate();
            while (go && r <= 100 && r >=0) {
                setSiteByServer(r);
                r = getRate();
            }
        }

        public void halt(){
            go = false;
        }

        private float getRate(){
            long time = System.currentTimeMillis();
            float delta = (float)(time - beginTime);
            if (delta > duration)
                return 101;
            return delta *100 / duration;
        }

        @Override
        public void start() {
            beginTime = System.currentTimeMillis();
            super.start();
        }
    }
}
