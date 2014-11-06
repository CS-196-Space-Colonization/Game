package economics;

public abstract class AbstractAgent implements EconomicAgent {
	private Inventory inventory = new Inventory();
	private Market market;
	
	public void give(Inventory other) {
		inventory.addInventoryFrom(other);
	}
	
	public Inventory take() {
		Inventory inv = new Inventory(inventory);
		inventory.clear();
		return inv;
	}
	
	public Inventory peek() {
		return new Inventory(inventory);
	}
	
	public Market getMarket() {
		return market;
	}
	
	public void setMarket(Market market) {
		this.market = market;
	}
}
