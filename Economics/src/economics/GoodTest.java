package economics;

import static org.junit.Assert.*;
import static economics.Market.GOODS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.ImmutableMap;

public class GoodTest {
	@Test
	public void testCreation() {
		List<Need> inputs = new ArrayList<Need>();
		Good test = new Good(1.0, inputs);
		assert(test.initialPrice == 1.0);
		assert(test.inputNeeds.equals(inputs));
	}
	
	@Test
	public void testListLabor() {
		Good labor = GOODS.get("labor");
		assert(labor.initialPrice == 1.0);
		assert(labor.inputNeeds.equals(new HashMap<Good, Double>()));
	}
	
	@Test
	public void testListWidgets() {
		Good labor = GOODS.get("widgets");
		assert(labor.initialPrice == 2.0);
		HashMap<Good, Double> map = new HashMap<Good, Double>();
		map.put(GOODS.get("labor"), .25);
		assert(labor.inputNeeds.equals(map));
	}
}
