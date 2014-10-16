package economics;

import economics.products.Quantity;

public interface Transaction {
	Quantity getOffer();
	Quantity getPrice();
	Quantity getRevenue();
	void execute(double amt);
	double getMarginalPrice();
}
