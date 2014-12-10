package com.thecolony.tractus.economics.products;

import java.util.Collections;

public class Labor extends Product {
	public Labor() {
		super("Labor", 1.0, Collections.<Product, Quantity> emptyMap());
	}
}
