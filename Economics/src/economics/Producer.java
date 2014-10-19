package economics;

import economics.need.Need;
import economics.products.Product;

public interface Producer {
	void runProductionStep();
	Product getProductionGood();
	Need getInputNeeds();
	void calculateInputNeeds();
}
