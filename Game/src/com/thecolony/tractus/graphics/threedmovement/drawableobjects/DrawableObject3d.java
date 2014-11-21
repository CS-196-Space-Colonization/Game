package com.thecolony.tractus.graphics.threedmovement.drawableobjects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * A class used to represent a game object that needs to be drawn in 3D
 * space.
 * @author Joe Pagliuco
 */
public class DrawableObject3d 
{
    private static int M_ID_COUNT = 0;
    private int mID;
    
    private String mClassType;
    
    protected Spatial mModel;
    protected String mName;
    
    /**
     * Used to create a DrawableObject3d.
     * @param position Position of object.
     * @param model Model used to graphically represent the object.
     * @param name Name of object (used for display purposes).
     * @param classType The type of DrawableObject (ie Fighter, Capital Ship, etc).
     */
    public DrawableObject3d(Vector3f position, Spatial model, String name, String classType)
    {
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        mClassType = classType;
        
        if (model != null)
        {
            mModel = model;
            mModel.setLocalTranslation(position);
            mModel.setUserData("Type", classType);
            mModel.setUserData("ID", mID);
        }
        mName = name;
    }
    
    /**
     * Sets the model representation of this object.
     * @param model A Spatial used to represent this object graphically.
     */
    public void setModel(Spatial model)
    {
        this.mModel = model;
        mModel.setUserData("Type", mClassType);
        mModel.setUserData("ID", mID);        
    }    
    /**
     * @return Returns the model representation of this object.
     */
    public Spatial getModel()
    {
        return mModel;
    }
    
    /**
     * @return Returns the position of the model that represents this object.
     */
    public Vector3f getPosition()
    {
        return mModel.getLocalTranslation();
    }
    
    /**
     * @return Returns the name of this object.
     */
    public String getName()
    {
        return mName;
    }
    
    /**
     * @return Returns the id of this object.
     */
    public int getID()
    {
        return mID;
    }
    
    /**
     * @return Returns the number of DrawableObject3d objects that have been
     * created since the application started.
     */
    public static int getIDCount()
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
        return "DrawableObject3d ID: " + mID + ", Name: " + mName;
    }
    
    /**
     * Compares Object o to this.
     * @param o An Object to compare this to.
     * @return Returns true if the id of this object equals the id of o,
     * false if either o is not a DrawableObject3d, or the equals check fails.
     */
    @Override
    public boolean equals(Object o)
    {
        DrawableObject3d d;
        if (o instanceof DrawableObject3d)
            d = (DrawableObject3d)o;
        else
            return false;
        return mID == d.getID();
    }
}