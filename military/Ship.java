package military;

/**
 * Represents an individual ship in the flotilla
 * For now, there're 4 types of ships: Fighters, frigates, cruisers, and capital ships
 * 
 * @author Arkojit
 *
 */
public class Ship
{
	private int maxHealth,
	            armour,
	            maxShields,
	            speed,
	            electroDamage,
	            kineticDamage;
	
	private int health,
			    shields;
	
	public Ship(ShipType type)
	{
		switch(type)
		{
		case FIGHTER: maxHealth = 50;
					  armour = 1;
					  maxShields = 0;
					  speed = 20;
					  electroDamage = 0;
					  kineticDamage = 1;
					  break;
		case FRIGATE: maxHealth = 200;
					  armour = 10;
					  maxShields = 0;
					  speed = 10;
					  electroDamage = 0;
					  kineticDamage = 10;
					  break;
		case CRUISER: maxHealth = 500;
					  armour = 10;
					  maxShields = 0;
					  electroDamage = 0;
					  speed = 10;
					  kineticDamage = 50;
					  break;
		case CAPITAL_SHIP: maxHealth = 1000;
						   armour = 50;
						   maxShields = 0;
						   speed = 5;
						   electroDamage = 0;
						   kineticDamage = 100;
		}
	}
}

enum ShipType
{
	FIGHTER, FRIGATE, CRUISER, CAPITAL_SHIP
}