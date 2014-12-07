package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;


public class BasicBuyerTest {
	private Market testMarket = new BuyerTestMarket();
	
	@Test
	public void testCreation() {
		Buyer underTest = givenBuyerWithTestMarket();
		assertTrue("Buyers should not start with needs!", underTest.getNeeds().getChildCount() == 0);
	}

	private Buyer givenBuyerWithTestMarket() {
		Buyer underTest = new BasicBuyer(testMarket);
		return underTest;
	}
	
	@Test
	public void testSetNeeds() {
		Buyer underTest = givenBuyerWithTestMarket();
		Need rootNeed = makeNeeds();
		underTest.setNeeds(rootNeed);
		assertTrue("Buyer does not properly set needs!", underTest.getNeeds().equals(rootNeed));
	}
	
	@Test
	public void testBuyGoods() {
		Buyer underTest = givenBuyerWithTestMarket();
		Inventory inventoryToExchange = new Inventory();
		inventoryToExchange.addQuantityOfProduct(new Money(), 10000000.0);
		underTest.give(inventoryToExchange);
		Need needs = makeNeeds();
		assertTrue("Buyer does not recognize it has money.", underTest.getSpendingMoney() > 0);
		underTest.setNeeds(needs);
		underTest.buyGoods();
		Inventory got = underTest.take();
		System.out.println(got);
		assertTrue("Buyer failed to buy needs!", needs.portionFulfilled(got) >= 1);
	}

	private Need makeNeeds() {
		Need rootNeed = new NeedBranch();
		Need metalNeed = new NeedBranch();
		rootNeed.add(metalNeed);
		metalNeed.add(new BasicNeed(new Quantity(new Iron(), 1.0)));
		metalNeed.add(new BasicNeed(new Quantity(new Steel(), 10.0)));
		rootNeed.add(new BasicNeed(new Quantity(new Labor(), 100.0)));
		return rootNeed;
	}

}
