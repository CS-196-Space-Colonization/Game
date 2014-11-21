package economics.need;

import java.util.ArrayList;
import java.util.List;

import economics.Inventory;
import tree.mutable.*;

public class NeedBranch implements Need {
	private MutableTreeNode<Need> needs = new MutableTreeNodeImpl<>();

	@Override
	public Inventory getNeededProducts() {
		Inventory inv = new Inventory();
		for (MutableTreeNode<Need> needNode: needs.getChildren())
			inv.addInventoryFrom(needNode.getData().getNeededProducts());
		return inv;
	}

	@Override
	public double portionFulfilled(Inventory has) {
		double max = 1.0;
		for (MutableTreeNode<Need> needNode: needs.getChildren())
			max = Math.min(max, needNode.getData().portionFulfilled(has));
		return max;
	}

	@Override
	public void add(Need child) {
		needs.add(new MutableTreeNodeImpl<Need>(child));
	}

	@Override
	public List<Need> getChildren() {
		List<Need> needsList = new ArrayList<>();
		for (MutableTreeNode<Need> child : needs.getChildren())
			needsList.add(child.getData());
		return needsList;
	}

	@Override
	public void removeChild(MutableTreeNode<Need> child) {
		needs.removeChild(child);
	}

	@Override
	public boolean contains(MutableTreeNode<Need> child) {
		return needs.contains(child);
	}

	@Override
	public int indexOf(MutableTreeNode<Need> child) {
		return needs.indexOf(child);
	}

	@Override
	public int getChildCount() {
		return needs.getChildren().size();
	}

	@Override
	public Need copy() {
		Need newCopy = new NeedBranch();
		for (MutableTreeNode<Need> need : needs.getChildren())
			newCopy.add(need.getData());
		return newCopy;
	}
}
