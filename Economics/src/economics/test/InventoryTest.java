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
import economics.products.Product;
import economics.products.Quantity;

public class InventoryTest {
	private Map<Product, Quantity> rawInventory = makeRawSampleInventory();
	
	private Map<Product, Quantity> makeRawSampleInventory() {
		Map<Product, Quantity> rawInventory = new HashMap<Product, Quantity>();
		Product iron = ProductsService.get("iron");
		Product steel = ProductsService.get("steel");
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
		Product iron = ProductsService.get("iron");
		assertFalse("Parameterized constructor results in empty inventory!", test.isEmpty());
		assertTrue("Parameterized constructor does not match input map! ", contentsEqual(test, rawInventory));
		rawInventory.put(iron, new Quantity(iron, 1.0));
		assertFalse("Parameterized constructor does not copy input map! ", Double.compare(test.getAmountOf(iron), 1.0) == 0);
	}
	
	@Test
	public void testGetProducts() {
		Inventory inventory = makeInventory();
		Set<Product> products = inventory.getProducts();
		assertTrue("Inventory constructed with iron does not contain it", products.contains(ProductsService.get("iron")));
		assertTrue("Inventory constructed with steel does not contain it", products.contains(ProductsService.get("steel")));
		assertFalse("Inventory constructed without labor contains it", products.contains(ProductsService.get("labor")));
	}
	
	@Test
	public void testNoLabor() {
		Inventory inventory = new Inventory();
		assertExpectedEqualsActual("Calling GetQuantityOf on empty inventory does not result in 0", 0.0, inventory.getAmountOf(ProductsService.get("iron")));
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
	public void testGetQuantityAgainstGetAmount() {
		Inventory inventory = makeInventory();
		Product iron = ProductsService.get("iron");
		assertTrue("GetQuantity does not match GetAmount!", inventory.getAmountOf(iron) == inventory.getQuantityOf(iron).getQuantity());
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
		Product iron = ProductsService.get("iron");
		double original = inventory.getAmountOf(iron);
		double expected = Math.max(original - qty, 0.0);
		inventory.removeQuantityOfProduct(iron, qty);
		double actual = inventory.getAmountOf(iron);
		assertExpectedEqualsActual("Removing quantity " + qty + " gives wrong result!", expected, actual);
	}
	
	private void testAddQuantityImpl(double qty) {
		Inventory inventory = makeInventory();
		Product iron = ProductsService.get("iron");
		double original = inventory.getAmountOf(iron);
		inventory.addQuantityOfProduct(iron, qty);
		double expected = Math.max(original + qty, 0.0);
		double actual = inventory.getAmountOf(iron);
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
			if (Double.compare(rawInventory.get(product).getQuantity(), test.getAmountOf(product)) != 0) {
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
	public void emptyEqualsEmpty() {
		Inventory inventory = makeInventory();
		Inventory empty = new Inventory();
		inventory.clear();
		assertTrue("Two empty inventories should equal each other!", inventory.equals(empty));
	}
	
	@Test
	public void testDoesNotEqualNull() {
		Inventory inventory = makeInventory();
		assertFalse("An inventory should not equal null!", inventory.equals(null));
	}
	
	@Test
	public void testEqualsCopy() {
		Inventory one = makeInventory();
		Inventory copy = new Inventory(one);
		assertTrue("An inventory should equal its copy!", one.equals(copy));
	}
	
	@Test
	public void testDoesNotEqualNonempty() {
		Inventory inventory = makeInventory();
		Inventory other = new Inventory();
		other.addQuantityOfProduct(ProductsService.get("iron"), 5.0);
		assertFalse("Equals does not correctly compare inventories!", inventory.equals(other));
	}
	
	@Test
	public void testDoesEqualIdentical() {
		Inventory inventory = makeInventory();
		Inventory other = new Inventory();
		other.addQuantityOfProduct(ProductsService.get("iron"), 5.0);
		other.addQuantityOfProduct(ProductsService.get("steel"), 10.0);
		assertFalse("Equals does not correctly compare inventories!", inventory.equals(other));
	}
}
