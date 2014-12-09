package com.eirb.projets9.map.objects;

import android.graphics.RectF;

/*
    * This class correspond to the custom view's rectangle management
    * */
public class RectangleRoom extends Room{

    /* -------------------- Attributes -------------------- */
    private RectF rect = new RectF();

    /**
     * Constructor of RectangleRoom
     *
     * @param rect the rectangle shape with its coordinates
     */

    public RectangleRoom(String id, String name, int state, RectF rect) {
        super(id, name, state);
        this.setRect(rect);
    }

    public RectangleRoom(RectF rect) {
        super();
        this.setRect(rect);
        this.setId("null");
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }
}