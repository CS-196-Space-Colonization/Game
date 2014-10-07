package economics;

public interface EconomicAgent {
	void enterMarket(Market market);
	void give(Inventory inventoryToExchange);
	void take(Inventory inventoryToExchange);
}
