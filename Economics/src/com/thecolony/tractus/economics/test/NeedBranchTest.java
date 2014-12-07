package com.thecolony.tractus.economics.test;

import org.junit.*;

import com.thecolony.tractus.economics.Inventory;
import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;

import junit.framework.TestCase;

public class NeedBranchTest extends TestCase {
	
	@Test
	public void testCreation() {
		Need needs = givenDefaultNeeds();
		assertTrue(needs.getChildCount() == 2);
		Need branch = needs.getChild(0);
		assertTrue(branch instanceof NeedBranch);
		Need directLeaf = needs.getChild(1);
		assertTrue(directLeaf instanceof BasicNeed);
		Quantity directLeafQty = directLeaf.getNeededProducts().getQuantityOf(new Iron());
		assertTrue(Double.compare(directLeafQty.getQuantity(), 401.0) == 0);
		assertTrue(branch.getChildCount() == 2);
		Inventory totalNeeded = needs.getNeededProducts();
		assertTrue(totalNeeded.getProducts().size() == 3);
	}
	
	@Test
	public void testEqualsIdentical() {
		Need needs = givenDefaultNeeds();
		Need needsTwo = givenDefaultNeeds();
		assertTrue(needs.equals(needsTwo));
	}
	
	@Test
	public void testEqualsSelf() {
		Need needs = givenDefaultNeeds();
		assertTrue(needs.equals(needs));
	}
	
	@Test
	public void testEqualsCopy() {
		Need needs = givenDefaultNeeds();
		assertTrue(needs.equals(needs.copy()));
	}
	
	@Test
	public void testRemoves() {
		Need needs = givenDefaultNeeds();
		needs.removeChild(needs.getChild(0));
		assertTrue(needs.getChildCount() == 1);
		assertTrue(needs.getChild(0) instanceof BasicNeed);
	}
	
	private Need givenDefaultNeeds() {
		Need root = new NeedBranch();
		Need branch = new NeedBranch();
		root.add(branch);
		root.add(new BasicNeed(new Quantity(new Iron(), 401.0)));
		branch.add(new BasicNeed(new Quantity(new OakWood(), 101.0)));
		branch.add(new BasicNeed(new Quantity(new Money(), 101.0)));
		return root;
	}

}
