package SpatialEntities;

import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Cluster extends Territory {
    public Cluster(double locationX, double locationY, Sector sector, StarSystem[] starsys, Res res, String name, String owner){
        super(locationX,locationY,sector,starsys,res,name,owner);
    }
    public String toString(){
        return superTerr.toString()+" Cluster="+name+super.toString();
    }
}
