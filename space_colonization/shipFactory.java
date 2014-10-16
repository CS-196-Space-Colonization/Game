package space_colonization;

public class shipFactory {
	private flotilla[] fleet;
	private int money;
	private int peoples;
	private static int costOfFactory = 100;
	public shipFactory()
	{
		money = 0;
		peoples = 0;
	}
	public shipFactory(int Money, int Person)
	{
		money = Money;
		peoples = Person;
	}
	public void addMoney(int $)
	{
		money = money + $;
	}
	public void addWorkers(int worker)
	{
		peoples = peoples + worker;
	}
	public void produceShip(ship Ship, int Flotilla)
	{
		if(money > Ship.getCost() && peoples > Ship.getCrew())
		{
			money = money - Ship.getCost();
			peoples = peoples - Ship.getCrew();
			fleet[Flotilla].addShip(Ship);
		}
	}
	public void produceFlotilla(ship[] floatil, int Flotilla)
	{
		for(int i = 0; i > floatil.length; i++)
		{
			produceShip(floatil[i], Flotilla);
		}
	}
	//make production of specific flotillas
}
