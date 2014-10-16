package economics.need;

import economics.Inventory;
import economics.products.*;

public class BasicNeed extends AbstractNeedNode implements Need {
	private Quantity need;
	
	public BasicNeed(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Inventory has) {
		return has.getAmountOf((Product)need.getUnit()) / need.getQuantity();
	}

	@Override
	public void insert(Need element, int index) {
		unsupported();
	}

	@Override
	public void remove(int index) {
		unsupported();
	}

	@Override
	public void remove(Need object) {
		unsupported();
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public Need getChildAt(int childIndex) {
		unsupported();
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(Need node) {
		unsupported();
		return -1;
	}
	
	private void unsupported() {
		throw new UnsupportedOperationException("Attempt to call a child method on a leaf node.");
	}


	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Need copy() {
		return new BasicNeed(need);
	}

	@Override
	public Inventory getNeededProducts() {
		Inventory result = new Inventory();
		result.addQuantityOfProduct((Product)need.getUnit(), need.getQuantity());
		return result;
	}
}