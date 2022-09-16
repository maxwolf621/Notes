# java and typescript utils

- [java and typescript utils](#java-and-typescript-utils)
  - [Java 8 Method References](#java-8-method-references)
  - [Java Collection](#java-collection)
  - [Interface List](#interface-list)
  - [Typescript MAP](#typescript-map)
  - [Java Map](#java-map)
  - [Queue](#queue)
  - [String](#string)
    - [Typescript](#typescript)
  - [Array](#array)
    - [Java](#java)
      - [remove](#remove)
      - [replace](#replace)
      - [indexOf](#indexof)
    - [Typescript](#typescript-1)
      - [Concatenate](#concatenate)
      - [filter](#filter)
      - [forEach](#foreach)
      - [Search (`index` & `lastIndexOf`)](#search-index--lastindexof)
      - [Get (`pop` & `shift` )](#get-pop--shift-)
      - [Insertion (`push` & `unshift`)](#insertion-push--unshift)
      - [reverse](#reverse)
      - [splice & slice](#splice--slice)
      - [some](#some)
      - [Accumulation (`reduce`)](#accumulation-reduce)
      - [Join](#join)
  - [Typescript Tuple](#typescript-tuple)
  - [Java Character](#java-character)
  - [Typescript#Set](#typescriptset)
  - [Java#Stack](#javastack)

## Java 8 Method References

```java
//  Class :: new
final Car car = Car.create( Car::new );
// Class :: static method
cars.forEach( Car::collide );
// Car :: method
cars.forEach( Car::repair );
```

## Java Collection
```java
boolean	add(E e)
boolean	addAll(Collection<? extends E> c)

boolean	contains(Object o)
boolean	containsAll(Collection<?> c)

void clear()

boolean	remove(Object o)
boolean	removeAll(Collection<?> c)
default boolean	removeIf(Predicate<? super E> filter)

boolean	retainAll(Collection<?> c)

Iterator<E>	iterator()
default Spliterator<E> spliterator()


int	size()
boolean	equals(Object o)
int	hashCode()
boolean	isEmpty()

Object[] toArray()
<T> T[] toArray(T[] a)

default Stream<E> parallelStream()
default Stream<E> stream()
```

## Interface List

Interface List supports methods from collection with `int index` arg
```java
E set(int index, E element)
void add(int index, E element) 
E get(int index)

boolean addAll(int index, Collection<? extends E> c)
[1,2].addAll(1,[1,2,3]); // [1, 1, 2, 3, 2] 
[1,1,2,3,2].remove(1); // [1, 2, 3, 2]

[5,2,3,2].set(0, 5); // replace 0th element with 5 

List<E> subList(int fromIndex_Inclusive, int toIndex_Exclusive)

// use them if necessary
E remove(int index)
protected void removeRange(int fromIndex, int toIndex_exclusive)

default void replaceAll(UnaryOperator<E> operator)
[1,2,3,4,5].replaceAll(num -> num * 2); // [2,3,6,8,10]

ListIterator<E>	listIterator()
ListIterator<E>	listIterator(int index)

default void sort(Comparator<? super E> c)

int	indexOf(Object o)
int	lastIndexOf(Object o)
```


## Typescript MAP
```typescript
const obj = { name: 'Tom', country: 'Chile' };
const map2 = new Map<string, string>(Object.entries(obj));
```

```typescript
const mapEx: Map<keyType, valueType> = new Map([               
    [ k1 , v1],                                                     
    [ k2 , v2]                                                  
]);                                                             
```

| Ty                                                   | Java               |
|------------------------------------------------------| -------------------|
|`clear()`                                               |`void clear()`
|`set(key: string, value: number): Map<string, number>`  |`Object put(Object key, Object v)`  
|`get(key: string): string | undefined`                  |`Object get(Object key)`
|`has(key: string): boolean`                             |`boolean containsKey(Object key)`
|`delete(key: string): boolean`                          |`Object remove(Object key)`    
|`keys(): IterableIterator<string>`                      |`Set keySet()`   
|`values(): IterableIterator<string>`                    |`Collection<V> values()` 
|`size : number`                                         |`int size()`

## Java Map 

```java 
boolean	containsValue(Object value)
Set<Map.Entry<K,V>>	entrySet() // Each Map<K,V>

default void forEach(BiConsumer<? super K,? super V> action) // For each (key , value)

boolean	isEmpty()

V remove(Object key)
default boolean	remove(Object key, Object value)

default V replace(K key, V newValue) // if k exists then replace old value to newValue
default boolean	replace(K key, V oldValue, V newValue) // if key,oldValue pair is existing then replace it with newValue

V get(Object key)
default V getOrDefault(Object key, V defaultValue)

V put(K key, V value)
void putAll(Map<? extends K,? extends V> m)
default V putIfAbsent(K key, V value)

String toString()
```

## Queue 

LinkedList implements Queue extends Collection
```java
boolean add(E e) // throwing exception
boolean offer(E e) 

// Retrieves but does not remove the head of element in quque
E element() // throwing exception if empty
E peek() // return null if empty

E poll() 
E remove()
```


## String 

### Typescript
```typescript 
let objStr = new String("abc def"); // object type not string 
const valueOfObject = objStr.valueOf();  // ObjectType to String
const objToSting = objStr.toString() // const objToSting: string

objStr.length; // 7

let str = "abc def" // string type

// charAt(index : number) : string
str.charAt(1) // b

// str.concat(s : string) : string
const concatStr = str.concat(" New Strings") // "abc def New Strings" 

str.lastIndexOf("abc"); // return -1 if it is non existent
str.indexOf("abc"); // return -1 if it is non existent

// str.substring(start: number, end_exclusive?: number | undefined): string
str.substring(2,4) // "c "

str.toUpperCase() 
str.toLowerCase()

// str.split(splitter: { [Symbol.split](string: string, limit?: number | undefined): string[]; }, 
//           limit?: number | undefined): string[]
console.log("I have".split(" " ,2));  // ["I", "have"] 

// return null if nothing matches
str.match("abc") // ["abc"]


// charCodeAt(index: number): number  return unicode

// Finds the first substring match in a regular expression search.
// search(searcher: { [Symbol.search](string: string): number; }): number
str.search("asdf") // -1 

// slice(start?: number | undefined, end_exclusive?: number | undefined) : string 
const sliceStr = str.slice(1,4) // "bc "
```


```java
String intern() // search string pool first else create one

String[] split(String regex, ?int arrayLength)
// IN.MA.CA.LA split("\\.")
// IN-MA-CA-LA => String[] s = {IN,MA,CA,LA}

// IN.MA.CA.LA split("\\.", arrayLength)
// arrayLength : 2 => {IN , MA-CA-LA}
// arrayLength : 3 =? IN, MA, CA-LA}
// arrayLength >= 4 {IN,MA,CA,LA}

// Content 
boolean contentEquals(StringBuffer sb)
boolean equalsIgnoreCase(String anotherString)

char[] toCharArray()
char charAt(int index)
String toLowerCase()
String toUpperCase()
String substring(int beginIndex, ?int endIndex)
boolean matches(String regex)
String trim() // "  abc  " => "abc"
int length()
static String valueOf(Object obj)
Boolean isEmpty()
Boolean contains(CharSequence chars)

byte[] getBytes()
byte[] getBytes(String charsetName) // str.getBytes( "UTF-8" );
```


## Array

### Java

```java
E get(int index)
boolean	contains(Object o)   
int	size()
void clear()
Object clone()
boolean	isEmpty()
Iterator<E>	iterator()  
Object[] toArray()  

// SORT
void sort(Comparator<? super E> c)
arrayList.sort(Comparator c)
arrayList.sort(Comparator.naturalOrder()); 
arrayList.sort(Comparator.reverseOrder());

// add/All
boolean	add(E e)  
void add(int index, E element)
boolean	addAll(Collection<? extends E> c)
boolean	addAll(int index, Collection<? extends E> c)

// foreach
void forEach(Consumer<? super E> action)
// foreach has no return val
numbers.forEach((num) -> {
    num = num * 10;
});


// this method inserts element in specific position 
E set(int index, E element)
void trimToSize() // delete extra unused-space in the Array 
// get the sub list from s to e
arrayList.subList(int s, int e)


// ensureCapacity instead of dynamically allocating the space 
void ensureCapacity(int minCapacity)
arrayList.ensureCapacity(int minCapacity)

// retain all array2 the elements if array1 contains these.
// kinda like containsAll() but returns array[]   
boolean retainAll(Collection<?> c)
array1.retainAll(array2);
```

#### remove
```java
E remove(int index)
boolean	remove(Object o) // HashSet
boolean	removeAll(Collection<?> c)
protected void removeRange(int fromIndex, int toIndex)
// remove element the index from start to end - 1
arrayList.removeRange(start, end);
// remove
boolean	removeIf(Predicate<? super E> filter)
// remove elements which contains "a"
elements.removeIf(e -> e.contains("a"));;
```

#### replace

```java
// replace 
void replaceAll(UnaryOperator<E> operator)
// replace each the num to num*2 
numbers.replaceAll(num -> num * 2);;
```

#### indexOf

```java
int	indexOf(Object o)
int	lastIndexOf(Object o)
```

### Typescript

#### Concatenate
```typescript
var alpha = ["a", "b", "c"]; 
var numeric = [1, 2, 3];
var alphaNumeric = alpha.concat(numeric);  // a,b,c,1,2,3 
```

#### filter
```typescript 
// isBigEnough
// every element(Function) 
// it returns boolean
function isBigEnough(element, index, array) { 
        return (element >= 10); 
} 
const passed = [12, 5, 8, 130, 44].every(isBigEnough); // false

// filter each element with condition
// it returns element the condition required
function isBigEnough(element, index, array) { 
   return (element >= 10);  4
} 
const passed = [12, 5, 8, 130, 44].filter(isBigEnough); 
console.log("Test Value : " + passed ); // 12,130,44
```

#### forEach

```typescript
// No return val
let num = [7, 8, 9];
num.forEach(function (value) {
    console.log(value); // 7,8,9
}); 
// or 
num.forEach((value) =>{
  console.log(value);
})
```

#### Search (`index` & `lastIndexOf`)

```java
// indexOf(Element e) if non-existing return -1
let index = [12, 5, 8, 130, 44].indexOf(8); 
console.log("index is : " + index );  // 2
//                         <-- find the element from right 
let index = [12, 5, 8, 130, 44].lastIndexOf(8); 
console.log("index is : " + index );  // 2

var numbers = [1, 4, 9]; 
var roots = numbers.map(Math.sqrt); 
console.log("roots is : " + roots );  // 1,2,3
```

#### Get (`pop` & `shift` )
```typescript
// Pop Last Element , return that element 
var numbers = [1, 4, 9]; 
var element = numbers.pop(); 
console.log("element is : " + element );  // 9


// Removes the first element from an array and returns it. 
// If the array is empty, undefined is returned and the array is not modified.
var arr = [99 , 1, 2, 3]
const rest = arr.shift();  
console.log("Shifted value is : " + rest " arr now is : " + arr!.toString() );  /
// "Shifted value is : 99",  " arr now is : 1,2,3" 
```

#### Insertion (`push` & `unshift`)

```java
// appends the given element(s) 
// in the last of the array and 
// returns the length of the new array.
const numbers = [1, 4, 9]; 
length = numbers.push(20); 

// appends the element(s) in the head of the array
// returns array.length
length = numbers.unshift(2);
```


#### reverse

```java
var arr = [0, 1, 2, 3].reverse(); 
console.log("Reversed array is : " + arr );  // 3,2,1,0

var arr = ["orange", "mango", "banana", "sugar", "tea"];  
const arr = ["orange", "mango", "banana", "sugar", "tea"]; 
arr.slice( 1, 2) );  // mango
```

#### splice & slice

Removing or replacing existing elements and/or adding new elements in place

```typescript
splice(start)
splice(start, deleteCount)
splice(start, deleteCount, item1)
splice(start, deleteCount, item1, item2, .... , itemN)

var removed = arr.splice(2, 0, "water");  // add new element "water" at index#2
console.log("After adding 1: " + arr );    // orange,mango,water,banana,sugar,tea 
console.log("removed is: " + removed); 
          
removed = arr.splice(3, 1);  // delete 1 element starting from index#3
console.log("After removing 1: " + arr );  // orange,mango,water,sugar,tea 
console.log("removed is: " + removed);  // banana
```

#### some

Determines whether the specified callback function returns true for any element of an array.

```typescript
some(predicate: (value: number, index: number, array: number[]) => unknown, thisArg?: any): boolean
function isBigEnough(element:number, index: number, array : number[]) { 
   return (element >= 10); 
          
}         
const nums = [2, 5, 8, 1, 4];
const res = nums.some(isBigEnough); // some element >= 10 ?
console.log("Any Elements >= 10 in nums? : " + res );  // false
```

#### Accumulation (`reduce`)

```typescript
// The reduce method calls the callbackfn function one time for each element in the array.
// Calls the specified callback function for all the elements in an array. 
// The return value of the callback function is the accumulated result, 
// and is provided as an argument in the next call to the callback function.
reduce(callbackfn: (previousValue: number, 
                    currentValue: number, 
                    currentIndex: number, 
                    arrayLength: number[]) => number): number

var total = [0, 1, 2, 3].reduce((preVal, curVal) => { return a + b; }); 
console.log("total is : " + total );  // 6

var total = [0, 1, 2, 3].reduceRight(function(a, b){ return a + b; }); 
console.log("total is : " + total );  // 6
```


#### Join

```typescript
Array<string>.join(separator?: string | undefined): string

// Adds all the elements of an array into a string, separated by the specified separator string.
const arr = new Array("Google","Youtube","Youtube");  

// if parameter is omitted, connate each element with comma (,) by default
const strJoinByDefault = arr.join();  // Google,Youtube,Youtube
    
let strWithComma = arr.join(", ");  // Google, Youtube, Youtube
let strWithPlus = arr.join(" + "); // Google + Youtube + Youtube
```

## Typescript Tuple

Tuple allows Array to contains different data type Element
```typescript 
const tuple_name = [value1,value2,value3, ... , value n];
const strNumber = ["hey", 123]

const mytuple = []; 
mytuple[0] = 120 
mytuple[1] = 234

var a =[10,"hello"] 
var [b,c] = a  // a[0] to b , a[1] tp c
```

## Java Character
```java
boolean Character.isUpperCase(char ch)
boolean Character.isLowerCase(char ch)
boolean Character.isWhitespace(char ch)
boolean Character.isLetter(char ch)
boolean Character.isDigit(char ch)
boolean Character.isLetterOrDigit(char ch)

static char Character.toUpperCase(char ch)
static char Character.toLowerCase(char ch)
String Character.toString(char ch)
Character Character.valueOf(char c)
```


## Typescript#Set

```typescript
const s = new Set<number>([1,2,3,4,5,6,7]);

let diceEntries = new Set<number>();
diceEntries.add(1).add(2).add(3);   //Chaining of add() method is allowed
diceEntries.size        

diceEntries.has(1);                 //true
diceEntries.has(10);                //false
diceEntries.delete(3);              // true
diceEntries.clear();      
```


## Java#Stack 

```java
boolean	empty()
E	peek()
E	pop()
E	push(E item)
int	search(Object o)
```
