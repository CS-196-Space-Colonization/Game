package com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.DrawableObject3d;

/**
 * A class used to represent a star.
 * @author Joe Pagliuco
 */
public class Star extends DrawableObject3d
{
    protected static final float M_POINT_LIGHT_RADIUS = 1000.0f;
    
    protected PointLight mPointLight;
    
    public Star(Vector3f position, Spatial model, String name)
    {
        super(position, model, name, "Star");
        
        initialize();
    }
    
    // Temporary
    public Star(Vector3f position, String name, AssetManager contentMan, float radius)
    {
        super(position, null, name, "Star");
        
        mModel = loadModel(contentMan, radius);
        mModel.setLocalTranslation(position);
        
        initialize();
    }
    
    // Temporary
    private Spatial loadModel(AssetManager contentMan, float radius)
    {
        Sphere s = new Sphere(32, 32, radius);
        Geometry g = new Geometry(mName, s);
        Material m = new Material(contentMan, "Common/MatDefs/Light/Lighting.j3md");
        m.setTexture("DiffuseMap", contentMan.loadTexture("Textures/sun.jpg"));
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Ambient", ColorRGBA.White);
        m.setColor("Diffuse", ColorRGBA.White);
        g.setMaterial(m);
        
        // Extra Lighting
        AmbientLight a = new AmbientLight();
        a.setColor(ColorRGBA.White);
        g.addLight(a);
        
        return g;
    }
    
    private void initialize()
    {
        mPointLight = new PointLight();
        mPointLight.setPosition(mModel.getLocalTranslation());
        mPointLight.setName("Star (" + mName + ") Point Light");
        mPointLight.setColor(ColorRGBA.White.mult(1.4f));
        mPointLight.setRadius(M_POINT_LIGHT_RADIUS);
    }
    
    /**
     * @return Returns the point light of this star, to be used as the light
     * a star would radiate.
     */
    public PointLight getPointLight()
    {
        return mPointLight;
    }
    
    /**
     * Makes a String representation of this.
     * @return Returns the ID, followed by the object's name.
     */
    @Override
    public String toString()
    {
        return "Star ID: " + getID() + ", Name: " + mName;
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
        Star s;
        if (o instanceof Star)
            s = (Star)o;
        else
            return false;
        return getID() == s.getID();
    }
}