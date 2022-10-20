## [First Unique Character In A String](https://leetcode.com/problems/first-unique-character-in-a-string/discuss/86348/Java-7-lines-solution-29ms)

Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.
```html
Input: s = "loveleetcode"
Output: 2
```

## [Fizz Buzz](https://leetcode.com/problems/fizz-buzz/discuss/89931/Java-4ms-solution-Not-using-%22%22-operation)

`answer[i] == "FizzBuzz"` if i is divisible by 3 and 5.
`answer[i] == "Fizz"` if i is divisible by 3.
`answer[i] == "Buzz"` if i is divisible by 5.
`answer[i] == i` (as a string) if none of the above conditions are true.

```
Input: n = 5
Output: ["1","2","Fizz","4","Buzz"]
```

```java
// using % to check if certain i is divisible by 3 or 5 or 5 and 3 (15) 
// which burdens CPU usage
class Solution {
    public List<String> fizzBuzz(int n) {
        
        List<String> res = new ArrayList<String>(n);

        for(int i = 1, fizz = 3 , buzz = 5 ; i <= n ; i++){
          if( i == fizz && i == buzz){
            res.add("FizzBuzz");
            fizz +=3;
            buzz +=5;
          }else if( i == fizz){
            res.add("Fizz");
            fizz +=3;
          }else if ( i == buzz){
            res.add("Buzz");
            buzz +=5;
          }else{
            res.add(Integer.toString(i));
          }
        }
        return res;
    }
}
```

## Intersection of Two Array 

```
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
```

## Intersection of Two Array II

```html
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
```

```html
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
Explanation: [9,4] is also accepted.
```

## Odd Even Linked List

