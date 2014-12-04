package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.graphics.threedmovement.drawableobjects.DrawableObject3d;
import resources.Res;

import com.jme3.scene.Spatial;

/**
 * Created by wesley on 11/28/14.
 */
public class VisualEntity extends  Territory{
    protected int ID;
    protected static int ID_COUNT=0;
    protected final double MASS, RADIUS;
    protected Spatial model;
    protected DrawableObject3d drawableObject3d;
    protected VisualEntity(float locX, float locZ, Territory superTerr, Territory[] terr, Res res, String name, String owner){
        super(locX,locZ,superTerr,terr,res,name,owner);
        MASS=0;
        RADIUS=0;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    @Override
    public int getID() {
        return this.ID;
    }
}
