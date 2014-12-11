package com.thecolony.tractus.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;


/**
 * A class that holds all the common objects used for graphics.
 * @author Joe Pagliuco
 */
public abstract class GraphicsManager
{
    private static Spatial M_SHIP_FIGHTER;
    private static Spatial M_SHIP_FRIGATE;
    private static Spatial M_SHIP_CRUISER;
    private static Spatial M_SHIP_CAPITAL_SHIP;
    
    private static Material M_DEFAULT_WHITE_MATERIAL;
    
    public static void loadGraphics(AssetManager contentMan)
    {
        Material mat = new Material(contentMan, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Red);
        M_SHIP_FIGHTER = contentMan.loadModel("Models/fighter.obj");
        M_SHIP_FIGHTER.setMaterial(mat.clone());
        M_SHIP_FIGHTER.scale(0.25f);
        
        M_SHIP_CAPITAL_SHIP = contentMan.loadModel("Models/Cube.001.mesh.xml");
        M_SHIP_CAPITAL_SHIP.setMaterial(new Material(contentMan, "Common/MatDefs/Misc/Unshaded.j3md"));
        M_SHIP_CAPITAL_SHIP.scale(0.5f);
        
        M_SHIP_CRUISER = contentMan.loadModel("Models/cruiser.scene");
        M_SHIP_CRUISER.setMaterial(new Material(contentMan, "Common/MatDefs/Misc/Unshaded.j3md"));
        M_SHIP_CRUISER.scale(0.35f);
        
        M_DEFAULT_WHITE_MATERIAL = new Material(contentMan, "Common/MatDefs/Misc/Unshaded.j3md");
        M_DEFAULT_WHITE_MATERIAL.setColor("Color", ColorRGBA.White);
    }
    
    public static Spatial getShipFighterModel()
    {
        return M_SHIP_FIGHTER.clone();
    }
    public static Spatial getShipFrigateModel()
    {
        return M_SHIP_FRIGATE.clone();
    }
    public static Spatial getShipCruiserModel()
    {
        return M_SHIP_CRUISER.clone();
    }
    public static Spatial getShipCaptialShipModel()
    {
        return M_SHIP_CAPITAL_SHIP.clone();
    }
    
    
    public static Material getDefaultWhiteMaterial()
    {
        return M_DEFAULT_WHITE_MATERIAL.clone();
    }
    
    
    public static Material generateMaterial(ColorRGBA color)
    {
        Material m = M_DEFAULT_WHITE_MATERIAL.clone();
        m.setColor("Color", color);
        
        return m.clone();
    }
}