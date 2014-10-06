package economics;

public interface Transaction {
	Double getOffer();
	Double getPrice();
	double getRemaining();
	void execute(double amt);
}
