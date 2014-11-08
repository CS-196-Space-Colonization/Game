package com.spacecolonization.military.vehicles.ships;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
    
    private boolean mTargetRotationAngleIsPositive;
    
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
        if (mInMovement)
        {
            // Rotate...           
            float angle = mDirection.dot(mTargetMovementPoint.subtract(getPosition()));
            if (mTargetRotationAngleIsPositive && angle >= 0)
                rotateDirection(Vector3f.UNIT_Y, deltaTime);
            else if (!mTargetRotationAngleIsPositive && angle <= 0)
                rotateDirection(Vector3f.UNIT_Y.negate(), deltaTime);
            else
            {
                Quaternion q = new Quaternion();
                q.fromAngleAxis(FastMath.PI / -2.0f, Vector3f.UNIT_Y);
                super.moveAlongDirectionalVector(q.mult(mDirection), deltaTime);
                
                Vector3f v = getPosition().subtract(mTargetMovementPoint);
                if ((mDirection.x >= 0 && v.x >= 0) || (mDirection.x < 0 && v.x < 0))
                {
                    
                    if ((mDirection.z >= 0 && v.z >= 0) || (mDirection.z < 0 && v.z < 0))
                    {
                        mModel.setLocalTranslation(mTargetMovementPoint);
                        mInMovement = false;
                    }
                }
            }
        }
    }
    
    public void setTarget(Vector3f targetPoint)
    {
        mTargetMovementPoint = targetPoint;
        mInMovement = true;
        mTargetRotationAngleIsPositive = (mDirection.dot(mTargetMovementPoint.subtract(getPosition())) > 0) ? true : false;
        System.out.println(mTargetRotationAngleIsPositive);
    }
}