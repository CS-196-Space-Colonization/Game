package com.thecolony.tractus.economics.products;

import java.util.Collections;

public class Money extends Product {

	public Money() {
		super("Money", 1.0, Collections.<Product, Quantity> emptyMap());
	}
	
}
