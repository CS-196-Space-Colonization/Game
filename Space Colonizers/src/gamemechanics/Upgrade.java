package gamemechanics;

public abstract class Upgrade
{
	private String name;
	private String description;
	private boolean implemented;
	
	public Upgrade(String name)
	{
		this.name = name;
	}
	
	public abstract void performUpgrade(PlayerValues gameValues);
}
