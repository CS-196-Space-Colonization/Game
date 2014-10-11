package economics;

public class Need {
	private Quantity need;
	
	public Need(Quantity need) {
		this.need = need;
	}
	
	public double portionFulfilled(Quantity has) {
		return has.getQuantity() / need.getQuantity();
	}

	public Quantity getNeed() {
		return need;
	}
}