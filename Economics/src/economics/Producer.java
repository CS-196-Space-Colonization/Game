package economics;

import java.util.Map;

public interface Producer {
	void step(Map<Good, Stock> stock);
	Stock getDailyProduction();
	void performMaintenance(Map<Good, Stock> stock);
}
