package com.spacecolonization.military.vehicles.ships;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.spacecolonization.graphics.MoveableObject;

/**
 * A class used to represent a ship.
 * @author Joe Pagliuco
 */
public abstract class Ship extends MoveableObject
{
    private static long M_ID_COUNT = 0;
    
    private static final float M_ROTATION_SPEED = FastMath.PI / 2.0f;
    
    protected int mMaxHealth;
    protected int mArmour;
    protected int mMaxShields;
    //protected int mSpeed; <----Part of MoveableObject
    protected int mElectroDamage;
    protected int mKineticDamage;
    
    protected Vector3f mTargetMovementPoint;
    
    private float mPrevAngle;
    
    public Ship(int maxHealth, int armour, int maxShields, float speed, int electroSpeed, int kinecticSpeed, Vector3f position, Spatial model, String name)
    {
        super(position, model, name, Vector3f.ZERO, speed, M_ROTATION_SPEED);
        
        this.mMaxHealth = maxHealth;
        this.mArmour = armour;
        this.mMaxShields = maxShields;
        this.mMovementSpeed = speed;
        this.mElectroDamage = electroSpeed;
        this.mKineticDamage = kinecticSpeed;
        
        this.mTargetMovementPoint = position;
        
        mPrevAngle = FastMath.PI;
        
        M_ID_COUNT++;
    }
    
    /**
     * @return Returns the number of Ship objects that have been
     * created since the application started.
     */
    public static long getIDCount()
    {
        return M_ID_COUNT;
    }
    
    public void update(float deltaTime)
    {
        if (mIsRotating)
        {            
            Vector3f v = mTargetMovementPoint.subtract(getPosition()).normalizeLocal();
            
            float angle = (float)Math.acos(v.dot(mDirection));         
            
            // If the cross product points up, the ship needs to rotate to the right.
            Vector3f cross = mDirection.cross(v);
            if (cross.y > 0)
            {
                super.rotateDirection(Vector3f.UNIT_Y, deltaTime);
            }
            else if (cross.y < 0)                
            {
                super.rotateDirection(Vector3f.UNIT_Y.negate(), deltaTime);
            }
            
            if (angle > mPrevAngle)
                mIsRotating = false;
            
            mPrevAngle = angle;
        }
        
        if (mIsMoving && !mIsRotating)
        {
            super.moveAlongDirectionalVector(deltaTime);
            
            Vector3f v = mTargetMovementPoint.subtract(getPosition()).normalizeLocal();
            if ((mDirection.x <= 0 && v.x >= 0) || (mDirection.x >= 0 && v.x <= 0))
            {
                if ((mDirection.z <= 0 && v.z >= 0) || (mDirection.z >= 0 && v.z <= 0))
                {
                    mModel.setLocalTranslation(mTargetMovementPoint);
                    mIsMoving = false;
                }
            }
        }
    }
    
    public void setTarget(Vector3f targetPoint)
    {
        mTargetMovementPoint = targetPoint;
        mIsMoving = true;
        mIsRotating = true;
        mPrevAngle = FastMath.PI;
    }
}