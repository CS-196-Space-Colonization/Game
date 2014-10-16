package AI;

public class Rep {
	//how instances of fighting have they gone through
	int fighting;
	//how many trades are they doing
	int trading;
	//how much have they explored? (i think this should be removed or reworked - Arturo)
	int exploring;
	//how many technologies have they discovered or what is the last technology they discovered
	int technology;
	
	public Rep()
	{
		fighting = 0;
		trading = 0;
		exploring = 0;
		technology = 0;
	}
	
	public Rep(int f, int t, int e, int te)
	{
		fighting = f;
		trading = t;
		exploring = e;
		technology = te;
	}
	
	public int getFighting()
	{
		return fighting;
	}
	
	public int getTrading()
	{
		return trading;
	}
	
	public int getExploring()
	{
		return exploring;
	}

	public int getTechnology()
	{
		return technology;
	}
	
	public void setFighting(int f)
	{
		fighting = f;
	}
	
	public void setTrading(int t)
	{
		trading = t;
	}
	
	public void setExploring(int e)
	{
		exploring = e;
	}
	
	public void setTechnology(int t)
	{
		technology = t;
	}
	
	public String toString()
	{
		String result = "";
		result += "Fighting: " + fighting + "\n";
		result += "Trading: " + trading + "\n";
		result += "Exploring: " + exploring + "\n";
		result += "Technology: " + technology + "\n";
		return result;
	}
}
