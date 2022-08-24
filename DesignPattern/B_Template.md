###### Tags : `Design Pattern`
# Template Pattern
### A pattern without Template Method

For example, we can keep the common part for the base class via Template Method
![image](https://user-images.githubusercontent.com/68631186/125339097-92ea9480-e383-11eb-9d84-c4a599f01186.png)
  
## Template Design UML
The Template Method defines the steps of an algorithm and allows subclasses to provide the implementation for one or more steps to prevent from duplicate codes
![](https://i.imgur.com/qhYbfmb.png)


```java
abstract class AbstractClass {
      /**
       * @description
       *   It's declared `final` to prevent 
       *   <strong> subclasses from reworking 
       *            the sequence of steps in the algorithm </strong>
       *   As we can see 
       *   <p> The Template means something is defined 
       *       by default and derived classes </p>
       */
      final void templateMethod()
      {
        /**
          * <p> these two will be implemented </p>
          */
        primitiveOperation2();
        primitiveOperation1();
        
        /**
          * <p> for the common parts of subclasses </P>
          */
        concreteOperation();   
      }

     /**
      * @description
      *   The Followings abstracts 
      *   <strong> Must be implemented by concrete subclasses </strong>
      */
     abstract void primitiveOperation1();
     abstract void primitiveOperation2();

     /** 
       * @description
       * You can also define a concrete operations
       *     defined in the abstract class
       * <p> It may be 
       *     used by in the template method or 
       *     used by by subclasses </p>
       */
     void concreteOperation()
     {
         //implementation here
     }


     /** 
       * @description
       *    A concrete method that do nothing 
       *    or something like 
       *    the sub-classes can override this method 
       *    but doesn't have to
       *   <strong> A hook is a method that is declared in the abstract class, 
       *            but only given an empty or default implementation. </strong>
       *   This gives sub-classes the ability to "hook into" the
       *   algorithm at various points
       */
     void hook()
     {
         //...
     }
}
```

## Example for Template Method Using with the hook

```java
public abstract class CaffeineBeverage{
  final void prepareRecipe() {
    boilWater();
    pourInCup();
    brew(); // implemented by subclasses
    /** 
      * <p> using hook makes code more flexible <p>
      */
    if (customerWantsCondiments()) {
            // implemented by subclasses
            addCondiments();  
    }
  }
  /**
    * <p> Abstracts </p>
    */
  abstract void brew();
  abstract void addCondiments();
  
  /* 
   * <p> Concrete operation </p> 
   */
  void boilWater() {
    System.out.println("Boiling water");
  }
  void pourInCup() {
    System.out.println("Pouring into cup");
  }
  
  /**
    * @description
    *     <p> A hook </p> 
    *     <p> By Default we always add Condiments for Customer </p>
    */
  boolean customerWantsCondiments() {
    return true;
  }
}

public class Coffee extends CaffeineBeverage {
   
    /*
     * @description
     *      it implements abstract functions from the base
     * @return Dripping Coffee through filter
     */
    public void brew() {
        System.out.println("Dripping Coffee through filter");
    }
    
    /*
     * @description
     *      it implements abstract functions from the base
     * @return Adding Sugar and Milk
     */
    public void addCondiments() {
        System.out.println("Adding Sugar and Milk");
    }
    
    /**
      * @description
      *   It implements the hook.
      *   To see wether customer wants Condiments
      * @return {@code true} if "y" is giving in
      */
    public boolean customerWantsCondiments() {
        String answer = getUserInput();
        if (answer.toLowerCase().startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
      * @description
      *     user-input for providing Custom Condiments
      *     using {@code BufferedReader(new InputStreamReader(System.in))}
      */
    private String getUserInput() {
        String answer = null;
        System.out.print("Would you like milk and sugar with your coffee (y/n)?");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = in.readLine();
        } catch (IOException ioe) {
            System.err.println("IO error trying to read your answer");
        }
        if (answer == null) {
            return "no"; 
        }
        return answer;
    }
}

/** 
 * @apinote 
 *   sub-class Tea that extends caffeineBeverage 
 */
public class Tea extends CaffeineBeverage
{
    /*
     * @description
     *      it implements abstract functions from the base
     * @return Print Steeping the tea
     */
    public void brew() {
        System.out.println("Steeping the tea");
    }
    /*
     * @description
     *      it implements abstract functions from the base
     * @return Print Adding Lemon
     */
    public void addCondiments() {
        System.out.println("Adding Lemon");
    }
    
    //....
}


/** 
  * <p> Implementation of Template Method </p>
  * <p> Making Coffee and Tea </p>
  */
public class BeverageTestDrive {
      public static void main(String[] args) {
          Coffee coffee = new Coffee();
          Tea tea = new Tea();
          
          System.out.println("\nMaking Coffee...");
          coffee.prepareRecipe();
          
          System.out.println("\nMaking Tea...");
          tea.prepareRecipe();
       }
}
```

## Compare the Difference 

![image](https://user-images.githubusercontent.com/68631186/125339873-5b301c80-e384-11eb-9485-915c20de594e.png)


## Hollywood Principle 
The Hollywood principle gives us a way to prevent dependency rot.   
It happens **when you have high-level components depending on low-level components depending on high-level components** depending on sideways components depending on low-level components, and so on.  

With the Hollywood Principle, we allow low-level components to hook themselves into a system, but the high-level components determine when they are needed.    
In other words, the high-level components give the low-level components a treatment of `don't call us, we'll call you`     
![](https://i.imgur.com/IKZGoPi.png)     


## Template Method and Hollywood Principle

1. **Class `CaffeineBeverage` has control over the algorithm for the recipe and calls on the sub-classes only when they are needed**
2. Class `Tee` and Class `Coffee` call the abstract directly without being called first

![](https://i.imgur.com/duKNSFW.png)
