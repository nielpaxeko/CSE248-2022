package menu;

import java.lang.reflect.InvocationTargetException;

import meats.Chicken;
import meats.Meat;

public class Factory {
	public Dish createDish(String dishType, String meatType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			Class chosenDish = Class.forName("menu." + dishType);
			Class chosenMeat = Class.forName("meats." + meatType);
			Meat meat = (Meat) chosenMeat.getDeclaredConstructor().newInstance();
			Dish dish = (Dish) chosenDish.getConstructor(new Class[] {String.class}).newInstance(meat);
			return dish;
	}
}
