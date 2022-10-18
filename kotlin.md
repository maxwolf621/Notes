# Kotlin && Typescript

- [Kotlin && Typescript](#kotlin--typescript)
  - [void Type](#void-type)
  - [variable with null type](#variable-with-null-type)
  - [Destructing Declaration](#destructing-declaration)
  - [flow](#flow)
    - [kotlin special control flow `when`](#kotlin-special-control-flow-when)
  - [function construction](#function-construction)
  - [Kotlin](#kotlin)
    - [`lateinit`](#lateinit)
    - [kotlin single line function (`=`)](#kotlin-single-line-function-)
    - [`?`](#)
    - [`data`](#data)
    - [`when` & range (`..`) & `else`](#when--range---else)
    - [` v() ?: return`](#-v--return)
    - [Trailing Comma (constructor/function)](#trailing-comma-constructorfunction)
    - [`vararg` & `*`](#vararg--)
    - [Member function](#member-function)
    - [Single-expression functions](#single-expression-functions)
    - [Unit (void) returning type](#unit-void-returning-type)
    - [default value in fun/constructor](#default-value-in-funconstructor)
    - [`open`](#open)
    - [Class Constructor & `init`](#class-constructor--init)
    - [getter and setter](#getter-and-setter)
    - [Interface](#interface)
    - [Conflict](#conflict)
  - [typealias (same as typescript type)](#typealias-same-as-typescript-type)
  - [Single Abstract Method (lambda)](#single-abstract-method-lambda)
  - [Delegation (`by`)](#delegation-by)
  - [Delegation Properties](#delegation-properties)
    - [Observable properties](#observable-properties)
    - [Storing properties in a map](#storing-properties-in-a-map)
    - [`::` qualifier](#-qualifier)
    - [`@Deprecated`](#deprecated)
  - [Function](#function)

Kotlin and Typescript both has Type Interference

- the variable must either have a type annotation or be initialized
```java
//         DataType is always uppercase
var name : DataType

// var name ; <-- wrong
```

```typescript
let   name : type ; 
const name : dataType[];
```

## void Type

```typescript
// With strictNullChecks set to true
let a: void = undefined; // Ok
let b: void = null; // Error
let c: void = 3; // Error
let d: void = "apple"; // Error
 
// With strictNullChecks set to false
let a: void = undefined; // Ok
let b: void = null; // Ok
let c: void = 3; // Error
let d: void = "apple";  // Error
```

## variable with null type

Typescript
```typescript
// With strictNullChecks set to true
let a: null = null; // Ok
let b: undefined = null; // Error
let c: number = null; // Error
let d: void = null; // Error
 
// With strictNullChecks set to false
let a: null = null; // Ok
let b: undefined = null; // Ok
let c: number = null; // Ok
let d: void = null; // Ok

// With strictNullChecks set to true
let a: undefined = undefined; // Ok
let b: undefined = null; // Error
let c: number = undefined; // Error
let d: void = undefined; // Ok
 
// With strictNullChecks set to false
let a: undefined = undefined; // Ok
let b: undefined = null; // Ok
let c: number = undefined; // Ok
let d: void = undefined; // Ok
```

kotlin
```

```


## Destructing Declaration


```java 
val person = Person("Adam", 100)
val (name, age) = person

val pair = Pair(1, 2)
val (first, second) = pair

val coordinates = arrayOf(1, 2, 3)
val (x, y, z) = coordinates
```

## flow 

for (i in 0..10) { } // 1 - 10
for (i in 0 until 10) // 1 - 9
(0..10).forEach { }
for (i in 0 until 10 step 2) // 0, 2, 4, 6, 8


### kotlin special control flow `when`

kidda like `switch`

```java
when (direction) {
    NORTH -> {
        print("North")
    }
    SOUTH -> print("South")
    EAST, WEST -> print("East or West")
    "N/A" -> print("Unavailable")
    else -> print("Invalid Direction")
}
```


## function construction



## Kotlin

### `lateinit`

With `lateint` I assure this variable will be assigned later
### kotlin single line function (`=`)

```java 
fun getRepository(): Repository {
    return repository
}

fun getRepository(): Repository = repository
```

### `?`

With `?` : Variable might be null

```java
val cannotBeNull: String = null // Invalid
val canBeNull: String? = null // Valid
```

### `data`

Generate an `equals`, `hashCode` and `toString` function automatically (along with `copy()`).
```java
 data class Dog(
    val name: String,
    val owner: Person
)

// only equals/hashCode are generated
@Parcelize
data class LoginKey(val placeholder: String = ""): Parcelable
```

### `when` & range (`..`) & `else`

```java
fun numberTypename(x : Number) = when(x){
    0 -> "zero"
    in 1..4 -> "four or less"
    5,6,7 -> "five to seven"
    is Byte -> "Byte"
    else -> "else number"
}
```

### ` v() ?: return`

```java
// tryGetName() ? tryGerName()  : null
val name = tryGetName() ?: return 
```
### Trailing Comma (constructor/function)

```java
fun x(name : String, )
```

### `vararg` & `*`

`vararg` equals `...`
```java
fun <T> asList(vararg : T) : List<T> {
    // ...
}
val t = asList(1,2,3,4,5);
```

`*` equals `Object[]`
```java
val a = arrayOf(1,2,3,4,5);
val r = asList(1,2,3,*a,4,5);
```

### Member function
**A member function** is a function that is defined inside a class or object:
```java
class Sample {
    fun foo() { print("Foo") }
}
```
**Functions with block body `{}` must always specify return types explicitly**
### Single-expression functions 
```java
// Explicitly declaring the return type is optional
fun double(x: Int) = x * 2
```
### Unit (void) returning type
```java
fun printHello(name: String?): Unit {} // same as
fun printHello(name: String?)
```

### default value in fun/constructor

order is matter , params with no default val fist then default ones

**When overriding a method that has default parameter values, the default parameter values must be omitted from the signature**

```java
fun foo(
    bar: Int = 0,
    baz: Int = 1,
    qux: () -> Unit,
) { /*...*/ }

foo(1) { println("hello") }     // Uses the default value baz = 1
foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
foo { println("hello") }        // Uses both default values bar = 0 and baz = 1
```

### `open`

```java
open class a {} // a can be inherited
open fun j() // j can be overridden
```

### Class Constructor & `init`

```java
class InitOrderDemo(name: String) {   
    val firstProperty = "First property: $name".also(::println)     
    
    init {        
        println("First initializer block that prints $name")    
    }
    
    val secondProperty = "Second property: ${name.length}".also(::println)       
    init {       
        println("Second initializer block that prints ${name.length}")   
    }
    
    var name = name;
    
    fun changeName(s : String){
       this.name = s;
       println(this.name);
    }
}

fun main(){
    
    val demo = InitOrderDemo("jian");
    demo.changeName("mayer");
}
```

### getter and setter

```java 
var <propertyName> : <PropertyType> = <property_initializer>
    get() = // ..
    set() = // ..

val simple: Int? // has type Int, default getter, must be initialized in constructor
val inferredType = 1 // has type Int and a default getter
var initialized = 1 // has type Int, default getter and setter

// custom getter
class Rectangle(val width: Int, val height: Int) {
    val area: Int 
    // property type is optional 
    // since it can be inferred from the getter's return type
        get() = this.width * this.height
}

// custom setter 
// (is called every time the value is assigned)
var stringRepresentation: String
    get() = this.toString()
    set(value) {
        setDataFromString(value) 
        // parses the string and assigns values to other properties
    }
```

### Interface 

- A class or object can **implement one or more interfaces**    
- Properties declared in interfaces **can't have backing fields**  

```java
interface MyInterface {
    // abstract or provide accessor implementations

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

### Conflict

you need to implement all the methods (conflicts)

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
## typealias (same as typescript type)


## Single Abstract Method (lambda)

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

```java
interface Printer {
    fun print()
}

//          parameter           returnType         anonymous              
fun Printer(block: () -> Unit): Printer = object : Printer { override fun print() = block() }
```

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
- **Type aliases can have only one member, while functional interfaces can have multiple non-abstract members and one abstract member.**   
Functional interfaces can also implement and extend other interfaces.
- **Functional interfaces are more flexible and provide more capabilities than type aliases**, but they can be more costly both syntactically and at runtime because they can require conversions to a specific interface. 



## Delegation (`by`)

Kotlin supports **Delegation** pattern natively requiring zero boilerplate code.

`className by arg`

```java
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}


class Derived(b: Base) : Base by b  // <--- WITH `by` Delegation NO NEED TO WRITE METHOD TO CALL print()

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

## Delegation Properties

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

## Function 

```java
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}
```