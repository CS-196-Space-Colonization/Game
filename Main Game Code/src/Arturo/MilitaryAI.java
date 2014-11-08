package Arturo;

public class MilitaryAI extends NPC
{

	public MilitaryAI(Map map, int playerNumber)
	{
		super(map, playerNumber,NPC.TYPE_MILITARY);
	}

	/**
	 * This method is the one called by the main game loop on every AI.
	 * Tries to add things that it needs to do the queue, and execute them
	 */
	public void act()
	{
		//do things
	}

}
