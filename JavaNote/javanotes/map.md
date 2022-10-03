# Map

- [Map](#map)
  - [Reference](#reference)
  - [Usage](#usage)
  - [Initializer (Immutable)](#initializer-immutable)
    - [`{{put(key,value); ... }}`](#putkeyvalue--)
    - [`stream.of(new Object{...})`](#streamofnew-object)
    - [java 9 `Map.of`](#java-9-mapof)
    - [java 9 `Map.ofEntries`](#java-9-mapofentries)
    - [`Collections.singletonMap(key,value)`](#collectionssingletonmapkeyvalue)
  - [Methods](#methods)
    - [entrySet](#entryset)
    - [get & put](#get--put)
    - [replace](#replace)
    - [compute & computeIf ComputeIfAbsent](#compute--computeif-computeifabsent)
    - [Merge](#merge)
  - [TreeMap](#treemap)
    - [Methods in SortedMap](#methods-in-sortedmap)
      - [Get FirstKey or LastKey](#get-firstkey-or-lastkey)
      - [tailMap(K equalOrLarge) and headMap(K small)](#tailmapk-equalorlarge-and-headmapk-small)
      - [subMap(lowBound_inclusive, upBound_exclusive)](#submaplowbound_inclusive-upbound_exclusive)
    - [Methods in NavigableMap](#methods-in-navigablemap)
      - [Higher/Lower-Key & Higher/Lower-Entry , ceiling/floor-Key & ceiling/floor-Entry](#higherlower-key--higherlower-entry--ceilingfloor-key--ceilingfloor-entry)
      - [PollLastEntry & PollFirstEntry](#polllastentry--pollfirstentry)
  - [LinkedHashMap](#linkedhashmap)

![圖 1](../images/map.png)  

## Reference

[TreeMap](https://www.geeksforgeeks.org/treemap-in-java/)  
[LinkedHashMap](https://www.geeksforgeeks.org/linkedhashmap-class-in-java/)  
[SortedMap](https://jenkov.com/tutorials/java-collections/sortedmap.html)  
[NavigableMap](https://jenkov.com/tutorials/java-collections/navigablemap.html)
[Java-TreeMap](https://www.cainiaojc.com/java/java-treemap.html)

## Usage

HashMap
- HashMap offers `O(1)` lookup and insertion.   
It is implemented by an array of linked lists. 
- It contains only unique elements.
- **It may have one `null` key and multiple `null` values.**
- It maintains no order.

LinkedHashMap
- It is same as HashMap instead maintains **insertion order**.

TreeMap
- TreeMap offers `O(log N)` lookup and insertion. Keys are ordered, this means that keys must implement the Comparable interface.  
- TreeMap is implemented by a Red-Black Tree.   
- It cannot have null key but multiple null values.
- It is same as HashMap instead maintains ascending order
(Sorted using the natural order of its key).

## Initializer (Immutable)
### `{{put(key,value); ... }}`
```java
Map<String, String> doubleBraceMap  = new HashMap<String, String>() {{
    put("key1", "value1");
    put("key2", "value2");
}};
```

### `stream.of(new Object[][]{...})`

```java
Map<String, Integer> map = Stream.of(new Object[][] { 
     { "data1", 1 }, 
     { "data2", 2 }, 
 }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
```
```java
// use collect(Collectors.collectingAndThen(..., then ...))
Map<String, String> map = Stream.of(new String[][] { 
    { "Hello", "World" }, 
    { "John", "Doe" },
}).collect(Collectors.collectingAndThen(
    Collectors.toMap(data -> data[0], data -> data[1]), 
    Collections::<String, String> unmodifiableMap));
```

### java 9 `Map.of`

`Map.of` supports only maximum of 10 key-value pair 
```java
Map<String, String> emptyMap = Map.of();
Map<String, String> map = Map.of("key1","value1", "key2", "value2");
```

p.s Java 9 `List.of` and `Set.of` will be compiled 
```java
List<Integer> values = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // note 11 elements here

Set<String> keys = Set.of("z", "o", "tw", "th", "fo", "fi", "si", "se", "e", "n", "te");
```

### java 9 `Map.ofEntries`

- [What is the use of Map.ofEntries() instead of Map.of()](https://stackoverflow.com/questions/46601959/what-is-the-use-of-map-ofentries-instead-of-map-of)

`Map.ofEntries` has no limitations on the number of key-value pairs:
- They disallow null keys and values. Attempts to create them using a null key or value result in NullPointerException.
- They are immutable. Calls to `Entry.setValue()` on a returned Entry result in UnsupportedOperationException.
- They are not serializable.
```java
Map<String, Integer> map = Map.ofEntries(Map.entry("name","John"),
       Map.entry("city","Berlin"),Map.entry("zip","73210"), Map.entry("home","St.54321"));

Map<String, String> map = Map.ofEntries(
  new AbstractMap.SimpleEntry<String, String>("name", "John"),
  new AbstractMap.SimpleEntry<String, String>("city", "Berlin"),
  new AbstractMap.SimpleEntry<String, String>("zip", "73210"),
  new AbstractMap.SimpleEntry<String, String>("home", "St.54321")
);
```


### `Collections.singletonMap(key,value)`

```java
public static Map<String, String> createSingletonMap() {
    return Collections.singletonMap("username1", "password1");
}
```

## Methods 

From Collection
```java
void clear()
int	size()
boolean	isEmpty()
Object clone()
V remove(Object key)
```


```java
void forEach(BiConsumer<? super K,? super V> action)
boolean	remove(Object key, Object value)
boolean	containsKey(Object key)
boolean	containsValue(Object value)
Collection<V> values()
Set<K> keySet()
```

### entrySet
```java
Set<Map.Entry<K,V>> entrySet()
Map<Integer, String> sites = Map.of(
        1,"Google",
        2,"Youtube",
        3,"Github"
    );
sites.entrySet().forEach(System.out::println);
```

### get & put

```java
V get(Object key)
default V getOrDefault(Object key,
                       V defaultValue)
default V putIfAbsent(K key,
                      V value)

V put(K key, V value) 
void putAll(Map<? extends K,? extends V> map)
```

### replace

`V replace(K key, V value)`  
- Replaces the entry for the specified key only if it is currently mapped to some value.

`boolean replace(K key, V oldValue, V newValue)`  
- Replaces the entry for the specified key only if currently mapped to the specified value.   

`void replaceAll(BiFunction<? super K,? super V,? extends V> function)`
```java
maps.replaceAll((key, value) -> value.toUpperCase());
```


### compute & computeIf ComputeIfAbsent

**If key is not null then compute (operate) this key,value pair.**

`V compute(K specifiedKey, BiFunction<? super K,? super V,? extends V> remappingFunction)`
Attempts to compute a mapping for the `specifiedKey` and its current mapped value (or null if there is no current mapping).
```java
int newPrice = prices.compute("Shoes", (key, value) -> value - value * 10/100);
```

`V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)`
If the specified key is not already associated with a value (or is mapped to `null`), attempts to compute its value using the given mapping function and enters it into this map unless `null`.

```java
Map<String, Integer> stringLength = new HashMap<>();
// if John is absent then set a new map ["john", 4]
assertEquals((long)stringLength.computeIfAbsent("John", s -> s.length()), 4);
assertEquals((long)stringLength.get("John"), 4);
```


`V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)`
If the value for the specified key is present and non-null, attempts to compute a new mapping given the key and its current mapped value.


### Merge

```java
// 10 entries restriction
Map<String, String> countries = Map.of(
    "Washington", "America",
    "Canberra", "Australia",
    "Madrid", "Spain"
);
//                                           key     newValue
String returnedValue = countries.merge("Washington", "USA", (oldValue, newValue) -> oldValue + "/" + newValue);
```

## TreeMap 

The class implements Map interfaces including `NavigableMap`, `SortedMap`, and extends `AbstractMap` class.

The map is **SORTED** according to the **natural ordering** of its keys, or by a **Comparator** provided at map creation time, depending on which constructor is used. 
- This proves to be an efficient way of sorting and storing the key-value pairs. 

TreeMap for multiple thread 
```java
SortedMap m = Collections.synchronizedSortedMap(new TreeMap(...)); 
```

A TreeMap is based upon a red-black tree data structure. 
```java
Variables (K key=Key, V value=Value, boolean color=Color) 
References (Entry left = Left, Entry right = Right, Entry parent = Parent)
```

Constructor
```java
TreeMap()
TreeMap(Comparator comp)
TreeMap(Map M)
TreeMap(SortedMap sm)


Map<Integer, String> hash_map = new HashMap<Integer, String>();

TreeMap<Integer, String> tree_map = new TreeMap<Integer, String>(hash_map);

SortedMap<Integer, String> sorted_map = new ConcurrentSkipListMap<Integer, String>();

TreeMap<Integer, String> tree_map = new TreeMap<Integer, String>(sorted_map);
```

### Methods in SortedMap

```java
SortedMap sortedMap = new TreeMap();
sortedMap.put("a", "1");
sortedMap.put("c", "3");
sortedMap.put("e", "5");
sortedMap.put("d", "4");
sortedMap.put("b", "2");
```
#### Get FirstKey or LastKey 

```java
String firstKey = (String) sortedMap.firstKey();
String lastKey = (String) sortedMap.lastKey();
```

#### tailMap(K equalOrLarge) and headMap(K small)

```java
     '---' tailMap("c")
a  b c d e
'--' 
  headMap("c")
```


Return new instance of map that `>= "c"` c in sortedMap
```java
SortedMap tailMap = sortedMap.tailMap("c");
// {c=3, d=4, e=5}
```

Return new instance of map that `< "c"` in the sortedMap
```java
SortedMap headMap = sortedMap.headMap("c");
// {a=1, b=2}
```

#### subMap(lowBound_inclusive, upBound_exclusive)

```java
// subMap >= b and < e
SortedMap subMap = sortedMap.subMap("b", "e");

// {b=2, c=3, d=4}
```

### Methods in NavigableMap

```java
TreeMap<String,String> sortedMap = new TreeMap<>()
sortedMap.put("a", "1");
sortedMap.put("c", "3");
sortedMap.put("e", "5");
sortedMap.put("d", "4");
sortedMap.put("b", "2")

System.out.println(sortedMap); // {a=1, b=2, c=3, d=4, e=5}

NavigableMap descending = sortedMap.descendingMap()
System.out.println(descending); // {e=5, d=4, c=3, b=2, a=1}
```

#### Higher/Lower-Key & Higher/Lower-Entry , ceiling/floor-Key & ceiling/floor-Entry

```java
class Main {
    public static void main(String[] args) {
        TreeMap<String, Integer> numbers = new TreeMap<>();
        numbers.put("First", 1);
        numbers.put("Second", 5);
        numbers.put("Third", 4);
        numbers.put("Fourth", 6);
        System.out.println("TreeMap: " + numbers);

        // 使用 higher()
        System.out.println("higherKey(): Returns the least key strictly greater than the given key, or null if there is no such key." + numbers.higherKey("Fourth"));

        System.out.println("higherEntry(): Returns a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key." + numbers.higherEntry("Fourth"));

        System.out.println("lowerKey(): Returns the greatest key strictly less than the given key, or null if there is no such key." + numbers.lowerKey("Fourth"));
        System.out.println("lowerEntry(): Returns a key-value mapping associated with the greatest key strictly less than the given key, or null if there is no such key. " + numbers.lowerEntry("Fourth"));

        System.out.println("ceilingKey(): Returns the greatest key less than or equal to the given key, or null if there is no such key." + numbers.ceilingKey("Fourth"));
        System.out.println("ceilingEntry(): Returns a key-value mapping associated with the greatest key less than or equal to the given key, or null if there is no such key." + numbers.ceilingEntry("Fourth"));

        System.out.println("floorKey(): Returns the greatest key less than or equal to the given key, or null if there is no such key.
         " + numbers.floorKey("Fourth"));

        System.out.println("floorEntry(): Returns a key-value mapping associated with the greatest key less than or equal to the given key, or null if there is no such key." + numbers.floorEntry("Fourth"));
    }
}



TreeMap: {First=1, Fourth=6, Second=5, Third=4}

higherKey(): Returns the least key strictly greater than the given key, or null if there is no such key. Second
higherEntry(): Returns a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key. Second=5
lowerKey(): Returns the greatest key strictly less than the given key, or null if there is no such key. First
lowerEntry(): Returns a key-value mapping associated with the greatest key strictly less than the given key, or null if there is no such key. First=1

ceilingKey(): Returns the greatest key less than or equal to the given key, or null if there is no such key. Fourth
ceilingEntry(): Returns a key-value mapping associated with the greatest key less than or equal to the given key, or null if there is no such key.Fourth=6
floorKey(): Returns the greatest key less than or equal to the given key, or null if there is no such key. Fourth
floorEntry(): Returns a key-value mapping associated with the greatest key less than or equal to the given key, or null if there is no such key. Fourth=6
```

#### PollLastEntry & PollFirstEntry

Poll Last/First Entry and Delete that entry

```java
// {a=1, b=2, c=3, d=4, e=5}

sortedMap.pollFirstEntry(); // a=1
sortedMap.pollLastEntry(); // e=1
```


## LinkedHashMap
![圖 2](../images/linkedHashset.png)  

Synchronized LinkedHashMap
```java
Map m = Collections.synchronizedMap(new LinkedHashMap(...));
```

Constructor
```java
LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>();

LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>(int capacity);

LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>(Map<? extends K,​? extends V> map);

LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>(int capacity, float fillRatio);
```

The following constructor is also used to initialize both the capacity and fill ratio for a LinkedHashMap along with whether to follow the insertion order or not.
```java
LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>(int capacity, float fillRatio, boolean Order);
```



