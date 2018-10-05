package com.honhai.foxconn.hometank.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.RectF;

public class MapPicture {

    private static Picture picture = new Picture();
    private static Paint one = new Paint();
    private static Paint two = new Paint();
    private static Path pathOne = new Path();
    private static Path pathtwo = new Path();
    private static int water = Color.rgb(0x46,0x71,0xa7);
    private static int sand = Color.rgb(0xc1,0xab,0x56);
    private static int pillar = Color.rgb(0x8b,0x39,0x2a);
    private static int pillarLine = Color.rgb(0x31,0x31,0x21);
    private static int road = Color.rgb(0x4b,0x4b,0x4b);
    private static float cliffEdge = .4f;

    public static Picture createMap(MapData[][] mapData , float interval){
        Canvas canvas = picture.beginRecording((int)interval*mapData.length,(int)interval*mapData[0].length);
        RectF rect;
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                rect = new RectF(i * interval, j * interval, (i + 1) * interval, (j + 1) * interval);
                switch (mapData[i][j]) {
                    case TEST_ROAD:
                        one.setColor(Color.CYAN);
                        canvas.drawRect(rect, one);
                        break;
                    case BRICK:
                        drawPillar(canvas,rect,interval);
                        break;
                    case SAND:
                        one.setColor(sand);
                        canvas.drawRect(rect, one);
                        break;
                    case ROAD_LINE_V:
                        drawRoadLineV(canvas,rect,interval);
                        break;
                    case ROAD_LINE_H:
                        drawRoadLineH(canvas,rect,interval);
                        break;
                    case ROAD_CROSS:
                        drawRoadCross(canvas,rect,interval);
                        break;
                    case ROAD_BEND_DR:
                        drawRoadBendDR(canvas,rect,interval);
                        break;
                    case ROAD_BEND_DL:
                        drawRoadBendDL(canvas,rect,interval);
                        break;
                    case ROAD_BEND_TL:
                        drawRoadBendTL(canvas,rect,interval);
                        break;
                    case ROAD_BEND_TR:
                        drawRoadBendTR(canvas,rect,interval);
                        break;
                    case ROAD_TRI_NT:
                        drawRoadTriNT(canvas,rect,interval);
                        break;
                    case ROAD_TRI_NR:
                        drawRoadTriNR(canvas,rect,interval);
                        break;
                    case ROAD_TRI_ND:
                        drawRoadTriND(canvas,rect,interval);
                        break;
                    case ROAD_TRI_NL:
                        drawRoadTriNL(canvas,rect,interval);
                        break;
                    case ROAD_END_RIGHT:
                        drawRoadEndRight(canvas,rect,interval);
                        break;
                    case ROAD_END_LEFT:
                        drawRoadEndLeft(canvas,rect,interval);
                        break;
                    case ROAD_END_TOP:
                        drawRoadEndTop(canvas,rect,interval);
                        break;
                    case ROAD_END_DOWN:
                        drawRoadEndDown(canvas,rect,interval);
                        break;
                    case RIVER_SEA_CENTER:
                        drawRiverSeaCenter(canvas,rect,interval);
                        break;
                    case RIVER_END_ALL:
                        drawRiverEndAll(canvas,rect,interval);
                        break;
                    case RIVER_LINE_V:
                        drawRiverLineV(canvas,rect,interval);
                        break;
                    case RIVER_LINE_H:
                        drawRiverLineH(canvas,rect,interval);
                        break;
                    case RIVER_BEND_DL:
                        drawRiverBendDL(canvas,rect,interval);
                        break;
                    case RIVER_BEND_DR:
                        drawRiverBendDR(canvas,rect,interval);
                        break;
                    case RIVER_BEND_TL:
                        drawRiverBendTL(canvas,rect,interval);
                        break;
                    case RIVER_BEND_TR:
                        drawRiverBendTR(canvas,rect,interval);
                        break;
                    case RIVER_END_RIGHT:
                        drawRiverEndRight(canvas,rect,interval);
                        break;
                    case RIVER_END_DOWN:
                        drawRiverEndDown(canvas,rect,interval);
                        break;
                    case RIVER_END_LEFT:
                        drawRiverEndLeft(canvas,rect,interval);
                        break;
                    case RIVER_END_TOP:
                        drawRiverEndTop(canvas,rect,interval);
                        break;
                    case RIVER_TRI_NT:
                        drawRiverTriNT(canvas,rect,interval);
                        break;
                    case RIVER_TRI_NR:
                        drawRiverTriNR(canvas,rect,interval);
                        break;
                    case RIVER_TRI_ND:
                        drawRiverTriND(canvas,rect,interval);
                        break;
                    case RIVER_TRI_NL:
                        drawRiverTriNL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TR:
                        drawRiverSeaTR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TL:
                        drawRiverSeaTL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DL:
                        drawRiverSeaDL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DR:
                        drawRiverSeaDR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NT:
                        drawRiverSeaNT(canvas,rect,interval);
                        break;
                    case RIVER_SEA_ND:
                        drawRiverSeaND(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NL:
                        drawRiverSeaNL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NR:
                        drawRiverSeaNR(canvas,rect,interval);
                        break;
                    case RIVER_CROSS:
                        drawRiverCross(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NDL:
                        drawRiverSeaNDL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NDR:
                        drawRiverSeaNDR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NTL:
                        drawRiverSeaNTL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NTR:
                        drawRiverSeaNTR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DRTL:
                        drawRiverSeaDRTL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DLTR:
                        drawRiverSeaDLTR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NDbutD:
                        drawRiverSeaNDbutD(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NLbutL:
                        drawRiverSeaNLbutL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NRbutR:
                        drawRiverSeaNRbutR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_NTbutT:
                        drawRiverSeaNTbutT(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DLbutTR:
                        drawRiverSeaDLbutTR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DRbutTL:
                        drawRiverSeaDRbutTL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TLbutDR:
                        drawRiverSeaTLbutDR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TRbutDL:
                        drawRiverSeaTRbutDL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DLbutR:
                        drawRiverSeaDLbutR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TLbutD:
                        drawRiverSeaTLbutD(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TRbutL:
                        drawRiverSeaTRbutL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DRbutT:
                        drawRiverSeaDRbutT(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DLbutT:
                        drawRiverSeaDLbutT(canvas,rect,interval);
                        break;
                    case RIVER_SEA_DRbutL:
                        drawRiverSeaDRbutL(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TLbutR:
                        drawRiverSeaTLbutR(canvas,rect,interval);
                        break;
                    case RIVER_SEA_TRbutD:
                        drawRiverSeaTRbutD(canvas,rect,interval);
                        break;
                }
            }
        }
        picture.endRecording();
        return picture;
    }

    private static void drawPillar(Canvas canvas, RectF rect, float interval) {
        one.setColor(pillar);
        two.setColor(pillarLine);
        two.setStrokeWidth(3);
        two.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,one);
        pathOne.reset();
        pathOne.moveTo(rect.left,(rect.top+rect.centerY())/2);
        pathOne.lineTo(rect.right,(rect.top+rect.centerY())/2);
        pathOne.moveTo(rect.left,rect.top);
        pathOne.lineTo(rect.right,rect.top);
        pathOne.moveTo(rect.left,rect.bottom);
        pathOne.lineTo(rect.right,rect.bottom);
        pathOne.moveTo(rect.left,(rect.bottom + rect.centerY())/2);
        pathOne.lineTo(rect.right,(rect.bottom + rect.centerY())/2);
        pathOne.moveTo(rect.left,rect.centerY());
        pathOne.lineTo(rect.right,rect.centerY());
        pathOne.moveTo((rect.left+rect.centerX())/2,rect.top);
        pathOne.lineTo((rect.left+rect.centerX())/2,(rect.top+rect.centerY())/2);
        pathOne.moveTo((rect.right+rect.centerX())/2,rect.top);
        pathOne.lineTo((rect.right+rect.centerX())/2,(rect.top+rect.centerY())/2);
        pathOne.moveTo(rect.centerX(),(rect.top+rect.centerY())/2);
        pathOne.lineTo(rect.centerX(),rect.centerY());
        pathOne.moveTo(rect.left,(rect.top + rect.centerY())/2);
        pathOne.lineTo(rect.left,rect.centerY());
        pathOne.moveTo(rect.right,(rect.top + rect.centerY())/2);
        pathOne.lineTo(rect.right,rect.centerY());
        pathOne.moveTo((rect.left+rect.centerX())/2,rect.top+rect.height()/2);
        pathOne.lineTo((rect.left+rect.centerX())/2,(rect.top+rect.centerY())/2+rect.height()/2);
        pathOne.moveTo((rect.right+rect.centerX())/2,rect.top+rect.height()/2);
        pathOne.lineTo((rect.right+rect.centerX())/2,(rect.top+rect.centerY())/2+rect.height()/2);
        pathOne.moveTo(rect.centerX(),(rect.top+rect.centerY())/2+rect.height()/2);
        pathOne.lineTo(rect.centerX(),rect.centerY()+rect.height()/2);
        pathOne.moveTo(rect.left,(rect.top + rect.centerY())/2+rect.height()/2);
        pathOne.lineTo(rect.left,rect.centerY()+rect.height()/2);
        pathOne.moveTo(rect.right,(rect.top + rect.centerY())/2+rect.height()/2);
        pathOne.lineTo(rect.right,rect.centerY()+rect.height()/2);
        canvas.save();
        canvas.clipRect(rect);
        canvas.drawPath(pathOne,two);
        canvas.restore();
        two.reset();
    }

    private static void drawRiverSeaTRbutD(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaTLbutR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDRbutL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(-90,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaTRbutL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDRbutT(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(-90,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaTLbutD(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaDLbutR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDLbutT(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaDLbutR(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaTRbutDL(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaTLbutDR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverSeaTRbutDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDRbutTL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaTRbutDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDLbutTR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaTRbutDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNTbutT(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaNDbutD(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNRbutR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverSeaNDbutD(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNLbutL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaNDbutD(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNDbutD(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaDLTR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaDRTL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDRTL(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaNTR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaNDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNTL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaNDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNDR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverSeaNDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNDL(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverCross(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaNR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverSeaNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaND(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaNT(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaDL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverSeaTR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaDR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverSeaTR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaTL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverSeaTR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverSeaTR(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverTriNL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverTriND(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverTriNR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverTriNT(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);

    }

    private static void drawRiverEndTop(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverEndLeft(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverEndDown(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverEndRight(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverBendDR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRiverBendDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverBendTR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRiverBendDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverBendTL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverBendDL(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverBendDL(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverLineH(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRiverLineV(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRiverLineV(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverEndAll(Canvas canvas, RectF rect, float interval) {
        drawRiverSeaCenter(canvas,rect,interval);
        one.setColor(sand);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-interval* cliffEdge,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.centerX()+interval* cliffEdge,rect.top,rect.right,rect.bottom,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-interval* cliffEdge,Path.Direction.CCW);
        pathOne.addRect(rect.left,rect.centerY()+interval* cliffEdge,rect.right,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,one);
    }

    private static void drawRiverSeaCenter(Canvas canvas, RectF rect, float interval) {
        one.setColor(water);
        canvas.drawRect(rect,one);
    }

    private static void drawRoadEndDown(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRoadEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadEndTop(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRoadEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadEndLeft(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRoadEndRight(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadEndRight(Canvas canvas, RectF rect, float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.save();
        canvas.rotate(-90,rect.centerX(),rect.centerY());
        canvas.drawRect(rect,two);
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-l*.04f,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,two);
    }

    private static void drawRoadTriNL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(270,rect.centerX(),rect.centerY());
        drawRoadTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadTriND(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRoadTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadTriNR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRoadTriNT(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadTriNT(Canvas canvas, RectF rect, float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.drawRect(rect,two);
        canvas.save();
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.3333f);
        }
        canvas.restore();
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-l*.04f,Path.Direction.CCW);
        canvas.drawPath(pathOne,two);
    }

    private static void drawRoadBendTR(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(-90,rect.centerX(),rect.centerY());
        drawRoadBendDR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadBendTL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(180,rect.centerX(),rect.centerY());
        drawRoadBendDR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadBendDL(Canvas canvas, RectF rect, float interval) {
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        drawRoadBendDR(canvas,rect,interval);
        canvas.restore();
    }

    private static void drawRoadBendDR(Canvas canvas, RectF rect, float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.drawRect(rect,two);
        canvas.save();
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.3333f);
        }
        canvas.restore();
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.centerX()-l*.04f,rect.bottom,Path.Direction.CCW);
        canvas.drawPath(pathOne,two);
        pathOne.reset();
        pathOne.addRect(rect.left,rect.top,rect.right,rect.centerY()-l*.04f,Path.Direction.CCW);
        canvas.drawPath(pathOne,two);
    }

    private static void drawRoadCross(Canvas canvas, RectF rect, float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.drawRect(rect,two);
        canvas.save();
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.3333f);
        }
        canvas.restore();
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
    }

    private static void drawRoadLineH(Canvas canvas, RectF rect, float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.save();
        canvas.rotate(90,rect.centerX(),rect.centerY());
        canvas.drawRect(rect,two);
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
    }

    private static void drawRoadLineV(Canvas canvas , RectF rect , float l) {
        one.setColor(Color.rgb(200,200,10));
        two.setColor(road);
        pathOne.reset();
        pathOne.addRect(-l*.04f,0,l*.04f,l*.26f,Path.Direction.CCW);
        canvas.save();
        canvas.drawRect(rect,two);
        canvas.translate(rect.left,rect.top);
        canvas.translate(l * .5f, l * .035f);
        for (int i = 0 ; i < 3 ; i++) {
            canvas.drawPath(pathOne,one);
            canvas.translate(0,l*.33f);
        }
        canvas.restore();
    }

}