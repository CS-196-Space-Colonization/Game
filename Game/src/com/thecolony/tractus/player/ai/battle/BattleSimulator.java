
package com.thecolony.tractus.player.ai.battle;

public class BattleSimulator {
	public static void singleBattleSim(Ship a, Ship b )
	{
		boolean done = false;
		while(!done)
		{
			System.out.println("ship a's HP remaining = " +a.getHP());
			System.out.println("ship b's HP remaining = " + b.getHP());
			if(a.getHP() == 0)
			{
				System.out.println("b wins");
				done = true;
			}
			if(b.getHP() == 0)
			{
				System.out.println("a wins");
				done = true;
			}
			Ship.battle(a, b);
		}
		
	}
	public static void flotillaBattleSim(flotilla a, flotilla b )
	{
		int move = 0;
		boolean done = false;
		while(!done)
		{
			move++
			;
			System.out.println("move: " + move);
			System.out.println("ship a's HP remaining = " + a.getHP());
			System.out.println("ship b's HP remaining = " + b.getHP());
			if(a.getHP() == 0)
			{
				System.out.println("b wins");
				done = true;
			}
			if(b.getHP() == 0)
			{
				System.out.println("a wins");
				done = true;
			}
			flotilla.battle(a, b);
		}
		System.out.println("move: " + move);
		
	}
	public static void main(String[] args)
	{
		flotilla one = new flotilla();
		one.addShip(new Ship());
		flotilla two = new flotilla();
		two.addShip(new Ship());
		singleBattleSim(one.getShip(0), two.getShip(0));
	}
}
