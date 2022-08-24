###### tags: `Design Pattern`
# Observer 

- [Observer](#observer)
  - [Properties for Subject(Obersable) and Observer](#properties-for-subjectobersable-and-observer)
  - [Example](#example)
      - [Concrete Subject (Ovservable)](#concrete-subject-ovservable)
      - [Concrete Observer](#concrete-observer)
      - [Client](#client)
  - [Java build-in Observer Pattern](#java-build-in-observer-pattern)
  - [Typescript](#typescript)

This pattern defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.   
![](https://i.imgur.com/EVVQirN.png)  


> Pattern UML  
> ![](https://i.imgur.com/CzsW8oV.png)
- The Observer Pattern provides an object design where subjects and observers are loosely coupled.  
	> When two objects are loosely coupled, they can interact,but have very little knowledge of each other  

Observer Pattern contains two parts  
1. Interface **Observable** Subject
    > `subscribe(observer o)` , add the Observer to list  
    > `unsubscribe(observer o)`, remove the Observer from list   
    > `notifyObservers(type field)`, update each observer in the list  
2. Interface Observer (get the data from `Observable`)  
    > `update()` : this will be called by Observable Subject's method `notifyObservers(type field)` to update the fields


```vim
Observable ---- observer.subscribe() ---> Oberserver
```

```python
class subject:
    def __init__(self):
        self.observers = []
        self._state = None
        self._stateNum = 0
        # other states ...
    def subscribe(self, observer):
        self.observers.append(observer)
    
    @property
    def GetState(self):
    # get state
        return self._state
    def GetStaeNum(self):
        return self._stateNum
    
    @product.setter
    def setState(self, value, valueNum):
    # set state
        self._state = value
        self._stateNum= value
        self._notifyObservers()
    def _notifyObservers(self):
    # update 
        for observer in self.observers:
            observer()
```


## Properties for Subject(Obersable) and Observer

The only thing the subject knows about an observer is that it implements a certain interface (the Observer interface). 

The subject doesn't need to know the concrete observe (so we can add/delete new observers at any time). 

Because the only thing the subject depends on is a list of observers that implement their Interface, and we also can replace any observer implementations at **run-time** with another observer and the subject will keep purring along.
   
- **We never need to modify the subject to add new types of observers. (Observers subscribe the subject)**
- **We can reuse subjects or observers independently of each other**
- If we have another use for a subject or an observer, we can easily reuse them because the two aren’t tightly coupled.
	> Changes to either the subject or an observer will not affect the other. Because the two are loosely coupled.

## Example 

Design the Weather Station  
![](https://i.imgur.com/PbT1KsS.png)  

1. **Subject (Needed for Pattern)**
2. **Observer (Needed for Pattern)**
3. DisplayElement 

```java
public interface Subject {
    // To add the observer
    public void subscribe(Observer o);
    // to remove the obeserver
    public void unsubscribe(Observer o);
    // Notify(update) obeservs 
    //    when the States of subject has Changed
    public void notifyObservers();
}

public interface Observer {
	public void update(float temp, float humidity, float pressure);
}

public interface DisplayElement {
    public void display();
}
```

#### Concrete Subject (Ovservable)
```java
/**
  * <p> Concrete Subject </p>
  * <p> Good for using List instead of ArrayList </p>
  * {@link #https://stackoverflow.com/questions/2279030/type-list-vs-type-arraylist-in-java}
  */
public class WeatherData implements Subject {
    
    // observers who subscribes this subject
    private List<Observer> observers;
    // observers need to contains these fields
    private float temperature;
    private float humidity;
    private float pressure;

    // the constuctor will create list of observers
    public WeatherData() {
        // list of observers
        observers = new ArrayList<Observer>();
    }

    // these two methods will be called by Observer
    public void subscribe(Observer o) {
        observers.add(o);
    }
    public void unsubscribe(Observer o) {
        observers.remove(o);
    }


    // Notify Observers if subject has any updates
    public void notifyObservers() {
        for (Observer observer : observers) {
            /**
             * <p> when {@code update} is called
             * {@code display} would also be called </p>
             **/
            observer.update(temperature, humidity, pressure);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temperature, 
                                float humidity, 
                                float pressure) 
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    //... other methodes
}
```

#### Concrete Observer
```java
public class CurrentConditionsDisplay implements Observer, DisplayElement 
{
    // notification from temperature
    private float temperature;
    private float humidity;
    private float pressure;
    
    // Subject (Observable)
    private WeatherData weatherData;
    
    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        
	// <<SUBSCRIBE>> :: add this observer to array list observers
        weatherData.subscribe(this);
    }
    
    // This is how Subject pass the "observable(data)" to Observer
    public void update(float temperature, 
                       float humidity, 
                       float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " +
                            emperature + 
                            "F degrees and " + 
                            humidity + "% humidity");
    }
}
```



#### Client

```java

public class WeatherStation {
    public static void main(String[] args) {

        // Observable Subject
        WeatherData weatherData = new WeatherData();


        // Observer 
        CurrentConditionsDisplay currentDisplay = 
            new CurrentConditionsDisplay(weatherData);

        StatisticsDisplay statisticsDisplay = 
            new StatisticsDisplay(weatherData);

        /**
         * Intuition
         * weatherData.subscribe(new CurrentConditionsDisplay())
         * weatherData.subscribe(new StatisticsDisplay())
         */

        /** 
         * Notifications
         * <p> Subjects updates three times </p>  
         *  <p> Observer will know </p> 
        **/
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);


        // remove the observer {@code currentDisplay}
        weatherData.unsubscribe(currentDisplay);

	}
}
```


## Java build-in Observer Pattern

![](https://i.imgur.com/Onrd9pB.png)



## Typescript

```typescript
interface Subject {
    // subscribe an observer to the subject.
    subscribe(observer: Observer): void;

    // unsubscribe an observer from the subject.
    unsubscribe(observer: Observer): void;

    // Notify all observers about an event.
    notify(): void;
}

class ConcreteSubject implements Subject {
    /**
     * @type {number} For the sake of simplicity, the Subject's state, essential
     * to all subscribers, is stored in this variable.
     */
    public state: number;

    private observers: Observer[] = [];


    public subscribe(observer: Observer): void {
        const isExist = this.observers.includes(observer);

        if (isExist) {
            return console.log('Subject: Observer has been subscribeed already.');
        }
        this.observers.push(observer);
    }

    public unsubscribe(observer: Observer): void {
        const observerIndex = this.observers.indexOf(observer);
        if (observerIndex === -1) {
            return console.log('Subject: Nonexistent observer.');
        }

        this.observers.splice(observerIndex, 1);
        console.log('Subject: unsubscribed an observer.');
    }

    /**
     * Trigger an update in each subscriber.
     */
    public notify(): void {
        console.log('Subject: Notifying observers...');
        for (const observer of this.observers) {
            observer.update(this);
        }
    }

    /**
     * Usually, the subscription logic is only a fraction of what a Subject can
     * really do. Subjects commonly hold some important business logic, that
     * triggers a notification method whenever something important is about to
     * happen (or after it).
     */
    public someBusinessLogic(): void {
        console.log('\nSubject: I\'m doing something important.');
        this.state = Math.floor(Math.random() * (10 + 1));

        console.log(`Subject: My state has just changed to: ${this.state}`);
        this.notify();
    }
}

/**
 * The Observer interface declares the update method, used by subjects.
 */
interface Observer {
    // Receive update from subject.
    update(subject: Subject): void;
}

/**
 * Concrete Observers react to the updates issued by the Subject they had been
 * subscribeed to.
 */
class ConcreteObserverA implements Observer {
    public update(subject: Subject): void {
        if (subject instanceof ConcreteSubject && subject.state < 3) {
            console.log('ConcreteObserverA: Reacted to the event.');
        }
    }
}

class ConcreteObserverB implements Observer {
    public update(subject: Subject): void {
        if (subject instanceof ConcreteSubject && (subject.state === 0 || subject.state >= 2)) {
            console.log('ConcreteObserverB: Reacted to the event.');
        }
    }
}


/**
 * Client
 */
const subject = new ConcreteSubject();

const observer1 = new ConcreteObserverA();
subject.subscribe(observer1);

const observer2 = new ConcreteObserverB();
subject.subscribe(observer2);

subject.someBusinessLogic();
subject.someBusinessLogic();

subject.unsubscribe(observer2);

subject.someBusinessLogic();
```