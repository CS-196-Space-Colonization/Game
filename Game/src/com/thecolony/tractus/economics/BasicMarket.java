package com.thecolony.tractus.economics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.thecolony.tractus.economics.products.Product;
import com.thecolony.tractus.economics.products.Quantity;


public class BasicMarket implements Market, Observer {
	public final ImmutableMap<String, Product> GOOD_PROTOTYPES;
	private Map<Product, LinkedList<Transaction>> market;
	private Map<Product, Double> lastPrices;
	
	public BasicMarket(ImmutableMap<String, Product> goods) {
		GOOD_PROTOTYPES = goods;
		market = new HashMap<Product, LinkedList<Transaction>>();
		lastPrices = new HashMap<Product, Double>();
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
		Quantity offer = transaction.getOffer();
		Product onOffer = (Product)offer.getUnit();
		transaction.acceptObserver(this);
		market.get(onOffer).add(transaction);
	}

	@Override
	public double getLastPrice(Product product) {
		Double lastPrice = lastPrices.get(product);
		if (lastPrice == null)
			return 0.0;
		return product.getInitialPrice();
	}

	@Override
	public Product getMoney() {
		return GOOD_PROTOTYPES.get("money");
	}

	@Override
	public void update(Observable other) {
		Transaction transaction = (Transaction)other;
		Quantity offer = transaction.getOffer();
		if (Double.compare(0.0, offer.getQuantity()) == 0)
			market.remove(transaction);
	}
}
