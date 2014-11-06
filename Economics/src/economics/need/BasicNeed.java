package economics.need;

import economics.Inventory;
import economics.products.*;

public class BasicNeed implements Need {
	private Quantity need;
	
	public BasicNeed(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Inventory has) {
		return has.getAmountOf((Product)need.getUnit()) / need.getQuantity();
	}

	@Override
	public Inventory getNeededProducts() {
		Inventory result = new Inventory();
		result.addQuantityOfProduct((Product)need.getUnit(), need.getQuantity());
		return result;
	}
}