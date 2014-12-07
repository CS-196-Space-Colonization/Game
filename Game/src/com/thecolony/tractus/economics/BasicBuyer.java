package com.thecolony.tractus.economics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thecolony.tractus.economics.need.Need;
import com.thecolony.tractus.economics.need.NeedBranch;
import com.thecolony.tractus.economics.products.Product;
import com.thecolony.tractus.economics.products.Quantity;


public class BasicBuyer extends AbstractAgent implements Buyer {
	private Product money;
	private Need needs;
	
	public BasicBuyer(Market market) {
		super(market);
		money = market.getMoney();
		setNeeds(new NeedBranch());
	}
	
	@Override
	public void setMarket(Market market) {
		super.setMarket(market);
		this.money = market.getMoney();
	}
	
	@Override
	public Need getNeeds() {
		return needs.copy();
	}

	@Override
	public void setNeeds(Need needs) {
		if (needs == null)
			needs = new NeedBranch();
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
	
	@Override
	public double getSpendingMoney() {
		return getInventory().getAmountOf(money);
	}

	private void buyGood(Quantity needed) {
		List<Transaction> offers = getMarket().getOffers((Product)needed.getUnit());
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
		return getInventory().getAmountOf((Product)needed.getUnit()) < needed.getQuantity();
	}

	private void executeTransaction(Quantity needed, Transaction bestOffer) {
		Product productNeeded = (Product)needed.getUnit();
		double amtNeeded = needed.getQuantity();
		Quantity bought = getInventory().getQuantityOf(productNeeded);
		double amountActuallyBought = calculateBought(bestOffer, amtNeeded - bought.getQuantity());
		bestOffer.execute(amountActuallyBought);
		getInventory().addQuantityOfProduct(productNeeded, amountActuallyBought);
		getInventory().removeQuantityOfProduct(money, amountActuallyBought*bestOffer.getMarginalPrice());
	}

	private double calculateBought(Transaction bestOffer, double amtNeeded) {
		return Math.min(bestOffer.getMarginalPrice()*getSpendingMoney(), 
						Math.min(amtNeeded, 
								 bestOffer.getOffer().getQuantity()));
	}
}
