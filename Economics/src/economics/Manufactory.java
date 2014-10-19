package economics;

import java.util.Map;

import economics.need.*;
import economics.products.Product;
import economics.products.Quantity;

public class Manufactory implements Producer {
	private final Product production;
	private Inventory inventory;
	private Need inputNeed;
	private Machinery machines;

	public Manufactory(Product productionGood) {
		production = productionGood;
		inventory = new Inventory();
		createMachinery();
		calculateInputNeeds();
	}

	private void createMachinery() {
		Machinery machines = new Machinery();
		machines.setThroughput(1.0);
		machines.setHealth(1.0);
		machines.setEfficiency(1.0);
	}

	@Override
	public void runProductionStep() {
		double quantityProduced = inputNeed.portionFulfilled(inventory) * machines.getMaximumDailyProduction();
		inventory.addQuantityOfProduct(production, quantityProduced);
	}

	@Override
	public Need getInputNeeds() {
		Map<Product, Quantity> inputGoods = production.getInputGoods();
		Need inputs = new NeedBranch();
		for (Product product : inputGoods.keySet()) {
			Quantity baseQuantity = inventory.getQuantityOf(product);
			Quantity actualQuantity = new Quantity(product, baseQuantity.getQuantity() * machines.getInputModifier());
			Need inputNeed = new BasicNeed(actualQuantity);
			inputs.insert(inputNeed, inputs.getChildCount()+1);
		}
		return inputs;
	}
	
	@Override 
	public void calculateInputNeeds() {
		inputNeed = getInputNeeds();
	}
	
	public void performMaintenance() {
		machines.performMaintenance(inventory);
	}
	
	@Override
	public Product getProductionGood() {
		return production;
	}

	public double getEfficiency() {
		return machines.getEfficiency();
	}

	public void setEfficiency(double efficiency) {
		machines.setEfficiency(efficiency);
	}

	public double getThroughput() {
		return machines.getThroughput();
	}

	public void setThroughput(double throughput) {
		machines.setThroughput(throughput);
	}
}
