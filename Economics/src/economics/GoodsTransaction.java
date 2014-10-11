package economics;

import java.util.ArrayList;
import java.util.List;

public class GoodsTransaction implements Transaction, Observable {
	private Quantity offer;
	private Quantity price;
	private Quantity revenue;
	private final double marginalPrice;
	private List<Observer> observers;
	
	public GoodsTransaction(Quantity offer, Quantity price) {
		this.offer = offer;
		this.price = price;
		this.revenue = new Quantity(price.getUnit(), 0.0);
		this.marginalPrice = price.getQuantity() / offer.getQuantity();
		this.observers = new ArrayList<Observer>();
	}

	@Override
	public void execute(double amtBought) {
		assertLessThanQuantity(amtBought);
		double moneyExchanged = amtBought * marginalPrice;
		offer = offer.subtract(offer.getUnit(), amtBought);
		price = price.subtract(price.getUnit(), moneyExchanged);
		revenue = revenue.add(revenue.getUnit(), moneyExchanged);
		alertObservers();
	}


	private void assertLessThanQuantity(double amtBought) {
		if (amtBought > offer.getQuantity())
			throw new IllegalArgumentException("Attempt to buy than offered.");
	}

	@Override
	public Quantity getOffer() {
		return offer;
	}
	
	@Override
	public Quantity getPrice() {
		return price;
	}

	@Override
	public double getMarginalPrice() {
		return marginalPrice;
	}
	
	@Override
	public Quantity getRevenue() {
		return revenue;
	}

	@Override
	public void acceptObserver(Observer other) {
		observers.add(other);
	}

	@Override
	public void removeObserver(Observer other) {
		observers.remove(other);
	}

	@Override
	public void alertObservers() {
		for (Observer observer : observers)
			observer.update(this);
	}
}
