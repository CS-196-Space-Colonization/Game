package com.thecolony.tractus;


import com.thecolony.tractus.player.ai.Reputations;

public class TestMain
{
	public static void main(String[] args)
	{
		int numberOfPlayers = 2;
		Reputations.setNumberOfPlayers(numberOfPlayers);
		System.out.println(Reputations.getReputation(0));
	}
}
