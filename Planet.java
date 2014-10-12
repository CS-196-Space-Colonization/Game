/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet {
    public enum PlanetType {
        TERRESTRIAL,GASGIANT,SUPEREARTH,SUBEARTH,MININEPTUNE,ROUGE,ICE,LAVA; //TODO: Will add more
    }
    private PlanetType type;
    private Res res=new Res(); //this will be filled by grabbing from below
    protected Continent[] continents;
    private final double MASS=1;
    private final double RADIUS=1;
    //TODO: previous two are determined by planet type
    //TODO: INSERT ATMO CONDITIONS
    private Star star;
    private String name,owner;
    private double locationX;
    private double locationY;
    public Planet(PlanetType type, Continent[] continents, Res res, String owner, String name){
        this.type=type;
        this.continents=continents;
        this.res=res;
        this.owner=owner;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PlanetType getType() {
        return type;
    }

    public double getMass() {
        return MASS;
    }

    public double getRadius() {
        return RADIUS;
    }

    public Continent[] getContinents() {

        return continents;
    }

    public void setContinents(Continent[] continents) {
        this.continents = continents;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public double getResource(String resourceName){
        double sum=0;
        for (Continent cont:continents) sum+=cont.getResource(resourceName);
        return sum;
    }
    public String toString(){
        return star.toString()+" Planet="+name;
    }
}
