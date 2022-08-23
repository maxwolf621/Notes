###### tags: `Design Pattern`
# Iterator

The Iterator Pattern provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

> Iterator-Muster  
![](https://i.imgur.com/murQQP1.png)  
![image](https://user-images.githubusercontent.com/68631186/126357563-ae3639d8-19ba-434d-b515-5372f6b50341.png)  

## Example   
To merge two menus   
![](https://i.imgur.com/WwWcYtK.png)  

```java
/**
  *<p> Merge Menus Without Iterator pattern </p>
  *<p> If we have two menus as the following  </p>
  *	<li> DinerMenu </li>
  *	<li> PancakeHouseMenu </li>
  *<p> Two menus have different structures </p>
  *<p> PancakeHouseMenu stores its items via {@code ArrayList<MenuItem>} </p>
  *<p> DinerMenu stores its items via {@code MenuItem[]} </p>
  */
public class MenuItem{
    String name;
    String description;
    boolean vegetarian;
    double price;
    
    public MenuItem(String name, String description,
                    boolean vegetarian, double price){
                        /* initialize ... */
    }
    
    // other methods ...
}

public class PancakeHouseMenu{
    ArrayList<MenuItem> menuItems;
    
    public PancakeHouse(){
        menuItems = new ArrayList<MenuItem>();
        addItem(/* name, description, price */);
        //....
    }
    
    /**
      * @Description
      *    Add the item in the menu
      */
    public void addItem(String name, String description,
                boolean vegetarian , double price)
                {
                    MenuItem menuItem = new MenuItem(/*..*/);
                    menItems.add(menuItem);
                }
}

public class DinerMenu{
    static final int MAX = 6;
    
    int items = 0;
    MenuItem[] menuItems;
    
    public DinerMenu(){
    	menuItems = new MenuItem[MAX];
	
	addItem(/*...*/);
	addItem(/*...*/);
	//.. add rest 
    }
    
    public void addItem(String name, String description,
                boolean vegetarian, double price){
                //...
    }
    public MenuItem[] getMenuItems(){
        return menuItems;
    }
    //...
}
```
- Both menus use different way to contain their information(e.g. description, name ... etc). 
    > One uses ArrayList and the other uses Array.

Now A waitress handle two menus sorta like this
```java
public class waitress{
    PancakeHouseMenu pancakeHouseMenu = new PancakeHOuseMenu();
    ArrayList<MenuItem> breakfastItems = pancakeHouseMenu.getMenuItems();
    
    DinerMenu dinerMenu = new DinerMenu();
    MenuItem[] LunchItems = dinerMenu.getMenuItems();
    
    //For PancakeHouseMenu
    for(int i = 0 ; i < breakfastItems.size() ; ++i)
    {
        MenuItem menuItem = breakfastItems.get(i);
	//...
    }
    
    //For dinerMenu 
    for(int i = 0; i < lunchItems.length ; ++i){
        MenuItem menuItem = lunchItem[i];
	//...
    }
}
```
- Here comes to the problem.
    > The Waitress need to have two different loop to iterate over the two different kinds of menus.  
    > Also if we added a third menu, we'd have yet another loop to iterate.  
    > **This definitely gives us (the Waitress) hard to maintain and extend (Against Design Pattern Rule)**

To solve the above problems we can use `iterator pattern`
> Iterator  
> provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.  
> ![image](https://user-images.githubusercontent.com/68631186/126334313-5ebfecb9-4e95-4dcf-a7de-151dc5b5ceca.png)

To have less coupled (open closed principle) we can change the UML like this
![image](https://user-images.githubusercontent.com/68631186/126357357-ce94e05b-9072-4fb8-93aa-0fd896663341.png)


[SourceCode](https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/iterator/dinermerger)

```java
public interface Iterator{
    boolean hasNext()
    MenuItem next()
}

public class ArrayListIterator implements Iterator{
	ArrayList<MenuItem> items;
	int position = 0;

	public ArrayListIterator(ArrayList<MenuItem> items){
		this.items = items;
	}
	
	/**
	  * @Description
	  *	Go next element 
	  */
	public MenuItem next(){
		return items.get(position++);
	}
	
	public boolean hasNext(){
		return items.size() > position;
	}
}
public class ArrayIterator implements Iterator{
	MenuItem[] items;
    	int position = 0;
	
	public ArrayIterator(MenuItem[] items) {
		this.items = items;
	}
	public MenuItem next() {
		return items.get(position++);
	}

	public boolean hasNext() {
		/*
		if (position >= items.length || items[position] == null) {
			return false;
		} else {
			return true;
		}
		*/
	
		return items.length > position;
	}
}

/**
  * <p> Menu that creats Iterator method</p>
  */
public interface Menu{
	Iterator createIterator();
}

/**
  * <p> PancakeHouseMenu and DinerMenu implement 
  *	Menu interface {@code createIterator()}</p>
  */
public class PancakeHouseMenu implements Menu{
	//..
	
	public Iterator createIterator(){
		return new PancakeIterator(menuItems);
	}
}
public class DinerMenu implements Menu{
	//..
	
	public Iterator createIterator() {
		return new DinerMenuIterator(menuItems);
	}
}

/**
  * <p> Iterators for Certain Menu
  * 	For example iterator for 
  * 	the structure of menu is array (like DinerMenuIterator) </p>
  */

// convert the array type to iterator
public class DinerMenuIterator implements Iterator {
	MenuItem[] items;
	int position = 0;
 
	public DinerMenuIterator(MenuItem[] items) {
		this.items = items;
	}
 
	public MenuItem next() {
		/*
		MenuItem menuItem = items[position];
		position = position + 1;
		return menuItem;
		*/
		
		// or shorten to 
		return items[position++];
	}
 
	public boolean hasNext() {
		/*
		if (position >= items.length || items[position] == null) {
			return false;
		} else {
			return true;
		}
		*/
		
		// or shorten to
		return items.length > position;
	}
}

// covert ArrayList to iterator
public class PancakeHouseMenuIterator implements Iterator {
	List<MenuItem> items;
	int position = 0;
 
	public PancakeHouseMenuIterator(List<MenuItem> items) {
		this.items = items;
	}
 
	public MenuItem next() {
		/* 
		MenuItem item = items.get(position);
		position = position + 1;
		return item;
		*/
		// or shorten to:
		return items.get(position++);
	}
 
	public boolean hasNext() {
		/*
		if (position >= items.size()) {
			return false;
		} else {
			return true;
		}
		*/
		// or shorten to:
		return items.size() > position;
	}
}
```
-  `MenuItem` Class : Methods to set up each meal's name,description and price
-  `Menu` Interface : Create an Iterator method (so the menus can be used as Iterator)
-  `Iterator` Interface : (adapter pattern) Different structure menus can use the same methods(e.g `next()`, `hasNext()` ... etc)


Now Waitress can use methods 
```java
public class Waitress{
       /**
	 * <p> We can use Menu instead of concreteMenus. </p>
	 *  <strong> Always programming to an interface,
	 *  Not an implementation for Less Decouple </strong>
	 */
	Menu pancakeHouseMenu; // PancakeHouseMenu pancakeHouseMenu <-- sucks	
	Menu dinerMenu;        // DinerMenu dinerMenu <-- sucks 
    
	public Waitress(Menu pancakeHouseMenu, Menu dinerMenu) {
		this.pancakeHouseMenu = pancakeHouseMenu;
		this.dinerMenu = dinerMenu;
	}
 
	public void printMenu() {
		Iterator pancakeIterator = pancakeHouseMenu.createIterator();
		Iterator dinerIterator = dinerMenu.createIterator();

		System.out.println("MENU\n----\nBREAKFAST");
		printMenu(pancakeIterator);
		System.out.println("\nLUNCH");
		printMenu(dinerIterator);

	}
 
	private void printMenu(Iterator iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			System.out.print(menuItem.getName() + ", ");
			System.out.print(menuItem.getPrice() + " -- ");
			System.out.println(menuItem.getDescription());
		}
	}
 
	public void printVegetarianMenu() {
		printVegetarianMenu(pancakeHouseMenu.createIterator());
		printVegetarianMenu(dinerMenu.createIterator());
	}
 
	public boolean isItemVegetarian(String name) {
		Iterator breakfastIterator = pancakeHouseMenu.createIterator();
		if (isVegetarian(name, breakfastIterator)) {
			return true;
		}
		Iterator dinnerIterator = dinerMenu.createIterator();
		if (isVegetarian(name, dinnerIterator)) {
			return true;
		}
		return false;
	}


	private void printVegetarianMenu(Iterator iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			if (menuItem.isVegetarian()) {
				System.out.print(menuItem.getName());
				System.out.println("\t\t" + menuItem.getPrice());
				System.out.println("\t" + menuItem.getDescription());
			}
		}
	}

	private boolean isVegetarian(String name, Iterator iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			if (menuItem.getName().equals(name)) {
				if (menuItem.isVegetarian()) {
					return true;
				}
			}
		}
		return false;
	}
}


/**
  * <p> Test </p>
  */
public class MenuTestDrive {
	public static void main(String args[]) {
		Menu pancakeHouseMenu = new PancakeHouseMenu();
		Menu dinerMenu = new DinerMenu();

			Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu);

			// With iterators
			waitress.printMenu();

			printMenus();
	}
}
```

## Using `java.util.Iterator` package to resize our Menus

[SourceCode](https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/iterator/dinermergeri)  

```java
/**
  * <p> Iterator from java package </p>
  */
public interface Iterator{
    boolean hasNext()
    MenuItem next()
    void remove() // the remove element method 
}

/**
  * we add {@code remove()} method in our DinerMenuIterator
  */
public class DinerMenuIterator implements Iterator{
	MenuItem[] list ;
	int position = 0;
	//..
    
        /**
	  * @Description
	  * 	remove the first element and shift each element forward
	  * @throws IllegalStateException
	  */
	public void remove() {
		if (position <= 0) {
			throw new IllegalStateException
				("You can't remove an item until you've done at least one next");
		}
		if (list[position-1] != null) {
			for (int i = position-1; i < (list.length-1); i++) {
				list[i] = list[i+1];
			}
			list[list.length-1] = null;
		}
	}
}


/**
  * <p> update the Waitress </p>
  */
import java.util.Iterator;
import java.util.ArrayList;
 
public class Waitress {
	Menu pancakeHouseMenu;
	Menu dinerMenu;
 
	public Waitress(Menu pancakeHouseMenu, Menu dinerMenu) {
		this.pancakeHouseMenu = pancakeHouseMenu;
		this.dinerMenu = dinerMenu;
	}
	
	public void printMenu(int withNewConstructs) {
		ArrayList<MenuItem> breakfastItems = ((PancakeHouseMenu) pancakeHouseMenu).getMenuItems();
		
		//pMenu.forEach(m -> printMenuItem(m));
		
		for (MenuItem m : breakfastItems) {
			printMenuItem(m);
		}
		
		MenuItem[] lunchItems = ((DinerMenu) dinerMenu).getMenuItems();
		for (MenuItem m : lunchItems) {
			printMenuItem(m);
		}
	}
	
	public void printMenuItem(MenuItem menuItem) {
		System.out.print(menuItem.getName() + ", ");
		System.out.print(menuItem.getPrice() + " -- ");
		System.out.println(menuItem.getDescription());
	}

 
	public void printMenu() {
		Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
		Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();

		System.out.println("MENU\n----\nBREAKFAST");
		printMenu(pancakeIterator);
		System.out.println("\nLUNCH");
		printMenu(dinerIterator);
	}
 
	private void printMenu(Iterator<MenuItem> iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			System.out.print(menuItem.getName() + ", ");
			System.out.print(menuItem.getPrice() + " -- ");
			System.out.println(menuItem.getDescription());
		}
	}
 
	public void printVegetarianMenu() {
		System.out.println("\nVEGETARIAN MENU\n----\nBREAKFAST");
		printVegetarianMenu(pancakeHouseMenu.createIterator());
		System.out.println("\nLUNCH");
		printVegetarianMenu(dinerMenu.createIterator());
	}
 
	public boolean isItemVegetarian(String name) {
		Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
		if (isVegetarian(name, pancakeIterator)) {
			return true;
		}
		Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
		if (isVegetarian(name, dinerIterator)) {
			return true;
		}
		return false;
	}


	private void printVegetarianMenu(Iterator<MenuItem> iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			if (menuItem.isVegetarian()) {
				System.out.print(menuItem.getName());
				System.out.println("\t\t" + menuItem.getPrice());
				System.out.println("\t" + menuItem.getDescription());
			}
		}
	}

	private boolean isVegetarian(String name, Iterator<MenuItem> iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			if (menuItem.getName().equals(name)) {
				if (menuItem.isVegetarian()) {
					return true;
				}
			}
		}
		return false;
	}
}

/**
  * <p> Test </p>
  */
public class MenuTestDrive {
	public static void main(String args[]) {
		PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
		DinerMenu dinerMenu = new DinerMenu();
		Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu);
		//waitress.printMenu();
		// -- added 12/30/2016
		waitress.printMenu(1);
		// ---
		//waitress.printVegetarianMenu();

		System.out.println("\nCustomer asks, is the Hotdog vegetarian?");
		System.out.print("Waitress says: ");
		if (waitress.isItemVegetarian("Hotdog")) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		System.out.println("\nCustomer asks, are the Waffles vegetarian?");
		System.out.print("Waitress says: ");
		if (waitress.isItemVegetarian("Waffles")) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}

	}
}
```


By applying iterator pattern we violate open closed principle in `Waitress` class
```java
public class Waitress{
    Menu pancakeHouseMenu;
    Menu dinerMenu;
    Menu dessert;
    
    //..
    
    public void printMenu(){
        /**
          * <p> The following declaration violates the open closed principle </p>
	  * <p> Once we get a new menu we must add 
	  *     a new {@code Iterator<MenuItem>} again. 
	  *     for example to add a desertMenu <p>
	  */
        Iterator<MenuItem> pancakeIter = pancakeHouseMenu.createIterator();
        Iterator<MenuItem> dinerIter = dinerMenu.createIterator();
	
	Iterator<MenuItem> dessert = dessertMenu.createIterator(); // <---- add new Menu
        
	// ...
    }
    //..
}
```
- For such problem we can use [composite pattern](S_Composite.md)
