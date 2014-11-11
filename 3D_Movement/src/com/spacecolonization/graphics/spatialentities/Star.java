package com.spacecolonization.graphics.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * A class used to represent a star.
 * @author Joe Pagliuco
 */
public class Star extends SpatialEntity
{
    private static int M_ID_COUNT = 0;
    protected static final float M_POINT_LIGHT_RADIUS = 1000.0f;
    
    protected PointLight mPointLight;
    
    public Star(Vector3f position, Spatial model, String name)
    {
        super(position, model, name);
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        initialize();
        
        super.setUserData("Star");
    }
    
    // Temporary
    public Star(Vector3f position, String name, AssetManager contentMan, float radius)
    {
        super(position, null, name);
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        mModel = loadModel(contentMan, radius);
        mModel.setLocalTranslation(position);
        
        initialize();
        
        super.setUserData("Star");
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
     * @return Returns the number of Star objects that have been
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
        return "Star ID: " + mID + ", Name: " + mName;
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
        return mID == s.mID;
    }
}