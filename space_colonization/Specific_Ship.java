
package space_colonization;


public class Specific_Ship extends ship {
	private String Type;
	public Specific_Ship()
	{
		super();
		Type = "Fighter";
		setShip();
	}
	public Specific_Ship(String type)
	{
		super();
		Type = type;
		setShip();
	}
	public void setToFighter()
	{
		//Fighter		Can’t exist in space without a mother capital ship. 1 pilot
	}
	public void setToFrigate()
	{
		//Frigates	Small ships with a crew of around 5 people
	}
	public void setToCruiser()
	{
		//Cruisers	Bigger ships with a crew of 20 people
	}
	public void setToCapital()
	{
		//Capital Ships	Massive ships with crews of 100 people
	}
	public void setShip()
	{
		if(Type.equals("Fighter"))
			setToFighter();
		else if(Type.equals("Capital"))
			setToCapital();
	}
}
