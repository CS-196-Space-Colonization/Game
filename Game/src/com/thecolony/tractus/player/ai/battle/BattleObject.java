package com.thecolony.tractus.player.ai.battle;

import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.DrawableObject3d;
import com.thecolony.tractus.player.ai.battle.ships.Ship;

public abstract class BattleObject implements Savable
{
    /** 0x00 ---- First Battle Stat */
    public static final int BATTLE_STAT_REG_POWER   		= 0x00;
    /** 0x01 */
    public static final int BATTLE_STAT_SP_POWER    		= 0x01;
    /** 0x02 */
    public static final int BATTLE_STAT_REG_DEFENSE 		= 0x02;
    /** 0x03 */
    public static final int BATTLE_STAT_SP_DEFENSE  		= 0x03;
    /** 0x04 */
    public static final int BATTLE_STAT_REG_ATTACK_COOLDOWN     = 0x04;
    /** 0x05 */
    public static final int BATTLE_STAT_SP_ATTACK_COOLDOWN      = 0x05;
    /** 0x06 */
    public static final int BATTLE_STAT_REG_ACCURACY		= 0x06;
    /** 0x07 */
    public static final int BATTLE_STAT_SP_ACCURACY		= 0x07;
    /** 0x08 */
    public static final int BATTLE_STAT_HP 			= 0x08;
    /** 0x09 */
    public static final int BATTLE_STAT_REG_RANGE 		= 0x09;
    /** 0x0A */
    public static final int BATTLE_STAT_SP_RANGE 		= 0x0A;
    /** 0x0B */
    public static final int BATTLE_STAT_MOVEMENT_SPEED		= 0x0B;
    /** 0x0C */
    public static final int BATTLE_STAT_REPAIR_ABILITY 		= 0x0C;
    /** 0x0D */
    public static final int BATTLE_STAT_BUILD_ABILITY		= 0x0D;
    /** 0x0E */
    public static final int BATTLE_STAT_TRANSPORT_ABILITY	= 0x0E;
    /** 0x0F */
    public static final int BATTLE_STAT_SP_ARMOR_STAT	 	= 0x0F;
    /** 0x10 */
    public static final int BATTLE_STAT_REG_ARMOR_STAT 		= 0x10;
    /** 0x11 */
    public static final int BATTLE_STAT_SP_WEAPON_STAT	 	= 0x11;
    /** 0x12 */
    public static final int BATTLE_STAT_REG_WEAPON_STAT 	= 0x12;

    protected String name;
    
    protected double[] qualities;
    protected int cost;
    protected String image;
    protected int crew;
    protected int ammo;

    private double x = 1;
    private double y = 0;// no longer going to change sp stat in this way.
    private double z = 1;

    static double HPfactor = 10;
    
    protected DrawableObject3d model;

    public BattleObject(double hp)
    {	
        name = "object";
        qualities = new double[BATTLE_STAT_REG_WEAPON_STAT + 1]; // BATTLE_STAT_REG_WEAPON_STAT is the largest value
        cost = 10;
        image = "[]";
        crew = 1;
        setEqualStats(10.0);
        qualities[BATTLE_STAT_HP] = hp;		
    }
    public BattleObject(String name)
    {
        this.name = name;
        qualities = new double[BATTLE_STAT_REG_WEAPON_STAT + 1]; // BATTLE_STAT_REG_WEAPON_STAT is the largest value
        cost = 10;
        image = "[]";
        crew = 1;
        setEqualStats(10.0);		
    }
    public BattleObject()
    {
        name = "object";
        qualities = new double[BATTLE_STAT_REG_WEAPON_STAT + 1]; // BATTLE_STAT_REG_WEAPON_STAT is the largest value
        cost = 10;
        image = "[]";
        crew = 1;
        setEqualStats(10.0);
    }
    public BattleObject(String nameOfShip, double[] stats, int Cost, String display, int Crew, int am)
    {
        name = nameOfShip;
        double[] fullStats = new double[19];
        for(int i = 0; i < 19; i++)
        {
            if(stats[i] == 0)
            {
                if(i == 4 || i == 5)
                    fullStats[i] = 5;
                else
                fullStats[i] = 10;
            }
            else
                fullStats[i] = stats[i];
        }
        qualities = fullStats;
        cost = Cost;
        image = display;
        crew = Crew;
        ammo = am;
    }

