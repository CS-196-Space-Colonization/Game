package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;

/**
 * Created by wes on 12/3/14.
 */
public class RegionalEntity extends Territory{
    public RegionalEntity(float locX, float locZ, Territory superTerr, Territory[] terr, Res res, String name, String owner){
        super(locX,locZ,superTerr,terr,res,name,owner);
    }
}
