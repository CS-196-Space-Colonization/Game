package economics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableMap;

public class GoodsFileReader {
	private File goodsFile;
	private ImmutableMap<String, Product> finalGoodsList;
	
	public GoodsFileReader(File goodsFile) {
		this.goodsFile = goodsFile;
		makeGoodsList();
	}
	
	private void makeGoodsList() {
		int lineDouble = 0;
		Map<String, Product> internalGoodsList = new HashMap<String, Product>();
		try (Scanner configuration = new Scanner(goodsFile)) {
			while (configuration.hasNext()) {
				lineDouble++;
				addGoodFromLine(internalGoodsList, configuration.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.err.printf("Resource data file %s not found!\n", goodsFile.toString());
			e.printStackTrace();
			System.err.println(e);
		} catch (NumberFormatException e) {
			System.err.printf("Malformed Double on line %d of %s", lineDouble, goodsFile.toString());
			e.printStackTrace();
			System.err.println(e);
		}
		finalGoodsList = ImmutableMap.copyOf(internalGoodsList);
		internalGoodsList = null;
	}

	private void addGoodFromLine(Map<String, Product> internalGoodsList, String line) {
		String[] lineComponents = line.toLowerCase().split(",");
		String name = lineComponents[0].trim();
		double initialPrice = Double.parseDouble(lineComponents[1].trim());
		Map<Product, Quantity> inputsMap = parseInputGoods(lineComponents, internalGoodsList);
		internalGoodsList.put(name, Product.makePrototype(initialPrice, ImmutableMap.copyOf(inputsMap)));
	}

	private  Map<Product, Quantity> parseInputGoods(String[] lineComponents, Map<String, Product> goodsSoFar) {
		Map<Product, Quantity> result = new HashMap<>(lineComponents.length / 2 - 1);
		for (int i = 2; i < lineComponents.length; i += 2) {
			String inputGoodName = lineComponents[i].toLowerCase().trim();
			Product inputGood = goodsSoFar.get(inputGoodName);
			if (inputGood == null) {
				System.err.println("Attempt to define finished good before raw good! (Did you switch the order?");
				throw new NullPointerException();
			}
			double inputGoodQuantity = Double.parseDouble(lineComponents[i+1].trim());
			result.put(inputGood, new Quantity(inputGood, inputGoodQuantity));
		}
		return result;
	}
	
	public ImmutableMap<String, Product> getGoodsList() {
		return finalGoodsList;
	}
}