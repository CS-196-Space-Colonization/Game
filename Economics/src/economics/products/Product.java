package economics.products;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

 
public class Product implements Unit { 
	private final double initialPrice;
	private final String type;
	private final ImmutableMap<Product, Quantity> inputGoods;

	public Product(String type, double initialPrice, Map<Product, Quantity> inputs) {
		this.type = type;
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
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

	public static Product get(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
