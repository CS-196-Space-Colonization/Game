package economics.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import economics.products.*;
import economics.products.Quantity;

public class ProductsService {
	private static final ImmutableMap<String, Product> validProducts = ImmutableMap.copyOf(makeValidProducts());

	private static Map<String, Product> makeValidProducts() {
		Map<String, Product> products = new HashMap<>();
		Product labor = new Labor();
		products.put("labor", labor);
		Map<Product, Quantity> ironInputs = new HashMap<>();
		ironInputs.put(labor, new Quantity(labor, 3.0));
		Product iron = new Iron();
		products.put("iron", iron);
		Map<Product, Quantity> steelInputs = new HashMap<>();
		steelInputs.put(labor, new Quantity(labor, 1.0));
		steelInputs.put(iron, new Quantity(iron, 1.0));
		Product steel = new Steel();
		products.put("steel", steel);
		Map<Product, Quantity> woodInputs = new HashMap<>();
		woodInputs.put(labor, new Quantity(labor, 10.0));
		Product wood = new Wood();
		products.put("wood", wood);
		Product money = new Money();
		products.put("money", money);
		return products;
	}
	
	public static Product get(String str) {
		return validProducts.get(str);
	}
}