package economics;

import economics.need.Need;

public interface Buyer extends EconomicAgent {
	Need getNeeds();
	void setNeeds(Need needs);
	void buyGoods();
}
