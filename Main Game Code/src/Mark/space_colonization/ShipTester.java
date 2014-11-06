package space_colonization;

public class ShipTester {
	public static void main(String[] args)
	{
		ship one = new ship("heal");
		one.setEqualStats(5000);
		ship two = new ship("build");
		two.setEqualStats(50);
		ship three = new ship("three");
		three.setEqualStats(500);
		System.out.println(one.getName());
		ship[] b = new ship[3];
		b[0] = one;
		b[1] = two;
		b[2] = three;
		flotilla a = new flotilla(b, true);
		System.out.println(a.getShip(0).getName());
		System.out.println(a.getShip(1).getName());
		System.out.println(a.getShip(2).getName());
		a.sortByAttack();
		System.out.println(a.getShip(0).getName());
		System.out.println(a.getShip(1).getName());
		System.out.println(a.getShip(2).getName());
	}
}
