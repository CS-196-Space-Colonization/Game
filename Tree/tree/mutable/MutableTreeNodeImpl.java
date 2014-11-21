package tree.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MutableTreeNodeImpl<T> implements MutableTreeNode<T> {
	public static final MutableTreeNode nullTree = new MutableTreeNode() {

		@Override
		public void add(MutableTreeNode child) {}
		
		@Override
		public List getChildren() {
			return Collections.EMPTY_LIST;
		}


		@Override
		public void removeChild(MutableTreeNode child) {}

		@Override
		public boolean contains(MutableTreeNode child) {
			return false;
		}

		@Override
		public int indexOf(MutableTreeNode child) {
			return -1;
		}

		@Override
		public Object getData() {
			return null;
		}
		
		@Override
		public void setData(Object newData) {}
	};

	
	private MutableTreeNode<T> parent;
	private List<MutableTreeNode<T>> children; 
	private T data;
	
	public MutableTreeNodeImpl() {
		this(nullTree, Collections.EMPTY_LIST);
	}
	
	public MutableTreeNodeImpl(T data) {
		this.data = data;
	}
	
	public MutableTreeNodeImpl(MutableTreeNode<T> parent) {
		this(parent, Collections.EMPTY_LIST);
	}
	
	public MutableTreeNodeImpl(MutableTreeNode<T> parent, List<MutableTreeNode<T>> children) {
		this.parent = parent;
		this.children = new ArrayList<>(children);
		parent.add(this);
	}
	
	@Override
	public List<MutableTreeNode<T>> getChildren() {
		return new ArrayList<>(children);
	}
	
	@Override
	public void add(MutableTreeNode<T> child) {
		children.add(child);
	}
	
	@Override
	public void removeChild(MutableTreeNode<T> child) {
		children.remove(child);
	}
	
	@Override
	public boolean contains(MutableTreeNode<T> child) {
		return children.contains(child);
	}

	@Override
	public int indexOf(MutableTreeNode<T> child) {
		return children.indexOf(child);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
