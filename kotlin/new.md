# features
- [features](#features)
  - [`lateinit`](#lateinit)
  - [kotlin single line function (`=`)](#kotlin-single-line-function-)
  - [check for null (`?`)](#check-for-null-)
  - [nullable (with `?`)](#nullable-with-)
  - [string interpolation (`{{}}`)](#string-interpolation-)
  - [data class](#data-class)
  - [when & `..`](#when--)
    - [find range (`..`) & `else`](#find-range---else)
    - [multiple different (data type) checking](#multiple-different-data-type-checking)
    - [enum](#enum)
  - [` v() ?: return`](#-v--return)
  - [variable arguments (`vararg`) and the `*` spread operator](#variable-arguments-vararg-and-the--spread-operator)
    - [spread *array](#spread-array)
    - [function parameter trailing comma](#function-parameter-trailing-comma)
    - [default value](#default-value)
    - [override default value](#override-default-value)
    - [default param precedes a parameter with no default value](#default-param-precedes-a-parameter-with-no-default-value)
    - [If the last argument after default parameters is a lambda](#if-the-last-argument-after-default-parameters-is-a-lambda)
    - [Unit-returning functions](#unit-returning-functions)
    - [Single-expression functions　(`=`)](#single-expression-functions)
    - [Explicit return types](#explicit-return-types)
    - [Function scope (Local/Member function)](#function-scope-localmember-function)
    - [Member Function](#member-function)
    - [Generic functions `<T>`](#generic-functions-t)
    - [Tail recursive functions](#tail-recursive-functions)

## `lateinit`


> with lateint I assure this variable will be assigned later

If you know that a value in Kotlin will be initialized later so you can guarantee that it will be non-null on any future access, instead of defining it as `name: String?`, you can define it as a `lateinit var name: String.`

PS. 
- **Accessing an uninitialized `lateinit` variable results in an exception.**
- It's fairly common to use `lateinit var` inside Activity/Fragment calls (because you commonly initialize things in `onCreate`/`onViewCreated`), so it's best to know about this.

## kotlin single line function (`=`)

```java 
fun getRepository(): Repository {
    return repository
}

fun getRepository(): Repository = repository
```

## check for null (`?`)

```java
val name: String? = "Adam"

if (name != null && name.length > 0) {
    print("String length is ${name.length}")
} else {
    print("String is empty.")
}
```

## nullable (with `?`)

```java
val cannotBeNull: String = null // Invalid
val canBeNull: String? = null // Valid
```

## string interpolation (`{{}}`)

```java
val hello: String
    get() = "Hello $name, your overlord ${overlord.name} has been expecting you."

val jsonString = """
    {
    	"hello": "world",
        "another": {
                "field": "field",
                "boom": "boom"
        }
    }
""".trimIndent()
```

## data class 

Generate an `equals`, `hashCode` and `toString` function automatically (along with `copy()`).

```java
 data class Dog(
    val name: String,
    val owner: Person
)
```

**Using object for no-arguments is commonly recommended in place of empty data class**, it actually has different behavior    
```java
@Parcelize
data class LoginKey(val placeholder: String = ""): Parcelable
```
- the `toString()` won't be consistent across processes

## when & `..`

when statements are super-powerful, because they can be combined with complex conditions, such as:

- checking if an `int` is in a range of x..y
- checking if a class is of a particular type (sealed class) or enum value

### find range (`..`) & `else`

**Kotlin 1.3: allows creating a variable within the `when` statement**

```java
val value = when(number) {
    0,1,2,3,4 -> 1.0
    in 5..9 -> 0.75
    in 10..14 -> 0.5
    in 15..19 -> 0.25
    20 -> 0.2
    else -> 0
}
```

### multiple different (data type) checking

```java
fun numberTypename(x : Number) = when(x){
    0 -> "zero"
    in 1..4 -> "four or less"
    5,6,7 -> "five to seven"
    is Byte -> "Byte"
    else -> "else number"
}
```

### enum

```java
enum class Colors {
   RED,
   GREEN,
   BLUE
}

val color = Colors.RED

when(color) {
    Colors.RED -> {
       ...
    }
    else ->
       ...
    }
}
```


## ` v() ?: return`

```java
// tryGetName() ? tryGerName()  : null
val name = tryGetName() ?: return 
```

## variable arguments (`vararg`) and the `*` spread operator

`vararg` means `...` in java/typescript

```java
// variable arguments
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) 
        result.add(t)
    
    return result
}
```

**When you call a vararg-function, you can pass arguments individually, for example `asList(1, 2, 3)`.**
```java
val list = asList(1, 2, 3)
```
- a vararg-parameter of type `T` is visible as an array of `T`, as in the example above, where the ts variable has type `Array<T>.`

**Only one parameter can be marked as `vararg`.** If a vararg parameter is not the last one in the list, values for the subsequent parameters can be passed using named argument syntax, or, if the parameter has a function type, by passing a lambda outside the parentheses.

### spread *array

**If you already have an ARRAY and want to pass its contents to the function**, use the spread operator (prefix the array with `*`):
```java 
val a = arrayOf(1, 2, 3)
val list = asList(-1, 0, *a, 4)
```

**If you want to pass a primitive type array into `vararg`**, you need to convert it to a regular (typed) array using the `toTypedArray()` function:
```java 
val a = intArrayOf(1, 2, 3) // IntArray is a primitive type array
val list = asList(-1, 0, *a.toTypedArray(), 4)
```

```java 
fun animateTogether(vararg animators: Animator) = AnimatorSet().apply {
    playTogether(*animators)
}

fun foo(vararg strings: String) { /*...*/ }
foo(strings = *arrayOf("a", "b", "c"))
```

### function parameter trailing comma 

```java
fun someFunction(
    v : String,
    j : Int,    // trailing comma 
)
```

### default value

Order is matter. 
Parameter with no default val must precede default ones

```java
fun read(
    b: ByteArray,   // <-- precede params with default value
    off: Int = 0,
    len: Int = b.size,
) { /*...*/ }

@JvmOverloads
fun printStrings(
    first: String = "Hello", 
    second: String = "World",
) { println("$first $second")
}

printStrings()
printStrings("Goodbye", "my dear")
printStrings(first = "Goodbye", second = "my dear")
printStrings(first = "Goodbye")
printStrings(second = "my dear")
```

### override default value

Overriding methods always use the same default parameter values as the base method. 

**When overriding a method that has default parameter values, the default parameter values must be omitted from the signature**

### default param precedes a parameter with no default value


If a default parameter PRECEDES a parameter with no default value, the default value can only be used by calling the function with named arguments:
```java
fun foo(
    bar: Int = 0,
    baz: Int,
) { /*...*/ }

foo(baz = 1) // The default value bar = 0 is used
```

### If the last argument after default parameters is a lambda

you can pass it either as a named argument or outside the parentheses `{}`:
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

### Unit-returning functions

```java
fun printHello(name: String?): Unit {}

// same
fun printHello(name: String?)
```

### Single-expression functions　(`=`)

When a **function returns a single expression, the curly braces can be omitted** and the body is specified after a `=` symbol:

```java
fun double(x: Int): Int = x * 2

// Explicitly declaring the return type is optional
fun double(x: Int) = x * 2
```

### Explicit return types

**Functions with block body `{}` must always specify return types explicitly**, unless it's intended for them to return `Unit`, in which case specifying the return type is optional.

**Kotlin does not infer return types for functions with block bodies because such functions may have complex control flow in the body**.

### Function scope (Local/Member function)

**Kotlin functions can be declared at the top level in a file**  

Kotlin supports local functions, which are functions inside other functions:
```java
fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()

    fun dfs(current: Vertex) {

        if (!visited.add(current)) return
        
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}
```

### Member Function

**A member function** is a function that is defined inside a class or object:
```java
class Sample {
    fun foo() { print("Foo") }
}
```
### Generic functions `<T>`

```java
fun <T> singletonList(item: T): List<T> { /*...*/ }
```
### Tail recursive functions

You cannot use tail recursion when there is more code after the recursive call, within `try/catch/finally` blocks, or on open functions. 

```java 
// tailrec style
val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double =
    if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))

// transitional style
val eps = 1E-10 // "good enough", could be 10^-15
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (Math.abs(x - y) < eps) return x
        x = Math.cos(x)
    }
}
```