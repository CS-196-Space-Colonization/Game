package space_colonization;

public class War_Simulator {
	public static void main(String[] args)
	{
		Fleet one = new Fleet("Attack", 10);
		Fleet two = new Fleet("Defend", 10);
		Time world = new Time();
		one.warSim(two);
		
	}
}
