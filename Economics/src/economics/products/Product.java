package economics.products;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

import economics.Quantity;
import economics.Unit;
 
public class Product implements Unit { 
	private final double initialPrice;
	private final ImmutableMap<Product, Quantity> inputGoods;

	public Product(double initialPrice, Map<Product, Quantity> inputs) {
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
	}
	
	public double getInitialPrice() {
		return initialPrice;
	}

	public Map<Product, Quantity> getInputGoods() {
		return inputGoods;
	}
}
