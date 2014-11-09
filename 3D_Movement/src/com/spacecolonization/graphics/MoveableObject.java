package com.spacecolonization.graphics;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * A class used to represent an object that needs to move.
 * @author Joe Pagliuco
 */
public class MoveableObject extends DrawableObject3d
{
    private static long M_ID_COUNT = 0;
    
    protected Vector3f mDirection;
    protected float mMovementSpeed;
    protected float mRotationSpeed;
    
    protected boolean mIsMoving;
    protected boolean mIsRotating;
    
    public MoveableObject(Vector3f position, Spatial model, String name)
    {
        super(position, model, name);
        
        mDirection = new Vector3f();
        mMovementSpeed = 0;
        
        mIsMoving = false;
        mIsRotating = false;
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
    }
    
    public MoveableObject(Vector3f position, Spatial model, String name, Vector3f direction, float movementSpeed, float rotationSpeed)
    {
        super(position, model, name);
        
        mDirection = direction;
        mMovementSpeed = movementSpeed;
        mRotationSpeed = rotationSpeed;
        
        mIsMoving = false;
        mIsRotating = false;
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
    }
    
    /**
     * @return Returns the number of MoveableObject objects that have been
     * created since the application started.
     */
    public static long getIDCount()
    {
        return M_ID_COUNT;
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
    
    public boolean isMoving()
    {
        return mIsMoving;
    }
    public boolean isRotating()
    {
        return mIsRotating;
    }
}