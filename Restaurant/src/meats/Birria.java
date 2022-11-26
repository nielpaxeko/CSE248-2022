package meats;

public class Birria implements Meat{
	@Override
	public double cost() {
		return 1.50;
	}
	@Override
	public String getDescription() {
		return "Birria";
	}
}
