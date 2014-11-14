package com.thecolony.tractus.player.ai;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestQueue 
{
	public static void main(String[] args)
	{
		Comparator<Command> comparator = new CommandComparator();
		PriorityQueue<Command> queue = new PriorityQueue<>(comparator);
		
		for(int i = 0; i < 5; i++)
			queue.add(new Command(i, i));
		
		for(int i = 33; i < 40; i++)
			queue.add(new Command(i, i));
		
		System.out.println("Added to queue");
		
		while(queue.size() != 0)
			System.out.println(queue.remove());
	}
}
