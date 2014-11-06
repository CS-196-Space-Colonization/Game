package Arturo;

import java.util.Scanner;

public class TestPlayer
{
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		NPC p = new DefaultAI(null, 0);
		Scanner in = new Scanner(System.in);
		System.out.println("Testing command queue...");
		for (int i = 0; i < 3; i++)
		{
			System.out.println("Enter command: (no spaces)");
			int command = in.nextInt();
			System.out.println("And now priority");
			int priority = in.nextInt();
			p.addCommand(command, priority);
		}
		p.addToFront(Command.DEFAULT);
		while (p.hasCommands())
		{
			System.out.println(p.removeCommand());
		}
	}
}
