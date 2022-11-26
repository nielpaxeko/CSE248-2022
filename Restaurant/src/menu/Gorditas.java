package menu;

import meats.Meat;

public class Gorditas extends Dish {
	private Meat meat;
	public Gorditas(Meat meat) {
		this.meat=meat;
	}
	@Override
	public String getDescription() {
		return meat.getDescription() + "Burrito ";
	}
	@Override
	public double cost() {
		return meat.cost()+11.99;
	}
}
