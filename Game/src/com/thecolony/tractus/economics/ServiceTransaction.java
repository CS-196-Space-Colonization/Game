package com.thecolony.tractus.economics;

import java.util.List;

import com.thecolony.tractus.economics.products.Quantity;


public class ServiceTransaction implements Transaction {
	private List<Observer> observers;

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
		for (Observer o : observers)
			o.update(this);
	}

	@Override
	public Quantity getOffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getRevenue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(double amt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMarginalPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
