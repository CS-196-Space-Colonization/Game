package economics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {
	private Map<Product, Quantity> inventory;
	
	public Inventory() {
		inventory = new HashMap<>();
	}
	
	public Inventory(Map<Product, Quantity> inventory) {
		this.inventory = inventory;
	}
	
	public void transferContentsFrom(Inventory other) {
		addInventoryFrom(other);
		for (Product product : other.getProducts()) {
			other.removeProductFromInventory(product, other.getQuantityOf(product));
		}
	}
	
	public void addInventoryFrom(Inventory other) {
		for (Product product : other.getProducts()) {
			addProduct(product, other.getQuantityOf(product));
		}
	}
	
	public boolean contains(Product product) {
		return inventory.get(product) == null;
	}
	
	public void setQuantityOf(Product product, double quantity) {
		inventory.put(product, new Quantity(product, quantity));
	}
	
	public double getQuantityOf(Product product) {
		if (!contains(product))
			return 0.0;
		return inventory.get(product).getQuantity();
	}

	public void addProduct(Product product, double quantity) {
		if (contains(product))
			setQuantityOf(product, inventory.get(product).getQuantity() + quantity);
		else
			setQuantityOf(product, quantity);
	}
	
	public void removeQuantityOfProduct(Product product, double quantity) {
		double oldQuantity = getQuantityOf(product);
		if (oldQuantity >= quantity) {
			removeProductFromInventory(product, quantity);
		} else {
			removeProductFromInventory(product, oldQuantity);
		}
	}
	
	private void removeProductFromInventory(Product product, double quantity) {
		addProduct(product, -quantity);
	}
	
	public Set<Product> getProducts() {
		return inventory.keySet();
	}

	public static Inventory fromMap(Map<Product, Double> goods) {
		Inventory result = new Inventory();
		Set<Product> keySet = goods.keySet();
		for (Product product : keySet) {
			result.addProduct(product, goods.get(product));
		}
		return result;
	}
}
