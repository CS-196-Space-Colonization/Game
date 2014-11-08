package Arturo;

/**
 * This is a default AI class that is going to be used primarily for testing
 * purposes. They have no real priority sorting for the different actions that
 * they can take, but will try to execute all commands in order.
 * 
 * @author Arturo Guerrero
 *
 */
public class DefaultAI extends NPC
{

	public DefaultAI(Map map, int playerNumber)
	{
		super(map, playerNumber, NPC.TYPE_NONE);
	}

	/**
	 * This method is the one called by the main game loop on every AI. Tries to
	 * add things that it needs to do the queue, and execute them
	 * @see NPC.act()
	 */
	public void act()
	{
		// do stuff
	}

}
