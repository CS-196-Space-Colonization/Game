package com.thecolony.tractus.military.ships;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireSphere;
import com.thecolony.tractus.graphics.GraphicsManager;
import com.thecolony.tractus.military.battle.MoveableBattleObject;
import java.util.ArrayList;

/**
 * A class used to represent a group of selected objects.
 * @author Joe Pagliuco
 */
public final class SelectedFamily
{
    private ArrayList<MoveableBattleObject> objects;
    
    private Vector3f centerPosition;
    
    private Node node;
    private Geometry wireFrameCenter;
    
    private boolean isTransforming;
    
    public SelectedFamily(Node node)
    {
        objects = new ArrayList<MoveableBattleObject>();
        centerPosition = new Vector3f();
        this.node = node;
        
        createWireFrameCenter();
    }
    public SelectedFamily(Node node, MoveableBattleObject... objects)
    {
        this.objects = new ArrayList<MoveableBattleObject>();
        
        this.node = node;
        
        createWireFrameCenter();
        
        addObjects(objects);
    }
    
    private void createWireFrameCenter()
    {
        WireSphere s = new WireSphere(.5f);
        wireFrameCenter = new Geometry("Selected Family WireSphere", s);
        wireFrameCenter.setMaterial(GraphicsManager.getDefaultWhiteMaterial());
    }
    
    public void addObjects(MoveableBattleObject... objects)
    {
        for (int i = 0; i < objects.length; i++)
        {
            if (objects[i].isSelected())
                continue;
            
            this.objects.add(objects[i]);
            objects[i].setIsSelected(true);
            node.attachChild(objects[i].getWireBoxGeometry());
        }
        
        calculateCenterPosition();
    }
    
    public void removeObjects(int index)
    {
        removeObjects(index, index+1);
    }
    /**
     * @param beginIndex The beginning index (inclusive).
     * @param endIndex The end index (exclusive).
     */
    public void removeObjects(int beginIndex, int endIndex)
    {
        for (int i = beginIndex; i < endIndex; i++)
        {
            node.detachChild(objects.get(i).getWireBoxGeometry());
            objects.get(i).setIsSelected(false);
        }
        
        ArrayList<MoveableBattleObject> newObjects = new ArrayList<MoveableBattleObject>();
        for (int i = 0; i < objects.size(); i++)
        {
            if (i == beginIndex)
                i = endIndex;
            newObjects.add(newObjects.get(i));
        }
        objects = newObjects;
        calculateCenterPosition();
    }
    
    public void clearFamily()
    {
        for (int i = 0; i < objects.size(); i++)
        {
            node.detachChild(objects.get(i).getWireBoxGeometry());
            objects.get(i).setIsSelected(false);
        }
        objects = new ArrayList<MoveableBattleObject>();
        centerPosition = Vector3f.ZERO;
        node.detachChild(wireFrameCenter);
    }
    
    private void calculateCenterPosition()
    {        
        float x = 0.0f;
        float z = 0.0f;

        int size = objects.size();
        for (int i = 0; i < size; i++)
        {
            MoveableBattleObject b = objects.get(i);
            x += b.getPosition().x;
            z += b.getPosition().z;
        }

        x /= size;
        z /= size;

        centerPosition = new Vector3f(x, 0.0f, z);
        wireFrameCenter.setLocalTranslation(centerPosition);
        if (!node.hasChild(wireFrameCenter))
            node.attachChild(wireFrameCenter);
    }
    
    public ArrayList<MoveableBattleObject> getFamily()
    {
        return objects;
    }
    
    public Vector3f getCenterPosition()
    {
        return centerPosition;
    }
    
    public void setTargetPoint(Vector3f targetPoint, boolean moveTo)
    {
        Vector3f directionalVector = targetPoint.subtract(centerPosition);
        
        for (int i = 0; i < objects.size(); i++)
            objects.get(i).setTargetPoint(objects.get(i).getPosition().add(directionalVector), moveTo);
        isTransforming = true;
    }
    
    public void update(float deltaTime)
    {
        if (isTransforming)
            calculateCenterPosition();
    }
}