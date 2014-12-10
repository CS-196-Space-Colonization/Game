package com.thecolony.tractus.economics;

import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;


public class Firm extends AbstractAgent {
	private Buyer supplier;
	private Seller sales;
	private Manufactory production;
	
	public Firm(Market m, Product product) {
		super(m);
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
            Inventory inventory = getInventory();
		supplier.give(inventory);
		supplier.buyGoods();
		inventory = supplier.take();
		production.give(inventory);
		production.runProductionStep();
		production.performMaintenance();
		inventory = production.take();
		sales.give(inventory);
		sales.postAdvertisements();
                this.give(sales.take());
	}
	
	public Product getProductionGood() {
		return production.getProductionGood();
	}
	
	public double getQuantity() {
		return production.getYesterdaysProductionQty();
	}
        
        public String getCurrentInventory() {
            return getInventory().toString();
        }
}
