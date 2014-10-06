package economics;

import static org.junit.Assert.*;
import static economics.Market.GOOD_PROTOTYPES;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class FirmTest extends TestCase {
	
	private EconomicAgent givenDefaultFirm() {
		EconomicAgent firmUnderTest = new EconomicAgent(GOODS.get("widgets"), 10.0);
		return firmUnderTest;
	}
	
	@Test
	public void testInitiallyPoor() {
		EconomicAgent firmUnderTest = givenDefaultFirm();
		assert(firmUnderTest.getMoney() == 0.0);
		assert(firmUnderTest.getDouble() == 0.0);
	}

	
	@Test
	public void testStep() {
		EconomicAgent firmUnderTest = givenDefaultFirm();
		firmUnderTest.step();
		assert(firmUnderTest.getDouble() == firmUnderTest.getDailyProduction());
	}
	
	@Test
	public void testBuy() {
	}
}
