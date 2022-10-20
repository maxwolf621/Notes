# Dynamical Programming

### Maximum Subarray 
- [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)

Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
- A subarray is a contiguous part of an array.
```java
//Input: 
nums = [-2,1,-3,4,-1,2,1,-5,4]
//Output: 6
//[4,-1,2,1] has the largest sum = 6.
```

```diff
+ dp[i] = Math.max(A[i] + dp[i - 1] , A[i]);
```


### Determine Whether Matrix Can Be Obtained By Rotation

- [LeetCode](https://leetcode.com/problems/determine-whether-matrix-can-be-obtained-by-rotation/)

Given two `n x n` **binary** matrices mat and target, return `true` if it is possible to make mat equal to target by rotating mat in 90-degree increments, or false otherwise.

```java
/**
  n == mat.length == target.length
  (1 <= n <= 10)
**/

//Input:
mat    = [[0,0,0],
          [0,1,0],
          [1,1,1]]
   
target = [[1,1,1],
          [0,1,0],
          [0,0,0]]
// Output: true
```

```java
/**
 000    100 111 001
 010 -> 110 010 011
 111    100 000 001
**/
public boolean findRotation(int[][] mat, int[][] target) {
  
}
```


### Longest Common Subsequence

- [LeetCode](https://leetcode.com/problems/longest-common-subsequence/)
- [Sol](https://reurl.cc/GxdYgv)

Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return `0`.

```java
// Input: 
text1 = "abcde", text2 = "ace" 
// The longest common subsequence is "ace" and its length is 3.
// Output: 3  
```


### Jump Game

- [Jump Game](https://leetcode.com/problems/jump-game/)

You are given an integer array nums.  
You are initially positioned at the array's first index, and **each element in the array represents your maximum jump length at that position.**

Return true if you can reach the last index, or false otherwise.

```java
Input: nums = [2,3,1,1,4]
Output: true
```
- Jump 1 step from index 0 to 1, then 3 steps to the last index.
Example 2:

```java
Input: nums = [3,2,1,0,4]
Output: false
```
- You will always arrive at index 3 no matter what. 
Its maximum jump length is 0, which makes it impossible to reach the last index.