![image](https://user-images.githubusercontent.com/68631186/154723484-cb1dd58e-a48f-4a23-89b6-a035602c25fd.png)
```
Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]
```
-  solve the problem in `O(1)` extra space complexity and `O(n)` time complexity.

```java
public class Solution {
public ListNode oddEvenList(ListNode head) {
    if (head != null) {
    
        ListNode odd = head, even = head.next, evenHead = even; 
    
        while (even != null && even.next != null) {
            odd.next = odd.next.next; 
            even.next = even.next.next; 
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead; 
    }
    return head;
}}


//
class Solution {
    public ListNode oddEvenList(ListNode head) {
        int ith = 1;
        int len = 1;
 
        ListNode tail = head;
        ListNode cur = head;
        ListNode pre = head; // combine odd
        
        if(head == null) {
            return null;
        }
            
        while(tail.next != null){
            tail = tail.next;
            len++;
        }

        if(len <= 2){
            return head;
        }
        
        while(len > 0){
            
            // even
            if(ith%2 ==0){
                pre.next = cur.next ;
                pre = pre.next;
                tail.next = cur;
                tail = tail.next;               
            }
            
            cur = cur.next;
            
            ++ith;
            --len;
        }
        
        tail.next = null;
        return head;
    }
}
```

## [Power of Three](https://leetcode.com/problems/power-of-three/)

```
Input: n = 27
Output: true
```
```
Input: n = 0
Output: false
```

## [Missing Number](https://leetcode.com/problems/missing-number/discuss/69786/3-different-ideas%3A-XOR-SUM-Binary-Search.-Java-code)

Given an array nums containing n distinct numbers in the range `[0, n]`, return the only number in the range that is missing from the array.

```diff 
Input: nums = [3,0,1]
Output: 2
--- Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 
--- 2 is the missing number in the range since it does not appear in nums.
```

```java
public int missingNumber(int[] nums) { //binary search
    Arrays.sort(nums );
    int left = 0, right = nums.length;

    while(left<right){
        mid = (left + (right - left))/2;
        if(nums[mid]>mid) right = mid;
        else left = mid+1;
    }
    return left;
}
```

## Happy Number

A happy number is a number defined by 
1. Starting with any positive integer, replace the number by the sum of the squares of its digits.
2. Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
3. Those numbers for which this process ends in 1 are happy.

```java
public boolean isHappy(int n) {
  Set<Integer> inLoop = new HashSet<Integer>();
  int squareSum,remain;

  // Once the current sum cannot be added to set, return false
  while (inLoop.add(n)) {
    squareSum = 0;
    while (n > 0) {
        remain = n%10;
      squareSum += remain*remain;
      n /= 10;
    }
    if (squareSum == 1)
      return true;
    else
      n = squareSum;

  }
  return false;
}
```

- [two pointer solution](https://leetcode.com/problems/happy-number/discuss/56976/O(1)-space-Java-solution)
```java
public boolean isHappy(int n) {
        if(n == 1) return true;
        int n1 = n;
        while(true){
            n = help(help(n));
            n1 = help(n1);
            if(n1 == 1 || n == 1) return true;
            if(n1 == n && n != 1) return false;
        }
    }
    private int help(int n){
        int temp = 0;
        while(n != 0){
            int tail = n % 10;
            temp += tail * tail;
            n /= 10;
        }
        return temp;
    }
```  


## [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/discuss/55099/Simple-Java-Solution-Bit-Shifting)
## [Reverse of 1 Bits](https://leetcode.com/problems/reverse-bits/discuss/54738/Sharing-my-2ms-Java-Solution-with-Explanation)

## [Fractorial Tralling Zeros](https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52367/My-explanation-of-the-Log(n)-solution)

Given an integer n, return the number of trailing zeroes in `n!`.  
Note that `n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1`.  

```html
Input: n = 3
Output: 0
Explanation: 3! = 6, no trailing zero.

Input: n = 5
Output: 1
Explanation: 5! = 120, one trailing zero.
```

## [Excel Sheet Column Number](https://leetcode.com/problems/excel-sheet-column-number/)

##### tags : `how to iterate string`

Given a string columnTitle that represents the column title as appear in an Excel sheet, return its corresponding column number.

```diff
+ A -> 1
+ B -> 2
+ C -> 3
! ...
+ Z -> 26
+ AA -> 27
+ AB -> 28 
! ...

--- For example
Input: columnTitle = "AB"
Output: 28

Input: columnTitle = "ZY"
Output: 701
```

## [Majority Element (Boyer-Moore Majority Vote Algorithm)](https://leetcode.com/problems/majority-element/discuss/51613/O(n)-time-O(1)-space-fastest-solution)

We will sweep down the sequence starting at the pointer position shown above.   
As we sweep we maintain a pair consisting of a current candidate and a counter. Initially, the current candidate is unknown and the counter is 0.   

When we move the pointer forward over an element e:
- If the counter is 0, we set the current candidate to e and we set the counter to 1.

```java
public class Solution {
    public int majorityElement(int[] num) {

        // Assume num[0] is Majority Element in the array
        int cnt = 1;
        int major = nums[0];

        for(int n : num){
            if(cnt == 0){
                major = n;
                ++cnt;
            }else if(major == n){
                ++cnt;
            }else{
                --cnt;
            }
        }

        return major;
    }
}
```

## [Two Sum II Inpput Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. 

Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return the indices of the two numbers, index1 and index2, added by one as an integer array `[index1, index2]` of length 2.

- The tests are generated such that there is exactly one solution. You may not use the same element twice.
- `O(1)` space complexity Only

```diff
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
*** Explanation: The sum of 2 and 7 is 9. 
*** Therefore, index1 = 1, index2 = 2. We return [1, 2].

Input: numbers = [2,3,4], target = 6
Output: [1,3]
*** Explanation: The sum of 2 and 4 is 6. 
*** Therefore index1 = 1, index2 = 3. We return [1, 3].
```


## [Find Peak Ekement](https://leetcode.com/problems/find-peak-element/)

A peak element is an element that is strictly greater than its neighbors.
- You must write an algorithm that runs in `O(log n)` time.

```diff
Input: nums = [1,2,3,1]
Output: 2 ( 2 < 3 > 1 )
*** Explanation: 3 is a peak element 
*** and your function should return the index number 2.
```
- [Solution](https://leetcode.com/problems/find-peak-element/discuss/1290642/Intuition-behind-conditions-or-Complete-Explanation-or-Diagram-or-Binary-Search)



## Intersection of Two Linked Lists

Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.   

If the two linked lists have no intersection at all, `return null`.

```diff
Input: intersectVal = 8, 
- listA = [4,1,8,4,5], 
- listB = [5,6,1,8,4,5], 
- skipA = 2, 
- skipB = 3
Output: Intersected at '8'
```
The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).    
From the head of A, it reads as `[4,1,8,4,5]`. From the head of B, it reads as `[5,6,1,8,4,5]`.    
There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.   

## [Sort List](https://leetcode.com/problems/sort-list/)

Given the head of a linked list, return the list after sorting it in ascending order.
![image](https://user-images.githubusercontent.com/68631186/154366818-80934954-3717-470d-821c-d24cafe48caf.png)

## [Single Number](https://leetcode.com/problems/single-number/)

You must implement a solution with a linear runtime complexity and use only constant extra space.
Require time complexity `O(n)` and Space Complecity `O(1)` 

```diff
Input: nums = [2,2,1]
Output: 1
```
## [Single Number II](https://leetcode.com/problems/single-number-ii/)

Require time complexity `O(n)` and Space Complecity `O(1)` 
```diff
Input: nums = [2,2,3,2]
Output: 3
```

## Best Time to Buy and Sell Stock  

## Best Time to Buy and Sell Stock II

## Pascal's Triangle

## [Remove Duplicates from Sorted List II](https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/)

## Add Bnary

```
Input: a = "11", b = "1"
Output: "100"
```

## Rotate List

```
Input: head = [1,2,3,4,5], k = 2
head will be rotated to right by 2 places
Output: [4,5,1,2,3]
```

```java
// Tips : get the newTailLen and pointer to it
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        
        if( head == null || head.next == null || k == 0) return head;
        
        ListNode tail = head;
        int linkedLen = 1;
        
        
        // Linked Lists Cycle
        while( tail.next != null){
            tail = tail.next;
            linkedLen++;
        }
        
        tail.next = head;    
        
        
        // tailLen starting from head;
        
        int tailLen = linkedLen - k%linkedLen;
        
        while(tailLen > 0){
            
            tail = tail.next;
            
            tailLen--;
        }
        
        head = tail.next;
        tail.next = null;
        
        return head;
    }
}
```

## [Jump Game](https://leetcode.com/problems/jump-game/)

You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.

Return true if you can reach the last index, or false otherwise.


```java
public boolean canJump(int[] nums) {
    int maxLocation = 0;
    for(int i=0; i<nums.length; i++) {
        if(maxLocation<i) return false; // if previous maxLocation smaller than i, meaning we cannot reach location i, thus return false.
        maxLocation = (i+nums[i]) > maxLocation ? i+nums[i] : maxLocation; // greedy:
    }
    return true;
}
```


## [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution)


![image](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)


```java
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```


## [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)

Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
- A subarray is a contiguous part of an array.

## [Pow(x,n)](https://leetcode.com/problems/powx-n/discuss/19546/Short-and-easy-to-understand-solution)

```
Input: x = 2.10000, n = 3
Output: 9.26100
Example 3:

Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
```

## [Rotate Image](https://leetcode.com/problems/rotate-image/discuss/18872/A-common-method-to-rotate-the-image)

## Search Insert Position

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
- Require `O(log n)` runtime complexity.

```html
Input: nums = [1,3,5,6], target = 5
Output: 2
```

## [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

Given an array of integers nums sorted in **non-decreasing** order, find the starting and ending position of a given target value.
- If target is not found in the array, return `[-1, -1]`.
- Require `O(log n)` runtime complexity.

```html
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

## [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) 

There is an integer array nums sorted in ascending order (with distinct values).

Prior to being passed to your function, nums is possibly rotated at an unknown pivot index `k (1 <= k < nums.length)` such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]` (0-indexed).  
- For example, `[0,1,2,4,5,6,7]` might be rotated at pivot index 3 and become `[4,5,6,7,0,1,2]`.

Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or `-1` if it is not in nums.

- Require `O(log n)` runtime complexity.

```java
// find minIndex then find target
public int search(int[] nums, int target) {
    int minIdx = findMinIdx(nums); 
    
    if (target == nums[minIdx]) return minIdx;
    int m = nums.length;
    
    // narrowing the range of search target
    int start = (target <= nums[m - 1]) ? minIdx : 0;
    int end = (target > nums[m - 1]) ? minIdx : m - 1;
    
    
    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) return mid;
        else if (target > nums[mid]) start = mid + 1;
        else end = mid - 1;
    }
    return -1;
}

public int findMinIdx(int[] nums) {
    int start = 0, end = nums.length - 1;
    while (start < end) {
        int mid = start + (end -  start) / 2;
        if (nums[mid] > nums[end]) start = mid + 1;
        else end = mid;
    }
	return start;
}
```

 
## [Implement `strStr()`](https://leetcode.com/problems/implement-strstr/)

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

## Remove Duplicates from Sorted Array

Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same.

- Must do this by modifying the input array in-place with `O(1)` extra memory.

```java
Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
```
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        
        int i = 1;
        for(int n : nums){
            if(nums[i - 1] != n){
                nums[i++] = n;
            }
        }
        
        return i;
    }
}
```

## [Remove Element](https://leetcode.com/problems/remove-element/)

Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. 

- The relative order of the elements may be changed.
- Must do this by modifying the input array in-place with `O(1)` extra memory.

```java
Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
```

```java
class Solution {
    public int removeElement(int[] nums, int val) {
    
        int i = 0;
        for(int n : nums){
            if( n != val){
                nums[i] = n;
                i++;
            }
        }
        
        return i;
    }
}
```


## [Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/discuss/8804/Simple-Java-solution-in-one-pass)


Given the head of a linked list, remove the nth node from the end of the list and return its head.

```java
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Input: head = [1,2], n = 1
Output: [1]

Input: head = [1], n = 1
Output: []
```



## [3sum](https://leetcode.com/problems/3sum/)

Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.
-  Notice that the solution set must not contain duplicate triplets, for example `[[-1,-1,2], [-1,-1,2]]`.

```java
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]

