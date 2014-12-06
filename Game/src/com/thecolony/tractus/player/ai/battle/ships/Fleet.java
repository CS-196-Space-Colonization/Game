package com.thecolony.tractus.player.ai.battle.ships;

public class Fleet {
	private Flotilla[] fleet;
	public Fleet ()
	{
		fleet = new Flotilla[0];
	}
	public Fleet(String type, int size)
	{
		fleet = ShipFactory.createFleet(type, size);
	}
	public Flotilla getFlotilla(int flow)
	{
		return fleet[flow];
	}
	public void addFlotilla(Flotilla newerish)
	{
		Flotilla[] temp = new Flotilla[fleet.length + 1];
		for(int i = 0; i < fleet.length; i++)
		{
			temp[i] = fleet[i];
		}
		temp[fleet.length] = newerish;
		fleet = temp;
	}
	public int getFleetLength()
	{
		return fleet.length;
	}
	public void takeOutFlotilla(int pointless)
	{
		Flotilla[] temp = new Flotilla[fleet.length - 1];
		int j = 0;
		for(int i = 0; i < fleet.length; i++)
		{
			if(i == pointless)
				j++;
			temp[i] = fleet[j];
			j++;
		}
		fleet = temp;
	}
	public Flotilla[] getFleet()
	{
		return fleet;
	}
	public static void battle(Fleet fleet, Fleet flee, int FlotillaOfFleet, int FlotillaOfFlee)
	{
		if(fleet.getFlotilla(FlotillaOfFleet).getFlotilla().length > 0 && flee.getFlotilla(FlotillaOfFlee).getFlotilla().length > 0)
		Flotilla.battle(fleet.getFlotilla(FlotillaOfFleet), flee.getFlotilla(FlotillaOfFlee), 1);
		if(fleet.getFlotilla(FlotillaOfFleet).getFlotilla().length == 0)
			fleet.takeOutFlotilla(FlotillaOfFleet);
		if(flee.getFlotilla(FlotillaOfFlee).getFlotilla().length == 0)
			flee.takeOutFlotilla(FlotillaOfFlee);
	}
	public static String warSim(Fleet one, Fleet two)
	{
		String result = "error with fleet warsim method";
		while(one.getFleetLength() > 0 || two.getFleetLength() > 0)
		{
			battle(one, two, 0, 0);
		if(one.getFleetLength() == 0 && two.getFleetLength() == 0)
			return "tie";
		else if(one.getFleetLength() == 0)
			return "lose";
		else
			return "win";
		}
		return result;
	}
	public double winProbabilityBasic(Fleet two)
	{
		double win = 0;
		double attacker = 0;
		double defender = 0;
		for(int i = 0; i < fleet.length; i++)
		{
			attacker = attacker + fleet[i].getFlotillaStats();
		}
		for(int i = 0; i < two.getFleet().length; i++)
		{
			defender = defender + two.getFlotilla(i).getFlotillaStats();
		}
		win = attacker / defender;
		return win;
	}

}
