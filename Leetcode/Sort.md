# Sort 

- [Sort](#sort)
  - [Quick Sort and Heap Sort](#quick-sort-and-heap-sort)
    - [Kth Largest Element in an Array](#kth-largest-element-in-an-array)
  - [Bucket Sort](#bucket-sort)
    - [Top K Frequent Elements](#top-k-frequent-elements)
    - [Sort Characters By Frequency](#sort-characters-by-frequency)

## Quick Sort and Heap Sort
### Kth Largest Element in an Array

Given an integer array nums and an integer k, return the kth largest element in the array.   
Note that it is the kth largest element in the sorted order, not the kth distinct element.   

```java
Input: [3,2,1,5,6,4] and k = 2
Output: 5
```

Heap Sort 
```java
public int findKthLargest(int[] nums, int k) {     
    PriorityQueue<Integer> heap = new PriorityQueue<>(); 
    /**
      * 3 
      * 2 , 3
      * 1 , 2 , 3 -> poll 1
      * 2 , 3 , 5 -> poll 2
      * 3 , 5 , 6 -> poll 3
      * 4 , 5 , 6 -> poll 4
      * 5 , 6 -> peek 5
      */
    for( int num : nums){
        heap.add(num);

        if(heap.size() > 2){
            heap.poll();
        }
    }

    return heap.peek();
}
```

Quick Sort
```java
public int findKthLargest(int[] nums, int k) {
    k = nums.length - k;
    
    int l = 0, h = nums.length - 1;

    while (l < h) {

        int j = partition(nums, l, h);
        
        if (j == k) {
            break;
        } else if (j < k) {
            l = j + 1;
        } else {
            h = j - 1;
        }
    }

    return nums[k];
}

private int partition(int[] a, int l, int h) {
    
    int i = l, j = h + 1;
    
    while (true) {    

        while (a[++i] < a[l] && i < h) ;
        while (a[--j] > a[l] && j > l) ;
        
        if (i >= j) {
            break;
        }
        
        swap(a, i, j);
    }
    
    swap(a, l, j);
    
    return j;
}

private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
}
```

## Bucket Sort

HashMap records freq of each element, e.g.
| element | 1 | 2 | 3 |
| --------| --| --| --|
| freq    | 2 | 3 | 2 |  

Buckets sorted by freq, e.g.
| freq | 2 | 3 |
| --------| --| --|
| element| 1,3 | 2 |  


### Top K Frequent Elements

- [LeetCode](https://leetcode.com/problems/top-k-frequent-elements/)

Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
```java 
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]

Input: nums = [1], k = 1
Output: [1]

--------------------------------------------

public List<Integer> topKFrequent(int[] nums, int k) {
    
    // collection of frequency for nums
    HashMap<Integer,Integer> map = new HashMap<>();

    // buckets (array of lists)
    // + 1 for sorting 
    List<Integer>[] bucket = new ArrayList[nums.length + 1];
    
    List<Integer> res = new ArrayList<>();

    // freq Counter fro each element in nums
    for(int num : nums){
        map.put(num, map.getOrDefault(num, 0) + 1);
    }

    // Put nums in the bucket sorted by frequency of each num
    for(var num : map.keySet()){
        // initialize the bucket
        int freq = map.get(num);

        // add new bucket
        if(bucket[freq] == null){
            bucket[freq] = new ArrayList<>();
        }
        
        bucket[freq].add(num);
    }
    
    // Output k most frequent elements
    for( int i = bucket.length - 1 ; 
         i >= 0 &&  res.size() < k ; 
         i-- ){
         if( bucket[i] != null){
            res.addAll(bucket[i]);
        }
    }
    return res;
} 
```

### Sort Characters By Frequency

- [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/)

Given a string s, **sort it in decreasing order based on the frequency** of the characters.   
The frequency of a character is the number of times it appears in the string.   

Return the sorted string.    
If there are multiple answers, return any of them.   
```html
Input: "tree"
Output: "eert"
```

```java
public String frequencySort(String s) {

    // Frequency of each character
    HashMap<Character,Integer> map = new HashMap<>();
    for(char c : s.toCharArray()){
        map.put(c, map.getOrDefault(c,0) + 1);
    }

    // Buckets sorted by frequency
    List<Character>[] bucket = new ArrayList[s.length() + 1];
    
    for(char c : map.keySet() ){
       
       int freq= map.get(c);
        
        if(bucket[freq] == null){
            bucket[freq] = new ArrayList<>(); 
        }

        // put character to buckets
        bucket[freq].add(c); 
    }


    StringBuilder strBuilder = new StringBuilder();

    // Output the character from max freq to min freq
    for(int freq = bucket.length - 1 ; freq >= 0 ; freq--){
        if(bucket[freq] != null){
            for(char c : bucket[freq]){
                for(int j = 0 ; j < freq ;  j++){
                    strBuilder.append(c);
                }
            }
        }
    }
    return strBuilder.toString();
}
``` 