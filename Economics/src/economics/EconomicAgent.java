package economics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EconomicAgent implements Buyer, Seller {
	
	private Map<Good, Stock> stock = new HashMap<Good, Stock>();
	private Market market;
	private Producer factory;
	private double health;
	
	public EconomicAgent(Good firmProduces) {
		if (firmProduces == null)
			throw new NullPointerException("Cannot have a non-productive firm!");
		factory = new Factory(firmProduces);
	}
	
	public double getMoney() {
		return stock.get("money").getQuantity();
	}
	public Stock getStock() {
		return new Stock(stock.get(firmOutput));
	}
	
	public void step() {
		buyGoods(market);
		factory.performMaintenance(stock);
		Stock output = factory.step(stock);
	}

	@Override
	public void postAdvertisement(Market market) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyGoods(Market market) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNeed(Stock need) {
		// TODO Auto-generated method stub
		
	}
}
