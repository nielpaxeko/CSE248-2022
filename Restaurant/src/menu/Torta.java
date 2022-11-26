package menu;

public class Torta extends Dish {
	private String meat;
	public Torta(String meat) {
		this.meat=meat;
	}
	
	@Override
	public String getDescription() {
		return getMeat() + " torta";
	}

	public String getMeat() {
		return meat;
	}

	public void setMeat(String meat) {
		this.meat = meat;
	}

	@Override
	public double cost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
