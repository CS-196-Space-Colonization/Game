package com.thecolony.tractus.economics.test;

import java.util.ArrayList;
import java.util.List;

import com.thecolony.tractus.economics.GoodsTransaction;
import com.thecolony.tractus.economics.Market;
import com.thecolony.tractus.economics.Transaction;
import com.thecolony.tractus.economics.products.Money;
import com.thecolony.tractus.economics.products.Product;
import com.thecolony.tractus.economics.products.Quantity;


public class BuyerTestMarket implements Market {
	@Override
	public List<Transaction> getOffers(Product needed) {
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(new GoodsTransaction(new Quantity(needed, 100.0), 
											  new Quantity(new Money(), 100.0)));
		return transactions;
	}

	@Override
	public void addOffer(Transaction transaction) {}

	@Override
	public double getLastPrice(Product product) {
		return 100.0;
	}
	
	@Override
	public Product getMoney() {
		return new Money();
	}
}