Input: nums = []
Output: []
```

```java
public  List<List<Integer>> threeSum(int[] nums) {
    //the solution set must not contain duplicate triplet
    Set<List<Integer>> res  = new HashSet<>();
    
    //Input: nums = []
    //Output: []
    if(nums.length==0) return new ArrayList<>(res);
    
    Arrays.sort(nums);
    for(int i=0; i<nums.length-2;i++){
        int j =i+1;
        int k = nums.length-1;
        
        // two pointer
        while(j<k){
            int sum = nums[i]+nums[j]+nums[k];
            
            if(sum==0)res.add(Arrays.asList(nums[i],nums[j++],nums[k--]));
            else if (sum >0) k--;
            else if (sum<0) j++;
    }

}
return new ArrayList<>(res);
```


## [Roman to Integer](https://leetcode.com/problems/roman-to-integer/discuss/6537/My-Straightforward-Python-Solution)
```
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```

```
Input: s = "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.
```


## [Integer to Roman](https://leetcode.com/problems/integer-to-roman/)

```
Roman        Integer
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```
Roman numerals are usually written largest to smallest from left to right. 
However, the numeral for four is not IIII. Instead, the number four is written as IV. 
- Because the one is before the five we subtract it making four. 

The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given an integer, convert it to a roman numeral.

## [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

## [String to Integer](https://leetcode.com/problems/string-to-integer-atoi/)

1. Check if the next character (if not already at the end of the string) is '-' or '+'. Read this character in if it is either. 
2. This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
3. Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
4. Convert these digits into an integer (i.e. `"123" -> 123, "0032" -> 32`). If no digits were read, then the integer is 0. Change the sign as necessary (from step 2). If the integer is out of the 32-bit signed integer range `[-231, 231 - 1]`, then clamp the integer so that it remains in the range. 
  - Specifically, integers less than `-231` should be clamped to `-231`, and integers greater than `231 - 1` should be clamped to `231 - 1`.

```html
Input: s = "   -42"
Output: -42
Explanation:
Step 1: "   -42" (leading whitespace is read and ignored)
            ^
Step 2: "   -42" ('-' is read, so the result should be negative)
             ^
Step 3: "   -42" ("42" is read in)
               ^
The parsed integer is -42.
Since -42 is in the range [-231, 231 - 1], the final result is -42.
```

```java
//string to integer
//  char[i] - "0" == interger;
"7" - "0" == 7
```

```java
public static int myAtoi(String str) {
    if (str.isEmpty()) return 0;
    
    int sign = 1, base = 0, i = 0;
    
    // whitespace
    while (str.charAt(i) == ' ')
        i++;x
        
    // negative or positive 
    if (str.charAt(i) == '-' || str.charAt(i) == '+')
        sign = str.charAt(i++) == '-' ? -1 : 1;
          
    /** Check Overflow
      Integer.MAX_VALUE = 2147483647
      
      Integer.MAX_VALUE/10 = 2147483640 
      
      Integer.MAX_VALUE % 10 = 7
    **/
    while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
        
        /**
        base > Integer.MAX_VALUE / 10  
            i.e. base>2147483640 (Ex: if base = 2147483641 ,then when multiplied by 10,It would lead to overflow)
        
        base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7
           i.e. base = 2147483640 + (Any addition greater than 7 would lead to integer overflow)
        **/
        if (base > Integer.MAX_VALUE / 10 || 
            (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) 
        {
            return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        base = 10 * base + (str.charAt(i++) - '0');
    }
    return base * sign;
}

```


```java
private static final int maxDiv10 = Integer.MAX_VALUE / 10;

/**
  * Tips how to Save
  */
public int atoi(String str) {
    int i = 0, n = str.length();
    
    int sign = 1;

    int num = 0;
    
    
    while (i < n && Character.isWhitespace(str.charAt(i))) i++;

    /**
        if (i < n && str.charAt(i) == '+') {
            i++; 
        } else if (i < n && str.charAt(i) == '-') {
            sign = -1;
            i++;
        }
    **/
    if( i < n && str.chaAt(i) = "+" || str.charAt(i) = "-"){
    
        sign = str.charAt(i) = "-" ? -1 : 1;
    }
    
    
    while (i < n && Character.isDigit(str.charAt(i))) {
        
        int digit = Character.getNumericValue(str.charAt(i));
        
        if (num > maxDiv10 || num == maxDiv10 && digit >= 8) {
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        num = num * 10 + digit;
        i++;
    }
    
    return sign * num;
}

```


## Longst Common Prefix

Write a function to find the longest common prefix string amongst an array of strings.
- If there is no common prefix, return an empty string `""`.

```diff
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

```java
public String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) return "";
    
    // ["flower", "flow" , "flight"]
    String pre = strs[0];
    
    for (int i = 1; i < strs.length; i++)

        /** compare each array's prefix **/
        //  int output = ”Learn Share Learn”.lastIndexOf("a");  -> returns 14
        while(strs[i].indexOf(pre) != 0)

            /** Update Prefix **/
            //  "GeeksforGeeks".substring(2, 5);  -> returns “eks”
            //  "GeeksforGeeks".substring(3);     -> returns “ksforGeeks”
            pre = pre.substring(0,pre.length()-1);  // 
    return pre;
}
```

## [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/?currentPage=1&orderBy=most_votes&query=)

Given a string s, find the length of the longest substring without repeating characters.
```html
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```


## Revser words in String


Given an input string s, reverse the string word by word.
```
s = "the sky is blue"
return "blue is sky the".
```

While iterating the string in reverse order, we keep track of a word’s begin and end position.   
When we are at the beginning of a word, we append it.
```
public String reverseWords(String s) {
    StringBuilder reversed = new StringBuilder();
    
    int j = s.length();
    
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == ' ') {
            j = i;  // tail of word
            
          /**  
            * We need to add Space " " at the Each Word
            *
            * scharAt(i-1) == ' ' : In front of beginning of each word
            *   " abc"
            *     '--i
            *
            *  i == 0 : Last Word
           **/
        } else if (i == 0 || s.charAt(i - 1) == ' ') {
            
            if (reversed.length() != 0) {
                reversed.append(' ');
            }
            
            reversed.append(s.substring(i, j));
        }
    }
    return reversed.toString();
}

```

