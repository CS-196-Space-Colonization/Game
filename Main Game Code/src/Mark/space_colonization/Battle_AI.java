package space_colonization;

public class Battle_AI {
	
	private int AILevel;
	private int money;
	private Fleet fleet;
	public Battle_AI(int level, int mon, Fleet fl)
	{
		AILevel = level;
		money = mon;
		fleet = fl;
	}
	public Battle_AI()
	{
		AILevel = 2000;
		money = 1000000000;
		fleet = new Fleet();
		setInvincable();
	}
	public Fleet getFleet()
	{
		return fleet;
	}
	public void setFleet(Fleet fl)
	{
		fleet = fl;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int mon)
	{
		money = mon;
	}
	public int getLevel()
	{
		return AILevel;
	}
	public void setLevel(int lev)
	{
		AILevel = lev;
	}
	public void arrangeShips()
	{
		//also arrange ship array in different formations putting different ships in different places
	}
	public void formFlotilla()
	{
		//how the AI builds its flotilla
	}
	public double PlanAttack(Fleet attacking)
	{
		return fleet.winProbabilityBasic(attacking);
	}
	public void setInvincable()
	{
		//will set group invincible
	}
	public void surrenderFlotilla(Fleet friend)
	{
		friend.addFlotilla(fleet.getFlotilla(0));
		fleet.takeOutFlotilla(0);
	}
	public void findNextTarget()
	{
		//looks at nearby civilizations
	}
	public void setBattleStratigy(Fleet attacking)
	{
		//sets AI's plans
	}
}
