package com.eirb.projets9.map.objects;

import android.graphics.Path;
import android.graphics.Region;

/*
* This class correspond to the custom view's polygon management
* */
public class PolygonRoom extends Room{

    /* -------------------- Attributes -------------------- */
    private Path path = new Path();
    private Region region = new Region();

    /**
     * Constructor of PolygonRoom
     *
     * @param path the polygon's path not used for the moment
     * @param region provide the coordinates of all the points of the polygon's path
     */
    public PolygonRoom(String id, String name, int state, Path path, Region region) {
        super(id, name, state);
        this.setPath(path);
        this.setRegion(region);
        region.setPath(path, new Region(0,0,1100,1200));
    }

    public PolygonRoom(Path path, Region region) {
        super();
        this.setPath(path);
        this.setRegion(region);
        region.setPath(path, new Region(0,0,1100,1200));
    }

    public PolygonRoom(Path path, Region region, String id) {
        super();
        this.setPath(path);
        this.setRegion(region);
        this.setId(id);
        region.setPath(path, new Region(0,0,1100,1200));
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}