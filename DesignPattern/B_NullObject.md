###### tags : `Design Pattern`
# Null Object
[Reference](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E7%A9%BA%E5%AF%B9%E8%B1%A1.md)  
[Another Example](https://github.com/iluwatar/java-design-patterns/tree/master/null-object)  

> Pattern UML
> ![image](https://user-images.githubusercontent.com/68631186/126873687-19af1fb5-a0ca-4b87-bf90-f72b6ed6ec94.png)

## Example

[Creat Null Iterator for example in Composite pattern](S_Composite.md)
![image](https://user-images.githubusercontent.com/68631186/126871297-5a899afb-ea63-4435-825b-faf6dc7a701a.png)

To have more control to iterate all the items of menus (e.g. Iterate items which are vegetarian)   
we can add a new method `createIterator()` in the `MenuComponent` to have an iterator
```java
public interface MenuComponent{
    
    // other methods ...
    
    Iterator<MenuComponent> createIterator()
}


public class Menu extends MenuComponent {
	Iterator<MenuComponent> iterator = null;
	ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
	
	//...
       
	public Iterator<MenuComponent> createIterator() {
		if (iterator == null) {
			/**
			  * <p> Push the the menuComponent to the stack </p>
			  */
			iterator = new CompositeIterator(menuComponents.iterator());
		}
		return iterator;
	}
}

/**
  * <p> A menuItem is an element(the leaf) </p>
  * <p> An element can't be iterated </p>
  */
public class MenuItem extends MenuComponent{
    
    //....
    
    /**
      * @Description
      *     In case a MenuItem object uses {@code createIterator}
      * @return Null Iterator
      */
    public Iterator<MenuComponent> createrIterator(){
        return new NullIterator()
    }
}
```

### null iterator implementation

Because `MenuItem` has nothing to iterate over, so we got two choices to solve such problem  
1. Return `null` from `createIterator()`
2. Return an iterator that always return false whe `hasNext()` is called
```java
public class NullIterator implements Iterator<MenuComponent>{

	public Object next(){
		return null;
	}
	public boolean hasNext(){
		return false;
	}
	
  /**
   * No longer needed as of Java 8
   *
   * (non-Javadoc)
   * @see java.util.Iterator#remove()
	public void remove() {
		throw new UnsupportedOperationException();
	}
	*/
}
```

### Composite(Components) Iterator

Composite Iterator got the job of iterating over the MenuItems in the Component and of making sure all the child Menus(grandson Menus, and so on) are included
```java
public class CompositeIterator implements Iterator{
	Stack<Iterator<MenuComponent>> stack = new Stack<Iterator<MenuComponent>>();
   
	public CompositeIterator(Iterator<MenuComponent> iterator) {
		stack.push(iterator);
	}
   
	public MenuComponent next() {
		if (hasNext()) {
			Iterator<MenuComponent> iterator = stack.peek();
			MenuComponent component = iterator.next();
			stack.push(component.createIterator());
			return component;
		} else {
			return null;
		}
	}
  
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		} else {
			Iterator<MenuComponent> iterator = stack.peek();
			if (!iterator.hasNext()) {
				stack.pop();
				return hasNext();
			} else {
				return true;
			}
		}
	}
}
```

Now the waitress can use the composite iterator to show which items are vegetarian  
```java
/**
  * <p> Client </p>
  */ 
public class Waitress{
	
	//...
	
	public void printVegetarianMenu() {
		Iterator<MenuComponent> iterator = allMenus.createIterator();

		System.out.println("\nVEGETARIAN MENU\n----");
		while (iterator.hasNext()) {
			MenuComponent menuComponent = iterator.next();
			try {
				if (menuComponent.isVegetarian()) {
					menuComponent.print();
				}
			} catch (UnsupportedOperationException e) {}
		}
	}
}


public class MenuTestDrive {
  public static void main(String args[]) {

    /**
      * Create the Menu Components
      */
    MenuComponent pancakeHouseMenu = 
      new Menu("PANCAKE HOUSE MENU", "Breakfast");
    MenuComponent dinerMenu = 
      new Menu("DINER MENU", "Lunch");
    MenuComponent cafeMenu = 
      new Menu("CAFE MENU", "Dinner");
    MenuComponent dessertMenu = 
      new Menu("DESSERT MENU", "Dessert of course!");

    /**
      * Merge the menu togetehr
      */
    MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");
    allMenus.add(pancakeHouseMenu);
    allMenus.add(dinerMenu);
    allMenus.add(cafeMenu);

    /**
      * <strong> add the items in the menus </strong>
      */
    pancakeHouseMenu.add(new MenuItem(
      "K&B's Pancake Breakfast", 
      "Pancakes with scrambled eggs and toast", 
      true,
      2.99));

    //...

    dinerMenu.add(new MenuItem(
      "Vegetarian BLT",
      "(Fakin') Bacon with lettuce & tomato on whole wheat", 
      true, 
      2.99));

    //....

    /**
      * <strong> Add the MenuComponent under a MenuComponent </strong>
      */
    dinerMenu.add(dessertMenu);

    dessertMenu.add(new MenuItem(
      "Apple Pie",
      "Apple pie with a flakey crust, topped with vanilla icecream",
      true,
      1.59));
    //...

    cafeMenu.add(new MenuItem(
      "Veggie Burger and Air Fries",
      "Veggie burger on a whole wheat bun, lettuce, tomato, and fries",
      true, 
      3.99));
    //...

    /**
      * Ask The Watiress for menu
      */
    Waitress waitress = new Waitress(allMenus);  

    waitress.printVegetarianMenu();  // ask the waitress for Vegetarian Menu

  }
}
```
