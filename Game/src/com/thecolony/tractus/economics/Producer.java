package com.thecolony.tractus.economics;

import com.thecolony.tractus.economics.need.Need;
import com.thecolony.tractus.economics.products.Product;


public interface Producer {
	void runProductionStep();
	Product getProductionGood();
	Need getInputNeeds();
	void calculateInputNeeds();
}
