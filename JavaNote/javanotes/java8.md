# Java 8

- [Java 8](#java-8)
  - [Reference](#reference)
  - [Functional Interface](#functional-interface)
  - [Default methods for interfaces](#default-methods-for-interfaces)
  - [lambda expression](#lambda-expression)
  - [type interference](#type-interference)
    - [threads](#threads)
  - [Syntax of Lambda Expressions](#syntax-of-lambda-expressions)
    - [lambda expression as parameter](#lambda-expression-as-parameter)
  - [函數合成](#函數合成)
  - [Method and Constructor References](#method-and-constructor-references)
## Reference

- [cheatSheet](https://github.com/winterbe/java8-tutorial)
- [Java 8 in Action](https://gitee.com/lihuadaiyu/read/blob/master/Java8%E5%AE%9E%E6%88%98/Java8%E5%AE%9E%E6%88%98%20%E7%AC%94%E8%AE%B0.md)
- [Java 8 in Action Source Code](https://github.com/java8/Java8InAction)
- [Lambda Conversion](https://mkyong.com/java8/java-8-flatmap-example/)
- [How to pass a function as a parameter in Java?](https://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java)

- [15 Practical Exercises Help You Master Java Stream API](https://blog.devgenius.io/15-practical-exercises-help-you-master-java-stream-api-3f9c86b1cf82)

## Functional Interface  

A functional interface is an interface that has one and only one abstract method, **although it can contain any number of default methods (new in Java 8) and static methods.**

**To ensure that your interface meet the requirements, you should add the `@FunctionalInterface`(optional) annotation.**

The compiler is aware of this annotation and throws a compiler error as soon as you try to add a second abstract method declaration to the interface.
```java 
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}

Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
Integer converted = converter.convert("123");
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

formula.calculate(100);     // 100.0
formula.sqrt(16);           // 4.0
```

## lambda expression

```java 
// sum of x, y
(int x, int y) -> x + y

// no parameter return 42 directly
() -> 42

// it's ok without return value 
(String s) -> { out.println(s); }


(Integer x) -> {
    Integer result;
    ...other statements
    ...
    return result;
};
```

**A lambda expression can quickly implement the abstract method, without all the unnecessary syntax needed if you don't use a lambda expression.**
```java
List<String> names = Arrays.asList("p", "a", "m", "x");

Collections.sort(names, new Comparator<String>() {
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

## type interference

**Lambda 表示式本身是中性的，它本身無關乎函式介面的名稱，它只關心方法簽署，但忽略方法名稱**

```java
public interface Function<P, R> {
    R call(P p);
}

Func<Integer, Integer> func = x -> x * 2;
```

### threads

```java
import static java.lang.System.out;

public class Hello {
    Runnable r1 = new Runnable() {
        public void run() {
            out.println(this);
        }
    };
    Runnable r2 = new Runnable() {
        public void run() {
            out.println(toString());
        }
    };

    public String toString() { return "Hello, world!"; }

    public static void main(String[] args) {
        new Hello().r1.run();
        new Hello().r2.run();
    }
}
```

## Syntax of Lambda Expressions
A lambda expression consists of the following:
You can omit the data type of the parameters in a lambda expression.    
In addition, you can omit the parentheses `(param1, param2, ...)` if there is only one parameter.  

For example, the following lambda expression is also valid:
```java
p -> p.getGender() == Person.Sex.MALE 
    && p.getAge() >= 18
    && p.getAge() <= 25
```

A body, which consists of a single expression or a statement block. 
```java
p.getGender() == Person.Sex.MALE 
    && p.getAge() >= 18
    && p.getAge() <= 25
```

If you specify a single expression, then the Java runtime evaluates the expression and then returns its value. Alternatively, you can use a `return` statement:
```java 
p -> {
    return p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25;
}
```
A return statement is not an expression; in a lambda expression, you must enclose statements in braces (`{}`). 

However, you do not have to enclose a void method invocation in braces. 
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
        /* you can now call the passed method by saying aMethod.accept(i), and it
        will be the equivalent of saying A.methodToPass(i) */
    }
}
class C {
    B b = new B();
    public C() {
        b.dansMethod(100, j -> A.methodToPass(j));   //Lambda Expression here
    }
}
```

## 函數合成

[認識 Lambda/Closure（7）JDK8 Lambda 語法](https://openhome.cc/Gossip/CodeData/JavaLambdaTutorial/Java8Lambda.html)

```java
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

public static <A, B, C> Func<A, C> compose(Func<A, B> f, Func<B, C> g) {
    return x -> g.apply(f.apply(x));
}
```


## Method and Constructor References

```java
Class :: Method
```

```java
Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
// equals
Converter<String, Integer> converter = Integer::valueOf;
```
