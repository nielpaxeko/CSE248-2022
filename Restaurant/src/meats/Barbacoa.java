package meats;

public class Barbacoa implements Meat{
	@Override
	public double cost() {
		return 1.00;
	}
	@Override
	public String getDescription() {
		return "Barbacoa ";
	}
}
