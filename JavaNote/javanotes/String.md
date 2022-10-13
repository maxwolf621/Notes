# String

- [String](#string)
    - [`final` for String](#final-for-string)
    - [`new` string()](#new-string)
  - [String Methods](#string-methods)
      - [equals(Object obj) and equalsIgnoreCase(String s)](#equalsobject-obj-and-equalsignorecasestring-s)
      - [compareTo(String s) and compareToIgnoreCase(String s)](#comparetostring-s-and-comparetoignorecasestring-s)
      - [trim()](#trim)
      - [replace(char oldChar, char newChar)](#replacechar-oldchar-char-newchar)
      - [valueOf()](#valueof)
  - [`toCharArray` String To Chars](#tochararray-string-to-chars)
  - [`String`, `StringBuffer` and `StringBuilder`](#string-stringbuffer-and-stringbuilder)
  - [StringBuilder](#stringbuilder)
    - [insert and append](#insert-and-append)
    - [char operation](#char-operation)


Since Java 9 `String` value is used for `byte[]` as character storage instead of `char`  
```java
/**
  * String Class In Java 8 (array)
  */
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    // character storage. 
    private final char value[];
}

/**
  * String Class In Java 9 (byte) 
  */
public final class String   
    implements java.io.Serializable, Comparable<String>, CharSequence {
    
    // character storage
    private final byte[] value;

    /** The identifier of the encoding used to encode the bytes in {@code value}. */
    private final byte coder;
}
```

### `final` for String

1. Hash value  : Reduce duplicate Strings
2. String Pool : It keeps all literal strings stored **at the compiler time** to reduce `new` operation
3. Security 
4. Thread Safe


### `new` string()

**Since Java 7, Each string in String Pool is stored in Stack to prevent from `OutOfMemoryError`**.   

`new String("stringValue")` operation creates
1. `new` creates an object in the stack
2. At the Compiler time `stringValue` is stored in String Pool and points to new memory location.

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2) ; // false

/**
  * To compare String value 
  * created by new String("...")
  * we must use {@code intern()}
  */
String s3 = s1.intern()     ;
String s4 = s2.intern()     ;
System.out.println(s3 == s4);  // true

// Check String Pool first
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
```

|`stringName.intern()` | ( `stringName` exists in String Pool )  |  
|    ---                                                     |--|
|  `true` |  return the reference  |
|  `false` | add it in pool and return reference of the string value|


## String Methods

```java
String[] split(String regex, ?int arrayLength)
// IN.MA.CA.LA split("\\.")
// IN-MA-CA-LA => String[] s = {IN,MA,CA,LA}
// -----------------------
// IN.MA.CA.LA split("\\.", arrayLength)
// arrayLength : 2 => {IN , MA-CA-LA}
// arrayLength : 3 =? IN, MA, CA-LA}
// arrayLength >= 4 {IN,MA,CA,LA}

int indexOf(int ch, int ?fromIndex) // search character
int indexOf(String str, ?int fromIndex) // search string
int lastIndexOf(int ch, ?int startFromIndex)
int lastIndexOf(String str, ?int startFromIndex)

String replace(char oldChar, char newChar)
String replaceAll(String regex, String replacement)
String replaceFirst(String regex, String replacement)

int compareTo(Object o)
int compareTo(String anotherString) 
int compareToIgnoreCase(String str)

static String copyValueOf(char[] data)
static String copyValueOf(char[] data, int offset, int count)


// most used
char[] toCharArray()
String toString()
char charAt(int index))

boolean contentEquals(StringBuffer sb)
boolean equals(Object anObject)
boolean equalsIgnoreCase(String anotherString)

byte[] getBytes()
byte[] getBytes(String charsetName) 
// str.getBytes( "UTF-8" );

String substring(int beginIndex)
String substring(int beginIndex, int endIndex)

String toLowerCase()
String toUpperCase()

String trim() // "  abc  " => "abc"

int length()
Boolean isEmpty()
int hashCode()

Boolean contains(CharSequence chars)
boolean matches(String regex)

// PREFIX or SUFFIX
boolean endsWith(String chars)
boolean startsWith(String chars, ?int toffset)

// search string pool first 
// if the string does not exist
// then create one in pool
String intern() 
//String s3 = new String("1234").intern()     ;
//String s4 = new String("1234").intern()     ;
//System.out.println(s3 == s4); 

```


```java
// String toLowerCase()
String word1 = "HeLLo";
String word3 = word1.toLowerCase(); // returns "hello"
// String toUpperCase()
String word1 = "HeLLo";
String word2 = word1.toUpperCase(); // returns “HELLO”

String subString(int i, int ?j_exclusive)

// Return the substring from the ith  index character to end.
"GeeksforGeeks".substring(3); // returns "ksforGeeks"

// Returns the substring from i to j-1 index.
"GeeksforGeeks".substring(2, 5); // returns "eks"


// Concatenates specified string to the end of this string.
String concat( String str)
String s1 = "Geeks";
String s2 = "forGeeks";
String output = s1.concat(s2); // returns "GeeksforGeeks"


// Returns the index within the string of the first occurrence of the specified string.
int indexOf (String s, int ?startAt)
String s = "Learn Share";
int output = s.indexOf("Share"); // returns 6
int output = s.indexOf("ea",3);// returns 13

// Returns the index within the string of the last occurrence of the specified string.
int lastIndexOf(String s)  
int output = s.lastIndexOf("a"); // returns 14
```

#### equals(Object obj) and equalsIgnoreCase(String s)

Compare string's content
```java
boolean equals(Object otherObj)
Boolean out = "Geeks".equals("Geeks"); // returns true
Boolean out = "Geeks".equals("geeks"); // returns false

boolean  equalsIgnoreCase(String anotherString)
Boolean out= "Geeks".equalsIgnoreCase("Geeks"); // returns true
Boolean out = "Geeks".equalsIgnoreCase("geeks"); // returns true
```
#### compareTo(String s) and compareToIgnoreCase(String s)

Compares two string lexicographically.
```java
int compareTo(String anotherString)
int out = s1.compareTo(s2);  // where s1 ans s2 are
                             // strings to be compared
out < 0  // s1 comes before s2
out = 0  // s1 and s2 are equal.
out > 0  // s1 comes after s2.
```

```java
int compareToIgnoreCase( String anotherString)
int out = s1.compareToIgnoreCase(s2);  
```
This returns difference s1-s2. If :
```java
out < 0  // s1 comes before s2
out = 0   // s1 and s2 are equal.
out > 0   // s1 comes after s2.
```



#### trim()

Returns the copy of the String, **by removing white spaces at both ends. It does not affect white spaces in the middle.**
```java
String word1 = " Learn Share Learn ";
String word2 = word1.trim(); 
/***
 *  Learn Share Learn 
 * Learn Share Learn
 */
```

#### replace(char oldChar, char newChar)

Returns new string by replacing all occurrences of oldChar with newChar.
```java
String s1 = "feeksforfeeks";

String s2 = "feeksforfeeks".replace('f' ,'g'); // returns "geeksgorgeeks"
```
- s1 is still `feeksforfeeks` and s2 is `geeksgorgeeks`

#### valueOf()

convert type `boolean` , `char`, `char[]`, `double`, `float`, `int`, `long`, `object` to `String`

```java
// Returns the string representation of the boolean argument.
static String valueOf(boolean b)

// Returns the string representation of the char argument.
static String valueOf(char c)

// Returns the string representation of the char array argument.
static String valueOf(char[] data)

// Returns the string representation of a specific sub-array of the char array argument.
static String valueOf(char[] data, int offset, int count)

// Returns the string representation of the double argument.
static String valueOf(double d)

// Returns the string representation of the float argument.
static String valueOf(float f)

// Returns the string representation of the int argument.
static String valueOf(int i)

// Returns the string representation of the long argument.
static String valueOf(long l)

// Returns the string representation of the Object argument.
static String valueOf(Object obj)
```

## `toCharArray` String To Chars

```java
String s1 ="hello";  
char c = s.charAt(0); //returns h  
char[] ch=s1.toCharArray();    
```

## `String`, `StringBuffer` and `StringBuilder`

1. Mutable
    - [x] `StringBuffer` & `StringBuilder` are `mutable`
2. Thread Safe
    - [x] String 
    - [x] StringBuffer via `synchronized`

Use `StringBuffer` for Multiple Thread Tasks otherwise `StringBuilder`

## StringBuilder

### insert and append

insert = append with offset arg

```java
StringBuilder append(boolean b)
StringBuilder append(char c)
StringBuilder append(char[] str)
StringBuilder append(char[] str, int offset, int len) // Appends the string representation of a subarray of the char array argument to this sequence.
StringBuilder append(CharSequence s) // Appends the specified character sequence to this Appendable.
StringBuilder append(CharSequence s, int start, int end) // Appends a subsequence of the specified CharSequence to this sequence.
StringBuilder append(double d)
StringBuilder append(float f)
StringBuilder append(int i)
StringBuilder append(long lng)
StringBuilder append(Object obj)
StringBuilder append(String str)
StringBuilder append(StringBuffer sb)

// Append with offset arg
StringBuilder insert(int offset, boolean b)
StringBuilder insert(int offset, char c)
StringBuilder insert(int offset, char[] str)
StringBuilder insert(int index, char[] str, int offset, int len)
StringBuilder insert(int dstOffset, CharSequence s)
StringBuilder insert(int dstOffset, CharSequence s, int start, int end)
StringBuilder insert(int offset, double d)
StringBuilder insert(int offset, float f)
StringBuilder insert(int offset, int i)
StringBuilder insert(int offset, long l)
StringBuilder insert(int offset, Object obj)
StringBuilder insert(int offset, String str)
```


```java
int capacity() // Returns the current capacity.
int length()

void ensureCapacity(int minimumCapacity)
void setLength(int newLength) // set new Length (not add new length with onl length)
void trimToSize()  

String substring(int start)
String substring(int start, int end)

StringBuilder reverse()
String toString()

StringBuilder delete(int start, int end) // like List#clear()

void getChars(int srcBegin, int srcEnd_exclusive, char[] dst, int dstBegin)
        StringBuilder str = new StringBuilder("GONE WRONG_01");
        char[] array = new char[10];
        Arrays.fill(array, '_');
  
        // get char from index 5 to 9
        // and store in array start index 3
        str.getChars(5, 10, array, 3);
        Stream.of(array).forEach(System.out::print);
        //___WRONG___

int indexOf(String str)
int indexOf(String str, int fromIndex)
int lastIndexOf(String str)
int lastIndexOf(String str, int fromIndex)

StringBuilder replace(int start, int end, String str)

CharSequence subSequence(int start, int end)
```

### char operation

```java
char charAt(int index) // Returns the char value in this sequence at the specified index.
void setCharAt(int index, char ch)
StringBuilder deleteCharAt(int index)
```