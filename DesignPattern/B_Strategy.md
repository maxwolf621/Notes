###### tags: `Design Pattern`

[fjp.github.io](https://fjp.at/design-patterns/strategy)  
[iluwatar](https://github.com/iluwatar/java-design-patterns/tree/master/strategy)
# Strategy
- [Strategy](#strategy)
  - [Pattern UML](#pattern-uml)
  - [Example](#example)
  - [Typescript](#typescript)

## Pattern UML  

![](https://i.imgur.com/QskFpjB.png)

```python
class concreteStragies1:
    def algorithm(self,'''parameters'''):
        #...
class concreteStragies2:
    def algorithm(self,'''parameters'''):
        #....

```

The Strategy pattern suggests: 
1. Encapsulating an algorithm/behavior in a class hierarchy (implement the interface Stratege)
2. Having clients of that algorithm holds a pointer to the base class of that hierarchy (Class `Context`)
3. Delegating all requests for the algorithm to that "anonymous" contained object. (`Execute(...)` method in `Context`)

## Example 

Create Strategy for algorithm

```java
public interface Strategy{
  public int algorithm()
}
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
 * CONTEXT picks up the interest strategy and executes it
 **/
public class Context{
    private Strategy strategy;

    public Context(Strategy strategy){
        // DI 
        this.strategy = strategy;
    }
    
    /**
      * @description
      *    <p> To Execute The Contrete Strategies. </p>
      *    <p> This method encapsulates the state of concrete strategies </p>
      */
    public int ExecuteStrategy(int a, int b){
        return strategy.algorithm(int a, int b);
    }

    // Other Business logics...
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



## Typescript 

```typescript
/**
 * The Context defines the interface of interest to clients.
 */
class Context {
    /**
     * @type {Strategy} The Context maintains a reference to one of the Strategy
     * objects. The Context does not know the concrete class of a strategy. It
     * should work with all strategies via the Strategy interface.
     */
    private strategy: Strategy;

    /**
     * Usually, the Context accepts a strategy through the constructor, but also
     * provides a setter to change it at runtime.
     */
    constructor(strategy: Strategy) {
        this.strategy = strategy;
    }

    /**
     * Usually, the Context allows replacing a Strategy object at runtime.
     */
    public setStrategy(strategy: Strategy) {
        this.strategy = strategy;
    }

    /**
     * The Context delegates some work to the Strategy object instead of
     * implementing multiple versions of the algorithm on its own.
     */
    public doSomeBusinessLogic(): void {
        // ...

        console.log('Context: Sorting data using the strategy (not sure how it\'ll do it)');
        const result = this.strategy.doAlgorithm(['a', 'b', 'c', 'd', 'e']);
        console.log(result.join(','));

        // ...
    }
}

interface Strategy {
    doAlgorithm(data: string[]): string[];
}

class ConcreteStrategyA implements Strategy {
    public doAlgorithm(data: string[]): string[] {
        return data.sort();
    }
}

class ConcreteStrategyB implements Strategy {
    public doAlgorithm(data: string[]): string[] {
        return data.reverse();
    }
}

/**
 * The client code picks a concrete strategy and passes it to the context. The
 * client should be aware of the differences between strategies in order to make
 * the right choice.
 */
const context = new Context(new ConcreteStrategyA());
console.log('Client: Strategy is set to normal sorting.');
context.doSomeBusinessLogic();

console.log('');

console.log('Client: Strategy is set to reverse sorting.');
context.setStrategy(new ConcreteStrategyB());
context.doSomeBusinessLogic();
```