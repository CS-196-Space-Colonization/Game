package com.spacecolonization.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 * A class that holds all of the model's as Spatials.
 * @author Joe Pagliuco
 */
public abstract class GameModels
{
    public static Spatial M_FIGHTER_SHIP;
    
    public static void loadModels(AssetManager contentMan)
    {
        Material mat = new Material(contentMan, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Red);
        
        M_FIGHTER_SHIP = contentMan.loadModel("Models/fighter.obj");
        M_FIGHTER_SHIP.setMaterial(mat);
    }
}