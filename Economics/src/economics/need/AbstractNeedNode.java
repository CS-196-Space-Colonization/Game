package economics.need;


public abstract class AbstractNeedNode implements Need {
	private Need parent;

	@Override
	public void removeFromParent() {
		parent.remove(this);
		parent = null;
	}

	@Override
	public void setParent(Need parent) {
		this.parent = parent;
	}

	@Override
	public Need getParent() {
		return parent;
	}

}