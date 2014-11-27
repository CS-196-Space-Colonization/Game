package com.thecolony.tractus.player.ai.battle;
//this class will be extended to make more specific objects
public class Stationary_Object extends BattleObject {
	public Stationary_Object()
	{
		super();
	}
	public Stationary_Object(double HP)
	{
		super(HP);
	}
	public Stationary_Object(String nameOfShip, double[] stats, int Cost, String display, int Crew, int ammo)
	{
		super(nameOfShip, stats, Cost, display, Crew, ammo);
	}
	
}
