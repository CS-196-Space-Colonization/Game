package space_colonization;

public class shipFactory {
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
	public void produceShip(ship Ship, flotilla Flotilla)
	{
		if(money > Ship.getCost() && peoples > Ship.getCrew())
		{
			money = money - Ship.getCost();
			peoples = peoples - Ship.getCrew();
			Flotilla.addShip(Ship);
		}
	}
	public void produceFlotilla(ship[] floatil, flotilla Flotilla)
	{
		for(int i = 0; i > floatil.length; i++)
		{
			produceShip(floatil[i], Flotilla);
		}
	}
	//make production of specific flotillas
}
