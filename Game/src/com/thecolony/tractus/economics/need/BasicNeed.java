package com.thecolony.tractus.economics.need;

import java.util.List;

import com.thecolony.tractus.economics.Inventory;
import com.thecolony.tractus.economics.products.*;


public class BasicNeed implements Need {
	private Quantity need;
	
	public BasicNeed(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Inventory has) {
		return has.getAmountOf((Product)need.getUnit()) / need.getQuantity();
	}

	@Override
	public Inventory getNeededProducts() {
		Inventory result = new Inventory();
		result.addQuantityOfProduct((Product)need.getUnit(), need.getQuantity());
		return result;
	}

	private void unsupported() {
		throw new UnsupportedOperationException("Attempt to use children of a leaf node");
	}
	
	@Override
	public void add(Need child) {
		unsupported();
	}

	@Override
	public List<Need> getChildren() {
		unsupported();
		return null;
	}

	@Override
	public int getChildCount() {
		unsupported();
		return 0;
	}
	
	@Override
	public Need copy() {
		return new BasicNeed(need);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BasicNeed))
			return false;
		BasicNeed other = (BasicNeed)o;
		return other.need.equals(need);
	}
	
	@Override
	public String toString() {
		return need.getUnit().getClass().getName() + ": " + need.getQuantity();
	}

	@Override
	public int indexOf(Need child) {
		unsupported();
		return 0;
	}

	@Override
	public boolean contains(Need child) {
		unsupported();
		return false;
	}

	@Override
	public void removeChild(Need child) {
		unsupported();
	}

	@Override
	public Need getChild(int index) {
		unsupported();
		return null;
	}
	
}