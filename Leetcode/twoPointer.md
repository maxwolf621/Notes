# Two Pointer
[Two Pointer Pattern](https://blog.techbridge.cc/2019/08/30/leetcode-pattern-two-pointer/)

- [Two Pointer](#two-pointer)
  - [Array](#array)
    - [Two Sum II Input Array Is Sorted](#two-sum-ii-input-array-is-sorted)
    - [Sum of Square Numbers](#sum-of-square-numbers)
    - [Reverse Vowels of a String](#reverse-vowels-of-a-string)
    - [Valid Palindrome](#valid-palindrome)
    - [Valid Palindrome II](#valid-palindrome-ii)
    - [Merge Sorted Array](#merge-sorted-array)
    - [3sum (ITERATION)](#3sum-iteration)
    - [Set Color (ITERATION)](#set-color-iteration)
  - [Matrix](#matrix)
    - [Search a (sort) 2D Matrix II](#search-a-sort-2d-matrix-ii)
  - [Happy Number](#happy-number)

## Array 
### Two Sum II Input Array Is Sorted

- [Two Sum II Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

Find out two elements which were added up equals target

```java
Input: numbers= {2, 7, 11, 15}, target=9
Output: {1, 2}

public int[] twoSum(int[] nums, int target) {
    int l = 0;
    int r = nums.length - 1;
    while(l < r){
        int sum = nums[l] + nums[r];

        if(sum > target) r--;
        else if(sum < target) l++;
        else return new int[]{l+1,r+1};

    }

    return new int[]{};
}
```

### Sum of Square Numbers

```java
Input: 5
Output: True
// Explanation: 1 * 1 + 2 * 2 = 5

public class Solution {
    public boolean judgeSquareSum(int c) {
        
        int left = 0, right = (int) Math.sqrt(c);
        
        while (left <= right) {
        
            int cur = left * left + right * right;
            
            if (cur < c) {
                left++;
            } else if (cur > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }
}
```


```py
from math import sqrt
class Solution:
    def judgeSquareSum(self, c: int) -> bool:
        left, right = 0, int(sqrt(c))
        while left <= right:
            total = left * left + right * right
            if total < c:
                left += 1
            elif total > c:
                right -= 1
            else:
                return True
        return False

```

### Reverse Vowels of a String

- [Java 8 HashSet](https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
- [Covert an Array to a Set](https://stackoverflow.com/questions/3064423/how-to-convert-an-array-to-a-set-in-java/3064447)


Given a string s, reverse only all the vowels in the string and return it.
```java
Given s = "leetcode", return "leotcede".
```

```java
class Solution {
    /** 
     Before java 8
         private final static HashSet<Character> vowels = new HashSet<>(
            Array.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')); 
    )
    **/

    Set<Character> vowels =  Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
    
    public String reverseVowels(String s) {

        int r = s.length() - 1;
        int l = 0;

        // String type is final
        char[] result = new char[s.length()];
        
        // Without {@code =} It may cause empty (e.g. u/0000)/ uncompleted 
        while(l <= r){
            char charLeft = s.charAt(l);
            char charRight= s.charAt(r);
            
            // No `While` Condition Allowed
            if(!vowels.contains(charLeft)){   
                //System.out.println(charLeft);
                result[l++] = charLeft;
            }
            else if(!vowels.contains(charRight)){
                
                //System.out.println(charRight);
                result[r--] = charRight;
            }
            else{
                // SWAP
                result[l++] = charRight;
                result[r--] = charLeft;
            }
        }
        
        // String(char[] chars)
        return new String(result);
    }
}
```

```python
def reverseVowels(self, s):
    s = list(s)
    vows = set('aeiouAEIOU')
    l, r = 0, len(s) - 1
    while l <= r:
        while l <= r and s[l] not in vows: l += 1
        while l <= r and s[r] not in vows: r -= 1
        if l > r: break
        s[l], s[r] = s[r], s[l]
        l, r = l + 1, r - 1
    return ''.join(s)
```


### Valid Palindrome

- [Palindrome](https://translate.google.co.uk/?sl=auto&tl=de&text=Palindrome&op=translate)

```java
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.

Input: s = " "
Output: true
```

```java
class Solution {
    public boolean isPalindrome(String s) {
    
    char[] c = s.toCharArray();
    
    for (int i = 0, j = c.length - 1; i < j; ) {
        if (!Character.isLetterOrDigit(c[i])) i++;
        else if (!Character.isLetterOrDigit(c[j])) j--;
        else if (Character.toLowerCase(c[i++]) != Character.toLowerCase(c[j--])) 
            return false;
    }
    return true;
}
```

### Valid Palindrome II

Find Out Any Valid **Palindrome** Existing in String

```html
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
```

```java
public boolean validPalindrome(String s) {
    
    int l  = 0;
    int r  = s.length() - 1 ;

    // iterate
    while( l < r ){
        if(s.charAt(l) != s.charAt(r)){
            return this.validPalindromeForSub(s, l + 1 , r) || this.validPalindromeForSub(s, l, r - 1);
        }
        ++l;
        --r;
    }

    return true;
} 

private boolean validPalindromeForSub(String s, int l, int r){
    
    while( l < r ){
        if(s.charAt(l) != s.charAt(r)){
            return false;
        }
        ++l;
        --r;
    }

    return true;
}
```
- [python slice](https://chunyeung.medium.com/%E7%B5%A6%E8%87%AA%E5%AD%B8%E8%80%85%E7%9A%84python%E6%95%99%E5%AD%B8-8-%E8%B3%87%E6%96%99%E7%B5%90%E6%A7%8B%E8%88%87%E5%88%97%E8%A1%A8-list-38eaf8701bfa)
```py
class Solution(object):
    def validPalindrome(self, s):
        """
        :type s: str
        :rtype: bool
        """
        # Time: O(n)
        # Space: O(n)
        left, right = 0, len(s) - 1
        while left < right:
            if s[left] != s[right]:
                one, two = s[left:right], s[left + 1:right + 1]
                return one == one[::-1] or two == two[::-1]
            left, right = left + 1, right - 1
        return True
```

### Merge Sorted Array

```java
Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
```

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int index1 = m - 1; 
    int index2 = n - 1;
    int indexMerge = m + n - 1;
    
    while (index2 >= 0) {
        if (index1 < 0) {
            nums1[indexMerge--] = nums2[index2--];
        } else if (index2 < 0) {
            nums1[indexMerge--] = nums1[index1--];
        } else if (nums1[index1] > nums2[index2]) {
            nums1[indexMerge--] = nums1[index1--];
        } else {
            nums1[indexMerge--] = nums2[index2--];
        }
    }
}
```

### 3sum (ITERATION)

- [3sum](https://leetcode.com/problems/3sum/)

Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.
- Notice that the solution set must not contain duplicate triplets, for example `[[-1,-1,2], [-1,-1,2]]`.

```java
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]

Input: nums = []
Output: []

public List<List<Integer>> threeSum(int[] nums) {
    
    // the solution set must not contain duplicate triplet
    Set<List<Integer>> res  = new HashSet<>();
    
    // Input: nums = []
    // Output: []
    if(nums.length==0) return new ArrayList<>(res);
    
    Arrays.sort(nums);
    
    for(int i=0; i< nums.length-2; i++){
        
        // two pointer
        int j = i+1;
        int k = nums.length-1;
        
        while(j<k){
            int sum = nums[i] + nums[j] + nums[k];
            
            if( sum == 0 ) res.add(Arrays.asList(nums[i],nums[j++],nums[k--]));
            else if (sum > 0 ) k--;
            else if (sum < 0 ) j++;
    }

    return new ArrayList<>(res);
}
```
### Set Color (ITERATION)

Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

the integers `0`, `1`, and `2` is used for representing the color `red`, `white`, and `blue`, respectively.
```java
Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

```java
class Solution {
    public void sortColors(int[] nums) {
        int[] n = new int[3];
        for (int num: nums) 
            ++n[num];
        for (int i = 0; i < n[0]; ++i) nums[i] = 0;
        for (int i = n[0]; i < n[0] + n[1]; ++i) nums[i] = 1;
        for (int i = n[0] + n[1]; i < n[0] + n[1] + n[2]; ++i) nums[i] = 2;        
    }
}

/**
 *  i : iteration  
 *  case 1 for A[i] == 2
 *    0, 2, .. , x , 1 , ...
 *       'i          'k 
 *    0, 1, ..., x, 2
 *    'i         'k
 *  case 2 for A[i] == 0
 *    1,1,0, ... , 
 *    'j  'i
 *    0,1,1,2,2,2
 *      'j'i
 */
public void sortColors(int[] A) {
    int j = 0, k = A.length - 1;

    for (int i = 0; i <= k; i++) {
        if(A[i] == 0){
            this.swap(A, i, j);
            j++;
        }
        else if(A[i] == 2){
            this.swap(A, i, k);
            k--;

            i--;
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

### Search a (sort) 2D Matrix II

- [Leetcode](https://leetcode.com/problems/search-a-2d-matrix-ii/)

```java
[
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
]

target = 5
Output: true
```

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[row].length - 1;
        
        while(row < matrix.length && col >= 0){
            
            int t = matrix[row][col];
            
            if(target == t){
                return true;
            }
            else if (target > t){
                row++; 
            }
            else{
                col--;
            }
        }
        
        return false;
    }
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
