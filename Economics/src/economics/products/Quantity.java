package economics.products;


public final class Quantity implements Comparable<Quantity> {
	public static final Quantity NullQuantity = new Quantity(new Unit(){}, 0.0);
	private final Unit unit;
	private final Double quantity;
	
	public Quantity(Unit unit, Double quantity) {
		this.unit = unit;
		this.quantity = quantity;
	}
	
	public Quantity add(Unit unit, Double quantity) {
		assertUnitMatches(unit);
		return new Quantity(unit, this.quantity + quantity);
	}
	
	public Quantity add(Quantity other) {
		return add(other.unit, other.quantity);
	}
	
	public Quantity subtract(Unit unit, Double quantity) {
		return add(unit, -quantity);
	}
	
	public Quantity subtract(Quantity other) {
		return subtract(other.unit, other.quantity);
	}
	
	private void assertUnitMatches(Unit unit) {
		if (unit != this.unit) {
			throw new IllegalArgumentException("Unit mismatch between " + unit.toString() + " and " + this.unit.toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Quantity))
			return false;
		Quantity RHS = (Quantity)other;
		
		return RHS.unit.equals(this.unit) && RHS.quantity.compareTo(quantity) == 0;
	}

	@Override
	public int compareTo(Quantity RHS) {
		assertUnitMatches(RHS.unit);
		double thisQuantity = this.quantity;
		double otherQuantity = RHS.quantity;
		if (thisQuantity > otherQuantity)
			return 1;
		else if (thisQuantity < otherQuantity)
			return -1;
		else
			return 0;
	}

	public Unit getUnit() {
		return unit;
	}

	public Double getQuantity() {
		return quantity;
	}
}
