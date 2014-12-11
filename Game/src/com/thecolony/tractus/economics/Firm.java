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
            Inventory inv = take();
            supplier.give(inv);
            supplier.buyGoods();
            inv = supplier.take();
            production.give(inv);
            production.runProductionStep();
            production.performMaintenance();
            inv = production.take();
            sales.give(inv);
            sales.postAdvertisements();
            inv = sales.take();
            this.give(inv);
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
