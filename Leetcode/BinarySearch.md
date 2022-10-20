# Binary Search

- [Binary Search](#binary-search)
    - [Single Element in a Sorted Array](#single-element-in-a-sorted-array)
  - [Approximate Target](#approximate-target)
    - [Search Insert Position](#search-insert-position)
    - [Find Smallest Letter Greater Than Target](#find-smallest-letter-greater-than-target)
    - [`Sqrt(x)`](#sqrtx)
    - [Case with Bound `mid >= sqrt(x)`](#case-with-bound-mid--sqrtx)
    - [Find Minimum in Rotated Sorted Array](#find-minimum-in-rotated-sorted-array)
    - [Find the Duplicate Number](#find-the-duplicate-number)
    - [Get First Bad Version](#get-first-bad-version)
    - [Kth Smallest Element in a Sorted Matrix](#kth-smallest-element-in-a-sorted-matrix)
    - [Find Peak Ekement](#find-peak-ekement)

```cpp
int binary_search(vector<int> &nums, int target) {
    int left = 0;
    int right = nums.size() - 1; 
     
    // interval : [left, right] 
    while (left <= right) {

        // Avoid Overflow
        int mid = left + (right - left) / 2;
        if (nums[mid] > target) {
            right = mid - 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            return mid; // the target is found
        }
    }
    return -1;  // no result
}

// close open interval
int find(vector<int>& nums, int target) {
    int left = 0, right = nums.size();
    
    // [1,2,3,5,6)? 
    //  '--left   '--right
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        
        else if (nums[mid] < target) left = mid + 1;
        
        else right = mid;
    }
    return -1;
}
```
- close interval  `[]`  
e.g. `[3,5]` containing elements `3,4,5`
- open interval `()`  
e.g. `(3,7)` containing elements `4,5,6`
- close-open interval `[)` or `(]`  
e.g. `(3,6]` containing elements `4,5,6`  

### Single Element in a Sorted Array 

- [Single Element in a Sorted Array](https://leetcode.com/problems/single-element-in-a-sorted-array/)

Find out the single Element in a sorted Array
```java 
Input: [1, 1, 2, 3, 3, 4, 4, 8, 8]
Output: 2
```
- Required : Time Complexity `O(logN)` and `O(1)` space.


```java
/**
  * The single element will be located at the odd index

   [1,1,2,3,3,4,4,8,8] let * = + 
    '       +       *
   [1,1,2,3,3]         let * = +
    '   +   *
   [1,1,2] 	       let ' = + + 2
    '+  *
   
   [1,1,2]
        '*
  */
public int singleNonDuplicate(int[] nums) {
    int l = 0, h = nums.length - 1;
    
    while (l < h) {
        
	int m = l + (h - l) / 2;
        
	// m must be even
	// m must always start from ecen index (0, 2, 4, ...)
        if (m % 2 == 1) {
            m--;  
        }
        
	// Only Duplicate Elements Exist Before Index m 
	// [1,1,2,2,3,3, .. , ..]
	//          '--m
        if (nums[m] == nums[m + 1]) {
            l = m + 2;
	
	// [1, 1, 2, 3, 3, 4, 4, 8, 8]
	//		'--m
	// [1, 1, 2, 3, 3]
	//        '--m  
	// [1, 1, 2,]
	//     '--m
	// [1,1,2]
	//  '--m
	
        } else {
            h = m;
        }
    }
    return nums[l];
}
```

## Approximate Target   

### Search Insert Position

Given a sorted array of distinct integers and a target value, return the index if the target is found.    
If not, return the index where it would be if it were inserted in order.   
- Require `O(log n)` runtime complexity.

```java
Input: nums = [1,3,5,6], target = 2
Output: 1

/**
   'l
  [1,3,5,6]  l : 0 , r : 3
	   'm  'r
     mid : 1 ( > target) => r : 0 (mid - 1) 
  [1,3,5,6] l : 0 , r : 0
   'l,r,m
   mid : 0 ( < target) => l : 1 (mid + 1)
*/
class Solution {
    public int searchInsert(int[] A, int target) {
        int l = 0, r = A.length-1;

        // [l , r]
        while(l <= r){    
            int mid = l + (r - l)/2;
            
            if(A[mid] == target) return mid;
            
            else if(A[mid] > target){
                r = mid-1;
            }
            else{
                l = mid+1;
            }
        }
	      // nums[r] < nums[l] < target
        return r + 1; // or return l;
    }
}
```

### Find Smallest Letter Greater Than Target

- [Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/discuss/?currentPage=1&orderBy=most_votes&query=)

Given a characters array letters that is sorted in non-decreasing order and a character target, **return the smallest character in the array that is larger than target.**

```java
Input: letters = ["c", "f", "j"],  target = "d"
Output: "f"

/**
    0   1   2   3   4
  ["c","f","j","k","l"]    ,   target : "d"
    'l      'm      'j     ,   iteration 1
    'l  'j,m               ,   iteration 2
    'l,j,m                 ,   iteration 3
        'l                 ,   iteration 4
  */
public char nextGreatestLetter(char[] a, char x) {
    
    int n = a.length;
    
    int l = 0; 
    int r = n;

    while (l <= r) {
      int mid = l + (r - l) / 2;
      if (a[mid] > x) r = mid - 1;
      else l = mid + 1; // <= target
    }

    // l can end up pointing to index 'n',
    // in which case we return the first element
    return a[l % n];
}
```

### `Sqrt(x)`

- [SqrtX](https://leetcode.com/problems/sqrtx/discuss/25047/A-Binary-Search-Solution)

```diff
Input: 8
Output: 2
Explanation: 
* The square root of 8 is 2.82842..., 
* and since we want to return an integer, 
* the decimal part will be truncated.
```

```java 
/**
	i : 1 and j : 8, mid : 4
	
  [1,2,3,4,5,6,7,8]
  i'     'm      'j
 
	j(8) updates to mid(4) - 1
	
  -----------------------

	i : 1 and j : 3 , mid : 2
	
  [1,2,3,4,5,6,7,8]
  i' 'm'j 
	
	i(1) updates to mid(2) + 1
	
	-----------------------
  
  i : 3 and j : 3, mid : 3 
	
  [1,2,3,4,5,6,7,8]
       'i,j,m
	
	j(3) update to 3 - 1
    	
  ------------------------

  [1,2,3,4,5,6,7,8]
     'j'i,m
  j is smaller than i, return j
*/
class Solution {
    public int mySqrt(int x) {
      int i = 1;
      int j = x;
      
      while (i <= j){
        int mid = i + (j-i)/2 ;
        
        //  [ ... , mid, x/mid ]  
        if (mid < x/mid) i = mid +1;
        
        // it more concise
        // instead mid <= x/mid
        else if( mid == x/mid) return mid;
        else j = mid-1;
      
        
        return j;
    }
}
```

### Case with Bound `mid >= sqrt(x)`

```diff
+ sqrt(4)

[1,2,3,4]
 'i'm  'j
i : 1 and j : 4, mid : 2

( m <= x/m )
j(4) update to 2 - 1

----------------

[1,2,3,4]
 'i,j,m
i : 1 and j : 1, mid : 1

(m < x/m )
(i) 1 update to 1 + 1

----------------

i(2) > j(1) 
```



### Find Minimum in Rotated Sorted Array 

- [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
- [python](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/discuss/158940/Beat-100%3A-Very-Simple-(Python)-Very-Detailed-Explanation)

```html
Input: [3,4,5,1,2],
Output: 1
```

```java
/**
  The Rotated Stored Array 
  Case 1
  [... ,big , bigger , biggest , smallest , smaller, small, ...]
        'l                'mid                        'r
                                  'l                  'r  

  Case 2
  [ ... ,big , bigger , biggest , smallest , smaller, small, ...]
                'l                            'm         'r
                'l                            'r 
*/ 
public int findMin(int[] nums) {

    int l = 0, r = nums.length - 1;
      
    while (l < r) {
      int m = l + (r - l) / 2;
      
      if (nums[m] <= nums[r]) r = m;
      else { 
        l = m + 1;
      }
    }
    
    return nums[l];
}
```

### Find the Duplicate Number 
- [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)

Given an array of integers nums containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.  
- There is only one repeated number in nums, return this repeated number.   
- must solve the problem without modifying the array nums and uses only constant extra space.  
```html
Input: nums = [1,3,4,2,2]
Output: 2
```

### Get First Bad Version

- [Get First Bad Version](https://leetcode.com/problems/first-bad-version/)

Suppose you have n versions `[1, 2, ..., n]` and you want to find out the first bad one, which causes all the following ones to be bad.
- You are given an API bool `isBadVersion(version)` which returns whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

```java
public int firstBadVersion(int n) {
  int l = 1, h = n; 
  
  while (l <= h) {
    int mid = l + (h - l) / 2;
    
    if (isBadVersion(mid)) {
      /**
	     * [ ... , .. ,.. , last_GoodVersion ,first_bad ,bad ,bad, ... , bad]
	     *                                			  '--- m
	     */
       h = mid - 1;
    } else l = mid + 1;
   
  }
  
  return l;
}
```

### Kth Smallest Element in a Sorted Matrix
- [Kth Smallest Element in a Sorted Matrix](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code)

```html
matrix = 
[[ 1,  5,  9],
 [10, 11, 13],
 [12, 13, 15]]

k = 8, return 13.
```

```java
/**
  * Binary Sort
  	
  */
public int kthSmallest(int[][] matrix, int k) {

	// interval : [lo, hi)  hi range with +1
	int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1; 
	
	// [lo, hi) 
	while(lo < hi) {
	
	    int mid = lo + (hi - lo) / 2;
	     
	    // count : Kth element
	    int count = 0
      int j = matrix[0].length - 1;
	    

      // Count How many elements are smaller than mid 
	    for(int i = 0; i < matrix.length; i++) {
        
        // count current kth  
	    	while(j >= 0 && matrix[i][j] > mid) j--;
        
        // j is not ordinal
        count += (j + 1);
	    }
	     
	    if(count < k) lo = mid + 1;
      else hi = mid; // count >= k
	    
	}
	return lo;
}

/**
  * Heap
  */
public int kthSmallest(int[][] matrix, int k) {
    
    int rowLen = matrix.length;
    int colLen = matrix[0].length;
    
    PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>(); // minHeap
    
    // MinHeap For First Row [1,5,9]
    //                                                  x, y, val
    for(int j = 0; j < colLen ; j++) pq.offer(new Tuple(0, j, matrix[0][j]));
    
    for(int i = 0; i < k - 1; i++) {
      // Poll the Root
      Tuple t = pq.poll();

      // x : row
      if(t.x == rowLen - 1) continue;
      
      pq.offer(new Tuple(t.x + 1, t.y, matrix[t.x + 1][t.y]));
    }
    /**
      [ 1,  5,  9]
      [10, 11, 13]
      [12, 13, 15]


      poll 1 from quque 
      offer the new element 10 (1,0) Matrix[0 + 1][0]
      // queue : 5(0,1) ,9(0,2),10(1,0)


      poll 5 from queue 
      offer the new element 11 (1,1)
      //queue : 9(0,2),10(1,0),11(1,1)


      poll 9 from queue
      offer the new element 13 (1,2)
      // queue : 10(1,0),11(1,1),13(1,2)


      poll the root 10 (1,0)
      offer the new element 12 (2,0)
      // queue : 11(1,1),12(2,0),13(1,2)


      poll the root 11 (1,1)
      offer the new element 13 (2,1)
      // queue : 12(2,0),13(2,1),13(1,2)

      poll the root 12 (2,0)
      // queue : 13(2,1),13(1,2)

      poll the root 13 (2,1)
      // queue : 13(1,2)
    */

    return pq.poll().val;
}


class Tuple implements Comparable<Tuple> {
    
    int x, y, val;
    
    public Tuple(int x, int y, int val) {
        // corrdinate 
        this.x = x; 
        this.y = y; 
        this.val = val;
    }

    @Override
    public int compareTo(Tuple that) {
        return this.val - that.val;
    }
}
```
- [`Comparable`](https://openhome.cc/Gossip/Java/ComparableComparator.html)

### Find Peak Ekement
- [Solution](https://leetcode.com/problems/find-peak-element/discuss/1290642/Intuition-behind-conditions-or-Complete-Explanation-or-Diagram-or-Binary-Search)

A peak element is an element that is strictly greater than its neighbors.   
You must write an algorithm that runs in `O(log n)` time.
```diff
Input: nums = [1,2,3,1]
Output: 2 ( 2 <- 3 -> 1 )
*** Explanation: 3 is a peak element 
*** and your function should return the index number 2.
```

Concept
```java 
Situation 1  
   3  mid
  / \
 2   1   nums[mid] > nums[mid - 1] && nums[mid] > nums[mide + 1]


Situation 2
  2  mid
 / \
1   3    nums[mid + 1] > nums[mid]

Situation 3
  2  mid 
 / \
3   1    nums[mid - 1] > nums[mid]
```
