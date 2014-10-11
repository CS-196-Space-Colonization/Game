package economics;

import java.util.List;

public interface NeedTreeNode {
	double portionFulfilled(Inventory inventory);
	void insert(NeedTreeNode element, int index);
	void remove(int index);
	void remove(NeedTreeNode object);
	void removeFromParent();
	void setParent(NeedTreeNode parent);
	boolean getAllowsChildren();
	NeedTreeNode getChildAt(int childIndex);
	int getChildCount();
	int getIndex(NeedTreeNode node);
	NeedTreeNode getParent();
	boolean isLeaf();
	NeedTreeNode copy();
	List<BasicNeed> toList();
}