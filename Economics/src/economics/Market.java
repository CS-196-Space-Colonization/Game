package economics;

import java.util.List;
import economics.products.Product;

public interface Market {
	List<Transaction> getOffers(Product needed);
	void addOffer(Transaction transaction);
	double getLastPrice(Product product);
}