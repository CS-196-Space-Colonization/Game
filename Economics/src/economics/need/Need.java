package economics.need;

import economics.Inventory;

public interface Need {
	Inventory getNeededProducts();
	double portionFulfilled(Inventory has);
	void insert(Need element, int index);
	void remove(int index);
	void remove(Need object);
	void removeFromParent();
	void setParent(Need parent);
	boolean getAllowsChildren();
	Need getChildAt(int childIndex);
	int getChildCount();
	int getIndex(Need node);
	Need getParent();
	boolean isLeaf();
	Need copy();
}