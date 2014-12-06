/**
 * @author Arturo Guerrero
 */
package com.thecolony.tractus.player.ai;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.thecolony.tractus.player.Player;

public abstract class NPC extends Player
{
	public static final int TYPE_NONE = 0;
	public static final int TYPE_MILITARY = 1;
	public static final int TYPE_TRADING = 2;
	public static final int TYPE_RESEARCHING = 3;

	private int level;
	private int type;
	private boolean inGame;

	private Comparator<Command> comparator = new CommandComparator();
	private PriorityQueue<Command> commands;

	public NPC(int playerNumber)
	{
		super(playerNumber);
		commands = new PriorityQueue<Command>(0,comparator);
		setType(TYPE_NONE);
		setInGame(true);
	}

	public NPC(int playerNumber, int type)
	{
		this(playerNumber);
		this.setType(type);
	}

	public void nextCommand()
	{
		Command c = removeCommand();
		if (c != null)
		{
			executeCommand(c);
		}
		else
		{
			executeCommand(new Command(Command.DEFAULT, 0));
		}
	}

	private void executeCommand(Command c)
	{
		switch (c.getCommand())
		{
			case Command.ATTACK:
				// attack *see mark
				// get target
				break;
			case Command.CREATE:
				// make things
				break;
			case Command.BUY:
				// buy things
				break;
			case Command.SELL:
				// sell things
				break;
			default:
				// explore for now
				break;
		}
	}

	/**
	 * @Precondition NPC is still in the game
	 */
	public abstract void act();

	/**
	 * Add each command to the priority queue. Commands of the same priority
	 * will be added in a random order.
	 * 
	 * @param command
	 * @param priority
	 */
	public void addCommand(int command, int priority)
	{
		commands.add(new Command(command, priority));
	}

	public Command removeCommand()
	{
		return commands.remove();
	}

	public Command peekCommand()
	{
		return commands.peek();
	}

	public boolean hasCommands()
	{
		return !commands.isEmpty();
	}

	public void addToFront(int command)
	{
		addCommand(command, peekCommand().getPriority() + 1);
	}

	public int getLevel()
	{
		return level;
	}

	private void setLevel(int level)
	{
		this.level = level;
	}

	public int getType()
	{
		return type;
	}

	private void setType(int type)
	{
		this.type = type;
	}

	public boolean isInGame()
	{
		return inGame;
	}

	public void setInGame(boolean inGame)
	{
		this.inGame = inGame;
	}
}
