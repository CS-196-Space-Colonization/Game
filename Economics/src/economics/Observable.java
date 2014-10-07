package economics;

public interface Observable {
	void acceptObserver(Observer other);
	void removeObserver(Observer other);
	void alertObservers();
}
