package com.thecolony.tractus.economics.test;

import java.util.ArrayList;
import java.util.List;

import com.thecolony.tractus.economics.Market;
import com.thecolony.tractus.economics.Transaction;
import com.thecolony.tractus.economics.products.Money;
import com.thecolony.tractus.economics.products.Product;


public class SellerTestMarket implements Market {
	private List<Transaction> transactions = new ArrayList<>();
	@Override
	public List<Transaction> getOffers(Product needed) {return transactions;}

	@Override
	public void addOffer(Transaction transaction) {
		transactions.add(transaction);
	}

	@Override
	public double getLastPrice(Product product) {
		return 100.0;
	}
	
	@Override
	public Product getMoney() {
		return new Money();
	}
}
