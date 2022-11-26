package meats;

public class Chicken implements Meat {
	public Chicken() {
		
	}
	@Override
	public double cost() {
		return .50;
	}
	@Override
	public String getDescription() {
		return "Chicken ";
	}
}
