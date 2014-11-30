package com.thecolony.tractus.player.ai.battle;

import com.jme3.math.Vector3f;

public class ShipTester {
	public static void main(String[] args)
	{
            Ship one = new Ship(Ship.SHIP_TYPE.Fighter, "heal", Vector3f.ZERO, new double[19], 0, "heal", 0, 0, 0.0);
            //Ship one = new Ship("heal");
            one.setEqualStats(5000);
            Ship two = new Ship(Ship.SHIP_TYPE.Fighter, "build", Vector3f.ZERO, new double[19], 0, "build", 0, 0, 0.0);
            //Ship two = new Ship("build");
            two.setEqualStats(50);
            Ship three = new Ship(Ship.SHIP_TYPE.Fighter, "three", Vector3f.ZERO, new double[19], 0, "three", 0, 0, 0.0);
            //Ship three = new Ship("three");
            three.setEqualStats(500);
            System.out.println(one.getName());
            Ship[] b = new Ship[3];
            b[0] = one;
            b[1] = two;
            b[2] = three;
            Flotilla a = new Flotilla(b, true);
            System.out.println(a.getShip(0).getName());
            System.out.println(a.getShip(1).getName());
            System.out.println(a.getShip(2).getName());
            a.sortByAttack();
            System.out.println(a.getShip(0).getName());
            System.out.println(a.getShip(1).getName());
            System.out.println(a.getShip(2).getName());
	}
}
