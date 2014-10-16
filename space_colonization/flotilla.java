package space_colonization;

public class flotilla {
	private String[] names;
	private double[] qualities;
	private int worth;
	private String[] image;
	private int crew;
	private ship[] Flotilla;
	private boolean full;
	public flotilla()
	{
		Flotilla = new ship[10];
		full = false;
	}
	public flotilla(ship[] group, boolean Full)
	{
		Flotilla = group;
		full = Full;
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
		checkRemoveShip();
		names = new String[Flotilla.length];
		worth = 0;
		crew = 0;
		for(int i = 0; i < Flotilla.length; i++)
		{
			names[i] = getShip(i).getName();
			for(int j = 0; j < 19; j++)
			{
				qualities[j] = qualities[j] + getShip(i).getQuality(j);
			}
			worth = worth + getShip(i).getCost();
			crew = crew + getShip(i).getCrew();	
			setImage();
		}
	}
	public ship getShip(int a)
	{
		return Flotilla[a];
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
	public void addShip(ship one)
	{
		ship[] temp = new ship[Flotilla.length];
		temp[Flotilla.length - 1] = one;
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
		a.setFlotillaStats();
		d.setFlotillaStats();
		calcRegDamage(a, d);
		calcRegDamage(d, a);
		calcSpDamage(a, d);
		calcSpDamage(d, a);
		a.checkRemoveShip();
		d.checkRemoveShip();
		a.setFlotillaStats();
		d.setFlotillaStats();
	}
	public double getRegPower()
	{
		return qualities[0];
	}
	public void setShipRegPower(int ship, double qual)
	{
		getShip(ship).setStat(0, qual);
	}
	public double getSpPower()
	{
		return qualities[1];
	}
	public void setShipSpPower(int ship, double qual)
	{
		getShip(ship).setStat(1, qual);
	}
	public double getRegDefence()
	{
		return qualities[2];
	}
	public void setShipRegDefence(int ship, double qual)
	{
		getShip(ship).setStat(2, qual);
	}
	public double getSpDefence()
	{
		return qualities[3];
	}
	public void setShipSpDefence(int ship, double qual)
	{
		getShip(ship).setStat(3, qual);
	}
	public double getregAttackCooldown()
	{
		return qualities[4];
	}
	public void setShipRegCooldown(int ship, double qual)
	{
		getShip(ship).setStat(4, qual);
	}
	public double getSpAttackCooldown()
	{
		return qualities[5];
	}
	public void setShipSpCooldown(int ship, double qual)
	{
		getShip(ship).setStat(5, qual);
	}
	public double getRegAccuracy()
	{
		return qualities[6];//can increase with longer life if I get there;
	}
	public void setShipRegAccuracy(int ship, double qual)
	{
		getShip(ship).setStat(6, qual);
	}
	public double getSpAccuracy()
	{
		return qualities[7];
	}
	public void setShipSpAccuracy(int ship, double qual)
	{
		getShip(ship).setStat(7, qual);
	}
	public double getHPstat()
	{
		return qualities[8];
	}
	public void setShipHP(int ship, double damage)
	{
		getShip(ship).setStat(8, damage);
	}
	public double getRegRange()
	{
		return qualities[9];
	}
	public void setShipRegRange(int ship, double qual)
	{
		getShip(ship).setStat(9, qual);
	}
	public double getSpRange()
	{
		return qualities[10];
	}
	public void setShipSpRange(int ship, double qual)
	{
		getShip(ship).setStat(10, qual);
	}
	public double getMovementSpeed()
	{
		return qualities[11];
	}
	public void setShipMovementSpeed(int ship, double qual)
	{
		getShip(ship).setStat(11, qual);
	}
	public double getRepairAbility()
	{
		return qualities[12];
	}
	public void setShipRepairAbility(int ship, double qual)
	{
		getShip(ship).setStat(12, qual);
	}
	public double getBuildAbility()
	{
		return qualities[13];
	}
	public void setShipBuildAbillity(int ship, double qual)
	{
		getShip(ship).setStat(13, qual);
	}
	public double getTransportAbility()
	{
		return qualities[14];
	}
	public void setShipTransportAbility(int ship, double qual)
	{
		getShip(ship).setStat(14, qual);
	}
	public double getSpArmorStat()
	{
		return qualities[15];
	}
	public void setShipSpArmorStat(int ship, double qual)
	{
		getShip(ship).setStat(15, qual);
	}
	public double getRegArmorStat()
	{
		return qualities[16];
	}
	public void setShipRegArmorStat(int ship, double qual)
	{
		getShip(ship).setStat(16, qual);
	}
	public double getSpWeaponStat()
	{
		return qualities[17];
	}
	public void setShipSpWeaponStat(int ship, double qual)
	{
		getShip(ship).setStat(17, qual);
	}
	public double getRegWeaponStat()
	{
		return qualities[18];
	}
	public void setShipRegWeaponStat(int ship, double qual)
	{
		getShip(ship).setStat(18, qual);
	}
}
