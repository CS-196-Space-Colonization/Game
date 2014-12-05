package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Sector extends RegionalEntity {
    //To be honest, wasn't sure if this was necessary, but maybe this is akin to galaxy, so whatevs
    private int ID;
    private static int ID_COUNT=0;
    public Sector(Vector3f pos,Cluster[] clusters, Res res, String name, String owner){
        super(pos,3,3,3,null,clusters,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
