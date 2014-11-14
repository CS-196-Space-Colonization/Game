package com.thecolony.tractus.player.ai;
import java.util.Comparator;

public class CommandComparator implements Comparator<Command>
{
	public int compare(Command c1, Command c2)
	{
		return c2.getPriority() - c1.getPriority();
	}
}
