package com.thecolony.tractus.player.ai.battle.ships;

public class FlotillaCreator {
	private static ShipFactory first;
	private int money;
	private int people;
	private double baseX;
	private double baseY;
	private Fleet fleet;
	private double factoryHP;
	public FlotillaCreator()
	{
		money = 10000000;
		people = 1000000;
		first = new ShipFactory(fleet, money, people, baseX, baseY, factoryHP);
	}
	public FlotillaCreator(Fleet f, int Money, int People, double x, double y, double HP)
	{
		first = new ShipFactory(f, Money, People, x, y, HP);
		fleet = f;
		money = Money;
		people = People;
		baseX = x;
		baseY = y;
		factoryHP = HP;
	}
	public void addPeople(int addedPeople)
	{
		people = people + addedPeople;
	}
	public void addMoney(int addedMoney)
	{
		money = money + addedMoney;
	}
	public static Flotilla createBaseFlotilla(int fighters, int frigate, int cruiser, 
			 String Type, int level)//consists of one capital ship and an amount of fighters
	{
		Flotilla one = new Flotilla();
		Ship bigest = new Specific_Ship("Capital", Type, level);
		Ship small = new Specific_Ship("Fighter", Type, level);
		Ship big = new Specific_Ship("Frigate", Type, level);
		Ship biger = new Specific_Ship("Cruiser", Type, level);
		first.produceShip(bigest, one);
		for(int i = 0; i < fighters; i++)
		{
			first.produceShip(small, one);
		}
		for(int i = 0; i < frigate; i++)
		{
			first.produceShip(big, one);
		}
		for(int i = 0; i < cruiser; i++)
		{
			first.produceShip(biger, one);
		}
		return one;
	}
	public static Flotilla[] createFleet(String type, int flotillas)
	{
		Flotilla[] one = new Flotilla[126];
		Flotilla two = createBaseFlotilla(100, 5, 20, type, 1);
		for(int i = 0; i < flotillas; i++)
		{
			one[i] = two;
		}
		return one;
	}

}
