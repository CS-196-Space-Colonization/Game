package military;

public class MilitaryValues
{
	public static ShipValues FIGHTER_VALUES = new ShipValues(500,  50, 1, 0,  1,  0, 50),
							 FRIGATE_VALUES = new ShipValues(200, 200, 5, 0, 10,  0, 25),
							 CRUISER_VALUES = new ShipValues(50,  100, 10, 0, 20, 0, 20),
							 CAPITAL_VALUES = new ShipValues(10, 5000, 20, 0, 50, 0, 10);
	
	static class ShipValues
	{
		public final int BASE_MAX_HEALTH,
						 BASE_ARMOUR,
						 BASE_SHIELDS,
						 BASE_KINETIC_DAMAGE,
						 BASE_ELECTRO_DAMAGE,
						 BASE_SPEED,
						 MAX_FLOTILLA_SIZE;
		
		public ShipValues(int maxFlotillaSize, int baseMaxHealth, int baseArmour, int baseShields, int baseKineticDamage, int baseElectroDamage, int baseSpeed)
		{
			MAX_FLOTILLA_SIZE = maxFlotillaSize;
			BASE_MAX_HEALTH = baseMaxHealth;
			BASE_ARMOUR = baseArmour;
			BASE_SHIELDS = baseShields;
			BASE_KINETIC_DAMAGE = baseKineticDamage;
			BASE_ELECTRO_DAMAGE = baseElectroDamage;
			BASE_SPEED = baseSpeed;
		}
	}
}
