###### tags: `Design Pattern`
# Singleton
[TOC]
   
**The Singleton Pattern ensures a class has only one instance, and provides a global point of access to it.**
> pattern UML  
> ![](https://i.imgur.com/ZhgkHCD.png)  

Examples where singletons are useful are:
- thread pools
- caches
- dialog boxes
- objects that handle preferences and registry settings
- objects used for logging
- objects that act as device drivers to devices like printers and graphics cards.

In fact, for many of these types of objects, if we were to instantiate more than one we’d run into all sorts of problems like incorrect program behavior, overuse of resources, or inconsistent results.  
By making use of the Singleton one can assure that every object in an application is making use of the same global resource.


## Lazy Initialization 
The singleton is similar to global variables but without the downside of getting created at program start like *global* variables. 
Instead, ***the singleton can be created only when it is needed, which can avoid time consuming initialization.***  
> if we never need the instance, it never gets created. This is lazy instantiation.

## Responsibilities 

1. Managing its one instance (and providing global access) 
2. It is also responsible for whatever its main role is intended. 

## `synchronized` singleton pattern
- `synchronized` 以免共用存取時，發生資料的競速（Race condition）問題。

a singleton pattern without **synchronized**
```java
/**
  * @apinote
  *   It is not thread safe
  *   <strong> this will cause the problem while applying for multiple threading </strong>
  *   <strong> A singleton should not be inherited from because its constructor should remain private. </strong>
  */
public class singleton{

    /**
      * {@code static} is shared by everyone
      */
    private static singleton unique;
    
    /**
      * @Description
      *   Create static singleton unique
      *   if {@code unique} is {@code null}
      *   then create one 
      *   else
      *   @return {@code unique}
      */
    pubic static singleton getsingleton()
    {
        if(unique == null){
            unique = new Singleton();
        }
        return unique;
    }
    
    //..
}
```
- In this case, Singleton is instantiated through its private constructor and assigned to to `unique` Instance. 
- If `unique` Instance wasn’t `null`, then it was previously created and is therefore returned. Because the `getInstance()` is a `static` method, it allows access from anywhere in the code using `Singleton::getInstance()`. This is just as easy as accessing a global variable but with the advantage of lazy instantiaion.

Add `synchronized` for gett method for thread Safe  
```java
public static synchronized singleton getsingleton(){
    //...
}
```
- If the application always creates and uses an instance of Singleton or the overhead of creation and run-time aspects of the Singleton are not onerous then `synchronize` is expensive
  > the synchronization is only relevant the first time when there was no instance created yet. 
- ***Once we've set the `unique` to an instance of singleton, we have no further need to synchronize this method.***

To reduce the cost of `synchronize`.
There are two way to handle as the following 

### `new` a private instance of singleton

```java
public class Singleton {

    // Default constructor does notrhing
    private Singleton() {}
    
    /**
      * <p> Guaranteed thread safe
      *     and also get away of affecting of synchronization </p>
      */
    private static Singleton unique = new Singleton();

    // getter
    public static Singleton getInstance() {
        return unique;
    }
    
    //....
}
```

### Double-checked Locking

First check if an instance of singleton is created, and if not (`== null`) then using `synchronized` to create one.    
This means using `synchronized` at the first time for creating a unique instance of singleton
```java
/**
  * @apinote Only creating synchronized Singleton once 
  */
public class Singleton {
    
    private volatile static Singleton unique;
    
    private Singleton() {}
    
    /**
      * <p> using {@code synhronized} only once
      *     while creating a instance
      */
    public static Singleton getInstance() {
        if (unique == null) {
            synchronized (Singleton.class) {
                if (unique  == null) {
                    unique  = new Singleton();
                }
            }
        }
        return unique;
    }
}
```


