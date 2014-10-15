/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star extends Territory{
    public enum StarType {RED_GIANT}
    private StarType type;
    //private Planet[] planets;
    //TODO: add other space objects to system
    private double[] radii; //info about its planets
    //TODO: Add more star characteristics
    public Star(double locationX, double locationY, StarType type, StarSystem starSystem, Planet[] planets, String name, String owner){
        super(locationX,locationY,starSystem,planets,name,owner);
        this.type=type;

    }

    public StarType getType() {
        return type;
    }

    public void setType(StarType type) {
        this.type = type;
    }
    public String toString(){
        return superTerr.toString()+" Star="+name;
    }
}
