package com.thecolony.tractus.graphics.drawableobjects.spatialentities;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.economics.Firm;
import com.thecolony.tractus.graphics.drawableobjects.DrawableObject3d;
import java.util.ArrayList;

/**
 * A class used to represent a planet.
 * @author Joe Pagliuco
 */
@Serializable
public class Planet implements java.io.Serializable
{
    protected transient DrawableObject3d drawableObject;
    
    private String name;
    private ColorRGBA color;
    
    private transient BoundingSphere boundingSphere;
    
    private ArrayList<Firm> firms;
    
    public Planet(String name, Node node, Spatial model, Vector3f position)
    {
        initialize(name, node, model, position);
    }
    
    // Temporary
    public Planet(String name, Node node, Vector3f position, AssetManager contentMan, float radius, ColorRGBA color)
    {        
        this.color = color;
        
        initialize(name, node, loadModel(contentMan, radius, color), position);
    }
    
    // Temporary
    private Spatial loadModel(AssetManager contentMan, float radius, ColorRGBA color)
    {
        Sphere s = new Sphere(32, 32, radius);
        Geometry g = new Geometry("Planet", s);
        Material m = new Material(contentMan, "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Ambient", color);
        m.setColor("Diffuse", color);
        g.setMaterial(m);
        
        return (Spatial)g;
    }
    
    private void initialize(String name, Node node, Spatial model, Vector3f position)
    {
        drawableObject = new DrawableObject3d(name, node, model, position, "Planet");
        
        this.name = name;
        
        BoundingVolume b = drawableObject.getModel().getWorldBound();
        if (b.getType() == BoundingVolume.Type.AABB)
        {
            BoundingBox bb = (BoundingBox)b;
            boundingSphere = new BoundingSphere(Math.max(Math.max(bb.getXExtent(), bb.getYExtent()), bb.getZExtent()), drawableObject.getPosition());
        }
        else if (b.getType() == BoundingVolume.Type.Sphere)
            boundingSphere = (BoundingSphere)b;
    }
    
    public String[] getDisplayInfo()
    {
        return new String[]
        {
            "Planet:",
            " Name: " + name,
            " Other Info..."
        };
    }
    
    public DrawableObject3d getDrawableObject3d()
    {
        return drawableObject;
    }
    
    public BoundingSphere getBoundingSphere()
    {
        return boundingSphere;
    }
    
    public String getName()
    {
        return name;
    }
    
    // Temporary...
    public ColorRGBA getColor()
    {
        return color;
    }
}