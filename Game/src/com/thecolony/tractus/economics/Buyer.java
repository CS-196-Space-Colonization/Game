package com.thecolony.tractus.economics;

import com.thecolony.tractus.economics.need.Need;

public interface Buyer extends EconomicAgent {
	Need getNeeds();
	void setNeeds(Need needs);
	void buyGoods();
	double getSpendingMoney();
}
