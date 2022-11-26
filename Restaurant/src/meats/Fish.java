package meats;

public class Fish implements Meat {
	@Override
	public double cost() {
		return 2.00;
	}
	@Override
	public String getDescription() {
		return "Fish ";
	}
}
