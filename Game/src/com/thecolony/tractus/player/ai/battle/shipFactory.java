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
	public shipFactory(int Money, int Person)
	{
		super(100);
		troops = new Fleet();
		money = Money;
		peoples = Person;
		xLocation = 0;
		yLocation = 0;
		hp = 100;
	}
	public void setHp(double newhp)
	{
		hp = newhp;
	}
	public double getHp()
	{
		return hp;
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
	public static void produceShip(ship Ship, flotilla Flotilla)
	{
			Flotilla.addShip(Ship);
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
	public static flotilla createBaseFlotilla(boolean capital, int fighters, int frigate, int cruiser, 
			 String Type, int level)//consists of one capital ship and an amount of fighters
	{
		flotilla one = new flotilla();
		ship bigest = new Specific_Ship("Capital", Type, level);
		ship small = new Specific_Ship("Fighter", Type, level);
		ship big = new Specific_Ship("Frigate", Type, level);
		ship biger = new Specific_Ship("Cruiser", Type, level);
		if(capital)
			produceShip(bigest, one);
		for(int i = 0; i < fighters; i++)
		{
			produceShip(small, one);
		}
		for(int i = 0; i < frigate; i++)
		{
			produceShip(big, one);
		}
		for(int i = 0; i < cruiser; i++)
		{
			produceShip(biger, one);
		}
		return one;
	}
	public static flotilla[] createFleet(String type, int flotillas)
	{
		flotilla[] one = new flotilla[flotillas + 1];
		flotilla two = createBaseFlotilla(true, 100, 20, 5, type, 1);
		for(int i = 0; i < flotillas; i++)
		{
			one[i] = two;
		}
		return one;
	}
}
