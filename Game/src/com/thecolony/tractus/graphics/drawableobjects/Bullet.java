package com.thecolony.tractus.graphics.drawableobjects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import com.thecolony.tractus.player.ai.battle.ships.Ship;

/**
 * A class used to represent a bullet shot by a ship.
 * @author Joe Pagliuco
 */
public class Bullet
{
    private final float BULLET_LENGTH = 5.0f;
    private final float BULLET_SPEED = 15.0f;    
    
    private Node node;
    private MoveableObject3d model;
    
    private Vector3f targetPoint;
    private float prevDistance;
    
    private boolean hasBeenShot;
    /** true if the bullet has been shot and has reached a target */
    private boolean isDead;
    
    /**
     * Creates a bullet based of a ship's orientation.
     * @param node The node to attach the bullet to.
     * @param ship The ship used to base the bullet off of.
     */
    public Bullet(Node node, Ship ship)
    {
        this.node = node;
        
        Vector3f direction = ship.getMoveableObject3d().getDirection().normalize().mult(BULLET_LENGTH);
        Line line = new Line(ship.getPosition(), ship.getPosition().add(direction));
        Geometry g = new Geometry("Bullet Line", line);
        g.setMaterial(GameGraphics.getDefaultWhiteMaterial());
        
        model = new MoveableObject3d("Bullet", node, g, ship.getPosition(), direction, BULLET_SPEED, 0.0f, "Bullet");
        
        hasBeenShot = false;
        isDead = false;
    }
    
    public void update(float deltaTime)
    {
        if (model.isMoving())
        {
            model.moveAlongDirectionalVector(deltaTime);

            float distance = targetPoint.distance(model.getPosition());

            if (distance > prevDistance)
                model.setIsMoving(false);

            prevDistance = distance;
        }
        
        if (hasBeenShot && !model.isMoving())
        {
            node.detachChild(model.getModel());
            isDead = true;
        }
    }
    
    public void setTargetPoint(Vector3f target)
    {
        Vector3f direction = target.subtract(model.getPosition());
        model.setDirection(direction);
        
        targetPoint = target.clone();
        model.setIsMoving(true);
        hasBeenShot = true;
    }
    
    public MoveableObject3d getMoveableObject3d()
    {
        return model;
    }
    
    public boolean isDead()
    {
        return isDead && hasBeenShot;
    }
}