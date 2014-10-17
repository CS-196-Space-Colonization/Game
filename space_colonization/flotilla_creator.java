package space_colonization;

public class flotilla_creator {
	private static shipFactory first;
	private int money;
	private int people;
	
	public flotilla_creator()
	{
		money = 10000000;
		people = 1000000;
		first = new shipFactory(money, people);
	}
	public flotilla_creator(int Money, int People)
	{
		first = new shipFactory(Money, People);
		money = Money;
		people = People;
	}
	public void addPeople(int addedPeople)
	{
		people = people + addedPeople;
	}
	public void addMoney(int addedMoney)
	{
		money = money + addedMoney;
	}
	public static flotilla createBaseFlotilla(int fighters)//consists of one capital ship and an amount of fighters
	{
		flotilla one = new flotilla();
		ship large = new Specific_Ship("Capital");
		ship small = new Specific_Ship("Fighter");
		first.produceShip(large, one);
		int i = 0;
		while(!one.getFull())
		{
			first.produceShip(small, one);
			if(i == fighters)
			{
				one.changeFull();
			}
		}
		return one;
	}
	public static flotilla[] createFleet(int length)
	{
		flotilla[] one = new flotilla[length];
		flotilla two = createBaseFlotilla(20);
		for(int i = 0; i < length; i++)
		{
			one[i] = two;
		}
		return one;
	}

}
