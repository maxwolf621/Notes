
Array Nesting
```java
// nesting
for(int i = 0; i < nums.length ; i++){
    // this S[i]'s path
    int path = 0;
    for(int j = i ; nums[j] != -1 ; ){        
        path++;
        int pre = j;
        j = nums[j];
        // mark 
        nums[pre] = -1;
    }
}
```

Reshape Array
```java
for(int i = 0, resR = 0, resC = 0 ; i < row ; i ++){
	for(int j = 0 ; j < col ; j++){
		if (resC == c){
			++resR;
			resC = 0;
		}
		res[resR][resC++] = nums[i][j];
		
	}
}
```

3 pointers
```java
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

```



```java
s = "A man, a plan, a canal: Panama"
!Character.isDigitOrLetter()
```

### String to Integer

```java
"42" to 42

while (i < str.length() &&
           // Each Character must be btw "0" - "9"
           str.charAt(i) >= '0' && 
           str.charAt(i) <= '9') {
        
        /**
         OVERFLOW CHECK
          TWO SITUATIONS
          base > Integer.MAX_VALUE / 10  
           ie base = 2147483641 ,then when multiplied by 10,It would lead to overflow)
              
          base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7
           ie base = 2147483640 + (Any addition greater than 7 would lead to integer overflow) 
        **/
        if (base > Integer.MAX_VALUE / 10 || 
             (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
            return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        base = 10 * base + (str.charAt(i++) - '0');
    }
```



expanding
```java
public String longestPalindrome(String s) {
    
    int start = 0, end = 0;
    
    for (int i = 0; i < s.length(); i++) {
        
        // Odd Situation
        int len1 = expandAroundCenter(s, i, i);
        
        // Even Situation
        int len2 = expandAroundCenter(s, i, i + 1);
        
        int len = Math.max(len1, len2);
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!
        if (len > end - start) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
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