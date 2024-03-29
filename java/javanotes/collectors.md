# Collectors
- [Collectors](#collectors)
  - [Reference](#reference)
  - [toList, toMap, toSet, toCollection](#tolist-tomap-toset-tocollection)
  - [Operations(reduce,joining)](#operationsreducejoining)
  - [CONVERSION(`mapping`,`flatMapping`,`collectingAndThen`)](#conversionmappingflatmappingcollectingandthen)
  - [filtering](#filtering)
  - [groupBy](#groupby)
    - [groupingBy(classifier\_1 , Collectors.groupingBy(classifier\_2))](#groupingbyclassifier_1--collectorsgroupingbyclassifier_2)
    - [groupingBy a Complex Map Key Type](#groupingby-a-complex-map-key-type)
  - [partitioningBy](#partitioningby)
  - [`Collectors.counting()`](#collectorscounting)
  - [Summing\* , Averaging\* and Summarizing\*](#summing--averaging-and-summarizing)
    - [Examples](#examples)
  - [`Collectors.maxBy` \& `Collectors.minBy`](#collectorsmaxby--collectorsminby)
  - [Collectors.reducing(...)](#collectorsreducing)

## Reference

- [Collectors.groupingBy](https://www.jianshu.com/p/21b20c375599)

## toList, toMap, toSet, toCollection

```java
// to list
static <T> Collector<T,?,List<T>> toList()	
// to set
static <T> Collector<T,?,Set<T>> toSet()	

// to map
static <T,K,U> Collector<T,?,Map<K,U>> toMap(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper)	
static <T,K,U> Collector<T,?,Map<K,U>> toMap(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper, 
    BinaryOperator<U> mergeFunction)	
static <T,K,U,M extends Map<K,U>> Collector<T,?,M> toMap(
    Function<? super T,? extends K> keyMapper, 
    Function<? super T,? extends U> valueMapper, 
    BinaryOperator<U> mergeFunction, 
    Supplier<M> mapFactory)	

static <T,C extends Collection<T>> Collector<T,?,C> toCollection(
    Supplier<C> collectionFactory)	

/************************ EXAMPLE **********************************************************
  List<Student> stdList = List.of(
    new Student(name : a , age : 10)
    new Student(name : b , age : 20)
    new Student(name : c , age : 10))
 *******************************************************************************************/
 
Map<String, Integer> studentMap = stdList.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); 
// {a=10, b=20, c=10}

List<Integer> ageList = stdList.stream().map(Student::getAge).collect(Collectors.toList()); 
// [10, 20, 10]

Set<Integer> ageSet = stdList.stream().map(Student::getAge).collect(Collectors.toSet()); 
// [20, 10]
```

## Operations(reduce,joining)

Concatenate the input elements into a String in encounter
- Parameter `delimiter` : input elements are separated by the specified delimiter, in encounter order (with the specified `prefix` and `suffix`).

```java
static Collector<CharSequence,?,String>	joining(
    ?CharSequence delimiter,
    ?CharSequence prefix, 
    ?CharSequence suffix)

// HTTP
HttpServletRequest.getReader().lines().collect(Collectors.joining()); 

List<String> stringList = new ArrayList(Arrays.asList("a","b","c"));
String joinings = stringList.stream().collect(Collectors.joining(",", 
                "Im-prefix ", 
                " Im-suffix"));
// Im-prefix a,b,c Im-suffix

String a = Stream.of(3, 2, 3)
                .map(String::valueOf)
                .collect(
                    Collectors.joining("," , "prefix- ", " -suffix")
                ); 
// prefix- 3,2,3 -suffix
```

a reduction of its input elements under a specified BinaryOperator.
```java
static <T> Collector<T,?,Optional<T>> reducing(
    BinaryOperator<T> op)	

// A reduction of its input elements 
// under a specified BinaryOperator.
// using the provided identity
static <T> Collector<T,?,T> reducing(
    T identity, 
    BinaryOperator<T> op)	

// a reduction of its input elements 
// under a specified mapping function 
// and BinaryOperator.
static <T,U> Collector<T,?,U> reducing(
    U identity, Function<? super T,? extends U> mapper, 
    BinaryOperator<U> op)	
```

## CONVERSION(`mapping`,`flatMapping`,`collectingAndThen`)

```java
static <T,U,A,R> Collector<T,?,R> flatMapping(
    Function<? super T,? extends Stream<? extends U>> mapper, 
    Collector<? super U,A,R> downstream)	
```
```java 
static <T,U,A,R> Collector<T,?,R> mapping(
    Function<? super T,? extends U> mapper, 
    Collector<? super U,A,R> downstream)	
    
// Examples 

givenArrayList.stream.collect(Collectors.mapping(
    s -> s.substring(1), Collectors.toList()));    
// equals
givenArrayList.stream.map(s -> s.substring(1))
                    .collect(Collectors.toList());    
```

```java
static <T,A,R,RR> Collector<T,A,RR> collectingAndThen(
    Collector<T,A,R> downstream, 
    Function<R,RR> finisher)	
/**
 * Collect Stream elements to a List instance,
 * and then convert the result into an ImmutableList instance
 */
List<String> result = givenArrayList.stream()
  .collect(collectingAndThen(toList(), ImmutableList::copyOf))
```

## filtering

```java
static <T,A,R> Collector<T,?,R>	filtering(
    Predicate<? super T> predicate, 
    Collector<? super T,A,R> downstream)	
``` 

## groupBy 


```java 
// Return a Collector which groups the input elements according
// to classifier, and organizes them into a Map<K,List<T>>
static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy(
    Function<? super T,? extends K> classifier)	

/**
@Data
public class Fruit{
    private String name;
    private float price;
}

List<Fruit> fruitList = Arrays.asList(
            new Fruit("apple" , 6),
            new Fruit("apple" , 6),
            new Fruit("banana", 7), 
            new Fruit("banana", 7),
            new Fruit("banana", 7), 
            new Fruit("grape" , 8));
*/
Map<String, List<Fruit>>map = fruitList.stream()
    .collect(Collectors.groupingBy(Fruit::getName));

// output  
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
Map<String, Long> map = fruitList.stream().collect(
            Collectors.groupingBy(
                // key
                Fruit::getName,
                // value
                Collectors.counting()));
// or
Map<String, Long> map = fruitList.stream().map(Fruit::getName).
        collect(Collectors.groupingBy(
            Function.identity(), 
            Collectors.counting())
        );
// {banana=3, apple=2, grape=1}

Map<String, Integer> sumMap = fruitList.stream().collect(
    Collectors.groupingBy(
        Fruit::getName, 
        Collectors.summingInt(Fruit::getPrice))
    );

Map<String, List<Integer>> map = fruitList.stream().collect(
    Collectors.groupingBy(
        Fruit::getName,  
        Collectors.mapping(
            Fruit::getPrice, 
            Collectors.toList())
    )
);
// {banana=[7, 7, 7], apple=[6, 6], grape=[8]}


List<Item> items = Arrays.asList(
        new Item("apple", 10, new BigDecimal("9.99")),
        new Item("banana", 20, new BigDecimal("19.99")),
        new Item("orang", 10, new BigDecimal("29.99")),
        new Item("watermelon", 10, new BigDecimal("29.99")),
        new Item("papaya", 20, new BigDecimal("9.99")),
        new Item("apple", 10, new BigDecimal("9.99")),
        new Item("banana", 10, new BigDecimal("19.99")),
        new Item("apple", 20, new BigDecimal("9.99"))
        );

//group by price
Map<BigDecimal, List<Item>> groupByPriceMap = 
	items.stream().collect(Collectors.groupingBy(Item::getPrice));
	
System.out.println(groupByPriceMap);
/** output **/
19.99=[
		Item{name='banana', qty=20, price=19.99}, 
		Item{name='banana', qty=10, price=19.99}
	], 
29.99=[
		Item{name='orang', qty=10, price=29.99}, 
		Item{name='watermelon', qty=10, price=29.99}
	], 
9.99=[
		Item{name='apple', qty=10, price=9.99}, 
		Item{name='papaya', qty=20, price=9.99}, 
		Item{name='apple', qty=10, price=9.99}, 
		Item{name='apple', qty=20, price=9.99}
	]


// group by price and use 'mapping' to convert List<Item> to Set<String>
Map<BigDecimal, Set<String>> result =
        items.stream().collect(
                Collectors.groupingBy(Item::getPrice,
                        Collectors.mapping(Item::getName, Collectors.toSet())
			// 
                )
        );
	
/** output **/
{
	19.99=[banana], 
	29.99=[orang, watermelon], 
	9.99=[papaya, apple]
}
```

### groupingBy(classifier_1 , Collectors.groupingBy(classifier_2))
```java
/    3 layers
//   '            '        '            
Map<String, Map<Integer, List<Student>>> typeAgeMap = 
        students.stream().collect(Collectors.groupingBy(Student::getSex, Collectors.groupingBy(Student::getAge)));


/** output **/
    Sex={...}   age=[...]
     ^          ^
{    |          | 
    female ={   20=[Student(id=7, name=g, age=20, sex=female, score=66.0)],
                22=[Student(id=9, name=i, age=22, sex=female, score=95.0)], 
                14=[Student(id=5, name=e, age=14, sex=female, score=95.0)]
            }, 
    male   ={   17=[Student(id=2, name=b, age=17, sex=male, score=60.0)], 
                18=[Student(id=1, name=a, age=18, sex=male, score=88.0), 
                    Student(id=8, name=h, age=18, sex=male, score=100.0)], 
                19=[Student(id=3, name=c, age=19, sex=male, score=100.0)], 
                20=[Student(id=4, name=d, age=20, sex=male, score=10.0)], 
                21=[Student(id=6, name=f, age=21, sex=male, score=55.0)], 
                25=[Student(id=10,name=j, age=25, sex=male, score=90.0)]
            }}
```  


### groupingBy a Complex Map Key Type

- [source code](https://www.baeldung.com/java-groupingby-collector)
```java
class BlogPost {
    String title;
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
    posts.stream().collect(
        groupingBy(
        post -> 
        new ImmutablePair<>(post.getType(), post.getAuthor())));

Map<Tuple, List<BlogPost>> postsPerTypeAndAuthor = 
    posts.stream().collect(
        groupingBy(
            post -> 
            new Tuple(post.getType(), post.getAuthor())));

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

Map<BlogPost.AuthPostTypesLikes, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
  .collect(groupingBy(post -> new BlogPost.AuthPostTypesLikes(post.getAuthor(), post.getType(), post.getLikes())));

Map<BlogPostType, String> postsPerType = posts.stream()
  .collect(groupingBy(BlogPost::getType, 
            mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));\

EnumMap<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
  .collect(groupingBy(BlogPost::getType, 
  () -> new EnumMap<>(BlogPostType.class), toList()));

Map<BlogPostType, IntSummaryStatistics> likeStatisticsPerType = posts.stream()
  .collect(groupingBy(BlogPost::getType, 
  summarizingInt(BlogPost::getLikes)));
Map<BlogPostType, Integer> likesPerType = posts.stream()
  .collect(
    groupingBy(
        BlogPost::getType, summingInt(BlogPost::getLikes)));

Map<BlogPostType, Double> averageLikesPerType = posts.stream()
  .collect(
    groupingBy(
        BlogPost::getType, 
        averagingInt(BlogPost::getLikes)));

Map<BlogPostType, Set<BlogPost>> postsPerType = posts.stream()
  .collect(
    groupingBy(
        BlogPost::getType, toSet()));

Map<BlogPostType, Optional<BlogPost>> maxLikesPerPostType = posts.stream()
  .collect(
    groupingBy(
        BlogPost::getType,maxBy(
            comparingInt(BlogPost::getLikes))));

int maxValLikes = 17;
Map<String, BlogPost.TitlesBoundedSumOfLikes> postsPerAuthor = posts.stream()
  .collect(toMap(BlogPost::getAuthor, post -> {
    int likes = (post.getLikes() > maxValLikes) ? maxValLikes : post.getLikes();
    return new BlogPost.TitlesBoundedSumOfLikes(post.getTitle(), likes);
  }, (u1, u2) -> {
    int likes = (u2.boundedSumOfLikes() > maxValLikes) ? maxValLikes : u2.boundedSumOfLikes();
    return new BlogPost.TitlesBoundedSumOfLikes(u1.titles().toUpperCase() + " : " + u2.titles().toUpperCase(), u1.boundedSumOfLikes() + likes);
  }));

Map<String, BlogPost.PostCountTitlesLikesStats> postsPerAuthor = posts.stream()
  .collect(groupingBy(BlogPost::getAuthor, collectingAndThen(toList(), list -> {
    long count = list.stream()
      .map(BlogPost::getTitle)
      .collect(counting());
    String titles = list.stream()
      .map(BlogPost::getTitle)
      .collect(joining(" : "));
    IntSummaryStatistics summary = list.stream()
      .collect(summarizingInt(BlogPost::getLikes));
    return new BlogPost.PostCountTitlesLikesStats(count, titles, summary);
  })));
```

## partitioningBy

```java
// Returns a Collector which partitions the input elements according 
// to a Predicate, and organizes them into a Map<Boolean, List<T>>.
static <T> Collector<T,?,Map<Boolean,List<T>>> partitioningBy(
    Predicate<? super T> predicate)	

static <T,D,A> Collector<T,?,Map<Boolean,D>> partitioningBy(
    Predicate<? super T> predicate, 
    Collector<? super T,A,D> downstream)	


Map<Boolean, List<Student>> partMap = fruitList.stream().collect(
		Collectors.partitioningBy(
			v -> 
			v.getPice() >= 5.0));
```


## `Collectors.counting()`

same as `count()`  
```java
static <T> Collector<T,?,Long> counting()	
Long count = theList.stream().collect(Collectors.counting()); // 3
```

## Summing* , Averaging* and Summarizing*

Summing*
- Returns a Collector that produces the sum of a double-valued function applied to the input elements.
```java
static <T> Collector<T,?,Double> summingDouble(ToDoubleFunction<? super T> mapper)	
static <T> Collector<T,?,Integer> summingInt(ToIntFunction<? super T> mapper)	
static <T> Collector<T,?,Long> summingLong(ToLongFunction<? super T> mapper)	
```

Averaging*
- Returns a Collector that produces the arithmetic mean of an `integer/Double/Long/`-valued function applied to the input elements.
```java
static <T> Collector<T,?,Double> averagingDouble(ToDoubleFunction<? super T> mapper)	
static <T> Collector<T,?,Double> averagingInt(ToIntFunction<? super T> mapper)	
static <T> Collector<T,?,Double> averagingLong(ToLongFunction<? super T> mapper)	
```

summarizing*
- Returns a Collector which applies an double/integer-producing mapping function to each input element, and returns summary statistics(`counting`, `summing*`,`min`,`average`,`max`) for the resulting values.
```java
static <T> Collector<T,?,DoubleSummaryStatistics> summarizingDouble(
    ToDoubleFunction<? super T> mapper)	

static <T> Collector<T,?,IntSummaryStatistics> summarizingInt(
    ToIntFunction<? super T> mapper)	

static <T> Collector<T,?,LongSummaryStatistics>	summarizingLong(
    ToLongFunction<? super T> mapper)	
```

### Examples 

```java
// Sum (All students' age)
Integer sumAge = students.stream().collect(Collectors.summingInt(Student::getAge)); 

// Average 
Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334

// return Statistics
DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
System.out.println("count:" + statistics.getCount() + 
                   ",max:" + statistics.getMax() + 
                   ",sum:" + statistics.getSum() + 
                   ",average:" + statistics.getAverage());
```  

## `Collectors.maxBy` & `Collectors.minBy`
- [source code](https://www.techiedelight.com/collectors-minby-maxby-method-java/)

Find max by given function
```java
Payroll p1 = new Payroll("Employee1", 115000);
Payroll p2 = new Payroll("Employee2", 100000);
Payroll p3 = new Payroll("Employee3", 120000);
List<Payroll> salaries = Arrays.asList(p1, p2, p3);

// get a person with the minimum income
Payroll min = salaries.stream()
                    .collect(Collectors.minBy(
                        Comparator.comparingInt(Payroll::getIncome)))
                    .get();

System.out.println("Employee with minimum Salary " + min);

// get a person with the maximum income
Payroll max = salaries.stream()
                    .collect(
                        Collectors.maxBy(
                            Comparator.comparingInt(Payroll::getIncome)))
                    .get();

System.out.println("Employee with maximum Salary " + max);

// get a person with the minimum income
Payroll min = salaries.stream()
                //                         current min
                //                         '   
                .collect(Collectors.minBy((x, y) -> x.getIncome() - y.getIncome()))
                .get();
 
// get a person with the maximum income
Payroll max = salaries.stream()
                .collect(
                    //            current max
                    //                '
                    Collectors.maxBy((x, y) -> x.getIncome() - y.getIncome()))
                .get();
```


##  Collectors.reducing(...)
```java
/**
 * stdList = [
 * {name : tom , age = 20},
 * {name : simon, age= 20}
* ]
 */
Integer allAge = stdList.stream()
    .map(Student::getAge)
    .collect(
        Collectors.reducing(
            Integer::sum))
    .get(); //40　　
```
