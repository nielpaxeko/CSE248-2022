package meats;

public class Steak implements Meat {
	@Override
	public double cost() {
		return 1.00;
	}
	@Override
	public String getDescription() {
		return "Steak ";
	}
}
