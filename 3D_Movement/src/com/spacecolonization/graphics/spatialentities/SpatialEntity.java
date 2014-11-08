package com.spacecolonization.graphics.spatialentities;

import com.spacecolonization.graphics.DrawableObject3d;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * An abstract class used to represent any spatial entity (ie Star, 
 * Planet, etc).
 * @author Joe Pagliuco
 */
public abstract class SpatialEntity extends DrawableObject3d
{
    private static int M_ID_COUNT = 0;
    
    public SpatialEntity(Vector3f position, Spatial model, String name)
    {
        super(position, model, name);
        
        M_ID_COUNT++;
    }
    
    /**
     * @return Returns the number of SpatialEntity objects that have been
     * created since the application started.
     */
    public static long getIDCount()
    {
        return M_ID_COUNT;
    }
    
    /**
     * Makes a String representation of this.
     * @return Returns the ID, followed by the object's name.
     */
    @Override
    public String toString()
    {
        return "SpatialEntity ID: " + Long.toString(mID) + ", Name: " + mName;
    }
    
    /**
     * Compares Object o to this.
     * @param o An Object to compare this to.
     * @return Returns true if the id of this object equals the id of o,
     * false if either o is not a SpatialEntity, or the equals check fails.
     */
    @Override
    public boolean equals(Object o)
    {
        SpatialEntity s;
        if (o instanceof SpatialEntity)
            s = (SpatialEntity)o;
        else
            return false;
        return mID == s.mID;
    }
}