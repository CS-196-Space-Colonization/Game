package economics;

public interface Transaction {
	Stock getOffer();
	Stock getPrice();
	double getRemaining();
	void execute(double amt);
}
