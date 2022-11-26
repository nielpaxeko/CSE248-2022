package menu;

import meats.Meat;

public class Bowl extends Dish  {
	private Meat meat;
	public Bowl(Meat meat) {
		this.meat=meat;
	}
	@Override
	public String getDescription() {
		return meat.getDescription() + "Bowl ";
	}
	@Override
	public double cost() {
		return meat.cost()+9.99;
	}
}
