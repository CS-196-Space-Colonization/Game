package com.thecolony.tractus.economics.products;

import java.util.Collections;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

 
public abstract class Product implements Unit { 
	private final double initialPrice;
	private final String type;
	private final ImmutableMap<Product, Quantity> inputGoods;
	private final ImmutableMap<Product, Quantity> maintenanceGoods;

	public Product(String type, double initialPrice, Map<Product, Quantity> inputs,
													 Map<Product, Quantity> maintenance) {
		this.type = type;
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
		this.maintenanceGoods = ImmutableMap.copyOf(maintenance);
	}
	
	public Product(String type, double initialPrice, Map<Product, Quantity> inputs) {
		this(type, initialPrice, inputs, Collections.<Product, Quantity> emptyMap());
	}
	
	public String getType() {
		return type;
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public Map<Product, Quantity> getInputGoods() {
		return inputGoods;
	}

	public ImmutableMap<Product, Quantity> getMaintenanceGoods() {
		return maintenanceGoods;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (Double.doubleToLongBits(initialPrice) != Double
				.doubleToLongBits(other.initialPrice))
			return false;
		if (inputGoods == null) {
			if (other.inputGoods != null)
				return false;
		} else if (!inputGoods.equals(other.inputGoods))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
