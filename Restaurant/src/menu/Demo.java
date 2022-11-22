package menu;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Demo {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the dish: ");
		String dishType = sc.nextLine();
		System.out.println("Enter the meat: ");
		String meatType = sc.nextLine();
		Factory factory = new Factory();
		Dish dish = factory.createDish(dishType, meatType);
		dish.getDescription();
		CookingBehavior behavior = new Grill(dish);
		dish.setCookingBehavior(behavior);
		System.out.print(dish.cookDish());
		
		
	}

}
