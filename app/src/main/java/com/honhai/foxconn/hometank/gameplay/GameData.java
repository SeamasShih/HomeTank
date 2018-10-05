package com.honhai.foxconn.hometank.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
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

import java.util.ArrayList;
import java.util.Arrays;

public class GameData {

    private final String TAG = "GameData";
    private static GameData gameData = new GameData();
    private BoxSet boxSet = new BoxSet();
    private MapData[][] mapData;
    private Player[] players;
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
    private int roadNum = mapW*mapH/3;
    private enum GenerateMap{
        CONTAIN,
        INVALID,
        PASS
    }


    private GameData() {
//        initialMap();
//        createPlayers(1);
//        myOrder = 0;
//        getMySelf().set((mapW-1)/2*interval,(mapH-1)/2*interval);
    }

    private void initialMap() {
        mapData = new MapData[mapW][mapH];
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                mapData[i][j] = MapData.SAND;
            }
        }
        generateRoad();
        for (int i = 0 ; i < mapH*mapW/30 ; i++) {
            generateWater();
        }
        for (int i = 0 ; i < mapH*mapW/40 ; i++) {
            generatePillar();
        }
        checkMap(mapData);
        boxSet.setMapBox(mapData, interval);
        map = MapPicture.createMap(mapData, interval);
    }

    private void generatePillar() {
        ArrayList<Point> arrayList = new ArrayList<>();
        int x = getRandom(mapW);
        int y = getRandom(mapH);
        int rate = 5;
        for (int i = 0 ; i < 10 ; i++){
            if (!MapFunction.isRoad(mapData[x][y]) && !MapFunction.isPillar(mapData[x][y]))
                break;
            x = getRandom(mapW);
            y = getRandom(mapH);
        }
        mapData[x][y] = MapData.BRICK;
        arrayList.add(new Point(x,y));
        while (!arrayList.isEmpty()){
            Point now = arrayList.get(arrayList.size()-1);
            if (now.x != 0 && !MapFunction.isRoad(mapData[now.x-1][now.y]) &&
                    !MapFunction.isPillar(mapData[now.x-1][now.y]) && getRandom(10) > 10-rate) {
                mapData[now.x - 1][now.y] = MapData.BRICK;
                arrayList.add(new Point(now.x - 1,now.y));
            }
            if (now.y != 0 && !MapFunction.isRoad(mapData[now.x][now.y-1]) &&
                    !MapFunction.isPillar(mapData[now.x][now.y-1]) && getRandom(10) > 10-rate) {
                mapData[now.x][now.y-1] = MapData.BRICK;
                arrayList.add(new Point(now.x,now.y-1));
            }
            if (now.x != mapW-1 && !MapFunction.isRoad(mapData[now.x+1][now.y]) &&
                    !MapFunction.isPillar(mapData[now.x+1][now.y]) && getRandom(10) > 10-rate) {
                mapData[now.x + 1][now.y] = MapData.BRICK;
                arrayList.add(new Point(now.x + 1,now.y));
            }
            if (now.y != mapH-1 && !MapFunction.isRoad(mapData[now.x][now.y+1]) &&
                    !MapFunction.isPillar(mapData[now.x][now.y+1]) && getRandom(10) > 10-rate) {
                mapData[now.x][now.y+1] = MapData.BRICK;
                arrayList.add(new Point(now.x,now.y+1));
            }
            arrayList.remove(now);
        }
    }

    private void generateWater() {
        ArrayList<Point> arrayList = new ArrayList<>();
        int x = getRandom(mapW);
        int y = getRandom(mapH);
        int rate = 7;
        for (int i = 0 ; i < 20 ; i++){
            if (!MapFunction.isRoad(mapData[x][y]) && !MapFunction.isRiver(mapData[x][y]))
                break;
            x = getRandom(mapW);
            y = getRandom(mapH);
        }
        mapData[x][y] = MapData.RIVER_END_ALL;
        arrayList.add(new Point(x,y));
        while (!arrayList.isEmpty()){
            Point now = arrayList.get(arrayList.size()-1);
            if (now.x != 0 && !MapFunction.isRoad(mapData[now.x-1][now.y]) &&
                    !MapFunction.isRiver(mapData[now.x-1][now.y]) && getRandom(10) > 10-rate) {
                mapData[now.x - 1][now.y] = MapData.RIVER_END_ALL;
                arrayList.add(new Point(now.x - 1,now.y));
            }
            if (now.y != 0 && !MapFunction.isRoad(mapData[now.x][now.y-1]) &&
                    !MapFunction.isRiver(mapData[now.x][now.y-1]) && getRandom(10) > 10-rate) {
                mapData[now.x][now.y-1] = MapData.RIVER_END_ALL;
                arrayList.add(new Point(now.x,now.y-1));
            }
            if (now.x != mapW-1 && !MapFunction.isRoad(mapData[now.x+1][now.y]) &&
                    !MapFunction.isRiver(mapData[now.x+1][now.y]) && getRandom(10) > 10-rate) {
                mapData[now.x + 1][now.y] = MapData.RIVER_END_ALL;
                arrayList.add(new Point(now.x + 1,now.y));
            }
            if (now.y != mapH-1 && !MapFunction.isRoad(mapData[now.x][now.y+1]) &&
                    !MapFunction.isRiver(mapData[now.x][now.y+1]) && getRandom(10) > 10-rate) {
                mapData[now.x][now.y+1] = MapData.RIVER_END_ALL;
                arrayList.add(new Point(now.x,now.y+1));
            }
            arrayList.remove(now);
        }
    }

    private void generateRoad(){
        ArrayList<Point> arrayList = new ArrayList<>();
        arrayList.add(new Point((mapData.length-1)/2,(mapData[0].length-1)/2));
        int start;
        int lx = mapData.length;
        int ly = mapData[0].length;
        boolean isFirst = true;
        Point point;
        while (arrayList.size() <= roadNum){
            point = new Point();
            point.set(getRandom(lx),getRandom(ly));
            start = getRandom(arrayList.size());
            while (checkRoadPoint(arrayList,point) != GenerateMap.PASS){
                point.set(getRandom(lx),getRandom(ly));
            }
            if (isFirst) {
                firstGenerateRoadFromTo(arrayList, start, point);
                isFirst = false;
            }
            else
                generateRoadFromTo(arrayList, start, point);
        }
        arrayList.forEach(point1 -> mapData[point1.x][point1.y] = MapData.TEST_ROAD);
    }

    private boolean arrayListContain(ArrayList<Point> arrayList , Point point){
        int i = 0;
        while (i < arrayList.size()){
            if (arrayList.get(i).equals(point.x,point.y))
                return true;
        }
        return false;
    }

    private boolean arrayListContain(ArrayList<Point> arrayList , int x , int y){
        int i = 0;
        while (i < arrayList.size()){
            if (arrayList.get(i).equals(x,y))
                return true;
            i++;
        }
        return false;
    }

    private void generateRoadFromTo(ArrayList<Point> arrayList, int start, Point to) {
        Point aim = arrayList.get(start);
        Point from = new Point(to.x,to.y);
        ArrayList<Point> tempArray = new ArrayList<>();
        tempArray.add(to);
        boolean next = true;
        while (!arrayListContain(arrayList,from.x-1 , from.y) && !arrayListContain(arrayList,from.x+1 , from.y) &&
                !arrayListContain(arrayList,from.x , from.y-1) && !arrayListContain(arrayList,from.x , from.y+1)){
            Point point = new Point();

            switch (getRandom(2)){
                case 0: //horizon
                    if (from.x < aim.x) {
                        point =  new Point(from.x + 1, from.y);
                        next = addPointToArray(tempArray,point);
                    }
                    else if (from.x > aim.x) {
                        point =  new Point(from.x - 1, from.y);
                        next = addPointToArray(tempArray, point);
                    }
                    else {
                        if (from.x == mapData.length-1){
                            point =  new Point(from.x - 1, from.y);
                            next = addPointToArray(tempArray, point);
                        }
                        else if (from.x == 0){
                            point =  new Point(from.x + 1, from.y);
                            next = addPointToArray(tempArray, point);
                        }
                        else {
                            point =  new Point((getRandom(2) == 1 ? from.x + 1 : from.x - 1), from.y);
                            if (checkRoadPoint(arrayList,point) == GenerateMap.INVALID)
                                continue;
                            next = addPointToArray(tempArray, point);
                        }
                    }
                    break;
                case 1: // vertical
                    if (from.y < aim.y) {
                        point =  new Point(from.x, from.y+1);
                        next = addPointToArray(tempArray,point);
                    }
                    else if (from.y > aim.y) {
                        point =  new Point(from.x, from.y-1);
                        next = addPointToArray(tempArray, point);
                    }
                    else {
                        if (from.y == mapData[0].length-1){
                            point =  new Point(from.x, from.y-1);
                            next = addPointToArray(tempArray, point);
                        }
                        else if (from.y == 0){
                            point =  new Point(from.x, from.y+1);
                            next = addPointToArray(tempArray, point);
                        }
                        else {
                            point =  new Point(from.x, (getRandom(2) == 1 ? from.y + 1 : from.y - 1));
                            next = addPointToArray(tempArray, point);
                        }
                    }
                    break;
            }
            if (next)
                from = point;
        }
        arrayList.addAll(tempArray);
    }

    private GenerateMap checkRoadPoint(ArrayList<Point> arrayList , Point point){
        boolean tl = false;
        boolean tr = false;
        boolean bl = false;
        boolean br = false;
        boolean t = false;
        boolean l = false;
        boolean r = false;
        boolean b = false;
        for (int i = 0 ; i < arrayList.size() ; i++){
            if (arrayList.get(i).equals(point.x,point.y))
                return GenerateMap.CONTAIN;
            if (arrayList.get(i).equals(point.x-1,point.y-1))
                tl = true;
            else if (arrayList.get(i).equals(point.x+1,point.y-1))
                tr = true;
            else if (arrayList.get(i).equals(point.x-1,point.y+1))
                bl = true;
            else if (arrayList.get(i).equals(point.x+1,point.y+1))
                br = true;
            else if (arrayList.get(i).equals(point.x+1,point.y))
                r = true;
            else if (arrayList.get(i).equals(point.x-1,point.y))
                l = true;
            else if (arrayList.get(i).equals(point.x,point.y+1))
                b = true;
            else if (arrayList.get(i).equals(point.x,point.y-1))
                t = true;
        }
        if (tr && (t && r) || tl && (t && l) || bl && (b && l) || br && (b && r))
            return GenerateMap.INVALID;

        return GenerateMap.PASS;
    }

    private void firstGenerateRoadFromTo(ArrayList<Point> arrayList , int start , Point to) {
        Point from = arrayList.get(start);
        boolean next = true;
        while (!from.equals(to.x,to.y)){
            Point point = new Point();

            if (from.equals(to.x-1,to.y) ||from.equals(to.x+1,to.y) || from.equals(to.x,to.y-1) || from.equals(to.x,to.y+1)){
                arrayList.add(to);
                return;
            }

            switch (getRandom(2)){
                case 0: //horizon
                    if (from.x < to.x) {
                        point =  new Point(from.x + 1, from.y);
                        next = addPointToArray(arrayList,point);
                    }
                    else if (from.x > to.x) {
                        point =  new Point(from.x - 1, from.y);
                        next = addPointToArray(arrayList, point);
                    }
                    else {
                        if (from.x == mapData.length-1){
                            point =  new Point(from.x - 1, from.y);
                            next = addPointToArray(arrayList, point);
                        }
                        else if (from.x == 0){
                            point =  new Point(from.x + 1, from.y);
                            next = addPointToArray(arrayList, point);
                        }
                        else {
                            point =  new Point((getRandom(2) == 1 ? from.x + 1 : from.x - 1), from.y);
                            next = addPointToArray(arrayList, point);
                        }
                    }
                    break;
                case 1: // vertical
                    if (from.y < to.y) {
                        point =  new Point(from.x, from.y+1);
                        next = addPointToArray(arrayList,point);
                    }
                    else if (from.y > to.y) {
                        point =  new Point(from.x, from.y-1);
                        next = addPointToArray(arrayList, point);
                    }
                    else {
                        if (from.y == mapData[0].length-1){
                            point =  new Point(from.x, from.y-1);
                            next = addPointToArray(arrayList, point);
                        }
                        else if (from.y == 0){
                            point =  new Point(from.x, from.y+1);
                            next = addPointToArray(arrayList, point);
                        }
                        else {
                            point =  new Point(from.x, (getRandom(2) == 1 ? from.y + 1 : from.y - 1));
                            next = addPointToArray(arrayList, point);
                        }
                    }
                    break;
            }
            if (next)
                from = point;
        }
    }

    private boolean addPointToArray(ArrayList<Point> arrayList , Point point){
        switch (checkRoadPoint(arrayList , point)){
            case CONTAIN:
                return true;
            case PASS:
                arrayList.add(point);
                return true;
        }
        return false;
    }

    private int getRandom(int Max){
        int r = (int) (Max * Math.random());
        return r == Max ? r-1 : r;
    }

    private void initialTestMap() {
        mapData = new MapData[19][15];
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (i % 2 == 0 || j % 2 == 0)
                    mapData[i][j] = MapData.TEST_ROAD;
                else
                    mapData[i][j] = MapData.BRICK;
            }
        }
        checkMap(mapData);
        boxSet.setMapBox(mapData, interval);
        map = MapPicture.createMap(mapData, interval);
    }

    private void checkMap(MapData[][] mapData) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (MapFunction.isRoad(mapData[i][j]))
                    checkMapRoad(i,j);
                else if (MapFunction.isRiver(mapData[i][j]))
                    checkMapRiver(i,j);
            }
        }
    }

    private void checkMapRiver(int i, int j) {
        boolean tl = false;
        boolean tr = false;
        boolean bl = false;
        boolean br = false;
        boolean t = false;
        boolean l = false;
        boolean r = false;
        boolean b = false;
        if (i != 0 && MapFunction.isRiver(mapData[i - 1][j]))
            l = true;
        if (j != 0 && MapFunction.isRiver(mapData[i][j - 1]))
            t = true;
        if (i != mapData.length - 1 && MapFunction.isRiver(mapData[i + 1][j]))
            r = true;
        if (j != mapData[0].length - 1 && MapFunction.isRiver(mapData[i][j + 1]))
            b = true;
        if (t && l && MapFunction.isRiver(mapData[i-1][j-1]))
            tl = true;
        if (t && r && i != mapData.length - 1 && MapFunction.isRiver(mapData[i+1][j - 1]))
            tr = true;
        if (b && r && i != mapData.length - 1 && j != mapData[0].length - 1 && MapFunction.isRiver(mapData[i+1][j + 1]))
            br = true;
        if (b && l && j != mapData[0].length - 1 && MapFunction.isRiver(mapData[i-1][j + 1]))
            bl = true;
        if (tl && tr && br && bl)
            mapData[i][j] = MapData.RIVER_SEA_CENTER;
        else if (tl && tr && br)
            mapData[i][j] = MapData.RIVER_SEA_NDL;
        else if (tl && tr && bl)
            mapData[i][j] = MapData.RIVER_SEA_NDR;
        else if (tl && br && bl)
            mapData[i][j] = MapData.RIVER_SEA_NTR;
        else if (tl && tr)
            mapData[i][j] = (b ? MapData.RIVER_SEA_NDbutD : MapData.RIVER_SEA_ND);
        else if (tl && br)
            mapData[i][j] = MapData.RIVER_SEA_DRTL;
        else if (tl && bl)
            mapData[i][j] = (r ? MapData.RIVER_SEA_NRbutR : MapData.RIVER_SEA_NR);
        else if (tl && r && b)
            mapData[i][j] = MapData.RIVER_SEA_TLbutDR;
        else if (tl && r)
            mapData[i][j] = MapData.RIVER_SEA_TLbutR;
        else if (tl && b)
            mapData[i][j] = MapData.RIVER_SEA_TLbutD;
        else if (tl)
            mapData[i][j] = MapData.RIVER_SEA_TL;
        else if (tr && br && bl)
            mapData[i][j] = MapData.RIVER_SEA_NTL;
        else if (tr && br)
            mapData[i][j] = (l ? MapData.RIVER_SEA_NLbutL : MapData.RIVER_SEA_NL);
        else if (tr && bl)
            mapData[i][j] = MapData.RIVER_SEA_DLTR;
        else if (tr && b && l)
            mapData[i][j] = MapData.RIVER_SEA_TRbutDL;
        else if (tr && b)
            mapData[i][j] = MapData.RIVER_SEA_TRbutD;
        else if (tr && l)
            mapData[i][j] = MapData.RIVER_SEA_TRbutL;
        else if (tr)
            mapData[i][j] = MapData.RIVER_SEA_TR;
        else if (br && bl)
            mapData[i][j] = (t ? MapData.RIVER_SEA_NTbutT: MapData.RIVER_SEA_NT);
        else if (br && t && l)
            mapData[i][j] = MapData.RIVER_SEA_DRbutTL;
        else if (br && t)
            mapData[i][j] = MapData.RIVER_SEA_DRbutT;
        else if (br && l)
            mapData[i][j] = MapData.RIVER_SEA_DRbutL;
        else if (br)
            mapData[i][j] = MapData.RIVER_SEA_DR;
        else if (bl && t && r)
            mapData[i][j] = MapData.RIVER_SEA_DLbutTR;
        else if (bl && t)
            mapData[i][j] = MapData.RIVER_SEA_DLbutT;
        else if (bl && r)
            mapData[i][j] = MapData.RIVER_SEA_DLbutR;
        else if (bl)
            mapData[i][j] = MapData.RIVER_SEA_DL;
        else if (l && t && r && b)
            mapData[i][j] = MapData.RIVER_CROSS;
        else if (l && t && r)
            mapData[i][j] = MapData.RIVER_TRI_ND;
        else if (l && t && b)
            mapData[i][j] = MapData.RIVER_TRI_NR;
        else if (l && r && b)
            mapData[i][j] = MapData.RIVER_TRI_NT;
        else if (l && t)
            mapData[i][j] = MapData.RIVER_BEND_TL;
        else if (l && r)
            mapData[i][j] = MapData.RIVER_LINE_H;
        else if (l && b)
            mapData[i][j] = MapData.RIVER_BEND_DL;
        else if (l)
            mapData[i][j] = MapData.RIVER_END_LEFT;
        else if (t && r && b)
            mapData[i][j] = MapData.RIVER_TRI_NL;
        else if (t && r)
            mapData[i][j] = MapData.RIVER_BEND_TR;
        else if (t && b)
            mapData[i][j] = MapData.RIVER_LINE_V;
        else if (t)
            mapData[i][j] = MapData.RIVER_END_TOP;
        else if (r && b)
            mapData[i][j] = MapData.RIVER_BEND_DR;
        else if (r)
            mapData[i][j] = MapData.RIVER_END_RIGHT;
        else if (b)
            mapData[i][j] = MapData.RIVER_END_DOWN;
    }

    private void checkMapRoad(int i , int j){
        int road = 0;
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
                mapData[i][j] = MapData.ROAD_END_LEFT;
                break;
            case 0b0010:
                mapData[i][j] = MapData.ROAD_END_TOP;
                break;
            case 0b0100:
                mapData[i][j] = MapData.ROAD_END_RIGHT;
                break;
            case 0b1000:
                mapData[i][j] = MapData.ROAD_END_DOWN;
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
                if (boxSet.checkBulletCollision(box)) {
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
