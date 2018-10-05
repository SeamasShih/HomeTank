package com.honhai.foxconn.hometank.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;

import com.honhai.foxconn.hometank.collision.Box;
import com.honhai.foxconn.hometank.collision.BoxSet;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.BulletPicture;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.HeavyTank;
import com.honhai.foxconn.hometank.gameplay.tankdrawable.TankPrototype;
import com.honhai.foxconn.hometank.map.MapData;
import com.honhai.foxconn.hometank.map.MapFunction;
import com.honhai.foxconn.hometank.map.MapPicture;

import java.util.Arrays;

public class GameData {

    private final String TAG = "GameData";
    private static GameData gameData = new GameData();
    private BoxSet boxSet = new BoxSet();
    private MapData[][] mapData;
    //    private Player mine = new Player();
    private Player[] players;
    private int myOrder = -1;
    private float interval = 120;
    //    private Box myBox = new Box(interval*.8f,interval*.6f);
    private Bullet[] bullets = new Bullet[15];
    private Boom[] booms = new Boom[15];
    private BulletPicture bulletPicture = new BulletPicture();
    private float bulletL = interval * 2 / 5;
    private int bulletSite = 0;
    private int boomSite = 0;
    private Bitmap bitmap;
    private int boomL = (int) interval / 4;
    private Picture map = new Picture();


    private GameData() { }

    public void setMapData(MapData[][] data){
        this.mapData = data;

        boxSet.setMapBox(mapData, interval);
        map = MapPicture.createMap(mapData, interval);
    }

    private void initialTestMap() {
        mapData = new MapData[19][15];
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (i % 2 == 0 || j % 2 == 0)
                    mapData[i][j] = MapData.TEST_ROAD;
                else
                    mapData[i][j] = MapData.TEST_PILLAR;
            }
        }
        checkMapRoad(mapData);
        boxSet.setMapBox(mapData, interval);
        map = MapPicture.createMap(mapData, interval);
    }

    private void checkMapRoad(MapData[][] mapData) {
        int road;
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (!MapFunction.isRoad(mapData[i][j]))
                    continue;
                road = 0;
                if (i != 0 && MapFunction.isRoad(mapData[i - 1][j]))
                    road |= 0b0001; //left
                if (j != 0 && MapFunction.isRoad(mapData[i][j - 1]))
                    road |= 0b0010; // top
                if (i != mapData.length - 1 && MapFunction.isRoad(mapData[i + 1][j]))
                    road |= 0b0100; // right
                if (j != mapData[0].length - 1 && MapFunction.isRoad(mapData[i][j + 1]))
                    road |= 0b1000; // bottom
                switch (road) {
                    case 0b0001:
                        break;
                    case 0b0010:
                        break;
                    case 0b0100:
                        break;
                    case 0b1000:
                        break;
                    case 0b0011:
                        mapData[i][j] = MapData.ROAD_BEND_TL;
                        break;
                    case 0b0101:
                        mapData[i][j] = MapData.ROAD_LINE_H;
                        break;
                    case 0b1001:
                        mapData[i][j] = MapData.ROAD_BEND_DL;
                        break;
                    case 0b0111:
                        mapData[i][j] = MapData.ROAD_TRI_ND;
                        break;
                    case 0b1011:
                        mapData[i][j] = MapData.ROAD_TRI_NR;
                        break;
                    case 0b1101:
                        mapData[i][j] = MapData.ROAD_TRI_NT;
                        break;
                    case 0b1111:
                        mapData[i][j] = MapData.ROAD_CROSS;
                        break;
                    case 0b0110:
                        mapData[i][j] = MapData.ROAD_BEND_TR;
                        break;
                    case 0b1010:
                        mapData[i][j] = MapData.ROAD_LINE_V;
                        break;
                    case 0b1110:
                        mapData[i][j] = MapData.ROAD_TRI_NL;
                        break;
                    case 0b1100:
                        mapData[i][j] = MapData.ROAD_BEND_DR;
                        break;
                }
            }
        }
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
    }

    public float getMyTheta() {
        return getMySelf().theta;
    }

    public int getPlayerAmount() {
        return players.length;
    }

    public void setPlayerTankType(int type) {
        getMySelf().type = type;
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

    public void setPlayersSite(int order, float theta) {
        getPlayer(order).set(theta);
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

    public void gunRight() {
        getMySelf().gunTheta++;
    }

    public void gunLeft() {
        getMySelf().gunTheta--;
    }

    public void setPlayerGunTheta(int order, float gunTheta) {
        getPlayer(order).gunTheta = gunTheta;
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
        float[] f = new float[]{interval / 2 * 1.414f, 0};
        Matrix matrix = new Matrix();
        matrix.setRotate(player.gunTheta);
        matrix.mapPoints(f);

        bullets[bulletSite] = new Bullet(player.type, player.x + f[0], player.y + f[1],
                System.currentTimeMillis(), player.gunTheta, bulletSite);
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

    public String getBulletInfo() {
        Player p = getMySelf();
        return " " + p.type
                + " " + p.x
                + " " + p.y
                + " " + p.gunTheta;
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
        public float distance;
        private boolean go = true;
        public Box box = new Box(bulletL / 4, bulletL);

        public Bullet(int type, float ix, float iy, long it, float theta, int site) {
            box.theta = theta;
            this.type = type;
            this.ix = ix;
            this.iy = iy;
            Matrix matrix = new Matrix();
            matrix.setRotate(theta);
            switch (type) {
                case 0:
                    distance = interval * 3;
                    break;
                case 1:
                    distance = interval * 4;
                    break;
                case 2:
                    distance = interval * 7;
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
            long ct = System.currentTimeMillis();
            while (go && ct <= at) {
                float r = ((float) (at - ct)) / ((float) speed);
                x = r * ix + (1 - r) * ax;
                y = r * iy + (1 - r) * ay;
                box.set(x, y);
                if (boxSet.checkCollision(box)) {
                    go = false;
                }
                ct = System.currentTimeMillis();
            }
            addBoom(x, y);
            nullBullet(site);
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
        public TankPrototype tank = new HeavyTank();

        public void setSiteByBox() {
            x = box.centre.x;
            y = box.centre.y;
        }

        public void setRotation() {
            theta = box.theta;
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
}
