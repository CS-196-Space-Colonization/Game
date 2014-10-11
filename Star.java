/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star {
    protected enum StarType {RED_GIANT}

    ;
    protected Planet[] planets;
    //TODO: add other space objects to system
    protected double[] radii; //this list and previous should be connected
    //TODO: Add more star characteristics
    protected Resource[] res = new Resource[]; //this will be filled by grabbing from below
    double locationX;
    double locationY;
}
