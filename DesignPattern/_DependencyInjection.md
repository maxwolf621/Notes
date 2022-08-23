###### tags: `Design Pattern`
# Dependency Injection
[TOC]

[From StackOverflow](https://stackoverflow.com/questions/3334578/what-is-dependency-injection)  
[From WIKI](https://en.wikipedia.org/wiki/Dependency_injection)  

## Example without dependency Injection

```java
public class Client {
    /**
     * <p> Internal reference to the service 
     *     used by this client </p>
     */ 
    private ExampleService service;

    /**
     * <p>  Specify a specific implementation 
     *      in the constructor instead of 
     *      using dependency injection </p>
     */
    Client() {
        /**
          * <strong> Hard code </strong>
          */
        service = new ExampleService();
    }

    /**
     * @Description
     *      use {@code service.getName}
     */
    public String greet() {
        return "Hello " + service.getName();
    }
}
```

## Types of dependency injection
There are at least three ways a client object can receive a reference to an external module

### Constructor Injection 
The dependencies are provided through a client's class constructor.  

```java
class Car{
  private Wheel wh = new NepaliRubberWheel();
  private Battery bt = new ExcideBattery();

  //The rest
}

/**
 *  <p> With DI </p>
 */ 
class Car{
  /**
   * <p> Inject an Instance of Wheel (dependency of car) at runtime </p>
   * <p> Inject an Instance of Battery (dependency of car) at runtime </p>
   */
  private Wheel wh;  
  private Battery bt;
  
  Car(Wheel wh,Battery bt) {
      this.wh = wh;
      this.bt = bt;
  }

  //The rest 
}
```

### Setter Injection
The client exposes a setter method that the injector uses to inject the dependency.

```java
public Car{

  private Wheel wh; 
  private Battery bt; 
  
  /**
    * <p> Constructor Injection </p>
    */
  Car(Wheel wh,Battery bt) {
      this.wh = wh;
      this.bt = bt;
  }

  /**
   * @Description
   *   The Setter Injection
   */
  void setWheel(Wheel wh) {
      this.wh = wh;
  }
  void setBattery(Battery bt){
      this.bt = bt;
  }
}
```


### Interface Injection

The dependency's interface provides an injector method that will inject the dependency into any client passed to it.  

Clients must implement an interface that **exposes a setter method that accepts the dependency**.
    
```java
/**
 * <p> without DI </p> 
 */
public class Car
{
    public Car()
    {
        /**
         * <p> HARD CODE </p>
         * <p> Car is dependent on {@code GasEngine} only </p>
         */
        GasEngine engine = new GasEngine();
        engine.Start();
    }
}

public class GasEngine
{
    public void Start()
    {
        Console.WriteLine("I use gas as my fuel!");
    }
}

/**------------------------------------**/

/**
 * <p> Using Interface Dependency </p>
 */ 
 public interface IEngine
{
    void Start();
}

public class GasEngine implements IEngine
{
    public void Start()
    {
        Console.WriteLine("I use gas as my fuel!");
    }
}

public class ElectricityEngine implements IEngine
{
    public void Start()
    {
        Console.WriteLine("I am electrocar");
    }
}

public class Car
{
    /**
     * <strong> Car class is dependent on only the IEngine interface, 
     *          not a specific implementation of engine </strong>
     * <p> It means the parameter that is passed in <pre> new Car </pre> 
     *     can be {@code GasEngine} or {@code ElectricityEngine}   </p>
     * 
     * For example 
     * <pre> 
     *  Car gasCar = new Car(new GasEngine());
     *  gasCar.Run();
     *  Car electroCar = new Car(new ElectricityEngine());
     *  electroCar.Run();
     * </pre>
     * 
     * <p> Such Dependency injection 
     *     is just a technique for achieving loose coupling </p>
     */
    private readonly IEngine _engine;
    
    public Car(IEngine engine)
    {
        _engine = engine;
    }

    public void Run()
    {
        _engine.Start();
    }
}
```

## The Advantages of DI

- Decoupling the creation of object (in other word, separate usage from the creation of object)
- Ability to replace dependencies (eg: Wheel, Battery) without changing the class that uses it(Car)
- Promotes **Code to interface not to implementation** principle
- Ability to create and use mock dependency during test (if we want to use a Mock of Wheel during test instead of a real instance.. we can create Mock Wheel object and let DI framework inject to Car)

