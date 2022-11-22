package menu;


public abstract class Dish {
	private String meat;
	private CookingBehavior behavior;
	
	public void setCookingBehavior(CookingBehavior behavior) {
		this.behavior=behavior;
	}
	public String cookDish() {
		return behavior.cook();
	}
	public abstract String getDescription();
	
	
}
