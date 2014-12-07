package com.thecolony.tractus.economics;

import java.util.List;

import com.thecolony.tractus.economics.products.Product;

public interface Market {
	List<Transaction> getOffers(Product needed);
	void addOffer(Transaction transaction);
	double getLastPrice(Product product);
	Product getMoney();
}