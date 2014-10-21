package space_colonization;

public class Fleet {
	private flotilla[] fleet;
	public Fleet ()
	{
		fleet = new flotilla[0];
	}
	public Fleet(String type, int size)
	{
		fleet = flotilla_creator.createFleet(type, size);
	}
	public flotilla getFlotilla(int flow)
	{
		return fleet[flow];
	}
	public void addFlotilla(flotilla newerish)
	{
		flotilla[] temp = new flotilla[fleet.length + 1];
		for(int i = 0; i < fleet.length; i++)
		{
			temp[i] = fleet[i];
		}
		temp[fleet.length] = newerish;
		fleet = temp;
	}
	public void takeOutFlotilla(int pointless)
	{
		flotilla[] temp = new flotilla[fleet.length - 1];
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
	public flotilla[] getFleet()
	{
		return fleet;
	}
	public void battle(flotilla two)
	{
		
	}
	public void warSim(Fleet two)
	{
		
	}

}
