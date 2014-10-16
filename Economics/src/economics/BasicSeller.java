package economics;

import economics.products.Product;
import economics.products.Quantity;

public class BasicSeller implements Seller, Observer {
	private final Inventory inventory;
	private Market market;

	public BasicSeller(Market market) {
		this.inventory = new Inventory();
		this.market = market;
	}

	@Override
	public void enterMarket(Market market) {
		this.market = market;
	}

	@Override
	public void give(Inventory inventoryToExchange) {
		inventory.transferContentsFrom(inventoryToExchange);
	}
	
	@Override
	public void take(Inventory inventoryToExchange) {
		inventoryToExchange.transferContentsFrom(inventory);
	}
	
	@Override
	public void postAdvertisements() {
		for (Product product : inventory.getProducts()) {
			Quantity offer = new Quantity(product, inventory.getAmountOf(product));
			Product money = market.GOOD_PROTOTYPES.get("money");
			Quantity price = new Quantity(money, market.getLastPrice(product));
			Transaction marketTransaction = new GoodsTransaction(offer, price);
			market.addOffer(marketTransaction);
		}
	}

	@Override
	public void update(Observable other) {
		Transaction completed = (Transaction)other;
		Quantity asset = completed.getOffer();
		Quantity money = completed.getRevenue();
		inventory.setQuantityOf((Product)asset.getUnit(), asset.getQuantity());
		inventory.addQuantityOfProduct((Product)money.getUnit(), money.getQuantity());
	}
}