    public int getAmmo()
    {
        return ammo;
    }
    public void addAmmo(int amount)
    {
        ammo = ammo + amount;
    }

    public String getImage()
    {
        return image;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String newName)
    {
        name = newName;
    }

    public double[] getStats()
    {
        double[] temp = new double[19];
        for(int i = 0; i < temp.length; i++)
        {
                temp[i] = qualities[i];
        }
        return temp;
    }

    public double getBattleStat(int BATTLE_STAT)
    {
        return qualities[BATTLE_STAT];
    }

    public void setBattleStat(int BATTLE_STAT, double value)
    {
        if (BATTLE_STAT == BATTLE_STAT_HP)
        {
            qualities[BATTLE_STAT_HP] = qualities[BATTLE_STAT_HP] - value;
            if (qualities[BATTLE_STAT_HP] < 0)
                    qualities[BATTLE_STAT_HP] = 0;
        }
        else
            qualities[BATTLE_STAT] = value;
    }
    
    public Vector3f getPosition()
    {
        return model.getPosition();
    }

    public int getCost()
    {
        return cost;
    }
    public void setCost(int Cost)
    {
        cost = Cost;
    }

    public int getCrew()
    {
        return crew;
    }
    public void setCrew(int newCrew)
    {
        crew = newCrew;
    }

    public static void calcSpDamage(Ship attack, Ship defend)
    {
        double damage = ((attack.getBattleStat(BATTLE_STAT_SP_POWER) + attack.getBattleStat(BATTLE_STAT_SP_WEAPON_STAT)) / (defend.getBattleStat(BATTLE_STAT_SP_DEFENSE) + defend.getBattleStat(BATTLE_STAT_SP_ARMOR_STAT)) * HPfactor);
        defend.setBattleStat(BATTLE_STAT_HP, damage);
    }
    public static void calcRegDamage(Ship attack, Ship defend)
    {
        double damage = ((attack.getBattleStat(BATTLE_STAT_REG_POWER) + attack.getBattleStat(BATTLE_STAT_REG_WEAPON_STAT))) / (defend.getBattleStat(BATTLE_STAT_REG_DEFENSE) + defend.getBattleStat(BATTLE_STAT_REG_ARMOR_STAT)  * HPfactor);
        defend.setBattleStat(BATTLE_STAT_HP, damage);
    }
    public static void battle(Ship a, Ship b)
    {
        calcRegDamage(a, b);
        calcRegDamage(b, a);
        calcSpDamage(a, b);
        calcSpDamage(b, a);
    }

    public void setX(double X)
    {
        x = X * crew;
    }

    public void setz(double Z)
    {
        x = Z + crew;
    }

