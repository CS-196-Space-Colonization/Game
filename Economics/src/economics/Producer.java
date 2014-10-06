package economics;

public interface Producer {
	void step();
	double getDailyProductionQuantity();
	Good getProductionGood();
}
