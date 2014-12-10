package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.saveInfo.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class StarSystem extends RegionalEntity{
    public StarSystem(Vector3f pos, Cluster cluster, Star[] stars, Res res, String name, String owner){
        super(pos,1f,1f,1f,cluster,stars,res,name,owner);
    }
}
