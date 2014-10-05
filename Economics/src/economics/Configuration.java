package economics;

import java.io.File;

public interface Configuration {
	File RESOURCES = new File("resources");
	File GOODS_FILE = new File(RESOURCES, "goods.txt");
}
