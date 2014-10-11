package economics;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Manufactory implements Producer {
	private static final double maxHealthChange = .03;
	private Product production;
	private Inventory inventory;
	private NeedTreeNode inputNeed;
	private NeedTreeNode maintenanceNeeds;
	private double health;
	private double efficiency;
	private double throughput;

	public Manufactory(Product productionGood, double productionQuantity) {
		this.production = productionGood;
		this.throughput = productionQuantity;
		throughput = 10.0;
		health = 1.0;
		efficiency = 1.0;
		setMaintenanceNeed(productionGood);
		setInputNeed();
	}

	private void setMaintenanceNeed(Product productionGood) {
		maintenanceNeeds = null;
	}

	@Override
	public void performMaintenance() {
		double portionFulfilled = maintenanceNeeds.portionFulfilled(inventory);
		health = Math.min(1.0, Math.max(0.0, calculateHealthChange(portionFulfilled)));
	}

	private double calculateHealthChange(double portionFulfilled) {
		double healthChange = maxHealthChange * (1-portionFulfilled);
		if (Math.abs(health) > maxHealthChange)
			healthChange = maxHealthChange * healthChange / Math.abs(healthChange);
		return healthChange;
	}

	@Override
	public void runProductionStep() {
		double quantityProduced = inputNeed.portionFulfilled(inventory) * throughput;
		inventory.addProduct(production, quantityProduced);
	}

	private void setInputNeed() {
		List<BasicNeed> inputNeeds = new LinkedList<BasicNeed>();
		Map<Product, Quantity> inputQuantities = production.getInputGoods();
		Set<Product> inputGoods = inputQuantities.keySet();
		for (Product product : inputGoods) {
			Quantity baseQuantity = inputQuantities.get(product);
			Quantity actualQuantity = new Quantity(product, baseQuantity.getQuantity() * throughput / efficiency);
			inputNeeds.add(new BasicNeed(actualQuantity));
		}
		inputNeed = null;
	}
	

	@Override
	public Product productionGood() {
		return production;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public double getThroughput() {
		return throughput;
	}

	public void setThroughput(double throughput) {
		this.throughput = throughput;
	}
}
