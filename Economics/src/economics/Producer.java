package economics;

import economics.products.Product;

public interface Producer {
	void runProductionStep();
	void performMaintenance();
	Product productionGood();
}
