package economics;

import static org.junit.Assert.*;
import static economics.Good.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class FirmTest extends TestCase {
	
	private Firm givenDefaultFirm() {
		Firm firmUnderTest = new Firm(GOODS.get("widgets"), 10.0);
		return firmUnderTest;
	}
	
	@Test
	public void testInitiallyPoor() {
		Firm firmUnderTest = givenDefaultFirm();
		assert(firmUnderTest.getMoney() == 0.0);
		assert(firmUnderTest.getStock() == 0.0);
	}

	
	@Test
	public void testStep() {
		Firm firmUnderTest = givenDefaultFirm();
		firmUnderTest.step();
		assert(firmUnderTest.getStock() == firmUnderTest.getDailyProduction());
	}
	
	@Test
	public void testBuy() {
		Firm seller = givenDefaultFirm();
		seller.step();
		Firm buyer = givenDefaultFirm();
		buyer.pay(seller, 10.0);
		seller.transferTo(buyer, "Widgets", seller.getStock()); 
	}
}
