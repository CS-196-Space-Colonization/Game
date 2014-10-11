package economics;

public abstract class AbstractNeedNode implements NeedTreeNode {

	private NeedTreeNode parent;

	public AbstractNeedNode() {
		super();
	}

	@Override
	public void removeFromParent() {
		parent.remove(this);
	}

	@Override
	public void setParent(NeedTreeNode parent) {
		this.parent = parent;
	}

	@Override
	public NeedTreeNode getParent() {
		return parent;
	}

}