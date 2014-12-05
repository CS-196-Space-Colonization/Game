package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.DrawableObject3d;
import resources.Res;

import com.jme3.scene.Spatial;

/**
 * Created by wesley on 11/28/14.
 */
public class VisualEntity extends  Territory{
    protected int ID;
    protected static int ID_COUNT=0;
    protected final double MASS, RADIUS;
    protected DrawableObject3d drawableObject;
    protected ColorRGBA color;
    protected BoundingSphere boundingSphere;
    protected String className;
    protected VisualEntity(String name, Node node, Spatial model, Vector3f pos){
        this(pos.getX(),pos.getZ(),null,null,new Res(),name,"no-one");
    }
    protected VisualEntity(float locX, float locZ, Territory superTerr, Territory[] terr, Res res, String name, String owner, Node node, AssetManager contentMan, ColorRGBA color){
        super(locX,locZ,superTerr,terr,res,name,owner);
        className=this.getClass().toString(); className=className.substring(className.lastIndexOf('.')+1);
        initialize(name, node, model, position);
        MASS=0;
        RADIUS=0;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    protected void initialize(String name, Node node, Spatial model, Vector3f position)
    {
        drawableObject = new DrawableObject3d(name, node, model, position, className);
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

    public DrawableObject3d getDrawableObject() {
        return drawableObject;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public BoundingSphere getBoundingSphere() {
        return boundingSphere;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
