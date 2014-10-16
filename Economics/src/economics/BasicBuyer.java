package economics;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import economics.need.BasicNeed;
import economics.need.Need;
import economics.products.Product;
import economics.products.Quantity;

public class BasicBuyer implements Buyer {
	private Product money = Product.get("money");
	private Need needs;
	private Inventory inventory;
	private Market market;
	
	public BasicBuyer(Market market) {
		enterMarket(market);
		inventory = new Inventory();
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
	public Need getNeeds() {
		return needs.copy();
	}

	@Override
	public void setNeeds(Need needs) {
		this.needs = needs;
	}

	@Override
	public void buyGoods() {
		Inventory neededProducts = needs.getNeededProducts();
		for (Product product : neededProducts.getProducts()) {
			buyGood(neededProducts.getQuantityOf(product));
			if (!canKeepBuying())
				return;
		}
	}
	
	private double getSpendingMoney() {
		return inventory.getAmountOf(Product.get("money"));
	}

	private void buyGood(Quantity needed) {
		List<Transaction> offers = market.getOffers((Product)needed.getUnit());
		do {
			Transaction bestOffer = Collections.min(offers, new BasicDealScorer());
			processTransaction(needed, bestOffer);
			offers.remove(bestOffer);
		} while(shouldKeepBuying(needed) && canKeepBuying());
	}

	private boolean canKeepBuying() {
		return getSpendingMoney() > 0.0;
	}

	private boolean shouldKeepBuying(Quantity needed) {
		return inventory.getAmountOf((Product)needed.getUnit()) < needed.getQuantity();
	}

	private void processTransaction(Quantity needed, Transaction bestOffer) {
		Product productNeeded = (Product)needed.getUnit();
		double amtNeeded = needed.getQuantity();
		Quantity bought = inventory.getQuantityOf(productNeeded);
		double amountActuallyBought = calculateBought(bestOffer, amtNeeded - bought.getQuantity());
		bestOffer.execute(amountActuallyBought);
		inventory.addQuantityOfProduct(productNeeded, amountActuallyBought);
		inventory.removeQuantityOfProduct(money, amountActuallyBought*bestOffer.getMarginalPrice());
	}

	private double calculateBought(Transaction bestOffer, double amtNeeded) {
		return Math.min(bestOffer.getMarginalPrice()*getSpendingMoney(), 
						Math.min(amtNeeded, 
								 bestOffer.getOffer().getQuantity()));
	}
}
