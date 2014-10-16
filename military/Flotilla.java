package military;

/**
 * A flotilla is a group of ships that can hold a certain maximum amount based on the type of ship
 * The flotilla is the lowest level of control the player has.
 * 
 * @author Arkojit
 *
 */
public class Flotilla
{
	private ShipType type;
	private Ship[] ships;
	private int maxSize;
	
	/**
	 * Creates an empty flotilla.
	 * 
	 * @param type
	 */
	public Flotilla(ShipType type)
	{
		this.type = type;
		switch(type)
		{
			case FIGHTER:      maxSize = MilitaryValues.FIGHTER_VALUES.MAX_FLOTILLA_SIZE; break;
			case FRIGATE:      maxSize = MilitaryValues.FRIGATE_VALUES.MAX_FLOTILLA_SIZE; break;
			case CRUISER:      maxSize = MilitaryValues.CRUISER_VALUES.MAX_FLOTILLA_SIZE; break;
			case CAPITAL_SHIP: maxSize = MilitaryValues.CRUISER_VALUES.MAX_FLOTILLA_SIZE; break;
		}
		ships = new Ship[maxSize];
	}
	
	/**
	 * Adds as many of the specified number of ships to the flotilla as it can.
	 */
	public void addShip(int num)
	{
		for(int i = 0; i < maxSize; i++)
		{
			if(num > 0)
			{
				if(ships[i] == null)
				{
					ships[i] = new Ship(type);
					num--;
				}
			}
			else
				break;
		}
	}
	
	/**
	 * Gets the number of ships remaining in this flotilla
	 * 
	 * @return
	 */
	public int getAliveShips()
	{
		int count = 0;
		for(int i = 0; i < ships.length; i++)
			if(ships[i] != null)
				count++;
		return count;
	}
	
	/**
	 * The passed flotilla deals a certain amount of damage to this flotilla based on
	 * their parameters
	 * 
	 * @param f
	 */
	public void dealDamage(Flotilla f)
	{
		//TODO: Implement
	}
}
