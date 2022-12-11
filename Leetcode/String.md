# String
- [String](#string)
    - [Fizz Buzz](#fizz-buzz)
  - [In-place algorithm](#in-place-algorithm)
    - [String Reverse](#string-reverse)
          - [tags : `i & Array#length()-i`](#tags--i--arraylength-i)
    - [Reverse using bytes and collection](#reverse-using-bytes-and-collection)
          - [tags : `bytes[] String#getBytes()`, `Collections#reverse(Collection<T> a)`](#tags--bytes-stringgetbytes-collectionsreversecollectiont-a)
    - [Reverse Via StringBuilder & StringBuffer](#reverse-via-stringbuilder--stringbuffer)
  - [Comparison](#comparison)
    - [:star: Implement `strStr()`](#star-implement-strstr)
    - [String Rotates](#string-rotates)
    - [Longest Common Prefix](#longest-common-prefix)
  - [Container Algorithm | HashMap](#container-algorithm--hashmap)
    - [:star2: Longest Substring Without Repeating Characters](#star2-longest-substring-without-repeating-characters)
    - [Valid Anagram](#valid-anagram)
    - [Longest Palindrome](#longest-palindrome)
    - [Palindromic Substrings](#palindromic-substrings)
    - [:star: Longest Palindromic Substring](#star-longest-palindromic-substring)
    - [:star: Isomorphic Strings](#star-isomorphic-strings)
  - [String & Integer](#string--integer)
    - [:star: String to Integer](#star-string-to-integer)
          - [tags : `Check Overflow`, `Integer.MAX_VALUE`](#tags--check-overflow-integermax_value)
    - [Palindrome Number](#palindrome-number)
          - [Tags : `%` , `Reverse 123 to 321 problem`, `x*10 + y%10`](#tags----reverse-123-to-321-problem-x10--y10)
    - [Count Binary Substrings](#count-binary-substrings)

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



Hints
```java
if( i == 3的倍數 && i == 5的倍數) // i is divisible by 3 and 5 <-- always the first
else if(i == 3的倍數)
else if(i == 5的倍數)
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
###### tags : `i & Array#length()-i`

Write a function that reverses a string.   
The input string is given as an array of characters s.  

Requirement : In-place algorithm with `O(1)` extra memory.   
```java
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
```
hints
1. Mapping relationship Each Iteration 
```java
 ["h","e","l","l","o"]
   '               '
   i              Array#length()-i
```

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
### Reverse using bytes and collection
###### tags : `bytes[] String#getBytes()`, `Collections#reverse(Collection<T> a)`

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

// StrByte to string
String rStr = new String(rStrByte);


// Collection#Reverse
String input = "Geeks For Geeks";

List<Character> trial1 = new ArrayList<>();
for (char c : input.toCharArray()) trial1.add(c);


Collections.reverse(trial1);

// ListIterator li = trial1.listIterator();
// while (li.hasNext())
//    System.out.print(li.next());
```

### Reverse Via StringBuilder & StringBuffer

```java
// StringBuilder#append
// StringBuilder#reverse
String str = "abc";
StringBuilder rStr = New StringBuilder();
rStr.append(str);
rStr.reverse();

// StringBuffer#reverse
String str = "Geeks";
StringBuffer sbr = new StringBuffer(str);
sbr.reverse();
```

## Comparison 


### :star: Implement `strStr()`
[Implement `strStr()`](https://leetcode.com/problems/implement-strstr/)

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
### String Rotates

- [solution](https://lakesd6531.pixnet.net/blog/post/349559815-leetcode%E8%A7%A3%E9%A1%8C%E7%B3%BB%E5%88%97%E2%94%80rotate-string)  
- [one line solution](https://leetcode.com/problems/rotate-string/discuss/118696/C%2B%2B-Java-Python-1-Line-Solution)  

Given a string and an offset, rotate string by offset. (rotate from left to right)
```java
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

cpp
```cpp
bool rotateString(string A, string B) {                
    return A.size() == B.size() && (A + A).find(B) != string::npos;
}    
```
### Longest Common Prefix

Write a function to find the longest common prefix string amongst an array of strings.
- If there is no common prefix, return an empty string `""`.
```java
Input : strs = ["flower","flow","flight"]
Output: "fl"
```

Hints
- Assume one of strings in array strs as prefix
- Compare pre with each elements in strs


```java
public String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) return ""
    
    // first assume "flower" as common prefix
    String pre = strs[0];
    
    for (int i = 1; i < strs.length; i++)
        
        /** Compare each array's prefix **/
        while(strs[i].indexOf(pre) != 0)

            /** Update Prefix **/
            // String#subString(start_inclusive , end_exclusive)
            pre = pre.substring(0,pre.length()-1);  // 
    return pre;
}
```


## Container Algorithm | HashMap

### :star2: Longest Substring Without Repeating Characters

- [Leetcode](https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/?currentPage=1&orderBy=most_votes&query=)

Given a string s, find the length of the longest substring without repeating characters.  
Constraint : `0 <= s.length <= 5 * 104`   
```java
s = "abcabcbb"
Output: 3

s = "pwwkew"
Output : 3
```

```java
public void Solution(){
    int start = 0;

    for(int i = 1 , str )
}
```

### Valid Anagram

Check if Two String that contains same characters
```java
s = "anagram", t = "nagaram", return true.
s = "ra t", t = "car", return false.
```

Hint
- using `char[256]` to record each character counts 
- each elements in `char[256]` should be `0` of both strings have same characters

```java
public boolean isAnagram(String s, String t) {
    char[] cnt = new char[256];

    for(char c : s.toCharArray()){
        cnt[c]++;
    }
    for(char c : t.toCharArray()){
        if(cnt[c] == 0) return false;
        cnt[c]--;
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

```java
Input : "abccccdd"
Output result length : 7
``` 
- One longest palindrome that can be built is `"dccaccd"`, whose length is `7`.

Hint
- **This Exercise you don't have return the Palindrome only the length**
- Consider case `abccccdd` -> `dccbccd` or `bccaaccd`, `aabdc` -> `aba`, `ada`, `aca` 
- Use Container to store counts of each character
- Palindrome means elements has counts in pair (e.g `aaa` -> `3/2 * 2 = 2` pair)
- if `result.length < s.length` means Palindrome is odd

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

    // wrong concept
    // this cant handle case like abccccddd ->dccbccd
    //if((s.length() % 2) !=0){
    //    res++;
    //}


    return palindrome;
}
```

### Palindromic Substrings

Given a string s, return the number of palindromic substrings in it.   
A string is a palindrome when it reads the same backward as forward.   
A substring is a contiguous sequence of characters within the string.   
```java
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
        while( left >= 0 && 
               right < s.length() && 
               s.charAt(left) == s.charAt(right)){
            cnt++;
            left--;
            right++;
        }
        return cnt;
    }
}
```

### :star: Longest Palindromic Substring

- [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

Wrong Concept
- Reverse `S` and become `S'`.
  Find the longest common substring between S and S', which must also be the longest palindromic substring.

For `example, S = "caba", S' = "abac".`
- The longest common substring between `S` and `S'` is `aba`, which is the answer.

`S = "abacdfgdcaba", S' = "abacdgfdcaba".` (`abacd-f-g-....` and `abacd-g-f- ...`)
- The longest common substring between `S` and `S’` is `abacd`. Clearly, this is not a valid palindrome.

We could see that the longest common substring method fails when there exists a reversed copy of a non-palindromic substring in some other part of S. 

To rectify this, each time we find a longest common substring candidate, we check if the substring’s indices are the same as the reversed substring’s original indices. 

If it is, then we attempt to update the longest palindrome found so far; if not, we skip this and find the next
candidate.

Hint
1. Do Iteration for Odd/Even Situation
```java
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
2. There are `only 2n – 1` such centers. why there are `2n – 1` but not `n` centers? 
   The reason is the center of a palindrome can be in between two letters.   
   Such palindromes have even number of letters (such as `abba`) and its center are between the two `b`s.
3. 
```java
public String longestPalindrome(String s) {
    
    // pan
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

    // String#Substring(start_inclusive, end_exclusive)
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

### :star: Isomorphic Strings

[python solution](https://leetcode.com/problems/isomorphic-strings/discuss/57941/Python-different-solutions-(dictionary-etc).)

All occurrences of a character must be replaced with another character while preserving the order of characters.  
```java
s = "egg"
t = "add"
return true.

s = "badc"
t = "baba"
return false
```
- **No two characters may map to the same character, but a character may map to itself.**

Hint
- Using Container s maps c and c maps s has same counts
- Using HashMap

```java


// array container
public boolean isIsomorphic(String s, String t) {
    int[] mapS = new int[128];
    int[] mapT = new int[128];

    for (int i = 0; i < s.length(); i++) {
        char c1 = s.charAt(i);
        char c2 = t.charAt(i);

        // checking the mapping
        if (mapS[c1] != mapT[c2]) {
            return false;
        // same count
        } else {
            if (mapS[c1] == 0) {
                mapS[c1] = i + 1;
                mapT[c2] = i + 1;
            }
        }
    }
    return true;
}

public boolean isIsomorphic(String s, String t) {
    HashMap<Character,Character> csMap = new HashMap<>();
    HashMap<Character,Character> ctMap = new HashMap<>();
    for( int i = 0 ; i < s.length() ; i++){
        char cs = s.charAt(i);
        char ct = t.charAt(i);
        if(csMap.containsKey(cs)){
            if(csMap.get(cs) != ct){
                return false;
            }
        }
        else if(ctMap.containsKey(ct)){
            if(ctMap.get(ct) != cs){
                return false;
            }
        }
        else{
            csMap.put(cs, ct);
            ctMap.put(ct, cs);
        }
    } 
    return true;
}
```

## String & Integer



### :star: String to Integer

###### tags : `Check Overflow`, `Integer.MAX_VALUE`

- [String to Integer](https://leetcode.com/problems/string-to-integer-atoi/)

```java
Input: s = "   -42"
Output: -42
```

Hint
```java
Step 1 : leading whitespace is read and ignored
"   -42" 
    ^
Step 2 : '-' is read, so the result should be negative
"   -42"
     ^
Step 3 : "42" is read in && OVERFLOW CHECKING
"   -42" 
       ^

Since -42 is in the range [-231, 231 - 1], the final result is -42.
```
Always Check Overflow
```java
base = base * 10 + Integer.valueOf(num)
'        '---> base * 10 > Integer.MAX_VALUE
'
'--------> base * 10  + Integer.valueOf(num) > Integer.MAX_VALUE
```


```java
/**
 char[i] - "0" == integer;
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
           ie ---> base = 2147483641
           then when multiplied by 10,It would lead to overflow)
              
          base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7
           ie ---> base = 2147483640 + (Any addition greater than 7) 
           would lead to integer overflow
        **/
        if (base > Integer.MAX_VALUE / 10 || 
             (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)
        ){
            return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        

        base = 10 * base + (str.charAt(i++) - '0');
    }
    return base * sign;
}
```
### Palindrome Number

###### Tags : `%` , `Reverse 123 to 321 problem`, `x*10 + y%10`

- [Palindrome Number](https://leetcode.com/problems/palindrome-number/description/)

An integer is a palindrome when it reads the same backward as forward
```java
121 is palindrome while 123 is not
```

Hint   
Reverse Algorithm
```java
x = 123 
rev = 0

rev = rev*10 + x%10

rev = 123 % 10 = 3
x = 123/10 => 12

rev = 3*10 + 12%10
x = 12/10 => 1

rev = 32*10 + 1 % 10
x = 1/10 => 0
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

