package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet extends VisualEntity {
    private int ID;
    private static int ID_COUNT=0;
    private final double MASS;
    private final double RADIUS;
    private PlanetType type;
    protected Continent[] continents;
    private Spatial model;
    private AssetManager manager;
    //TODO: INSERT ATMO CONDITIONS(not likely to ever happen, though)
    public Planet(float locationX, float locationZ, PlanetType type, Spatial model, Star star, Continent[] continents, Res res, String name, String owner, AssetManager manager){
        super(locationX,locationZ,star,continents,res,name,owner);
        this.type=type;
        MASS=1;  RADIUS=1;
        this.model=model;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
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

    @Override
    public int getID() {
        return this.ID;
    }
}
