package meats;

public class Shrimp implements Meat {
	@Override
	public double cost() {
		return 3.00;
	}
	@Override
	public String getDescription() {
		return "Shrimp ";
	}
}
