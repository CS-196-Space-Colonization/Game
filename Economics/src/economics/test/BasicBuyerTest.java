package economics.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import economics.*;
import economics.need.BasicNeed;
import economics.need.Need;
import economics.need.NeedBranch;
import economics.products.Product;
import economics.products.Quantity;

public class BasicBuyerTest {
	private Market testMarket = new Market() {
		@Override
		public List<Transaction> getOffers(Product needed) {
			ArrayList<Transaction> transactions = new ArrayList<>();
			transactions.add(new GoodsTransaction(new Quantity(needed, 100.0), 
												  new Quantity(ProductsService.get("money"), 100.0)));
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
			return ProductsService.get("money");
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
		inventoryToExchange.addQuantityOfProduct(ProductsService.get("money"), 10000000.0);
		underTest.give(inventoryToExchange);
		Need needs = makeNeeds();
		underTest.setNeeds(needs);
		underTest.buyGoods();
		Inventory got = underTest.take();
		assertTrue("Buyer failed to buy needs!", needs.portionFulfilled(got) > 1);
	}

	private Need makeNeeds() {
		Need rootNeed = new NeedBranch();
		Need metalNeed = new NeedBranch();
		rootNeed.insert(metalNeed, 0);
		metalNeed.insert(new BasicNeed(new Quantity(ProductsService.get("iron"), 1.0)), 0);
		metalNeed.insert(new BasicNeed(new Quantity(ProductsService.get("steel"), 10.0)), 1);
		rootNeed.insert(new BasicNeed(new Quantity(ProductsService.get("labor"), 100.0)), 1);
		return rootNeed;
	}

}
