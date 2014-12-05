package com.thecolony.tractus.worldgen.SpatialEntities;

import java.io.Serializable;

/**
 * Created by chthonic7 on 10/11/14.
 */
@com.jme3.network.serializing.Serializable
public enum VisualType implements Serializable{
    TERRESTRIAL_PLANET(18/5,"Planet"),GASGIANT_PLANET(6,"Planet"),SUBEARTH_PLANET(12/5,"Planet"),MININEPTUNE_PLANET(24/5,"Planet"),LAVA_PLANET(6/5,"Planet"),
    DWARF_STAR(15,"Star"),MAINSEQUENCE_STAR(19,"Star"),GIANT_STAR(21,"Star"),SUPERGIANT_STAR(25,"Star");
    private final float RADIUS;
    private final String TYPE;
    VisualType(double radius, String type){
        this.RADIUS=(float)radius;
        this.TYPE=type;
    }
    public float getRADIUS(){return this.RADIUS;}
    public String getTYPE(){return this.TYPE;}
}
