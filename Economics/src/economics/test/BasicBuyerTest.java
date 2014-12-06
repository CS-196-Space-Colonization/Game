package economics.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import economics.*;
import economics.need.*;
import economics.products.*;

public class BasicBuyerTest {
	private Market testMarket = new Market() {
		@Override
		public List<Transaction> getOffers(Product needed) {
			ArrayList<Transaction> transactions = new ArrayList<>();
			transactions.add(new GoodsTransaction(new Quantity(needed, 100.0), 
												  new Quantity(new Money(), 100.0)));
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
			return new Money();
		}
	};
	
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
