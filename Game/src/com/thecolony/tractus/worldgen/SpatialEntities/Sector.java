package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.worldgen.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Sector extends Territory {
    //To be honest, wasn't sure if this was necessary, but maybe this is akin to galaxy, so whatevs
    public Sector(float locationX, float locationZ, Cluster[] clusters, Res res, String name, String owner){
        super(locationX,locationZ,null,clusters,res,name,owner);
    }
    public String toString(){
        return "Sector="+name+super.toString();
    }
}
