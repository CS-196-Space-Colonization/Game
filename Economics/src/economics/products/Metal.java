package economics.products;

import java.util.Map;

public abstract class Metal extends Product {
	public Metal(String type, double initialPrice, Map<Product, Quantity> inputs) {
		super(type, initialPrice, inputs);
	}
}
