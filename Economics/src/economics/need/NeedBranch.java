package economics.need;

import java.util.ArrayList;
import java.util.List;

import economics.Inventory;


public class NeedBranch extends AbstractNeedNode implements Need {
	private List<Need> children;
	
	public NeedBranch() {
		children = new ArrayList<>();
	}
	
	@Override
	public double portionFulfilled(Inventory inventory) {
		double fulfilled = Double.MAX_VALUE;
		for (Need need : children) {
			fulfilled = Math.min(fulfilled, need.portionFulfilled(inventory));
		}
		return fulfilled;
	}

	@Override
	public void insert(Need element, int index) {
		children.add(index, (Need)element);
	}

	@Override
	public void remove(int index) {
		children.remove(index);
	}

	@Override
	public void remove(Need object) {
		children.remove(object);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public Need getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public int getIndex(Need node) {
		return children.indexOf(node);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Need copy() {
		NeedBranch copied = new NeedBranch();
		for (int i = 0; i < children.size(); i++) {
			copied.insert(children.get(i), i);
		}
		return copied;
	}

	@Override
	public Inventory getNeededProducts() {
		Inventory needed = new Inventory();
		for (Need child : children) {
			needed.addInventoryFrom(child.getNeededProducts());
		}
		return needed;
	}
}
