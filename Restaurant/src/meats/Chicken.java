package meats;

public class Chicken implements Meat {

	@Override
	public void showMeat() {
		System.out.println("Chicken");
	}

	@Override
	public void cookMeat() {
		System.out.println("Chicken is to be grilled");
	}

}
