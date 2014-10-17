package space_colonization;

public class flotilla_creator {
	shipFactory first;
	int money;
	int people;
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
	public void create

}
