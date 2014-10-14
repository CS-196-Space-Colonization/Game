package economics.products;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableMap;

import economics.Quantity;

public class GoodsFileReader {
	private File goodsFile;
	private Map<String, Product> goodsList;
	
	public GoodsFileReader(File goodsFile) {
		this.goodsFile = goodsFile;
		this.goodsList = new HashMap<>();
		makeGoodsList();
	}
	
	private void makeGoodsList() {
		int lineNumber = 0;
		try (Scanner configuration = new Scanner(goodsFile)) {
			while (configuration.hasNextLine()) {
				lineNumber++;
				String line = configuration.nextLine();
				addLineToProducts(line);
			}
		} catch (FileNotFoundException e) {
			System.err.printf("Resource data file %s not found!\n", goodsFile.toString());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.printf("Malformed Double on line %d of %s", lineNumber, goodsFile.toString());
			e.printStackTrace();
		}
	}

	private void addLineToProducts(String line) {
		String[] lineComponents = splitLine(line);
		Product good = getProductData(lineComponents);
		goodsList.put(good.getType(), good);
	}

	private Product getProductData(String[] lineComponents) {
		String name = getName(lineComponents);
		double initialPrice = getInitialPrice(lineComponents);
		Map<Product, Quantity> inputProducts = getInputGoods(lineComponents);
		Product good = new Product(name, initialPrice, inputProducts);
		return good;
	}

	private String[] splitLine(String line) {
		return line.toLowerCase().split(",");
	}
	
	private String getName(String[] lineComponents) {
		return lineComponents[0].trim();
	}
	
	private double getInitialPrice(String[] lineComponents) {
		double initialPrice = Double.parseDouble(lineComponents[1].trim());
		return initialPrice;
	}

	private  Map<Product, Quantity> getInputGoods(String[] lineComponents) {
		Map<Product, Quantity> result = new HashMap<>(lineComponents.length / 2 - 1);
		for (int i = 2; i < lineComponents.length; i += 2) {
			String inputGoodName = lineComponents[i].toLowerCase().trim();
			Product inputGood = goodsList.get(inputGoodName);
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
		return ImmutableMap.copyOf(goodsList);
	}
}