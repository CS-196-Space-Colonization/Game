package com.thecolony.tractus.economics.need;

import java.util.List;

import com.thecolony.tractus.economics.Inventory;

public interface Need {
	Inventory getNeededProducts();
	double portionFulfilled(Inventory has);
	void add(Need child);
	List<Need> getChildren();
	int getChildCount();
	int indexOf(Need child);
	boolean contains(Need child);
	void removeChild(Need child);
	Need getChild(int index);
	Need copy();
}