package meats;

public class Beef implements Meat {
	@Override
	public double cost() {
		return .50;
	}
	@Override
	public String getDescription() {
		return "Beef ";
	}
}
