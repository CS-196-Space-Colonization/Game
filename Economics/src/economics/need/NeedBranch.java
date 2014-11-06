package economics.need;

import java.util.ArrayList;
import java.util.List;

import economics.Inventory;
import tree.mutable.*;

public class NeedBranch implements Need {
	private MutableTreeNode<Need> needs = new MutableTreeNodeImpl<>();

	@Override
	public Inventory getNeededProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double portionFulfilled(Inventory has) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void add(Need child) {
		needs.add(new MutableTreeNodeImpl<Need>(child));
	}

	@Override
	public Need getParent() {
		MutableTreeNode<Need> par = needs.getParent();
		return par != null ? par.getData() : null;
	}

	@Override
	public List<Need> getChildren() {
		List<Need> needsList = new ArrayList<>();
		for (MutableTreeNode<Need> child : needs.getChildren())
			needsList.add(child.getData());
		return needsList;
	}

	@Override
	public void removeFromParent() {
		needs.removeFromParent();
	}

	@Override
	public void setParent(Need newParent) {
		needs.setParent(newParent);
	}

	@Override
	public void removeChild(Need child) {
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
}
