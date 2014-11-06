package Arturo;

public class DefaultAI extends NPC
{

	public DefaultAI(Map map, int playerNumber)
	{
		super(map, playerNumber, NPC.TYPE_NONE);
	}
	
	/**
	 * This method is the one called by the main game loop on every AI.
	 * Tries to add things that it needs to do the queue, and execute them
	 */
	public void act()
	{
		//do stuff
	}

}
