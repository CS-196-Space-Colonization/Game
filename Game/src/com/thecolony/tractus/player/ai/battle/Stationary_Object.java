package com.thecolony.tractus.player.ai.battle;
//this class will be extended to make more specific objects

import com.jme3.math.Vector3f;
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
}
