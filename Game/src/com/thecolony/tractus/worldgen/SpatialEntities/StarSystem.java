package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.worldgen.resources.Res;
/**
 * Created by chthonic7 on 10/8/14.
 */
public class StarSystem extends Territory {
    public StarSystem(float locationX, float locationZ, Cluster cluster, Star[] stars, Res res, String name, String owner){
        super(locationX,locationZ,cluster,stars,res,name,owner);
    }
    public String toString(){
        return superTerr.toString()+" Star System="+name+super.toString();
    }
}
