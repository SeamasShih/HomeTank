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

    public static boolean isPillar(MapData mapData){
        switch (mapData){
            case BRICK:
                return true;
        }
        return false;
    }

    public static boolean isRiver(MapData mapData){
        switch (mapData){
            case RIVER_CROSS:
            case RIVER_LINE_H:
            case RIVER_LINE_V:
            case RIVER_SEA_DL:
            case RIVER_SEA_DR:
            case RIVER_SEA_ND:
            case RIVER_SEA_NL:
            case RIVER_SEA_NR:
            case RIVER_SEA_NT:
            case RIVER_SEA_TL:
            case RIVER_SEA_TR:
            case RIVER_TRI_ND:
            case RIVER_TRI_NL:
            case RIVER_TRI_NR:
            case RIVER_TRI_NT:
            case RIVER_BEND_DL:
            case RIVER_BEND_DR:
            case RIVER_BEND_TL:
            case RIVER_BEND_TR:
            case RIVER_END_ALL:
            case RIVER_END_TOP:
            case RIVER_END_DOWN:
            case RIVER_END_LEFT:
            case RIVER_END_RIGHT:
            case RIVER_SEA_CENTER:
            case RIVER_SEA_TLbutD:
            case RIVER_SEA_DLbutT:
            case RIVER_SEA_DLbutR:
            case RIVER_SEA_DRbutTL:
            case RIVER_SEA_NTL:
            case RIVER_SEA_NRbutR:
            case RIVER_SEA_NDbutD:
            case RIVER_SEA_NDL:
            case RIVER_SEA_NDR:
            case RIVER_SEA_NTR:
            case RIVER_SEA_DLTR:
            case RIVER_SEA_DRTL:
            case RIVER_SEA_DRbutL:
            case RIVER_SEA_DRbutT:
            case RIVER_SEA_NLbutL:
            case RIVER_SEA_NTbutT:
            case RIVER_SEA_TLbutR:
            case RIVER_SEA_TRbutD:
            case RIVER_SEA_TRbutL:
            case RIVER_SEA_DLbutTR:
            case RIVER_SEA_TLbutDR:
            case RIVER_SEA_TRbutDL:
                return true;
        }
        return false;
    }
}
