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
		for(int i = 0; i < 19; i++)
		{
			qualities[i] = 0;
		}
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
	public static void battle(flotilla a, flotilla d)
	{
		int damage = 0;
		
		
	}
	
}
