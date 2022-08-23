###### tags: `Design Pattern` 
# State  

[Another example](https://refactoring.guru/design-patterns/state)  
[Another example2](https://github.com/iluwatar/java-design-patterns/tree/master/state)  

> Pattern UML 
> ![](https://i.imgur.com/P5Ehwfs.png)  

## Example 
[SourceCode](https://github.com/bethrobson/Head-First-Design-Patterns/blob/master/src/headfirst/designpatterns/state/gumball/GumballMachine.java)  

A gumball machine without state pattern 
```java
public class GumballMachine {
	// 4 states FOR Gumball Machine
	final static int SOLD_OUT = 0;
	final static int NO_QUARTER = 1;
	final static int HAS_QUARTER = 2;
	final static int SOLD = 3;

	int state = SOLD_OUT;   // Default state of the GumBall  
	int count = 0;		// Counts of GumBall, 0 by default

	public GumballMachine(int count) {
		this.count = count;
		if (count > 0) {
		    state = NO_QUARTER;
		}
	}

	/**
	* <p> Each Action would have one of these states
	*     <li> HAS_QUARTER </li> 
	*     <li> NO_QUARTER  </li>
	*     <li> SOLD_OUT    </li> 
	*     <li> SOLD        </li>
	* </p>

	public void insertQuarter() { // insert 0.25 U$D
		if (state == HAS_QUARTER) {
		    //..
		} else if (state == NO_QUARTER) {
		    //..
		} else if (state == SOLD_OUT) {
		    //..
		} else if (state == SOLD) {
		    //..
		}
	}

	public void ejectQuarter() { // eject 0.25 U$D
		//..
	}
	public void turnCrank() {
		//..
	}
	private void dispense() {
		//..
	}
```


If later we would add a new state in `GumballMachine` and this apparently violate open closed principle which menas the programmer need to add this "state" in each action method one by one.  
By state pattern to avoid such annoying thing  

[sourceCode](https://fjp.at/design-patterns/state)
```java
/**
  * <p> Abstraction stores </p>
  * <p> abstract Actions for Concrete States </p> 
  */
public interface State {

	public void insertQuarter(); 
	public void ejectQuarter(); 
	public void turnCrank();
	public void dispense();
    
	public void refill();
}

/**
  *<p> Concrete State can be  
  *    the new state we might add in the future </p>
  *<p> We can also control Context class' object in 
  *    In Concrete State </p>
  */
public class SoldoutState implements State{
    //...
    
}
public class SoldState implements State {
    //..
}
public class NoQuarterState implements State{
    private GumballMachine gumballMachine;
    
    //...
    
    public void insertQuarter(){
    	gumballMachine.setState(gumballMachine.getHasQuarterState());
    }
    
    //...
}
public class HasQuarterState implements State{
    //..
}

public class WinnerState implement State{
    //...
}

/**
  * <p> Context </p>
  */
public class GumballMachine {
 
 	// the concrete states
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	State winnerState;

	/**
	  * <p> Default state and count 
	  *     In the gumball machine </p>
	  */
	State state = soldOutState;
	int count = 0;


	public GumballMachine(int numberGumballs) {
		/**
		  * <p> Use {@code new} is suck </p>
		  * <p> The drawback of the state pattern </p>
		  */
		soldOutState = new SoldOutState(this);
		noQuarterState = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState = new SoldState(this);
		winnerState = new WinnerState(this);

		this.count = numberGumballs;
		if (numberGumballs > 0) {
			state = noQuarterState;
		} 
	}

	public void insertQuarter() {
		state.insertQuarter();
	}

	public void ejectQuarter() {
		state.ejectQuarter();
	}

	public void turnCrank() {
		state.turnCrank();
		state.dispense();
	}

	void setState(State state) {
		this.state = state;
	}

	/**
	  * @Description
	  *    dispense the gum ball to cumstomer
	  */
	void releaseBall() {
		System.out.println("A gumball comes rolling out the slot...");
		if (count > 0) {
			count = count - 1;
		}
	}
	
	/**
	  * @Description
	  *     re-fill the gum
	  * @param count
	  *     count for refill
	  */
	void refill(int count) {
		this.count += count;
		System.out.println("The gumball machine was just refilled; its new count is: " + this.count);
		state.refill();
	}
	
	/**
	  * @Description
	  *   get total counts of gum
	  */
	int getCount() {
		return count;
	}

	/** 
	  * <p> The following are getter methods </p>
	  * <li> get current state </li>
	  * <li> get SoldOut state </li>
	  * <li> get No Quarter state</li>
	  * <li> get Has Quarter state</li>
	  * <li> get Sold State </li>
	  * <li> get Winner state </li>
	  */
	public State getState() {
		return state;
	}

	public State getSoldOutState() {
		return soldOutState;
	}

	public State getNoQuarterState() {
		return noQuarterState;
	}

	public State getHasQuarterState() {
		return hasQuarterState;
	}

	public State getSoldState() {
		return soldState;
	}

	public State getWinnerState() {
		return winnerState;
	}
}

/**
  * <p> Client </p>
  */
public class GumballMachineTestDrive {
    public static void main(String[] args) {

		/**
		  * <p> A gumball Machine contains 10 gumballs </p>
		  */
		GumballMachine gumballMachine = new GumballMachine(10);

		/**
		  * <strong> unlike strategy pattern </strong>
		  * <p> state pattern uses one context object to
		  *	change the state </p>
		  */
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		gumballMachine.insertQuarter();
		gumballMachine.ejectQuarter();
		gumballMachine.turnCrank();

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.ejectQuarter();

		gumballMachine.insertQuarter();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
	}
}
```

## Difference btw State and Strategy 
The State and Strategy Patterns have the same class diagram, but they differ in intent.  

The Strategy Pattern typically configures **Context classes with a behavior or algorithm**, which can be done through composition during runtime.   

State Pattern allows **a(一個) Context to change its behavior as the state of the Context changes**.  
- State transitions can be controlled by the State classes or by the Context classes.  
- It is also possible for State classes to be shared among Context instances.

### [Strategy](Strategy.md) 

Strategy Pattern allow you change the behaviour **by composing with a different object**.
```java
/**
  * <p> This pattern avoids such case  
  * use inheritance to define the behavior of a class, 
  * then you’re stuck with that behavior even if you need to change it. </p>
  * <p> unlike state pattern </p>
  * <p> state pattern use different context object to have different behaviour </p>
  */
public class StrategyPattern{
  public static void main(String[] args){
    
    Context behaviour1 = new Context(new StrategyOne());
    Context behaviour2 = new Context(new StrategyTwo()));
    
    behaviour1.ExecuteStrategy(/** @param **/); 
    behaviour2.ExecuteStrategy(/** @param **/);
  }
}
```


