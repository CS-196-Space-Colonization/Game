package economics;

public interface Seller {
	void postAdvertisement(Market market);
	double acceptTransaction(Need need);
}