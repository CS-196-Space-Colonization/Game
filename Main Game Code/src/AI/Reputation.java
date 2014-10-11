package AI;

public class Reputation {
	 //holds the reputation for ever player throughout the game
	static Rep[] reputations;
	
	public Reputation(int numberOfPlayers)
	{
		reputations = new Rep[numberOfPlayers];
	}
	
	/*
	 * @Precondition: playerNumber is a valid number within the range of reputations
	 */
	public static Rep getReputation(int playerNumber)
	{
		return reputations[playerNumber];
	}
	
	
}
