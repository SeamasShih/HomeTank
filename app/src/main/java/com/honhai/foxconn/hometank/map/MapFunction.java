package com.honhai.foxconn.hometank.map;

public class MapFunction {
    public static boolean isRoad(MapData mapData){
        switch (mapData){
            case TEST_ROAD:
            case ROAD_BEND_TL:
            case ROAD_CROSS:
            case ROAD_LINE_H:
            case ROAD_LINE_V:
            case ROAD_TRI_NL:
            case ROAD_TRI_NT:
            case ROAD_TRI_ND:
            case ROAD_TRI_NR:
            case ROAD_BEND_DL:
            case ROAD_BEND_DR:
            case ROAD_BEND_TR:
            case ROAD_END_TOP:
            case ROAD_END_DOWN:
            case ROAD_END_LEFT:
            case ROAD_END_RIGHT:
                return true;

        }
        return false;
    }
}
