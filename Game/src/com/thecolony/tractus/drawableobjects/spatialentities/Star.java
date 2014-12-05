package com.thecolony.tractus.drawableobjects.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.drawableobjects.DrawableObject3d;

/**
 * A class used to represent a star.
 * @author Joe Pagliuco
 */
public class Star
{
    protected static final float M_POINT_LIGHT_RADIUS = 1000.0f;
    
    protected PointLight mPointLight;
    
    protected DrawableObject3d drawableObject;
    
    private String name;
    
    private BoundingSphere boundingSphere;
    
    public Star(String name, Node node, Spatial model, Vector3f position)
    {
        initialize(name, node, model, position);
    }
    
    // Temporary
    public Star(String name, Node node, Vector3f position, AssetManager contentMan, float radius)
    {        
        initialize(name, node, loadModel(contentMan, radius), position);
    }
    
    // Temporary
    private Spatial loadModel(AssetManager contentMan, float radius)
    {
        Sphere s = new Sphere(32, 32, radius);
        Geometry g = new Geometry("Star", s);
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
    
    private void initialize(String name, Node node, Spatial model, Vector3f position)
    {
        drawableObject = new DrawableObject3d(name, node, model, position, "Star");
        
        this.name = name;
        
        mPointLight = new PointLight();
        mPointLight.setPosition(drawableObject.getPosition());
        mPointLight.setName("Star (" + name + ") Point Light");
        mPointLight.setColor(ColorRGBA.White.mult(1.4f));
        mPointLight.setRadius(M_POINT_LIGHT_RADIUS);
        
        node.addLight(mPointLight);
        
        BoundingVolume b = drawableObject.getModel().getWorldBound();
        if (b.getType() == BoundingVolume.Type.AABB)
        {
            BoundingBox bb = (BoundingBox)b;
            boundingSphere = new BoundingSphere(Math.max(Math.max(bb.getXExtent(), bb.getYExtent()), bb.getZExtent()), drawableObject.getPosition());
        }
        else if (b.getType() == BoundingVolume.Type.Sphere)
            boundingSphere = (BoundingSphere)b;
    }
    
    /**
     * @return Returns the point light of this star, to be used as the light
     * a star would radiate.
     */
    public PointLight getPointLight()
    {
        return mPointLight;
    }
    
    public BoundingSphere getBoundingSphere()
    {
        return boundingSphere;
    }
    
    public DrawableObject3d getDrawableObject()
    {
        return drawableObject;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDisplayInfo()
    {
        return "Star:\n"
                + " Name: " + name + "\n"
                + " Other Info...";
    }
}