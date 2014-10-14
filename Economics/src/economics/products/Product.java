package economics.products;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

import economics.Quantity;
import economics.Unit;
 
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(initialPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((inputGoods == null) ? 0 : inputGoods.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
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
