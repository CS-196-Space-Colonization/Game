package com.thecolony.tractus.player.ai.battle;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameModels;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.MoveableObject;

public class Ship extends BattleObject
{
    public static enum SHIP_TYPE { Fighter, Frigate, Cruiser, CapitalShip };
    
    private static final float M_ROTATION_SPEED = FastMath.PI / 2.0f;
    
    private double fuel;    
    
    protected Vector3f targetMovementPoint;
    
    private float prevAngle;
    private float prevDistance;
    
    /**
     * Should not be used unless other values are going to be set.
     */
    public Ship()
    {
        super();
                
        fuel = 0;
    }
    
    public Ship(SHIP_TYPE shipType, String nameOfShip, Vector3f position, double[] stats, int cost, String display, int crew, int ammo, double fuel)
    {
        super(nameOfShip, stats, cost, display, crew, ammo);
        
        this.fuel = fuel;
        
        initialize(shipType, position);
    }
    
    private void initialize(SHIP_TYPE shipType, Vector3f position)
    {
        if (shipType == SHIP_TYPE.Fighter)
        {
            model = new MoveableObject(Vector3f.ZERO, GameModels.getShipFighterModel(), name, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Fighter");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.Frigate)
        {            
            model = new MoveableObject(Vector3f.ZERO, GameModels.getShipFrigateModel(), name, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Frigate");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.Cruiser)
        {
            model = new MoveableObject(Vector3f.ZERO, GameModels.getShipCruiserModel(), name, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Cruiser");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.CapitalShip)
        {
            model = new MoveableObject(Vector3f.ZERO, GameModels.getShipCaptialShipModel(), name, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Capital Ship");
            model.getModel().setLocalTranslation(position);
        }
        
        model.getModel().setUserData("Selected", false);
    }
    
    public void addFuel(double add)
    {
        fuel = fuel + add;
    }
    public double getFuel()
    {
        return fuel;
    }
    public void useFuel(double amount)
    {
        fuel = fuel - amount;
    }
    
    public void update(float deltaTime)
    {
        if (((MoveableObject)model).isRotating())
        {            
            Vector3f v = targetMovementPoint.subtract(model.getPosition()).normalizeLocal();
            
            float angle = (float)Math.acos(v.dot(((MoveableObject)model).getDirection()));         
            
            // If the cross product points up, the ship needs to rotate to the right.
            Vector3f cross = ((MoveableObject)model).getDirection().cross(v);
            if (cross.y > 0)
            {
                ((MoveableObject)model).rotateDirection(Vector3f.UNIT_Y, deltaTime);
            }
            else if (cross.y < 0)                
            {
                ((MoveableObject)model).rotateDirection(Vector3f.UNIT_Y.negate(), deltaTime);
            }
            
            if (angle > prevAngle)
                ((MoveableObject)model).setIsRotating(false);
            
            prevAngle = angle;
        }
        
        if (((MoveableObject)model).isMoving() && !((MoveableObject)model).isRotating())
        {
            ((MoveableObject)model).moveAlongDirectionalVector(deltaTime);
            
            float distance = targetMovementPoint.distance(((MoveableObject)model).getPosition());
            
            if (distance > prevDistance)
            {
                model.getModel().setLocalTranslation(targetMovementPoint);
                ((MoveableObject)model).setIsMoving(false);
            }
            
            prevDistance = distance;
        }
    }
    
    public void setTarget(Vector3f targetPoint, boolean moveTo)
    {
        targetMovementPoint = targetPoint;
        ((MoveableObject)model).setIsMoving(moveTo);
        ((MoveableObject)model).setIsRotating(true);
        prevAngle = FastMath.PI;
        prevDistance = Float.POSITIVE_INFINITY;
    }
    
    public boolean isSelected()
    {
        return model.getModel().getUserData("Selected");
    }
    
    public void setIsSelected(boolean selected)
    {
        model.getModel().setUserData("Selected", selected);
    }
}