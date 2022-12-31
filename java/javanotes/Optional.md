
# Optional

- [Optional](#optional)
  - [To initialize Optional instance](#to-initialize-optional-instance)
  - [`empty()` create a **null optional instance**](#empty-create-a-null-optional-instance)
      - [`of(parameter)` creates a **non null object**](#ofparameter-creates-a-non-null-object)
  - [`.ofNullable()` allows creating the object as `null` or not `null`](#ofnullable-allows-creating-the-object-as-null-or-not-null)
  - [Existing Checking](#existing-checking)
    - [isPresent](#ispresent)
    - [isEmpty](#isempty)
    - [Optional With lambda](#optional-with-lambda)
  - [Since Java 9](#since-java-9)
    - [IfPresentOrElse (if .. else ...)](#ifpresentorelse-if--else-)
    - [Default value (`if not` situation)](#default-value-if-not-situation)
    - [Difference btw `.orElse` and `.orElseGet`](#difference-btw-orelse-and-orelseget)
  - [`.filter( para -> condition )`](#filter-para---condition-)
  - [`map(class::method)` and `map(para->method)`](#mapclassmethod-and-mappara-method)
    - [Filter and Map](#filter-and-map)
  - [Stream with Optional](#stream-with-optional)
    - [Since Java 9 (`Optional::Stream`)](#since-java-9-optionalstream)

`Optional` gives the alternatives (e.g. if-else ... etc) to make code cleaner


## To initialize Optional instance

There are three methods to initialize optional object

## `empty()` create a **null optional instance**
```java
/**
 * Creating a {@code Option<String> null} elements 
 */
Optional<String> empty = Optional.empty();

/**
 * @return {@code Optional.empty}  
 */
System.out.println(empty); 
```
 
#### `of(parameter)` creates a **non null object**
```java
Optional<String> opt = Optional.of("mtk");

/**
 * @return {@code Optional[mtk]}
 */
System.out.println(opt); 
```

If the object is `null` then throws `NullPointerException`
```java
String name = null;
/** 
  * <p> if object inside {@code of} is {@code null} 
  *        @throws NullPointerException </p>
  */
Optional<String> optnull = Optional.of(name);
```
 
## `.ofNullable()` allows creating the object as `null` or not `null` 

```java
String name = null;
Optional<String> optOrNull = Optional.ofNullable(name);
/**
 * @return {@code Optional.empty}
 */
System.out.println(optOrNull); 
```

## Existing Checking

### isPresent

`.isPresent()` if exists then returns `true`
```java
Optional<String> opt = Optional.of("hey"); 
if(opt.isPresent())  System.out.println("true");
```

### isEmpty

`.isEmpty()` if object is non-existent then returns false
```java
String object = null;
Optional<String> opt = Optional.ofNullable(object);
if(opt.isEmpty()) System.out.println("true");
```

### Optional With lambda 

```java
Optional<String> optOrNull = Optional.ofNullable("WhatsUp");

/**
 * <p> Without lambda </p>
 */
if (optOrNull.isPresent()) {
    System.out.println(optOrNull.get().length());
}

/**
  * <p> With lambda </p>
  * Variable {@code str} will be {@code String} type
  * depending on {@code Optional<String> optOrNull}'s type
  */
optOrNull.isPresent(
          str - > System.out.println(str.length()) );
```

## Since Java 9

### IfPresentOrElse (if .. else ...)

Using `.ifPresentOrElse(object parameter -> if_expression , else_expression )` instead
```java
optOrNull.ifPresentOrElse(
 (str) ->  {System.out.println(str.length(); }, 
 ()    ->  {System.out.println("empty");     } 
);
```

### Default value (`if not` situation)

```java
/**
  * {@code orElse} 
  * and
  * {@code orElseGet(param -> method)} 
  */
String name = null;
String opt = Optional.ofNullable(name).orElse("Im_default_value")

/**
  * @return {@code String} Im_default_value
  */
System.out.println(opt);    
String opt_2 = Optional.ofNullable(name)
                       .orElseGet( ()->"Im_default_value");


/**
  * Lambda 
  */
public class OrElseOptionalDemo{ 
  
  //...
  
  public static String getDefaultValue(){
        System.out.println("calling getDefaultValue");
        return "Im_default_value";
  }
}

/**
 * public static void main()
 */
String name = null;
String name2 = Optional.ofNullable(name)
                       .orElse(getDefaultValue());

// or using {@code orElseGet(class::method)}
String name3 = Optional.ofNullable(name)
                       .orElseGet(OrElseOptionalDemo::getDefaultValue);
```

### Difference btw `.orElse` and `.orElseGet` 
- [`orElse` and `orElseGet`](https://www.baeldung.com/java-optional#differences-or-else)

As a matter of fact, these two methods give the impression that they overlap each other in functionality.   
- Suggestion : **Use `orElseGet` instead of `orElse` in practice**  

```java
String name = "Im_not_Null";

// {@code orElse(Method())} 
String name2 = Optional.ofNullable(name)
                /**
                 * {@code getDefaultValue()} always will be called
                 * {@code ofNullable(name)} is null whether or not
                 */
               .orElse(getDefaultValue());

// {@code orElseGet(class::method)} 
String name3 = Optional.ofNullable(name)
               /**
                 * {@code orElseGet} with {@code OrElseOptionalDemo::getDefaultValue} 
                 * means this method may not be called 
                 */
               .orElseGet(OrElseOptionalDemo::getDefaultValue);
```

## `.filter( para -> condition )`

```java
String password = "12345";
Optional<String> opt = Optional.ofNullable(password);
System.out.println(opt.filter(pwd -> pwd.length() > 6).isPresent());

/**
  * <p> 
  * {@code Predicate<T>} as the parameters
  * for {@code filter} and {@code and}
  * </p>
  */
Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;

/**
 * <p> 
 * Use {@code and } 
 * To filter multiple conditions
 * </p>
 */
password = "1234567";
opt = Optional.ofNullable(password);
boolean result = opt.filter(len6.and(len10)).isPresent();
```

## `map(class::method)` and `map(para->method)`

```java
String name = "ToMap";
/**
 * <p> 
 * Creating a {@code Optional<String>} instance 
 * named nameOptional 
 * </p>
 */
Optional<String> nameOptional = Optional.of(name);

/**
 * <p>
 * Use {@code map(String:length)}
 * instead of {@code nameOptional.get().length()} 
 * </p>
 */
Optional<Integer> intOpt = nameOptional.map(String::length);
System.out.println(intOpt.orElse(0));
```

### Filter and Map

- [`filter`](https://www.baeldung.com/java-optional#filter) and `map` 

`map` :　Transforming Value
`filter` : Filter value with Condition

```java
/**
 * <p> 
 * The use of {@code filter} and {@code map} 
 * </p>
 */
String password = "password";
Optional<String>  opt = Optional.ofNullable(password);

Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;
Predicate<String> eq = pwd -> pwd.equals("password");

/**
  * <p> 
  * {@code map} the {@code opt} to lowerCase 
  * and {@code filter} the length btw 6 and 10
  * check if it equals password
  * </p>
  */
boolean result = opt.map(String::toLowerCase)
                    .filter(len6
                        .and(len10)
                        .and(eq))
                    .isPresent();
```
## Stream with Optional

- [Stream of Optional](https://www.baeldung.com/java-filter-stream-of-optional)

```java
List<String> filteredList = listOfOptionals.stream()
  .filter(Optional::isPresent)
  // optional<types> -> types
  .map(Optional::get)
  .collect(Collectors.toList());

List<String> filteredList = listOfOptionals.stream()
  // if not null
  .flatMap(
    o -> o.map(Stream::of).orElseGet(Stream::empty))
  .collect(Collectors.toList());
```

### Since Java 9 (`Optional::Stream`)

Using a predefined method for converting Optional instance into a Stream instance. 

It will return a stream of either one or zero element(s) whether the Optional value is or isn't present:
```java
List<String> filteredList = listOfOptionals.stream()
                                           .flatMap(Optional::stream)
                                           .collect(Collectors.toList());
```
