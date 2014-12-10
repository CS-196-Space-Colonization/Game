/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.military.ships;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.thecolony.tractus.graphics.GraphicsManager;
import com.thecolony.tractus.graphics.drawableobjects.MoveableObject3d;
import com.thecolony.tractus.player.Player;
import com.thecolony.tractus.military.battle.MoveableBattleObject;

/**
 *
 * @author Mark Haynie
 * @author Joe Pagliuco
 */
public class Ship extends MoveableBattleObject
{
    /** Class Structure **/
        // Variables...
        // Constructors...
        // Initialization Methods...
        // Update Methods...
        // Getters/Setters...    
    
    
    public static enum SHIP_TYPE
    {
        Fighter("Fighter"), Frigate("Frigate"), Cruiser("Cruiser"), CapitalShip("Capital Ship");
        String type;

        SHIP_TYPE(String s)
        {
            type = s;
        }

        @Override
        public String toString()
        {
            return type;
        }
    }
    private SHIP_TYPE shipType;
    
    private static final float M_ROTATION_SPEED = FastMath.PI / 2.0f;
    
    private double fuel;
    
    private Vector3f position;
    private Vector3f targetMovementPoint;
    private float prevAngle;
    private float prevDistance;
    
    private MoveableObject3d model; 
    private Geometry wireBoxGeometry;
    
    private String Qual;
    private double level;
    private String type;

    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // START CONSTRUCTORS /////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        
    /**
     * Should not be used unless other values are going to be set.
     */
    public Ship()
    {
        super();

        fuel = 0;
    }

    public Ship(Player p, SHIP_TYPE shipType, String nameOfShip, Node node, Vector3f position, double[] stats, int cost, int crew, int ammo, double fuel)
    {
        super(p, nameOfShip, stats, cost, crew, ammo);
        this.fuel = fuel;
        this.shipType = shipType;
        initialize(shipType, node, position);
        Qual = "Attack";
        level = 1;
        type = shipType.toString();
        setShip(type);
    }

