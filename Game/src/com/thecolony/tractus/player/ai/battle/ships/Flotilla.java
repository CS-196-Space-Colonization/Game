/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thecolony.tractus.player.ai.battle.ships;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.WireBox;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameGraphics;
import com.thecolony.tractus.player.ai.battle.BattleObject;

/**
 *
 * @author Joe Pagliuco
 */
public class Flotilla
{
    private String[] names;
    private double[] qualities;
    private int worth;
    private String[] image;
    private int crew;
    private Ship[] flotilla;
    private boolean isFull;
    private float movementSpeed; // Slowest ship's movement ship
    
    private Vector3f centerPosition;
    private Geometry wireBoxGeometry;
    private Vector3f targetPoint;
    
    private boolean isSelected;
    
    private String name;
    
    public Flotilla()
    {
        initialize(new Ship[0], Vector3f.ZERO, false, "Empty Name");
    }
    public Flotilla(Vector3f centerPosition, String name)
    {
        initialize(new Ship[0], centerPosition, false, name);
    }
    public Flotilla(Ship[] group, boolean isFull, String name)
    {
        initialize(group, Vector3f.ZERO, isFull, name);
    }
    public Flotilla(Ship[] group, String name)
    {
        initialize(group, Vector3f.ZERO, false, name);
    }
    public Flotilla(Ship[] group, boolean isFull, Vector3f centerPosition, String name)
    {
        initialize(group, centerPosition, isFull, name);
    }
    
    private void initialize(Ship[] group, Vector3f centerPosition, boolean isFull, String name)
    {
        flotilla = group;
        this.centerPosition = centerPosition;
        this.isFull = isFull;
        names = new String[flotilla.length];
        
        if (flotilla.length != 0)
            setShipPositions();
        
        isSelected = false;
        
        this.name = name;
        
        setMovementSpeed();
    }
    
    private void setShipPositions()
    {
        // Temporary way to do it...
        int sideLength = (int)Math.sqrt(flotilla.length);
        if (sideLength * sideLength != flotilla.length)
            sideLength++;
        
        float maxExtent = 0;
        for (int i = 0; i < flotilla.length; i++)
        {
            BoundingBox b = (BoundingBox)flotilla[i].getDrawableObject3d().getModel().getWorldBound();
            maxExtent = Math.max(maxExtent, b.getXExtent());
            maxExtent = Math.max(maxExtent, b.getZExtent());
        }
        maxExtent *= 2.0f * 1.5f;
        
        int halfSideLength = sideLength >> 1;
        int offset = (sideLength % 2 == 0) ? 0 : 1;
        int index = 0;
        for (int i = -halfSideLength; i < halfSideLength + offset; i++)
        {
            boolean stop = false;
            for (int j = -halfSideLength; j < halfSideLength + offset; j++)
            {
                flotilla[index++].getDrawableObject3d().getModel().setLocalTranslation(centerPosition.add(new Vector3f(maxExtent, 0.0f, maxExtent).mult(new Vector3f(i, 1, j))));
                stop = index == flotilla.length;
                if (stop)
                    break;
            }
            if (stop)
                break;
        }
        
        float height = 0.0f;
        for (int i = 0; i < flotilla.length; i++)
            height = Math.max(height, ((BoundingBox)flotilla[i].getDrawableObject3d().getModel().getWorldBound()).getYExtent());
        height *= 2.0f;
        
        BoundingBox boundingBox = new BoundingBox(centerPosition, maxExtent * halfSideLength, height, maxExtent * halfSideLength);
        WireBox wireBox = new WireBox();
        wireBox.fromBoundingBox(boundingBox);
        wireBoxGeometry = new Geometry("Flotilla WireBox Geometry", wireBox);
        wireBoxGeometry.setMaterial(GameGraphics.getDefaultWhiteMaterial());
        wireBoxGeometry.setLocalTranslation(centerPosition);
        wireBoxGeometry.scale(1.5f);
    }
    
    private void setMovementSpeed()
    {
        if (flotilla.length > 0)
            movementSpeed = (float)flotilla[0].getBattleStat(BattleObject.BATTLE_STAT_MOVEMENT_SPEED);
        for (int i = 1; i < flotilla.length; i++)
            movementSpeed = (float)Math.min(movementSpeed, flotilla[i].getBattleStat(BattleObject.BATTLE_STAT_MOVEMENT_SPEED));
    }
    
    public String getName()
    {
        return name;
    }    
    public String getName(int place)
    {
        return flotilla[place].getName();
    }
    
    public Ship[] getFlotilla()
    {
        return flotilla;
    }
    
    public Vector3f getCenterPosition()
    {
        return centerPosition;
    }
    
