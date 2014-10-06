package economics;

import java.util.Map;

public interface Producer {
	void runProductionStep(Map<Good, Double> inventory);
	void performMaintenance(Map<Good, Double> inventory);
	Good productionGood();
}
