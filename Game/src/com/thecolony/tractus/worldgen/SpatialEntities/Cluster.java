package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Cluster extends Territory {
    private int ID;
    private static int ID_COUNT=0;
    public Cluster(float locationX, float locationZ, Sector sector, StarSystem[] starsys, Res res, String name, String owner){
        super(locationX,locationZ,sector,starsys,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    @Override
    public int getID() {
        return this.ID;
    }
}
