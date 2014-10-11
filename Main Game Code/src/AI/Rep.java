package AI;

public class Rep {
	int fighting, trading, exploring, technology;
	
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
}
