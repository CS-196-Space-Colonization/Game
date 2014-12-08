/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thecolony.tractus.military.battle;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.player.Player;

/**
 *
 * @author Joe Pagliuco
 */
public abstract class MoveableBattleObject extends BattleObject
{
    public MoveableBattleObject()
    {
        super();
    }
    
    public MoveableBattleObject(double hp)
    {
        super(hp);
    }

    public MoveableBattleObject(String name)
    {
        super(name);
    }

    public MoveableBattleObject(Player p, String nameOfShip, double[] stats, int Cost, int Crew, int am)
    {
        super(p, nameOfShip, stats, Cost, Crew, am);
    }
    
    public abstract Vector3f getPosition();
    public abstract boolean isTransforming();
    public abstract boolean isMoving();
    public abstract boolean isRotating();
    public abstract void move(Vector3f offset);
    public abstract void setTargetPoint(Vector3f targetPoint, boolean moveTo);
}