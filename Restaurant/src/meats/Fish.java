package meats;

public class Fish implements Meat {

	@Override
	public void showMeat() {
		System.out.println("Fish");
		
	}

	@Override
	public void cookMeat() {
		System.out.println("This fish is to be roasted");
	}

}
