package menu;

public class Bowl extends Dish  {
	private String meat;
	
	
	public Bowl(String meat) {
		this.meat=meat;
	}
	
	@Override
	public String getDescription() {
		return getMeat() + " bowl";
	}

	public String getMeat() {
		return meat;
	}

	public void setMeat(String meat) {
		this.meat = meat;
	}

}
