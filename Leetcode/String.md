# String
- [String](#string)
    - [Implement `strStr()`](#implement-strstr)
    - [Longest Substring Without Repeating Characters](#longest-substring-without-repeating-characters)
    - [Fizz Buzz](#fizz-buzz)
  - [In-place algorithm](#in-place-algorithm)
    - [String Reverse](#string-reverse)
  - [Methods of `String`](#methods-of-string)
  - [Comparison](#comparison)
    - [String Rotates](#string-rotates)
    - [Longst Common Prefix](#longst-common-prefix)
    - [Valid Anagram](#valid-anagram)
    - [Longest Palindrome](#longest-palindrome)
    - [~Isomorphic Strings](#isomorphic-strings)
    - [Palindromic Substrings](#palindromic-substrings)
    - [~Longest Palindromic Substring](#longest-palindromic-substring)
  - [Integer As String](#integer-as-string)
    - [String to Integer](#string-to-integer)
    - [Valid Number](#valid-number)
    - [Palindrome Number](#palindrome-number)
    - [Count Binary Substrings](#count-binary-substrings)

### Implement `strStr()`

- [Implement `strStr()`](https://leetcode.com/problems/implement-strstr/)

Return the index of the first occurrence of needle in haystack, or `-1` if needle is not part of haystack.

- What should we return when needle is an empty string? This is a great question to ask during an interview.
    * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's `strstr()` and Java's `indexOf()`.

```java
Input: haystack = "hello", needle = "ll"
Output: 2

Input: haystack = "aaaaa", needle = "bba"
Output: -1

Input: haystack = "", needle = ""
Output: 0
```

### Longest Substring Without Repeating Characters

- [Leetcode](https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/?currentPage=1&orderBy=most_votes&query=)

Given a string s, find the length of the longest substring without repeating characters.
```html
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```

### Fizz Buzz 
- [Fizz Buzz](https://leetcode.com/problems/fizz-buzz/)   

`answer[i] == "FizzBuzz"` if i is divisible by 3 and 5.  
`answer[i] == "Fizz"` if i is divisible by 3.   
`answer[i] == "Buzz"` if i is divisible by 5.  
`answer[i] == i` (as a string) if none of the above conditions are true.   

```java 
Input:  n = 5
output: ["1","2","Fizz","4","Buzz"]
```

```java
/**
  * Using % burdens CPU efficiency
  */
class Solution {
    public List<String> fizzBuzz(int n) {
        

        //     let res : string[] = [];
        List<String> res = new ArrayList<String>(n);

	    // i is not array index
        for(int i = 1, fizz = 3 , buzz = 5 ; i <= n ; i++){
          if( i == fizz && i == buzz){
            // res.push("FizzBuzz");
            res.add("FizzBuzz");
            fizz +=3;
            buzz +=5;
          }else if( i == fizz){
            //  res.push("Fizz");
            res.add("Fizz");
            fizz +=3;
          }else if ( i == buzz){
            // res.push("Buzz");
            res.add("Buzz");
            buzz +=5;
          }else{
            // res push(i.toString())
            res.add(Integer.toString(i));
          }
        }
        return res;
    }
}
```
 
 
## In-place algorithm

### String Reverse

Write a function that reverses a string. The input string is given as an array of characters s.   
```html
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
```
- You must do this by modifying the input array in-place with `O(1)` extra memory.   

```java
class Solution {
    public void reverseString(char[] s) {
        char tmp;
        for( int i = 0 ; i < s.length/2 ; i++ ){
            tmp = s[ s.length - 1 - i];
            s[s.length - 1 -i ] = s[i];
            s[i] = tmp ;
        }
    }   
}
```

## Methods of `String` 

- [methods from Strings](https://www.geeksforgeeks.org/reverse-a-string-in-java/)

```java
/**
  * <code> String.getBytes() </code>
  * 1. <pre> str.getBytes() </pre>
  * 2. <pre> String str = new String(byte[] bytes) </pre>
  */

String str ="abc"

// Convert String to Bytes
byte[] strBytes= str.getBytes();

byte[] rStrBytes = new Byte[strBytes.length];
int len = strBytes.length; 

for( int i  = 0 ;  i < len ; i++){
    rStrByte[i] = strBytes[len - 1 - i];
}

// to string
String rStr = new String(rStrByte);


/**
  * <code> new StringBuilder </code> with method <code> reverse() </code>
  */
String str = "abc";
StringBuilder rStr = New StringBuilder();
rStr.append(str);
rStr.reverse();


/**
  * <code> new StringBuffer(str) </code>
  */
String str = "Geeks";
StringBuffer sbr = new StringBuffer(str);
sbr.reverse();


/**
  * Collection
  */
String input = "Geeks For Geeks";

char[] hello = input.toCharArray();
List<Character> trial1 = new ArrayList<>();

for (char c : hello) trial1.add(c);

Collections.reverse(trial1);

ListIterator li = trial1.listIterator();
while (li.hasNext())
    System.out.print(li.next());
```

## Comparison 

### String Rotates

[solution](https://lakesd6531.pixnet.net/blog/post/349559815-leetcode%E8%A7%A3%E9%A1%8C%E7%B3%BB%E5%88%97%E2%94%80rotate-string)  
[one line solution](https://leetcode.com/problems/rotate-string/discuss/118696/C%2B%2B-Java-Python-1-Line-Solution)  

Given a string and an offset, rotate string by offset. (rotate from left to right)
```html
Input: s = "abcde", goal = "cdeab"
Output: true

Input: s = "abcde", goal = "abced"
Output: false
```
```java
public boolean rotateString(String A, String B) {
    if(A.length() != B.length()){
        return false
    }
    String　AA = A.concat(A);
    
    // abcabc
    //   cab 
    if(AA.contains(B)){
        return true
    }
    
    return false;
}
```

or

```java
public boolean rotateString(String A, String B) {
    return A.length() == B.length() && (A+A).contains(B);
}
```

```cpp
bool rotateString(string A, string B) {                
    return A.size() == B.size() && (A + A).find(B) != string::npos;
}    
```

### Longst Common Prefix

Write a function to find the longest common prefix string amongst an array of strings.
- If there is no common prefix, return an empty string `""`.

```diff
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

```java
public String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) return "";
    
    // "flower"
    String pre = strs[0];
    
    for (int i = 1; i < strs.length; i++)

        /** compare each array's prefix **/
        while(strs[i].indexOf(pre) != 0)

            /** Update Prefix **/
            //  "GeeksforGeeks".substring(2, 5);  -> returns “eks”
            //  "GeeksforGeeks".substring(3);     -> returns “ksforGeeks”
            pre = pre.substring(0,pre.length()-1);  // 
    return pre;
}
```

### Valid Anagram

Check if Two String that contains same characters
```html
s = "anagram", t = "nagaram", return true.
s = "rat", t = "car", return false.
```

```java
public boolean isAnagram(String s, String t) {
    char[] cnt = new char[256];

    for(int i = 0 ; i < s.length() ; i++){
        cnt[s.charAt(i)]++;
    }

    for(int i = 0; i < t.length() ; i++){

        if(cnt[t.charAt(i)] == 0) return false;

        cnt[t.charAt(i)]--;
    }

    for(int n : cnt){
        if(n != 0){
            return false;
        }
    }

    return true;
}


public boolean isAnagram(String s, String t) {
    char[] chs = new char[26];
    
    for(char c : s.toCharArray()){
        chs[c - 'a']++;
    }
    
    for(char c : t.toCharArray()){
    
        if(chs[c - 'a'] == 0) return false;
    
        chs[c - 'a']--;
    }

    // each value in chs[] should be 0
    for(int n : chs ){
        if(n != 0) return false;
    }
    
    return true;
```



### Longest Palindrome

```
Input : "abccccdd"
Output : 7
``` 
- One longest palindrome that can be built is `"dccaccd"`, whose length is `7`.


```java
public int longestPalindrome(String s) {

    int[] cnts = new int[256];
    
    for (char c : s.toCharArray()) {
        cnts[c]++;
    }

    int palindrome = 0;
    
    // dcc_ccd
    for (int cnt : cnts) {
        palindrome += (cnt / 2) * 2;
    }
    
    // a
    if (palindrome < s.length()) {
        palindrome++;   
    }
    
    return palindrome;
}
```

### ~Isomorphic Strings

```java
Given "egg", "add", return true.
```
- `e` maps to `a` and `g` maps to `d`

All occurrences of a character must be replaced with another character while preserving the order of characters.  

**No two characters may map to the same character, but a character may map to itself.**

```java
// array container

public boolean isIsomorphic(String s, String t) {
    int n = s.length();
    int[] mapS = new int[128];
    int[] mapT = new int[128];

    for (int i = 0; i < n; i++) {
        char c1 = s.charAt(i);
        char c2 = t.charAt(i);

        if (mapS[c1] != mapT[c2]) {
            return false;
        } else {
            if (mapS[c1] == 0) {
                mapS[c1] = i + 1;
                mapT[c2] = i + 1;
            }
        }
    }
    return true;
}
```
- [python solution](https://leetcode.com/problems/isomorphic-strings/discuss/57941/Python-different-solutions-(dictionary-etc).)


### Palindromic Substrings

Given a string s, return the number of palindromic substrings in it.   
A string is a palindrome when it reads the same backward as forward.   
A substring is a contiguous sequence of characters within the string.   
```html
Input: "aaa"
Output: 6
```
- Six palindromic strings: `"a", "a", "a", "aa", "aa", "aaa".`

```java
class Solution {
    public int countSubstrings(String s) {
        int cnt = 0;

        for(int i = 0 ; i < s.length() ; i++){
            cnt += palindromeSearch(s , i, i);   // odd palindrome
            cnt += palindromeSearch(s , i, i+1); //even palindrome 
        } 
        
        return cnt;
    }
    public int palindromeSearch(String s, int left , int right){
        int cnt = 0;
        
        /**
         *    a <- a -> a
         */
        while( left >= 0 && right < s.length() && 
               s.charAt(left) == s.charAt(right)){
            cnt++;
            left--;
            right++;
        }
        return cnt;
    }
}
```

### ~Longest Palindromic Substring

- [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

Wrong Concept
- Reverse `S` and become `S’`. Find the longest common substring between S and S’, which must also be the longest palindromic substring.

For `example, S = “caba”, S’ = “abac”.`
- The longest common substring between `S` and `S’` is `aba`, which is the answer.
`S = “abacdfgdcaba”, S’ = “abacdgfdcaba”.` (`abacd-f-g-....` and `abacd-g-f- ...`)
- The longest common substring between `S` and `S’` is `abacd`. Clearly, this is not a valid palindrome.

We could see that the longest common substring method fails when there exists a reversed copy of a non-palindromic substring in some other part of S. To rectify this, each time we find a longest common substring candidate, we check if the substring’s
indices are the same as the reversed substring’s original indices. If it is, then we attempt
to update the longest palindrome found so far; if not, we skip this and find the next
candidate.

tips : **Observe that a palindrome mirrors around its center**. 
```diff
+ Expand From The Center 

Odd : 
        abcba
      <-"->
        abcba
       <-"-> 
        abcba
        <-"->
Even
        abba
      <-''->
        abba
       <-''->

```
- there are `only 2n – 1` such centers.
    - why there are `2n – 1` but not `n` centers? The reason is the center of a palindrome can be in between two letters. Such palindromes have even number of letters (such as `abba`) and its center are between the two `b`s.

```java
/**
  * Expand Around Center
  */
public String longestPalindrome(String s) {
    
    int start = 0, end = 0;
    
    for (int i = 0; i < s.length(); i++) {
        
        // Odd Situation
        int len1 = expandAroundCenter(s, i, i);
        
        // Even Situation
        int len2 = expandAroundCenter(s, i, i + 1);
        
        int len = Math.max(len1, len2);
        
        if (len > end - start) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
    return s.substring(start, end + 1);
}

private int expandAroundCenter(String s, int left, int right) {
    int L = left, R = right;
    
    /**
      * Condition : 
      *    Bound 
      *    Character  
      */
    while (L >= 0 && R < s.length() 
            && s.charAt(L) == s.charAt(R)) 
    {
        L--;
        R++;
    }
    
    /**
      * abadb
         '-'
      L<-' '->R
      */
    return R - L - 1;
}
```


## Integer As String

### String to Integer
- [String to Integer](https://leetcode.com/problems/string-to-integer-atoi/)

```java
Input: s = "   -42"
Output: -42

//Step 1 : leading whitespace is read and ignored
"   -42" 
    ^
//Step 2 : '-' is read, so the result should be negative
"   -42"
     ^
//Step 3 : "42" is read in
"   -42" 
       ^

// Since -42 is in the range [-231, 231 - 1], the final result is -42.
```

```java
/**
 char[i] - "0" == interger;
    "7"  - "0" == 7
**/
public static int myAtoi(String str) {
    if (str.isEmpty()) return 0;
     
    int sign = 1, base = 0, i = 0;
    
    // step 1 
    while (str.charAt(i) == ' ') i++;
        
    // step 2 
    if (str.charAt(i) == '-' || str.charAt(i) == '+')
        sign = str.charAt(i++) == '-' ? -1 : 1;
          
    while (i < str.length() &&
           // Each Character must be btw "0" - "9"
           str.charAt(i) >= '0' && 
           str.charAt(i) <= '9') {
        
        /**
         OVERFLOW CHECK
          TWO SITUATIONS
          base > Integer.MAX_VALUE / 10  
           ie base = 2147483641 ,then when multiplied by 10,It would lead to overflow)
              
          base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7
           ie base = 2147483640 + (Any addition greater than 7 would lead to integer overflow) 
        **/
        if (base > Integer.MAX_VALUE / 10 || 
             (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
            return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        base = 10 * base + (str.charAt(i++) - '0');
    }
    return base * sign;
}

```

### Valid Number

### Palindrome Number

- [Palindrome Number](https://leetcode.com/problems/palindrome-number/description/)

An integer is a palindrome when it reads the same backward as forward
```
121 is palindrome while 123 is not
```
Reverse Algorithm
```java
x = 123 
rev = 0

rev = rev*10 + x%10

x = 123/10 = 12
x = 12/10 = 1
```
```java
public boolean isPalindrome(int x) {
    int reverseX = 0;
    int y = x;
        
    if( x == 0){
        return true;
    }
    
    // Digits or Negative is not Palindrome
    if(x < 0 || (x%10 ==0) ){
        return false;
    }
    
    
    // reverse the number
    while(x!=0){
        reverseX =  reverseX*10 +  x % 10 ;
        
        // 121/10 = 12
        // 12/10 = 1
        // 1/10 = 0
        x /= 10;
    }
    return reverseX == y ;
}
```


### Count Binary Substrings

- [Explanation](https://leetcode.com/problems/count-binary-substrings/discuss/108625/JavaC%2B%2BPython-Easy-and-Concise-with-Explanation)

There are `6` substrings that have equal number of consecutive `1`'s and `0`'s: `"0011", "01", "1100", "10", "0011", and "01"`
```java
// Input: 
"00110011"
// Output: 
6
```

```diff
- preLen = 0, curLen = 1, count = 0

# 1
 + i - 1
 00110011  
  'i
- curLen = 1 + 1 

# 2
 + i - 1
00110011 
  'i
- preLen = curLen, curLen = 1
- count++ ("00")

# 3
 
00110011 
   'i
- count++

# 4
    +i-1
 00110011  
     '-1
- preLen = curLen, curLen = 1
- count++

# 5
     +i-1
 00110011 curLen++ 
      'i
- count++
     
      +i-1
 00110011   
       'i
- preLen = curLen, curLen = 1
- count++
      
       +i-1
 00110011  curLen++
        'i

- count++
```

```java
public int countBinarySubstrings(String s) {
    
    int preLen = 0, curLen = 1, count = 0;
    
    for (int i = 1; i < s.length(); i++) {
        
        if (s.charAt(i) == s.charAt(i - 1)) {
            curLen++;
        } else {
        
            //count += Math.min(preLen, curLen);
            preLen = curLen;
            
            // repeatably reset for each consecutive set
            curLen = 1;    
        }
        
        // if curLen >= preLen means 
        // A new substring composition
        if (preLen >= curLen) {
            count++;
        } 
    }
    return count;
}
```

