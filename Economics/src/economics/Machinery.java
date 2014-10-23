package economics;

import economics.need.Need;

public class Machinery {
	private static final double maxHealthIncrease = .03;
	private static final double maxHealthDecrease = .05;
	private Need maintenanceNeeds;
	private double health;
	private double efficiency;
	private double throughput;

	public Need getMaintenanceNeeds() {
		return maintenanceNeeds;
	}

	public void setMaintenanceNeeds(Need maintenanceNeeds) {
		this.maintenanceNeeds = maintenanceNeeds;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public double getThroughput() {
		return throughput;
	}

	public void setThroughput(double throughput) {
		this.throughput = throughput;
	}
	
	public double getMaximumDailyProduction() {
		return throughput * health;
	}
	
	public double getInputModifier() {
		return getMaximumDailyProduction() / efficiency;
	}

	public void performMaintenance(Inventory inventory) {
		double portionFulfilled = getMaintenanceNeeds().portionFulfilled(inventory);
		setHealth(Math.min(1.0, Math.max(0.0, calculateHealthChange(portionFulfilled))));
	}
	
	private double calculateHealthChange(double portionFulfilled) {
		double healthChange = -maxHealthDecrease * (1 - portionFulfilled);
		if (healthChange > maxHealthIncrease)
			healthChange = maxHealthIncrease;
		
		return healthChange;
	}
}