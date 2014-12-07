package com.thecolony.tractus.economics.products;

import java.util.Map;
import java.util.HashMap;

public class Steel extends Metal {

	public Steel() {
		super("Steel", 50.0, makeInputs(), makeMaintenance());
		
		
	}

	private static Map<Product, Quantity> makeInputs() {
		Map<Product, Quantity> inputs = new HashMap<>();
		inputs.put(new Carbon(), new Quantity(new Carbon(), 1.0));
		inputs.put(new Iron(), new Quantity(new Iron(), 5.0));
		return inputs;
	}
	
	private static Map<Product, Quantity> makeMaintenance() {
		Map<Product, Quantity> inputs = new HashMap<>();
		inputs.put(new Carbon(), new Quantity(new Wood(), 1.0));
		return inputs;
	}

}
