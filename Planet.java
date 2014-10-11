/**
 * Created by chthonic7 on 10/8/14.
 */
public enum Planet {
    TERRESTRIAL,ICE, MININEPTUNE,GAS,SUPEREARTH,SUBEARTH,ROUGE;


    protected Resource[] res=new Resource[]; //this will be filled by grabbing from below
    protected Continent[] continents;
    protected PlanetType type=PlanetType.GAS_GIANT;
    protected final double MASS;
    protected final double RADIUS;
    //previous two are determined by planet type
    //TODO: INSERT ATMO CONDITIONS
    protected Star star;
    //TODO: generate getters and setters
    double locationX;
    double locationY;
}
