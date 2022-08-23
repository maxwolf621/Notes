# Throwable

- [Interview Question](https://www.journaldev.com/2167/java-exception-interview-questions-and-answers)
- [Error And Exception](https://medium.com/@clu1022/java%E7%AD%86%E8%A8%98-exception-%E8%88%87-error-dbdf9a9b0909)
- [Checked and Unchecked Exception](https://howtodoinjava.com/java/exception-handling/checked-vs-unchecked-exceptions-in-java/)
- [beginnersbook](https://beginnersbook.com/2013/04/java-checked-unchecked-exceptions-with-examples/#:~:text=Unchecked%20exceptions%20are%20not%20checked,t%20give%20a%20compilation%20error.)
- [Ntu](https://www3.ntu.edu.sg/home/ehchua/programming/java/j5a_exceptionassert.html)
- [stackoverflower](https://stackoverflow.com/questions/7129979/java-exception-handling)

## Error and Exception

![image](https://user-images.githubusercontent.com/68631186/128909452-5ebbd566-1aa4-4f7e-b396-d65f64dede5a.png)

#### Error

The Error class describes internal system errors that rarely occur. If such an error occurs, there is little that you can do and the program will be terminated by the Java runtime.
> e.g. `OutOfMemoryError`, `StackOverflowError`, `VirtualMachineError`, or `LinkageError` ...

### Exception

**The biggest difference between checked and unchecked exceptions is that checked exceptions are forced by the compiler and used to indicate exceptional conditions that are out of the control of the program, while unchecked exceptions are occurred during runtime and used to indicate programming errors.**
  
Checked Exception
**The exceptions that are checked during the compile-time are termed as Checked exceptions in Java.**    
1. The Java compiler checks the checked exceptions during compilation to verify that a method that is throwing an exception contains the code to handle the exception with the `try-catch` block or not.    
2. If there is no code to handle them, then the compiler checks whether the method is declared using the throws keyword.
- If the compiler finds neither of the two cases, then it gives a compilation error.(A checked exception extends the Exception class.)
    
```java
public static void main(String[] args){

    // somefile.txt does not exist
    // throws FileNotFoundException
    FileReader file = new FileReader("somefile.txt");
    
    //...
}
```

Unchecked Exception (Runtime exception)

**Unchecked exceptions are not checked at Compile Time.**    
It means if your program is throwing an unchecked exception and even if you didn’t handle/declare that exception, **the program won’t give a compilation error.** 
- **Most of the times these exception occurs due to the bad data provided by user during the User-Program Interaction.(e.g. `NullPointerException`)**

```java
class Example {  
   public static void main(String args[])
   {
	int arr[] ={1,2,3,4,5};
	 
    // Runtime Fialed 
    // ArrayIndexOutOfBoundsException
	System.out.println(arr[7]);
   }
}
```

## Exception Handling

**Exception Handler是例外發生時依序呼叫堆疊在Call Stack內的Exception Handler Methods**   
![圖 2](images/3c84ef162fb8c2b6a1130d1c63e3d0947bda32ab759c8da0d06a5d98c6170699.png)  
- 當Exception Handler所能處理的例外類型與Method拋出的例外類型相符時，即為合適的Exception Handler  

When an exception occurs inside a Java method, the method creates an Exception object and passes the Exception object to the JVM (in Java term, the method `throw` an Exception). 
It searches backward through the CALL STACK until it finds a matching exception handler for that particular class of Exception object (in Java term, it is called `catch` the Exception).     
If the JVM cannot find a matching exception handler in all the methods in the call stack, it terminates the program.


- The JVM is responsible for finding an exception handler to process the Exception object.      
- The Exception object contains the **type of the exception**, and **the state of the program when the exception occurs**. 

```java
public class MethodCallStackDemo {
   public static void main(String[] args) {
      System.out.println("Enter main()");
      methodA();
      System.out.println("Exit main()");
   }
 
   public static void methodA() {
      System.out.println("Enter methodA()");
      methodB();
      System.out.println("Exit methodA()");
   }
 
   public static void methodB() {
      System.out.println("Enter methodB()");
      methodC();
      System.out.println("Exit methodB()");
   }
 
   public static void methodC() {
      System.out.println("Enter methodC()");
      System.out.println(1 / 0);  // divide-by-0 triggers an ArithmeticException
      System.out.println("Exit methodC}
}
```

![image](https://user-images.githubusercontent.com/68631186/167220048-84b756e7-01fc-44eb-b7ac-ecf1540dbe03.png)

In normal situtation
```diff
JVM invoke the main().
main() pushed onto call stack, before invoking methodA().
methodA() pushed onto call stack, before invoking methodB().
methodB() pushed onto call stack, before invoking methodC().
methodC() completes.
methodB() popped out from call stack and completes.
methodA() popped out from the call stack and completes.
main() popped out from the call stack and completes. Program exits.
```

With Exception be like
```
Enter main()
Enter methodA()
Enter methodB()
Enter methodC()
Exception in thread "main" java.lang.ArithmeticException: / by zero
        at MethodCallStackDemo.methodC(MethodCallStackDemo.java:22)
        at MethodCallStackDemo.methodB(MethodCallStackDemo.java:16)
        at MethodCallStackDemo.methodA(MethodCallStackDemo.java:10)
        at MethodCallStackDemo.main(MethodCallStackDemo.java:4)
```
1. `MethodC()` triggers an ArithmeticException. As it does not handle this exception, it popped off from the call stack immediately. 
2. `MethodB()` also does not handle this exception and popped off the call stack. 
3. So `does methodA()` and `main()` method. 
4. The main() method passes back to JVM, which abruptly terminates the program and print the call stack trace, as shown.
![image](https://user-images.githubusercontent.com/68631186/167148922-950f6448-d70b-44d3-a018-656f00f4db70.png)


## Exception Handling Operations `try ... catch ...` 

![圖 1](images/c6fee1237c7185b97cbafb6ca42a680cc1601aa1322941e59de4f385ab1ad8e5.png)  

對於所有的Checked Exceptions，Java規定需規定進行`try ... catch ...`或者 `throw new` 來處理這些Exceptions, 如下 
```java
public class TestException {  
    public static void main(String[] args) {  
        int a = 1;  
        int b = 0;  
        try { 
        //-------------------listen--------------------------------------------+ 
            if (b == 0) throw new ArithmeticException();    
        }
        //--------------------------------------------------------------------+
        
        catch (ArithmeticException e) { //-------------------------------------+  
            //How you gonna handle this exception                              |  
        }//--------------------------------------------------------------------+
        
        // other catch ...
    }  
}  
```


|  |  |
|--|--|
|`try`  |  監聽可能會出現外的程式碼, 將Code放在`try` block內，當某段程式碼發生例外時，例外就被拋出給  
|`catch`| **Fetch the Exception from `try` block, Order of exception：先子類後父類(也就是越底層exception的先寫)**
|`finally`| `finally { ... }`內的程式碼永遠都會被執行, 主要用於回收在`try` Block Access的External Sources(e.g. Database、Network or files in devices)，當它執行完成之後，才會回來執行`try`或`catch` block中的`return`或`throw new`，**如果`finally`中使用了`return`或者`throw new`等終止(Terminate)方法的語句，則就不會跳回執行**
|`throw`  | for blocks `{ .... }` that might throw exception
|`throws` | for methods that might throw exception 

- **`RuntimeException`及其子類的Unchecked Exception發生時, JVM會自動`throw`, 允許Program忽略`RuntimeException`**
- 當運行中的Method的`throw`不能`catch`時，Java允許該方法不做任何拋出聲明, 因為大多數Error(Exception)屬於永遠不被允許發生的狀況
  > 言下之意就是Programmer寫的Code不應不合語法以及邏輯,如果有誤應直接修改程式碼    

### Exception執行順序

```java
class TestThrow{
    static void proc(){
        try{
            throw new NullPointerException("demo");    // 2
        }catch(NullPointerException e){
            System.out.println("Caught inside proc");  // 1
            throw e;
        }
    }

    public static void main(String [] args){
        try{
            proc();
        }catch(NullPointerException e){
            System.out.println("Re-caught: "+e);       //3 
        }
    }
}
```

## Exception Handler for Methods

```java
public void methodD() throws XxxException, YyyException {   // declare method's signature

   // Methd Body ...

   // XxxException occurs
   if ( ... )
      throw new XxxException(...);   // construct an XxxException object and throw to JVM
   ...
   // YyyException occurs
   if ( ... )
      throw new YyyException(...);   // construct an YyyException object and throw to JVM
   ...
}
```
- 如果是**Unchecked exception(Error、RuntimeException或它們的子類)，那麽可以不使用`throws` keyword來聲明要拋出的例外，編譯(Compiler)仍能順利通過**，但在Runtime時會被系統拋出
- 當聲明Methods會拋出Checke Exceptions則表該Method在運行時可能會出現Checked Exceptions，必定得用`try ... catch ...`，或者`throw new`處理例外，否則會導致Compiler Error**
- 當Method有拋出例外時，必須處理或者重新拋出該例外  
  - IF caller無力處理拋出的例外，應該繼續拋出(持續丟給上層,不是擺爛)
- 當Method有`throws`聲明時，拋出的例外則必須要遵守任何Checked Exception的處理和聲明
  - **若Override一個Method, 則不能宣告與覆蓋方法不同或不相關的Exception, 宣告的任何例外必須是被overridden method所宣告的例外之同類或子類**  

#### `throws` and  `try ... catch` 
```java
public void methodC() {  // no exception declared
   ......
   try {
      ......
      // uses methodD() which declares XxxException & YyyException
      methodD();
      ......
   } catch (XxxException ex) {
      // Exception handler for XxxException
      ......
   } catch (YyyException ex} {
      // Exception handler for YyyException
      ......
   } finally {   // optional
      // These codes always run, used for cleaning up
      ......
   }
   ......
}

// using throws 
public void methodC() throws XxxException, YyyException {  
   ...
   // uses methodD() which declares "throws XxxException, YyyException"
   methodD();   // no need for try-catch
   ...
}
```


## `finally`

`finally` Block無論有沒有例外拋出(exception)都會執行。
 - 如果拋出例外，即使沒有`catch` Block，`finally` Block也會執行    
    - 例如`finally`在關閉文件和釋放(release)任何在方法開始時被分配的其他(外部)資源是很有用(避免如因為exception造成memory leak)     
 
`finally` Block是optional,**但是每個try至少需要一個`catch`或者`finally` Block**, 如
```java
/**
  * `finally`會在`try`結束之前執行
  */
try{
    System.out.println("1");
    return ;     // print 1 -> print 2 -> do return
}
finally{
    System.out.println("2");
}

/**
  * 印出
  * 1 
  * 2
  */
```
- `finally` Block不應該有返回值，在有`finally`時`try`中的`return`不會立馬返回caller，而是記錄返回值後執行`finally` Block執行完畢後才做`try`內的`return`


## Assertion 

Assertion is useful in detecting bugs.   

It has two Forms
```java
assert booleanExpr;
assert booleanExpr : errorMessageExpr;
```
When the runtime execute the assertion, it first evaluates the booleanExpr.   
- If the value is true, nothing happens.    
- If it is false, the runtime throws an `AssertionError`
	- If an object is passed as the errorMessageExpr(second form), the object's `toString()` will be called to obtain the message string.

**It also serves to document the inner workings of you program (e.g., pre-conditions and post-conditions) and enhances the maintainability.**


One good candidate for assertion is the switch-case statement where the programmer believes that one of the cases will be selected, and the default-case is not plausible. For example,
```java
public class AssertionSwitchTest {
   public static void main(String[] args) {
      char operator = '%';                  // assumed either '+', '-', '*', '/' only
      int operand1 = 5, operand2 = 6, result = 0;
      switch (operator) {
         case '+': result = operand1 + operand2; break;
         case '-': result = operand1 - operand2; break;
         case '*': result = operand1 * operand2; break;
         case '/': result = operand1 / operand2; break;
         default: assert false : "Unknown operator: " + operator;  // not plausible here
      }
      System.out.println(operand1 + " " + operator + " " + operand2 + " = " + result);
   }
}

```

To enable Assertion
```bash
javac AssertionSwitchTest.java   # no option needed to compile
java -ea AssertionSwitchTest     # enable assertion
```


## Pre-conditions of public methods
**Assertion should not be used to check the validity of the arguments (pre-condition) passed into "public" method.** 

It is because public methods are exposed and anyone could call this method with an invalid argument. Instead, use a `if` statement to check the argument and throw an `IllegalArgumentException` otherwise.   
On the other hand, private methods are under your sole control and it is appropriate to assert the pre-conditions. For example,

```java
// Constructor of Time class
public Time(int hour, int minute, int second) {
   if(hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
      throw new IllegalArgumentException();
   //...
}
```

## Tips

- `throws`、`throw new`、`try ... catch ... finally`分別如何使用？
  - 在Java中，每個例外都是一個Object，它是`Throwable`類或其子類的Instance，**當一個方法出現例外後便會拋出(`throws`)含有例外訊息的Exception Object，呼叫該例外對象的方法則可以讓我們catch到這個例外並可以對其進行處理**  
  - 一般情況下是用`try`進行監聽，如果系統會拋出（`throw`）一個Exception，可以通過它的類型來捕獲（`catch`）它，或通過`finally`來處理；

- **每當遇到一個`try`，例外的結構就會被PUSH到CALL STACK中，直到所有的try block都PUSH完畢**，如果下一級的`try`沒有對某種例外進行處理，CALL STACK就會執行POP, 直到遇到有處理這種例外的`try`或者最終將例外拋給JVM

- Runtime Exception And Unchecked exception
    - RuntimeException表示JVM在**運行時遇到的例外**，是一種常見運行錯誤，只要程式設計得宜通常就不會發生   
    - **Checked Exception跟Program's Context環境有關，即使Program設計無誤，仍可因使用的問題而拋出例外**   
    - Java Compiler要求方法必須聲明拋出該方法執行時可能會發生的Checked Exception，但是並不要求必須宣告拋出未被Catch的`RuntimeException`   


例外和繼承一樣，是OOP中經常被濫用的東西，在Effective Java中對例外的使用的幾點原則： 
- 不要將例外處理用於正常的控制流（設計良好的API不應該強迫它的呼叫者為了正常的控制流而使用例外） 
- 對可以修復的情況使用Checked Exception，對Error使用`RuntimeException` 
- 避免不必要的使用Checked Exception（透過一些狀態檢測機制）  
- 每個方法拋出的例外都要有Context
- **保持例外的原子性(Purity)** 
- 不要在`catch`中忽略掉`try`捕獲到的exception
- 常見的`RuntimeException` 
  - ArithmeticException
  - ClassCastException 
  - IllegalArgumentException （Parameter例外） 
  - IndexOutOfBoundsException 
  - NullPointerException 
  - SecurityException

## NoClassDefFoundError v.s. ClassNotFoundException

這兩種例外都是出現在當某個特定的類別無法於 runtime 時找到的情況下，但出現的理由不同

- `NoClassDefFoundError` 發生於當某個特定的Class在編譯時期找得到, 但是在執行時期又消失了的情況. e.g. 產生了 A.class 以及 B.class, B.class 裡面會用到 A.class, 然後在執行程式之前, 把 A.class 砍掉, 就會出現`NoClassDefFoundError`

- `ClassNotFoundException`: 這發生在執行期間, 呼叫`Class.forName()`或著是 `loadClass()`去載入某個Class時, 該Class無法在Class Path上被找到的情況.
