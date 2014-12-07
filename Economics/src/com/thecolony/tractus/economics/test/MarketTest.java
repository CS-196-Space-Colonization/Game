package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.products.*;

public class MarketTest {
	private Market getTestMarket() {
		return new BasicMarket();
	}
	
	@Test
	public void testAddGetOffer() {
		Market market = getTestMarket();
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 5.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new OakWood(), 55.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 115.0), new Quantity(new Money(), 150.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Steel(), 5124.0), new Quantity(new Money(), 121340.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Labor(), 5000000.0), new Quantity(new Money(), 10.0)));
		market.addOffer(new GoodsTransaction(new Quantity(new Iron(), 5.0), new Quantity(new Money(), 10.0)));
	}
}
