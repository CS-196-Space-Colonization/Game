package economics;

public class Stock {
	private final Good good;
	private double quantity;
	
	public Stock(Good good, double qty) {
		this.good = good;
	}
	
	public Stock(Stock stock) {
		this.good = stock.good;
		this.quantity = stock.quantity;
	}
	
	public Good getGood() {
		return good;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public double changeQuantity(double delta) {
		double oldQuantity = quantity;
		quantity = Math.max(quantity + delta, 0.0);
		return oldQuantity - quantity;
	}
}
