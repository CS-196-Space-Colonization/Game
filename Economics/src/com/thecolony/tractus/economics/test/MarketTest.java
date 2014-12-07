package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.products.*;

public class MarketTest {
	private Market getTestMarket() {
		Market market = new BasicMarket();
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 5.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new OakWood(), 55.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 115.0), new Quantity(new Money(), 150.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Steel(), 5124.0), new Quantity(new Money(), 121340.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Labor(), 5000000.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 5.0), new Quantity(new Money(), 10.0)));
		return market;
	}
	
	@Test
	public void testAddGetOffer() {
		Market market = getTestMarket();
		List<Transaction> ironOffers = market.getOffers(new Iron());
		assertTrue(ironOffers.size()==3);
		assertTrue(market.getOffers(new Carbon()).size() == 0);
		assertTrue(market.getOffers(new Steel()).size() == 1);
	}
	
	@Test
	public void testRemoveFinishedTransactions() {
		Market market = getTestMarket();
		List<Transaction> ironOffers = market.getOffers(new Iron());
		assertTrue(ironOffers.size()==3);
		Transaction ex = ironOffers.get(0);
		ex.execute(ex.getOffer().getQuantity());
		assertTrue(market.getOffers(new Iron()).size()==2);
	}
	
	@Test
	public void testLastPrices() {
		Market market = getTestMarket();
		List<Transaction> ironOffers = market.getOffers(new Iron());
		assertTrue(ironOffers.size()==3);
		Transaction ex = ironOffers.get(0);
		ex.execute(ex.getOffer().getQuantity());
		assertTrue(market.getLastPrice(new Iron()) != 0.0);
	}
}
