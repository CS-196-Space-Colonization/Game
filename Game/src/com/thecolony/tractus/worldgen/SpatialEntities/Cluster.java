package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.worldgen.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Cluster extends Territory {
    public Cluster(float locationX, float locationZ, Sector sector, StarSystem[] starsys, Res res, String name, String owner){
        super(locationX,locationZ,sector,starsys,res,name,owner);
    }
}
