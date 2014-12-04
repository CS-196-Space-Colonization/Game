package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;
/**
 * Created by chthonic7 on 10/8/14.
 */
public class StarSystem extends Territory {
    private int ID;
    private static int ID_COUNT=0;
    public StarSystem(float locationX, float locationZ, Cluster cluster, Star[] stars, Res res, String name, String owner){
        super(locationX,locationZ,cluster,stars,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