    public void setY(double Y)
    {
        y = Y;
    }
    public void setStaionaryObject(double[] qual)
    {
        setStats(qual);
    }
    public void setStatsHeal(double level)//level varies directly with cost, ex if a ship is twice the level
                                                                              //of one ship it will cost a little less than twice as much	
    {
        qualities[0] = level * 0;
        qualities[1] = (level - y) * 0;
        qualities[2] = level * 5 * x;
        qualities[3] = (level - y) * 5 * x;
        qualities[4] = level * 0;
        qualities[5] = (level - y) * 0;
        qualities[6] = level * 0;
        qualities[7] = (level - y) * 0;
        qualities[8] = level * 3 * x;
        qualities[9] = level * 0;
        qualities[10] = (level - y) * 0;
        qualities[11] = level * 4 * x;
        qualities[12] = level * 3 * x;
        qualities[13] = level * 0;
        qualities[14] = level * 2 * x;
        qualities[15] = (level - y) * 0;
        qualities[16] = level * 0;
        qualities[17] = (level - y) * 0;
        qualities[18] = level * 0;
    }
    public void setStatsBuild(double level)
    {
        qualities[0] = level * 0;
        qualities[1] = (level - y) * 0;
        qualities[2] = level * 5 * x;
        qualities[3] = (level - y) * 5 * x;
        qualities[4] = level * 0;
        qualities[5] = (level - y) * 0;
        qualities[6] = level * 0;
        qualities[7] = (level - y) * 0;
        qualities[8] = level * 3 * x;
        qualities[9] = level * 0;
        qualities[10] = (level - y) * 0;
        qualities[11] = level * 2 * x;
        qualities[12] = level * x;
        qualities[13] = level * 10 * x;
        qualities[14] = level * 10 * x;
        qualities[15] = (level - y) * 0;
        qualities[16] = level * 0;
        qualities[17] = (level - y) * 0;
        qualities[18] = level * 0;
    }
    public void setStatsAttack(double level)
    {
        qualities[0] = level * 10 * x;
        qualities[1] = (level - y) * 10 * x;
        qualities[2] = level * 2 * x;
        qualities[3] = (level - y) * 2 * x;
        qualities[4] = 5;
        qualities[5] = 5;
        qualities[6] = 1;
        qualities[7] = 1;
        qualities[8] = level * 5 * x;
        qualities[9] = level * 4 * z;
        qualities[10] = level * 4 * z;
        qualities[11] = level * 5 * x;
        qualities[12] = level * 0;
        qualities[13] = level * 0;
        qualities[14] = level * x;
        qualities[15] = level * 0;
        qualities[16] = level * 0;
        qualities[17] = level * 0;
        qualities[18] = level * 0;
    }
    public void setStatsDefend(double level)
    {
        qualities[0] = level * 2 * x;
        qualities[1] = (level - y) * 2 * x;
        qualities[2] = level * 10 * x;
        qualities[3] = (level - y) * 10 * x;
        qualities[4] = 5;
        qualities[5] = 5;
        qualities[6] = 1;
        qualities[7] = 1;
        qualities[8] = level * 5 * x;
        qualities[9] = level * 4 * z;
        qualities[10] = level * 4 * z;
        qualities[11] = level * 5 * x;
        qualities[12] = level * 0;
        qualities[13] = level * 0;
        qualities[14] = level * x;
        qualities[15] = level * 0;
        qualities[16] = level * 0;
        qualities[17] = level * 0;
        qualities[18] = level * 0;
    }
    public void setStatsSpeed(double level)
    {
        qualities[0] = level * 4 * x;
        qualities[1] = (level - y) * 4 * x;
        qualities[2] = level * 4 * x;
        qualities[3] = (level - y) * 4 * x;
        qualities[4] = 2;
        qualities[5] = 2;
        qualities[6] = 1;
        qualities[7] = 1;
        qualities[8] = level * 5 * x;
        qualities[9] = level * 3 * z;
        qualities[10] = level * 3 * z;
        qualities[11] = level * 5 * x;
        qualities[12] = level * 0;
        qualities[13] = level * 0;
        qualities[14] = level * x;
        qualities[15] = level * 0;
        qualities[16] = level * 0;
        qualities[17] = level * 0;
        qualities[18] = level * 0;
    }
    public void setStatsRange(double level)
    {
        qualities[0] = level * 5 * x;
        qualities[1] = (level - y) * 5 * x;
        qualities[2] = level * 5 * x;
        qualities[3] = (level - y) * 5 * x;
        qualities[4] = 5;
        qualities[5] = 5;
        qualities[6] = 1;
        qualities[7] = 1;
        qualities[8] = level * 5 * x;
        qualities[9] = level * 10 * z;
        qualities[10] = level * 10 * z;
        qualities[11] = level * 7 * x;
        qualities[12] = level * 0;
        qualities[13] = level * 0;
        qualities[14] = level * x;
        qualities[15] = level * 0;
        qualities[16] = level * 0;
        qualities[17] = level * 0;
        qualities[18] = level * 0;
    }
    public void setStatsBalance(double level)
    {
        qualities[0] = level * 6 * x;
        qualities[1] = (level - y) * 6 * x;
        qualities[2] = level * 6 * x;
        qualities[3] = (level - y) * 6 * x;
        qualities[4] = 4;
        qualities[5] = 4;
        qualities[6] = 1;
        qualities[7] = 1;
        qualities[8] = level * 5 * x;
        qualities[9] = level * 4 * z;
        qualities[10] = level * 4 * z;
        qualities[11] = level * 5 * x;
        qualities[12] = level * 0;
        qualities[13] = level * 0;
        qualities[14] = level * x;
        qualities[15] = level * 0;
        qualities[16] = level * 0;
        qualities[17] = level * 0;
        qualities[18] = level * 0;
    }

