# Function & BiFunction


- [Function & BiFunction](#function--bifunction)
  - [Ref](#ref)
  - [Function](#function)
  - [BiFunction](#bifunction)

## Ref

[Java8_Function和BiFunction](https://www.jianshu.com/p/8dc46a2dc21d)   
[what is function curring](https://techwithmaddy.com/what-is-function-currying-in-java)   
 
```java
public int multiplyNumbers(int firstNumber, int secondNumber, int thirdNumber){
    return firstNumber * secondNumber * thirdNumber;
}

// via curry way
public static
        Function<Integer,
                Function<Integer,
                    Function<Integer, Integer>>> curry() {
        return f -> (
                s -> (
                    t -> multiplyNumbers(f, s, t)
                ));
    }

//using curried function
Integer curriedResult = curry().apply(1).apply(2).apply(3);
```

## Function

```java
public interface Function<T, R> {

    // Applies this function(itself) to the given argument.
    R apply(T t);

    // Returns a composed function that first applies the
    // before function to its input, 
    // and then applies itself to the result.
    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    // Returns a composed function that first applies itself to its input, 
    // and then applies the after function to the result.
    default <V> Function<T, V> andThen(Function<? super R, ? extends V>after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    // Returns a function that always returns its input argument.
    static <T> Function<T,T> identity()
}
```


```java
// method with apply
public int compute(int a, Function<Integer, Integer> function) {
    int result = function.apply(a);
    return result;
}

// method with compose 
public int compute(int a, Function<Integer, Integer> itSelf, 
                    Function<Integer, Integer> other){
    return itSelf.compose(other).apply(a);
}
example.compute(2, value -> value * 3, value -> value * value);
// (2*2)*3


// method with andThen
public int compute2(int a, Function<Integer, Integer> itSelf, Function<Integer, Integer> other) {
    return itSelf.andThen(other).apply(a);
}
example.compute(2, value -> value * 3, value -> value * value);
// (2*3)^2
```

## BiFunction

Pass Two Arguments `T,U` and return `R`

```java
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
 
    default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}

// apply
test.compute3(2, 3, (v1, v2) -> v1 * v2)   //6



// andThen
public int compute4(int a, int b, BiFunction<Integer, Integer, Integer> biFunction,Function<Integer, Integer> function) {
    return biFunction.andThen(function).apply(a, b);
}
test.compute4(2, 3, (v1, v2) -> v1 + v2, v1 -> v1 * v1)
```