package economics;

import java.util.List;

public interface Buyer extends EconomicAgent {
	List<Need> getNeeds();
	void setNeeds(List<Need> needs);
	void buyGoods();
}
