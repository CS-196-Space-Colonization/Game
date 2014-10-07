package economics;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
 
public class Product implements Unit {
	private final double initialPrice;
	private final ImmutableMap<Product, Quantity> inputGoods;

	private Product(double initialPrice, Map<Product, Quantity> inputs) {
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
	}
	
	public static Product makePrototype(double initialPrice, ImmutableMap<Product, Quantity> inputs) {
		return new Product(initialPrice, inputs);
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public Map<Product, Quantity> getInputGoods() {
		return inputGoods;
	}
}
