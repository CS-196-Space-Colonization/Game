package com.thecolony.tractus.economics;

import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;


public class Firm extends AbstractAgent {
	private Inventory inventory;
	private Buyer supplier;
	private Seller sales;
	private Manufactory production;
	
	public Firm(Market m, Product product) {
		super(m);
		inventory = new Inventory();
		supplier = new BasicBuyer(m);
		sales = new BasicSeller(m);
		production = new Manufactory(product);
		supplier.setNeeds(makeNeeds());
	}
	
	private Need makeNeeds() {
		Need inputNeeds = production.getInputNeeds();
		Need maintenanceGoods = production.getMaintenanceNeeds();
		Need allNeeds = new NeedBranch();
		allNeeds.add(inputNeeds);
		allNeeds.add(maintenanceGoods);
		return allNeeds;
	}
	
	public void step() {
		inventory = sales.take();
		supplier.give(inventory);
		supplier.buyGoods();
		inventory = supplier.take();
		production.give(inventory);
		production.runProductionStep();
		production.performMaintenance();
		inventory = production.take();
		sales.give(inventory);
		sales.postAdvertisements();
	}
}
