package space_colonization;

public class War_Simulator {
	public static void main(String[] args)
	{
		Fleet one = new Fleet("Attack", 10);
		Fleet two = new Fleet("Defence", 5);
		System.out.println(one.getFleetLength());
		System.out.println(two.getFleetLength());
		System.out.println(Fleet.warSim(one, two));
		
	}
}
