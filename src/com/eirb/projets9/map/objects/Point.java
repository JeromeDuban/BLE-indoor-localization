package com.eirb.projets9.map.objects;


public class Point {

    /* -------------------- Attributes -------------------- */
    private float x;
    private float y;

    /**
     * Constructor of Point
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    public Point(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

