package com.thecolony.tractus;


/**
 * Holds the reputation for ever player throughout the game
 * @author Arturo Guerrero
 * @version v0.01 2014-10-29
 *
 */
public class Reputations {
	private static Rep[] reputations;
	private static int numberOfPlayers;
	
	/**
	 * Creates the array that holds the reputations for all players
	 * Should and can only be called by the setNumberOfPlayer method, which should be called once
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
	/**
	 * Sets the number of players to the class variable and then creates an array of that size
	 * @param n: number of players
	 */
	public static void setNumberOfPlayers(int n)
	{
		numberOfPlayers = n;
		createArray();
	}
	
	/*
	 * Get the reputation according to player number
	 * @Precondition: playerNumber is a valid number within the range of reputations
	 * @return Rep for that player
	 */
	public static Rep getReputation(int playerNumber)
	{
		return reputations[playerNumber];
	}
	
	
}
