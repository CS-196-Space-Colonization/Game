/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star {
    protected enum StarType {RED_GIANT}

    ;
    protected Planet[] planets;
    //TODO: add other space objects to system
    protected double[] radii;
    //TODO: Add more star characteristics
    protected Res res = new Res(); //this will be filled by grabbing from below
    private double locationX;
    private double locationY;
    private String name, owner;
}
