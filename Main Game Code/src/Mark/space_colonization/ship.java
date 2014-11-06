package space_colonization;

public class ship extends Battle_Object
{
	private double fuel;
	public ship(String nameOfShip)
	{
		super(nameOfShip);
		
	}
	public ship()
	{
		super();
	}
	public ship(String nameOfShip, double[] stats, int Cost, String display, int Crew, int ammo, double f)
	{
		super(nameOfShip, stats, Cost, display, Crew, ammo);
		fuel = f;
	}
	public void addFuel(double add)
	{
		fuel = fuel + add;
	}
	public double getFuel()
	{
		return fuel;
	}
	public void useFuel(double amount)
	{
		fuel = fuel - amount;
	}
}