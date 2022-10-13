# Java 8

- [Java 8](#java-8)
  - [Reference](#reference)
  - [Functional Interface](#functional-interface)
  - [Default methods for interfaces](#default-methods-for-interfaces)
  - [lambda](#lambda)
  - [Syntax of Lambda Expressions](#syntax-of-lambda-expressions)
    - [lambda expression as parameter](#lambda-expression-as-parameter)
  - [函數合成](#函數合成)
  - [Method References (`Class::Method`)](#method-references-classmethod)
    - [Constructor References](#constructor-references)
  - [Lambda Scopes](#lambda-scopes)
    - [Accessing fields and static variables](#accessing-fields-and-static-variables)
    - [Accessing Default Interface Methods](#accessing-default-interface-methods)
  - [Built-in Functional Interfaces](#built-in-functional-interfaces)
    - [Predicates(`Predicate<T>`)](#predicatespredicatet)
    - [Function<T,R>](#functiontr)
    - [Suppliers](#suppliers)
    - [Consumers](#consumers)
    - [Comparators](#comparators)
## Reference

[oracle java lambda expression](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
**[cheatSheet](https://github.com/winterbe/java8-tutorial)**   
[Java 8 in Action](https://gitee.com/lihuadaiyu/read/blob/master/Java8%E5%AE%9E%E6%88%98/Java8%E5%AE%9E%E6%88%98%20%E7%AC%94%E8%AE%B0.md)   
[Java 8 in Action Source Code](https://github.com/java8/Java8InAction)  
[Lambda Conversion](https://mkyong.com/java8/java-8-flatmap-example/)   
[How to pass a function as a parameter in Java?](https://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java)   
[15 Practical Exercises Help You Master Java Stream API](https://blog.devgenius.io/15-practical-exercises-help-you-master-java-stream-api-3f9c86b1cf82)  
[Java 8 java.util.function 常用的Functional Interface](https://matthung0807.blogspot.com/2018/09/java-8-javautilfunction-functional.html)
## Functional Interface  

A functional interface is an interface that has one and only one abstract method, **although it can contain any number of default methods (new in Java 8) and static methods.**

**To ensure that your interface meet the requirements, you should add the `@FunctionalInterface`(optional) annotation.**

The compiler is aware of this annotation and throws a compiler error as soon as you try to add a second abstract method declaration to the interface.
```java 
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}

// Anonymous
Converter<String,Integer> toInteger = 
        new Converter<String,Integer>(){
            @Override
            public Integer convert(String from) {
                return Integer.valueOf(from);
            }
        };
// Lambda
Converter<String, Integer> convertToInteger =  (from) -> Integer.valueOf(from);

Integer converted = converter.convertToInteger("123");
System.out.println(converted);    // 123
```

## Default methods for interfaces

```java
interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

// Anonymous Class
Formula formula = new Formula() {
    @Override
    public double calculate(int a) {
        return sqrt(a * 100);
    }
};

// lambda
Formula formula = (a) -> return sqrt(a*100);

formula.calculate(100);     // 100.0
formula.sqrt(16);           // 4.0
```

## lambda 

**Lambda 表示式本身是中性的，它本身無關乎函式介面的名稱，它只關心方法簽署，但忽略方法名稱**

```java 
// sum of x, y
(int x, int y) -> x + y

// no parameter return 42 directly
() -> 42

// it's ok without return value 
(String s) -> { out.println(s); }


(Integer x) -> {
    Integer result;
    //...other statements
    // ...
    return result;
};
```

**A lambda expression can quickly implement the abstract method, without all the unnecessary syntax needed if you don't use a lambda expression.**
```java
List<String> names = Arrays.asList("p", "a", "m", "x");

// anonymous class
Collections.sort(names, 
                 new Comparator<String>() {
                    @Override
                    public int compare(String a, String b) {
                        return b.compareTo(a);
                    }
                });

// with lambda expr
Collections.sort(names, (String a, String b) -> {
    return b.compareTo(a);
});
```

## Syntax of Lambda Expressions

A lambda expression consists of the following:
You can omit the data type of the parameters in a lambda expression.    
In addition, you can omit the parentheses `(param1, param2, ...)` if there is only one parameter.  

For example, the following lambda expression is also valid:
```java
p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25
```

A body, which consists of a single expression or a statement block. 
```java
p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25
```

If you specify a single expression, then the Java runtime evaluates the expression and then returns its value. 

Alternatively, you can use a `return` statement:
```java 
p -> {
    return p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25;
}
```
- **A return statement is not an expression; in a lambda expression, you must enclose statements in braces (`{}`).**

However, you do not have to enclose a **void** method invocation in braces. 
```java
email -> System.out.println(email)
```

### lambda expression as parameter

here uses a new standard functional interface, `java.util.function.IntConsumer`.
```java
class A {
    public static void methodToPass(int i) { 
        // do stuff
    }
}

import java.util.function.IntConsumer;

class B {
    public void dansMethod(int i, IntConsumer aMethod) {
        /* 
            you can now call the passed method 
            by saying aMethod.accept(i), and it
            will be the equivalent of saying 
            A.methodToPass(i) 
        */
    }
}
class C {
    B b = new B();
    public C() {
        b.dansMethod(100, j -> A.methodToPass(j));   
        //Lambda Expression here
    }
}
```

## 函數合成

[認識 Lambda/Closure（7）JDK8 Lambda 語法](https://openhome.cc/Gossip/CodeData/JavaLambdaTutorial/Java8Lambda.html)

```java
// anonymous 
compose(
    new Func<Integer, Integer>() {
        public Integer apply(Integer x) {
            return x + 2;
        }
    },
    new Func<Integer, Integer>() {
        public Integer apply(Integer y) {
            return y * 3;
        }
    }
);

// lambda
public static <A, B, C> Func<A, C> compose(Func<A, B> f, Func<B, C> g) {
    return x -> g.apply(f.apply(x));
}
```


## Method References (`Class::Method`)

```java
MethodName fn = Class::Method;
```

```java
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
Converter<String, Integer> converter = Integer::valueOf;
// Same as Converter<String, Integer> converter = 
//                 (from) -> Integer.valueOf(from);

Integer converted = converter.convert("123");
System.out.println(converted);   // 123

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
Something something = new Something();
// Converter<String, String> converter = (s) -> String.valueOf(s.charAt(0))
Converter<String, String> converter = something::startsWith;
String converted = converter.convert("Java");
System.out.println(converted);    // "J"
```


### Constructor References 


```java 
class Person {
    String firstName;
    String lastName;

    Person() {}

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
```

Next we specify a person factory interface to be used for creating new persons:
```java
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
```

Instead of implementing the factory manually, we glue everything together via constructor references:
```java
// PersonFactory<Person> personFactory = 
//          (firstName,lastName) -> new Person(firstName,lastName)
PersonFactory<Person> personFactory = Person::new;
Person person = personFactory.create("Peter", "Parker");
```
-  `Person::new` creates a reference to the Person constructor  
    The Java compiler automatically chooses the right constructor by matching the signature of `PersonFactory.create`.   


## Lambda Scopes

Accessing outer scope variables from lambda expressions is very similar to anonymous objects. 

You can access `final` variables from the local outer scope as well as instance fields and static variables.
```java
final int num = 1;
Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);

stringConverter.convert(2);  // 3
```

But different to anonymous objects the variable `num` does not have to be declared `final`. 
The following code is also valid:
```java
int num = 1;
Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
stringConverter.convert(2); // 3
```
- `num` must be implicitly `final` for the code to compile. 


**Writing to `num` from within the lambda expression is also prohibited.**
```java
int num = 1;
Converter<Integer, String> stringConverter =
        (from) -> String.valueOf(from + num);
num = 3; // !!!
```

### Accessing fields and static variables

In contrast to local variables, **we have both read and write access to instance fields and static variables from within lambda expressions**. 

This behavior is well known from anonymous objects.
```java
class Lambda4 {
    static int outerStaticNum;
    int outerNum;

    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
```

### Accessing Default Interface Methods

Remember the formula example from the first section? Interface `Formula` defines a default method `sqrt` which can be accessed from each formula instance including anonymous objects. This does not work with lambda expressions.

Default methods **cannot** be accessed from within lambda expressions.   
The following code does not compile:
```java
Formula formula = (a) -> sqrt(a * 100);
```


## Built-in Functional Interfaces

The JDK 1.8 API contains many built-in functional interfaces. Some of them are well known from older versions of Java like `Comparator` or `Runnable`. 

Those existing interfaces are extended to enable Lambda support via the `@FunctionalInterface` annotation.

But the Java 8 API is also full of new functional interfaces to make your life easier. 
Some of those new interfaces are well known from the [Google Guava](https://code.google.com/p/guava-libraries/) library.


```java
public static void processPersonsWithFunction(
    List<Person> roster,                    // 1
    Predicate<Person> tester,               // 2
    Function<Person, String> mapper,        // 3
    Consumer<String> block) {               // 4
    for (Person p : roster) {
        if (tester.test(p)) {
            String data = mapper.apply(p);
            block.accept(data);
        }
    }
}
processPersonsWithFunction(
    roster,                                   //1
    p -> p.getGender() == Person.Sex.MALE     //2
        && p.getAge() >= 18                    
        && p.getAge() <= 25,                   
    p -> p.getEmailAddress(),                 //3
    email -> System.out.println(email)        //4
);
```

### Predicates(`Predicate<T>`)

Predicates are **boolean-valued** functions of one argument. 

**The interface contains various default methods for composing predicates to complex logical terms (`and`, `or`, `negate`)**

use case : `Optional.filter(Predicate<? super T> predicate)`
```java
// anonymous 
Optional<String> os1 = Optional.ofNullable(str).filter(
    new Predicate<String>() {
          @Override
          public boolean test(String s) {
              return s.length() < 10; // false
          }
    });
```


```java
// lambda 
Predicate<String> predicate = (s) -> s.length() > 0;

predicate.test("foo");              // true

// get the string-length that <= 0 
predicate.negate().test("foo");     // false

Predicate<Boolean> nonNull = Objects::nonNull;
Predicate<Boolean> isNull = Objects::isNull;

Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isNotEmpty = isEmpty.negate();
```

### Function<T,R>

`Function<Target,Return>` accepts one argument and produce a result. 

**Default methods can be used to chain multiple functions together (`compose`, `andThen`).**

```java
package java.util.function;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
    ...
}

Function<String, Integer> toInteger = Integer::valueOf;
Function<String, String> backToString = toInteger.andThen(String::valueOf);

backToString.apply("123");     // "123"
```

### Suppliers

**Suppliers produce a result of a given generic type.** 

Unlike Functions, Suppliers don't accept arguments.

```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}

Supplier<Person> personSupplier = Person::new;
personSupplier.get();   // new Person
```

### Consumers


Consumers represent operations to be performed on a single input argument.

- Other `Consumers` type `IntConsumer`、`LongConsumer`、`DoubleConsumer`

```java
package java.util.function;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    ...
}

Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
greeter.accept(new Person("Luke", "Skywalker"));

Arrays.asList("Justin", "Monica", "Irene").forEach(out::println);
```

### Comparators

Comparators are well known from older versions of Java. 

Java 8 adds various default methods to the interface.
```java
Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

Person p1 = new Person("John", "Doe");
Person p2 = new Person("Alice", "Wonderland");

comparator.compare(p1, p2);             // > 0
comparator.reversed().compare(p1, p2);  // < 0
```
