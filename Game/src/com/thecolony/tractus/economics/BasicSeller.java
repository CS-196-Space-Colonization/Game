package com.thecolony.tractus.economics;

import com.thecolony.tractus.economics.products.Product;
import com.thecolony.tractus.economics.products.Quantity;

public class BasicSeller extends AbstractAgent implements Seller, Observer {

	public BasicSeller(Market market) {
		super(market);
	}
	
	@Override
	public void postAdvertisements() {
		Inventory inventory = getInventory();
		Market market = getMarket();
		for (Product product : inventory.getProducts()) {
			Quantity offer = new Quantity(product, inventory.getAmountOf(product));
			Quantity price = new Quantity(market.getMoney(), market.getLastPrice(product));
			Transaction marketTransaction = new GoodsTransaction(offer, price);
			marketTransaction.acceptObserver(this);
			market.addOffer(marketTransaction);
		}
	}

	@Override
	public void update(Observable other) {
		Transaction completed = (Transaction)other;
		Quantity asset = completed.getOffer();
		Quantity money = completed.getRevenue();
		Inventory inv = getInventory();
		inv.addQuantityOfProduct(money);
		inv.removeQuantityOfProduct(asset);
	}
}