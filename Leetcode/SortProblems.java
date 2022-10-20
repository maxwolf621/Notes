import java.util.ArrayList;
import java.util.*;
public class SortProblems{
    /**
     * Top K Frequent Elements
       Given [1,1,1,2,2,3] and k = 2, return [1,2].
     */
    public int[] topKFrequent(int[] nums, int k) {
        // collection of frequency for nums
        HashMap<Integer,Integer> map = new HashMap<>();

        // buckets (array of lists)
        // + 1 for sorting 
        List<Integer>[] bucket = new ArrayList[nums.length + 1];
        
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        // put nums in the bucket sorted by frequency of each num
        for(var num : map.keySet()){
            // initialize the bucket
            int freq = map.get(num);
            if(bucket[freq] == null){
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(num);
        }

        // Output Top K Frequency
        

    } 

 /**
  * <p> Sort Characters By Frequency </p>
    Input: "tree"
    Output: "eert"

    Explanation:
    'e' appears twice while 'r' and 't' both appear once.
    So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
  */
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
      

  /**
   * Sort Colors (Medium)
   * Input: [2,0,2,1,1,0]
   * Output: [0,0,1,1,2,2]
   * 
   */
  public void sortColors(int[] nums) {
      // 
      int redPtr = 0; 
      int bluePtr = nums.length;
      int len = nums.length;
      int i = 0;
      while(i < len){
          // redPtr is 0 , bluePtr is 2
          if(nums[i] == 0){
              this.swap(nums, i ,redPtr);
              redPtr++;
              i++;
          }else if(nums[i] == 2){
              swap(nums, i, bluePtr);
              bluePtr--;
              i++;
          }
      }
  }

  public void sortColorsRef(int[] nums) {
    int zero = -1, one = 0, two = nums.length;
    while (one < two) {
        if (nums[one] == 0) {
            swap(nums, ++zero, one++);
        } else if (nums[one] == 2) {
            swap(nums, --two, one);
        } else {
            ++one;
        }
    }
}
  private void swap(int[] nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}
