/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thecolony.tractus.player.ai.battle.ships;

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
    private boolean full;
    private double xLocation;
    private double yLocation;
    
    public Flotilla()
    {
        flotilla = new Ship[0];
        full = false;
        xLocation = 0;
        yLocation = 0;
        names = new String[flotilla.length];
    }
    public Flotilla(double x, double y)
    {
        flotilla = new Ship[0];
        full = false;
        xLocation = x;
        yLocation = y;
        names = new String[flotilla.length];
    }
    public Flotilla(Ship[] group, boolean Full)
    {
        flotilla = group;
        full = Full;
        xLocation = 0;
        yLocation = 0;
        names = new String[flotilla.length];
    }
    public Flotilla(Ship[] group)
    {
        flotilla = group;
        full = false;
        xLocation = 0;
        yLocation = 0;
        names = new String[flotilla.length];
    }
    public Flotilla(Ship[] group, boolean Full, double x, double y)
    {
        flotilla = group;
        full = Full;
        xLocation = x;
        yLocation = y;
        names = new String[flotilla.length];
    }
    public String getName(int place)
    {
        return flotilla[place].getName();
    }
    public Ship[] getFlotilla()
    {
        return flotilla;
    }
    public double getX()
    {
        return xLocation;
    }
    public double getY()
    {
        return yLocation;
    }
    public void setX(double change)
    {
        xLocation = xLocation + change;
    }
    public void setY(double change)
    {
        yLocation = yLocation + change;
    }
    public boolean getFull()
    {
        return full;
    }
    public void changeFull()
    {
        full = !full;
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
}