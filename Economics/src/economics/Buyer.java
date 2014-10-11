package economics;

public interface Buyer extends EconomicAgent {
	NeedTreeNode getNeeds();
	void buyGoods();
	void setNeeds(NeedTreeNode needs);
}
