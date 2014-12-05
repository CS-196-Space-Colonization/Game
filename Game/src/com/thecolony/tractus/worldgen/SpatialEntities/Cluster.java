package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Cluster extends RegionalEntity{
    private int ID;
    private static int ID_COUNT=0;
    public Cluster(Vector3f pos, Sector sector, StarSystem[] starsys, Res res, String name, String owner){
        super(pos,2,2,2,sector,starsys,res,name,owner);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    @Override
    public int getID() {
        return this.ID;
    }
}
