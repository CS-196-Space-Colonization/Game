/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.military.ships;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.graphics.drawableobjects.DrawableObject3d;
import com.thecolony.tractus.military.battle.BattleObject;

/**
 *
 * @author Joe Pagliuco
 */
public class ShipFactory extends BattleObject
{

    private int money;
    private int peoples;
    private static int costOfFactory = 100;
    private double xLocation;
    private double yLocation;
    private Fleet troops;
    private double hp;
    
    private DrawableObject3d model;

    public ShipFactory()
    {
        super();
        money = 0;
        peoples = 0;
        troops = new Fleet();
        xLocation = 0;
        yLocation = 0;
        hp = 10;
    }

    public ShipFactory(Fleet f, int Money, int Person, double x, double y, double HP)
    {
        super(HP);
        troops = f;
        money = Money;
        peoples = Person;
        xLocation = x;
        yLocation = y;
        hp = HP;
    }

    public ShipFactory(int Money, int Person, double x, double y, double HP)
    {
        super(HP);
        troops = new Fleet();
        money = Money;
        peoples = Person;
        xLocation = x;
        yLocation = y;
        hp = HP;
    }

    public ShipFactory(int Money, int Person)
    {
        super(100);
        troops = new Fleet();
        money = Money;
        peoples = Person;
        xLocation = 0;
        yLocation = 0;
        hp = 100;
    }

    public void setHp(double newhp)
    {
        hp = newhp;
    }

    public double getHp()
    {
        return hp;
    }

    public Fleet getFleet()
    {
        return troops;
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

    public int getCost()
    {
        return costOfFactory;
    }

    public void setCost(int cost)
    {
        costOfFactory = cost;
    }

    public void addMoney(int $)
    {
        money = money + $;
    }

    public void addWorkers(int worker)
    {
        peoples = peoples + worker;
    }

    public static void produceShip(Ship Ship, Flotilla Flotilla)
    {
        Flotilla.addShip(Ship);
    }

    public void produceFlotilla(Ship[] floatil, Fleet f)
    {
        Flotilla one = new Flotilla(floatil, false, Vector3f.ZERO, "name", true);
        f.addFlotilla(one);
    }

    public void produceFlotilla(Flotilla floatil, Fleet f)
    {
        f.addFlotilla(floatil);
    }

    public static Flotilla createBaseFlotilla(boolean capital, int fighters, int frigate, int cruiser,
            String Type, int level)//consists of one capital ship and an amount of fighters
    {
        Flotilla one = new Flotilla();
        Ship bigest = new Specific_Ship("Capital", Type, level);
        Ship small = new Specific_Ship("Fighter", Type, level);
        Ship big = new Specific_Ship("Frigate", Type, level);
        Ship biger = new Specific_Ship("Cruiser", Type, level);
        if (capital)
        {
            produceShip(bigest, one);
        }
        for (int i = 0; i < fighters; i++)
        {
            produceShip(small, one);
        }
        for (int i = 0; i < frigate; i++)
        {
            produceShip(big, one);
        }
        for (int i = 0; i < cruiser; i++)
        {
            produceShip(biger, one);
        }
        return one;
    }

    public static Flotilla[] createFleet(String type, int flotillas)
    {
        Flotilla[] one = new Flotilla[flotillas + 1];
        Flotilla two = createBaseFlotilla(true, 100, 20, 5, type, 1);
        for (int i = 0; i < flotillas; i++)
        {
            one[i] = two;
        }
        return one;
    }

    @Override
    public Vector3f getPosition()
    {
        return model.getPosition();
    }
}