
package com.thecolony.tractus.player.ai.battle;

public class BattleSimulator {
	public static void singleBattleSim(Ship a, Ship b )
	{
		boolean done = false;
		while(!done)
		{
			System.out.println("ship a's HP remaining = " +a.getBattleStat(Ship.BATTLE_STAT_HP));
			System.out.println("ship b's HP remaining = " + b.getBattleStat(Ship.BATTLE_STAT_HP));
			if(a.getBattleStat(Ship.BATTLE_STAT_HP) == 0)
			{
				System.out.println("b wins");
				done = true;
			}
			if(b.getBattleStat(Ship.BATTLE_STAT_HP) == 0)
			{
				System.out.println("a wins");
				done = true;
			}
			Ship.battle(a, b);
		}
		
	}
	public static void flotillaBattleSim(Flotilla a, Flotilla b )
	{
		int move = 0;
		boolean done = false;
		while(!done)
		{
			move++;
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
			Flotilla.battle(a, b);
		}
		System.out.println("move: " + move);
		
	}
	public static void main(String[] args)
	{
		Flotilla one = new Flotilla();
		one.addShip(new Ship());
		Flotilla two = new Flotilla();
		two.addShip(new Ship());
		singleBattleSim(one.getShip(0), two.getShip(0));
	}
}