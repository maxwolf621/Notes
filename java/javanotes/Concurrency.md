# Concurrency

![Alex Wu](image.png)

![image](https://user-images.githubusercontent.com/68631186/126061245-14b919ac-68dd-47ca-a905-5ab1e9292805.png)    
Program (Blueprint)
- Code and Data on DISK

Process (Running Application)
- **Instance of a Program & Organization of a Program in RAM**
- Process 指已經執行(EXECUTED)並且被記憶體載入的Program  
- Program的每一行程式碼隨時都有可能被 CPU 執行

Thread (A running Application handles different tasks at same time)
- **Process is a Container of Threads**
  - e.g. ChatRoom Application 可以同時接受對方傳來的訊息以及發送自己的訊息給對方。
-  **O.S manages each thread inside a Queue，優先權高的可能會執行比較久，但優先權低的也會執行到，只是可能會一直被插隊。**
-  thread 之間工作切換的速度很快，因此看來就像在同時執行

## Life Cycle of a Thread

![image](https://user-images.githubusercontent.com/68631186/126061324-564dfd3f-6b8c-45b9-b4c3-788ec255ec20.png)  

## Thread Life Cycle In JAVA
![image](https://user-images.githubusercontent.com/68631186/126061335-f576d692-d39a-418d-ae27-ee9541410f62.png)  
1. new a thread 物件並執行`start()` method 後，會進入Runnable狀態
2. JVM 中會有一個 scheduler 專門負責處理所有狀態為 Runnable 的 thread 排程，因此即使狀態是 Runnable thread，也必須要被排入執行才會真的執行`run()` Method 中的程式。
 
 
