package menu;

import meats.Meat;

public class Enchiladas extends Dish {
	private Meat meat;
	public Enchiladas(Meat meat) {
		this.meat=meat;
	}
	@Override
	public String getDescription() {
		return meat.getDescription() + "Enchilafas ";
	}
	@Override
	public double cost() {
		return meat.cost()+6.99;
	}
}
