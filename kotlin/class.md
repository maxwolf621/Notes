# class

[GiveMePasS's Android惡補筆記](https://givemepass.blogspot.com/2020/02/delegated-properties.html)
[Kotlin使用心得（九）：委託（Delegate](https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E4%B9%9D-%E5%A7%94%E8%A8%97-delegate-f219428d6ff7)

- [class](#class)
  - [Constructor](#constructor)
    - [`init`](#init)
    - [Companion objects](#companion-objects)
  - [Inheritance (`open`) & (`final`)](#inheritance-open--final)
  - [class properties](#class-properties)
    - [getter and setter](#getter-and-setter)
    - [Private Set](#private-set)
    - [Injection](#injection)
    - [Backing fields](#backing-fields)
    - [Custom Setter and Getter](#custom-setter-and-getter)
    - [lateinit & @SetUp & @Test](#lateinit--setup--test)
    - [`lateinitVariable.isInitialized`](#lateinitvariableisinitialized)
    - [Compile-Time Constants](#compile-time-constants)
  - [interface](#interface)
    - [Interfaces Inheritance](#interfaces-inheritance)
    - [Resolving overriding conflicts](#resolving-overriding-conflicts)
  - [SAM functional interface (prepare for lambda)](#sam-functional-interface-prepare-for-lambda)
    - [SAM conversions](#sam-conversions)
    - [callable references to functional interface constructors](#callable-references-to-functional-interface-constructors)
    - [typealias vs functional array](#typealias-vs-functional-array)
  - [visibility](#visibility)
    - [Package](#package)
    - [Class](#class-1)
    - [private primary constructor](#private-primary-constructor)
  - [Extensions](#extensions)
    - [Nullable receiver (`?.`)](#nullable-receiver-)
    - [Scope of package extensions](#scope-of-package-extensions)
    - [Declaring extensions as members](#declaring-extensions-as-members)
  - [Data Class](#data-class)
    - [copy the class](#copy-the-class)
  - [Nested and inner classes](#nested-and-inner-classes)
  - [Generics: in, out, where](#generics-in-out-where)
    - [type projection](#type-projection)
    - [start-projections](#start-projections)
    - [Upper bounds (`<T extend J>`)](#upper-bounds-t-extend-j)
    - [`_`](#_)
  - [TypeAlias](#typealias)
  - [Delegation (`by`)](#delegation-by)
    - [Delegation Properties](#delegation-properties)
    - [Observable properties](#observable-properties)
    - [Storing properties in a map](#storing-properties-in-a-map)
    - [`::` qualifier](#-qualifier)
    - [`@Deprecated`](#deprecated)
  - [`@JvmInline` inlined class](#jvminline-inlined-class)
    - [Inline classes vs type aliases](#inline-classes-vs-type-aliases)
    - [Inline classes and delegation](#inline-classes-and-delegation)

**Kotlin dose not have `new` keyword**

## Constructor 

**If the primary constructor does not have any annotations or visibility modifiers, the `constructor` keyword can be omitted:**
```java
class Person constructor(_firstName : String){
    var firstName = _firstName;
}

// omit the constructor keyword for primary constructor
class Person(firstName: String) { /*...*/ }

           // with trailing comma ,
class Person(val firstName: String,) { /*...*/ }
```


```java
// also call data class (only data exists)
class DataClassWithMandatoryFields(
    val name: String,
    val surname: String,
    val age: Number
)
// initializer
val objectWithAllValuesProvided = DataClassWithMandatoryFields("John", "Deere", 82)

// a new data class where the fields aren’t mandatory (optional members)
class DataClassWithNullInitializedFields(
    val name: String? = null,
    val surname: String? = null,
    val age: Number? = null
)
// each field must check for nullable
assertThat(objectWithNameInitializedFields.name?.length).isEqualTo("4")


// default value
class DataClassWithDefaultValues(
    val name: String = "",
    val surname: String = "",
    val age: Number = Int.MIN_VALUE
)
val dataClassWithNameProvided = DataClassWithDefaultValues(name = "John")

// Secondary Constructors
class DataClassWithSecondaryConstructors(
    val name: String,
    val surname: String,
    val age: Number
) {
    constructor() : this("", "Doe", Int.MIN_VALUE)
    constructor(name: String) : this(name, "Deere", Int.MIN_VALUE)
    constructor(name: String, surname: String) : this(name, surname, Int.MIN_VALUE)
}
```
- If a non-abstract class does not declare any constructors (primary or secondary), it will have a generated primary constructor with no arguments. 
- The visibility of the constructor will be `public`
- You can override a non-abstract `open` member with an abstract one.

### `init`

you can test the code in `init` block
- Primary constructor parameters can be used in the initializer blocks.      
- They can also be used in property fields initializers declared in the class body   
```java
class InitOrderDemo(name: String) {   

    constructor(name : String , val : Int): this(name = name){
        println("I'm Secondary Constructor");
    }

    val firstProperty = "First property: $name".also(::println)        
    init {        
        println("First initializer block that prints $name")    
    }
    
    val secondProperty = "Second property: ${name.length}".also(::println)       
    init {       
        println("Second initializer block that prints ${name.length}")   
    }
}

fun main() {
    val demo = InitOrderDemo("Jian");
}
/**
    First property: Jian
    First initializer block that prints Jian
    Second property: 4
    Second initializer block that prints 4
*/
```

### Companion objects

If you need to write a function that can be called without having a class instance but that needs access to the internals of a class (such as a factory method), you can write it as a member of an object declaration inside that class.Even more specifically, if you declare a companion object inside your class, you can access its members using only the class name as a qualifier.
```java
class MyView : View { constructor(ctx: Context) : super(ctx) constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) }
```

## Inheritance (`open`) & (`final`)

A member marked override is itself `open`, so it may be overridden in subclasses. 

If you want to prohibit re-overriding, use `final`:
```java
class Example // Implicitly inherits from Any
open class Base // Class is open for inheritance
```

If the derived class has a primary constructor, the base class can (and must) be initialized in that primary constructor according to its parameters.  

If the derived class has no primary constructor, then each secondary constructor has to initialize the base type using the `super` keyword or it has to delegate to another constructor which does.      
```java
// assign value to super
open class Base(p: Int)
class Derived(p: Int) : Base(p)
```

```java
// secondary constructors 
// can call different constructors of the base type:
class MyView : View {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}
```

**Kotlin requires explicit modifiers (`open`) for overridable members and overrides**:
```java
open class Shape {
    open fun draw() { /*...*/ }
    fun fill() { /*...*/ }
}

class Circle() : Shape() {
    override fun draw() { /*...*/ }
}
```

```java
open class Shape {
    open val vertexCount: Int = 0
}

class Rectangle : Shape() {
    override val vertexCount = 4
}
```

Note that you can use the override keyword as part of the property declaration in a primary constructor:
```java
interface Shape {
    val vertexCount: Int
}

class Rectangle(override val vertexCount: Int = 4) : Shape // Always has 4 vertices

class Polygon : Shape {
    override var vertexCount: Int = 0  // Can be set to any number later
}
```
## class properties 

### getter and setter

Initializer of fields with `?` , `val`, nd `var`
```java 
// it has type Int, default getter, 
// must be initialized in constructor
val simple: Int? 

// it has type Int and a default getter (val : final type)
val inferredType = 1

// it has type Int, default getter and setter
var initialized = 1 
```

`val` variable's custom getter
- You can omit the property type if it can be inferred from the getter:
```java
// custom getter
class Rectangle(val width: Int, val height: Int) {
    val area: Int 
        get() = this.width * this.height
}
fun main() {
    val rectangle = Rectangle(3, 4)
    println("Width=${rectangle.width}, height=${rectangle.height}, area=${rectangle.area}")
}
```

`var` variable's custom getter / setter
```java
// custom setter (is called every time the value is assigned)
var stringRepresentation: String
    get() = this.toString()
    set(value) {
        setDataFromString(value) 
    }
```
- If you define a custom setter, it will be called every time you assign a value to the property, except its initialization.

### Private Set 

```java
class privateSetting{
    var privateMember: String = "abc"
    private set 
    // the setter is private 
    // and has the default implementation
    fun setPrivateMember(s : String){
        this.privateMember = s;
        println("Setting privateMember to ${s}")
    }
}

val privateSettingObj = privateSetting();
privateSettingObj.setPrivateMember("adb");
// Setting privateMember to adb
```

### Injection

```java
var setterWithAnnotation: Any? = null
    @Inject set // annotate the setter with Inject
```

### Backing fields

- [What's Kotlin Backing Field For?](https://stackoverflow.com/questions/43220140/whats-kotlin-backing-field-for)

In Kotlin, a field is only used as a part of a property to hold its value in memory.

Fields cannot be declared directly. 
 
However, when a property needs a backing field, Kotlin provides it automatically.   

This backing field can be referenced in the accessors using the field identifier:  
```java
class timer{
    var counter : Int = 0 // the initializer assigns the backing field directly
    // get() = field;
    set(value) {
        if (value >= 0)
            field = value
            // counter = value 
            // ERROR StackOverflow: 
            // Using actual name 'counter' would make setter recursive
    }
}

fun main(){
    val t = timer();
    t.counter = 4
    println(t.counter);
}
```
- **The `field` identifier can only be used in the accessors of the property.**
- B`acking Field (field)`: It allows storing the property value in memory possible. When we initialize a property with a value, the initialized value is written to the backing field of that property. In the above program, the value is assigned to field, and then, field is assigned to `get()`.


### Custom Setter and Getter 

- [source code](https://www.geeksforgeeks.org/kotlin-setters-and-getters/)

```java
class Registration( email: String, pwd: String, age: Int , gender: Char) {
 
    var email_id: String = email
        // Custom Getter
        get() {
           return field.toLowerCase()
        }
    var password: String = pwd
        // Custom Setter
        set(value){
            field = if(value.length > 6) value else throw IllegalArgumentException("Passwords is too small")
        }
 
    var age: Int = age
        // Custom Setter
        set(value) {
            field = if(value > 18 ) value else throw IllegalArgumentException("Age must be 18+")
        }
    var gender : Char = gender
        // Custom Setter
        set (value){
            field = if(value == 'M') value else throw IllegalArgumentException("User should be male")
        }
}
```

### lateinit & @SetUp & @Test

- [details](https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E5%8D%81%E4%B8%80-lateinit-vs-lazy-1ef96bc5b3b3)

Properties declared as having a non-null type must be initialized in the constructor in kotlin.

`lateinit` allows **properties can be initialized through dependency injection**, or in the setup method of a unit test. 
- This modifier can be used on `var` properties declared inside the body of a class (**not in the primary constructor**, and only when the **property does not have a custom getter or setter**), as well as for top-level properties and local variables. 

**The type of the property or variable must be non-null, and it must not be a primitive type**.

**Accessing a `lateinit` property before it has been initialized throws a special exception.** 
```java
public class MyTest {

    // private var subject : TestSubject? = null
    lateinit var subject: TestSubject

    @SetUp fun setup() {
        // setter
        subject = TestSubject();
    }

    @Test fun test() {
        // ...
    }
}
```
### `lateinitVariable.isInitialized` 

Checking whether a lateinit var is initialized

```java
if (foo::bar.isInitialized) {
    println(foo.bar)
}
```


### Compile-Time Constants 

https://givemepass.blogspot.com/2020/01/properties-fields.html

## interface

Interfaces in Kotlin can contain **declarations of abstract methods, as well as method implementations**. 
Interfaces can have **properties(NO STATE)**, but these need to be abstract or provide accessor implementations.

A class or object can **implement one or more interfaces**  

Properties declared in interfaces **can't have backing fields**
```java
interface MyInterface {
    val prop: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}

class Child : MyInterface {
    override val prop: Int = 29
}
```

### Interfaces Inheritance

An interface can derive from other interfaces, meaning it can both provide implementations for their members and declare new functions and properties. 

Classes implementing such an interface are **only required to define the missing implementations**:
```java
interface Named {
    val name: String
}

// only required to define the missing implementations
interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName $lastName"
}

// only required to define the missing implementations
data class Employee(
    // implementing 'name' is not required
    override val firstName: String,
    override val lastName: String,
    
    val position: Position
) : Person
```

### Resolving overriding conflicts

Interfaces A and B both declare functions `foo()` and `bar()`.
  
```java
interface A {
    fun foo() { print("A") }
    fun bar()                      
}                                     
                                      
interface B {                         
    fun foo() { print("B") }     
    fun bar() { print("bar") }
}

class C : A {
    // you have to override bar() and provide an implementation.
    override fun bar() { print("bar") } 
}

// you need to implement all the methods
// that you have inherited from multiple interfaces
class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}
```

This rule applies both to methods for which you've inherited a single implementation (`bar()`) and to those for which you've inherited multiple implementations (`foo()`).

## SAM functional interface (prepare for lambda) 

An interface with only one abstract method is called a functional interface, or a Single Abstract Method (SAM) interface.

```java
fun interface KRunnable {
   fun invoke()
}
```

### SAM conversions

With a SAM conversion, Kotlin can convert any lambda expression whose signature matches the signature of the interface's single method into the code, which dynamically instantiates the interface implementation.

```java
fun interface IntPredicate {
   fun accept(i: Int): Boolean
}

// Creating an instance of a class
val isEven = object : IntPredicate {
   override fun accept(i: Int): Boolean {
       return i % 2 == 0
   }
}

// with conversion
val isEven = IntPredicate { it % 2 == 0 }
```

### callable references to functional interface constructors

```java
interface Printer {
    fun print()
}

//          parameter           returnType         anonymous              
fun Printer(block: () -> Unit): Printer = object : Printer { override fun print() = block() }
```

With callable references to functional interface constructors enabled.

Its constructor will be created implicitly.
```java
// any code using the ::Printer function reference will compile. 
documentsStorage.addPrinter(::Printer)
```

### typealias vs functional array

```java
/**
 * Instead 
    interface Printer {
        fun print()
    }
 */
typealias IntPredicate = (i: Int) -> Boolean

val isEven : IntPredicate = { it % 2 == 0 }

fun main() {
   println("Is 7 even? - ${isEven(7)}")
}
```

**Type aliases can have only one member, while functional interfaces can have multiple non-abstract members and one abstract member.**   
Functional interfaces can also implement and extend other interfaces.

**Functional interfaces are more flexible and provide more capabilities than type aliases**, but they can be more costly both syntactically and at runtime because they can require conversions to a specific interface. 

Consider your needs :
- If your API needs to accept a function (any function) with some specific parameter and return types – use a simple functional type or define a type alias to give a shorter name to the corresponding functional type.
- If your API accepts a more complex entity than a function – for example, it has non-trivial contracts and/or operations on it that can't be expressed in a functional type's signature – declare a separate functional interface for it.

## visibility

Local variables, functions, and classes can't have visibility modifiers.

### Package

```java
// file name: example.kt
package foo

private fun foo() { ... } // visible inside example.kt

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in example.kt
internal val baz = 6    // visible inside the same module
```
### Class

```java
open class Outer {
    private val private_a = 1

    protected open val protected_b = 2

    internal open val intern_c = 3

    val val_d = 4  // public by default

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Method `Nested` and member `e` are visible

    override val b = 5   // 'b' is protected
    override val c = 7   // 'c' is internal
}

class Unrelated(o: Outer) {
    // o.private_a, o.protected_b are not visible (private and protected)
    // o.intern_c and o.val_d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either
}
```

### private primary constructor

specify the visibility of the primary constructor of a class

```java
class C private constructor(a: Int) { ... }
```

## Extensions

Kotlin provides the ability to extend a class or an interface with new functionality without having to inherit from the class or use design patterns such as Decorator.
```java
fun extension.methodName(...){
    ....
}
```

adds a `swap` function to `MutableList<T>`
```java
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

### Nullable receiver (`?.`)

```java
fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is auto-cast to a non-null type, 
    // so the toString() below
    // resolves to the member function of the Any class
    return toString()
}

fun main() {
    var obj: Any? = "i dont like it"

    print(obj?.toString()) // is actually null
    print(obj.toString()) // returns "null" string
}
```

### Scope of package extensions

An extension declared at the top level of a file has access to the other private top-level declarations in the same file.

If an extension is declared outside its receiver type, it cannot access the receiver's `private` or `protected` members.

Inside its declaring package (top level of a file)
```java
package org.example.declarations;

fun List<String>.getLongestString() : List<String> {
    for( i in this){
        println(i)
        /**
            red
            green
            blue
        */
    }
    return this;
}
```

To use an extension outside its declaring package, import it at the call site:
```java
package org.example.usage;
import org.example.declarations.getLongestString;

fun main() {
    val list = listOf("red", "green", "blue")
    list.getLongestString()
}
```

### Declaring extensions as members 

```java
class Host(val hostname: String) {
    fun printHostname() { print(hostname) }
}

class Connection(val host: Host, val port: Int) {
    
    fun Host.getConnectionString() {
        println(toString())                  // calls Host.toString()
        println(this@Connection.toString())  // calls Connection.toString()
    }

    fun call(){
        host.getConnectionString() 
    }
}

fun main() {
    Connection(Host("kotlin"), 443).call()
}
```

## Data Class 

Data Class whose main purpose is to hold data

Automatically derives
```java
equals()/hashCode()/toString() 
componentN() // functions corresponding to the properties in their order of declaration.
copy()  
```

To ensure consistency and meaningful behavior of the generated code, data classes have to fulfill the following requirements:
- The primary constructor needs to have at least one parameter.
- All primary constructor parameters need to be marked as `val` or `var`.
- Data classes cannot be `abstract`, `open`, `sealed`, or `inner`.

```java
// they will be treated as equal.
data class Person(val name: String) {
    var age: Int = 0
}

val person1 = Person("John")
val person2 = Person("John")
person1.age = 10
person2.age = 20

// destructing 
val jane = Person("Jane", 35)
val (name, age) = jane
println("$name, $age years of age") // prints "Jane, 35 years of age"
```


### copy the class

```java
data class Person(val name: String) {
    var age: Int = 0
    // Custom Copy
    fun copy(name: String = this.name, age: Int = this.age) = Person(name)
}
fun main() {
    val jack = Person(name = "Jack")
    val olderJack = jack.copy(age = 2)
}

/**
    Default Copy : 
	val jack = Person("jack");
    jack.age = 18;
    
    println("jack ${jack.name} and ${jack.age}");
    val copy = jack.copy(name = "jackColone");
    copy.age = 18;
    println("${copy.name} and ${copy.age}");
**/
```

## Nested and inner classes

- Anonymous inner classes
- `Inner` classes
  - Inner classes carry a reference to an object of an outer class:
- `class` within a `class`
- interface within a `class`

```java
// inner
class Outer {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

val demo = Outer().Inner().foo() // == 1

// Anonymous inner class instances are created using an object expression:
window.addMouseListener(object : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
})
```

## Generics: in, out, where

### type projection

`Array<out Any>` : Corresponds to Java's `Array<? extends Any>` 
```java
fun copy(from: Array<out Any>, to: Array<Any>) { ... }
```
- out : `from`'s data type is SMALLER THAN Any

`Array<in String>` corresponds to Java's `Array<? super String>`. 
```java
fun fill(dest: Array<in String>, value: String) { ... }
```
- `dest`'s data Type is BIGGER THAN STRING

### start-projections

**Sometimes you want to say that you know nothing about the type argument, but you still want to use it in a safe way.** 

The safe way here is to define such a projection of the generic type, that every concrete instantiation of that generic type will be a subtype of that projection.

For `Foo<out T : TUpper>`
- where `T` is a covariant type parameter with the upper bound `TUpper`, `Foo<*>` is equivalent to `Foo<out TUpper>`.   
This means that when the `T` is unknown you can safely read values of `TUpper` from `Foo<*>`.

For `Foo<in T>`, where `T` is a contravariant type parameter, `Foo<*>` is equivalent to `Foo<in Nothing>`. 
- This means there is nothing you can write to `Foo<*>` in a safe way when `T` is unknown.

If a generic type has several type parameters, each of them can be projected independently.    
For example, if the type is declared as interface Function<in T, out U> you could use the following star-projections:

- `Function<*, String>` means `Function<in Nothing, String>.`
- `Function<Int, *>` means `Function<Int, out Any?>.`
- `Function<*, *>` means `Function<in Nothing, out Any?>.`

### Upper bounds (`<T extend J>`)

The default upper bound (if there was none specified) is `Any?`
```java
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
    where T : CharSequence,
          T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}
```
- `T` type must implement both `CharSequence` and `Comparable`.

### `_`

```java
abstract class SomeClass<T> {
    abstract fun execute() : T
}

class SomeImplementation : SomeClass<String>() {
    override fun execute(): String = "Test"
}

class OtherImplementation : SomeClass<Int>() {
    override fun execute(): Int = 42
}

object Runner {
    inline fun <reified S: SomeClass<T>, T> run() : T {
        return S::class.java.getDeclaredConstructor().newInstance().execute()
    }
}

fun main() {
    // T is inferred as String because SomeImplementation derives from SomeClass<String>
    val s = Runner.run<SomeImplementation, _>()
    assert(s == "Test")

    // T is inferred as Int because OtherImplementation derives from SomeClass<Int>
    val n = Runner.run<OtherImplementation, _>()
    assert(n == 42)
}

```

## TypeAlias

(kidda like `type` in typescript)

Type aliases provide alternative names for existing types  
It's useful to shorten long generic types.  
You can provide different aliases for function types
You can have new names for inner and nested classes
```java
// Generics
typealias NodeSet = Set<Network.Node>
typealias FileTable<K> = MutableMap<K, MutableList<File>>

// function types
typealias MyHandler = (Int, String, Any) -> Unit
typealias Predicate<T> = (T) -> Boolean

// class
class A {
    inner class Inner
}
class B {
    inner class Inner
}
typealias AInner = A.Inner
typealias BInner = B.Inner
```

```java 
typealias Predicate<T> = (T) -> Boolean
```
When you add typealias `Predicate<T>` and use `Predicate<Int>` in your code, the Kotlin compiler always expands it to `(Int) -> Boolean`. 

Thus you can pass a variable of your type whenever a general function type is required and vice versa:
```java ​
typealias Predicate<T> = (T) -> Boolean

// foo(p : (T) -> Boolean) 
fun foo(p: Predicate<Int>) = p(42)
​
fun main() {
    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f)) // prints "true"

    val p: Predicate<Int> = { it > 0 }
    println(listOf(1, -2).filter(p)) // prints "[1]"
```


## Delegation (`by`)

The Delegation pattern has proven to be a good alternative to implementation inheritance(減少OVERRIDE), and Kotlin supports it natively requiring zero boilerplate code.

`className by arg`

```java
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(b: Base) : Base by b  // <--- WITH Delegation NO NEED TO WRITE METHOD TO CALL print()

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()   // delegate by BASE
}

interface Base {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived(b: Base) : Base by b {
    
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()  // delegate BaseImpl.println -> BaseImpl: x = 10
    println(derived.message) // print Message of Derived
}
```

### Delegation Properties

- [Local delegated properties](https://kotlinlang.org/docs/delegated-properties.html#local-delegated-properties)   
- [Translation rules for delegated properties](https://kotlinlang.org/docs/delegated-properties.html#translation-rules-for-delegated-properties)  
- [Providing a delegate](https://kotlinlang.org/docs/delegated-properties.html#providing-a-delegate)

如果今天有一個屬性是許多類別需要的，最簡單的方法就是每個類別都寫這個屬性，並且每個類別都初始化這個屬性，這樣一來，就會變成一種浪費，因為每個類別都寫相同屬性不但程式會變得很多餘，而且一旦有一天需要修改的時候，你會不知道還有哪幾個類別是需要一起修改，而修改就會造成邊際效應。
- 利用 Delegated properties，將properties管理在同一個類別

```java
val/var <property name>: <Type> by delegate.
```

A delegate should has two method `getValue` and `setValue`
```java
import kotlin.reflect.KProperty

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
```

Delegatee Mode
- (FIRST TIME) Lazy properties: the value is computed only on first access. (默認)
By default, the evaluation of lazy properties is `synchronized`: the value is computed only in one thread, but all threads will see the same value.
- (CHANGES) Observable properties: listeners are notified about changes to this property.    
- (MAP) Storing properties in a map instead of a separate field for each property.    


### Observable properties

`by Delegates.observable()` takes two arguments: the initial value and a handler for modifications.  
It has three parameters: the property being assigned to, the old value, and the new v

If you want to intercept assignments and veto them, use `vetoable()` instead of `observable()`.     
The handler passed to `vetoable()` will be called before the assignment of a new property value.   
```java
import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}

fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
}
/**
<no name> -> first
first -> second
*/
```

### Storing properties in a map

**This comes up often in applications for things like parsing JSON or performing other dynamic tasks.** 

In this case, you can use the map instance itself as the delegate for a delegated property.
```java 
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))
```

### `::` qualifier

A property can delegate its getter and setter to another property.  
Such delegation is available for **both top-level and class properties (member and extension).**

To delegate a property to another property, use the `::` qualifier in the delegate name, for example, `this::delegate` or `MyClass::delegate`.

The delegate property can be:
- A top-level property ( `T by ::topLevelT`)
- A member or an extension property of the same class (`T by this::memberT`)
- A member or an extension property of another class (`T by anotherClassInstance::anotherClassT`)
```java
var topLevelInt: Int = 0

class ClassWithDelegate(val anotherClassInt: Int)

class MyClass(var memberInt: Int, val anotherClassInstance: ClassWithDelegate) {
    
    var delegatedToMember: Int by this::memberInt
    var delegatedToTopLevel: Int by ::topLevelInt

    val delegatedToAnotherClass: Int by anotherClassInstance::anotherClassInt
}

var MyClass.extDelegated: Int by ::topLevelInt
```

```java
//                 [field's name , value] 
class User(val map: Map<String, Any?>) {
    // fields 
    val name : String by map
    val age : age by map
}

val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))

// or mutable
class MutableUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int     by map
}
```
### `@Deprecated`

when you want to RENAME a property in a backward-compatible way: introduce a new property, annotate the old one with the `@Deprecated` annotation, and delegate its implementation.

```java
class MyClass {
   var newName: Int = 0
   @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
   var oldName: Int by this::newName
}
fun main() {
   val myClass = MyClass()
   // Notification: 'oldName: Int' is deprecated.
   // Use 'newName' instead
   myClass.oldName = 42
   println(myClass.newName) // 42
}
```
## `@JvmInline` inlined class

**Inline class properties cannot have backing fields.**   

They can only have simple computable properties (no lateinit/delegated properties).
```java
@JvmInline
value class Name(val s: String) {
    init {
        require(s.length > 0) { }
    }

    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }
}

fun main() {
    val name = Name("Kotlin")
    name.greet() // method `greet` is called as a static method
    println(name.length) // property getter is called as a static method
}

// inheritance
interface Printable {
    fun prettyPrint(): String
}

@JvmInline
value class Name(val s: String) : Printable {
    override fun prettyPrint(): String = "Let's $s!"
}

fun main() {
    val name = Name("Kotlin")
    println(name.prettyPrint()) // Still called as a static method
}
```


### Inline classes vs type aliases

inline classes introduce a truly new type, contrary to type aliases which only introduce an alternative name (alias) for an existing type:
```typescript 
typealias NameTypeAlias = String

@JvmInline
value class NameInlineClass(val s: String)

fun acceptString(s: String) {}
fun acceptNameTypeAlias(n: NameTypeAlias) {}
fun acceptNameInlineClass(p: NameInlineClass) {}

fun main() {
    val nameAlias: NameTypeAlias = ""
    val nameInlineClass: NameInlineClass = NameInlineClass("")
    val string: String = ""

    acceptString(nameAlias) // OK: pass alias instead of underlying type
    acceptString(nameInlineClass) // Not OK: can't pass inline class instead of underlying type

    // And vice versa:
    acceptNameTypeAlias(string) // OK: pass underlying type instead of alias
    acceptNameInlineClass(string) // Not OK: can't pass underlying type instead of inline class
}
```

### Inline classes and delegation

[Inline classes and delegation](https://kotlinlang.org/docs/inline-classes.html#inline-classes-and-delegation)