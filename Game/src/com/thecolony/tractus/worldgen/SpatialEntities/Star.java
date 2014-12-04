package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star extends VisualEntity {
    //Interesting idea is to assigng textures to each kinda star, as well as radii
    public enum StarType {RED_GIANT}
    private StarType type;
    //TODO: add other space objects to system
    private double[] radii; //info about its planets
    //TODO: Add more star characteristics
    private int ID;
    private static int ID_COUNT=0;
    public Star(float locationX, float locationZ, StarType type, StarSystem starSystem, Planet[] planets, Res res, String name, String owner){
        super(locationX,locationZ,starSystem,planets,res,name,owner);
        this.type=type;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }

    public StarType getType() {
        return type;
    }

    public void setType(StarType type) {
        this.type = type;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
