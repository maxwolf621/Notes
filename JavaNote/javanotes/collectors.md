# Collectors

- [Collectors](#collectors)
  - [Reference](#reference)
  - [toList, toMap, toSet, toCollection](#tolist-tomap-toset-tocollection)
  - [Operations(reduce,joining)](#operationsreducejoining)
  - [CONVERSION(mapping,flatMapping,collectingAndThen​)](#conversionmappingflatmappingcollectingandthen)
  - [Filtering](#filtering)
  - [groupBy & partitionBy](#groupby--partitionby)
  - [maxBy/minBy & counting](#maxbyminby--counting)
  - [Summing---](#summing---)
  - [Averaging---](#averaging---)
  - [Summarizing---](#summarizing---)


## Reference

- [Collectors.groupingBy](https://www.jianshu.com/p/21b20c375599)


## toList, toMap, toSet, toCollection

```java
static <T> Collector<T,?,List<T>> toList​()	
static <T> Collector<T,?,Set<T>> toSet​()	


static <T,K,U> Collector<T,?,Map<K,U>> toMap​(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper)	
static <T,K,U> Collector<T,?,Map<K,U>> toMap​(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper, 
    BinaryOperator<U> mergeFunction)	
static <T,K,U,M extends Map<K,U>> Collector<T,?,M> toMap​(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper, 
    BinaryOperator<U> mergeFunction, 
    Supplier<M> mapFactory)	

static <T,C extends Collection<T>> Collector<T,?,C> toCollection​(
    Supplier<C> collectionFactory)	
```

## Operations(reduce,joining)

concatenates the input elements into a String in encounter
Parameter `delimiter` : input elements are separated by the specified delimiter, in encounter order (with the specified `prefix` and `suffix`).

```java
static Collector<CharSequence,?,String>	joining​(
    ?CharSequence delimiter,
    ?CharSequence prefix, 
    ?CharSequence suffix)

HttpServletRequest.getReader().lines().collect(Collectors.joining()); 


List<String> stringList = 
    new ArrayList(Arrays.asList("a","b","c"));
// Im-prefix a,b,c Im-suffix
String joinings = stringList.stream()
    .collect(Collectors.joining(",", 
                "Im-prefix ", 
                " Im-suffix"));
```



a reduction of its input elements under a specified BinaryOperator.
```java
static <T> Collector<T,?,Optional<T>> reducing​(
    BinaryOperator<T> op)	

// a reduction of its input elements 
// under a specified BinaryOperator.
// using the provided identity
static <T> Collector<T,?,T>	reducing​(
    T identity, 
    inaryOperator<T> op)	

// a reduction of its input elements 
// under a specified mapping function 
// and BinaryOperator.
static <T,U> Collector<T,?,U> reducing​(
    U identity, Function<? super T,? extends U> mapper, 
    BinaryOperator<U> op)	
```


## CONVERSION(mapping,flatMapping,collectingAndThen​)

```java
static <T,U,A,R> Collector<T,?,R> mapping​(
    Function<? super T,? extends U> mapper, 
    Collector<? super U,A,R> downstream)	
// Examples 
givenArrayList.stream.collect(Collectors.mapping(
    s -> s.substring(1), Collectors.toList()));    
// equals
givenArrayList.stream.map(s -> s.substring(1))
                    .collect(Collectors.toList());    

static <T,U,A,R> Collector<T,?,R> flatMapping​(
    Function<? super T,? extends Stream<? extends U>> mapper, 
    Collector<? super U,A,R> downstream)	
```

```java
static <T,A,R,RR> Collector<T,A,RR>	collectingAndThen​(
    Collector<T,A,R> downstream, 
    Function<R,RR> finisher)	
/**
 * collect Stream elements to a List instance,
 * and then convert the result into an ImmutableList instance
 */
List<String> result = givenList.stream()
  .collect(collectingAndThen(toList(), ImmutableList::copyOf))
```

## Filtering

```java
static <T,A,R> Collector<T,?,R>	filtering​(
    Predicate<? super T> predicate, 
    Collector<? super T,A,R> downstream)	
``` 

## groupBy & partitionBy

```java 
@Data
public class Fruit{
    private String name;
    private float price;
}

List<Fruit> fruitList = Arrays.asList(
            new Fruit("apple", 6),
            new Fruit("apple", 6),
            new Fruit("banana", 7), 
            new Fruit("banana", 7),
            new Fruit("banana", 7), 
            new Fruit("grape",8));
```



```java
// Returns a Collector which partitions the input elements according 
// to a Predicate, and organizes them into a Map<Boolean, List<T>>.
static <T> Collector<T,?,Map<Boolean,List<T>>> partitioningBy​(
    Predicate<? super T> predicate)	

static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy​(
    Function<? super T,? extends K> classifier)	

Map<String, List<Fruit>>map = fruitList.stream()
    .collect(Collectors.groupingBy(Fruit::getName));

{
    banana=[
        Fruit(name=banana, price=7.0),
        Fruit(name=banana, price=7.0), 
        Fruit(name=banana, price=7.0)], 
    apple=[
        Fruit(name=apple, price=6.0), 
        Fruit(name=apple, price=6.0)], 
    grape=[
        Fruit(name=grape, price=8.0)]
}
```

```java
static <T,D,A> Collector<T,?,Map<Boolean,D>> partitioningBy​(
    Predicate<? super T> predicate, 
    Collector<? super T,A,D> downstream)	


Map<String, Long> map = fruitList.stream().collect(
            Collectors.groupingBy(
                Fruit::getName,
                Collectors.counting()));

// or
Map<String, Long> map = fruitList.stream().map(Fruit::getName).
        collect(Collectors.groupingBy(
            Function.identity(), Collectors.counting()));

{banana=3, apple=2, grape=1}

Map<String, Integer> sumMap = fruitList.stream().collect.
(Collectors.groupingBy(Fruit::getName, Collectors.summingInt(Fruit::getPrice)));

Map<String, List<Integer>> map = fruitList.stream().collect(
    Collectors.groupingBy(
        Fruit::getName, 
        Collectors.mapping(
            Fruit::getPrice, 
            Collectors.toList())
    )
);
{banana=[7, 7, 7], apple=[6, 6], grape=[8]}


```java
class BlogPost {
    String title;p
    String author;
    BlogPostType type;
    int likes;
}
enum BlogPostType {
    NEWS,
    REVIEW,
    GUIDE
}
class Tuple {
    BlogPostType type;
    String author;
}

Map<Pair<BlogPostType, String>, List<BlogPost>> postsPerTypeAndAuthor = 
    posts.stream().collect(groupingBy(
        post -> new ImmutablePair<>(post.getType(), post.getAuthor())));

Map<Tuple, List<BlogPost>> postsPerTypeAndAuthor = 
    posts.stream()
        .collect(groupingBy(
            post -> new Tuple(post.getType(), post.getAuthor())));
```


```java
public class BlogPost {
    private String title;
    private String author;
    private BlogPostType type;
    private int likes;

    record AuthPostTypesLikes(String author, BlogPostType type, int likes) {};
    
    // constructor, getters/setters
}

Map<BlogPost.AuthPostTypesLikes, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
  .collect(
    groupingBy(post -> new BlogPost.AuthPostTypesLikes(
        post.getAuthor(), 
        post.getType(),
        post.getLikes())));
```


## maxBy/minBy & counting

Returns `Optional<T>` of max/min values from given collection

```java
static <T> Collector<T,?,Optional<T>> maxBy​(
    Comparator<? super T> comparator)	
static <T> Collector<T,?,Optional<T>> minBy​(
    Comparator<? super T> comparator)	

// strList : ["a","b", "c"]
// return c
stringList.stream().collect(Collectors.maxBy(Comparator.naturalOrder())).get()
```


```java
static <T> Collector<T,?,Long> counting​()	
stringList.stream().collect(Collectors.counting());
```


## Summing---

Returns a Collector that produces the sum of a double-valued function applied to the input elements.

```java
static <T> Collector<T,?,Double> summingDouble​(ToDoubleFunction<? super T> mapper)	
static <T> Collector<T,?,Integer> summingInt​(ToIntFunction<? super T> mapper)	
static <T> Collector<T,?,Long> summingLong​(ToLongFunction<? super T> mapper)	
```

## Averaging---

Returns a Collector that produces the arithmetic mean of an `integer/Double/Long/`-valued function applied to the input elements.
```java
static <T> Collector<T,?,Double> averagingDouble​(ToDoubleFunction<? super T> mapper)	
static <T> Collector<T,?,Double> averagingInt​(ToIntFunction<? super T> mapper)	
static <T> Collector<T,?,Double> averagingLong​(ToLongFunction<? super T> mapper)	
```

## Summarizing---

Returns a Collector which applies an double/integer-producing mapping function to each input element, and returns summary statistics(`counting`, `summing---`,`min`,`average`,`max`) for the resulting values.

```java
static <T> Collector<T,?,DoubleSummaryStatistics> summarizingDouble​(
    ToDoubleFunction<? super T> mapper)	

static <T> Collector<T,?,IntSummaryStatistics> summarizingInt​(
    ToIntFunction<? super T> mapper)	

static <T> Collector<T,?,LongSummaryStatistics>	summarizingLong​(
    ToLongFunction<? super T> mapper)	
```
