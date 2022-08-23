###### tags : `Design Pattern`
# Composite

[sourceCode](https://fjp.at/design-patterns/composite)  
[anotherExample](https://dotblogs.com.tw/pin0513/2011/01/16/20838)

> Coomposite-Muster  
> ![image](https://user-images.githubusercontent.com/68631186/126869687-0695af60-0da0-47b4-bba7-5e62701460ae.png)  
> **The Composite Pattern allows us to build structures of objects in the form of trees that contain both compositions of objects and individual objects as nodes.**  


![image](https://user-images.githubusercontent.com/68631186/126360058-040544ea-5d83-4431-8616-ad68f4eb3028.png)  

## Example
A scenario for the menu inserting a new menu under it  

We can create a sub-menu(named dessert Menus) under dinermenu with tree-structure as the following  
![image](https://user-images.githubusercontent.com/68631186/126869754-34ff74cb-d3ad-4911-9ecd-a4337eb0d8ed.png)  

#### The goal we want is the following  
> 1. Print Whole Tree Structure   
> ![image](https://user-images.githubusercontent.com/68631186/126358373-a6853e2d-93a1-40e5-88c1-c9737ce8b575.png)  
> 2. Print Part of Tree Structure  
> ![image](https://user-images.githubusercontent.com/68631186/126869790-2953d81b-9551-4ba7-9942-04fc9ac2a78d.png)

- Using a composite structure, we can apply the same operations over both composites and individual objects.  
	> In other words, in most cases we can ignore the differences between compositions of objects and individual objects.(e.g. print whole structure or just individual ones.)  

### The role of the MenuComponent abstract class 
![image](https://user-images.githubusercontent.com/68631186/126869880-522f32b6-7a3b-490c-a0bd-dd01d301d99e.png)
![image](https://user-images.githubusercontent.com/68631186/126869886-2ce70f80-97d1-442c-a1f3-efc228ee35b4.png)

It provides an interface for the leaf nodes and the composite nodes.  
- **It should provide a default implementation of the methods** so that if the MenuItem (the leaf) or the Menu(the composite component) doesn't want to implement some of the methods.**(node and leaf extends same class but have different behaviour)**  
![](https://i.imgur.com/T4ENYIX.png)  

Tree Structure  
![](https://i.imgur.com/PhWCSlw.png)    

```java
/**
  * <p> Define our default implementation of the methods 
  *     In case the leaf/the component does not implement 
  *     the method from base</p>
  */
public abstract class MenuComponent {

	public void add(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	public void remove(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	public MenuComponent getChild(int i) {
		throw new UnsupportedOperationException();
	}
  
	public String getName() {
		throw new UnsupportedOperationException();
	}
	public String getDescription() {
		throw new UnsupportedOperationException();
	}
	public double getPrice() {
		throw new UnsupportedOperationException();
	}
	public boolean isVegetarian() {
		throw new UnsupportedOperationException();
	}
	public void print() {
		throw new UnsupportedOperationException();
	}
}

/**
  * <p> The Leaf will implement </p>
  * <li>{@code getName} </li>
  * <li>{@code getDescription} </li>
  * <li>{@code getPrice} </li>
  * <li>{@code isVegetarian} </li>
  * <li>{@code print} </li>
  */
public class MenuItem extends MenuComponent{
  String name;
  String description;
  boolean vegetarian;
  double price;

  public MenuItem(String name, 
                  String description, 
                  boolean vegetarian, 
                  double price) 
  { 
    this.name = name;
    this.description = description;
    this.vegetarian = vegetarian;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }

  public boolean isVegetarian() {
    return vegetarian;
  }

  /**
    * @Description
    *   Print this.item on a certain menu
    *   <li>name</li>
    *   <li>price</li>
    *   <li>description</li>
    */
  public void print() {
    System.out.print("  " + getName());
    if (isVegetarian()) {
      System.out.print("(v)");
    }
    System.out.println(", " + getPrice());
    System.out.println("     -- " + getDescription());
  }
}


/**
  * <p> Composite Components </p>
  * <p> Each menu(composit component) 
  *     has different items stored in 
  *     different types of collections 
  * 	  (aggregates such as ArrayList, standard array or HashMap). </p>
  * <p> It needs to implement the following methods </p>
  * <li> {@code add} </li>
  * <li> {@code remove} </li>
  * <li> {@code getChild} </li>
  * <li> {@code getName} </li>
  * <li> {@code getDescription} </li>
  * <li> {@code print} </li>
  */
import java.util.Iterator;
import java.util.ArrayList;
public class Menu extends MenuComponent {
  ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
  String name;
  String description;

  public Menu(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void add(MenuComponent menuComponent) {
    menuComponents.add(menuComponent);
  }

  public void remove(MenuComponent menuComponent) {
    menuComponents.remove(menuComponent);
  }

  public MenuComponent getChild(int i) {
    return (MenuComponent)menuComponents.get(i);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  /**
  * @Description
  *   Print this Menu's properties
  *   <li> name <li>
  *   <li> description <li>
  *   and iterate each item in this menu
  */
  public void print() {
    System.out.print("\n" + getName());
    System.out.println(", " + getDescription());
    System.out.println("---------------------");

    /**
     * <p> Iterate of an Array List via {@code iterator()} </p>
     */ 
    Iterator<MenuComponent> iterator = menuComponents.iterator();
    while (iterator.hasNext()) {
      MenuComponent menuComponent = (MenuComponent) iterator.next();
      menuComponent.print();
    }
  }
}

/**
  * <p>Client of composite pattern 
  *    sticks to open closed principle </p>
  */
public class Waitress{
    /**
      * <p> All menus contains 
      *     in the Tree Structure </p>
      */
    MenuComponent allMenus;
    
    public Waitress(MenuComponent allMenus){
        this.allMenus = allMenu;
    }
    
    public void printMenu()
    {
        /**
         * Compare with 
	 * @See <a href="https://github.com/maxwolf621/DesignPattern/blob/main/B_Iterator.md">Iterator pattern</a>
         * for print menu
         */
        allMenus.print();
    }
}

/**
  * <p> Test </p>
  */
public class MenuTestDrive{
public static void main(String args[]) {
    
    /**
      * Create Menus 
      * <li> PACAKE HOUSE MENU </li>
      * <li> DINER MENU </li>
      * <li> CAFE MENU </li>
      * <li> DESSERT MENU </lI>
      */
    MenuComponent pancakeHouseMenu = 
        new Menu("PANCAKE HOUSE MENU", "Breakfast");
    MenuComponent dinerMenu = 
        new Menu("DINER MENU", "Lunch");
    MenuComponent cafeMenu = 
        new Menu("CAFE MENU", "Dinner");
    MenuComponent dessertMenu = 
        new Menu("DESSERT MENU", "Dessert of course!");
    MenuComponent coffeeMenu = new Menu("COFFEE MENU", "Stuff to go with your afternoon coffee");

    /**
      * Put all the menus in allMenus
      */
    MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");
    allMenus.add(pancakeHouseMenu);
    allMenus.add(dinerMenu);
    allMenus.add(cafeMenu);
    
    /**
      * Create a menu under a menu
      */
    pancakeHouseMenu.add(new MenuItem(
            "K&B's Pancake Breakfast", 
            "Pancakes with scrambled eggs, and toast", 
            true,
            2.99));
            
    /**
      * Ask Waitress show the menus
      */
    Waitress waitress = new Waitress(allMenus);
    waitress.printMenu();
            
}
```
- We use an internal iterator inside the Menu class to recursively to print the tree structure (of menus), because of that we actually have less control to iterate over components


## Choose OSR or Transparency for Composite pattern?
[Detail](https://ithelp.ithome.com.tw/articles/10242929)

**The Composite Pattern takes the Single Responsibility design principle and trades it for transparency.**  

### One Single Responsibility
一個類別只能有一個改變的原因。你做你該做的事，我做我該做的事。你我互不干擾

#### Composite Pattern that uses OSR  
![image](https://user-images.githubusercontent.com/68631186/126422735-b5c4efc5-d836-4320-8971-003ae86c9143.png)  

For example  
Although MenuItem and MenuComponent both extend the same abstract class but  
- `MenuItem` is leaf ( it doesn't implement add/remove method)  
- `MenuComponent` is Composite Objects (it implements add/remove method)  
 
### Transparency
**By allowing the Component interface to contain the child management operations and the leaf operations**.  

#### Composite Pattern that uses Transparency
![image](https://user-images.githubusercontent.com/68631186/126422678-b8a4975f-3f28-4c05-b26b-d97f32b535c1.png)

**A client can treat both composites(MenuCoponent) and leaf nodes(MenusItem) uniformly**;  so whether an element is a composite node or leaf becomes transparent to the client(client have no further details about them).  

**Given we have both types of operations in the Component class, we lose a bit of safety because a client might try to do something inappropriate or meaningless on an element (e.g. leaf can call `remove` method).**  

### Descision 

It is a design decision; we could take the design in the other direction and separate out the responsibilities into interfaces.  
This would make our design safe, in the sense that any inappropriate calls on elements would be caught at compile time or run-time, but we’d lose transparency and our code would have to use conditionals and the `instanceof` operator.  
