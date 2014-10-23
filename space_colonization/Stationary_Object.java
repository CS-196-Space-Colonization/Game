package space_colonization;
//this class will be extended to make more specific objects
public class Stationary_Object extends Battle_Object {
	private double hp;
	public Stationary_Object()
	{
		super();
	}
	public Stationary_Object(double HP)
	{
		super(HP);
		hp = HP;
	}
	
}
