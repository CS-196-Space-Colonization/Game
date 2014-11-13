package com.thecolony.tractus;


public class TestMain 
{
	public static void main(String[] args)
	{
		int numberOfPlayers = 2;
		Reputations.setNumberOfPlayers(numberOfPlayers);
		System.out.println(Reputations.getReputation(0));
	}
}
