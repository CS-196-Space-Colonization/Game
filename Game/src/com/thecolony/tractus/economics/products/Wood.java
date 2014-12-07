package com.thecolony.tractus.economics.products;

import java.util.Collections;

public class Wood extends Product {
	
	public Wood() {
		this("Wood");
	}
	
	protected Wood(String type) {
		super(type, 5.0, Collections.<Product, Quantity> emptyMap());
	}
}
