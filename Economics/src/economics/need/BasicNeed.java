package economics.need;

import java.util.List;

import tree.mutable.MutableTreeNode;
import economics.Inventory;
import economics.products.*;

public class BasicNeed implements Need {
	private Quantity need;
	
	public BasicNeed(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Inventory has) {
		return has.getAmountOf((Product)need.getUnit()) / need.getQuantity();
	}

	@Override
	public Inventory getNeededProducts() {
		Inventory result = new Inventory();
		result.addQuantityOfProduct((Product)need.getUnit(), need.getQuantity());
		return result;
	}

	private void unsupported() {
		throw new UnsupportedOperationException("Attempt to use children of a leaf node");
	}
	
	@Override
	public void add(Need child) {
		unsupported();
	}

	@Override
	public List<Need> getChildren() {
		unsupported();
		return null;
	}

	@Override
	public int getChildCount() {
		unsupported();
		return 0;
	}

	@Override
	public int indexOf(MutableTreeNode<Need> child) {
		unsupported();
		return 0;
	}

	@Override
	public boolean contains(MutableTreeNode<Need> child) {
		unsupported();
		return false;
	}

	@Override
	public void removeChild(MutableTreeNode<Need> child) {
		unsupported();
	}
	
	public Need copy() {
		return new BasicNeed(need);
	}
}