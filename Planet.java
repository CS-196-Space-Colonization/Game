/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet extends Territory{
    private final double MASS;
    private final double RADIUS;
    public enum PlanetType {
        TERRESTRIAL,GASGIANT,SUPEREARTH,SUBEARTH,MININEPTUNE,ROUGE,ICE,LAVA; //TODO: Will add more
    }
    private PlanetType type;
    //private Res res=new Res(); //this will be filled by grabbing from below
    protected Continent[] continents;
    //TODO: INSERT ATMO CONDITIONS
    private String name,owner;
    private double locationX;
    private double locationY;
    public Planet(double locationX, double locationY, PlanetType type, Star star, Continent[] continents, String name, String owner){
        super(locationX,locationY,star,continents,name,owner);
        this.type=type;
        MASS=1;
        RADIUS=1;
        //TODO: previous two are determined by planet type
    }

    public PlanetType getType() {
        return type;
    }

    public void setType(PlanetType type) {
        this.type=type;
    }

    public double getMass() {
        return MASS;
    }

    public double getRadius() {
        return RADIUS;
    }

    public String toString(){
        return superTerr.toString()+" Planet="+name;
    }
}
