package economics;

public interface Producer {
	void runProductionStep();
	void performMaintenance();
	Product productionGood();
}
