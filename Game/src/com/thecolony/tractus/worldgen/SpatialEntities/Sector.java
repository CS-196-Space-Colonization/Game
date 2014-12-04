package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Sector extends Territory {
    //To be honest, wasn't sure if this was necessary, but maybe this is akin to galaxy, so whatevs
    private int ID;
    private static int ID_COUNT=0;
    public Sector(float locationX, float locationZ, Cluster[] clusters, Res res, String name, String owner){
        super(locationX,locationZ,null,clusters,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
