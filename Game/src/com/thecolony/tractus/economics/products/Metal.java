package com.thecolony.tractus.economics.products;

import java.util.Map;

public abstract class Metal extends Product {
	public Metal(String type, double initialPrice, Map<Product, Quantity> inputs, Map<Product, Quantity> maintenance) {
		super(type, initialPrice, inputs, maintenance);
	}
}
