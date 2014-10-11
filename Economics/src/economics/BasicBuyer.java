package economics;

import java.util.Collections;
import java.util.List;

public class BasicBuyer implements Buyer {
	private NeedTreeNode needs;
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
	public NeedTreeNode getNeeds() {
		return needs.copy();
	}

	@Override
	public void setNeeds(NeedTreeNode needs) {
		this.needs = needs;
	}

	@Override
	public void buyGoods() {
		for (BasicNeed need : needs.toList()) {
			buyNeed(need);
			if (getSpendingMoney() <= 0.0)
				return;
		}
	}
	
	private double getSpendingMoney() {
		return inventory.getQuantityOf(market.GOOD_PROTOTYPES.get("money"));
	}

	private void buyNeed(BasicNeed need) {
		Product productNeeded = (Product)need.getNeed().getUnit();
		List<Transaction> offers = market.getOffers(productNeeded);
		Quantity bought = new Quantity(productNeeded, 0.0);
		double amtNeeded = need.getNeed().getQuantity();
		do {
			Transaction bestOffer = Collections.min(offers, new BasicDealScorer());
			bestOffer.execute(calculateBought(bought, bestOffer, amtNeeded));
		} while(need.portionFulfilled(inventory) < 1.0 && getSpendingMoney() > 0.0);
	}

	private double calculateBought(Quantity bought, Transaction bestOffer, double amtNeeded) {
		return Math.min(bestOffer.getMarginalPrice()*getSpendingMoney(), 
						Math.min(amtNeeded - bought.getQuantity(), 
								 bestOffer.getOffer().getQuantity()));
	}
}
