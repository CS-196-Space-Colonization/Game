package AI;

public class Command 
{
	public static final int TEST = -1;
	public static final int MOVE = 0;
	public static final int ATTACK = 1;
	public static final int BUY = 2;
	public static final int SELL = 3;
	public static final int REASEARCH = 4;
	public static final int CREATE = 5;
	
	private int priority;
	private int command;
	
	public Command(int command, int priority)
	{
		this.setCommand(command);
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String toString()
	{
		return command + ": " + priority;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}
}
