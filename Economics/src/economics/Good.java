package economics;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
 
public class Good {
	public final double initialPrice;
	public final ImmutableMap<Good, Double> inputGoods;

	private Good(double initialPrice, Map<Good, Double> inputs) {
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
	}
	
	public static Good makePrototype(double initialPrice, ImmutableMap<Good, Double> inputs) {
		return new Good(initialPrice, inputs);
	}
}
