package space_colonization;

public class ship extends Battle_Object
{
	private String name;
	private double[] qualities;
	private int cost;//to be replaced by an array of specific reasorces later once this is finallized
	private String image;
	private int crew;
	public ship(String nameOfShip)
	{
		super();
		name = nameOfShip;
		qualities = new double[19];
		cost = 10;
		image = "[]";
		crew = 1;
		setEqualStats(10.0);
		
	}
	public ship()
	{
		super();
		name = "ship";
		qualities = new double[19];
		cost = 10;
		image = "[]";
		crew = 1;
		setEqualStats(10.0);
	}
	public ship(String nameOfShip, double[] stats, int Cost, String display, int Crew)
	{
		super(stats[8]);
		name = nameOfShip;
		qualities = stats;
		cost = Cost;
		image = display;
		crew = Crew;
	}
	public void setStat(int stat, double num)
	{
		qualities[stat] = num;
	}
	public String getImage()
	{
		return image;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String newName)
	{
		name = newName;
	}
	public double[] getStats()
	{
		double[] temp = new double[19];
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = qualities[i];
		}
		return temp;
	}
	public double getRegPower()
	{
		return qualities[0];
	}
	public void setRegPower(double qual)
	{
		qualities[0] = qual;
	}
	public double getSpPower()
	{
		return qualities[1];
	}
	public void setSpPower(double qual)
	{
		qualities[1] = qual;
	}
	public double getRegDefence()
	{
		return qualities[2];
	}
	public void setRegDefence(double qual)
	{
		qualities[2] = qual;
	}
	public double getSpDefence()
	{
		return qualities[3];
	}
	public void setSpDefence(double qual)
	{
		qualities[3] = qual;
	}
	public double getregAttackCooldown()
	{
		return qualities[4];
	}
	public void setRegCooldown(double qual)
	{
		qualities[4] = qual;
	}
	public double getSpAttackCooldown()
	{
		return qualities[5];
	}
	public void setSpCooldown(double qual)
	{
		qualities[5] = qual;
	}
	public double getRegAccuracy()
	{
		return qualities[6];//can increase with longer life if I get there;
	}
	public void setRegAccuracy(double qual)
	{
		qualities[6] = qual;
	}
	public double getSpAccuracy()
	{
		return qualities[7];
	}
	public void setSpAccuracy(double qual)
	{
		qualities[7] = qual;
	}
	public double getRegRange()
	{
		return qualities[9];
	}
	public void setRegRange(double qual)
	{
		qualities[9] = qual;
	}
	public double getSpRange()
	{
		return qualities[10];
	}
	public void setSpRange(double qual)
	{
		qualities[10] = qual;
	}
	public double getMovementSpeed()
	{
		return qualities[11];
	}
	public void setMovementSpeed(double qual)
	{
		qualities[11] = qual;
	}
	public double getRepairAbility()
	{
		return qualities[12];
	}
	public void setRepairAbility(double qual)
	{
		qualities[12] = qual;
	}
	public double getBuildAbility()
	{
		return qualities[13];
	}
	public void setBuildAbillity(double qual)
	{
		qualities[13] = qual;
	}
	public double getTransportAbility()
	{
		return qualities[14];
	}
	public void setTransportAbility(double qual)
	{
		qualities[14] = qual;
	}
	public double getSpArmorStat()
	{
		return qualities[15];
	}
	public void setSpArmorStat(double qual)
	{
		qualities[15] = qual;
	}
	public double getRegArmorStat()
	{
		return qualities[16];
	}
	public void setRegArmorStat(double qual)
	{
		qualities[16] = qual;
	}
	public double getSpWeaponStat()
	{
		return qualities[17];
	}
	public void setSpWeaponStat(double qual)
	{
		qualities[17] = qual;
	}
	public double getRegWeaponStat()
	{
		return qualities[18];
	}
	public void setRegWeaponStat(double qual)
	{
		qualities[18] = qual;
	}
	public int getCost()
	{
		return cost;
	}
	public void setCost(int Cost)
	{
		cost = Cost;
	}
	public int getCrew()
	{
		return crew;
	}
	public void setCrew(int newCrew)
	{
		crew = newCrew;
	}
	public double getQuality(int place)
	{
		return qualities[place];
	}
	static double HPfactor = 10;
	public static void calcSpDamage(ship attack, ship defend)
	{
		double damage = ((attack.getSpPower() + attack.getSpWeaponStat())) / (defend.getSpDefence() + defend.getSpArmorStat()) * HPfactor;
		defend.setHP(damage);
	}
	public static void calcRegDamage(ship attack, ship defend)
	{
		double damage = ((attack.getRegPower() + attack.getRegWeaponStat())) / (defend.getRegDefence() + defend.getRegArmorStat())  * HPfactor;
		defend.setHP(damage);
	}
	public static void battle(ship a, ship b)
	{
		calcRegDamage(a, b);
		calcRegDamage(b, a);
		calcSpDamage(a, b);
		calcSpDamage(b, a);
	}
	private double x = 100.0;
	public void setX(double X)
	{
		x = X * crew;
	}
	private double z = 100.0;
	public void setz(double Z)
	{
		x = Z + crew;
	}
	private double y = 1;// no longer going to change sp stat in this way.
	public void setY(double Y)
	{
		y = Y;
	}
	public void setStaionaryObject(double[] qual)
	{
			setStats(qual);
	}
	public void setStatsHeal(double level)//level varies directly with cost, ex if a ship is twice the level
										  //of one ship it will cost a little less than twice as much	
	{
		qualities[0] = level * 0;
		qualities[1] = (level - y) * 0;
		qualities[2] = level * 5 * x;
		qualities[3] = (level - y) * 5 * x;
		qualities[4] = level * 0;
		qualities[5] = (level - y) * 0;
		qualities[6] = level * 0;
		qualities[7] = (level - y) * 0;
		qualities[8] = level * 3 * x;
		qualities[9] = level * 0;
		qualities[10] = (level - y) * 0;
		qualities[11] = level * 4 * x;
		qualities[12] = level * 3 * x;
		qualities[13] = level * 0;
		qualities[14] = level * 2 * x;
		qualities[15] = (level - y) * 0;
		qualities[16] = level * 0;
		qualities[17] = (level - y) * 0;
		qualities[18] = level * 0;
	}
	public void setStatsBuild(double level)
	{
		qualities[0] = level * 0;
		qualities[1] = (level - y) * 0;
		qualities[2] = level * 5 * x;
		qualities[3] = (level - y) * 5 * x;
		qualities[4] = level * 0;
		qualities[5] = (level - y) * 0;
		qualities[6] = level * 0;
		qualities[7] = (level - y) * 0;
		qualities[8] = level * 3 * x;
		qualities[9] = level * 0;
		qualities[10] = (level - y) * 0;
		qualities[11] = level * 2 * x;
		qualities[12] = level * x;
		qualities[13] = level * 10 * x;
		qualities[14] = level * 10 * x;
		qualities[15] = (level - y) * 0;
		qualities[16] = level * 0;
		qualities[17] = (level - y) * 0;
		qualities[18] = level * 0;
	}
	public void setStatsAttack(double level)
	{
		qualities[0] = level * 10 * x;
		qualities[1] = (level - y) * 10 * x;
		qualities[2] = level * 2 * x;
		qualities[3] = (level - y) * 2 * x;
		qualities[4] = 5;
		qualities[5] = 5;
		qualities[6] = 1;
		qualities[7] = 1;
		qualities[8] = level * 5 * x;
		qualities[9] = level * 4 * z;
		qualities[10] = level * 4 * z;
		qualities[11] = level * 5 * x;
		qualities[12] = level * 0;
		qualities[13] = level * 0;
		qualities[14] = level * x;
		qualities[15] = level * 0;
		qualities[16] = level * 0;
		qualities[17] = level * 0;
		qualities[18] = level * 0;
	}
	public void setStatsDefend(double level)
	{
		qualities[0] = level * 2 * x;
		qualities[1] = (level - y) * 2 * x;
		qualities[2] = level * 10 * x;
		qualities[3] = (level - y) * 10 * x;
		qualities[4] = 5;
		qualities[5] = 5;
		qualities[6] = 1;
		qualities[7] = 1;
		qualities[8] = level * 5 * x;
		qualities[9] = level * 4 * z;
		qualities[10] = level * 4 * z;
		qualities[11] = level * 5 * x;
		qualities[12] = level * 0;
		qualities[13] = level * 0;
		qualities[14] = level * x;
		qualities[15] = level * 0;
		qualities[16] = level * 0;
		qualities[17] = level * 0;
		qualities[18] = level * 0;
	}
	public void setStatsSpeed(double level)
	{
		qualities[0] = level * 4 * x;
		qualities[1] = (level - y) * 4 * x;
		qualities[2] = level * 4 * x;
		qualities[3] = (level - y) * 4 * x;
		qualities[4] = 2;
		qualities[5] = 2;
		qualities[6] = 1;
		qualities[7] = 1;
		qualities[8] = level * 5 * x;
		qualities[9] = level * 3 * z;
		qualities[10] = level * 3 * z;
		qualities[11] = level * 5 * x;
		qualities[12] = level * 0;
		qualities[13] = level * 0;
		qualities[14] = level * x;
		qualities[15] = level * 0;
		qualities[16] = level * 0;
		qualities[17] = level * 0;
		qualities[18] = level * 0;
	}
	public void setStatsRange(double level)
	{
		qualities[0] = level * 5 * x;
		qualities[1] = (level - y) * 5 * x;
		qualities[2] = level * 5 * x;
		qualities[3] = (level - y) * 5 * x;
		qualities[4] = 5;
		qualities[5] = 5;
		qualities[6] = 1;
		qualities[7] = 1;
		qualities[8] = level * 5 * x;
		qualities[9] = level * 10 * z;
		qualities[10] = level * 10 * z;
		qualities[11] = level * 7 * x;
		qualities[12] = level * 0;
		qualities[13] = level * 0;
		qualities[14] = level * x;
		qualities[15] = level * 0;
		qualities[16] = level * 0;
		qualities[17] = level * 0;
		qualities[18] = level * 0;
	}
	public void setStatsBalance(double level)
	{
		qualities[0] = level * 6 * x;
		qualities[1] = (level - y) * 6 * x;
		qualities[2] = level * 6 * x;
		qualities[3] = (level - y) * 6 * x;
		qualities[4] = 4;
		qualities[5] = 4;
		qualities[6] = 1;
		qualities[7] = 1;
		qualities[8] = level * 5 * x;
		qualities[9] = level * 4 * z;
		qualities[10] = level * 4 * z;
		qualities[11] = level * 5 * x;
		qualities[12] = level * 0;
		qualities[13] = level * 0;
		qualities[14] = level * x;
		qualities[15] = level * 0;
		qualities[16] = level * 0;
		qualities[17] = level * 0;
		qualities[18] = level * 0;
	}

	public void setEqualStats(double stat)
	{
		double[] temp = new double[19];
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = stat;
		}
		temp[6] = 0.5;
		temp[7] = 0.5;
		qualities = temp;
	}
	public void setStats(double[] stat)
	{
		qualities[0] = stat[0];
		qualities[1] = stat[1];
		qualities[2] = stat[2];
		qualities[3] = stat[3];
		qualities[4] = stat[4];
		qualities[5] = stat[5];
		qualities[6] = stat[6];
		qualities[7] = stat[7];
		qualities[8] = stat[8];
		qualities[9] = stat[9];
		qualities[10] = stat[10];
		qualities[11] = stat[11];
		qualities[12] = stat[12];
		qualities[13] = stat[13];
		qualities[14] = stat[14];
		qualities[15] = stat[15];
		qualities[16] = stat[16];
		qualities[17] = stat[17];
		qualities[18] = stat[18];
	}
	//do not intend to use this setCustom method in anything but testing
	public void setStatsCustom(double regPower, double spPower, double regDefence, double spDefence, double regCoolDown, double spCooldown, 
			double regWeaponsAccuracy, double spWeaponsAccuracy, double HP,	double regAttackRange, double spAttackRange, double MovementSpeed, 
			double Repair, double build, double transport, double spArmor, double regArmor, double spWeapon, double regWeapon)
	{
		qualities[0] = regPower;
		qualities[1] = spPower;
		qualities[2] = regDefence;
		qualities[3] = spDefence;
		qualities[4] = regCoolDown;
		qualities[5] = spCooldown;
		qualities[6] = regWeaponsAccuracy;
		qualities[7] = spWeaponsAccuracy;
		qualities[8] = HP;
		qualities[9] = regAttackRange;
		qualities[10] = spAttackRange;
		qualities[11] = MovementSpeed;
		qualities[12] = Repair;
		qualities[13] = build;
		qualities[14] = transport;
		qualities[15] = spArmor;
		qualities[16] = regArmor;
		qualities[17] = spWeapon;
		qualities[18] = regWeapon;
		
	}
}