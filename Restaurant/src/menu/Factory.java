package menu;

import java.lang.reflect.InvocationTargetException;

public class Factory {
	public Dish createDish(String dishType, String meatType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			System.out.println(dishType);
			Class clazz = Class.forName("menu." + dishType);
			Dish dish = (Dish)clazz.getConstructor(new Class[] {String.class}).newInstance(meatType);
			
			return dish;
	}
}