    public BoundingBox getBoundingBox()
    {
        return (BoundingBox) wireBoxGeometry.getWorldBound();
    }
    
    public Geometry getWireBoxGeometry()
    {
        return wireBoxGeometry;
    }
    
    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    public boolean isSelected()
    {
        return isSelected;
    }
    
    public boolean getFull()
    {
        return isFull;
    }
    public void changeFull()
    {
        isFull = !isFull;
    }
    
    public String[] getImage()
    {
        return image;
    }
    public void setImage()
    {
        String[] temp = new String[flotilla.length];
        for(int i = 0; i < flotilla.length; i++)
        {
            temp[i] = getShip(i).getImage();
        }
        image = temp;
    }
    
    public void setFlotillaStats()
    {
        if(flotilla.length > 0)
        {
            names = new String[flotilla.length];
            worth = 0;
            crew = 0;
            for(int i = 0; i < 19; i++)
            {
                qualities[i] = 0;
            }
            for(int i = 0; i < flotilla.length; i++)
            {
                names[i] = getName(i);
                for(int j = 0; j < 19; j++)
                {
                    qualities[j] = qualities[j] + flotilla[i].getBattleStat(BattleObject.BATTLE_STAT_REG_POWER + j); // Get jth battle stat
                }
                worth = worth + flotilla[i].getCost();
                crew = crew + flotilla[i].getCrew();	
                setImage();
            }
            qualities[4] = qualities[4] / flotilla.length;
            qualities[5] = qualities[5] / flotilla.length;
        }
    }
    public double getFlotillaStats()
    {
        double total = 0;
        for(int i = 0; i < 19; i++)
            total = total + qualities[i];
        return total;
    }
    
    public double getFlotillaStat(int BATTLE_STAT)
    {
        double total = 0.0;
        for (int i = 0; i < flotilla.length; i++)
            total += flotilla[i].getBattleStat(BATTLE_STAT);
        
        return total;
    }
    
    public Ship getShip(int a)
    {
        return flotilla[a];
    }
    public void addShip(Ship one)
    {
        Ship[] temp = new Ship[flotilla.length + 1];
        for(int i = 0; i < flotilla.length; i++){
            temp[i] = flotilla[i];
        }
        temp[flotilla.length] = one;
        flotilla = temp;
    }
    
