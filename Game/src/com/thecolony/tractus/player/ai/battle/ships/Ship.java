/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thecolony.tractus.player.ai.battle.ships;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Line;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameGraphics;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.MoveableObject;
import com.thecolony.tractus.player.ai.battle.BattleObject;

/**
 *
 * @author Joe Pagliuco
 */

public class Ship extends BattleObject
{
    public static enum SHIP_TYPE { 
        Fighter("Fighter"), Frigate("Frigate"), Cruiser("Cruiser"), CapitalShip("Capital Ship");
    
        String type;
        SHIP_TYPE(String s) { type = s; }
        
        @Override
        public String toString() { return type; }
    };
    private SHIP_TYPE shipType;
    
    private static final float M_ROTATION_SPEED = FastMath.PI / 2.0f;
    
    private double fuel;    
    
    private Vector3f targetMovementPoint;
    
    private float prevAngle;
    private float prevDistance;
    
    private Geometry wireBoxGeometry;
    
    private String Qual;
    private double level;
    private String type;
    
    /**
     * Should not be used unless other values are going to be set.
     */
    public Ship()
    {
        super();
                
        fuel = 0;
    }
    
    public Ship(SHIP_TYPE shipType, String nameOfShip, Node node, Vector3f position, double[] stats, int cost, String display, int crew, int ammo, double fuel)
    {
        super(nameOfShip, stats, cost, display, crew, ammo);
        this.fuel = fuel;
        this.shipType = shipType;
        initialize(shipType, node, position);
        Qual = "Attack";
        level = 1;
        type = shipType.toString();
    }
    
     public Ship(SHIP_TYPE shipType, String nameOfShip, Node node, Vector3f position, double[] stats, int cost, String display, int crew, int ammo, double fuel, String qual, int Lev)
    {
        super(nameOfShip, stats, cost, display, crew, ammo);
        this.fuel = fuel;
        this.shipType = shipType;
        initialize(shipType, node, position);
        Qual = qual;
        level = Lev;
        type = shipType.toString();
    }
    
    private void initialize(SHIP_TYPE shipType, Node node, Vector3f position)
    {
        
        
        if (shipType == SHIP_TYPE.Fighter)
        {
            model = new MoveableObject(name, node, GameGraphics.getShipFighterModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Fighter");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.Frigate)
        {            
            model = new MoveableObject(name, node, GameGraphics.getShipFrigateModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Frigate");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.Cruiser)
        {
            model = new MoveableObject(name, node, GameGraphics.getShipCruiserModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Cruiser");
            model.getModel().setLocalTranslation(position);
        }
        else if (shipType == SHIP_TYPE.CapitalShip)
        {
            model = new MoveableObject(name, node, GameGraphics.getShipCaptialShipModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float)getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED, "Capital Ship");
            model.getModel().setLocalTranslation(position);
        }
        
        model.getModel().setUserData("Selected", false);
        
        WireBox wireBox = new WireBox();
        wireBox.fromBoundingBox((BoundingBox) model.getModel().getWorldBound());
        wireBoxGeometry = new Geometry("Ship WireBox Geometry", wireBox);
        wireBoxGeometry.setMaterial(GameGraphics.getDefaultWhiteMaterial());
        wireBoxGeometry.setLocalTranslation(position);
        wireBoxGeometry.scale(1.5f);
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
                ((MoveableObject)model).setIsMoving(false);
            
            prevDistance = distance;
        }
        
        if (isRotating())
            wireBoxGeometry.setLocalRotation(model.getModel().getLocalRotation());
        
        if (isMoving() && !isRotating())
        {
            MoveableObject m = (MoveableObject)model;
            wireBoxGeometry.move(m.getDirection().mult(m.getMovementSpeed() * deltaTime));
        }
    }
    
    public void move(Vector3f offset)
    {
        model.getModel().move(offset);
        wireBoxGeometry.move(offset);
    }
    
    public void setTargetPoint(Vector3f targetPoint, boolean moveTo)
    {
        targetMovementPoint = targetPoint;
        ((MoveableObject)model).setIsMoving(moveTo);
        ((MoveableObject)model).setIsRotating(true);
        prevAngle = FastMath.PI;
        prevDistance = Float.POSITIVE_INFINITY;
    }
    
    public Vector3f getPosition()
    {
        return model.getPosition();
    }
    
    /**
     * @return If the ship is moving or rotating, this method returns a Line
     * running from the ship's position to it's target point, returns null
     * otherwise.
     */
    public Line getLineToTarget()
    {
        if (!isTransforming())
            return null;
        else
            return new Line(model.getPosition(), targetMovementPoint);
    }
    
    /**
     * @return true if the ship is moving or rotating, false otherwise.
     */
    public boolean isTransforming()
    {
        return ((MoveableObject)model).isMoving() || ((MoveableObject)model).isRotating();
    }
    
    public boolean isMoving()
    {
        return ((MoveableObject)model).isMoving();
    }
    
    public boolean isRotating()
    {
        return ((MoveableObject)model).isRotating();
    }
    
    public boolean isSelected()
    {
        return model.getModel().getUserData("Selected");
    }
    
    public void setIsSelected(boolean selected)
    {
        model.getModel().setUserData("Selected", selected);
    }
    
    public Geometry getWireBoxGeometry()
    {
        return wireBoxGeometry;
    }
    
    @Override
    public String getDisplayInfo()
    {
        return this.shipType.toString() + ":\n " + super.getDisplayInfo();
    }
    
    
    public void setToFighter()
	{
		crew = 1;
		//Fighter		Can�t exist in space without a mother capital ship. 1 pilot, only 100 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToFrigate()
	{
		crew = 5;
		//Frigates	Small ships with a crew of around 5 people, only 20 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToCruiser()
	{
		crew = 20;
		//Cruisers	Bigger ships with a crew of 20 people, only 5 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToCapital()
	{
		crew = 100;
		//Capital Ships	Massive ships with crews of 100 people, only one per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setShip(String type)
	{
		if(type.equals("Fighter"))
			setToFighter();
		else if(type.equals("Capital"))
			setToCapital();
		else if(type.equals("Frigate"))
			setToFrigate();
		else if(type.equals("Cruiser"))
			setToCruiser();
		else 
			System.out.println("invalid input!!!!!");
	}
}