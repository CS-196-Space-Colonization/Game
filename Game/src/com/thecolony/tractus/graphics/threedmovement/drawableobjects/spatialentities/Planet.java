/**
 * CLASS MARKED FOR DELETION WITH THIS NOTE. BUILD WILL LIKELY BREAK UNTIL A FEW COMMITS LATER. SORRY!
 */
package com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.DrawableObject3d;
import org.lwjgl.opengl.Drawable;

/**
 * A class used to represent a planet.
 * @author Joe Pagliuco
 */
public class Planet extends DrawableObject3d implements java.io.Serializable
{
    ColorRGBA color;

    protected DrawableObject3d model;

    public Planet(Vector3f position, Spatial model, String name)
    {
        super(position, null, name, "Planet");

        this.model = new DrawableObject3d(position, model,name,"Planet");
        initialize();
    }
    
    // Temporary
    public Planet(Vector3f position, String name, AssetManager contentMan, float radius, ColorRGBA color)
    {
        super(position, null, name, "Planet");
        
        mModel = loadModel(contentMan, radius, color);
        mModel.setLocalTranslation(position);
        
        this.color = color;
        
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
     * Makes a String representation of this.
     * @return Returns the ID, followed by the object's name.
     */
    @Override
    public String toString()
    {
        return "Planet ID: " + getID() + ", Name: " + mName;
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
        return getID() == p.getID();
    }
}