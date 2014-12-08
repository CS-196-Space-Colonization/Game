package com.thecolony.tractus.military.battle;
//this class will be extended to make more specific objects

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.thecolony.tractus.graphics.drawableobjects.DrawableObject3d;
import com.thecolony.tractus.player.Player;

public class Stationary_Object extends BattleObject
{
    private DrawableObject3d model;
    
    public Stationary_Object()
    {
        super();
    }

    public Stationary_Object(double HP)
    {
        super(HP);
    }

    public Stationary_Object(Player owner, String nameOfShip, double[] stats, int Cost, int Crew, int ammo)
    {
        super(owner, nameOfShip, stats, Cost, Crew, ammo);
    }
    
    @Override
    public Vector3f getPosition()
    {
        return model.getPosition();
    }

    @Override
    public Geometry getWireBoxGeometry()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
