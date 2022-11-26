package menu;

import meats.Meat;

public class Quesadilla extends Dish {
	private Meat meat;
	public Quesadilla(Meat meat) {
		this.meat=meat;
	}
	@Override
	public String getDescription() {
		return meat.getDescription() + "Quesadilla ";
	}
	@Override
	public double cost() {
		return meat.cost()+7.99;
	}
}
