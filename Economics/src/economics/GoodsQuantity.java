package economics;

import java.util.Map;

public class GoodsQuantity implements Need {
	private final Good item;
	private final double quantity;
	
	public GoodsQuantity(Good item, double qty) {
		this.item = item;
		this.quantity = qty;
	}
	
	public Good getGood() {
		return item;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	@Override
	public double portionFulfilled(Map<Good, Double> stock) {
		Double qty = stock.get(item);
		qty = qty == null ? 0 : qty;
		return qty / quantity;
	}
}
