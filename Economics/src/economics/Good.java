package economics;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
 
public class Good {
	public static final ImmutableMap<String, Good> GOODS = (new GoodsCreator(Configuration.GOODS_FILE)).getGoodsList();
	public final double initialPrice;
	public final ImmutableMap<Good, Double> inputGoods;

	public Good(double initialPrice, Map<Good, Double> inputs) {
		this.initialPrice = initialPrice;
		this.inputGoods = ImmutableMap.copyOf(inputs);
	}
}
