package Arturo;

public class Command
{
	public static final int DEFAULT = -1;
	public static final int MOVE = 0;
	public static final int ATTACK = 1;
	public static final int BUY = 2;
	public static final int SELL = 3;
	public static final int RESEARCH = 4;
	public static final int CREATE = 5;
	public static final int EXPlORE = 6; //I think explore would do similar to what move does, but go to places where you haven't been

	private int priority;
	private int command;

	public Command(int command, int priority)
	{
		this.setCommand(command);
		this.priority = priority;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
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
			case EXPlORE:
				return "Explore" + ": " + priority;

			default:
				return "Default" + ": " + priority;
		}

	}

	public int getCommand()
	{
		return command;
	}

	public void setCommand(int command)
	{
		this.command = command;
	}
}
