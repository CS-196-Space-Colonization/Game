package com.thecolony.tractus.player.ai.battle;

public class flotilla_creator {
	private static shipFactory first;
	private int money;
	private int people;
	private double baseX;
	private double baseY;
	private Fleet fleet;
	private double factoryHP;
	public flotilla_creator()
	{
		money = 10000000;
		people = 1000000;
		first = new shipFactory(fleet, money, people, baseX, baseY, factoryHP);
	}
	public flotilla_creator(Fleet f, int Money, int People, double x, double y, double HP)
	{
		first = new shipFactory(f, Money, People, x, y, HP);
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
	public static flotilla createBaseFlotilla(int fighters, int frigate, int cruiser, 
			 String Type, int level)//consists of one capital ship and an amount of fighters
	{
		flotilla one = new flotilla();
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
	public static flotilla[] createFleet(String type, int flotillas)
	{
		flotilla[] one = new flotilla[126];
		flotilla two = createBaseFlotilla(100, 5, 20, type, 1);
		for(int i = 0; i < flotillas; i++)
		{
			one[i] = two;
		}
		return one;
	}

}
