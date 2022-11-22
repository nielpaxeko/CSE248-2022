package menu;


public class Grill implements CookingBehavior {
	Dish dish;
	public Grill(Dish dish) {
		this.dish = dish;
	}
	
	@Override
	public String cook() {
		return "Grilled " + dish.getDescription();
	}

}
