package menu;

import meats.Meat;

public class Burrito extends Dish {
	private Meat meat;
	public Burrito(Meat meat) {
		this.meat=meat;
	}
	@Override
	public String getDescription() {
		return meat.getDescription() + "Burrito ";
	}
	@Override
	public double cost() {
		return meat.cost()+9.99;
	}
}
