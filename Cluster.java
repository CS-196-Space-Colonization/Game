/**
 * Created by chthonic7 on 10/8/14.
 */
public class Cluster extends Territory{
    public Cluster(double locationX, double locationY, Sector sector, StarSystem[] starsys, String name, String owner){
        super(locationX,locationY,sector,starsys,name,owner);
    }
    public String toString(){
        return superTerr.toString()+" Cluster="+name;
    }
}
