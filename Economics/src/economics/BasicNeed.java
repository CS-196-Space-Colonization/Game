package economics;

import java.util.ArrayList;
import java.util.List;

public class BasicNeed extends AbstractNeedNode implements NeedTreeNode {
	private Quantity need;
	
	public BasicNeed(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Inventory has) {
		return has.getQuantityOf((Product)need.getUnit()) / need.getQuantity();
	}

	public Quantity getNeed() {
		return need;
	}

	@Override
	public void insert(NeedTreeNode element, int index) {
		unsupported();
	}

	@Override
	public void remove(int index) {
		unsupported();
	}

	@Override
	public void remove(NeedTreeNode object) {
		unsupported();
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public NeedTreeNode getChildAt(int childIndex) {
		unsupported();
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(NeedTreeNode node) {
		unsupported();
		return -1;
	}
	
	private void unsupported() {
		unsupported();
	}


	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public NeedTreeNode copy() {
		return new BasicNeed(need);
	}

	@Override
	public List<BasicNeed> toList() {
		List<BasicNeed> needs = new ArrayList<>();
		return needs;
	}
}