package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.thecolony.tractus.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star extends VisualEntity
{

    private static final float LIGHT_RADIUS = 1000f;
    private PointLight pointLight;

    public Star(Vector3f pos, String name, Node node, Spatial model, VisualType type)
    {
        super(pos, name, node, model, type);
    }

    public Star(Vector3f pos, StarSystem starSys, Planet[] planets, Res res, String name, String owner, Node node, AssetManager manager, ColorRGBA color, VisualType type)
    {
        super(pos, starSys, planets, res, name, owner, node, manager, color, type);
    }

    protected void initialize(String name, Node node, Spatial model, Vector3f position)
    {
        super.initialize(name, node, model, position);
        pointLight = new PointLight();
        pointLight.setPosition(drawableObject.getPosition());
        pointLight.setName("Star (" + name + ") Point Light");
        pointLight.setColor(ColorRGBA.White.mult(1.4f));
        pointLight.setRadius(LIGHT_RADIUS);
        node.addLight(pointLight);
    }

    @Override
    protected Spatial loadModel(AssetManager contentMan, float radius, ColorRGBA color)
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

    public PointLight getPointLight()
    {
        return pointLight;
    }
}
