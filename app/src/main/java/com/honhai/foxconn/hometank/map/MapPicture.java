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
                    case TEST_PILLAR:
                        one.setColor(Color.BLUE);
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
                }
            }
        }
        picture.endRecording();
        return picture;
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
        two.setColor(Color.rgb(100,100,100));
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
        two.setColor(Color.rgb(100,100,100));
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
        two.setColor(Color.rgb(100,100,100));
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
        two.setColor(Color.rgb(100,100,100));
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
        two.setColor(Color.rgb(100,100,100));
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