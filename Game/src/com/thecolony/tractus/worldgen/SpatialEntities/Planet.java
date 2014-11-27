package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.worldgen.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet extends Territory {
    private final double MASS;
    private final double RADIUS;
    private PlanetType type;
    //private resources.Res res=new resources.Res(); //this will be filled by grabbing from below
    protected Continent[] continents;
    //TODO: INSERT ATMO CONDITIONS
    public Planet(double locationX, double locationY, PlanetType type, Star star, Continent[] continents, Res res, String name, String owner){
        super(locationX,locationY,star,continents,res,name,owner);
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
        return superTerr.toString()+" Planet="+name+super.toString();
    }
}
