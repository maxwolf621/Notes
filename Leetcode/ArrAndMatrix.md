# Array And Matrix 

- [Array And Matrix](#array-and-matrix)
  - [Reference](#reference)
  - [Intersection of Two Array II](#intersection-of-two-array-ii)
  - [Integer to Roman](#integer-to-roman)
  - [In Place Algo](#in-place-algo)
    - [Max Consecutive One](#max-consecutive-one)
    - [Move Zeroes](#move-zeroes)
    - [Remove Element](#remove-element)
    - [Remove Duplicates from Sorted Array && II](#remove-duplicates-from-sorted-array--ii)
    - [Find First and Last Position of Element in Sorted Array](#find-first-and-last-position-of-element-in-sorted-array)
    - [Set Mismatch](#set-mismatch)
    - [Find the Duplicate Number](#find-the-duplicate-number)
    - [Single Number I & II](#single-number-i--ii)
    - [Majority Element](#majority-element)
    - [Array Nesting](#array-nesting)
    - [Toeplitz Matrix](#toeplitz-matrix)
    - [!Beautiful Arrangement II](#beautiful-arrangement-ii)
    - [Merge Sorted Array](#merge-sorted-array)
    - [Set Color](#set-color)
  - [Matrix](#matrix)
    - [Reshape the Matrix](#reshape-the-matrix)
    - [Spiral Matrix](#spiral-matrix)
    - [Rotate Image](#rotate-image)

## Reference
[Reference TwoPointer Python](https://medium.com/@derekfan/%E4%B9%9D%E7%AB%A0%E7%AE%97%E6%B3%95-template-two-pointer-d3144ec8b05c)  
[TwoPointer Cpp](https://blog.techbridge.cc/2019/08/30/leetcode-pattern-two-pointer/)  
[TwoPointer Java](https://chocoluffy.com/2016/12/04/%E6%B5%85%E6%9E%90%E7%BB%8F%E5%85%B8%E9%9D%A2%E8%AF%95%E7%AE%97%E6%B3%95%E9%A2%98-two-pointer%E7%9A%84%E8%BF%90%E7%94%A8/)  



## Intersection of Two Array II

- [Leetcode](https://leetcode.com/problems/intersection-of-two-arrays-ii/)

```java
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9] or [9,4]
```


## Integer to Roman 
- [Integer to Roman](https://leetcode.com/problems/integer-to-roman/)
- [SOL](https://leetcode.com/problems/integer-to-roman/discuss/6274/Simple-Solutionhttps://leetcode.com/problems/integer-to-roman/discuss/6274/Simple-Solution)

Given an integer, convert it to a roman numeral.

| Roman     |   Integer |
|-----------|-----------|
|I          |   1       |
|V          |   5       |
|X          |   10      |
|L          |   50      |
|C          |   100     |
|D          |   500     |
|M          |   1000    |

1. Roman numerals are usually written largest to smallest from left to right. 
2. **However, the numeral for four is not `IIII`. Instead, the number four is written as IV. Because the one is before the five we subtract it making four.**

- `I` can be placed before `V` (5) and `X` (10) to make 4 and 9. 
- `X` can be placed before `L` (50) and `C` (100) to make 40 and 90. 
- `C` can be placed before `D` (500) and `M` (1000) to make 400 and 900.

```typescript
Input: num = 58
Output: "LVIII"
Explanation: L = 50, V = 5, III = 3.
```

## In Place Algo

### Max Consecutive One

```java
Input: nums = [1,1,0,1,1,1]
Output: 3
```
- The first two digits or the last three digits are consecutive `1`s. 
The maximum number of consecutive 1s is 3.

```java
public int findMaxConsecutiveOnes(int[] nums) {
	int max = 0 ;
	int cur = 0 ;
	for(int n : nums){
		cur = n == 0 ? 0 : cur + 1; 
        max = Math.max(cur, max);
	}
	
	return max;
}

// Stream
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        var wrapper = new Object(){ int cnt = 0; int max = 0;};
        Arrays.stream(nums).boxed().forEach(
            v ->{         
            wrapper.cnt = (v == 0 ? 0 : wrapper.cnt + 1);
            wrapper.max = Math.max(wrapper.max, wrapper.cnt);
            });   
        
        return wrapper.max;
    }
}
```

### Move Zeroes 

```java
Given nums = [0, 1, 0, 3, 12]
Output = [1, 3, 12, 0, 0]

public void moveZeroes(int[] nums) {
	int curIndex = 0;
	
	for(int n : nums){
		if(n != 0){
			nums[curIndex++] = n;
		}
	}
	
	for( curIndex ; curIndex < nums.length() ; curIndex++){
		nums[curIndex]  = 0 ;
	}
}
```

### Remove Element

- [LeetCode](https://leetcode.com/problems/remove-element/)
Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. 
- The relative order of the elements may be changed.
- Must do this by modifying the input array in-place with `O(1)` extra memory.

```java
Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
```

```java
/**  
    c*
    [3,2,2,3]
     '  
     
       *
    [2,2,2,3]
       '     
         * 
    [2,2,2,3]
         '
         * 
    [2,2,2,3]
           '
         * 
    [2,2,2,3]
        
  */
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

### Remove Duplicates from Sorted Array && II

- [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

**Given an integer array nums sorted in non-decreasing order**, remove the duplicates in-place such that each unique element appears only once. 
- The relative order of the elements should be kept the same.

```java
Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]

Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
``` 
- It does not matter what you leave beyond the first k elements.
- Must do this by modifying the input array in-place with `O(1)` extra memory.

```java
public int removeDuplicates(int[] nums) {    

    int tail = 0; // tail = 1
    
    for(int i = 1; i < nums.length ; i++ ){
        
        if(nums[tail] != nums[i]){
            nums[++tail] = nums[i];
        }
    }
    return tail+1; // return tail
}
```


- [LeetCode](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)     

Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice.  
```java
Input: nums = [1,1,1,2,2,3]
Output: 5, nums = [1,1,2,2,3,_]

Input: nums = [0,0,1,1,1,1,2,3,3]
Output: 7, nums = [0,0,1,1,2,3,3,_,_]
```
- The relative order of the elements should be kept the same. 
- Must do this by modifying the input array in-place with `O(1)` extra memory.


```java
class Solution {
    public int removeDuplicates(int[] nums) {
        
        // testcase
        // [1,1,1,1,2]
        // [1,1,1,2,2,3,4,5,6,6,6]
        // [1,1,2,3]
        // [0,0,1,1,2,3,2,3,3]
        
        int duplicate = 1;
        int curIndex = 0;
        
        for(int i = 1 ; i < nums.length ; i++){
            if(nums[i - 1] == nums[i] & duplicate < 2){
                duplicate++;
                nums[++curIndex] = nums[i];
            }
            if(nums[i-1] != nums[i]){
                nums[++curIndex] = nums[i];
                duplicate = 1;
            }
        }
        return curIndex+1; // length = index + 1
    } 
    public int removeDuplicates2(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])
                nums[i++] = n;
        return i;
    }
}

```

### Find First and Last Position of Element in Sorted Array

- [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

Given an array of integers nums sorted in **non-decreasing** order, find the starting and ending position of a given target value.
- If target is not found in the array, return `[-1, -1]`.
- Require `O(log n)` runtime complexity.

```html
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

```java
public int[] searchRange(int[] nums, int target) {
        double left = target - 0.5, right = target + 0.5;
        int l = bs(nums, left), r = bs(nums, right);
        if(l == r) return new int[]{-1, -1};
        return new int[]{l, r-1};
}
    
public int bs(int[] nums, double target) {
        int l = 0, h = nums.length-1;
        while(l <= h){
            int m = l + (h - l)/2;
            if(target > nums[m]) l = m+1;
            else h = m-1;
        }
        return l;
}
```

### Set Mismatch 

- [Set Mismatch](https://leetcode.com/problems/set-mismatch/)

You have a set of integers, which originally contains all the numbers from `1` to `n` (`[1, 2, ... , n]`).   
Find the number that occurs twice and the number that is missing and return them in the form of an array.   

```java
Input  : [1,2,2,4]
Output : [2,3]

Input  : [2,2]
Output : [2,1]

Imput  : [1,1]
Output : [1,2]
```
Algorithm
```
CIRCLE (DUPLICATE NUMBER HAS SAME INDEX)

[1,1]
[0,0]

[2,2]
[1,1]  
=> [-2,2]

[2,3]
[1,2]
=> [,-3]
```

```java
public static int[] findErrorNums(int[] nums) {
	int[] res = new int[2];

	for (int i : nums)
	{
        int num = nums[Math.abs(i) - 1];
        
        // the duplicate number will be negative not positive 
		if (num < 0) res[0] = Math.abs(i); 
		// mark it *= -1
        else nums[Math.abs(i) - 1]*= -1;
	}

    // find the missing one 
	for (int i=0; i<nums.length; i++) {
		if (nums[i] > 0) res[1] = i+1;
	}

	return res;
}
```

### Find the Duplicate Number

Given an array of integers nums containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.   

```java
Input: nums = [1,3,4,2,2]
Output: 2
```
- There is ONLY ONE repeated number in nums, return this repeated number. 
- **Must solve the problem without modifying the array nums and uses only constant extra space.**

```java
public int findDuplicate(int[] nums) {
    for(int n : nums){
        
        /**
            abs(1) -> nums[1-1] : 1 marks -1 
            abs(3) -> nums[3-1] : 4 marks -4
            abs(-4) -> nums[4-1] : 2 marks -2
            abs(-2) -> nums[2-1] : 3 marks -3
            abs(-2) -> nums[2-1] : -3 returns 2
        **/
        int idx = Math.abs(n);
        
        if(nums[idx- 1] < 0){
            return idx;
        }
        
        nums[idx-1] *= -1;
    }
    
    return 0;
}


/**
  * Pigeonhole Principle
  *   [1,2,3,4,4,6,7,8]
  *
  */
public int findDuplicate(int[] nums) {

    // l and h are index
     int l = 1, h = nums.length - 1;
     
     while (l <= h) {
         
	    int mid = l + (h - l) / 2;
        
        int cnt = 0;
         
        for (int i = 0; i < nums.length; i++) {
            // [1,2,3,4,5,6,7,8]
            //        '
            if (nums[i] <= mid) cnt++;
    }
        
        if (cnt > mid) h = mid - 1;         
        else l = mid + 1;
     }
     return l;
}
```

### Single Number I & II

- [Single Number](https://leetcode.com/problems/single-number/)
- [Single Number II](https://leetcode.com/problems/single-number-ii/)

Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
```java
Input: nums = [4,1,2,1,2]
Output: 4

Input: nums = [1]
Output: 1
```
- Require Time Complexity `O(n)` and Space Complexity `O(1)` 

Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.
```java
Input: nums = [2,2,3,2]
Output: 3

Input: nums = [0,1,0,1,0,1,99]
Output: 99
```
- Require Time Complexity `O(n)` and Space Complexity `O(1)` 

### Majority Element
- [Boyer-Moore Majority Vote Algorithm](https://leetcode.com/problems/majority-element/discuss/51613/O(n)-time-O(1)-space-fastest-solution)
Given an array nums of size n, return the majority element.

The majority element is the element that appears more than `⌊n / 2⌋` times. You may assume that the majority element always exists in the array.
```java
Input: nums = [3,2,3]
Output: 3

Input: nums = [2,2,1,1,1,2,2]
Output: 2

public class Solution {
    public int majorityElement(int[] nums) {

        // First Assume num[0] is Majority Element in the array
        int cnt = 1;
        int major = nums[0];

        for(int n = 1; n< nums.length ; n++){
	
            if(cnt == 0){
                major = nums[n];
                ++cnt;
            }else if(major == nums[n]){
                ++cnt;
            }else{
                --cnt;
            }
        }

        return major;
    }

    public int majorityElement(int[] nums) {
        int max = nums[0];
        int cnt = 1;

        for(int i = 1 ; i < nums.length ; i++){    
            if(max != nums[i] && cnt > 0){
                --cnt;
            }else if(cnt == 0){
                max = nums[i];
                ++cnt;
            }else{
                ++cnt;
            }

        }
        return max;
    }
}
```

### Array Nesting

- [Array Nesting](https://leetcode.com/problems/array-nesting/)

```java
Input: A = [5,4,0,3,1,6,2]
Output: 4

`A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.`    
One of the longest `S[K]`:  `S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}`
````

```java
Algorithm

 i
[5,4,0,3,1,6,2] 

each element iteration does
for(int j = i , nums[j] !=-1 ;){
    path++;
    int pre = j;
    j = nums[j];
    nums[pre] = -1;
}
```

```java
public int arrayNesting(int[] nums) {
    
    int max = 0;
    
    // Start nesting from index 0
    for (int i = 0; i < nums.length; i++) {
        
        
        // S[j]'s nesting array path length 
        int cnt = 0;
        
        for (int j = i; nums[j] != -1; ) {
            
            cnt++;
            
            int temp = j;
            
            j = nums[j] ;
            
            nums[temp] = -1;
        }
        
        max = Math.max(max, cnt);
    }
    
    return max;
}
```

### Toeplitz Matrix

In the grid, the diagonals are: `"[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".`
In each diagonal all elements are the same, so the answer is true.
```java
Input:  
                      0  1   2  3
[[1,2,3,4],         \ 1,\ 2,\3, \4 
 [5,1,2,3],        5,\ 1,\ 2,\3   
 [9,5,1,2] ]    9,\ 5,\ 1,\ 2    // the last does not need to iterate

Output: true
class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {

        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[i].length - 1; j++) {
                if (matrix[i][j] != matrix[i + 1][j + 1]) return false;
            }
        }
        return true;
    }
}
```

### !Beautiful Arrangement II

- [Leetcode](https://leetcode.com/problems/beautiful-arrangement-ii/)

Given two integers `n` and `k`, construct a list answer that contains `n` different positive integers ranging from `1` to `n`.
-  If there multiple valid answers, return any of them.   
```java
Input: n = 3, k = 2
Output: [1, 3, 2]
```
- The`[1, 3, 2]` has `(n)3` different positive integers ranging from `1` to `3`, and the `[2, 1]` has exactly `2` distinct integers: `1` and `2`


```java
public int[] constructArray(int n, int k) {
    int[] ret = new int[n];

    ret[0] = 1;
    
    for (int i = 1, interval = k; i <= k; i++, interval--) {
        ret[i] = i % 2 == 1 ? ret[i - 1] + interval : ret[i - 1] - interval;
    }
    
    for (int i = k + 1; i < n; i++) {
        ret[i] = i + 1;
    }
    return ret;
}
```

### Merge Sorted Array

- [Leetcode](https://leetcode.com/problems/merge-sorted-array)

You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.

Merge nums1 and nums2 into a single array sorted in non-decreasing order.
```java
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        
    int tailPtr  = (m--) + (n--) - 1;
        
	while( m >= 0 && n >=0  ){
            nums1[tailPtr--] = nums1[m] >= nums2[n] ? nums1[m--] : nums2[n--]; 
        }
    while(n>=0){
            nums1[tailPtr--] = nums2[n--];
        }
    }
}
```

### Set Color

Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

the integers `0`, `1`, and `2` is used for representing the color `red`, `white`, and `blue`, respectively.
```java
Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```


```JAVA
public void sortColors(int[] A) {
    int j = 0, k = A.length - 1;

    for (int i = 0; i <= k; i++) {
        if(A[i] == 0){
            this.swap(A, i, j);
            j++;
        }
        else if(A[i] == 2){
            // after swap we must re-compare with a[i-1] and a[i] 
            this.swap(A, i, k);
            i--;
            k--;
        }
    }
}

private void swap(int[] A, int i, int j){

    int temp = A[j];
    A[j] = A[i];
    A[i] = temp; 
}
```

## Matrix

### Reshape the Matrix

```java
Input: nums = [[1,2],[3,4]]

Reshape The Matrix[1][4] r = 1, c = 4
Output: [[1,2,3,4]]

public int[][] matrixReshape(int[][] nums, int r, int c) {

    int col = nums[0].length;
	int row = nums.length;
	
	if(r*c != row * col){
		return nums;
	} 
	
	int[][] res = new int[r][c];

	for(int i = 0, resR = 0, resC = 0 ; i < row ; i ++){
		for(int j = 0 ; j < col ; j++){
			if (resC == c){
				++resR;
				resC = 0;
			}
			res[resR][resC++] = nums[i][j];
			
		}
	}
	return res;
}
```


### Spiral Matrix 
- [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution)

![image](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)
```java
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

```java
/**
   l   r 
  [1,2,3],u
  [4,5,6],
 d[7,8,9]]

**/
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>(); 
        if (matrix == null || matrix.length == 0) return res;
        
	int n = matrix.length, m = matrix[0].length;
        
	int up = 0,  down = n - 1;
        
	int left = 0, right = m - 1;
        
	while (res.size() < n * m) {
	    // >
	    for (int j = up ; j <= right && res.size() < n * m; j++)
                res.add(matrix[up][j]);

	    // ˅  
        for (int i = up + 1; i <= down - 1 && res.size() < n * m; i++)
        
                res.add(matrix[i][right]);
        // <         
        for (int j = right; j >= left && res.size() < n * m; j--)
                res.add(matrix[down][j]);
        
        // ^        
        for (int i = down - 1; i >= up + 1 && res.size() < n * m; i--) 
                res.add(matrix[i][left]);
                left++; right--; up++; down--; 
        }
        return res;
    }
}
```


### Rotate Image

- [Solution](https://leetcode.com/problems/rotate-image/discuss/18872/A-common-method-to-rotate-the-image)    

You are given an `n x n` 2D matrix representing an image, rotate the image by 90 degrees (clockwise).   
You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.   
- DO NOT allocate another 2D matrix and do the rotation.    
```java
Input: 
matrix = [[1,2,3],
		 [4,5,6],
		 [7,8,9]]
Output:
    [[7,4,1],
	 [8,5,2],
	 [9,6,3]]
```
