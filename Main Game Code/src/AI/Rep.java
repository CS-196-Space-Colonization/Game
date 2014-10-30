/**
 * <h1>Add Two Numbers!</h1>
 * Create list of all the actions that can be performed by a player
 * @author Arturo Guerrero
 * @version v0.01 2014-10-28
 */
package AI;

public class Rep {
	//tendency to fight
	private int fighting;
	//tendency to trade
	private int trading;
	//how much have they explored? (i think this should be removed or reworked - Arturo)
	private int exploring;
	//how many technologies have they discovered or what is the last technology they discovered
	private int technology;
	
	/**
	 * This is a <b>doc</b> comment.
	 * Basic constructor that creates a default reputation when no parameters are passed.
	 * Sets all traits to 0.
	 */
	public Rep()
	{
		fighting = 0;
		trading = 0;
		exploring = 0;
		technology = 0;
	}
	/**
	 * Creates a reputation for the player with values passes in
	 * @param f: tendency to fight
	 * @param t: tendency to trade
	 * @param e: tendency to explore
	 * @param te: how many technologies discovered
	 */
	public Rep(int f, int t, int e, int te)
	{
		fighting = f;
		trading = t;
		exploring = e;
		technology = te;
	}
	
	//standard setters and getters
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
	
	/**
	 * Creates string representation of Rep. Block style with each characteristic followed by its value.
	 * @return String Each characteristic of the reputation followed by its value
	 */
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
