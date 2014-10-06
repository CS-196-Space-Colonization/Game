package economics;

import java.util.Map;

public interface Need {
	double portionFulfilled(Map<String, Good> stock);
}
