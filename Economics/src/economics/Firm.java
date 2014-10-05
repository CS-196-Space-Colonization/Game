package economics;


import static economics.Good.*;
import java.util.HashMap;
import java.util.Map;

public class Firm {
	
	private Map<Good, Double> stock = new HashMap<Good, Double>();
	private double dailyProductionOutput;
	private Good firmOutputResource;
	
	public Firm(Good firmProduces, double dailyProduction) {
		if (firmProduces == null)
			throw new NullPointerException("Cannot have a non-productive firm!");
		firmOutputResource = firmProduces;
		this.dailyProductionOutput = dailyProduction;
		stock.put(firmOutputResource, 0.0);
	}
	
	public double getMoney() {
		return stock.get("Money");
	}
	public double getStock() {
		return stock.get(firmOutputResource);
	}
	
	public double getDailyProduction() {
		return dailyProductionOutput;
	}
	
	public void step() {
		stock.put(firmOutputResource, stock.get(firmOutputResource) + dailyProductionOutput);
	}
	
	public void transferTo(Firm transferTo, String itemToTransfer, double quantity) {
		transferTo.stock.put(firmOutputResource, stock.get(firmOutputResource) + dailyProductionOutput);
		this.stock.put(firmOutputResource, stock.get(firmOutputResource) - dailyProductionOutput);
	}

	public void pay(Firm seller, double quantity) {
		seller.transferTo(seller, "money", quantity);
	}
}
