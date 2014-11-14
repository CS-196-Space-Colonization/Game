package com.thecolony.tractus.player.ai;

public class Command
{
	public static final int DEFAULT = -1;
	public static final int MOVE = 0;
	public static final int ATTACK = 1;
	public static final int BUY = 2;
	public static final int SELL = 3;
	public static final int RESEARCH = 4;
	public static final int CREATE = 5;
	public static final int EXPLORE = 6; // I think explore would do similar to
											// what move does, but go to places
											// where you haven't been

	private int priority;
	private int command;

	public Command(int command, int priority)
	{
		if (isValid(command))
			this.command = command;
		else
			this.command = DEFAULT;
		this.priority = priority;
	}

	public static boolean isValid(int command)
	{
		switch(command)
		{
			case MOVE: case ATTACK: case BUY: case SELL: case RESEARCH: case CREATE: case EXPLORE:
				return true;
			default:
				return false;
		}
	}

	public int getPriority()
	{
		return priority;
	}

	public int getCommand()
	{
		return command;
	}

	public String toString()
	{
		switch (command)
		{
			case MOVE:
				return "Move" + ": " + priority;
			case ATTACK:
				return "Attack" + ": " + priority;
			case BUY:
				return "Buy" + ": " + priority;
			case SELL:
				return "Sell" + ": " + priority;
			case RESEARCH:
				return "Research" + ": " + priority;
			case CREATE:
				return "Create" + ": " + priority;
			case EXPLORE:
				return "Explore" + ": " + priority;
			default:
				return "Default" + ": " + priority;
		}

	}
}
