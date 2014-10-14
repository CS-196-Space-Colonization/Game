package economics.test;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import economics.Inventory;
import economics.Quantity;
import economics.products.Product;

public class InventoryTest {
	private ImmutableMap<String, Product> validProducts;
	private Map<String, Product> products = new HashMap<>();
	
	@Before
	public void setup() {
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
		validProducts = ImmutableMap.copyOf(products);
	}
	
	@Test
	public void testDefaultCreationResultsInEmptyInventory() {
		Inventory inventory = new Inventory();
		assertTrue("Default inventory construction does not result in an empty inventory!", inventory.isEmpty());
	}
	
	@Test
	public void testParameterizedConstructor() {
		Map<Product, Quantity> rawInventory = new HashMap<Product, Quantity>();
		Product iron = validProducts.get("iron");
		Product steel = validProducts.get("steel");
		rawInventory.put(iron, new Quantity(iron, 9.0));
		rawInventory.put(steel, new Quantity(steel, 18.0));
		Inventory test = new Inventory(rawInventory);
		assertTrue("Parameterized constructor does not match input map! ", contentsEqual(test, rawInventory));
		rawInventory.put(iron, new Quantity(iron, 1.0));
		assertFalse("Parameterized constructor does not copy input map! ", Double.compare(test.getQuantityOf(iron), 1.0) == 0);
	}

	private boolean contentsEqual(Inventory test,
			Map<Product, Quantity> rawInventory) {
		for (Product product : rawInventory.keySet()) {
			if (Double.compare(rawInventory.get(product).getQuantity(), test.getQuantityOf(product)) != 0) {
				return false;
			}
		}
		return test.getProducts().equals(rawInventory.keySet());
	}
	

}
