package economics;

import java.util.Map;

public interface Need {
	double portionFulfilled(Map<Good, Double> stock);
}
