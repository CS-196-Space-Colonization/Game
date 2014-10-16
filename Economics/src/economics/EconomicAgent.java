package economics;

public interface EconomicAgent {
	Market getMarket();
	void setMarket(Market other);
	void give(Inventory inventoryToExchange);
	Inventory take();
}
