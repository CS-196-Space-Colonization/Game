package tree.mutable;
import java.util.List;

public interface MutableTreeNode<T> {
	void add(MutableTreeNode<T> child);
	MutableTreeNode<T> getParent();
	List<MutableTreeNode<T>> getChildren();
	void removeFromParent();
	void setParent(MutableTreeNode<T> newParent);
	void removeChild(MutableTreeNode<T> child);
	boolean contains(MutableTreeNode<T> child);
	int indexOf(MutableTreeNode<T> child);
	T getData();
	void setData(T newData);
}