package meats;

public class Pork implements Meat {

	@Override
	public void showMeat() {
		System.out.println("Pork");
	}

	@Override
	public void cookMeat() {
		System.out.println("Pork is to be smoked");
	}

}
