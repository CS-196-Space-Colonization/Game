package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import resources.Res;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet extends VisualEntity {
    private int ID;
    private static int ID_COUNT=0;
    protected Continent[] continents;
    public Planet(Vector3f pos, String name, Node node, Spatial model, VisualType type){
        super(pos, name, node, model, type);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    public Planet(Vector3f pos, Star star, Continent[] continents, Res res, String name, String owner, Node node, AssetManager manager, ColorRGBA color, VisualType type){
        super(pos,star,continents,res,name,owner,node,manager,color,type);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    @Override
    public int getID() {
        return this.ID;
    }
}
