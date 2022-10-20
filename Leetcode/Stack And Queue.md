# Stack And Queue

- [Stack And Queue](#stack-and-queue)
  - [Implement Queue Using Stacks](#implement-queue-using-stacks)
  - [Implement Stack using Queues](#implement-stack-using-queues)
  - [Min Stack](#min-stack)
  - [Valid Parentheses](#valid-parentheses)
  - [Daily Temperatures](#daily-temperatures)
  - [Next Greater Element II](#next-greater-element-ii)

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack; 
```
## Implement Queue Using Stacks

```java
/**
Using Two Stacks
**/

// in 
private Stack<Integer> in = new Stack<>();
// out
private Stack<Integer> out = new Stack<>();

public void push(int x) {
    in.push(x);
}


public int pop() {
    in2out();
    return out.pop();
}

public int peek() {
    in2out();
    return out.peek();
}


/**
3 2 1
1 2 3 
**/
private void in2out() {
    if (out.isEmpty()) {
        while (!in.isEmpty()) {
            out.push(in.pop());
        }
    }
}

public boolean empty() {
    return in.isEmpty() && out.isEmpty();
}

```

## Implement Stack using Queues

```java
class myStack{
    Queue<Integer> queue;

    myStack(){
        queue = new LinkedList<>();
    }

    public void add(int num){
        queue.add(num);

        int cnt = queue.size();

        // Convert FILO to FIFO
        // 1 2 3 
        // 2,3,1
        // 3,1,2
        while( cnt--  > 1){
            int front = queue.poll();
            queue.add(front);
        }
    }

    public boolean empty(){
        return queue.isEmpty(); 
    }
    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }
}
```

## Min Stack 

Implement the MinStack class:

```java
MinStack() initializes the stack object.
void push(int val) pushes the element val onto the stack.
void pop() removes the element on the top of the stack.
int top() gets the top element of the stack.
int getMin() retrieves the minimum element in the stack.
 ```
```

Input
[[],[-2],[0],[-3],[],[],[],[]]

MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);

minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

```java
class MinStack {

  private Stack<Integer> dataStack;
  private Stack<Integer> minStack;
  int min;

  MinStack(){
      dataStack = new Stack<>();
      minStack = new Stack<>();
      min = Integer.MAX_VALUE;
  }

  public void push(int num){
     dataStack.push(num);

     // new pushed num must compare with min, 
     min = Math.min(min, num);
            
     minStack.push(min); 
  }

  public void pop(){
      dataStack.pop();
      minStack.pop();
      min = minStack.isEmpty() ? Integer.MAX_VALUE : minStack.peek();
  }

  public int top(){
      return dataStack.peek();
  }

  public int getMin(){
      return minStack.peek();
  }
}
```

## Valid Parentheses

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for(char c : s.toCharArray()){
        if(c == '{' || c == '[' || c == '('){
            stack.add(c);
        } 

        else{
          char left = stack.pop();
          
          // right 1 ] 
          // right 2 } 
          // right 3 )
          boolean right1 = left == '{' && c == '}';
          boolean right2 = left == '[' && c == ']';
          boolean right3 = left == '(' && c == ')';

          if(right1 || right2 || right3){
              return true;
          }
        }

    }
    return stack.isEmpty();
}


public boolean isValid_better(String s) {
  Stack<Character> stack = new Stack<Character>();
  for (char c : s.toCharArray()) {
      if (c == '(')
          stack.push(')');
      else if (c == '{')
          stack.push('}');
      else if (c == '[')
          stack.push(']');
      else if (stack.isEmpty() || stack.pop() != c)
          return false;
  }
  return stack.isEmpty();
}
```

## Daily Temperatures

```diff
//Input(Temperatures) : 
[73, 74, 75, 71, 69, 72, 76, 73] 
//Output: 
[1, 1, 4, 2, 1, 1, 0, 0]
```
- Output is the number of days you have to wait after the `ith` day to get a warmer temperature. 
- If there is no future day for which this is possible, keep `answer[i] == 0` instead.

```java
/**
Stack 
**/
public int[] dailyTemperatures_arr(int[] temperatures) {
    int len = temperatures.length;
    
    // stores index 
    int[] stack = new int[len];
    int top = -1;
    
    int[] res = new int[len];
    
    for(int i = 0; i < len; i++) {
    
        while(// stack is not empty 
              top > -1 && 
              // If current > top element of stack
              temperatures[i] > temperatures[stack[top]]) {
            
            int idx = stack[top--];
            
            res[idx] = i - idx;
        }
        
        stack[++top] = i;
    }
    
    return ret;
}
```

## Next Greater Element II

- [Next Greater Element II](https://leetcode.com/problems/next-greater-element-ii/)

Given a CIRCULAR integer array nums 
(i.e., the next element of `nums[nums.length - 1]` is nums[0]), 

- Return the `NEXT` greater number for every element in nums.   
  - The `NEXT` greater number of a number `x` is the first greater number to its traversing-order next in the array. 
which means you could search circularly to find its next greater number. If it doesn't exist, return `-1` for this number.

```
* Input: nums = [1,2,1]
* Output: [2,-1,2]

Explanation: 
*  The first 1's next greater number is 2; 
*  The number 2 can't find next greater number. 
*  The second 1's next greater number needs to search circularly, which is also 2.
```

```java
/**
  * nums = [1,2,6,4]
  * i = 0 , curNum = 1 , stack.push(i)
  * i = 1 , curNum = 2
  *         stack.isNotEmpty => 
              Compare :
  *              curNum 2 > nums[stack.peek(0)]
  *              res[stack.pop(0)] = curNum
  *         stack.push(i)
  * i = 2, curNum = 6
  *        stack.isNotEmpty =>
  *           Compare :
  *               curNum 6 > nums[stack.peek(1)]
  *               res[stack.pop(1)] = curNum
  *        stack.push(i)
  * i = 3, curNum = 4
  *        stack.isNotEmpty =>
  *           Compare :
  *               curNum 4 < nums[stack.peek(2)]
  *        stack.push(i)
  * i = 4, curNum[4%4]  = 1
  *        stack.isNotEmpty =>
  *           Compare :
  *               curNum 1 < nums[stack.peek(3)]
  * i = 5, curNum[5%4] = 2    
  *        stack.isNotEmpty =>
  *           Compare :
  *               curNum 2 < nums[stack.peek(3)]
  * i = 6, curNum[6%4] = 6
  *         stack.isNotEmpty =>
  *           Compare :
  *               curNum 6 > nums[stack.peek(3)]
  * i = 7, curNum[7%4] = 4
  *         stack.isNotEmpty =>
  *           Compare :
  *               curNum 4 < nums[stack.peek(2)]
  * End Of Loop
  */
public int[] nextGreaterElements(int[] nums) {
    
    int n = nums.length;
    int[] next = new int[n];
    
    Arrays.fill(next, -1);
    
    // Store index
    Stack<Integer> st = new Stack<>();
    
    for (int i = 0; i < n * 2; i++) {
        
        // Circular i % n 
        int currNum = nums[i % n];
         
        
        while (!pre.isEmpty() && nums[pre.peek()] < currNum) {
            next[pre.pop()] = currNum;
        }
       
        if (i < n){
            pre.push(i);
        }
    }
    return next;
}
```
