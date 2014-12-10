package com.thecolony.tractus.graphics.drawableobjects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * A class used to represent a game object that needs to be drawn in 3D
 * space.
 * @author Joe Pagliuco
 */
public class DrawableObject3d
{    
    protected Spatial mModel;
    protected String mName;
    
    protected Node node;
    
    /**
     * Used to create a DrawableObject3d.
     * @param name Name of object (used for display purposes).
     * @param node A reference to the Node this object needs to be attached to.
     * @param model Model used to graphically represent the object.
     * @param position Position of object.
     */
    public DrawableObject3d(String name, Node node, Spatial model, Vector3f position)
    {        
        if (model != null)
        {
            mModel = model;
            mModel.setLocalTranslation(position);
        }
        mName = name;
        
        this.node = node;
        node.attachChild(model);
    }
    
    /**
     * Sets the model representation of this object.
     * @param model A Spatial used to represent this object graphically.
     */
    public void setModel(Spatial model)
    {
        this.mModel = model;
    }
    /**
     * @return Returns the model representation of this object.
     */
    public Spatial getModel()
    {
        return mModel;
    }
    
    /**
     * Convenience method to get Spatial position.
     * @return Returns getModel().getLocalTranslation().
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
     * Attach/Detaches this model from it's node.
     * @param attach true to attach to node, false to detach.
     */
    public void changeNodeState(boolean attach)
    {
        if (attach)
            node.attachChild(mModel);
        else
            node.detachChild(mModel);
    }
    
    public Node getNode()
    {
        return node;
    }
}