# Math

### gcd and lcm

Extended Euclidean algorithm   
![image](https://user-images.githubusercontent.com/68631186/156912587-92ad98f2-0345-491d-b1ec-5e1ca67bbea7.png)   
```java 
int gcd(int a, int b){
  return b == 0 ? b : gcd(b, a%b);
}
```

```java
int lcm(int a, int b){
  return a * b / gcd(a,b);
}
```

### Count Primes

- [Count Primes](https://leetcode.com/problems/count-primes/)

Given an integer n, return the number of prime numbers that are strictly less than n.

```html 
Input: n = 10
Output: 4
There are 4 prime numbers less than 10, they are 2, 3, 5, 7.

Input: n = 0
Output: 0
Example 3:

Input: n = 1
Output: 0
```

```java
public int countPrimes(int n) {

  // Assume Each Number < n is not Prime
  boolean[] isNotPrime = new boolean[n+1];
  
  int count = 0;
  
  for (int i = 2; i < n; i++) {

      // if i is prime
      if (!isNotPrime[i]) {
          
          count++;

          // MUTIPLE OF PRIME IS NOT PRIME
          // 2*2 , 3*2 , 5*2 , 7*2 , 11*2 , 13*2 , ...
          // 2*3,  3*3 , 5*3 , 7*3 , 11*3 , 13*3 , ...
          // 2*4,  3*4 , 5*4 , 7*4 , 11*4 , 13*4 , .... 
          for (int j = 2; i*j < n; j++) {
              notPrime[i*j] = true;
          }
      }
  }
  return count;
}
```
    
### 

Given an integer num, `return` a string of its base 7 representation.
     
```html
Input: num = 100
Output: "202"
```    

```java
public String convertToBase7(int num) {

    if (num == 0) {
        return "0";
    }

    StringBuilder sb = new StringBuilder();

    boolean isNegative = num < 0;
    if (isNegative) {
        num = -num;
    }

    while (num > 0) {
        sb.append(num % 7);

        // move next digit 
        num /= 7;
    }

    String ret = sb.reverse().toString();
    
    return isNegative ? "-" + ret : ret;
}
```

### Excel Sheet Column Title (CONVERT TO BASE 26)

```html
* 1 -> A
* 2 -> B
* 3 -> C
* ...
* 26 -> Z
* 27 -> AA
* 28 -> AB
```

```java 
/**
  * 27 =  "A+ + "A"
  * 28 =  "A" + "B"
  */
public String convertToTitle(int n) {

    if (n == 0) {
        return "";
    }
    
    // Start At 1 not 0
    n--;

    return convertToTitle(n / 26) + (char) (n % 26 + 'A');   
}
```

### Factorial Trailing Zeroes 

Given an integer n, return the number of trailing zeroes in n!.
```html
Input: n = 5    
Output: 1
Explanation: 5! = 120, one trailing zero.
```

```java
public int trailingZeroes(int n) { 
        // (120/5 + (24/5 + (4/5 + (0) ) 
        return n == 0 ? 0 : n / 5 + trailingZeroes(n/5);    
}
```

### Add Binary

```html
* a = "11"
* b = "1"
* Return "100".
```

```java
public String addBinary(String a, String b) {

    int i = a.length() - 1, j = b.length() - 1 ;

    int carry = 0;
    
    StringBuilder str = new StringBuilder();

    // carry could be 1 , 0 
    while (carry == 1 || i >= 0 || j >= 0) {
        
        if (i >= 0 && a.charAt(i--) == '1') {
            carry++;
        }
        
        if (j >= 0 && b.charAt(j--) == '1') {
            carry++;
        }

        /**
         *   111
         * + 111
         *   ---
         *   222
         */
        str.append(carry % 2);
        
        
        carry /= 2;
    }
    
    return str.reverse().toString();
}
```

### Power Of Three 
- [Power of Three](https://leetcode.com/problems/power-of-three/)

```java
Input: n = 27
Output: true

Input: n = 0
Output: false
```

### **Add** Strings

Given two non-negative integers, num1 and num2 represented as string, return the sum of num1 and num2 as a string.

```java
public String addStrings(String num1, String num2) {
    int i = num1.length() - 1; 
    int j = num2.length() - 1;  
    
    int carry = 0;
    StringBuilder str = new StringBuilder();
    
    while(carry > 0 || i >= 0 || j >= 0){

        int n1 = i < 0 ? 0 : num1.charAt(i--) - '0';
        int n2 = j < 0 ? 0 : num2.charAt(j--) - '0';

        carry = n1 + n2 + carry;

        str.append(carry%10);

        carry /= 10;
    }

    return str.reverse().toString() ;
}
```

### Valid Perfect Square

```diff
* Input: num = 16
* Output: true
* 
* Input: num = 14
* Output: false
```
   
```java
public Boolean isPerfectSquare(int num) {
     /**
      * 1 , 4 , 9 , 16 , 25 , 36 , 49
      *   3   5   7    9   11   13
      * 1 = 1
      * 4 = 1 + 3
      * 9 = 1 + 3 + 5
      * 16 = 1 + 3 + 5 + 7
      * 25 = 1 + 3 + 5 + 7 + 9
      * 36 = 1 + 3 + 5 + 7 + 9 + 11
      * ....
      */
     int commonDiffers = 1;
     while(num > 0){
         num -= commonDiffers;
         commonDiffers +=2;
     }

     return num == 0;
}

// Newton Method
public boolean isPerfectSquareNewTonMethod(int num) {
    long x = num;

    while (x * x > num) {
        x = (x + num / x) >> 1;
    }
    return x * x == num;
}
```

### Majority Element

Given an array nums of size n, return the majority element.

```html
* The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
* Input: nums = [2,2,1,1,1,2,2]
* Output: 2
```

```java
public int majorityElement(int[] nums) {
    /**
     * cnt : record total accounts for each element
     */
    int cnt = 0;
    int majority = nums[0];
    for (int num : nums) {
        /**
         * arr [0,0,0,1,1]
         * majority = 0 , cnt = 0 + 1
         * cnt = 1 + 1
         * cnt = 2 + 1
         * cnt = 3 - 1
         * cnt = 2 - 1
         */
        majority = (cnt == 0) ? num : majority;
        cnt = (majority == num) ? cnt + 1 : cnt - 1;
    }
    return majority;
}
```


### Product Of Array Except Self

Given an integer array nums, return an array answer such that `answer[i]` is equal to the product of all the elements of nums except `nums[i]`.

- The product of any prefix or suffix of nums is guaranteed to fit in a `32-bit` integer.
- You must write an algorithm that runs in `O(n)` time and without using the division operation.

```java
/**
  *       1       2       3,   4
  * left  1       1       2*1  2*3*1
  * right 4*3*2   4*3     4    1
  */
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        
        // Calculate lefts and store in res.
        int left = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0)
                left = left * nums[i - 1];
            res[i] = left;
        }
        // Calculate rights and the product from the end of the array.
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1)
                right = right * nums[i + 1];
            res[i] *= right;
        }
        return res;
    }
}
```


### Maximum Product of Three Numbers  

```html
Input: nums = [1,2,3]
Output: 6 

Input: nums = [-1,-2,-3]
Output: -6
```

```java
public int maximumProduct_heap(int[] nums) {
    
    // positive, store 3 max numbers
    PriorityQueue<Integer> poheap = new PriorityQueue<>();
    
    // negative, store 2 min number
    PriorityQueue<Integer> neheap = new PriorityQueue<>(Collections.reverseOrder());
    
    for (int num : nums) {
        poheap.offer(num);
        neheap.offer(num);
       
        if (poheap.size() > 3) {
            poheap.poll();
        }
        
        // store only 2 : two negatives makes a positive
        if (neheap.size() > 2) {
            neheap.poll();
        }
    }
    
    int postiveMax = 1;
    
    int max = 0;
    
    while (!poheap.isEmpty()) {
        max = poheap.poll();
        positiveMax *= tempMax;
    }
    
    while (!neheap.isEmpty()) {
        max *= neheap.poll();
    }
    
    return Math.max(c1, max);
}
```

## Bits 

### Number Of 1 Bits

- [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/discuss/55099/Simple-Java-Solution-Bit-Shifting)

### Reverse of 1 Bits
- [Reverse of 1 Bits](https://leetcode.com/problems/reverse-bits/discuss/54738/Sharing-my-2ms-Java-Solution-with-Explanation)
