# Generic 

[Ref](https://ethan-imagination.blogspot.com/2018/11/javase-gettingstarted-017.html)

- [Generic](#generic)
  - [Generic Interface](#generic-interface)
  - [Generic Implementation](#generic-implementation)
  - [Generic Method](#generic-method)
  - [Wildcard `?`](#wildcard-)
    - [Bounded Wildcards](#bounded-wildcards)
  - [Questions](#questions)

```java
className<Type> obj = New className<Type>();
```
- `Type`不可為Primitive Type

## Generic Interface

```java
interface Info<T> { 
    public T getVar(); 
    public void setVar(T x);
}

class InfoImpl implements Info<String> { 
    private String var;
    
    public InfoImpl(String var){
        this.setVar(var);
    }
    
    @Override
    public void setVar(String var){
        this.var = var;
    }
    
    //...
}
```

## Generic Implementation

```java
interface Info<T> { 
    public T getVar(); 
    public void setVar(T x);
}

class InfoImpl<T> implements Info<T> {
    private T var; 
    
    public InfoImpl(T var){
        this.setVar(var);
    }
    
    @Override
    public void setVar(T var){
        //...
    }
    
    //...
}

class TestGeneric4 {
    public static void main(String[] args){
        InfoImpl<String> i = new InfoImpl<String>("Java generic interface 2.");
        System.out.println(i.getVar());
    }
}
```

## Generic Method

```java
class TestGeneric5 {
    
    static <T> void staticMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
    
    <T> void otherMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
    
    public static void main(String[] args){
        
        // use static method
        // Class.method(..)
        // Class.<T>Method(..)
        TestGeneric5.staticMethod(13.5F);                   
        TestGeneric5.<String>staticMethod("Java is good!"); 
        
        // use regular method
        // object.method(T a, ...)
        // object.<T>Method(T a, ...)
        TestGeneric5 obj = new TestGeneric5();
        obj.otherMethod((byte)23);                  
        obj.<Integer>otherMethod(new Integer(123));  
    }
}
```


## Wildcard `?`


```java
infoImpl<Integer> p1 = new infoImpl<Integer>();
infoImpl<Float> p2 = new infoImpl<Float>();


// Allow p references to All Number object
// Accept all the subclass 
infoImpl<? extends Number> p = null;

// p can reference to subclasses extending Number
p = new infoImpl<Integer>;  // Reference to Integer object
p = new infoImpl<Float>;    // Reference to Float object;
```

### Bounded Wildcards

Upper Bounded Wildcards(Getter)
```java
// ref can reference to SubClasses of BaseClass
Class <? extends BaseClass> ref;

/**
        +————————————————+
       /    BaseClass     \
      /     /      \       \ 
     /    subL      subR    \
    /     / \        / \     \
   /     .. ..      .. ..     \
  +————————————————————————————+ 
   
**/     
```

Lower Bounded Wildcards (Setter)
```java
// ref can reference to any SubClass's BaseClasses 
Class <? super subClass> ref;

/**
           *———————————————*
          /      BaseClass |
         /        /  *—————*
        /      subL  |    \
       /       / ————*   subR
      /  subClas |  \     / \
     /___________|  ..   .. ..


**/     
```

For Example 
```java
class Food{}

class Fruit extends Food{}
class Meat extends Food{}

class Apple extends Fruit{}
class Banana extends Fruit{}
class Pork extends Meat{}
class Beef extends Meat{}

class RedApple extends Apple{}
class GreenApple extends Apple{}

// Generic Class
class Plate<T> {
    private T item;
    public Plate(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}
}

Plate<Fruit> plate = New Plate<Apple>(New Apple()); // Error

// Upper Bounded Wildcard 
// plateU can reference to any subclasses of Fruit
Plate< ? extends Fruit> plateU ;

// reference type must >= Fruit 
Apple a  = plateU.get(); // err
Fruit f  = plateU.get();
Object o = plateU.get();

// Lower Bounds Wildcard
// plateL can reference to current class's BaseClasses
Plate< ? super Fruit> plateL;
Object o = plateU.get(); // Only Object allowed
```

## Questions

- Java中的泛型是什麽? 使用泛型的好處是什麽?
  - 在Java1.4或更早版本，在集合中存儲對象並在使用前進行類型轉換（強制轉換）非常的不方便，所以**泛型提供了編譯期Type safety，確保你只能把正確類型的對象放入集合中，避免了Run-Time出現異常(`ClassCastException`)**  

* Java的泛型是如何工作的 ?
  - 泛型是通過Type erasure來實現的，Compiler在編譯時擦除了所有類型相關的資訊，所以在運行時不存在任何類型相關的訊息。
    - 你無法在Rin-Time時訪問到類型參數，因為Compiler已經把泛型類型轉換成了原始類型。
  > For example, `List<String>`在運行時僅用一個`List`來表示。這樣做的目的，是確保能和Java 5之前的版本二進制類庫進行相容
  
- 什麽是泛型中的限定通配符和非限定通配符 ?
  - 有兩種限定通配符，一種是`<? extends T>`它通過確保類型必須是T的subclass來設定類型的上界，另一種是`<? super T>`它通過確保類型必須是T的base class來設定類型的下界  
    - 泛型類型必須用限定內的類型來進行初始化，否則會導致Compiler Error。
    - 另一方面`<?>`表示了非限定通配符，因為`<?>`可以用任意類型來替代
* `List<? extends T>`和`List <? super T>`之間有什麽區別 ?
   - `List<? extends T>`和`List <? super T>`都是限定通配符，`List<? extends T>`可以接受任何繼承自T的類型的List，而`List<? super T>`可以接受任何T的父類構成的List。
   - 例如`List<? extends Number>`可以接受`List<Integer>`或`List<Float>`
- 一個泛型方法，讓它能接受泛型參數並返回泛型類型?
  - 你需要用泛型類型來替代Primitive Type，比如使用`T`, `E` ,`K`,or `V`等被廣泛認可的類型占位符。
  - 最簡單的情況下，一個泛型方法可能會像這樣:
  ```java
  public V put(K key, V value) {
     return cache.put(key, value);
  }
  ```
-  使用Generic來實現LRU Cache?
   - `LinkedHashMap`可以用來實現固定大小的LRU Cache，當LRU Cache已經滿了的時候，它移出最早的Key-Value Pair Cache    
   - `LinkedHashMap`提供了一個稱為`removeEldestEntry()`的Method，該方法會被`put()`和`putAll()`呼叫並刪除最老的key-value pair  
- 你可以把`List<String>`傳遞給一個接受`List<Object>`參數的方法嗎？
  - 乍看之下`String`是一種`Object`，所以`List<String>`應當可以用在需要`List<Object>`的地方，但是事實並非如此。真這樣做的話會導致編譯錯誤。  
    - **因為`List<Object>`可以存儲任何類型的對象包括`String`, `Integer`等等，而`List<String>`卻只能用來存儲`String`s**
  ```java
  List<Object> objectList;
  List<String> stringList;
  objectList = stringList;  //compilation error incompatible types
  ```
- Array中可以用泛型嗎?
    - Array事實上並不支持泛型，Effective Java一書中建議使用`List`來代替`Array`，因為`List`可以提供Compiler Time的類型安全保證，而Array卻不能。

- 如何阻止Java中的類型未檢查的警告?
    - 如果你把泛型和原始類型混合起來使用，例如 Java 5的javac Compiler會產生類型未檢查的警告 `List<String> rawList = new ArrayList()`
