package economics;

public interface Transaction {
	GoodsQuantity getOffer();
	GoodsQuantity getPrice();
	double getRemaining();
	void execute(double amt);
}
