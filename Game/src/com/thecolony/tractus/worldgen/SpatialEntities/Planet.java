package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.thecolony.tractus.saveInfo.Res;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.thecolony.tractus.economics.Firm;

import java.util.ArrayList;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Planet extends VisualEntity {
    protected Continent[] continents;
    private ArrayList<Firm> firms;
    
    public Planet(Vector3f pos, String name, Node node, Spatial model, VisualType type){
        super(pos, name, node, model, type);
        Res resy=res;
        firms = new ArrayList<Firm>();
        setDisplayInfo();
    }
    public Planet(Vector3f pos, Star star, Continent[] continents, Res res, String name, String owner, Node node, AssetManager manager, ColorRGBA color, VisualType type){
        super(pos,star,continents,res,name,owner,node,manager,color,type);
        Res resy=res;
        firms = new ArrayList<Firm>();
        setDisplayInfo();
    }
    
    public void addFirm(Firm firm) {
        firms.add(firm);
    }
    
    public void addFirms(ArrayList<Firm> firms) {
        firms.addAll(firms);
    }
    
    @Override
    protected String[] setDisplayInfo() {
        String[] basicInfo = super.setDisplayInfo();
        String newInfo = "";
        for(int i = 0; i < basicInfo.length; i++)
            newInfo += basicInfo[i] + "\n";
        try {
            for(int i = 0; i < firms.size(); i++)
            {
                Firm firm = firms.get(i);
                String firmInfo = firm.getProductionGood().getType() + "  production: " + firm.getQuantity();
                newInfo += firmInfo + "\n";
                newInfo += " " + firm.getCurrentInventory() + "\n";
            }
        } catch (NullPointerException npe)
        {
            
        }
        drawableObject.getModel().setUserData("Display Info", newInfo.split("\n"));
        return newInfo.split("\n");
    }
    
    public ArrayList<Firm> getFirms()
    {
        return firms;
    }
}
