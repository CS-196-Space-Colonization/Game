package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.products.*;


public class SellerTest {
	private Market testMarket = new SellerTestMarket();
	
	@Test
	public void testSellerPostsAdverts() {
		Seller underTest = givenDefaultSeller();
		underTest.postAdvertisements();
		assertTrue(testMarket.getOffers(null).size() == 3);
	}
	
	@Test
	public void testSellerGetsMoneyLosesProduct() {
		Seller underTest = givenDefaultSeller();
		underTest.postAdvertisements();
		Transaction test = testMarket.getOffers(null).get(2);
		test.execute(1.0);
		Inventory after = underTest.take();
		double amtProduct = after.getAmountOf(new Iron());
		assertTrue("Wrong amount of product " + amtProduct, Double.compare(amtProduct,19.0)==0);
		assertTrue("Wrong amount of money", Double.compare(after.getAmountOf(new Money()), 5.0)==0);
	}
	
	private Seller givenDefaultSeller() {
		Inventory defaultInventory = new Inventory();
		defaultInventory.addQuantityOfProduct(new Iron(), 20.0);
		defaultInventory.addQuantityOfProduct(new Labor(), 99.9);
		defaultInventory.addQuantityOfProduct(new OakWood(), 100.0);
		Seller seller = new BasicSeller(testMarket);
		seller.give(defaultInventory);
		return seller;
	}
}
