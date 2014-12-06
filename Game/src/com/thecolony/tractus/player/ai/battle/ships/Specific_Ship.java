
package com.thecolony.tractus.player.ai.battle.ships;


public class Specific_Ship extends Ship {
	private String Type;
	private String Qual;
	private double level;
	private int crew;
	public Specific_Ship()
	{
		super();
		Type = "Fighter";
		Qual = "Attack";
		level = 1.0;
		setShip(Type);
	}
	public Specific_Ship(String type, String qual, double Level)
	{
		super();
		Type = type;
		Qual = qual;
		level = Level;
		setShip(Type);
	}
	public void setToFighter()
	{
		crew = 1;
		//Fighter		Can�t exist in space without a mother capital ship. 1 pilot, only 100 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToFrigate()
	{
            level = level * 2;
		crew = 5;
		//Frigates	Small ships with a crew of around 5 people, only 20 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToCruiser()
	{
                level = level * 3;
		crew = 20;
		//Cruisers	Bigger ships with a crew of 20 people, only 5 per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setToCapital()
	{
		crew = 100;
                level = level * 4;
		//Capital Ships	Massive ships with crews of 100 people, only one per flotilla
		if(Qual.equals("Heal"))
		{
			setCrew(crew);
			setStatsHeal(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Build"))
		{
			setCrew(crew);
			setStatsBuild(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Attack"))
		{
			setCrew(crew);
			setStatsAttack(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Defence"))
		{
			setCrew(crew);
			setStatsDefend(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Speed"))
		{
			setCrew(crew);
			setStatsSpeed(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Range"))
		{
			setCrew(crew);
			setStatsRange(level);
			setCost((int)(level * 5 - 1));
		}
		else if(Qual.equals("Balance"))
		{
			setCrew(crew);
			setStatsBalance(level);
			setCost((int)(level * 5 - 1));
		}
		else 
			System.out.println("error!!!");
	}
	public void setShip(String type)
	{
		if(type.equals("Fighter"))
			setToFighter();
		else if(type.equals("Capital Ship"))
			setToCapital();
		else if(type.equals("Frigate"))
			setToFrigate();
		else if(type.equals("Cruiser"))
			setToCruiser();
		else 
			System.out.println("invalid input!!!!!");
	}
}