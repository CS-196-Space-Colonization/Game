package com.thecolony.tractus.economics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.Product;
import com.thecolony.tractus.economics.products.Quantity;


public class Manufactory extends AbstractAgent implements Producer {
	private final Product production;
	private Need inputNeed;
	private Machinery machines;

	public Manufactory(Product productionGood) {
		super(null);
		production = productionGood;
		createMachinery();
		calculateInputNeeds();
	}

	private void createMachinery() {
		machines = new Machinery();
		machines.setMaintenanceNeeds(makeMaintenanceNeeds(production.getMaintenanceGoods()));
		machines.setThroughput(1.0);
		machines.setHealth(1.0);
		machines.setEfficiency(1.0);
	}

	private Need makeMaintenanceNeeds(ImmutableMap<Product, Quantity> maintenanceGoods) {
		NeedBranch result = new NeedBranch();
		for (Product p : maintenanceGoods.keySet()) {
			result.add(new BasicNeed(maintenanceGoods.get(p)));
		}
		return result;
	}

	@Override
	public void runProductionStep() {
		Inventory inventory = getInventory();
		double portionOfMaxProduced = Math.min(1.0, inputNeed.portionFulfilled(inventory));
		double quantityProduced = portionOfMaxProduced * machines.getMaximumDailyProduction();
		inventory.addQuantityOfProduct(production, quantityProduced);
		Map<Product, Quantity> inputGoods = production.getInputGoods();
		for (Product p : inputGoods.keySet()) {
			inventory.removeQuantityOfProduct(p, portionOfMaxProduced * inputGoods.get(p).getQuantity());
		}
	}

	@Override
	public Need getInputNeeds() {
		Map<Product, Quantity> inputGoods = production.getInputGoods();
		Need inputs = new NeedBranch();
		for (Product product : inputGoods.keySet()) {
			Quantity baseQuantity = inputGoods.get(product);
			Quantity actualQuantity = new Quantity(product, baseQuantity.getQuantity() * machines.getInputModifier());
			Need inputNeed = new BasicNeed(actualQuantity);
			inputs.add(inputNeed);
		}
		return inputs;
	}
	
	public Need getMaintenanceNeeds() {
		return machines.getMaintenanceNeeds();
	}
	
	@Override 
	public void calculateInputNeeds() {
		inputNeed = getInputNeeds();
	}
	
	public Need getNeeds() {
		NeedBranch needs = new NeedBranch();
		needs.add(getInputNeeds());
		needs.add(machines.getMaintenanceNeeds());
		return needs;
	}
	
	public void performMaintenance() {
		machines.performMaintenance(getInventory());
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
	
	public Machinery getMachinery() {
		return machines;
	}
}
