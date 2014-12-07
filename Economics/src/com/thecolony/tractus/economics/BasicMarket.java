package com.thecolony.tractus.economics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.thecolony.tractus.economics.products.*;


public class BasicMarket implements Market, Observer {
	private Map<Product, LinkedList<Transaction>> market;
	private Map<Product, Double> lastPrices;
	
	public BasicMarket() {
		market = new HashMap<Product, LinkedList<Transaction>>();
		lastPrices = new HashMap<>();
	}
	
	
	@Override
	public List<Transaction> getOffers(Product needed) {
		List<Transaction> result = market.get(needed);
		if (result == null)
			return new LinkedList<Transaction>();
		return result;
	}

	
	@Override
	public void addOffer(Transaction transaction) {
		Quantity offer = transaction.getOffer();
		Product onOffer = (Product)offer.getUnit();
		transaction.acceptObserver(this);
		List<Transaction> TransactionList = market.get(onOffer);
		if (TransactionList == null)
			market.put(onOffer, new LinkedList<Transaction>());
		market.get(onOffer).add(transaction);
	}

	@Override
	public double getLastPrice(Product product) {
		Double lastPrice = lastPrices.get(product);
		if (lastPrice == null)
			return 0.0;
		return lastPrice;
	}

	@Override
	public Product getMoney() {
		return new Money();
	}

	@Override
	public void update(Observable other) {
		Transaction transaction = (Transaction)other;
		Quantity offer = transaction.getOffer();
		if (Double.compare(0.0, offer.getQuantity()) == 0)
			market.remove(transaction);
		lastPrices.put((Product)transaction.getOffer().getUnit(), transaction.getMarginalPrice());
	}
}
