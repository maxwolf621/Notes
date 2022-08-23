###### tags: `Design Pattern`
# Strategy
[Header's example](https://fjp.at/design-patterns/strategy)  
[Another Example](https://github.com/iluwatar/java-design-patterns/tree/master/strategy)

> Pattern UML  
> ![](https://i.imgur.com/QskFpjB.png)

```python
class concreteStragies1:
    def algorithm(self,'''parameters'''):
        #...
class concreteStragies2:
    def algorithm(self,'''parameters'''):
        #....

```

The Strategy pattern suggests: 
1. Encapsulating an algorithm/behavior in a class hierarchy (implement the interface)
2. Having clients of that algorithm holds a pointer to the base class of that hierarchy (Class Context)
3. Delegating all requests for the algorithm to that "anonymous" contained object. (ExecuteStraegy(..) method in Context)



## Example 

Doing `+` or `-` for arithmetic 


```java
public interface Strategy{
 public int algorithm()
}


/**
  * <p> Concrete Strategy </p>
  */
public class doAdd implements Strategy{
  @Override
  public int algorithm(int a ,int b){
    //do something
  }
}

public class doSubstract implements Strategy{
  @Override
  public int algorithm(int a, int b){
    //do something
  }
}

/**
  * <p> It allows us use difference strategies(behaviors) at run-time <p> 
  * <p> it encapsulates the state of concrete strategies </p>
  */
public class Context{
    private Strategy strategy;

    public Context(Strategy strategy){
        // DI 
        this.strategy = strategy;
    }
    
    /**
      * @Description
      *    <p> To Execute The Contrete Strategies. </p>
      *    <p> This method encapsulates the state of concrete strategies </p>
      */
    public int ExecuteStrategy(int a, int b){
        return strategy.algorithm(int a, int b);
    }
}


/**
  * <p> Client </p>
  */
public class StrategyPattern{
  public static void main(String[] args){
    
    Context add = new Context(new doAdd());
    Context substract = new Context(new doSubstract()));
    
    /**
      * <strong >we can have different algorithms in run-time
      *          such as add , substract ... </strong> 
      */
    int a = 5;
    int b = 1;
    
    add.ExecuteStrategy(a,b); 
    substract.ExecuteStrategy(a,b);
  }
}
```
