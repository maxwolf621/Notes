###### Tags : `JAVA`
# Thread Safe 
- [Thread Safe](#thread-safe)
  - [`Collections.synchronizedCollection`](#collectionssynchronizedcollection)
  - [`ConcurrentHashMap`](#concurrenthashmap)
  - [Atomic Objects](#atomic-objects)
  - [Synchronization](#synchronization)
    - [Synchronized Methods](#synchronized-methods)
    - [Synchronized statements](#synchronized-statements)
  - [Volatile Fields](#volatile-fields)
    - [The happens-before relationship](#the-happens-before-relationship)

## `Collections.synchronizedCollection`
- 創建一個thread-safe的集合(collection)透過synchronization wrappers。
```java
Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
Thread thread1 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
Thread thread2 = new Thread(() -> syncCollection.addAll(Arrays.asList(7, 8, 9, 10, 11, 12)));
thread1.start();
thread2.start();
```
- synchronized collections 利用intrinsic locking被lock在每一個method中.
  > intrinsic lock是一個與class中特定的Instance有關的Internal Instance  
- method在同一時間只可以被一個thread存取,假如其他threads試圖存取，就會被阻擋blocked，直到那個method被上一個thread 解鎖(unlocked)。

## `ConcurrentHashMap`

synchronized collections(`java.util.concurrent`)的替代品
```java
Map<String,String> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("1", "one");
concurrentMap.put("2", "two");
concurrentMap.put("3", "three");
```
- concurrent collections跟synchronized collections不同的地方是**concurrent collections會將數據分成不同segments做到thread-safety的功能**
- **在 `ConcurrentHashMap` ，每一個thread可以鎖住一個map的segment(一個map由不同segments組成)。所以一個map可以同時被多個threads存取**


1. `ConcurrentHashMap` : Thread safe without having to `synchronized` the whole map very fast reads while write is done with a lock No locking at the object level Uses multitude of locks.
2. `SynchronizedHashMap` : **Object level synchronization Both read and writes acquire a lock Locking the collection** has a performance drawback May cause contention
    - Vector
    - HashTable
    - CopyOnWriteArrayList
    - CopyOnWriteArraySet
    - Stack

## Atomic Objects

Java提供 `AtomicInteger`, `AtomicLong`, `AtomicBoolean`, 和 `AtomicReference` 等 Atomic Objects 來完成 Thread Safe

- Atomic Classes最大的優勢是可容許利用上述Atomic Objects的操作以做到Thread Safe，同時沒有用到任何高成本的`synchronized`  

```java
/**
  * <p> 在沒有Atomic的情況發生Race Condition
  *     例如兩個threads同時競爭存取{@code incrementCounter} 
  *     因為{@code incrementCounter}不是atomic 
  *     導致最counter的值會是2 
  * </p>
  */
public class Counter {

    private int counter = 0;
     
    public void incrementCounter() {
        counter += 1;
    }
     
    public int getCounter() {
        return counter;
    }
}

/**
  *<p> Atomic object 
  *    calls {@code incrementAndGet} 時會先加1再存取加1之後的值 </p>
  */
public class AtomicCounter {

    private final AtomicInteger counter = new AtomicInteger();
     
    public void incrementCounter() {

        /**
          * <p> call atomic object method </p>
          * <p> {@code incrementAndGet} to avoid race condition </p> 
          */
        counter.incrementAndGet();
    }
    public int getCounter() {
        return counter.get();
    }
}
```

## Synchronization

### Synchronized Methods
synchronized method use an **intrinsic locks** or **monitor locks** to lock a certain thread  

- The Lock allows only one Thread to access synchronized method and block other treads until the lock thread gets it job down or throws exception  

```java
public synchronized void incrementCounter() {
    counter += 1;
}
```
- 在Multiple Threads的環境中，**monitor locks只是一個監視角色作為監測不包括的存取情況**    
- When the tread finish the job, it will release intrinsic locks allowing other threads to fetch the intrinsic locks to access the `synchronized` method

### Synchronized statements 

當我們只是對於一個segment來進行thread-safe如果是使用synchronized method則會造成很多不必要的cost, 可以使用Synchronized Statements對某一特定物件實行synchronize  
  
```java
public void incrementCounter() {
    /**
      * 只針對一些需要用到synchronization的object
      * 放到synchronized block內
      */
    synchronized(this) {
        counter += 1; 
    }
}
```
- 與Synchronized Method不同，synchronized statements必須具體指出物件  

## Volatile Fields

**在一般class中, 變量的值會被儲存在CPU Cache, 不過由於這個情況會對變量的**可見性**受到影響，有可能會出現不被其他threads見到的情況  **

![圖 1](images/9d3972d349cf1168237471fbd408d5f3f978cf4c8fcd15f0661c879e31fe78fb.png)  

Volatile 主要提供 
1. Visibility guarantee for data 
2. The value of the variable with `volatile` keyword will immediately flush to memory as long as it is modified  

For example
```java 
public class Counter {
    private volatile int counter;     
}
``` 
- 透過 volatile 變數 `counter` 值會被儲存在主記憶體內, 所以當執行這個Program時，會直接去Memory查找Counter而不是在CPU cache內  
  > 每一次 JVM 寫入`counter`的值時都會直接寫入Memory
  > ![圖 2](images/48a731948f0be9b40b77e236aff195b94b866b34baad640d6f8153ec67a84922.png)  
- `volatile` 可以讓其他的Threads都能夠在Main Memory內見到`volatile`變數的值(Visibility of Data)

### The happens-before relationship  

The happens-before relationship is a very important aspect of the Java Memory Model. [More Detail](Thread.md)   
- **When an happens-before relationship is established between two distinct events it means that all changes made in the first event and also its current state will be seen and reflected in the second event.**
  > E,g. When a thread writes into a volatile variable and another thread later accesses that same variable, an happens before relationship will be established between the two threads. **All changes made by the first thread will be visible by the second thread.**

**In fact all the other shared and locally cached variables will be propagated from the writing thread through the thread that later accessed the volatile variable, even if the other variables are not declared as volatile.**









