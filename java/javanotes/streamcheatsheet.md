

## for-loop

```java
static IntStream rangeClosed(int startInclusive, int endInclusive)
static IntStream range(int start_Inclusive, int end_Exclusive)

// for (int n = 1 ; n < 20 , n*2)
Stream.iterate(1, n -> n < 20 , n -> n * 2)
```

## mapToObj
```java
String testString = "String";
Stream<String> stringStream = testString.codePoints()
  .mapToObj(c -> String.valueOf((char) c));
```