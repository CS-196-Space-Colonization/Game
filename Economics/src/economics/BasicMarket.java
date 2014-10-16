package economics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import economics.products.Product;

public class BasicMarket implements Market {
	public final ImmutableMap<String, Product> GOOD_PROTOTYPES;
	private Map<Product, LinkedList<Transaction>> market;
	private Map<Product, Double> lastPrices;
	
	public BasicMarket(ImmutableMap<String, Product> goods) {
		GOOD_PROTOTYPES = goods;
		market = new HashMap<Product, LinkedList<Transaction>>();
		lastPrices = new HashMap<>();
		refreshOffers();
	}
	
	private void refreshOffers() {
		ImmutableSet<String> keys = GOOD_PROTOTYPES.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			Product forSale = GOOD_PROTOTYPES.get(i.next());
			market.put(forSale, new LinkedList<Transaction>());
		}
	}
	
	
	@Override
	public List<Transaction> getOffers(Product needed) {
		return market.get(needed);
	}

	
	@Override
	public void addOffer(Transaction transaction) {
	}

	@Override
	public double getLastPrice(Product product) {
		Double lastPrice = lastPrices.get(product);
		if (lastPrice == null)
			return 0.0;
		return product.getInitialPrice();
	}
}
