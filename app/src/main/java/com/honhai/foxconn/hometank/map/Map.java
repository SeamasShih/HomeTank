package com.honhai.foxconn.hometank.map;

import java.io.Serializable;

public class Map implements Serializable {

    private MapData[][] mapData;

    private Map(int w, int h) {
        mapData = new MapData[h][w];
    }

    public MapData getMapData(int x, int y) {
        return mapData[x][y];
    }

    public MapData[][] getMapData() {
        return mapData;
    }
}