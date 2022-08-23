# String

- [String](#string)
    - [`final` for String](#final-for-string)
  - [Declaration](#declaration)
  - [String Methods](#string-methods)
      - [equals(Object obj) and equalsIgnoreCase(String s)](#equalsobject-obj-and-equalsignorecasestring-s)
      - [compareTo(String s) and compareToIgnoreCase(String s)](#comparetostring-s-and-comparetoignorecasestring-s)
      - [toLowerCase() and toUpperCase()](#tolowercase-and-touppercase)
      - [trim()](#trim)
      - [replace(char oldChar, char newChar)](#replacechar-oldchar-char-newchar)
      - [valueOf()](#valueof)
  - [`toCharArray` String To Chars](#tochararray-string-to-chars)
  - [`String`, `StringBuffer` and `StringBuilder`](#string-stringbuffer-and-stringbuilder)
  - [StringBuffer](#stringbuffer)
    - [Methods](#methods)


Since Java 9 `String` value is used for `byte[]` as character storage instead of (Java 8)`char`  
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

1. Hash value  : Same Value Could Be Found via Hashcode
2. String Pool : Reduce `new` the same value
 - String Pool keeps all literal strings, theses are stored **at the compiler time**
3. Security 
4. Thread Safe

## Declaration

**Since Java 7, String Pool is in Stack for preventing from `OutOfMemoryError`**.  

`new String("stringValue")` will create 2 Objects
1. At the Compiler time `stringValue` is created in String Pool and is referenced to
2. `new` will create an object in the stack

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2) ; // false

/**
  * To compare String value 
  * that was created by new String("...")
  * we must use {@code intern()}
  */
String s3 = s1.intern()         ;
String s4 = s2.intern()         ;
System.out.println(s3 == s4)    ;  // true

// Check String Pool first
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
```

|`stringName.intern()` ( `stringName` exists in String Pool )| |  
|    ---               |--|
|  True |  return the reference  |
|  False | Add it in pool and return value of the reference  |


## String Methods

`?` means it's optional parameter

```java
String subString(int i, int ?j)

// Return the substring from the ith  index character to end.
"GeeksforGeeks".substring(3); // returns "ksforGeeks"

// Returns the substring from i to j-1 index.
"GeeksforGeeks".substring(2, 5); // returns "eks"
```

Concatenates specified string to the end of this string.
```java
String concat( String str)

String s1 = "Geeks";
String s2 = "forGeeks";
String output = s1.concat(s2); // returns "GeeksforGeeks"
```

```java
// Returns the index within the string of the first occurrence of the specified string.
int indexOf (String s, int ?i)

String s = "Learn Share Learn";
int output = s.indexOf(“Share”); // returns 6

// Returns the index within the string of the first occurrence of the specified string, starting at the specified index.
int output = s.indexOf("ea",3);// returns 13
```


```java
// Returns the index within the string of the last occurrence of the specified string.
int lastIndexOf(String s)  
int output = s.lastIndexOf("a"); // returns 14
```

#### equals(Object obj) and equalsIgnoreCase(String s)

Compares this string to the specified object.
```java
boolean equals(Object otherObj)

Boolean out = "Geeks".equals("Geeks"); // returns true
Boolean out = "Geeks".equals("geeks"); // returns false
```

```java
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

This returns difference s1-s2. If :
out < 0  // s1 comes before s2
out = 0   // s1 and s2 are equal.
out > 0   // s1 comes after s2.
```

#### toLowerCase() and toUpperCase()

```java
// String toLowerCase()
String word1 = "HeLLo";
String word3 = word1.toLowerCase(); // returns "hello"
// String toUpperCase()
String word1 = "HeLLo";
String word2 = word1.toUpperCase(); // returns “HELLO”
```

#### trim()

Returns the copy of the String, **by removing white spaces at both ends. It does not affect white spaces in the middle.**
```java
String word1 = "Learn Share Learn";
String word2 = word1.trim(); // returns "Learn Share Learn"
```

#### replace(char oldChar, char newChar)

Returns new string by replacing all occurrences of oldChar with newChar.
```java
String s1 = "feeksforfeeks";

String s2 = "feeksforfeeks".replace(‘f’ ,’g’); // returns "geeksgorgeeks"
```
- s1 is still `feeksforfeeks` and s2 is `geeksgorgeeks`

#### valueOf()


convert type `boolean` , `char`, `char[]`, `double`, `float`, `int`, `long`, `object` to `String`

```java
// Returns the string representation of the boolean argument.
static String	valueOf(boolean b)

// Returns the string representation of the char argument.
static String	valueOf(char c)

// Returns the string representation of the char array argument.
static String	valueOf(char[] data)

// Returns the string representation of a specific subarray of the char array argument.
static String	valueOf(char[] data, int offset, int count)

// Returns the string representation of the double argument.
static String	valueOf(double d)

// Returns the string representation of the float argument.
static String	valueOf(float f)

// Returns the string representation of the int argument.
static String	valueOf(int i)

// Returns the string representation of the long argument.
static String	valueOf(long l)

// Returns the string representation of the Object argument.
static String	valueOf(Object obj)
```

## `toCharArray` String To Chars

```java
String s="hello";  
char c=s.charAt(0); //returns h  
String s1="hello";    

char[] ch=s1.toCharArray();    
for(int i=0;i<ch.length;i++){    
    System.out.println("char at "+i+" index is: "+ch[i]);   
}  
```


## `String`, `StringBuffer` and `StringBuilder`

1. Mutable
    - [x] `StringBuffer` & `StringBuilder` are `mutable`
2. Thread Safe
    - [x] String 
    - [x] StringBuffer via `synchronized`

## StringBuffer

- [Examples](https://www.geeksforgeeks.org/stringbuffer-class-in-java/)

```java
StringBuffer s1 = new StringBuffer();

int size = 10;
StringBuffer s2 = new StringBuffer(size);

StringBuffer s3 = new StringBuffer("this is stringBuffer);
```

### Methods

`append()` Used to add text at the end of the existing text.   
`capacity()` Returns the current capacity.     
`deleteCharAt()` Deletes the character at the index specified    
```java
 StringBuffer s = new StringBuffer("GeeksforGeeks");
 
        s.delete(0, 5);
        // Returns forGeeks
        System.out.println(s);
 
        s.deleteCharAt(7);
        // Returns forGeek
        System.out.println(s);
```

`void ensureCapacity(int expandSize)`
- Ensures capacity is at least equals to the given minimum.   

`insert(...)`	
- Inserts text at the specified index position   

`reverse()`	
- Reverse the characters within a StringBuffer object   

`replace(int start, int end, String str)`	
- Replace one set of characters with another set inside a StringBuffer object   

