package economics;

import java.util.ArrayList;
import java.util.List;


public class NeedBranch extends AbstractNeedNode implements NeedTreeNode {
	private List<NeedTreeNode> children;
	public NeedBranch() {
		children = new ArrayList<>();
	}
	
	@Override
	public double portionFulfilled(Inventory inventory) {
		double fulfilled = Double.MAX_VALUE;
		for (NeedTreeNode need : children) {
			fulfilled = Math.min(fulfilled, need.portionFulfilled(inventory));
		}
		return fulfilled;
	}

	@Override
	public void insert(NeedTreeNode element, int index) {
		children.add(index, (NeedTreeNode)element);
	}

	@Override
	public void remove(int index) {
		children.remove(index);
	}

	@Override
	public void remove(NeedTreeNode object) {
		children.remove((NeedTreeNode)object);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public NeedTreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public int getIndex(NeedTreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public NeedTreeNode copy() {
		NeedBranch copied = new NeedBranch();
		for (int i = 0; i < children.size(); i++) {
			copied.insert(children.get(i), i);
		}
		return copied;
	}

	@Override
	public List<BasicNeed> toList() {
		List<BasicNeed> result = new ArrayList<>();
		for (NeedTreeNode child : children) {
			result.addAll(child.toList());
		}
		return result;
	}
}
