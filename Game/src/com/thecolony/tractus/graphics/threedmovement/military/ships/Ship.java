package com.thecolony.tractus.graphics.threedmovement.military.ships;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameModels;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.MoveableObject;

/**
 * A class used to represent a ship.
 * @author Joe Pagliuco
 */
public class Ship extends MoveableObject
{
    private static int M_ID_COUNT = 0;
    
    private static final float M_ROTATION_SPEED = FastMath.PI / 2.0f;
    
    public static enum SHIP_TYPE { Fighter, Frigate, Cruiser, CapitalShip };
    
    protected int mMaxHealth;
    protected int mArmour;
    protected int mMaxShields;
    //protected int mSpeed; <----Part of MoveableObject
    protected int mElectroDamage;
    protected int mKineticDamage;
    
    protected Vector3f mTargetMovementPoint;
    
    private float mPrevAngle;
    private float mPrevDistance;
    
    public Ship(SHIP_TYPE shipType, Vector3f position, String name)
    {
        // The model, movement speed, direction, and model name need to be changed based on shipType.
        super(position, GameModels.getShipFighterModel(), name, Vector3f.UNIT_X, 0.0f, M_ROTATION_SPEED);
        
        M_ID_COUNT++;
        mID = M_ID_COUNT;
        
        if (shipType == SHIP_TYPE.Fighter)
        {
            mMaxHealth = 50;
            mArmour = 1;
            mMaxShields = 0;
            mMovementSpeed = 20.0f;
            mElectroDamage = 0;
            mKineticDamage = 1;
            
            mModel = GameModels.getShipFighterModel();
            mModel.setLocalTranslation(position);
            super.setUserData("Fighter");
            mDirection = Vector3f.UNIT_X;
        }
        else if (shipType == SHIP_TYPE.Frigate)
        {
            mMaxHealth = 200;
            mArmour = 10;
            mMaxShields = 0;
            mMovementSpeed = 10.0f;
            mElectroDamage = 0;
            mKineticDamage = 10;
            
            mModel = GameModels.getShipFrigateModel();
            mModel.setLocalTranslation(position);
            super.setUserData("Frigate");
            mDirection = Vector3f.UNIT_X;
        }
        else if (shipType == SHIP_TYPE.Cruiser)
        {
            mMaxHealth = 500;
            mArmour = 10;
            mMaxShields = 0;
            mMovementSpeed = 10.0f;
            mElectroDamage = 0;
            mKineticDamage = 50;
            
            mModel = GameModels.getShipCruiserModel();
            mModel.setLocalTranslation(position);
            super.setUserData("Cruiser");
            mDirection = Vector3f.UNIT_X;
        }
        else if (shipType == SHIP_TYPE.CapitalShip)
        {
            mMaxHealth = 1000;
            mArmour = 50;
            mMaxShields = 0;
            mMovementSpeed = 5.0f;
            mElectroDamage = 0;
            mKineticDamage = 100;
            
            mModel = GameModels.getShipCaptialShipModel();
            mModel.setLocalTranslation(position);
            super.setUserData("Captial Ship");
            mDirection = Vector3f.UNIT_X;
        }
        
        mModel.setUserData("Selected", false);
    }
    
    /**
     * @return Returns the number of Ship objects that have been
     * created since the application started.
     */
    public static int getIDCount()
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
            
            float distance = mTargetMovementPoint.distance(getPosition());
            
            if (distance > mPrevDistance)
            {
                mModel.setLocalTranslation(mTargetMovementPoint);
                mIsMoving = false;
            }
            
            mPrevDistance = distance;
            
//            Vector3f v = mTargetMovementPoint.subtract(getPosition()).normalizeLocal();
//            if ((mDirection.x <= 0 && v.x >= 0) || (mDirection.x >= 0 && v.x <= 0))
//            {
//                if ((mDirection.z <= 0 && v.z >= 0) || (mDirection.z >= 0 && v.z <= 0))
//                {
//                    mModel.setLocalTranslation(mTargetMovementPoint);
//                    mIsMoving = false;
//                }
//            }
        }
    }
    
    public void setTarget(Vector3f targetPoint, boolean moveTo)
    {
        mTargetMovementPoint = targetPoint;
        mIsMoving = moveTo;
        mIsRotating = true;
        mPrevAngle = FastMath.PI;
        mPrevDistance = Float.POSITIVE_INFINITY;
    }
    
    public boolean isSelected()
    {
        return mModel.getUserData("Selected");
    }
    
    public void setIsSelected(boolean selected)
    {
        mModel.setUserData("Selected", selected);
    }
    
    /**
     * Makes a String representation of this.
     * @return Returns the ID, followed by the object's name.
     */
    @Override
    public String toString()
    {
        return "Ship ID: " + mID + ", Name: " + mName;
    }
    
    /**
     * Compares Object o to this.
     * @param o An Object to compare this to.
     * @return Returns true if the id of this object equals the id of o,
     * false if either o is not a Ship, or the equals check fails.
     */
    @Override
    public boolean equals(Object o)
    {
        Ship s;
        if (o instanceof Ship)
            s = (Ship)o;
        else
            return false;
        return mID == s.getID();
    }
}