package economics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import economics.need.Need;
import economics.products.Product;
import economics.products.Quantity;

public class BasicBuyer extends AbstractAgent implements Buyer {
	private Product money;
	private Need needs;
	private Inventory inventory;
	private Market market;
	
	public BasicBuyer(Market market) {
		setMarket(market);
		inventory = new Inventory();
	}
	
	@Override
	public void setMarket(Market market) {
		super.setMarket(market);
		this.money = market.getMoney();
	}
	
	@Override
	public Need getNeeds() {
		return needs == null ? null : needs.copy();
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
		return inventory.getAmountOf(money);
	}

	private void buyGood(Quantity needed) {
		List<Transaction> offers = market.getOffers((Product)needed.getUnit());
		Comparator<Transaction> dealRater = new BasicDealScorer();
		Collections.sort(offers, dealRater);
		for (Transaction bestOffer : offers) {
			executeTransaction(needed, bestOffer);
			if (!(shouldKeepBuying(needed) && canKeepBuying()))
				break;
		} 
	}

	private boolean canKeepBuying() {
		return getSpendingMoney() > 0.0;
	}

	private boolean shouldKeepBuying(Quantity needed) {
		return inventory.getAmountOf((Product)needed.getUnit()) < needed.getQuantity();
	}

	private void executeTransaction(Quantity needed, Transaction bestOffer) {
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
