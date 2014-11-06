package economics.products;

import java.util.Collections;

public class Carbon extends Product {

	public Carbon() {
		super("Carbon", 0.1, Collections.<Product, Quantity> emptyMap());
	}
	
}
