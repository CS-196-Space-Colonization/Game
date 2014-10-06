package economics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class Market {
	public final ImmutableMap<String, Good> GOOD_PROTOTYPES;
	private Map<Good, LinkedList<Transaction>> market;
	
	public Market(ImmutableMap<String, Good> goods) {
		GOOD_PROTOTYPES = goods;
		market = new HashMap<Good, LinkedList<Transaction>>();
		refreshOffers();
	}
	
	private void refreshOffers() {
		ImmutableSet<String> keys = GOOD_PROTOTYPES.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			Good forSale = GOOD_PROTOTYPES.get(i.next());
			market.put(forSale, new LinkedList<Transaction>());
		}
	}
	
	public List<Transaction> getOffers(Good Gooded) {
		return market.get(Gooded);
	}

	public void addAdvertisement(Transaction transaction) {
		market.get(transaction.getOffer()).add(transaction);
	}
}
