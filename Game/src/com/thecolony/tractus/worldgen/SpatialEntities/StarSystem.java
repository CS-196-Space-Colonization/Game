package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class StarSystem extends RegionalEntity{
    private int ID;
    private static int ID_COUNT=0;
    public StarSystem(Vector3f pos, Cluster cluster, Star[] stars, Res res, String name, String owner){
        super(pos,1f,1f,1f,cluster,stars,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
