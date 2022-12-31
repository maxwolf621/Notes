###### tags: `JAVA`
[Java 基本問題](https://reurl.cc/0prKQk)     
[OOD Principle](https://github.com/maxwolf621/DesignPattern/blob/main/_Principle.md)   

# Basics

- [Basics](#basics)
  - [Pass By Parameter](#pass-by-parameter)
  - [Pass By Value](#pass-by-value)
  - [Access-Specifier](#access-specifier)
    - [Access Control For The Class](#access-control-for-the-class)
  - [OOP Four Basic Concepts of OOP](#oop-four-basic-concepts-of-oop)
    - [Encapsulation](#encapsulation)
    - [Polymorphism](#polymorphism)
    - [Abstraction(Hiding the Implementation)](#abstractionhiding-the-implementation)
    - [Inheritances (Reusability)](#inheritances-reusability)
  - [Inheritance vs Abstraction](#inheritance-vs-abstraction)
    - [`Is-A` Relationship Does Not Work In Reverse](#is-a-relationship-does-not-work-in-reverse)
  - [`finalize` keyword](#finalize-keyword)
  - [IMMUTABLE `final` keyword](#immutable-final-keyword)
    - [Final Value](#final-value)
    - [Final Class](#final-class)
  - [Package & Namespace](#package--namespace)
    - [Class Loader](#class-loader)
  - [`abstract` keyword](#abstract-keyword)
    - [abstract class](#abstract-class)
    - [When an Abstract Class Implements an Interface](#when-an-abstract-class-implements-an-interface)
    - [When an Abstract Class extends an Concrete Class (Inheritance)](#when-an-abstract-class-extends-an-concrete-class-inheritance)
    - [`IS-A` Hierarchies Hierarchy](#is-a-hierarchies-hierarchy)
  - [`interface` keyword (`abstract`的延伸)](#interface-keyword-abstract的延伸)
  - [Java 9 `private` and `private static`](#java-9-private-and-private-static)
    - [Usage of the Private Method](#usage-of-the-private-method)
    - [Benefits of Private Methods in Interfaces](#benefits-of-private-methods-in-interfaces)
  - [Interface & Abstract Class](#interface--abstract-class)
    - [Use Interface or Abstract](#use-interface-or-abstract)
  - [`super`](#super)
  - [`Override`](#override)
  - [Dynamic Binding](#dynamic-binding)
    - [Overload](#overload)
  - [Anonymous Inner Classes](#anonymous-inner-classes)
## Pass By Parameter 

```java
public class Dog {

    String name;

    Dog(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getObjectAddress() {
        return super.toString();
    }
}

class PassByValueExample {
    public static void main(String[] args) {

        // dog is a reference to address (obj)
        Dog dog = new Dog("A");

        func(dog); // parameter is address's value
        System.out.println(dog.getName());   // B
    }

    private static void func(Dog dog) {
        // reference to the same address
        // which will actually change 
        dog.setName("B");
    }
}
```
## Pass By Value

```java
public class PassByValueExample {
    public static void main(String[] args) {
        Dog dog = new Dog("A");
        
        System.out.println(dog.getObjectAddress()); 
        // Dog@4554617c

        func(dog);
        
        System.out.println(dog.getObjectAddress()); 
        // Dog@4554617c
        
        System.out.println(dog.getName());          
        // A
    }

    // Assign New Reference to An Object
    private static void func(Dog dog) {
        System.out.println(dog.getObjectAddress()); 
        // Dog@4554617c

        dog = new Dog("B");  
        // change reference#dog from Dog@4554617c to Dog@74a14482

        System.out.println(dog.getObjectAddress()); 
        // Dog@74a14482

        System.out.println(dog.getName());          
        // B
    }
}
```

## Access-Specifier

```java
access-specifier storage returnType function(parameters)

public final integer fun(int i , String b){
  //...
}
```

### Access Control For The Class

Visibility Within package   
|Access Specifier | Accessible to a SUBCLASS inside A same package | Accessible to all OTHER CLASSES in the same package|
| ---             | ---                                            |---                                                  |
|  default(no modifier)  | Yes                                     |Yes                                                 |
|  public                | Yes                                     |Yes                                                 |
|  protected             | Yes                                     |Yes                                                 |
|  private               |  No                                     |No                                                  |   

- **`default` is used when classes and `public void main()` in the same package**

---

Visibility Outside the class's package 
| Access Specifier      |  Accessible to a SUBCLASS outside the same package | Accessible to all OTHER CLASSES outside the same package |
| ----                  |     ---                                            |     ----                                                 |
|  default(no modifier) |     No                                             |     No                                                   |
|  public               |     Yes                                            |     Yes                                                  |
|  protected            |     Yes                                            |     No                                                   |
|  private              |     No                                             |     No                                                   | 



***The Derived classes can not access base's `private` members but theses can be accessed via `public`/`protected` method from Base Class***  

**`protected`通常都用來修飾類別成員(e.g. methods, field)，表該成員在繼承時對於其Derived classes是可見的，其對於類別(Class)沒什麼意義**      

| Access Specifier    |     |
| ---                 | --- |
| `public` class A    | Any Classes can inherit from it and uses it (its methods)
| `private` class A   | Attributes in A can only be accessed by method **in the same class**, can't be the **BASE class**   
| `protected` class A | Members in A can only be **accessed by same package's classes or its subclasses(other classes from other packages can inherit from it)**
| `default` class A   | Only Classes in SAME PACKAGE can inherit from it (Accessed By the Classes Within the same package)

PS
1. **DO NOT declare more than one `public` class in same file**   
2. Pubic class' name should be the same as file name.  
   e.g. filename : `X.java` => class name : `public class X`  
3. **non-public class only can be accessed by same package's classes**.  
## OOP Four Basic Concepts of OOP
- [Class and Object](https://medium.com/@nwyyy/design-pattern%E5%88%9D%E5%BF%83%E8%80%85%E7%AD%86%E8%A8%98-1-95774a905010)   

Object-oriented programming (OOP) is a programming paradigm(model) based on the concept of **objects, which can contain data and code**.    
- Data : in the form of Fields (often known as attributes or properties)
- Code : in the form of procedures (often known as methods).
```html
Object = (DATE)Fields + (CODE)Methods
```

1. Encapsulation
2. Polymorphism
3. Abstraction
4. Inheritance

### Encapsulation

**Encapsulation is wrapping/binding up of Data and Code(functions) in single unit.**

OBJECT將其本身的資料以及行為 (Behaviors) 包裝在其內部(Group all relevant things together.)，**外界除了透過物件所開放的成員  (如： 屬性、方法、事件...etc...) 使用物件外，不需知道物件內部的各種實作細節**

**MODEL之間只透過他們API進行溝通，一個模組不需要知道其他模組的内部工作情況，我們把概念稱作訊息隱藏或封装**. e.g 利用instance提供的method(e.g setter/getter)來訪問某MODEL內的某個功能  

### Polymorphism 

Polymorphism describes a pattern in OOP in which **classes have different functionality while sharing a common interface.**   
To archive **an object exhibits different behavior in different situation**

>>> **相同性質的類別及相同名稱Methods的行為，會依物件特性不同而有所不同，這個性質經常出現在介面實作以及抽象類別的覆寫(`@override`)上**

```java
public abstract Encryption{
    abstract void Encrypt()
}

/**
  *Encrypt()
  *  '---------> Caesar
  *  '---------> ASE 
  *  '---------> RSA
  * Caesar , ASE and RSA have their own encryption, 
  * that is we must override the method encrypt() from class Encryption
  */
```

### Abstraction(Hiding the Implementation)

**一個良好的OOP/OOD會隱藏所有實現(implementations)的DETAILS,把它的API與實現細節清楚地隔離開(Decoupling)**

In Java the programmer can implement Abstraction using concepts such as `abstract` and `interface`.     
1. Firstly,**an Abstract class can consist of abstract and non-abstract methods.** 
    > A class that extends an abstract class have to provide the implementations for the abstract methods. 
2. ~~Secondly,**an Interface is a collection of abstract methods.**~~ (After java 9 it allows for`private`/`default` implementation)
    

- **Abstraction is hiding the implementation(method) and Encapsulation is to hide data(member).**
- **Show only necessary thing to user that he required**, not extra information (use `public` 、`private` or `protected`).  
- abstraction helps to reduce the complexity of the system.

### Inheritances (Reusability)

**Inheritance enables you to create new classes that re-use (public/protected fields), extend and modify the behavior that is defined in existing classes**  

Inheritance is the methodology of using properties and methods of an already existing class(so called superclass or parent) in a new class(so called subclass or child).

>>> 透過繼承的方式，可以複製父物件所有`public`以及`protected`成員的功能，外界在存取子物件時也可以得到父物件的所有`public`以及`protected`成員的功能,而子物件本身也可以存取到父物件所public/protected成員的功能，或是進一步改變(`override`)父物件的行為

## Inheritance vs Abstraction

[What is the Difference Between Abstraction and Inheritance](https://reurl.cc/l5pQbY)  

The main difference between abstraction and inheritance is that **abstraction allows hiding the implementation details and displaying only the functionality to the users**, while **inheritance allows using PROPERTIES and METHODS of an already existing class** 

![image](https://user-images.githubusercontent.com/68631186/128846000-d2ae0501-6980-4a35-ad51-91f1a1390918.png)  

### `Is-A` Relationship Does Not Work In Reverse

- [Details For Liskov Substitution](https://www.jyt0532.com/2020/03/22/lsp/)  
- [Behavior and LSP](https://reurl.cc/eOrjdK)  
```java
/**
  *  Big -------------------------> Small
  */
GradedActivity activity = new GradedActivity();

/** 
  * SMALL <---- BIG 
  **/
FinalExam exam = activity;    // ERROR!

/**
  * The Compiler Works 
  * BUT failed in run-time
  */
GradedActivity activity = new GradedActivity();
FinalExam exam = (FinalExam) activity;    // ERROR!
```


## `finalize` keyword  

`finalize` to release the memory while doing Termination housekeeping in case of **Memory Leaks**  
- Similar with `delete` in Cpp

1. `finalize` method returns `void`
2. `finalize` method may not be executed even program reaches its end

## IMMUTABLE `final` keyword 

| TYPE          |                          |
|---------------|--------------------------|
|`final` class  | **can't be a Base Class**|
|`final` method | can't be OVERRIDDEN      |
|`final` field  | a CONSTANT field         |

### Final Value

Final Field Usually represents as UPPERCASE
```java
final T VAR;
```

### Final Class 

- The class must be declared as final so that child classes can’t be created.
- Fields in the class must be declared private so that direct access is not allowed.
- Fields in the class must be declared as `final` so that we can’t change the value of it after object creation.
- **A parameterized constructor should initialize all the fields performing a `deep copy` so that data members can't be modified with an object reference.**
Deep Copy of objects should be performed in the getter methods to return a copy rather than returning the actual object reference)

## Package & Namespace 

Java Package is kinda like `.h` in Cpp.  
```java
import java.util.*
```
Create a package in bash
```bash
javac -d . filename.java
```

### Class Loader
- [More Details](https://www.baeldung.com/java-classloaders)  
  
Compiler uses **Class Loader** to find the package and it search the package first from
1. search from JDK's standard library
2. search optional package
3. search from class' path (package list)

##  `abstract` keyword

* An abstract class is not instantiated, but other classes extend it.
* An abstract class method has no body and must be overridden in a subclass

```java
/**
  * <p> A Abstract Class </p>
  */
/*public or protected*/ abstract class Name{

  // A Abstract Method
  /*public or protected*/ abstract T method(...);

  //A Abstract VARIABLE
  /*final*/ K VAR = VALUE ;
}
```

### abstract class 

- [abstract class extends concrete class](https://stackoverflow.com/questions/20970980/abstract-class-extends-concrete-class)   
- [abstract class extends abstract class](https://stackoverflow.com/questions/6743584/can-one-abstract-class-extend-another-abstract-class-and-increase-functionality)


An abstract class may have `static` fields,`static` methods. 
  
You can use these static members with a class reference (for example, `AbstractClass.staticMethod()`) as you would with any other class.

### When an Abstract Class Implements an Interface

An abstract class **does not implement** all of the interface's method
```java 
abstract class X implements Y {
  // x methods of y are implemented
}
```

A class that implements an interface must implement all of the interface's methods. 
```java
class XX extends X { // class XX extends X implements Y
  // All of methods in interface Y must be implemented
  Here remaining methods of Y (All - x) must be implemented
}
```

### When an Abstract Class extends an Concrete Class (Inheritance)

```java
public class Animal{
  public final String name;
  public final int weight;

  public Animal(String name, int weight){ /* ... */ }
  public void jump() { /* ... */}
}

public abstract class Mammal extends Animal{
  // Constructors
  public Mammal(String name, int weight){
    // SuperClass need to be initialized
    super(name, weight);
  }

  public abstract void jumpMammal();

  @Override
  public final void jump(){
      jumpMammal();
  }
}
```
- Any class that extends `Mammal` is required to override the `jumpMammal()` method, therefore running in the `jump()` method.

### `IS-A` Hierarchies Hierarchy

Relationship中呼叫Method時，最先從該(Current)類別中查找看是否有對應的Method，如果没有才會從父類別去找，檢查是否從父類別繼承，都沒有就要進行強制轉型:

```java
Hierarchies Hierarchy
  A
  '--> B
       '-->C-->D

class A{
    public void show(A obj) {
        System.out.println("A.show(A)");
    }

    public void show(C obj) {
        System.out.println("A.show(C)");
    }
}

class B extends A {
    @Override
    public void show(A obj) {
        System.out.println("B.show(A)");
    }
}

class C extends B {
}

class D extends C {
}

public static void main(String[] args) {

    A a = new A();
    B b = new B();
    C c = new C();
    D d = new D();

    // A中存在show(A a) 
    a.show(a); //印出 : A.show(A)
    
    /** 
      * A中不存在show(B obj)
      * 將show(B obj)轉型成show(A obj)
      */
    a.show(b); 
    // 印出來自A的show(A obj) : A.show(A)
    
    // B存在繼承A的show(C obj)
    b.show(c); 
    // 印出來自A的show(C obj) : A.show(C)
    
    /** 
      * B中不存在show(D obj)
      * 但 D extends C -> C extends B 
           -> B存在繼承A的{@code show(C obj)}
      * 故 Convert D obj to C obj 
      */
    b.show(d); // A.show(C)

    A ba = new B(); // Reference to B object 
    ba.show(c); // A.show(C)
    ba.show(d); // A.show(C)
}
```

## `interface` keyword (`abstract`的延伸)
- **Java 8之前，它可以看成是一個完全ABCs(Class only with abstract methods)，也就是說它不能有任何的方法實作**，所以當某個Interface新增了新的方法，得要修改所有Implementations，讓它們都實作新增的方法[__MORE DETAILS](https://matthung0807.blogspot.com/2017/09/java-interfacedefault-methods.html)
    > An interface is an abstract Class that is used to group related methods with EMPTY bodies

-  Interface中的Methods都是`public` By Default，不允許定義為`protected`~~或`private`~~
    - Java 8後，允許將Method定義為`default`  
    - Java 9後，允許將Method定義為`private`，這樣就能定義某些REUSE的method又不會暴露出去 [__Example GeekForGeek](https://www.geeksforgeeks.org/private-methods-java-9-interfaces/)
- Fields are `static` or `final` by default      

| Interface | properties | method | 
|--|--|--|
| Before java 8| `final` variables, `static` variables |  `static` methods, `abstract` methods |
| After  java 8| same | + `default` methods  |
| After  java 9| same | + `private` methods, `private static` methods |

## Java 9 `private` and `private static`   
1. Private methods can be implemented `static` or non-static meaning that in an interface we are able to create private methods to encapsulate code from both `default` and `static` public method signatures.
2. `private` also means things done on the side, so the user can not see it(coz of encapsulation). That's why we call the public methods a public interface it's all the user will see from the outside.   
3. `private` is what won't be **reimplemented and accessed** by future programmers using our code.  

### Usage of the Private Method

Private methods will improve code re-usability inside interfaces and will provide choice to expose only our intended methods implementations to users.   
- **Private Methods are only accessible within that interface ONLY and cannot be accessed or inherited from an interface to another interface or class.**
- Private method in `interface` cannot be `abstract`.
- **Private method can be used(called) only inside `interface` and other `static` and non-static methods in the `interface`.**
- Private non-static methods cannot be used inside private static methods. (Same as `static` usage)
    - We should use `private` modifier to define these methods and no lesser accessibility than private modifier.

```java 
interface example{
    // Hide details on implementation 
    // from classes that `implement` the interface
    default usePrivateMethod(){
        privateMethod();
        StaticPrivateMethod();
    }
    
    private void privateMethod(){
        //...
    }
    static private void staticPrivateMethod(){
        // NO PRIVATE METHOD ALLOWED
    }
}
```
### Benefits of Private Methods in Interfaces

**封裝 + 減少相同的CODE**
- As a result, one of the main benefits of having private method in `interface`s is **encapsulation**.    
- Another benefit is (as with private methods in general) that there is **less duplication and more re-usable code added to interfaces for methods with similar functionality**.

## Interface & Abstract Class
- [Further Details](https://stackoverflow.com/questions/1913098/what-is-the-difference-between-an-interface-and-abstract-class)   


**A class/an Abstract class can extend one abstract class/class and implements multiple interfaces**
```java
class A extends classX implements interfaceBase1, interfaceBase2{
  // implements methods of interfaceBase1 and interfaceBase2 
}


abstract class Animal{
  //...
}
interface Pet{
  //..
}
                 // 共同部分     // 多形
public class Dog extends Animal implements Pet{
  //...
}
```

**Interface can `EXTEND` MULTIPLE Interfaces** 
```java
public interface derived extends InterfaceBase1, InterfaceBase2{
  // create new static variable , new abstract methods
}

// for example
public interface Sports {
   public void setHomeTeam(String name);
   public void setVisitingTeam(String name);
}

// Hockey has six methods (two from Sport)
public interface Hockey extends Sports {
   public void homeGoalScored();
   public void visitingGoalScored();
   public void endOfPeriod(int period);
   public void overtimePeriod(int ot);
}
```



| Abstract | Interface |
| -------- | --------- |
| can have constants, members, method stubs(methods without a body) and defined methods | private method, default method,final/static members, abstract method.
| **Methods and members of an abstract class can be defined with public/protected**| All methods of an interface can be defined as `public`, `private`, `static`, and `default`.
| When extending an abstract class, a child class must implement the abstract methods, whereas ***an abstract class can extend another abstract class and abstract methods from the parent class don't have to be implemented.*** | **an interface extending another interface is not responsible for implementing methods from the parent interface.**
| **A child class can only extend a single class (abstract or concrete)** | **an interface can extend or a class can implement multiple other interfaces**.
| A child class can define abstract methods **with the same or less restrictive visibility** | an implementation must define `public`, `private`, and `default` visibility


`abstract class` is `IS-A` Relationship，
- Follow Liskov Substitution Principle   

`interface` is `LIKE-A(HAS-A)` Relationship, it contains abstract methods. 
- There is no `IS-A` Relationship btw `interface` and implementations  

- **A Class can `IMPLEMENT` multiple interfaces but it can not `EXTEND` multiple abstract classes**

- `interface`'s Keyword has only `static` , `final` , `default`,`abstract class`'s has no constraint  
- `interface`'s Members have only `public`,`private/private static` or `default`, `abstract class`'s Members have different Access Controls  

### Use Interface or Abstract

**在多數的Cases，考慮`interface`應優先於`abstract`, 因為Interface沒有`abstract`嚴格的CLASS層次結構要求，可以更彈性地為Class添加更多行為**

Interface  
- 需要讓**UNRELATED**的類別都實現一個某種規範paradigm/model functionality
  - **e.g. 不相關的Classes都可以實現`Comparable`界面中的`compareTo()`方法**
- 需要使用多重繼承(Multiple Inheritances) e.g `interface a extends b, c`

Abstract Class
- RELATED(Reusability) : 繼承**性質相同**的類別 
- ACCESS CONTROL (Flexibility) : **需要能控制繼承來的成員的ACCESS CONTROL**
- 需要繼承非`static`和非`final`的FIELDS

```java
abstract class GraphicsObj{
  // A class Point
  protected Point Origin;
  
  public GraphicsObj{
    Origin = new Point(0,0);
  }
  
  public void moveTo(int newX, int newY){
    Origin.x = newX;
    Origin.y = newY;
  }
  
  // this should be implemented by the derived class
  public abstract void draw(); 
  
  // Garbage Collection
  protected void finalize() throw Throwable{
    Origin = null;
    super.finalize();
  }
}

/** 
  * <p> A interface </p>
  */
interface Paint
{
  public void fillColor(String color);
  public double getArea();
}
class Rectangle2 extends GraphicsObj implements Paint
{
    public void draw(){
      //..
    }
    
    /* Implementation of INTERFACE */
    @OVerride
    public void fillColor(String Color){
      //...
    }
    @Override
    public double getArea(){
      //...
    }
}
```

## `super`

如果子類別需要呼叫父類別Constructors，就需要利用`super()`呼叫或者當子類別override來自父類別某個method，可以通過使用`super`來呼叫被子類Overridden的方法

PS. **子類別一定會呼叫父類別的Constructor來完成初始化(一般是呼叫父類別Default Constructor)**

## `Override`

為了滿足LSP，Override有三個限制
- `Access-Specifier` : 子類方法訪問權限必須**`>=`**父類方法  
  (`public > protected > private`)
- `return` : 子類方法的返回類型必須是父類方法**相同**或為**其子類型**
- `throws` : 子類方法拋出的異常類型必須是**父類拋出異常類型**或為**其子類型**

**Annotation `@Override` : 可以讓Compiler幫忙檢查是否滿足上面的三個限制條件**
```java
// SubClass overrides SuperClass 的func()
class SuperClass {
    protected List<Integer> func() throws Throwable {
        return new ArrayList<>();
    }
}
class SubClass extends SuperClass {
    
    // ArrayList < List
    @Override
    public ArrayList<Integer> func() throws Exception {
        return new ArrayList<>();
    }
}
```
- 子類的Methods訪問權限為`public`，大於父類的`protected`
- Sub的`@return`為`ArrayList<Integer>`，是父類`List<Integer>`的子類。
- 子類拋出`@throws`的異常類型為`Exception`，是父類拋出異常`Throwable`的子類。

## Dynamic Binding 

When a superclass variable references a subclass object, a potential problem exists
- E.g :: if the subclass has overridden a method in the superclass, and an instance makes a call to which method? Which Method it will call ? Method from superclass or subclass ?

Unlike Static Binding calling functions at the compiler time, Dynamic Binding only checks the type of object if it can be called or not
```java
class Example{
  public static void main(String args[])
  {
    Employee EmployeeA = new Employee();
    Employee EngineerB = new Engineer();
    EmployeeA.getSalary(); 
    /**
      * getSalary() 
      * in Class Engineer or in Class Employee?
      * Dynamic Binding checks that for us 
      */
    EngineerB.getSalary();
  }
}
```

```java
class Example{
  public static void main()
  {
    Employee e = new Manager();
    e.getSalary();
    e.doAccounting(); // Compiler Error (e doesn't have doAccounting methods)
  }
}
```

At Compile-Time (Existence)
- [x] 1. Check if `Employee` exists 
- [x] 2. Check if `Manager` is subclass of `Employee`
- [x] 3. Check if `Employee e` has `getSalary()` method
- [x] 4. Check if `Employee e` has `doAccounting()` method

At Run-Time (Check Overridden Function and References)
- [x] 1. (Check the Reference) `Employee e` references to an `Manager` class;
- [x] 2. (Check the Overridden Function) `Manager` class has its own `getSalary()` method 


### Overload

A Class contains same name methods with at least some difference(parameter, order of parameters or counts of parameter...etc), for example
```java
class OverloadingExample {
    
    public void show(int x) {
        System.out.println(x);
    }

    public void show(int x, String y) {
        System.out.println(x + " " + y);
    }
}

public static void main(String[] args) {
    OverloadingExample example = new OverloadingExample();
    example.show(1);
    example.show(1, "2");
}
```


## Anonymous Inner Classes

> Sometimes the programmer needs a class that is SIMPLE, and to be instantiated only once in the code. 

**An anonymous inner class is an inner class that has no name.**
> An inner class is a class that is defined inside another class. 

1. An anonymous inner class must either implement an interface, or extend another class.
2. If the anonymous inner class extends a superclass, **the superclass’s no-arg constructor is called when the object is created.**
3. An anonymous inner class must **OVERRIDE all of the abstract methods specified by the interface it is implementing, or the superclass it is extending.**
4. Because an anonymous inner class’s definition is written inside a method, it can access that method’s local variables, but only if they are declared `final`, or if they are effectively `final`(An effectively `final` variable is a variable whose value is never changed.). 
    - A compiler error will result if an anonymous inner class tries to use a variable that is not final, or not effectively final.

```java
public class Example{
    public static void main(String[] args)
    {
        int num;
        
        Scanner keyboard = new Scanner(System.in);
        
        // Anonymous Inner Classes
        IntCalculator square = new IntCalculator(){
            public int calculate(int number){
                return number * number;
            }
        };
        
        System.out.println("Enter an integer Number: ");
        num = keyboard.nextInt();
        
        // USER INNER CLASS OBJECT
        System.out.println("The square is " + square.calculate(num));
    }
}
```


