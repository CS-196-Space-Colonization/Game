package AI;

public class Reputations {
	 //holds the reputation for ever player throughout the game
	private static Rep[] reputations;
	private static int numberOfPlayers;
	
	/*
	 * @Precondition: number of players is already set.
	 */
	private static void createArray()
	{
		reputations = new Rep[numberOfPlayers];
		for(int i = 0; i < numberOfPlayers; i++)
		{
			reputations[i] = new Rep();
		}
	}
	
	public static void setNumberOfPlayers(int numberOfPlayers)
	{
		Reputations.numberOfPlayers = numberOfPlayers;
		createArray();
	}
	
	/*
	 * @Precondition: playerNumber is a valid number within the range of reputations
	 */
	public static Rep getReputation(int playerNumber)
	{
		return reputations[playerNumber];
	}
	
	
}
