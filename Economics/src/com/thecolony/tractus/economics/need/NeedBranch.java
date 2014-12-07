package com.thecolony.tractus.economics.need;

import java.util.ArrayList;
import java.util.List;

import com.thecolony.tractus.economics.Inventory;


public class NeedBranch implements Need {
	private List<Need> needs = new ArrayList<Need>();

	@Override
	public Inventory getNeededProducts() {
		Inventory inv = new Inventory();
		for (Need needNode: needs)
			inv.addInventoryFrom(needNode.getNeededProducts());
		return inv;
	}

	@Override
	public double portionFulfilled(Inventory has) {
		double max = 1.0;
		for (Need needNode: needs)
			max = Math.min(max, needNode.portionFulfilled(has));
		return max;
	}

	@Override
	public void add(Need child) {
		needs.add(child);
	}

	@Override
	public List<Need> getChildren() {
		return new ArrayList<Need>(needs);
	}

	@Override
	public void removeChild(Need child) {
		needs.remove(child);
	}

	@Override
	public boolean contains(Need child) {
		return needs.contains(child);
	}

	@Override
	public int indexOf(Need child) {
		return needs.indexOf(child);
	}

	@Override
	public int getChildCount() {
		return needs.size();
	}

	@Override
	public Need copy() {
		Need newCopy = new NeedBranch();
		for (Need need : needs)
			newCopy.add(need);
		return newCopy;
	}
	
	@Override
	public Need getChild(int index) {
		return needs.get(index);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NeedBranch))
			return false;
		NeedBranch otherNeed = (NeedBranch)o;
		return otherNeed.needs.containsAll(needs);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		for (Need child : needs) {
			result.append(child.toString());
			result.append(", ");
		}
		result.append("}, ");
		return result.toString();
	}
}
