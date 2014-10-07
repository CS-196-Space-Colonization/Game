package economics;

import java.util.Comparator;

public class BasicDealScorer implements Comparator<Transaction> {

	@Override
	public int compare(Transaction LHS, Transaction RHS) {
		return LHS.getPrice().compareTo(RHS.getPrice());
	}
}
