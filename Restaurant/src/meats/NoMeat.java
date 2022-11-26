package meats;

public class NoMeat implements Meat {
	@Override
	public double cost() {
		return .00;
	}
	@Override
	public String getDescription() {
		return " ";
	}
}
