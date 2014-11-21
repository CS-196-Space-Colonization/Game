package economics.need;

import java.util.List;

import tree.mutable.MutableTreeNode;

import economics.Inventory;

public interface Need {
	Inventory getNeededProducts();
	double portionFulfilled(Inventory has);
	void add(Need child);
	List<Need> getChildren();
	int getChildCount();
	int indexOf(MutableTreeNode<Need> child);
	boolean contains(MutableTreeNode<Need> child);
	void removeChild(MutableTreeNode<Need> child);
	Need copy();
}