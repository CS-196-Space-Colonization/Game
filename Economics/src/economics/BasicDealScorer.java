package economics;

import java.util.Comparator;

public class BasicDealScorer implements Comparator<Transaction> {

	@Override
	public int compare(Transaction LHS, Transaction RHS) {
		int result = Double.compare(LHS.getMarginalPrice(),  RHS.getMarginalPrice());
		if (result == 0)
			result = -Double.compare(LHS.getOffer().getQuantity(),  RHS.getOffer().getQuantity());
			//Negative because we want to prefer higher quantity.
		return result;
	}
}
