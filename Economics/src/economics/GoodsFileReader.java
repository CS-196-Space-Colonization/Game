package economics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

public class GoodsFileReader {
	private File goodsFile;
	private ImmutableMap<String, Good> finalGoodsList;
	
	public GoodsFileReader(File goodsFile) {
		this.goodsFile = goodsFile;
		makeGoodsList();
	}
	
	private void makeGoodsList() {
		int lineNumber = 0;
		Map<String, Good> internalGoodsList = new HashMap<String, Good>();
		try (Scanner configuration = new Scanner(goodsFile)) {
			while (configuration.hasNext()) {
				lineNumber++;
				addGoodFromLine(internalGoodsList, configuration.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.err.printf("Resource data file %s not found!\n", goodsFile.toString());
			e.printStackTrace();
			System.err.println(e);
		} catch (NumberFormatException e) {
			System.err.printf("Malformed number on line %d of %s", lineNumber, goodsFile.toString());
			e.printStackTrace();
			System.err.println(e);
		}
		finalGoodsList = ImmutableMap.copyOf(internalGoodsList);
		internalGoodsList = null;
	}

	private void addGoodFromLine(Map<String, Good> internalGoodsList, String line) {
		String[] lineComponents = line.toLowerCase().split(",");
		String name = lineComponents[0].trim();
		double initialPrice = Double.parseDouble(lineComponents[1].trim());
		Map<Good, Double> inputsMap = parseInputGoods(lineComponents, internalGoodsList);
		Set<Good> keys = inputsMap.keySet();
		List<Good> inputsList = new ArrayList<Good>(keys.size());
		for (Iterator<Good> i = keys.iterator(); i.hasNext(); ) {
			Good key = i.next();
			Double qty = inputsMap.get(key);
			inputsList.add(new Good(key, qty));
		}
		internalGoodsList.put(name, Good.makePrototype(initialPrice, inputsList));
	}

	private  Map<Good, Double> parseInputGoods(String[] lineComponents, Map<String, Good> goodsSoFar) {
		Map<Good, Double> result = new HashMap<Good, Double>(lineComponents.length / 2 - 1);
		for (int i = 2; i < lineComponents.length; i += 2) {
			String inputGoodName = lineComponents[i].toLowerCase().trim();
			Good inputGood = goodsSoFar.get(inputGoodName);
			if (inputGood == null) {
				System.err.println("Attempt to define finished good before raw good! (Did you switch the order?");
				throw new NullPointerException();
			}
			double inputGoodQuantity = Double.parseDouble(lineComponents[i+1].trim());
			result.put(inputGood, inputGoodQuantity);
		}
		return result;
	}
	
	public ImmutableMap<String, Good> getGoodsList() {
		return finalGoodsList;
	}
}