package com.thecolony.tractus.player.ai.battle;

public class ShipTester {
	public static void main(String[] args)
	{
		Ship one = new Ship("heal");
		one.setEqualStats(5000);
		Ship two = new Ship("build");
		two.setEqualStats(50);
		Ship three = new Ship("three");
		three.setEqualStats(500);
		System.out.println(one.getName());
		Ship[] b = new Ship[3];
		b[0] = one;
		b[1] = two;
		b[2] = three;
		flotilla a = new flotilla(b, true);
		System.out.println(a.getShip(0).getName());
		System.out.println(a.getShip(1).getName());
		System.out.println(a.getShip(2).getName());
		a.sortByAttack();
		System.out.println(a.getShip(0).getName());
		System.out.println(a.getShip(1).getName());
		System.out.println(a.getShip(2).getName());
	}
}
