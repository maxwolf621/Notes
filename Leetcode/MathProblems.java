import java.util.*;

public class MathProblems {

    int gcd(int a, int b){
        return b == 0 ? a : gcd(b, a%b);
    }

    int lcm(int a, int b){
        return a * b / gcd(a,b);
    }

    /**
     *  Count Primes
     *  Given an integer n, return the number of prime numbers that are strictly less than n.
     * Input: n = 10
     *  Output: 4
     *  Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
     * 
     * *** multiple of prime numbers (except 1) are not prime
     */
    public int countPrimes(int n) {
        boolean[] notPrimes = new boolean[n + 1];
        int count = 0;
        for (int i = 2; i < n; i++) {

            if (notPrimes[i]) {
                continue;
            }
            
            count++;
            
            // if i is not prime then ...then record all i*i is not prime
            // j = 2*2 , 2*2+2 , 2*2+2*2 , 2*2+2*3, 2*2+2*4, ... so on , are not prime
            for (long j = (long) (i) * i;   j < n   ; j += i) {
                notPrimes[(int) j] = true;
            }
        }
        return count;
    }
    public int countPrimes_sol2(int n) {
        boolean[] notPrime = new boolean[n];
        
        int count = 0;
        for (int i = 2; i < n; i++) {

            // if i is prime
            if (!notPrime[i]) {
                
                count++;
                
                // 3*2 , 5*2 , 7*2 , 11*2 , 13*2 , ...
                // 3*3 , 5*3 , 7*3 , 11*3 , 13*3 , ...
                // 3*4 , 5*4 , 7*4 , 11*4 , 13*4 , .... 
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }
        
        return count;
    }
    
    

    /**
     * Given an integer num, return a string of its base 7 representation.
     * Input: num = 100
     * Output: "202"
     */
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

    /**
     * (CONVERT TO BASE 26)
     * Excel Sheet Column Title (Easy)
     * 
     * 1 -> A
     * 2 -> B
     * 3 -> C
     * ...
     * 26 -> Z
     * 27 -> AA
     * 28 -> AB
     */
    public String convertToTitle(int n) {
        
        if (n == 0) {
            return "";
        }

        // start from 1 not zero
        n--;
        
        return convertToTitle(n / 26) + (char) (n % 26 + 'A');   
    }

    /**
     * Given an integer n, return the number of trailing zeroes in n!.
     * Input: n = 5
     * Output: 1
     * Explanation: 5! = 120, one trailing zero.
     */
    public int trailingZeroes(int n) {

        // (120 / 5 + (24/5 + ( 4/5 + ( 0) ) 
        return n == 0 ? 0 : n / 5 + trailingZeroes(n/5);    
    }

    /**
     * Add Binary (Easy)
     * a = "11"
     * b = "1"
     * Return "100".
     */
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
             * 111
             * 111
             * 222
             */
            str.append(carry % 2);

            // if 2/2 = 1 carry
            // else 1/2 = 1
            carry /= 2;
        }
        return str.reverse().toString();
    }

    /**
     * Add Strings
     * Given two non-negative integers, num1 and num2 represented as string, return the sum of num1 and num2 as a string.
     */
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1; // start from digit
        int j = num2.length() - 1;  
        int carry = 0;
        StringBuilder str = new StringBuilder();
        
        /**
         *   "199" 
         * +  011"
         */
        while(carry ==1 || i >= 0 || j >= 0){

            // char to int
            int n1 = i < 0 ? '0' : num1.charAt(i) - '0';
            int n2 = j < 0 ? '0' : num2.charAt(j) - '0';
            
            /** 
             * n = 9 + 1 + 0 = 10 
             * n = 9 + 1 + 1 = 11 
             * n = 1 + 0 + 1 = 2
             */
            int n = n1 + n2 + carry;
            
            /**
             * "10 % 10"  + "11 % 10" + "2 % 10"
             */
            str.append(n%10);

            carry = n/10;
        }

        /**
         * return "210"
         */
        return str.reverse().toString() ;
    }

    /**
     * Valid Perfect Square
     * 
     * Input: num = 16
     * Output: true
     * 
     * Input: num = 14
     * Output: false
     */
    public Boolean isPerfectSquare(int num) {
         /**
          * solution :
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

    /**
     *  Majority Element (Easy)
     * Given an array nums of size n, return the majority element.
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
     * Input: nums = [2,2,1,1,1,2,2]
     * Output: 2
     */
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

    /**
     * Product of Array Except Self (Medium)
     * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
     * 
     * Restriction :
     *  - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
     *  - You must write an algorithm that runs in O(n) time and without using the division operation.
     */
    public int[] productExceptSelf(int[] nums) {
        
    }

    /**
     * Maximum Product of Three Numbers (Easy)
     * Input: nums = [1,2,3]
     * Output: 6
     * 
     * Input: nums = [-1,-2,-3]
     * Output: -6
     */
    public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int n : nums) {
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }
    
            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }
        return Math.max(max1*max2*max3, max1*min1*min2);
    }

    public int maximumProduct_heap(int[] nums) {
        PriorityQueue<Integer> poheap = new PriorityQueue<>();
        PriorityQueue<Integer> neheap = new PriorityQueue<>(Collections.reverseOrder());
        for (int num : nums) {
            poheap.offer(num);
            neheap.offer(num);
            if (poheap.size() > 3) {
                poheap.poll();
            }
            if (neheap.size() > 2) {
                neheap.poll();
            }
        }
        int c1 = 1;
        int max = 0;
        while (!poheap.isEmpty()) {
            max = poheap.poll();
            c1 *= max;
        }
        while (!neheap.isEmpty()) {
            max *= neheap.poll();
        }
        return Math.max(c1, max);
    }
}
