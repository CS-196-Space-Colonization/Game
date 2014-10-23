package economics.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;


import economics.GoodsTransaction;
import economics.Transaction;
import economics.BasicDealScorer;
import economics.products.Quantity;

public class BasicDealScorerTest {
	@Test
	public void testCorrectOrder() {
		Transaction[] raw = getTransactionsArray();
		List<Transaction> offers = Arrays.asList(raw);
		Collections.sort(offers, new BasicDealScorer());
		assertTrue(Double.compare(offers.get(0).getMarginalPrice(), 1.0/15.0) == 0);
		assertTrue(Double.compare(offers.get(1).getMarginalPrice(), 1.0/1.0) == 0);
		assertTrue(Double.compare(offers.get(2).getMarginalPrice(), 15.0/1.0) == 0);
	}
	private Transaction[] getTransactionsArray() {
		Transaction[] raw = new Transaction[] {
				 new GoodsTransaction(new Quantity(ProductsService.get("iron"), 1.0), 
									 new Quantity(ProductsService.get("money"), 1.0)),
				 new GoodsTransaction(new Quantity(ProductsService.get("iron"), 1.0), 
						 			 new Quantity(ProductsService.get("money"), 15.0)),
	 			 new GoodsTransaction(new Quantity(ProductsService.get("iron"), 15.0), 
	 					  			 new Quantity(ProductsService.get("money"), 1.0)),
				
		};
		return raw;
	}

}
