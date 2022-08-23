###### tags: `Design Pattern`
# Observer 
This pattern defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.   
![](https://i.imgur.com/EVVQirN.png)  


> Pattern UML  
> ![](https://i.imgur.com/CzsW8oV.png)
- The Observer Pattern provides an object design where subjects and observers are loosely coupled.  
	> When two objects are loosely coupled, they can interact,but have very little knowledge of each other  

Observer Pattern contains two parts  
1. Interface Observable Subject
    > `registerObserver(observer o)` , add the Observer to list  
    > `removeObserver(observer o)`, remove the Observer from list   
    > `notifyObservers(type field)`, update each observer in the list  
2. Interface Observer (who will get the data from `Observable`)  
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
    def registerObserver(self, observer):
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

The subject doesn’t need to know the concrete observe** (so we can add/delete new observers at any time). 
    > Because the only thing the subject depends on is a list of observers that implement their Interface, and we also can replace any observer implementations at **run-time** with another observer and the subject will keep purring along.
   
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
    public void registerObserver(Observer o);
    // to remove the obeserver
    public void removeObserver(Observer o);
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

Concrete Subject (Ovservable)
```java
/**
  * <p> Concrete Subject </p>
  * <p> Good for using List instead of ArrayList </p>
  * {@link #https://stackoverflow.com/questions/2279030/type-list-vs-type-arraylist-in-java}
  */
public class WeatherData implements Subject {
    // our observers List
    private List<Observer> observers;
    // observers need to contains these fields
    private float temperature;
    private float humidity;
    private float pressure;

    // subject constuctor will create list of observers
    public WeatherData() {
    // list of observers
        observers = new ArrayList<Observer>();
    }

    // these two methods will be called in observers's constructor
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /** 
     * @Description
     *   it will invoke while calling 
     *   {@code setMeasurements}
     *    '->{@code measurementsChanged}
     *        '-->{@code notifyObservers}
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            /**
	      * <p> when {@code update} is called
              *     {@code display} would also be called </p>
	      */
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

Concrete Observer
```
/**
  * <p> CurrentConditionDisplay as Observer </p>
  */
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
        weatherData.registerObserver(this);
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
- we can also have another Display such as ForecastDisplay... etc 

```java
/**
  * <p> Client </p>
  */
public class WeatherStation {
    public static void main(String[] args) {
        // create the subject
        WeatherData weatherData = new WeatherData();

        /**
	 * <p> Subscribers </p>
	 * Kidda like xxxxDisplay.subscribe(xxxxData)
	 */
	CurrentConditionsDisplay currentDisplay =  new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        /** 
	  * Notifications 
	  * <p> Subjects updates three times </p> 
          * <p> Observer will know </p> 
	  */
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);


        // remove the observer {@code currentDisplay}
        weatherData.removeObserver(currentDisplay);

	}
}
```


## Java build-in Observer Pattern

![](https://i.imgur.com/Onrd9pB.png)
