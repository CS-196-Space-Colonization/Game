package tree.mutable;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class MutableTreeNodeTest {

	@Test
	public void testDefault() {
		MutableTreeNode<String> strTree = new MutableTreeNodeImpl<>();
		assertEmptyChildren(strTree);
		assertEquals("Default constructor should not initialize a parent", strTree.getParent(), MutableTreeNodeImpl.nullTree);
	}

	@Test
	public void testParent() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>(strTreeRoot);
		assertEmptyChildren(strTreeChild);
		assertParentContainsFirstChild(strTreeRoot, strTreeChild);
	}

	private void assertParentContainsFirstChild(MutableTreeNode<String> strTreeRoot, MutableTreeNode<String> strTreeChild) {
		assertTrue("Did correctly initialize parent in child!", strTreeChild.getParent() == strTreeRoot);
		assertTrue("Did add child to parent!", strTreeRoot.contains(strTreeChild));
		assertTrue("Did add child as first", strTreeRoot.indexOf(strTreeChild) == 0);
	}
	
	@Test 
	public void testAdd() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>();
		strTreeRoot.add(strTreeChild);
		assertParentContainsFirstChild(strTreeRoot, strTreeChild);
	}
	
	@Test 
	public void testSetParent() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>();
		strTreeChild.setParent(strTreeRoot);
		assertParentContainsFirstChild(strTreeRoot, strTreeChild);
	}
	
	@Test
	public void testSetParentSameWithoutClear() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>();
		strTreeChild.setParent(strTreeRoot);
		strTreeChild.setParent(strTreeRoot);
		assertParentContainsFirstChild(strTreeRoot, strTreeChild);
	}
	
	@Test
	public void testSetParentSameWithClear() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>();
		strTreeChild.setParent(strTreeRoot);
		strTreeChild.setParent(MutableTreeNodeImpl.nullTree);
		strTreeChild.setParent(strTreeRoot);
		assertParentContainsFirstChild(strTreeRoot, strTreeChild);
	}
	
	@Test
	public void testRemoveParent() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		MutableTreeNode<String> strTreeChild = new MutableTreeNodeImpl<>(strTreeRoot);
		strTreeChild.removeFromParent();
		assertEmptyChildren(strTreeRoot);
		assertTrue("Did not clear parent of strTreeChild", strTreeChild.getParent() == MutableTreeNodeImpl.nullTree);
	}

	private void assertEmptyChildren(MutableTreeNode<String> strTree) {
		List<MutableTreeNode<String>> children = strTree.getChildren();
		assertNotNull("Should initialize a list", children);
		assertTrue("Should initialize an empty list", children.isEmpty());
	}
	
	@Test
	public void testData() {
		MutableTreeNode<String> strTreeRoot = new MutableTreeNodeImpl<>();
		strTreeRoot.setData("Hello World");
		String data = strTreeRoot.getData();
		assertTrue("MutableTreeNode does not correctly maintain data.", data.equals("Hello World"));
	}
}
