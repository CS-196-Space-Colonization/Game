package economics;

import java.util.HashMap;
import java.util.Map;

public class EconomicAgent implements Buyer, Seller {
	
	private Map<Good, Double> inventory = new HashMap<Good, Double>();
	private Producer factory;
	private Market market;
	
	public EconomicAgent(Good firmProduces) {
		if (firmProduces == null)
			throw new NullPointerException("Cannot have a non-productive firm!");
		factory = new Factory(firmProduces, 1.0);
	}
	
	public void enterMarket(Market market) {
		this.market = market;
	}
	
	public double getMoney() {
		return inventory.get(market.GOOD_PROTOTYPES.get("money"));
	}
	public Double getDouble() {
		return new Double(inventory.get(factory.productionGood()));
	}
	
	public void step() {
		buyGoods(market);
		factory.performMaintenance(inventory);
		factory.runProductionStep(inventory);
		postAdvertisement();
	}

	@Override
	public void postAdvertisement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyGoods(Market market) {
		// TODO Auto-generated method stub
		
	}
}