    public void checkRemoveShip()
    {
        Ship[] temp = flotilla;
        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i].getBattleStat(BattleObject.BATTLE_STAT_HP) == 0)
            {
                boolean passed = false;
                Ship[] temp2 = new Ship[temp.length - 1];
                for(int j = 0; j < temp.length; j++)
                {
                    if( j == i)
                    {
                        j++;
                        passed = true;
                    }
                    if (passed)
                        temp2[j - 1] = temp[j];
                    else
                        temp2[j] = temp[j];
                }
                temp = temp2;
            }
        }
        flotilla = temp;
    }
    
    public void damageShip(int ship, double damage)
    {
        flotilla[ship].setBattleStat(BattleObject.BATTLE_STAT_HP, damage);
    }
    public void removeShip(int place)
    {
        boolean passed = false;
        Ship[] temp = new Ship[flotilla.length - 1];
        for(int j = 0; j < temp.length; j++)
        {
            if( j == place)
            {
                j++;
                passed = true;
            }
            if (passed)
                temp[j - 1] = flotilla[j];
            else
                temp[j] = flotilla[j];
        }
        flotilla = temp;
        
        setMovementSpeed();
    }
    
    public double getHP()
    {
        double total = 0;
        for(int i = 0; i < flotilla.length; i++)
            total = total + flotilla[i].getBattleStat(BattleObject.BATTLE_STAT_HP);
        return total;
    }
    
    static double HPfactor = 10;
    public static void calcSpDamage(Flotilla attack, Flotilla defend)
    {
        double damage = ((attack.getBattleStat(BattleObject.BATTLE_STAT_SP_POWER) + attack.getBattleStat(BattleObject.BATTLE_STAT_SP_WEAPON_STAT))) /
                (defend.getBattleStat(BattleObject.BATTLE_STAT_SP_DEFENSE) + defend.getBattleStat(BattleObject.BATTLE_STAT_SP_ARMOR_STAT)) * HPfactor;
        //double damage = ((attack.getSpPower() + attack.getSpWeaponStat())) / (defend.getSpDefence() + defend.getSpArmorStat()) * HPfactor;
        defend.setHP(damage);
    }
    public static void calcRegDamage(Flotilla attack, Flotilla defend)
    {
        double damage = ((attack.getBattleStat(BattleObject.BATTLE_STAT_REG_POWER) + attack.getBattleStat(BattleObject.BATTLE_STAT_REG_WEAPON_STAT))) /
                (defend.getBattleStat(BattleObject.BATTLE_STAT_REG_DEFENSE) + defend.getBattleStat(BattleObject.BATTLE_STAT_REG_ARMOR_STAT)) * HPfactor;
        //double damage = ((attack.getRegPower() + attack.getRegWeaponStat())) / (defend.getRegDefence() + defend.getRegArmorStat()) * HPfactor;
        defend.setHP(damage);
    }
    public void setHP(double damage)
    {
        double temp = damage;
        double shipHP = 0;
        for(int i = 0; i < flotilla.length; i++)
        {
            shipHP = getShip(i).getBattleStat(BattleObject.BATTLE_STAT_HP);
            if(temp > shipHP)
            {
                temp = temp - shipHP;
                getShip(i).setBattleStat(BattleObject.BATTLE_STAT_HP, shipHP);
            }
            else
            {
                getShip(i).setBattleStat(BattleObject.BATTLE_STAT_HP, temp);
                temp = 0;
                break;
            }
        }
    }
    
    public static void battle(Flotilla a, Flotilla d)
    {
        if(a.getFlotilla().length > 0 && d.getFlotilla().length > 0)
        {
            a.setFlotillaStats();
            d.setFlotillaStats();
            calcRegDamage(a, d);
            calcRegDamage(d, a);
            calcSpDamage(a, d);
            calcSpDamage(d, a);
            a.checkRemoveShip();
            d.checkRemoveShip();
        }
    }
        
    public void setShipsBattleStat(int shipIndex, int BATTLE_STAT, double quality)
    {
        if (BATTLE_STAT == BattleObject.BATTLE_STAT_REG_ACCURACY)
            setFlotillaStats(); // Not sure if this should be here - Joe
        getShip(shipIndex).setBattleStat(BATTLE_STAT, quality);
    }
    public double getBattleStat(int BATTLE_STAT)
    {
        setFlotillaStats();
        return qualities[BATTLE_STAT];
    }
    
    public void sortByHP()
    {
        Ship[] temp = new Ship[flotilla.length];
        Ship[] temp2 = flotilla;
        for(int j = 0; j < flotilla.length; j++)
        {
            int place = 0;
            double smallest = temp2[place].getBattleStat(BattleObject.BATTLE_STAT_HP);
            for(int i = 0; i < temp2.length; i++)
            {
                if(temp2[i].getBattleStat(BattleObject.BATTLE_STAT_HP) < smallest)
                        place = i;
            }
            temp[j] = temp2[place];
            Ship temp3 = temp2[temp2.length - 1];
            temp2[place] = temp3;
            Ship[] temp4 = temp2;
            temp2 = new Ship[temp2.length - 1];
            for(int a = 0; a < temp2.length; a++)
                temp2[a] = temp4[a];
        }
        flotilla = temp;
    }
    public void sortByAttack()
    {
        Ship[] temp = new Ship[flotilla.length];
        Ship[] temp2 = flotilla;
        for(int j = 0; j < flotilla.length; j++)
        {
            int place = 0;
            double smallest = temp2[place].getBattleStat(BattleObject.BATTLE_STAT_REG_POWER);
            for(int i = 0; i < temp2.length; i++)
            {
                if(temp2[i].getBattleStat(BattleObject.BATTLE_STAT_REG_POWER) > smallest)
                    place = i;
            }
            temp[j] = temp2[place];
            Ship temp3 = temp2[temp2.length - 1];
            temp2[place] = temp3;
            Ship[] temp4 = temp2;
            temp2 = new Ship[temp2.length - 1];
            for(int a = 0; a < temp2.length; a++)
               temp2[a] = temp4[a];
        }
        flotilla = temp;
    }
    
    /**
     * Update the flotilla.
     * @param deltaTime The time elapsed between each frame.
     */
    public void update(float deltaTime)
    {        
        for (int i = 0; i < flotilla.length; i++)
            flotilla[i].update(deltaTime);
        
        if (isMoving() && !isRotating())
        {
            Vector3f direction = targetPoint.subtract(centerPosition).normalize();
            wireBoxGeometry.move(direction.mult(movementSpeed * deltaTime));
            centerPosition.addLocal(direction.mult(movementSpeed * deltaTime));
        }
    }
    
    /**
     * Sets the target transformation point for the flotilla.
     * @param targetPoint The point to transform to.
     * @param moveTo Whether or not the flotilla should move to the target
     * point in addition to rotating to it.
     */
    public void setTargetPoint(Vector3f targetPoint, boolean moveTo)
    {
        this.targetPoint = targetPoint;
        Vector3f movementVector = targetPoint.subtract(centerPosition);
        System.out.println(flotilla.length);
        for (int i = 0; i < flotilla.length; i++)
            flotilla[i].setTargetPoint(flotilla[i].getPosition().add(movementVector), moveTo);
    }
    
    /**
     * @return true if any ship in the flotilla is transforming, false otherwise.
     */
    public boolean isTransforming()
    {        
        boolean isTransforming = false;
        for (int i = 0; i < flotilla.length; i++)
        {
            isTransforming = flotilla[i].isTransforming();
            if (isTransforming)
                break;
        }
        
        return isTransforming;
    }
    
    public boolean isMoving()
    {
        boolean isMoving = false;
        for (int i = 0; i < flotilla.length; i++)
        {
            isMoving = flotilla[i].isTransforming();
            if (isMoving)
                break;
        }
        
        return isMoving;
    }
    
    public boolean isRotating()
    {
        boolean isRotating = false;
        for (int i = 0; i < flotilla.length; i++)
        {
            isRotating = flotilla[i].isRotating();
            if (isRotating)
                break;
        }
        
        return isRotating;
    }
    
     public static void flotillaBattle(Flotilla a, Flotilla b, double time)
	{
		double rt1 = 0;
                double rt2 = 0;
                double st1 = 0;
                double st2 = 0;
                
		boolean done = false;
		while(!done)
		{
                    a.setFlotillaStats();
                    b.setFlotillaStats();
                    rt1 = rt1 + time;
                    rt2 = rt2 + time;
                    st1 = st1 + time;
                    st2 = st2 + time;
			if(a.getBattleStat(Ship.BATTLE_STAT_HP) == 0 || b.getBattleStat(Ship.BATTLE_STAT_HP) == 0)
                        {
				done = true;
                        }
                        else if(rt1 >= a.getBattleStat(4))
                        {
                            rt1 = rt1 - a.getBattleStat(4);
                            calcRegDamage(a, b);
                        }
                        else if(rt2 >= b.getBattleStat(4))
                        {
                            rt2 = rt2 - b.getBattleStat(4);
                            calcRegDamage(b, a);
                        }
                        else if(st1 >= a.getBattleStat(5))
                        {
                            st1 = st1 - a.getBattleStat(5);
                            calcSpDamage(a, b);
                        }
                        else if(st2 >= b.getBattleStat(5))
                        {
                            st2 = st2 - b.getBattleStat(5);
                            calcSpDamage(b, a);
                        }
		}
        }
    
    public String getDisplayInfo()
    {
        return "Flotilla:\n" + " Name: " + name + "\n "
                + "Battle Stats:\n"
                + "  HP: " + getFlotillaStat(BattleObject.BATTLE_STAT_HP) + "\n"
                + "  Reg Power: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_POWER) + "\n"
                + "  Sp Power: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_POWER) + "\n"
                + "  Reg Defense: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_DEFENSE) + "\n"
                + "  Sp Defense: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_DEFENSE) + "\n"
                + "  Reg Attack Cooldown: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_ATTACK_COOLDOWN) + "\n"
                + "  Sp Attack Cooldown: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_ATTACK_COOLDOWN) + "\n"
                + "  Reg Accuracy: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_ACCURACY) + "\n"
                + "  Sp Accuracy: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_ACCURACY) + "\n"
                + "  Reg Range: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_RANGE) + "\n"
                + "  Sp Range: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_RANGE) + "\n"
                + "  Reg Armor Stat: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_ARMOR_STAT) + "\n"
                + "  Sp Armor Stat: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_ARMOR_STAT) + "\n"
                + "  Reg Weapon Stat: " + getFlotillaStat(BattleObject.BATTLE_STAT_REG_WEAPON_STAT) + "\n"
                + "  Sp Weapon Stat: " + getFlotillaStat(BattleObject.BATTLE_STAT_SP_WEAPON_STAT) + "\n"
                + "  Repair Ability: " + getFlotillaStat(BattleObject.BATTLE_STAT_REPAIR_ABILITY) + "\n"
                + "  Transport Ability: " + getFlotillaStat(BattleObject.BATTLE_STAT_TRANSPORT_ABILITY) + "\n"
                + "  Build Ability: " + getFlotillaStat(BattleObject.BATTLE_STAT_BUILD_ABILITY) + "\n"
                + "  Movement Speed: " + movementSpeed;
    }
    
    public void move(Vector3f offset)
    {
        for (int i = 0; i < flotilla.length; i++)
            flotilla[i].move(offset);
        centerPosition.addLocal(offset);
        wireBoxGeometry.move(offset);
    }
}