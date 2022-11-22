package meats;

public class Beef implements Meat {

	@Override
	public void showMeat() {
		System.out.println("Beef");	
	}

	@Override
	public void cookMeat() {
		System.out.println("Beef is to be roasted");
		
	}

}
