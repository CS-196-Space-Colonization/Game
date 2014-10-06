package economics;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Factory implements Producer {
	private static final double baseHealthLoss = .03;
	private Good production;
	private Map<Good, Double> maintenanceGoods;
	private double health;
	private double efficiency;
	private double throughput;

	public Factory(Good productionGood, double productionQuantity) {
		this.production = productionGood;
		this.throughput = productionQuantity;
		this.maintenanceGoods = getMaintenanceGoods(productionGood);
		health = 1.0;
		efficiency = 1.0;
	}

	private Map<Good, Double> getMaintenanceGoods(Good productionGood) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void performMaintenance(Map<Good, Double> inventory) {
		Set<Good> keys = maintenanceGoods.keySet();
		double repairModifier = health < .99 ? 1.0 : 2.0;
		for (Iterator<Good> i = keys.iterator(); i.hasNext();) {
			Good good = i.next();
			double needed = maintenanceGoods.get(good);
			double available = inventory.get(good);
			double actual = Math.max(needed*repairModifier, available);
			double healthLoss = calculateHealthLoss(actual, needed);
			health -= healthLoss;
			inventory.put(good, new Double(inventory.get(good) - actual));
		}
		
	}

	private double calculateHealthLoss(double available, double needed) {
		return baseHealthLoss * (1-available/needed);
	}

	@Override
	public void runProductionStep(Map<Good, Double> inventory) {
		double produced = throughput;
		Set<Good> keys = production.inputGoods.keySet();
		for (Iterator<Good> i = keys.iterator(); i.hasNext();) {
			Good good = i.next();
			double needed = production.inputGoods.get(good) * throughput;
			double available = inventory.get(good);
			double actual = Math.max(needed, available);
			produced = Math.min(produced, actual/needed);
			inventory.put(good, new Double(inventory.get(good) - needed / efficiency));
		}
		produced *= health;
		inventory.put(production, new Double(inventory.get(production) + produced));
	}

	@Override
	public Good productionGood() {
		return production;
	}

}
