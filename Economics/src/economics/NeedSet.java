package economics;

import java.util.LinkedList;
import java.util.List;

import economics.products.Product;

public class NeedSet {
	private List<Need> needSet;
	
	public NeedSet (List<Need> needList) {
		needSet = new LinkedList<>();
		needSet.addAll(needList);
	}
	
	public void addNeed(Need need) {
		needSet.add(need);
	}
	
	public void removeNeed(Need need) {
		needSet.remove(need);
	}
	
	public double portionFulfilled(Inventory inventory) {
		double fulfilled = Double.MAX_VALUE;
		for (Need need : needSet) {
			Quantity productNeeded = need.getNeed();
			fulfilled = Math.min(fulfilled, inventory.getQuantityOf((Product)productNeeded.getUnit()) / productNeeded.getQuantity());
		}
		return fulfilled;
	}
}
