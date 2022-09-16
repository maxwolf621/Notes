# Stream

- [Stream](#stream)
  - [Reference](#reference)
  - [Arrays.asList](#arraysaslist)
  - [Arrays.stream(`T[]` arr)](#arraysstreamt-arr)
  - [Ints.asList(...)](#intsaslist)
  - [Stream.generate(...)](#streamgenerate)
  - [Collections,List,Set Stream](#collectionslistset-stream)
  - [Stream.of](#streamof)
  - [IntStream,DoubleStream,LongStream](#intstreamdoublestreamlongstream)
    - [iterate](#iterate)
    - [rangeClosed and range](#rangeclosed-and-range)
    - [boxed()](#boxed)
    - [mapToObj](#maptoobj)
    - [parallel](#parallel)
  - [mapToInt, mapToLong ,mapToDouble,](#maptoint-maptolong-maptodouble)
  - [intermediate operations](#intermediate-operations)
  - [Terminal Operations `___Match`](#terminal-operations-___match)
  - [Collectors](#collectors)
    - [Collectors.joining](#collectorsjoining)
    - [Collectors.toXXXX()](#collectorstoxxxx)
    - [Collectors.counting()](#collectorscounting)
    - [Collectors.maxBy(...)](#collectorsmaxby)
    - [summing---, average---, summarizing---](#summing----average----summarizing---)
    - [Collectors.groupingBy(....)](#collectorsgroupingby)
    - [Collectors.groupingBy(.... , Collectors.groupingBy(...))](#collectorsgroupingby--collectorsgroupingby)
    - [Collectors.partitioningBy(...)](#collectorspartitioningby)
    - [Collectors.reducing(...)](#collectorsreducing)
  - [Exclude Null Element (`Objects::nonNull`)](#exclude-null-element-objectsnonnull)
  - [Order of intermediate operations](#order-of-intermediate-operations)
  - [Reusing Stream](#reusing-stream)
  - [Build Up Your Own Collector](#build-up-your-own-collector)
  - [stream forEach](#stream-foreach)
  - [FlatMap](#flatmap)
  - [Parallel Streams](#parallel-streams)
    - [parallelStream with `sorted`](#parallelstream-with-sorted)
  - [Reduce](#reduce)
    - [accumulator & combiner](#accumulator--combiner)

## Reference
- [SoruceCode](https://www.cnblogs.com/owenma/p/12207330.html)
- [豬肉工程師](https://matthung0807.blogspot.com/2021/01/java-8-stream-pipeline-operations-intro.html)   
- [Difference btw Stream and RxJava](https://stackoverflow.com/questions/30216979/difference-between-java-8-streams-and-rxjava-observables)  
- **[Tony Blog](http://blog.tonycube.com/2015/10/java-java8-3-stream.html)**
- [flatMap](https://mkyong.com/java8/java-8-flatmap-example/)  
- **[Java 8 Stream](https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/)**
- [foreach](https://stackoverflow.com/questions/38021061/how-to-use-if-else-logic-in-java-8-stream-foreach)

**Stream API大量使用Java 8的lambda表達式來達到函式程式設計(Functional Programming)與流式介面(Fluent Interfaces)的寫法**
```java 
                                   Stream pipeline operations

                                          lazy-execution                                  Eager execution
     +--------+           +--------+     +-------------------------+                        +---------------------+
     |        |           |        |     |                         |                        |                     |
     | Source |---------->| Stream |---->| intermediate operations |----------------------->| terminal operations |
     |        |           |        |     |                         |                        |                     |
     +--------+           +--------+     +-------------------------+                        +---------------------+

e.g. Collection                          e.g. Predicate<? super T> predicate           e.g. collect()
     Arrays.stream(Object[])                  map()                                         forEach(x -> operations)
     Stream.of(Object[])                      flapMap()                                     findAny()
     BufferedReader.lines()                   peek()                                        findFirst()
     ...                                      sorted()                                      anyMatch()
                                              distinct()                                    allMatch()
                                              ...                           ...
```
1. Source : Usually is Collection or Array
2. intermediate OPs **(return a new Stream when lazy-execution each method is called)**
    - 中間操作的結果會返回新的Stream，所以能繼續更多的中間操作。中間操作是懶執行(lazy-executing)，**也就是執行到中間操作時並不會真的被執行，只有到終端操作被執行時前面的中間操作才會開始執行**
3. terminal Ops 終端操作遍歷前面中間操作的Stream並產生結果並結束整個管線操作。產生的結果可能是一個新的集合或只是對來源發生副作用

## Arrays.asList

```java
public static <T> List<T> asList(T... a); // immutable 

int arr[] = {1,2,3,4,5,6,7};
List intList = Arrays.asList(arr);

// Array has Object, not primitives 
List<Integer> list = Arrays.asList(1,2,3);
// or 
List<Integer> list = Arrays.asList(new Integer[] {1,2,3});

Stream<String> stream1 = Arrays.asList("a", "b", "c").stream();
```


```java
List<Integer> list = Arrays.asList(new int[] {1,2,3});
```
because primitive to wrapper coercion (ie. `int[]` to `Integer[]`) is not built into the language.   
As a result, each primitive type would have to be handled as it's own overloaded method, which is what the commons package does. i.e. `public static List<Integer> asList(int i...);`


## Arrays.stream(`T[]` arr)

```java
Stream<String> stream1 = Arrays.stream(new String[10]);

int[] arr = {1,2,3};

//In java 8+ 
List<Integer> list = Arrays.stream(arr) //IntStream
                           .boxed()     
                           .collect(Collectors.toList());
//In Java 16 and later:
List<Integer> list = Arrays.stream(arr)
                           .boxed()
                           .toList();
// via IntStream
IntStream.of(arr) // return IntStream
         .boxed()  // Stream<Integer>
         .collect(Collectors.toList());

Stream<String> stream3 = Arrays.stream(new String[] {"a", "b", "c"});

Stream<String> stream4 = new BufferedReader(new FileReader(new File("hello.txt"))).lines();
```

## Ints.asList(...)

```java
int[] arr = {1,2,3};
// via guava libraries
// {@code import com.google.common.primitives.Ints;} 
List<Integer> Ints.asList(arr);
``

## Stream.Iterate(...)

```java
Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
// 0, 3, 6, 9 ,12
```

## Stream.generate(...)

```java
Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
stream3.forEach(System.out::println);
```

## Collections,List,Set Stream

Streams can be created from various data sources, especially collections. Lists and Sets support new methods `stream()` and `parallelStream()` to either create a sequential or a parallel stream which is capable of operating on multiple threads. 

```java
Arrays.asList("a1", "a2", "a3")
      .stream()
      .findFirst()
      .ifPresent(System.out::println);  // a1
```

## Stream.of

```java
static <T> Stream<T> of(T... values)
static <T> Stream<T> of(T t)

Stream<String> streamOf = Stream.of("1", "3", "4", "5", "7","", "9");
```

## IntStream,DoubleStream,LongStream

### iterate

```java
DoubleStream
//       iterate(initializer,condition,iteration)
        .iterate(1.2, d -> d < 3, d -> d + 0.5)
        .forEach(System.out::println);

// similar  to
for (double d = 1.2; d < 3; d += 0.5) {
    System.out.println(d);
}
```
### rangeClosed and range

```java
static IntStream rangeClosed(int startInclusive, int endInclusive)
static IntStream range(int startInclusive, int endExclusive)

// same as
for (int i = startInclusive; i <= endInclusive ; i++) 
```

### boxed()

transform `T`Stream to a Stream<T>

```java 
DoubleStream.of(1.0, 5.0, 10.0) // DoubleStream
        .boxed() // stream<Double>
        .map(Double::intValue) // to Int
        .forEach(System.out::println);
```

### mapToObj

- [IntStream mapToObj() in Java](https://www.geeksforgeeks.org/intstream-maptoobj-java/)

Transform Primitive streams to object streams
```java
IntStream.range(1, 4) // IntStream
    .mapToObj(i -> "a" + i) // Stream<String>
    .forEach(System.out::println);
```

### parallel

Synchronized

```java
IntStream.range(5, 12).parallel().forEach(
    System.out::println
)
/**
 * 3
 * 5
 * 4
 * 1
 * 2
 */
```


## mapToInt, mapToLong ,mapToDouble,

```java
IntStream mapToInt(ToIntFunction<? super T> mapper)
LongStream mapToLong(ToLongFunction<? super T> mapper)
DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
```

```java
Stream
    // Stream<String>
    .of("a1", "a2", "a2") 
    // Stream<R> map(Function<? super T,? extends R> mapper)
    .map(s -> s.substring(1)) // "1", "2", "2"
    // IntStream mapToInt(ToIntFunction<? super T> mapper)
    .mapToInt(Integer::parseInt) // 1,2,2
    // Optional<T> max(Comparator<? super T> comparator)
    .max() // 3
    .ifPresent(System.out::println);  // 2
```


## intermediate operations

```java
Stream<T> filter(Predicate<? super T> predicate)
Stream<T> limit(int maxSize)
Stream<T> skip(long Skip_N_number)
Stream<T> distinct()
Stream<T> sorted(Comparator<? super T> comparator)
Stream<R> map(Function<? super T,? extends R> mapper)
Stream<T> peek(Consumer<? super T> action)
long count()

// filter
Stream
    .of("d2", "a2", "b1", "b3", "c")
    .filter(s -> {
        System.out.println("filter: " + s);
        return true;
    }).forEach(s -> System.out.println("forEach: " + s));

List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
Integer findFirst = list.stream().findFirst().get(); //1
Integer findAny = list.stream().findAny().get(); //1
long count = list.stream().count(); //5
Integer max = list.stream().max(Integer::compareTo).get(); //5
Integer min = list.stream().min(Integer::compareTo).get(); //1　　

// peek has return void unlike .map()
Stream<Student> stdStream = Stream.of(
                new Student(1, "a", 19, "male", 88), 
                new Student(2, "a", 18, "female", 90));

stdStream.peek(o -> o.setAge(100)).forEach(System.out::println);
// all student age are 100
```

## Terminal Operations `___Match`

```java
// if all elements meet the criteria
boolean allMatch = list.stream().allMatch(e -> e > 10); //false
// if none meet the criteria
boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
// if any ... 
boolean anyMatch = list.stream().anyMatch(e -> e > 4); //true
```

```java
Stream.of("d2", "a2", "b1", "b3", "c")
    .map(s -> {
        System.out.println("map: " + s);
        return s.toUpperCase();
    })
    .anyMatch(s -> {
        System.out.println("anyMatch: " + s);
        return s.startsWith("A");
    });
// map:      d2
// anyMatch: D2
// map:      a2
// anyMatch: A2
```
- The operation `anyMatch` returns `true` as soon as the predicate applies to the given input element.     
Due to the vertical execution of the stream chain, map has only to be executed twice in this case.     
So instead of mapping all elements of the stream, map will be called as few as possible.    

## Collectors

### Collectors.joining

```java
String a= Stream.of(3, 2, 3).map(String::valueOf).collect(Collectors.joining("," , "prefix ", " suffix"));
System.out.println(a);
// prefix 3,2,3 suffix
```

### Collectors.toXXXX()

```java
/**
 * new Student(name : a , age : 10)
 * new Student(name : b , id : 20)
 * new Student(name : c , id : 10)
 */

List<Integer> ageList = stdList.stream().map(Student::getAge).collect(Collectors.toList()); // [10, 20, 10]

Set<Integer> ageSet = stdList.stream().map(Student::getAge).collect(Collectors.toSet()); // [20, 10]
  
Map<String, Integer> studentMap = stdList.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {a=10, b=20, c=10}
```

### Collectors.counting()
```java
Long count = list.stream().collect(Collectors.counting()); // 3
```

### Collectors.maxBy(...)
```java
Integer maxAge = list.stream().map(Student::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
```

### summing---, average---, summarizing---
```java
// Sum (All student age)
Integer sumAge = students.stream().collect(Collectors.summingInt(Student::getAge)); 

// Average 
Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334


DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());
```  

### Collectors.groupingBy(....)

```java
Map<Integer, List<Student>> ageMap = list.stream().collect(Collectors.groupingBy(Student::getAge));
```

### Collectors.groupingBy(.... , Collectors.groupingBy(...))
```java
Map<String, Map<Integer, List<Student>>> typeAgeMap = 
        students.stream().collect(Collectors.groupingBy(Student::getSex, Collectors.groupingBy(Student::getAge)));

Sex       age
 ^         ^
{female = {20 = [Student(id=7, name=g, age=20, sex=female, score=66.0)], 22=[Student(id=9, name=i, age=22, sex=female, score=95.0)], 14=[Student(id=5, name=e, age=14, sex=female, score=95.0)]}, male={17=[Student(id=2, name=b, age=17, 
sex=male, score=60.0)], 18=[Student(id=1, name=a, age=18, sex=male, score=88.0), Student(id=8, name=h, age=18, sex=male, score=100.0)], 19=[Student(id=3, name=c, age=19, sex=male, score=100.0)], 20=[Student(id=4, name=d, age=20, sex=male, score=10.0)], 21=[Student(id=6, name=f, age=21, sex=male, score=55.0)], 25=[Student(id=10, name=j, age=25, sex=male, score=90.0)]}}
```  

### Collectors.partitioningBy(...)
```java
Map<Boolean, List<Student>> partMap = 
    list.stream().collect(Collectors.partitioningBy(v -> v.getAge() >= 18));
```
###  Collectors.reducing(...)
```java
Integer allAge = list.stream().map(Student::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40　　
```

## Exclude Null Element (`Objects::nonNull`)
- [Source Code](https://matthung0807.blogspot.com/2019/09/java-8-lambda-stream-collect-exclude.html)  

```java
List<Customer> customerList = new ArrayList<>();
customerList.add(new Customer(1L, "Ryu"));
customerList.add(new Customer(2L, "Ken"));
customerList.add(new Customer(3L, null));
customerList.add(null);
customerList.add(new Customer(5L, null));
customerList.add(new Customer(6L, "Zyan"));

List<String> nameList1 = customerList.stream()
        .filter(Objects::nonNull) // filter object == null
        .map(e -> e.getName())    
        .filter(Objects::nonNull) // filter object.getName == null
        .collect(Collectors.toList());
System.out.println(nameList1); // [Ryu, Ken, Zyan]
```

`.filter(Objects::nonNull)`為Method References（方法參考）的寫法，
原本的寫法是`.filter(e -> Objects.nonNull(e))`

`Objects.nonNull(Object obj)`相當於`obj != null`，原始碼如下。
```java
public static boolean nonNull(Object obj) {
    return obj != null;
}
```

## Order of intermediate operations

```java
Stream.of("d2", "a2", "b1", "b3", "c")
    .sorted((s1, s2) -> {
        System.out.printf("sort: %s; %s\n", s1, s2);
        return s1.compareTo(s2);
    })
    .filter(s -> {
        System.out.println("filter: " + s);
        return s.startsWith("a");
    })
    .map(s -> {
        System.out.println("map: " + s);
        return s.toUpperCase();
    })
    .forEach(s -> System.out.println("forEach: " + s));
// sort:    a2; d2
// sort:    b1; a2
// sort:    b1; d2
// sort:    b1; a2
// sort:    b3; b1
// sort:    b3; d2
// sort:    c; b3
// sort:    c; d2
// filter:  a2
// map:     a2
// forEach: A2
// filter:  b1
// filter:  b3
// filter:  c
// filter:  d2

Stream.of("d2", "a2", "b1", "b3", "c")
    .filter(s -> {
        System.out.println("filter: " + s);
        return s.startsWith("a");
    })
    .sorted((s1, s2) -> {
        System.out.printf("sort: %s; %s\n", s1, s2);
        return s1.compareTo(s2);
    })
    .map(s -> {
        System.out.println("map: " + s);
        return s.toUpperCase();
    })
    .forEach(s -> System.out.println("forEach: " + s));

// filter:  d2
// filter:  a2
// filter:  b1
// filter:  b3
// filter:  c
// map:     a2
// forEach: A2
```

## Reusing Stream

Java 8 streams cannot be reused. As soon as you call any terminal operation the stream is closed

To overcome this limitation we have to to create a new stream chain for every terminal operation we want to execute

```java
Supplier<Stream<String>> streamSupplier =
    () -> Stream.of("d2", "a2", "b1", "b3", "c")
            .filter(s -> s.startsWith("a"));

streamSupplier.get().anyMatch(s -> true);   // ok
streamSupplier.get().noneMatch(s -> true);  // ok

```
Each call to `get()` constructs a new stream on which we are save to call the desired terminal operation.

## Build Up Your Own Collector

Now that we know some of the most powerful built-in collectors, let’s try to build our own special collector. 

Assume We want to transform all persons of the stream into a single string consisting of all names in upper letters separated by the `|` pipe character. 

Since strings in Java are immutable, we need a helper class like StringJoiner to let the collector construct our string.  

The supplier initially constructs such a `StringJoiner` with the appropriate delimiter. 

The accumulator is used to add each persons upper-cased name to the StringJoiner. 

The combiner knows how to merge two `StringJoiners` into one.  

In the last step the finisher constructs the desired String from the `StringJoiner`.
```java
Collector<Person, StringJoiner, String> personNameCollector =
    Collector.of(
        () -> new StringJoiner(" | "),          // supplier
        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
        (j1, j2) -> j1.merge(j2),               // combiner
        StringJoiner::toString);                // finisher

String names = persons
    .stream()
    .collect(personNameCollector);

System.out.println(names);  // MAX | PETER | PAMELA | DAVID
```

## stream forEach

```java
map.entrySet().stream()
        .forEach(
                pair -> {
                    if (pair.getValue() != null) {
                        myMap.put(pair.getKey(), pair.getValue());
                    } else {
                        myList.add(pair.getKey());
                    }
                }
        );

// Better 
animalMap.forEach(
        (key, value) -> {
            if (value != null) {
                myMap.put(key, value);
            } else {
                myList.add(key);
            }
        }
);
```

## FlatMap

Map is kinda limited because every object can only be mapped to exactly one other object. 

But what if we want to transform one object into multiple others or none at all? This is where flatMap comes to the rescue.

**FlatMap transforms each element of the stream into a stream of other objects.(e.g. `stream<?>`)** So each object will be transformed into zero, one or multiple other objects backed by streams. 

The contents of those streams will then be placed into the returned stream of the flatMap operation.

Before we see flatMap in action we need an appropriate type hierarchy:
```java
@Data
class Foo {
    String name;
    List<Bar> bars = new ArrayList<>();
}

class Bar {
    String name;

    Bar(String name) {
        this.name = name;
    }
}
List<Foo> foos = new ArrayList<>();

// create foos
IntStream
    .range(1, 4)
    .forEach(i -> foos.add(new Foo("Foo" + i)));

// create bars
foos.forEach(f ->
    IntStream
        .range(1, 4)
        .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));
```

FlatMap accepts a function which has to return a stream of objects.    
So in order to resolve the bar objects of each foo, we just pass the appropriate function:
```java
// stream<foo>
foos.stream()
    // stream <bars>
    .flatMap(f -> f.bars.stream())
    .forEach(b -> System.out.println(b.name));

// Bar1 <- Foo1
// Bar2 <- Foo1
// Bar3 <- Foo1
// Bar1 <- Foo2
// Bar2 <- Foo2
// Bar3 <- Foo2
// Bar1 <- Foo3
// Bar2 <- Foo3
// Bar3 <- Foo3
```


The above code example can be simplified into a single pipeline of stream operations:
```java
IntStream.range(1, 4)
    .mapToObj(i -> new Foo("Foo" + i))
    .peek(f -> IntStream.range(1, 4)
        .mapToObj(i -> new Bar("Bar" + i + " <- " f.name))
        .forEach(f.bars::add))
    .flatMap(f -> f.bars.stream())  
    .forEach(b -> System.out.println(b.name));
```

FlatMap is also available for the Optional class introduced in Java 8.  

Optionals flatMap operation returns an optional object of another type. So it can be utilized to prevent nasty `null` checks.
```java
class Outer {
    Nested nested;
}

class Nested {
    Inner inner;
}

class Inner {
    String foo;
}
```
In order to resolve the inner string foo of an outer instance you have to add multiple `null` checks to prevent possible `NullPointerExceptions`:
```java
Outer outer = new Outer();
if (outer != null && 
    outer.nested != null && 
    outer.nested.inner != null) {
    System.out.println(outer.nested.inner.foo);
}
```
The same behavior can be obtained by utilizing optionals flatMap operation:
```java
Optional.of(new Outer())
    .flatMap(o -> Optional.ofNullable(o.nested))
    .flatMap(n -> Optional.ofNullable(n.inner))
    .flatMap(i -> Optional.ofNullable(i.foo))
    .ifPresent(System.out::println);
```
- Each call to flatMap returns an Optional wrapping the desired object if present or null if absent.

## Parallel Streams

Streams can be executed in parallel to increase runtime performance on large amount of input elements.    
Parallel streams use a common `ForkJoinPool` available via the `static ForkJoinPool.commonPool()` method.      
The size of the underlying thread-pool uses up to five threads - depending on the amount of available physical CPU cores:
```java
ForkJoinPool commonPool = ForkJoinPool.commonPool();
System.out.println(commonPool.getParallelism());    // 3
```

Collections support the method `parallelStream()` to create a parallel stream of elements.    
Alternatively you can call the intermediate method `parallel()` on a given stream to convert a sequential stream to a parallel counterpart.   

```java 
Arrays.sList("a1", "a2", "b1", "c2", "c1")
    .parallelStream()
    .filter(s -> {
        System.out.format("filter: %s [%s]\n",
            s, Thread.currentThread().getName());
            return true;
    })
    .map(s -> {
        System.out.format("map: %s [%s]\n",
            s, Thread.currentThread().getName());
        return s.toUpperCase();
    })
    .forEach(s -> System.out.format("forEach: %s [%s]\n",
        s, Thread.currentThread().getName()));

filter:  b1 [main]
filter:  a2 [ForkJoinPool.commonPool-worker-1]
map:     a2 [ForkJoinPool.commonPool-worker-1]
filter:  c2 [ForkJoinPool.commonPool-worker-3]
map:     c2 [ForkJoinPool.commonPool-worker-3]
filter:  c1 [ForkJoinPool.commonPool-worker-2]
map:     c1 [ForkJoinPool.commonPool-worker-2]
forEach: C2 [ForkJoinPool.commonPool-worker-3]
forEach: A2 [ForkJoinPool.commonPool-worker-1]
map:     b1 [main]
forEach: B1 [main]
filter:  a1 [ForkJoinPool.commonPool-worker-3]
map:     a1 [ForkJoinPool.commonPool-worker-3]
forEach: A1 [ForkJoinPool.commonPool-worker-3]
forEach: C1 [ForkJoinPool.commonPool-worker-2]
```
- the parallel stream utilizes all available threads from the common `ForkJoinPool` for executing the stream operations.   
The output may differ in consecutive runs because the behavior which particular thread is actually used is non-deterministic.


### parallelStream with `sorted`

```java 
Arrays.asList("a1", "a2", "b1", "c2", "c1")
    .parallelStream()
    .filter(s -> {
        System.out.format("filter: %s [%s]\n",
            s, Thread.currentThread().getName());
        return true;
    })
    .map(s -> {
        System.out.format("map: %s [%s]\n",
            s, Thread.currentThread().getName());
        return s.toUpperCase();
    })
    .sorted((s1, s2) -> {
        System.out.format("sort: %s <> %s [%s]\n",
            s1, s2, Thread.currentThread().getName());
        return s1.compareTo(s2);
    })
    .forEach(s -> System.out.format("forEach: %s [%s]\n",
        s, Thread.currentThread().getName()));

filter: b1 [main]
filter: a1 [ForkJoinPool.commonPool-worker-19]
filter: c2 [ForkJoinPool.commonPool-worker-9]
map: c2 [ForkJoinPool.commonPool-worker-9]
filter: c1 [ForkJoinPool.commonPool-worker-5]
map: c1 [ForkJoinPool.commonPool-worker-5]
filter: a2 [ForkJoinPool.commonPool-worker-23]
map: a2 [ForkJoinPool.commonPool-worker-23]
map: a1 [ForkJoinPool.commonPool-worker-19]
map: b1 [main]
sort: A2 <> A1 [main]
sort: B1 <> A2 [main]
sort: C2 <> B1 [main]
sort: C1 <> C2 [main]
sort: C1 <> B1 [main]
sort: C1 <> C2 [main]
forEach: B1 [main]
forEach: A1 [ForkJoinPool.commonPool-worker-19]
forEach: C1 [ForkJoinPool.commonPool-worker-9]
forEach: A2 [ForkJoinPool.commonPool-worker-27]
forEach: C2 [ForkJoinPool.commonPool-worker-23]
```
- sort on a parallel stream uses the Java 8 method `Arrays.parallelSort()` under the hood.    
This method decides on the length of the array if sorting will be performed sequentially or in parallel: If the length of the specified array is less than the minimum granularity, then it is sorted using the appropriate `Arrays.sort` method.   

## Reduce

The reduction operation combines all elements of the stream into a single result. 

This reduce method accepts a BinaryOperator accumulator function.    
```java
Optional<T> reduce​(BinaryOperator<T> accumulator)	
```

`BiFunctions` are like Function but accept two arguments.   
The following example function compares both persons ages in order to return the person with the maximum age.
```java
persons.stream().reduce(
    (p1, p2) -> p1.age > p2.age ? p1 : p2)
    .ifPresent(System.out::println);    // Pamela
```

This reduce method accepts both an identity value (initializer) and a BinaryOperator accumulator. 
```java
T reduce​(T identity, BinaryOperator<T> accumulator)
```

This method can be utilized to construct a new Person with the aggregated names and ages from all other persons in the stream:
```java
Person result =
    persons
        .stream()
        .reduce(new Person("", 0), (p1, p2) -> {
            p1.age += p2.age;
            p1.name += p2.name;
            return p1;
        });

System.out.format("name=%s; age=%s", result.name, result.age);
```

The third reduce method accepts three parameters: an identity value, a BiFunction accumulator and a combiner function of type BinaryOperator. 
```java
<U> U reduce​(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
```

Since the identity values type is not restricted to the Person type, we can utilize this reduction to determine the sum of ages from all persons:
```java
Integer ageSum = persons
    .stream()
    .reduce( 0 , 
           (sum, p) -> sum += p.age, 
           (sum1, sum2) -> sum1 + sum2);
System.out.println(ageSum);  
```

### accumulator & combiner

[Why is a combiner needed for reduce method that converts type in java 8](https://stackoverflow.com/questions/24308146/why-is-a-combiner-needed-for-reduce-method-that-converts-type-in-java-8)
```java
sequential stream

1,2,3,4 - accumulator -> 10 
```
parallel stream 
```java
3,4 - accumulator -> 7 --+
                         |-- combiner -> 10
1,2 - accumulator -> 3 --+
```


```java
Integer ageSum = persons
    .stream()
    .reduce(0,
        (sum, p) -> {
            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
            return sum += p.age;
        },
        // Never get called
        // combiner got called for multiple threads
        (sum1, sum2) -> {
            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
            return sum1 + sum2;
        });

// accumulator: sum=0; person=Max
// accumulator: sum=18; person=Peter
// accumulator: sum=41; person=Pamela
// accumulator: sum=64; person=David
```



Combiner get called in Parallel Stream
```java
List<Person> persons = Arrays.asList(
    new Person("Max", 18),
    new Person("Peter", 23),
    new Person("Pamela", 23),
    new Person("David", 12));

persons
    .parallelStream()
    .reduce(0,
           (sum, p) -> {
                System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                sum, p, Thread.currentThread().getName());
                return sum += p.age;
            },(sum1, sum2) -> {
                System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                sum1, sum2, Thread.currentThread().getName());
                return sum1 + sum2;
            }
    );

accumulator: sum=0; person=Pamela; [main]
accumulator: sum=0; person=Max;    [ForkJoinPool.commonPool-worker-3]
accumulator: sum=0; person=David;  [ForkJoinPool.commonPool-worker-2]
accumulator: sum=0; person=Peter;  [ForkJoinPool.commonPool-worker-1]
combiner:    sum1=18; sum2=23;     [ForkJoinPool.commonPool-worker-1]
combiner:    sum1=23; sum2=12;     [ForkJoinPool.commonPool-worker-2]
combiner:    sum1=41; sum2=35;     [ForkJoinPool.commonPool-worker-2]
```

In summary, it can be stated that parallel streams can bring be a nice performance boost to streams with a large amount of input elements.  
But keep in mind that **some parallel stream operations like `reduce` and `collect` need additional computations (combine operations) which isn’t needed when executed sequentially.**

