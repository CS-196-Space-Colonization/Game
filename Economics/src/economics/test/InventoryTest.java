package economics.test;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	private Map<Product, Quantity> rawInventory;
	
	@Before
	public void setup() {
		validProducts = ImmutableMap.copyOf(makeValidProducts());
		rawInventory = makeRawSampleInventory();
	}

	private Map<String, Product> makeValidProducts() {
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
		return products;
	}
	
	private Map<Product, Quantity> makeRawSampleInventory() {
		Map<Product, Quantity> rawInventory = new HashMap<Product, Quantity>();
		Product iron = validProducts.get("iron");
		Product steel = validProducts.get("steel");
		rawInventory.put(iron, new Quantity(iron, 9.0));
		rawInventory.put(steel, new Quantity(steel, 18.0));
		return rawInventory;
	}
	
	private Inventory makeInventory() {
		Inventory inventory = new Inventory(rawInventory);
		return inventory;
	}
	
	@Test
	public void testDefaultCreationResultsInEmptyInventory() {
		Inventory inventory = new Inventory();
		assertTrue("Default inventory construction does not result in an empty inventory!", inventory.isEmpty());
	}
	
	@Test
	public void testParameterizedConstructor() {
		Inventory test = makeInventory();
		Product iron = validProducts.get("iron");
		assertFalse("Parameterized constructor results in empty inventory!", test.isEmpty());
		assertTrue("Parameterized constructor does not match input map! ", contentsEqual(test, rawInventory));
		rawInventory.put(iron, new Quantity(iron, 1.0));
		assertFalse("Parameterized constructor does not copy input map! ", Double.compare(test.getQuantityOf(iron), 1.0) == 0);
	}
	
	@Test
	public void testGetProducts() {
		Inventory inventory = makeInventory();
		Set<Product> products = inventory.getProducts();
		assertTrue("Inventory constructed with iron does not contain it", products.contains(validProducts.get("iron")));
		assertTrue("Inventory constructed with steel does not contain it", products.contains(validProducts.get("steel")));
		assertFalse("Inventory constructed without labor contains it", products.contains(validProducts.get("labor")));
	}
	
	@Test
	public void testNoLabor() {
		Inventory inventory = new Inventory();
		assertExpectedEqualsActual("Calling GetQuantityOf on empty inventory does not result in 0", 0.0, inventory.getQuantityOf(validProducts.get("iron")));
	}
	
	@Test
	public void testAddQuantityBasic() {
		testAddQuantityImpl(5.0);
	}
	
	@Test
	public void testAddQuantityHuge() {
		testAddQuantityImpl(500000000000000000.0);
	}
	
	@Test
	public void testAddQuantityTiny() {
		testAddQuantityImpl(1e-3);
	}
	
	@Test
	public void testAddQuantityNegative() {
		try {
			testAddQuantityImpl(-1);
			fail("addQuantityOfProduct should not allow negatives!");
		} catch (IllegalArgumentException e) {
			//Expected.
		}
	}
	
	@Test
	public void testRemoveQuantity() {
		testRemoveQuantityImpl(5.0);
	}
	
	@Test
	public void testRemoveQuantityHuge() {
		testRemoveQuantityImpl(100.0);
	}
	
	@Test
	public void testRemoveQuantityNegative() {
		try {
			testRemoveQuantityImpl(-1);
			fail("removeQuantityOfProduct should not allow negatives!");
		} catch (IllegalArgumentException e) {
			//Expected.
		}
	}
	
	private void testRemoveQuantityImpl(double qty) {
		Inventory inventory = makeInventory();
		Product iron = validProducts.get("iron");
		double original = inventory.getQuantityOf(iron);
		double expected = Math.max(original - qty, 0.0);
		inventory.removeQuantityOfProduct(iron, qty);
		double actual = inventory.getQuantityOf(iron);
		assertExpectedEqualsActual("Removing quantity " + qty + " gives wrong result!", expected, actual);
	}
	
	private void testAddQuantityImpl(double qty) {
		Inventory inventory = makeInventory();
		Product iron = validProducts.get("iron");
		double original = inventory.getQuantityOf(iron);
		inventory.addQuantityOfProduct(iron, qty);
		double expected = Math.max(original + qty, 0.0);
		double actual = inventory.getQuantityOf(iron);
		assertExpectedEqualsActual("Adding quantity " + qty + " gives wrong result!", expected, actual);
	}

	private void assertExpectedEqualsActual(String message, double expected, double actual){
		if (Double.compare(expected, actual) != 0) {
			System.err.printf(message + "\n Expected: %f, Actual: %f", expected, actual);
			fail();
		}
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
	
	@Test
	public void testAddInventoryFrom() {
		Inventory inventory = makeInventory();
		Inventory secondInventory = new Inventory();
		secondInventory.addInventoryFrom(inventory);
		assertTrue("addInventoryFrom does not copy inventories!", inventory.equals(secondInventory));
	}
	
	@Test
	public void testTransferContentsFrom() {
		Inventory empty = new Inventory();
		Inventory source = makeInventory();
		empty.transferContentsFrom(source);
		assertTrue("transferContentsFrom does not correctly copy inventory!", empty.equals(makeInventory()));
		assertTrue("transferContentsFrom does not remove contents from other inventory!", source.isEmpty());
	}
	
	@Test
	public void testEqualsSelf() {
		Inventory inventory = makeInventory();
		assertTrue("Inventory does not equal self!", inventory.equals(inventory));
	}
	
	@Test
	public void testDoesNotEqualEmpty() {
		Inventory inventory = makeInventory();
		assertFalse("Equals does not correctly compare inventories!", inventory.equals(new Inventory()));
	}
	
	@Test
	public void testDoesNotEqualNonempty() {
		Inventory inventory = makeInventory();
		Inventory other = new Inventory();
		other.addQuantityOfProduct(validProducts.get("iron"), 5.0);
		assertFalse("Equals does not correctly compare inventories!", inventory.equals(other));
	}
	
	@Test
	public void testDoesEqualIdentical() {
		Inventory inventory = makeInventory();
		Inventory other = new Inventory();
		other.addQuantityOfProduct(validProducts.get("iron"), 5.0);
		other.addQuantityOfProduct(validProducts.get("steel"), 10.0);
		assertFalse("Equals does not correctly compare inventories!", inventory.equals(other));
	}
}
