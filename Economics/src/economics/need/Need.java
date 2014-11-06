package economics.need;

import java.util.List;

import tree.mutable.MutableTreeNode;

import economics.Inventory;

public interface Need {
	Inventory getNeededProducts();
	double portionFulfilled(Inventory has);
	void add(Need child);
	Need getParent();
	List<Need> getChildren();
	int getChildCount();
	void removeFromParent();
	void setParent(Need newParent);
}