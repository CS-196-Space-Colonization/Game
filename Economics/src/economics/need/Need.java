package economics.need;

import economics.Inventory;

public interface Need {
	Inventory getNeededProducts();
	double portionFulfilled(Inventory has);
	void insert(Need element, int index);
	void remove(int index);
	void remove(Need object);
	boolean getAllowsChildren();
	Need getChildAt(int childIndex);
	int getChildCount();
	int getIndex(Need node);
	boolean isLeaf();
	Need copy();
}