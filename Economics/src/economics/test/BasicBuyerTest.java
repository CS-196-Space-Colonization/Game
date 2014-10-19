package economics.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import economics.*;
import economics.products.Product;
import economics.products.Quantity;

public class BasicBuyerTest {
	Market testMarket = new Market() {
		private Product money = new Product("money", 1.0, Collections.EMPTY_MAP);
		@Override
		public List<Transaction> getOffers(Product needed) {
			ArrayList<Transaction> transactions = new ArrayList<>();
			transactions.add(new GoodsTransaction(new Quantity(needed, 10.0), 
												  new Quantity(Product.get("money"), 100.0)));
			return transactions;
		}

		@Override
		public void addOffer(Transaction transaction) {}

		@Override
		public double getLastPrice(Product product) {
			return 100.0;
		}
		
		@Override
		public Product getMoney() {
			return money;
		}
	};
	
	@Test
	public void testCreation() {
		Buyer underTest = givenBuyerWithTestMarket();
		assertTrue("Buyers should not start with needs!", underTest.getNeeds() == null);
	}

	private Buyer givenBuyerWithTestMarket() {
		Buyer underTest = new BasicBuyer(testMarket);
		return underTest;
	}
	
	@Test
	public void testSetNeeds() {
		Buyer underTest = givenBuyerWithTestMarket();
		//List<Need> needs
		//underTest.setNeeds(needs);
	}

}
