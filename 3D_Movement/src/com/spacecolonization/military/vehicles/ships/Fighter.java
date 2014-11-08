package com.spacecolonization.military.vehicles.ships;

import com.jme3.math.Vector3f;
import com.spacecolonization.graphics.GameModels;

/**
 * A class represented to represent a fighter ship.
 * @author Joe Pagliuco
 */
public class Fighter extends Ship
{
    private static long M_ID_COUNT = 0;

    public static final int M_BASE_MAX_HEALTH = 50;
    public static final int M_BASE_ARMOUR = 1;
    public static final int M_BASE_MAX_SHIELDS = 0;
    public static final float M_BASE_SPEED = 20f;
    public static final int M_BASE_ELECTRO_DAMAGE = 0;
    public static final int M_BASE_KINETIC_DAMAGE = 1;
    
    public Fighter(Vector3f position, String name)
    {
        super(M_BASE_MAX_HEALTH, M_BASE_ARMOUR, M_BASE_MAX_SHIELDS, M_BASE_SPEED, M_BASE_ELECTRO_DAMAGE, M_BASE_KINETIC_DAMAGE, position, GameModels.M_FIGHTER_SHIP, name);
        
        mDirection = Vector3f.UNIT_X;
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        mModel.setName("Fighter " + Long.toString(mID));
    }
    
    /**
     * @return Returns the number of Fighter objects that have been
     * created since the application started.
     */
    public static long getIDCount()
    {
        return M_ID_COUNT;
    }
    
    @Override
    public void update(float deltaTime) 
    {
        super.update(deltaTime);
    }
}