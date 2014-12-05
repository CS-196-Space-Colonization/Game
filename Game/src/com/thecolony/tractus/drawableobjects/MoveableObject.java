package com.thecolony.tractus.drawableobjects;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * A class used to represent an object that needs to move.
 * @author Joe Pagliuco
 */
public class MoveableObject extends DrawableObject3d
{
    
    protected Vector3f mDirection;
    protected float mMovementSpeed;
    protected float mRotationSpeed;
    
    protected boolean mIsMoving;
    protected boolean mIsRotating;
    
    public MoveableObject(String name, Node node, Spatial model, Vector3f position, Vector3f direction, float movementSpeed, float rotationSpeed, String classType)
    {
        super(name, node, model, position, classType);
        
        mDirection = direction;
        mMovementSpeed = movementSpeed;
        mRotationSpeed = rotationSpeed;
        
        mIsMoving = false;
        mIsRotating = false;
    }
    
    public Vector3f getDirection()
    {
        return mDirection;
    }
    public void setDirection(Vector3f direction)
    {
        mDirection = direction;
    }
    public void rotateDirection(Vector3f rotationAxis, float deltaTime)
    {
        Quaternion rot = new Quaternion();
        rot.fromAngleAxis(mRotationSpeed * deltaTime, rotationAxis);
        mDirection = rot.mult(mDirection);
        mModel.rotate(rot);
    }
    
    public float getMovementSpeed()
    {
        return mMovementSpeed;
    }
    public void setMovementSpeed(float speed)
    {
        mMovementSpeed = speed;
    }
    
    public float getRotationSpeed()
    {
        return mMovementSpeed;
    }
    public void setRotationSpeed(float speed)
    {
        mMovementSpeed = speed;
    }
    
    /**
     * Moves the object along the directional vector at this object's speed.
     */
    public void moveAlongDirectionalVector(float deltaTime)
    {
        mModel.move(mDirection.mult(mMovementSpeed * deltaTime));
    }
    
    public void setIsMoving(boolean moving)
    {
        mIsMoving = moving;
    }
    public boolean isMoving()
    {
        return mIsMoving;
    }
    
    public void setIsRotating(boolean rotating)
    {
        mIsRotating = rotating;
    }
    public boolean isRotating()
    {
        return mIsRotating;
    }
    
    public boolean isTransforming()
    {
        return isMoving() || isRotating();
    }
}