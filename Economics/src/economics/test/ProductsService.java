package economics.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import economics.products.Product;
import economics.products.Quantity;

public class ProductsService {
	private static final ImmutableMap<String, Product> validProducts = ImmutableMap.copyOf(makeValidProducts());

	private static Map<String, Product> makeValidProducts() {
		Map<String, Product> products = new HashMap<>();
		Product labor = new Product("labor", 1.0, Collections.EMPTY_MAP);
		products.put("labor", labor);
		Map<Product, Quantity> ironInputs = new HashMap<>();
		ironInputs.put(labor, new Quantity(labor, 3.0));
		Product iron = new Product("iron", 5.0, ironInputs);
		products.put("iron", iron);
		Map<Product, Quantity> steelInputs = new HashMap<>();
		steelInputs.put(labor, new Quantity(labor, 1.0));
		steelInputs.put(iron, new Quantity(iron, 1.0));
		Product steel = new Product("steel", 10.0, steelInputs);
		products.put("steel", steel);
		Map<Product, Quantity> woodInputs = new HashMap<>();
		woodInputs.put(labor, new Quantity(labor, 10.0));
		Product wood = new Product("wood", 15.0, woodInputs);
		products.put("wood", wood);
		Product money = new Product("money", 1.0, Collections.EMPTY_MAP);
		products.put("money", money);
		return products;
	}
	
	public static Product get(String str) {
		return validProducts.get(str);
	}
}