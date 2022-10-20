# Hash

Space Complexity : `O(N)` 
Time Complexity : `O(1)`  

- [Hash](#hash)
  - [Roman to Integer](#roman-to-integer)
  - [Contains Duplicate](#contains-duplicate)
  - [Longest Harmonious Subsequence](#longest-harmonious-subsequence)
  - [Longest Consecutive Sequence](#longest-consecutive-sequence)
  - [Degree of an Array](#degree-of-an-array)
  - [Two Sum](#two-sum)
  - [Sum of Square Numbers](#sum-of-square-numbers)
  - [Isomorphic Strings](#isomorphic-strings)
 
## Roman to Integer
- [Roman to Integer](https://leetcode.com/problems/roman-to-integer/discuss/6537/My-Straightforward-Python-Solution)

| Roman    |   Integer|
| ---------|----------|
| I        |     1    |
| V        |     5    |
| X        |     10   |
| L        |     50   |
| C        |     100  |
| D        |     500  |
| M        |     1000 |

```java
Input: s = "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.
```

```java
/**
  * result += current number > pre-number ?
  *            IV = (current number - pre-nubmer) - pre-number : current number
  *
  */
class Solution {
    private Map<Character, Integer> rToI = Map.of(
       'I', 1, 
       'V', 5,
       'X', 10,
       'L', 50,
       'C', 100, 
       'D', 500,
       'M', 1000
    );
    
    public int romanToInt(String s) {
        
        int pre = 0 ; 
        int result = 0;
        
        for(char c : s.toCharArray()){
            int cur = rToI.get(c);
            
            
            result += (cur > pre) ? ( (cur -pre) - pre) : cur;
            
            pre = cur;
        }
        
        return result;
    }
}
```
```typescript
function romanToInt(s: string): number {
    const m : Map<string, number> = new Map([
       ['I', 1   ], 
       ['V', 5   ],
       ['X', 10  ],
       ['L', 50  ],
       ['C', 100 ],   
       ['D', 50  ], 
       ['M', 1000],
    ]);
    
    let cur = 0   ;
    let result = 0;
    let pre = 0;
    
    let strArray = [...s];
    
    for(let c of strArray){
        cur = m.get(c);    
        result += cur > pre ? cur - pre - pre : cur;        
        pre = cur;
            
    }
        
    return result;
};
```

## Contains Duplicate

- [Leetcode](https://leetcode.com/problems/contains-duplicate/description/) 
- [`final` with Collection](https://stackoverflow.com/questions/26500423/what-does-it-mean-for-a-collection-to-be-final-in-java)

```html
Input: nums = [1,2,3,1]
Output: true
```

```java
final List list = new LinkedList(); 
// ...
list.add(someObject); //allowed
list.remove(someObject); //allowed
list = new LinkedList(); //not allowed (Reference is Final which can not be changed to reference other addr)
list = refToSomeOtherList; //not allowed
```

```java
public boolean containsDuplicate(int[] nums) {
    
    /**
      * {@code final} prevents the set from changing the reference
      * but still allows the modification of the current set. 
      */
    final Set<Integer> set = new HashSet<Integer>();
    
    for (int num : nums) {

      if(set.contains(num)){
        return true;
      }
        set.add(num);
    }

    return false;
    //return set.size() < nums.length;
}
```

## Longest Harmonious Subsequence

- [Leetcode](https://leetcode.com/problems/longest-harmonious-subsequence/description/)

```java
Input: [1,3,2,2,5,2,3,7]
Output: 5
//The longest harmonious subsequence is [3,2,2,2,3].
```
- A harmonious array as an array where the difference between its maximum value and its minimum value is exactly 1.

```python
def findLHS(self, A):
    """
    count = {}
    for x in A:
    count[x] = count.get(x, 0) + 1
    """
    count = collections.Counter(A)
    
    longest = 0
    
    for x in count:
        if x+1 in count:
            longest = max(ans, count[x] + count[x+1])
    return longest
```

```java
class Solution {
    public int findLHS(int[] nums) {
        Map<Integer, Integer> maps = new HashMap<>();
        
        for(int n : nums){
            maps.put(n , maps.getOrDefault(n , 0) + 1);
        }
        

        // find the longest harmonious path 
        int longst = 0;
        
        for(int n  : nums){
            if(maps.containsKey(n + 1)){
                longst = Math.max(longst , maps.get(n) + maps.get(n+1));
            }
        }
        return longst;
    }
}
```

## Longest Consecutive Sequence

- [Leetcode](https://leetcode.com/problems/longest-consecutive-sequence/description/)

Given an **unsorted** array of integers nums, return the length of the longest consecutive elements sequence.
- Require Time Complexity `O(N)`

```java
Input: nums = [100, 4, 200, 1, 3, 2]
The longest consecutive elements sequence is `[1, 2, 3, 4]`. 
Return its length: 4.

Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

```java
public int longestConsecutive(int[] nums) {
    Map<Integer, Integer> countForNum = new HashMap<>();
    
    for (int num : nums) {
        countForNum.put(num, 1);
    }
    
    for (int num : nums) {
        forward(countForNum, num);
    }
    return maxCount(countForNum);
}

private int forward(Map<Integer, Integer> countForNum, int num) {
    if (!countForNum.containsKey(num)) {
        return 0;
    }
    int cnt = countForNum.get(num);
    if (cnt > 1) {
        return cnt;
    }
    cnt = forward(countForNum, num + 1) + 1;
    countForNum.put(num, cnt);
    return cnt;
}

private int maxCount(Map<Integer, Integer> countForNum) {
    int max = 0;
    for (int num : countForNum.keySet()) {
        max = Math.max(max, countForNum.get(num));
    }
    return max; 
}
```


## Degree of an Array
- [Leetcode](https://leetcode.com/problems/degree-of-an-array/description/)

Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.    
Find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.    
```java
Input: nums = [1,2,2,3,1]
Output: 2

3 has max degree
[1,2,3,2,3,3]
     '-----'
its min path is
[1,2,3,2,3,3]
     '---'
public int findShortestSubArray(int[] A) {
    
    // First pos of each repeated element
    // Degree of each element in array
    Map<Integer, Integer> degree = new HashMap<>(), first = new HashMap<>();

    // Path's Length
    int res = 0;

    // Most Repeated Element
    int maxDegree = 0;

    for (int i = 0; i < A.length; ++i) {
        
	    first.putIfAbsent(A[i], i);
        // Freq of elements
        degree.put(A[i], degree.getOrDefault(A[i], 0) + 1);
        
        // Update Degree or count the distance
        if (degree.get(A[i]) > maxDegree) {
                
            maxDegree = degree.get(A[i]);
            
            /**
            * The Distance : i - first.get(A[i]) + 1
            * index|0-1-2-3-4-5-6|
            *      [1,2,2,3,4,2,1]
            *         '       '--- i    
            *         '--first.get(A[i])
            */
            res = i - first.get(A[i]) + 1;
            
        } else if(degree.get(A[i]) == maxDegree){
                res = Math.min(res, i - first.get(A[i]) + 1);
        }
    }
    
    return res;
}
```


## Two Sum
- [Leetcode](https://leetcode.com/problems/two-sum/description/)

```html
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
```
```java
public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> indexForNum = new HashMap<>();
    
    for (int i = 0; i < nums.length; i++) {
        if (indexForNum.containsKey(target - nums[i])) {
            return new int[]{indexForNum.get(target - nums[i]), i};
        } else {
            indexForNum.put(nums[i], i);
        }
    }
    return null;
}
```
## Sum of Square Numbers
```java
public class Solution {
    public boolean judgeSquareSum(int c) {
        HashSet<Integer> maps= new HashSet<Integer>();
        
        for(int i =0 ; i <= Math.sqrt(c) ; i++){
            
            maps.add(i*i);
            
            if(maps.contains(c - i*i)){
                return true;
            }
        }
        return false;
    }
}
```
## Isomorphic Strings

```java
Given "egg", "add", return true.
```
- `e` maps to `a` and `g` maps to `d`

All occurrences of a character must be replaced with another character while preserving the order of characters.  

**No two characters may map to the same character, but a character may map to itself.**

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> maps = new HashMap<>();
        
        char[] records = new char[256];
        
        for(int i = 0 ; i < s.length() ; i++){
            
            char ch_s = s.charAt(i);
            char ch_t = t.charAt(i);
            
            if(maps.containsKey(ch_s)){
                if(maps.get(ch_s) != ch_t){
                    return false;
                }
            }
            else{
                if(records[ch_t] != 0){
                    return false;
                }
                else{
                    records[ch_t]++;
                    maps.put(s.charAt(i), t.charAt(i));
                }
            }
        }
        
        return true;
    }
}