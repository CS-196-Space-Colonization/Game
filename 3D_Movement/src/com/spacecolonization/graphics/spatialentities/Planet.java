package com.spacecolonization.graphics.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * A class used to represent a planet.
 * @author Joe Pagliuco
 */
public class Planet extends SpatialEntity
{
    private static int M_ID_COUNT = 0;
    
    public Planet(Vector3f position, Spatial model, String name)
    {
        super(position, model, name);
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        initialize();
        
        super.setUserData("Planet");
    }
    
    // Temporary
    public Planet(Vector3f position, String name, AssetManager contentMan, float radius, ColorRGBA color)
    {
        super(position, null, name);
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        mModel = loadModel(contentMan, radius, color);
        mModel.setLocalTranslation(position);
        super.setUserData("Planet");
        
        initialize();
    }
    
    // Temporary
    private Spatial loadModel(AssetManager contentMan, float radius, ColorRGBA color)
    {
        Sphere s = new Sphere(32, 32, radius);
        Geometry g = new Geometry(mName, s);
        Material m = new Material(contentMan, "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Ambient", color);
        m.setColor("Diffuse", color);
        g.setMaterial(m);
        
        return (Spatial)g;
    }
    
    private void initialize()
    {
        
    }
    
    /**
     * @return Returns the number of Planet objects that have been
     * created since the application started.
     */
    public static int getIDCount()
    {
        return M_ID_COUNT;
    }
    
    /**
     * Makes a String representation of this.
     * @return Returns the ID, followed by the object's name.
     */
    @Override
    public String toString()
    {
        return "Planet ID: " + mID + ", Name: " + mName;
    }
    
    /**
     * Compares Object o to this.
     * @param o An Object to compare this to.
     * @return Returns true if the id of this object equals the id of o,
     * false if either o is not a Planet, or the equals check fails.
     */
    @Override
    public boolean equals(Object o)
    {
        Planet p;
        if (o instanceof Planet)
            p = (Planet)o;
        else
            return false;
        return mID == p.mID;
    }
}