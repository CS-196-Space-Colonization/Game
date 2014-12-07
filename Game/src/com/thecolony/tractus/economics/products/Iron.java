package com.thecolony.tractus.economics.products;

import java.util.Collections;

public class Iron extends Metal {
	public Iron() {
		super("Iron", 7.0, Collections.<Product, Quantity> emptyMap(), Collections.<Product, Quantity> emptyMap());
	}
}
