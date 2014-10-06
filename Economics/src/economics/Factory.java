package economics;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Factory implements Producer {
	private static final double baseHealthLoss = .03;
	private Stock production;
	private Map<Good, Stock> maintenanceGoods;
	private double health;
	private double efficiency;

	public Factory(Stock production, Map<Good, Stock> maintenanceGoods) {
		this.production = production;
		this.maintenanceGoods = maintenanceGoods;
		health = 1.0;
		efficiency = 1.0;
	}

	@Override
	public void step(Map<Good, Stock> stock) {
		double produced = production.getQuantity();
		Set<Good> keys = production.getGood().inputGoods.keySet();
		for (Iterator<Good> i = keys.iterator(); i.hasNext();) {
			Good good = i.next();
			double needed = production.getGood().inputGoods.get(good) * production.getQuantity();
			double available = stock.get(good).getQuantity();
			double actual = Math.max(needed, available);
			produced = Math.min(produced, actual/needed);
			stock.get(good).changeQuantity(-needed / efficiency);
		}
		produced *= health;
		stock.get(production.getGood()).changeQuantity(produced);
	}

	@Override
	public void performMaintenance(Map<Good, Stock> stock) {
		Set<Good> keys = maintenanceGoods.keySet();
		double repairModifier = health < .99 ? 1.0 : 2.0;
		for (Iterator<Good> i = keys.iterator(); i.hasNext();) {
			Good good = i.next();
			double needed = maintenanceGoods.get(good).getQuantity();
			double available = stock.get(good).getQuantity();
			double actual = Math.max(needed*repairModifier, available);
			double healthLoss = calculateHealthLoss(actual, needed);
			health -= healthLoss;
			stock.get(good).changeQuantity(-actual);
		}
		
	}

	private double calculateHealthLoss(double available, double needed) {
		return baseHealthLoss * (1-available/needed);
	}

	@Override
	public Stock getDailyProduction() {
		return new Stock(production.getGood(), production.getQuantity() * health * efficiency);
	}

}
