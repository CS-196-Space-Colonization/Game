/**
 * Created by chthonic7 on 10/8/14.
 */
public class StarSystem extends Territory{
    public StarSystem(double locationX, double locationY, Cluster cluster, Star[] stars, Res res, String name, String owner){
        super(locationX,locationY,cluster,stars,res,name,owner);
    }
    public String toString(){
        return superTerr.toString()+" Star System="+name+super.toString();
    }
}
