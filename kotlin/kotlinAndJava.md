# Kotlin And Java 

- [Kotlin And Java](#kotlin-and-java)
  - [Class and Variable and Function](#class-and-variable-and-function)
  - [interfaces with val, var and fun](#interfaces-with-val-var-and-fun)
    - [implementation (Difference)](#implementation-difference)
    - [kotlin `constructor`, `super()`, `this()` and annotation](#kotlin-constructor-super-this-and-annotation)
  - [enum class (`enum` -> `enum` class)](#enum-class-enum---enum-class)
  - [annotation class](#annotation-class)
  - [final class in kotlin](#final-class-in-kotlin)
  - [flow control](#flow-control)
  - [`object` for singleton](#object-for-singleton)
  - [statics via companion object, const val](#statics-via-companion-object-const-val)
  - [inner class in kotlin](#inner-class-in-kotlin)
  - [java `instanceOf` -> kotlin `is`](#java-instanceof---kotlin-is)
  - [creating anonymous implementations for classes/interfaces](#creating-anonymous-implementations-for-classesinterfaces)
  - [kotlin does not have `condition ? true : false` ternary operator](#kotlin-does-not-have-condition--true--false-ternary-operator)
  - [Collection of Kotlin](#collection-of-kotlin)
  - [operator conventions (`get()` vs `[]`, `.equals()` vs `==`)](#operator-conventions-get-vs--equals-vs-)
  - [kotlin does not have checked exception](#kotlin-does-not-have-checked-exception)
  - [volatile is replaced with `@Volatile`](#volatile-is-replaced-with-volatile)
  - [synchronized](#synchronized)
  - [multiple generic bounds  (`extend .. & ..` -> `where T : .. T : ..`)](#multiple-generic-bounds--extend------where-t---t--)
  - [nullable](#nullable)

## Class and Variable and Function 

```java
package guide.to.kotlin;                                                package guide.to.kotlin;

public abstract class BaseClass {                                       abstract class BaseClass {       
    // ...                                                                  // ...
}                                                                       }               

public class MyClass extends BaseClass {                                class MyClass: BaseClass() {
    private final Map<String, Object> objects = new HashMap<>();            private val objects = hashMapOf<String, Any?>() 

    public MyClass() {                                                      init {
        System.out.println("MyClass created");                                println("MyClass created") 
    }                                                                       }  

    @Override                                                            
    protected void addToMap(String key, Object value) {                     override fun addToMap(key: String, value: Any?) {       
        objects.put(key, value);                                                objects.put(key, value);
    }                                                                       }     

    public <T> T getObject(String key) {                                    fun <T> getObject(key: String): T =    
        return (T) objects.get(key);                                             objects.get(key) as T   
    }                                                                       
}                                                                       }

final MyClass myClass = new MyClass();                                  val myClass = MyClass()
```

- `voud` -> `Unit`
- `extends || implements` -> `:` ( multiple inheritance with `,` to combine)
- `final` -> `val`
- `@Override` -> `override fun`
- constructor -> `init`
- `Object` -> `any?`
- `Map<T,Object> o = new HashMap<>()` -> `val object = hashMapOf<T,any?>()`
- `var` : mutable variable (Kotlin)
- `var`s can have different visibility modifiers, for example a `public` getter, but `private` setter.

## interfaces with val, var and fun

```java
public interface MyContract {                                   interface MyContract {
    String getValue();                                              var value: String   
    void setValue(String value);                                    val name: String                                                            
    String getName();                                               

    void doSomething(String input1, String input2);                fun doSomething(input1: String, input2: String)
}                                                               }
```

### implementation (Difference)

```java
class MyImpl(
    override var value: String,
    override val name: String
): MyContract {
    override fun doSomething(input1: String, input2: String) {
        //...
    }
}
```
- implements interface -> each field and method with `override` (prefix) 
- Using `,` to part the field 


```java
public class MyClass extends BaseClass implements MyInterface {        class MyClass(     
    private String mutableField = "";                                       tag: String,            // arg for base  
    private final String name;                                              private val name: String,
                                                                            var mutableField: String = "" // default argument
    public MyClass(String tag, String name) {                          ): BaseClass(tag), MyInterface {
        super(tag);                                                         override fun methodFromInterface() {}
    }                                                                  }

    // set and get
    public String getMutableField() {
        return mutableField;
    }
    public void setMutableField(String mutableField) {
        this.mutableField = mutableField;
    }

    @Override
    public void methodFromInterface() {
    }
} 
```

### kotlin `constructor`, `super()`, `this()` and annotation

You can define multiple constructors with `constructor` and call other constructor with `this()` or `super()`. 
It is also needed if annotations are added to it (for example, class MyClass @Inject constructor() {}.    
```java
class MyCustomView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}
```

## enum class (`enum` -> `enum` class)

```java
enum Colors {       enum class Colors {
    RED,                RED,
    BLUE                BLUE
}                   }
```

## annotation class

```java
public @interface CustomAnnotation {}  
annotation class CustomAnnotation
```


## final class in kotlin

**abstract classes in kotlin are `open` by default**
```java
java                                        kotlin

public final class FinalClass {             class FinalClass {  
}                                           }

public class SomeClass {                    open class SomeClass {
}                                           }
```

## flow control


```java
for(int i = 0; i < n; i++) {        for(i in 0 until n) { 
    // do something                     // do something 
}                                   } 

for(pair in pairs) {                for((key, value) in map.entries) {
    ...                                 ...
}                                   }
```

## `object` for singleton

```java
object MySingleton {
    // this is a singleton

    fun something() {
    }
}

MySingleton.something()
```

## statics via companion object, const val

`static` doesn't exist in kotlin

```java
public class MyClassWithStatics {
    public static final String SOME_CONSTANT = "Hello!";

    public static String someUglyMutableField = "don't do this like ever";

    public static void hello() {
        System.out.println(SOME_CONSTANT + " " + someUglyMutableField);
    }
}

public class MyOtherClass {
    void doSomething() {
        String constant = MyClassWithStatics.SOME_CONSTANT;
        String someUglyMutableField = MyClassWithStatics.someUglyMutableField;
        MyClassWithStatics.hello();
    }
}

class MyClassWithStatics {
    companion object {
        const val SOME_CONSTANT = "Hello!"

        @JvmField var someUglyMutableField: String = "don't do this like ever"

        @JvmStatic fun hello() { println("$SOME_CONSTANT $someUglyMutableField") }
    }
}
```

## inner class in kotlin

In Kotlin, all nested classes are equivalent with public static class in Java.

So to make a nested class be an "inner" class, it must be specified as inner class.

```java
class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        ...
    }
}
```

## java `instanceOf` -> kotlin `is`

```java
if (object instanceOf someClass){         if(object is someClass){
    //..                                            //...
}                                         }
```

If we cannot leverage smart-casting, then we can use `as` (throws exception on failure) or `as?` (returns null).
```java
(obj as? Cat)?.meow()
```

Also worth noting here that smart-casting wouldn't work with mutable nullable field variables, and should be re-assigned to a `val` first, like `val name = name`. But there will be an example for this later.


## creating anonymous implementations for classes/interfaces

```java
myView.setOnClickListener(new View.OnClickListener() {      myView.setOnClickListener(object: View.OnClickListener {
    @Override                                                   
    public void onClick(View view) {                                override fun onClick(view: View) {
        // do something                                                 // do something}
    }                                                               }
});                                                         })


// Lambda
myView.setOnClickListener((view) -> {       myView.setOnClickListener { view ->    
    // do something                             // do something
});                                         }    
```
Note that we can ditch the enclosing () around the last lambda argument of a function, this is called "trailing lambdas".

- Kotlin 1.4 change: Since Kotlin 1.4, you can also use trailing lambdas (and not just `object: MyInterface {)` with interfaces, if you declare the interface as `fun interface`.   
This needs the interface to only have a single method.

## kotlin does not have `condition ? true : false` ternary operator

instead use
```java
val something = when {
    condition -> y    
    else -> x
}
```

## Collection of Kotlin
`arrayOf` and `arrayOfNulls` and `listOf` and `mutableListOf` and `linkedMapOf`
Kotlin provides functions that initialize collections as part of the standard library, and should typically be preferred compared to using their constructors directly.

```java
private final List<String> strings = new ArrayList<>();
// is generally replaced with
private val strings = arrayListOf<String>()
```

In Kotlin, the mutator methods are separated into a separate MutableList interface, so if we do want to mutate this list in place over time, we should use either `mutableListOf<T>()`, or `arrayListOf<T>()`.    
For example ::
```java
mapOf<K, V>(), mutableMapOf<K, V>(), and linkedMapOf<K, V>().
setOf<T>(), mutableSetOf<T>(), hashSetOf<T>().
```

Also, you create a list with a size of elements and a lambda for each element.
```java
val squares = List(size = 6) { index -> index * index } 
// same as:
val squares = listOf(0, 1, 4, 9, 16, 25)
```

## operator conventions (`get()` vs `[]`, `.equals()` vs `==`)

Many names that a given method had in Java are converted implicitly to "operator"s in Kotlin.

- `==` in Kotlin actually translates to `.equals()`
- `===` is referential `==` equality in java.

```java
String value = map.get("key")   
// in kotlin
val value = map["key"]
```

<, <=, > and >= are mapped to .compareTo calls, and it is possible to override other operators such as +=, -=, +a and so on..


## kotlin does not have checked exception

```java
try(Closeable closeable = new Closeable()) { ... }
```

## volatile is replaced with `@Volatile`

## synchronized

For synchronized, we can use the `synchronized(lock) { ... }` function from the Kotlin standard library.

## multiple generic bounds  (`extend .. & ..` -> `where T : .. T : ..`)

```java
// java
public class MyClass<T extends SomeClass & Comparable> {
    ...
}

// In kotlin
class MyClass<T: SomeClass> { // this would be single-bounds

class MyClass<T>
    where T: SomeClass,
          T: Comparable { // multiple generic bounds

```

## nullable 

```java
private List<Item> items = null;
private item : List<Item>? = null;

public void updateItems(List<Item> items) {
    this.items = items;
    notifyDataSetChanged();
}
fun updateItem(items : List<Item>?){
    // same as java
}

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false));
}

override fun onCreateViewHolder(parent : ViewGroup)

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(items.get(position));
} 
@Override
public int getItemCount() {
    return items == null ? 0 : items.size(); 
}
```