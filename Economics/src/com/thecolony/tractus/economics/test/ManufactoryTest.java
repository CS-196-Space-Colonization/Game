package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;


public class ManufactoryTest {
	private Manufactory givenDefaultManufactory() {
		return new Manufactory(new Steel());
	}
	
	private Need getExpectedNeeds() {
		Need root = new NeedBranch();
		Need inputNeeds = new NeedBranch();
		Need maintenanceNeeds = new NeedBranch();
		root.add(inputNeeds);
		root.add(maintenanceNeeds);
		inputNeeds.add(new BasicNeed(new Quantity(new Carbon(), 1.0)));
		inputNeeds.add(new BasicNeed(new Quantity(new Iron(), 5.0)));
		maintenanceNeeds.add(new BasicNeed(new Quantity(new Wood(), 1.0)));
		return root;
	}
	
	@Test
	public void testCalculatedNeedEqualsExpected() {
		Manufactory underTest = givenDefaultManufactory();
		Need needs = underTest.getNeeds();
		Need expectedNeeds = getExpectedNeeds();
		assertTrue(needs.equals(expectedNeeds));
	}
	
	@Test
	public void testIdealProductionStep() {
		Manufactory underTest = givenDefaultManufactory();
		Need needs = underTest.getNeeds();
		Inventory products = needs.getNeededProducts();
		underTest.give(products);
		underTest.runProductionStep();
		Inventory result = underTest.take();
		assertTrue(Double.compare(result.getAmountOf(new Steel()), 1.0) == 0);
		assertTrue(Double.compare(result.getAmountOf(new Iron()), 0.0) == 0);
		assertTrue(Double.compare(result.getAmountOf(new Wood()), 1.0) == 0);
	}
	
	@Test
	public void testWorstPossibleProductionStep() {
		Manufactory underTest = givenDefaultManufactory();
		underTest.runProductionStep();
		assertTrue(underTest.take().isEmpty());
	}
	
	@Test
	public void testMediocreProductionStep() {
		Manufactory underTest = givenDefaultManufactory();
		Need needs = underTest.getNeeds();
		Inventory inputs = needs.getNeededProducts();
		for (Product p : inputs.getProducts()) {
			inputs.removeQuantityOfProduct(p, inputs.getAmountOf(p) / 2);
		}
		inputs.addQuantityOfProduct(new Steel(), 100.0);
		inputs.addQuantityOfProduct(new Money(), 10000.0);
		underTest.give(inputs);
		underTest.runProductionStep();
		Inventory result = underTest.take();
		assertTrue("Wrong amount of steel", Double.compare(result.getAmountOf(new Steel()), 100.5)==0);
		assertTrue("Wrong amount of money", Double.compare(result.getAmountOf(new Money()), 10000.0)==0);
		assertTrue("Leftover Iron", Double.compare(result.getAmountOf(new Iron()), 0.0)==0);
	}
	
	@Test
	public void testUnevenProductionStep() {
		Manufactory underTest = givenDefaultManufactory();
		Need needs = underTest.getNeeds();
		Inventory inputs = needs.getNeededProducts();
		inputs.removeQuantityOfProduct(new Carbon(), .5);
		underTest.give(inputs);
		underTest.runProductionStep();
		Inventory result = underTest.take();
		assertTrue("Wrong amount of steel", Double.compare(result.getAmountOf(new Steel()), .5)==0);
	}
}
