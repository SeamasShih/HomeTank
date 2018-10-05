package com.honhai.foxconn.hometank.map;

import java.io.Serializable;

public class Map implements Serializable {

    private MapData[][] mapData;

    private Map(int w, int h) {
        mapData = new MapData[h][w];
    }

    public static Map createTestMap() {
        Map map = new Map(200, 200);
        for (int i = 0; i < map.mapData.length; i++) {
            for (int j = 0; j < map.mapData[0].length; j++) {
                if ((i + 1) % 2 == 0 || (j + 1) % 2 == 0)
                    map.mapData[j][i] = MapData.TEST_ROAD;
                else
                    map.mapData[j][i] = MapData.TEST_PILLAR;
            }
        }
        return map;
    }

    public MapData getMapData(int x, int y) {
        return mapData[x][y];
    }

    public MapData[][] getMapData() {
        return mapData;
    }
}