package economics;

import economics.products.Quantity;

public interface Transaction extends Observable {
	Quantity getOffer();
	Quantity getPrice();
	Quantity getRevenue();
	void execute(double amt);
	double getMarginalPrice();
}
