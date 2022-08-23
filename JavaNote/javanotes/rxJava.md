

- [RxJava 2.0 DOC](https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-2.0)  
## Difference btw Reactor and RxJava

## `Flowable` and `Fluxs`

`Flowable` and `Flux` have very similar API. Obviously, they both support basic operators like `map()`, `filter()`, `flatMap()`, as well as more advanced ones. 

The main difference is the target Java version. 
- RxJava 2.x must still support **Java 6** as it is widely used on Android (read later on). 
- Reactor, on the other hand, **targets Java 8+**. 
    - **Reactor can take advantage of modern (-ish, Java 8 is 5 years old, at the time of writing) APIs like java.time and java.util.function. It’s so much safer to type**

```java
import java.time.Duration;
flux.window(Duration.ofSeconds(1));

import java.util.concurrent.TimeUnit;
flowable.window(1, TimeUnit.SECONDS);
```
- Passing around a single Duration instance is easier and safer than an integer. 
- Reactor has a direct conversion from `CompletableFuture`, `Optional`, `java.util.stream.Stream` etc.


## Checked exceptions
Reactor uses standard functional types from JDK, like Function in its API. That’s great. 

But a tiny side-effect of that choice is an awkward handling of checked exceptions inside transformations. Consider the following code, that does not compile:
```java
Flux
    .just("java.math.BigDecimal", "java.time.Instant")
    .map(Class::forName)
```

`Class.forName()` throws checked `ClassNotFoundException`, unfortunately, you are not allowed to throw checked exceptions from `java.util.function.Function`. 

In RxJava, on the other hand, `io.reactivex.functions.Function` is free from such constraints and the similar code would compile just fine. Whether you like checked exceptions or not, once you have to deal with them, RxJava makes the experience more enjoyable. 
## Type Safe

RxJava has safer API.

| RxJava 2	| Reactor |	Purpose |
| ----------| ------- | --------|
| Completable |	N/A    |	Completes successfully or with failure, without emitting any value. Like `CompletableFuture<Void>`
| Maybe<T>	| `Mono<T>`|	Completes successfully or with failure, may or may not emit a single value. Like an asynchronous Optional<T>
| Single<T>	| N/A	   |    Either complete successfully emitting exactly one item or fails.
|Observable<T>	| N/A  |	Emits an indefinite number of events (zero to infinite), optionally completes successfully or with failure. Does not support backpressure due to the nature of the source of events it represents.
|Flowable<T> |	Flux<T> |	Emits an indefinite number of events (zero to infinite), optionally completes successfully or with failure. Support backpressure **(the source can be slowed down when the consumer cannot keep up)**

## Debugging

Reactor adds a wonderful debugging gem:
```java
Hooks.onOperatorDebug();
```
It placed at the beginning of your application will track how signals are flowing through your stream.

```java
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.io.File;

public class StackTest {

    public static void main(String[] args) {

        Mono<Long> totalTxtSize = Flux
                .just("/tmp", "/home", "/404")
                .map(File::new)
                .concatMap(file -> Flux.just(file.listFiles()))
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(".txt"))
                .map(File::length)
                .reduce(0L, Math::addExact);


        totalTxtSize.subscribe(System.out::println);
    }

}
```

## Usage 

RxJava is mainly used for Android development 
- avoid callback hell by modelling UI events as streams
- easily switching back and forth between threads, especially making sure I/O doesn’t happen on UI thread

Reactor is mainly used for SpringBoot