    public void setEqualStats(double stat)
    {
        double[] temp = new double[19];
        for(int i = 0; i < temp.length; i++)
        {
                temp[i] = stat;
        }
        temp[6] = 1;
        temp[7] = 1;
        qualities = temp;
    }
    public void setStats(double[] stat)
    {
        for (int i = 0; i < qualities.length; i++)
                qualities[i] = stat[i];
    }
    //do not intend to use this setCustom method in anything but testing
    public void setStatsCustom(double regPower, double spPower, double regDefence, double spDefence, double regCoolDown, double spCooldown, 
                    double regWeaponsAccuracy, double spWeaponsAccuracy, double HP,	double regAttackRange, double spAttackRange, double MovementSpeed, 
                    double Repair, double build, double transport, double spArmor, double regArmor, double spWeapon, double regWeapon)
    {
        qualities[0] = regPower;
        qualities[1] = spPower;
        qualities[2] = regDefence;
        qualities[3] = spDefence;
        qualities[4] = regCoolDown;
        qualities[5] = spCooldown;
        qualities[6] = regWeaponsAccuracy;
        qualities[7] = spWeaponsAccuracy;
        qualities[8] = HP;
        qualities[9] = regAttackRange;
        qualities[10] = spAttackRange;
        qualities[11] = MovementSpeed;
        qualities[12] = Repair;
        qualities[13] = build;
        qualities[14] = transport;
        qualities[15] = spArmor;
        qualities[16] = regArmor;
        qualities[17] = spWeapon;
        qualities[18] = regWeapon;
    }
    
    public DrawableObject3d getDrawableObject3d()
    {
        return model;
    }
    
    public String getDisplayInfo()
    {
        return "Name: " + name + "\n "
                + "Battle Stats:\n"
                + "  HP: " + qualities[BATTLE_STAT_HP] + "\n"
                + "  Reg Power: " + qualities[BATTLE_STAT_REG_POWER] + "\n"
                + "  Sp Power: " + qualities[BATTLE_STAT_SP_POWER] + "\n"
                + "  Reg Defense: " + qualities[BATTLE_STAT_REG_DEFENSE] + "\n"
                + "  Sp Defense: " + qualities[BATTLE_STAT_SP_DEFENSE] + "\n"
                + "  Reg Attack Cooldown: " + qualities[BATTLE_STAT_REG_ATTACK_COOLDOWN] + "\n"
                + "  Sp Attack Cooldown: " + qualities[BATTLE_STAT_SP_ATTACK_COOLDOWN] + "\n"
                + "  Reg Accuracy: " + qualities[BATTLE_STAT_REG_ACCURACY] + "\n"
                + "  Sp Accuracy: " + qualities[BATTLE_STAT_SP_ACCURACY] + "\n"
                + "  Reg Range: " + qualities[BATTLE_STAT_REG_RANGE] + "\n"
                + "  Sp Range: " + qualities[BATTLE_STAT_SP_RANGE] + "\n"
                + "  Reg Armor Stat: " + qualities[BATTLE_STAT_REG_ARMOR_STAT] + "\n"
                + "  Sp Armor Stat: " + qualities[BATTLE_STAT_SP_ARMOR_STAT] + "\n"
                + "  Reg Weapon Stat: " + qualities[BATTLE_STAT_REG_WEAPON_STAT] + "\n"
                + "  Sp Weapon Stat: " + qualities[BATTLE_STAT_SP_WEAPON_STAT] + "\n"
                + "  Repair Ability: " + qualities[BATTLE_STAT_REPAIR_ABILITY] + "\n"
                + "  Transport Ability: " + qualities[BATTLE_STAT_TRANSPORT_ABILITY] + "\n"
                + "  Build Ability: " + qualities[BATTLE_STAT_BUILD_ABILITY] + "\n"
                + "  Movement Speed: " + qualities[BATTLE_STAT_MOVEMENT_SPEED];
    }
}