    public Ship(Player p, SHIP_TYPE shipType, String nameOfShip, Node node, Vector3f position, double[] stats, int cost, int crew, int ammo, double fuel, String qual, int Lev)
    {
        super(p, nameOfShip, stats, cost, crew, ammo);
        this.fuel = fuel;
        this.shipType = shipType;
        initialize(shipType, node, position);
        Qual = qual;
        level = Lev;
        type = shipType.toString();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // END CONSTRUCTORS ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // START INITIALIZATION METHODS ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void initialize(SHIP_TYPE shipType, Node node, Vector3f position)
    {
        if (shipType == SHIP_TYPE.Fighter)
        {
            model = new MoveableObject3d(name, node, GraphicsManager.getShipFighterModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float) getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED);
            model.getModel().setLocalTranslation(position);
        } else if (shipType == SHIP_TYPE.Frigate)
        {
            model = new MoveableObject3d(name, node, GraphicsManager.getShipFrigateModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float) getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED);
            model.getModel().setLocalTranslation(position);
        } else if (shipType == SHIP_TYPE.Cruiser)
        {
            model = new MoveableObject3d(name, node, GraphicsManager.getShipCruiserModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float) getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED);
            model.getModel().setLocalTranslation(position);
        } else if (shipType == SHIP_TYPE.CapitalShip)
        {
            model = new MoveableObject3d(name, node, GraphicsManager.getShipCaptialShipModel(), Vector3f.ZERO, Vector3f.UNIT_X, (float) getBattleStat(BATTLE_STAT_MOVEMENT_SPEED), M_ROTATION_SPEED);
            model.getModel().setLocalTranslation(position);
        }
        
        this.position = model.getPosition();

        model.getModel().setUserData("Selected", false);

        createWireBoxGeometry();
        
        setDisplayInfo();
    }
    
    private void createWireBoxGeometry()
    {
        WireBox wireBox = new WireBox();
        wireBox.fromBoundingBox((BoundingBox) model.getModel().getWorldBound());
        wireBoxGeometry = new Geometry("Ship WireBox Geometry", wireBox);
        wireBoxGeometry.setMaterial(GraphicsManager.getDefaultWhiteMaterial());
        wireBoxGeometry.setLocalTranslation(model.getPosition());
        wireBoxGeometry.scale(1.5f);
    }
    
    protected void setDisplayInfo()
    {
        String[] s = super.getBattleStatsAsString();
        String[] display = new String[s.length + 1];
        System.arraycopy(s, 0, display, 1, s.length);
        display[0] = this.shipType.toString() + ":";
        model.getModel().setUserData("Display Info", display);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // END INITIALIZATION METHODS /////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // START UPDATE METHODS ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void update(float deltaTime)
    {
        if (model.isRotating())
        {
            Vector3f v = targetMovementPoint.subtract(model.getPosition()).normalizeLocal();

            float angle = (float) Math.acos(v.dot(model.getDirection()));

            // If the cross product points up, the ship needs to rotate to the right.
            Vector3f cross = model.getDirection().cross(v);
            if (cross.y > 0)
            {
                model.rotateDirection(Vector3f.UNIT_Y, deltaTime);
            } else if (cross.y < 0)
            {
                model.rotateDirection(Vector3f.UNIT_Y.negate(), deltaTime);
            }

            if (angle > prevAngle)
            {
                model.setIsRotating(false);
            }

            prevAngle = angle;
        }

        if (model.isMoving() && !model.isRotating())
        {
            model.moveAlongDirectionalVector(deltaTime);

            float distance = targetMovementPoint.distance(model.getPosition());

            if (distance > prevDistance)
            {
                model.setIsMoving(false);
            }

            prevDistance = distance;
        }

        if (isRotating())
        {
            wireBoxGeometry.setLocalRotation(model.getModel().getLocalRotation());
        }

        if (isMoving() && !isRotating())
        {            
            MoveableObject3d m =  model;
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
        model.setIsMoving(moveTo);
        model.setIsRotating(true);
        prevAngle = FastMath.PI;
        prevDistance = Float.POSITIVE_INFINITY;
    }
    
    @Override
    public void setBattleStat(int BATTLE_STAT, double value)
    {
        if (BATTLE_STAT == BATTLE_STAT_MOVEMENT_SPEED)            
            model.setMovementSpeed((float)value);
        
        super.setBattleStat(BATTLE_STAT, value);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // END UPDATE METHODS /////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // START GETTERS/SETTERS //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return true if the ship is moving or rotating, false otherwise.
     */
    public boolean isTransforming()
    {
        return model.isMoving() || model.isRotating();
    }

    public boolean isMoving()
    {
        return model.isMoving();
    }

    public boolean isRotating()
    {
        return model.isRotating();
    }
    
    public Geometry getWireBoxGeometry()
    {
        return wireBoxGeometry;
    }
    
    public void setToFighter()
    {
        crew = 1;
        //Fighter		Can�t exist in space without a mother capital ship. 1 pilot, only 100 per flotilla
        if (Qual.equals("Heal"))
        {
            setCrew(crew);
            setStatsHeal(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Build"))
        {
            setCrew(crew);
            setStatsBuild(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Attack"))
        {
            setCrew(crew);
            setStatsAttack(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Defence"))
        {
            setCrew(crew);
            setStatsDefend(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Speed"))
        {
            setCrew(crew);
            setStatsSpeed(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Range"))
        {
            setCrew(crew);
            setStatsRange(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Balance"))
        {
            setCrew(crew);
            setStatsBalance(level);
            setCost((int) (level * 5 - 1));
        } else
        {
            System.out.println("error!!!");
        }
    }

    public void setToFrigate()
    {
        level = level * 2;
        crew = 5;
        //Frigates	Small ships with a crew of around 5 people, only 20 per flotilla
        if (Qual.equals("Heal"))
        {
            setCrew(crew);
            setStatsHeal(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Build"))
        {
            setCrew(crew);
            setStatsBuild(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Attack"))
        {
            setCrew(crew);
            setStatsAttack(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Defence"))
        {
            setCrew(crew);
            setStatsDefend(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Speed"))
        {
            setCrew(crew);
            setStatsSpeed(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Range"))
        {
            setCrew(crew);
            setStatsRange(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Balance"))
        {
            setCrew(crew);
            setStatsBalance(level);
            setCost((int) (level * 5 - 1));
        } else
        {
            System.out.println("error!!!");
        }
    }

    public void setToCruiser()
    {
        level = level * 3;
        crew = 20;
        //Cruisers	Bigger ships with a crew of 20 people, only 5 per flotilla
        if (Qual.equals("Heal"))
        {
            setCrew(crew);
            setStatsHeal(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Build"))
        {
            setCrew(crew);
            setStatsBuild(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Attack"))
        {
            setCrew(crew);
            setStatsAttack(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Defence"))
        {
            setCrew(crew);
            setStatsDefend(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Speed"))
        {
            setCrew(crew);
            setStatsSpeed(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Range"))
        {
            setCrew(crew);
            setStatsRange(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Balance"))
        {
            setCrew(crew);
            setStatsBalance(level);
            setCost((int) (level * 5 - 1));
        } else
        {
            System.out.println("error!!!");
        }
    }

    public void setToCapital()
    {
        crew = 100;
        level = level * 4;
        //Capital Ships	Massive ships with crews of 100 people, only one per flotilla
        if (Qual.equals("Heal"))
        {
            setCrew(crew);
            setStatsHeal(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Build"))
        {
            setCrew(crew);
            setStatsBuild(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Attack"))
        {
            setCrew(crew);
            setStatsAttack(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Defence"))
        {
            setCrew(crew);
            setStatsDefend(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Speed"))
        {
            setCrew(crew);
            setStatsSpeed(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Range"))
        {
            setCrew(crew);
            setStatsRange(level);
            setCost((int) (level * 5 - 1));
        } else if (Qual.equals("Balance"))
        {
            setCrew(crew);
            setStatsBalance(level);
            setCost((int) (level * 5 - 1));
        } else
        {
            System.out.println("error!!!");
        }
    }

    public void setShip(String type)
    {
        if (type.equals("Fighter"))
        {
            setToFighter();
        } else if (type.equals("Capital Ship"))
        {
            setToCapital();
        } else if (type.equals("Frigate"))
        {
            setToFrigate();
        } else if (type.equals("Cruiser"))
        {
            setToCruiser();
        } else
        {
            System.out.println("invalid input!!!!!");
        }
    }

    public SHIP_TYPE getType() {
        return shipType;
    }

    @Override
    public Vector3f getPosition()
    {
        return model.getPosition();
    }
    
    public MoveableObject3d getMoveableObject3d()
    {
        return model;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // END GETTERS/SETTERS ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Called after being passed through a network to reconstruct itself.
     * @param node The Node this ship is drawn to.
     */
    public void reconstruct(Node node)
    {
        initialize(shipType, node, position);
    }
}