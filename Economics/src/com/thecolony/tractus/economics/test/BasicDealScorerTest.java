package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import com.thecolony.tractus.economics.BasicDealScorer;
import com.thecolony.tractus.economics.GoodsTransaction;
import com.thecolony.tractus.economics.Transaction;
import com.thecolony.tractus.economics.products.*;



public class BasicDealScorerTest {
	@Test
	public void testCorrectOrder() {
		Transaction[] raw = getTransactionsArray();
		List<Transaction> offers = Arrays.asList(raw);
		Collections.sort(offers, new BasicDealScorer());
		assertTrue(Double.compare(offers.get(0).getMarginalPrice(), 1.0/15.0) == 0);
		assertTrue(Double.compare(offers.get(1).getMarginalPrice(), 1.0/1.0) == 0);
		assertTrue(Double.compare(offers.get(2).getMarginalPrice(), 1.0/1.0) == 0);
		assertTrue(Double.compare(offers.get(3).getMarginalPrice(), 15.0/1.0) == 0);
	}
	
	@Test
	public void testTieBreaker() {
		Transaction[] raw = getTransactionsArray();
		List<Transaction> offers = Arrays.asList(raw);
		Collections.sort(offers, new BasicDealScorer());
		assertTrue(offers.get(1).getOffer().getQuantity() > offers.get(2).getOffer().getQuantity());
	}
	
	private Transaction[] getTransactionsArray() {
		Transaction[] raw = new Transaction[] {
				 new GoodsTransaction(new Quantity(new Iron(), 1.0), 
									 new Quantity(new Money(), 1.0)),
				 new GoodsTransaction(new Quantity(new Iron(), 1.0), 
						 			 new Quantity(new Money(), 15.0)),
	 			 new GoodsTransaction(new Quantity(new Iron(), 15.0), 
	 					  			 new Quantity(new Money(), 1.0)),
	 			 new GoodsTransaction(new Quantity(new Iron(), 300.0),
	 					 			  new Quantity(new Money(), 300.0))
				
		};
		return raw;
	}

}
