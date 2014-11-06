package space_colonization;

public class shipFactory extends Battle_Object{
	private int money;
	private int peoples;
	private static int costOfFactory = 100;
	private double xLocation;
	private double yLocation;
	private Fleet troops;
	private double hp;
	public shipFactory()
	{
		super();
		money = 0;
		peoples = 0;
		troops = new Fleet();
		xLocation = 0;
		yLocation = 0;
		hp = 10;
	}
	public shipFactory(Fleet f, int Money, int Person, double x, double y, double HP)
	{
		super(HP);
		troops = f;
		money = Money;
		peoples = Person;
		xLocation = x;
		yLocation = y;
		hp = HP;
	}
	public shipFactory(int Money, int Person, double x, double y, double HP)
	{
		super(HP);
		troops = new Fleet();
		money = Money;
		peoples = Person;
		xLocation = x;
		yLocation = y;
		hp = HP;
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
	public void produceShip(ship Ship, flotilla Flotilla)
	{
		if(money > Ship.getCost() && peoples > Ship.getCrew())
		{
			money = money - Ship.getCost();
			peoples = peoples - Ship.getCrew();
			Flotilla.addShip(Ship);
		}
	}
	public void produceFlotilla(ship[] floatil, Fleet f)
	{
		flotilla one = new flotilla(floatil);
		f.addFlotilla(one);
	}
	public void produceFlotilla(flotilla floatil, Fleet f)
	{
		f.addFlotilla(floatil);
	}
}
