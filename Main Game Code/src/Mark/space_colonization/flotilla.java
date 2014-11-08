package space_colonization;

public class flotilla {
	private String[] names;
	private double[] qualities;
	private int worth;
	private String[] image;
	private int crew;
	private ship[] Flotilla;
	private boolean full;
	private double xLocation;
	private double yLocation;
	public flotilla()
	{
		Flotilla = new ship[0];
		full = false;
		xLocation = 0;
		yLocation = 0;
		names = new String[Flotilla.length];
	}
	public flotilla(double x, double y)
	{
		Flotilla = new ship[0];
		full = false;
		xLocation = x;
		yLocation = y;
		names = new String[Flotilla.length];
	}
	public flotilla(ship[] group, boolean Full)
	{
		Flotilla = group;
		full = Full;
		xLocation = 0;
		yLocation = 0;
		names = new String[Flotilla.length];
	}
	public flotilla(ship[] group)
	{
		Flotilla = group;
		full = false;
		xLocation = 0;
		yLocation = 0;
		names = new String[Flotilla.length];
	}
	public flotilla(ship[] group, boolean Full, double x, double y)
	{
		Flotilla = group;
		full = Full;
		xLocation = x;
		yLocation = y;
		names = new String[Flotilla.length];
	}
	public String getName(int place)
	{
		return Flotilla[place].getName();
	}
	public ship[] getFlotilla()
	{
		return Flotilla;
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
	public boolean getFull()
	{
		return full;
	}
	public void changeFull()
	{
		full = !full;
	}
	public String[] getImage()
	{
		return image;
	}
	public void setImage()
	{
		String[] temp = new String[Flotilla.length];
		for(int i = 0; i < Flotilla.length; i++)
		{
			temp[i] = getShip(i).getImage();
		}
		image = temp;
	}
	public void setFlotillaStats()
	{
		if(Flotilla.length > 0)
		{
		names = new String[Flotilla.length];
		worth = 0;
		crew = 0;
		for(int i = 0; i < 19; i++)
		{
			qualities[i] = 0;
		}
		for(int i = 0; i < Flotilla.length; i++)
		{
			names[i] = getName(i);
			for(int j = 0; j < 19; j++)
			{
				qualities[j] = qualities[j] + Flotilla[i].getQuality(j);
			}
			worth = worth + Flotilla[i].getCost();
			crew = crew + Flotilla[i].getCrew();	
			setImage();
		}
		qualities[4] = qualities[4] / Flotilla.length;
		qualities[5] = qualities[5] / Flotilla.length;
		}
	}
	public double getFlotillaStats()
	{
		double total = 0;
		for(int i = 0; i < 19; i++)
			total = total + qualities[i];
		return total;
	}
	public ship getShip(int a)
	{
		return Flotilla[a];
	}
	public void addShip(ship one)
	{
			ship[] temp = new ship[Flotilla.length + 1];
			for(int i = 0; i < Flotilla.length; i++){
				temp[i] = Flotilla[i];
			}
			temp[Flotilla.length] = one;
			Flotilla = temp;
	}
	public void checkRemoveShip()
	{
		ship[] temp = Flotilla;
		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i].getHP() == 0)
			{
				boolean passed = false;
				ship[] temp2 = new ship[temp.length - 1];
				for(int j = 0; j < temp.length; j++)
				{
					if( j == i)
					{
					j++;
					passed = true;
					}
					if (passed)
					temp2[j - 1] = temp[j];
					else
					temp2[j] = temp[j];
				}
				temp = temp2;
			}
		}
		Flotilla = temp;
	}
	public void damageShip(int ship, double damage)
	{
		Flotilla[ship].setHP(damage);
	}
	public void removeShip(int place)
	{
		boolean passed = false;
		ship[] temp = new ship[Flotilla.length - 1];
		for(int j = 0; j < temp.length; j++)
		{
			if( j == place)
			{
			j++;
			passed = true;
			}
			if (passed)
			temp[j - 1] = Flotilla[j];
			else
			temp[j] = Flotilla[j];
		}
		Flotilla = temp;
	}
	public double getHP()
	{
		double Total = 0;
		for(int i = 0; i < Flotilla.length; i++)
			Total = Total + Flotilla[i].getHP();
		return Total;
	}
	static double HPfactor = 10;
	public static void calcSpDamage(flotilla attack, flotilla defend)
	{
		double damage = ((attack.getSpPower() + attack.getSpWeaponStat())) / (defend.getSpDefence() + defend.getSpArmorStat()) * HPfactor;
		defend.setHP(damage);
	}
	public static void calcRegDamage(flotilla attack, flotilla defend)
	{
		double damage = ((attack.getRegPower() + attack.getRegWeaponStat())) / (defend.getRegDefence() + defend.getRegArmorStat()) * HPfactor;
		defend.setHP(damage);
	}
	public void setHP(double damage)
	{
		double temp = damage;
		double shipHP = 0;
		for(int i = 0; i < Flotilla.length; i++)
		{
			shipHP = getShip(i).getHP();
			if(temp > shipHP)
			{
				temp = temp - shipHP;
				getShip(i).setHP(shipHP);
			}
			else
			{
				getShip(i).setHP(temp);
				temp = 0;
				break;
			}
		}
	}
	public static void battle(flotilla a, flotilla d)
	{
		if(a.getFlotilla().length > 0 && d.getFlotilla().length > 0)
		{
			a.setFlotillaStats();
			d.setFlotillaStats();
			calcRegDamage(a, d);
			calcRegDamage(d, a);
			calcSpDamage(a, d);
			calcSpDamage(d, a);
			a.checkRemoveShip();
			d.checkRemoveShip();
		}
	}
	public double getRegPower()
	{
		setFlotillaStats();
		return qualities[0];
	}
	public void setShipRegPower(int ship, double qual)
	{
		getShip(ship).setStat(0, qual);
	}
	public double getSpPower()
	{
		setFlotillaStats();
		return qualities[1];
	}
	public void setShipSpPower(int ship, double qual)
	{
		getShip(ship).setStat(1, qual);
	}
	public double getRegDefence()
	{
		setFlotillaStats();
		return qualities[2];
	}
	public void setShipRegDefence(int ship, double qual)
	{
		getShip(ship).setStat(2, qual);
	}
	public double getSpDefence()
	{
		setFlotillaStats();
		return qualities[3];
	}
	public void setShipSpDefence(int ship, double qual)
	{
		getShip(ship).setStat(3, qual);
	}
	public double getregAttackCooldown()
	{
		setFlotillaStats();
		return qualities[4];
	}
	public void setShipRegCooldown(int ship, double qual)
	{
		getShip(ship).setStat(4, qual);
	}
	public double getSpAttackCooldown()
	{
		setFlotillaStats();
		return qualities[5];
	}
	public void setShipSpCooldown(int ship, double qual)
	{
		getShip(ship).setStat(5, qual);
	}
	public double getRegAccuracy()
	{
		setFlotillaStats();
		return qualities[6];//can increase with longer life if I get there;
	}
	public void setShipRegAccuracy(int ship, double qual)
	{
		setFlotillaStats();
		getShip(ship).setStat(6, qual);
	}
	public double getSpAccuracy()
	{
		setFlotillaStats();
		return qualities[7];
	}
	public void setShipSpAccuracy(int ship, double qual)
	{
		getShip(ship).setStat(7, qual);
	}
	public double getHPstat()
	{
		setFlotillaStats();
		return qualities[8];
	}
	public void setShipHP(int ship, double damage)
	{
		getShip(ship).setStat(8, damage);
	}
	public double getRegRange()
	{
		setFlotillaStats();
		return qualities[9];
	}
	public void setShipRegRange(int ship, double qual)
	{
		getShip(ship).setStat(9, qual);
	}
	public double getSpRange()
	{
		setFlotillaStats();
		return qualities[10];
	}
	public void setShipSpRange(int ship, double qual)
	{
		getShip(ship).setStat(10, qual);
	}
	public double getMovementSpeed()
	{
		setFlotillaStats();
		return qualities[11];
	}
	public void setShipMovementSpeed(int ship, double qual)
	{
		getShip(ship).setStat(11, qual);
	}
	public double getRepairAbility()
	{
		setFlotillaStats();
		return qualities[12];
	}
	public void setShipRepairAbility(int ship, double qual)
	{
		getShip(ship).setStat(12, qual);
	}
	public double getBuildAbility()
	{
		setFlotillaStats();
		return qualities[13];
	}
	public void setShipBuildAbillity(int ship, double qual)
	{
		getShip(ship).setStat(13, qual);
	}
	public double getTransportAbility()
	{
		setFlotillaStats();
		return qualities[14];
	}
	public void setShipTransportAbility(int ship, double qual)
	{
		getShip(ship).setStat(14, qual);
	}
	public double getSpArmorStat()
	{
		setFlotillaStats();
		return qualities[15];
	}
	public void setShipSpArmorStat(int ship, double qual)
	{
		getShip(ship).setStat(15, qual);
	}
	public double getRegArmorStat()
	{
		setFlotillaStats();
		return qualities[16];
	}
	public void setShipRegArmorStat(int ship, double qual)
	{
		getShip(ship).setStat(16, qual);
	}
	public double getSpWeaponStat()
	{
		setFlotillaStats();
		return qualities[17];
	}
	public void setShipSpWeaponStat(int ship, double qual)
	{
		getShip(ship).setStat(17, qual);
	}
	public double getRegWeaponStat()
	{
		setFlotillaStats();
		return qualities[18];
	}
	public void setShipRegWeaponStat(int ship, double qual)
	{
		getShip(ship).setStat(18, qual);
	}
	public void sortByHP()
	{
		ship[] temp = new ship[Flotilla.length];
		ship[] temp2 = Flotilla;
		for(int j = 0; j < Flotilla.length; j++)
		{
			int place = 0;
			double smallest = temp2[place].getHP();
			for(int i = 0; i < temp2.length; i++)
			{
				if(temp2[i].getHP() < smallest)
					place = i;
			}
			temp[j] = temp2[place];
			ship temp3 = temp2[temp2.length - 1];
			temp2[place] = temp3;
			ship[] temp4 = temp2;
			temp2 = new ship[temp2.length - 1];
			for(int a = 0; a < temp2.length; a++)
			temp2[a] = temp4[a];
		}
		Flotilla = temp;
	}
	public void sortByAttack()
	{
		ship[] temp = new ship[Flotilla.length];
		ship[] temp2 = Flotilla;
		for(int j = 0; j < Flotilla.length; j++)
		{
			int place = 0;
			double smallest = temp2[place].getRegPower();
			for(int i = 0; i < temp2.length; i++)
			{
				if(temp2[i].getRegPower() > smallest)
					place = i;
			}
			temp[j] = temp2[place];
			ship temp3 = temp2[temp2.length - 1];
			temp2[place] = temp3;
			ship[] temp4 = temp2;
			temp2 = new ship[temp2.length - 1];
			for(int a = 0; a < temp2.length; a++)
			temp2[a] = temp4[a];
		}
		Flotilla = temp;
	}
}
