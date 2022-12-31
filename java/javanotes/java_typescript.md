# java and typescript utils

- [java and typescript utils](#java-and-typescript-utils)
  - [Java 8 Method References](#java-8-method-references)
  - [Typescript#Set](#typescriptset)
  - [Typescript MAP](#typescript-map)
    - [initializer](#initializer)
  - [Java Map](#java-map)
  - [String](#string)
  - [Array](#array)
      - [Concatenate](#concatenate)
      - [filter](#filter)
      - [forEach](#foreach)
      - [Search (`index` & `lastIndexOf`)](#search-index--lastindexof)
      - [Get (`pop` & `shift` )](#get-pop--shift-)
      - [Insertion (`push` & `unshift`)](#insertion-push--unshift)
      - [reverse & slice](#reverse--slice)
      - [splice](#splice)
      - [Accumulation (`reduce`)](#accumulation-reduce)
      - [Join](#join)
  - [Java Character](#java-character)
  - [Java#Stack & Java&Queue](#javastack--javaqueue)

## Java 8 Method References

```java
final ObjectName obj = ObjectName.create( ObjectName::new );
ObjectName.forEach( ObjectName::static_method );
ObjectName.forEach( ObjectName::method );
```


## Typescript#Set

```typescript
// initializer
const s = new Set<number>([1,2,3,4,5,6,7]);

// use add(item)
let diceEntries = new Set<number>();
diceEntries.add(1).add(2).add(3);   //Chaining of add() method is allowed
diceEntries.size        
diceEntries.has(1);    //true
diceEntries.delete(3); // true
diceEntries.clear();      


const mySet = new Set([
    'foo',
    'bar',
]);

const setIter = mySet.values(); // const setIter: IterableIterator<string>

console.log(setIter.next().value); // "foo"
console.log(setIter.next().value); // "bar"
console.log(setIter.next().value); // "undefine"

const itr = mySet.entries(); //IterableIterator<[string, string]>

for ( const i of itr){
    console.log(i);
}
```
## Typescript MAP

### initializer

```typescript
// Object.entries(obj)
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
boolean	isEmpty()

Set<Map.Entry<K,V>>	entrySet() // Each Map<K,V>

V remove(Object key)
default boolean	remove(Object key, Object value)

V get(Object key)
default V getOrDefault(Object key, V defaultValue)

V put(K key, V value)
void putAll(Map<? extends K,? extends V> m)
default V putIfAbsent(K key, V value)
```

## String 

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


Java 
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

String trim() // "  abc  " => "abc"

char[] toCharArray()
char charAt(int index)

String substring(int beginIndex, ?int endIndex)

boolean matches(String regex)

int length()

static String valueOf(Object obj)

Boolean isEmpty()
Boolean contains(CharSequence chars)

String toLowerCase()
String toUpperCase()

byte[] getBytes()
byte[] getBytes(String charsetName) // str.getBytes( "UTF-8" );
```


## Array
#### Concatenate
```typescript
var alpha = ["a", "b", "c"]; 
var numeric = [1, 2, 3];
var alphaNumeric = alpha.concat(numeric);  // a,b,c,1,2,3 
```

#### filter
```typescript 
function isBigEnough(element, index, array) { 
        return (element >= 10); 
} 

// every return boolean type
const passed = [12, 5, 8, 130, 44].every(isBigEnough); // false


// some(callbackFunction : (value: number, index: number, array: number[]) => unknown, thisArg?: any): boolean

const nums = [2, 5, 8, 1, 4];
const res = nums.some(isBigEnough); // nums.some(e => e >= 10);
console.log("Any Elements >= 10 in nums? : " + res );  // false


// filter each element with condition
// it returns element the condition required
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
// If the array is empty
// undefined is returned and the array is not modified.
var arr = [99 , 1, 2, 3]
const rest = arr.shift();  
console.log("Shifted value is : " + rest " arr now is : " + arr!.toString() );  /
// "Shifted value is : 99",  " arr now is : 1,2,3" 
```

#### Insertion (`push` & `unshift`)

```java
// appends the given element(s) in the last of the array and 
// returns array.length
const numbers = [1, 4, 9]; 
length = numbers.push(20); 

// appends the element(s) in the head of the array and
// returns array.length
length = numbers.unshift(2);
```

#### reverse & slice

```typescript
let arr : Number[] | String[] = [0, 1, 2, 3].reverse(); 
console.log("Reversed array is : " + arr );  // 3,2,1,0

arr = ["orange", "mango", "banana", "sugar", "tea"]; 
let subArray = arr.slice(1, 2);  
console.log(subArray); // mango
```

#### splice

Removing or replacing existing elements and/or adding new elements in place

```typescript
splice(index)
splice(index, deleteItemCount)
splice(index, deleteItemCount, ...newInsertItemsAtIndex)

var removed = arr.splice(2, 0, "water");  // add new element "water" at index#2
console.log("After adding 1: " + arr );   // orange,mango,water,banana,sugar,tea 
console.log("removed is: " + removed); 
          
removed = arr.splice(3, 1);  // delete 1 element starting from index#3
console.log("After removing 1: " + arr );  // orange,mango,water,sugar,tea 
console.log("removed is: " + removed);  // banana
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
## Java#Stack & Java&Queue

```java
boolean	empty()
E	peek()
E	pop()
E	push(E item)

int	search(Object o)
```

LinkedList implements Queue extends Collection

```java
boolean add(E e) // throwing exception 
boolean offer(E e) 

// Retrieves but does not remove the head of element in Queue
E element() // throwing exception if empty
E peek() // return null if empty

E poll()  // return null if empty
E remove() throwing exception if empty
```
