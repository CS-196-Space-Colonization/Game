package com.thecolony.tractus.player.ai.battle;

public class Time {
	private static int time;
	public Time()
	{
		time = 0;
	}
	public Time(int t)
	{
		time = t;
	}
	public static int getTime()
	{
		return time;
	}
	public void setTiem(int t)
	{
		time = t;
	}
	public void addTime()
	{
		time++;
	}

}
