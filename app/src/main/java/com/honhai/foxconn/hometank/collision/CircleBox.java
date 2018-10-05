package com.honhai.foxconn.hometank.collision;

public class CircleBox {
    public float x;
    public float y;
    public float radius;

    public CircleBox(float x , float y , float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean contain(float x , float y){
        return (float) Math.sqrt((x-this.x)*(x-this.x) + (y-this.y)*(y-this.y)) <= radius;
    }

    public boolean contain(float[] points){
        return contain(points[0],points[1]);
    }
}
