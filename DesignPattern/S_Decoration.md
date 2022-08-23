###### tags : `Design Pattern`
# Decoration
[SoruceCode](https://fjp.at/design-patterns/decorator)  
[Example from illuwater](https://github.com/iluwatar/java-design-patterns/tree/master/decorator)

The Decorator Pattern provides an alternative to subclassing for extending behavior.  
**Decorator pattern lets you dynamically change the behavior of an object at run time by wrapping them in an object of a decorator class**   

> Pattern   
>![image](https://user-images.githubusercontent.com/68631186/126654796-c53ab694-fec0-412c-8f7f-87a436b3f169.png)  

```java
/**
  * <p> abstract component </p>
  * <p> this abstraction is used (<strong> dependency </strong>)
  *     by concreteComponent and abstraction for decoration </p>
  */
public abstract class Beverage {
	
	// Beverage size
	public enum Size { TALL, GRANDE, VENTI };
	// Default Size
	Size size = Size.TALL;
	
	String description = "Unknown Beverage";

	public String getDescription() {
		return description;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Size getSize() {
		return this.size;
	}

	public abstract double cost();
}

/**
  * <p> The concrete components </p>
  */
public class Espresso extends Beverage {

	public Espresso() {
		description = "Espresso";
	}

	public double cost() {
		return 1.99;
	}
}

public class DarkRoast extends Beverage {
	public DarkRoast() {
		description = "Dark Roast Coffee";
	}

	public double cost() {
		return .99;
	}
}

/**
  * <p> To extend the behavior of these concrete components  
  *     abstraction(Decorator) inherits the Beverage base class </p>
  */
public abstract class CondimentDecorator extends Beverage {
	
	/**
	* <p> To decorate the concrete component </p>
	* <strong> follow DI principle </strong>
	*/
	public Beverage beverage;

	public abstract String getDescription();

	public Size getSize() {
		return beverage.getSize();
	}
}
 
 

/*****************************************************************
  *                      DECORATOR                               *
  * Using ConcreteDecorator to wrap the concrete components      *
  * which gives them new behaviors.	                         *
  ***************************************************************/
  
/**
  * <p> Decorate the beverage with 
  *     <li> Whip </li>  
  *	<li> Soy  </li>
  *	<li> Mocha<li> 
  * </p>
  */
public class Whip extends CondimentDecorator {
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}


	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	public double cost() {
		return beverage.cost() + .10;
	}
}

public class Soy extends CondimentDecorator {
	public Soy(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Soy";
	}

	public double cost() {
		double cost = beverage.cost();
		if (beverage.getSize() == Size.TALL) {
			cost += .10;
		} else if (beverage.getSize() == Size.GRANDE) {
			cost += .15;
		} else if (beverage.getSize() == Size.VENTI) {
			cost += .20;
		}
		return cost;
	}
}

public class Mocha extends CondimentDecorator {
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Mocha";
	}

	public double cost() {
		return beverage.cost() + .20;
	}
}

/**
  * <p> Client </p>
  */
public class StarbuzzCoffee {
	public static void main(String args[]) {

		Beverage beverage = new Espresso();
		System.out.println(beverage.getDescription()
				+ " $" + String.format("%.2f", beverage.cost()));

	       /**
		 * <p> Using decoration pattern </p>
		 * <p> We can change(decorate) 
		 *     object beverage2 dinamically with </p> 
		 * <li> Mocha </li>
		 * <li> Whip </li>
		 */
		Beverage beverage2 = new DarkRoast();
		beverage2 = new Mocha(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.getDescription()
				+ " $" + String.format("%.2f", beverage2.cost()));

	       /**
		* <p> Decorate the beverage3 with </p>
		* <li> Soy </li>
		* <li> Mocha </li>
		* <li> Whip </li>
		*/
		Beverage beverage3 = new HouseBlend();
		beverage3.setSize(Size.VENTI);
		beverage3 = new Soy(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Whip(beverage3);
		System.out.println(beverage3.getDescription()
				+ " $" + String.format("%.2f", beverage3.cost()));
		}
}
