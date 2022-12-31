- [@fcamel](https://medium.com/@fcamel/%E9%9D%9E%E5%90%8C%E6%AD%A5%E7%A8%8B%E5%BC%8F%E8%A8%AD%E8%A8%88%E5%92%8C-non-blocking-io-a43881081aac)
- [Linux IO](https://ithelp.ithome.com.tw/articles/10161404)  
- [I/O Models: 同步、非同步、阻塞、非阻塞](https://kaka-lin.github.io/2020/07/io_models/)


## 阻塞(Blocking) VS 非阻塞(Non-blocking)

阻塞與非阻塞關注的是同一個THREAD發出請求(呼叫)時在**等待結果時的狀態**。

- 阻塞: 呼叫方發出請求後，在獲得結果之前，呼叫方會懸(`Hang`)住不動(BLOCK)，無法回應，直到獲得結果。

- 非阻塞: 呼叫方發出請求後，呼叫方不會因為等待結果，而懸住不動(`Hang`)。但後續通常透過輪詢(`Polling`)機制取得結果。

## 同步(Synchronous) VS 非同步(Asynchronous)

同步與非同步關注的是**執行緒之間的通訊模式**。
- e.g. 使用者執行緒與Kernel之間的交流模式。


- SYNC : 使用者執行緒發出 I/O 請求後，要等待結果返回或主動詢問結果
- ASYNC: 或稱異步。使用者執行緒發出 I/O 請求後，**不需要等待直接返回，所以沒有返回結果。當Kernel I/O有結果後，會通知使用者執行緒或者呼叫callback函數。**
    - 執行緒發出請求後，就不用管了，另一個執行緒自己會處理，

Async : **等待處理好之後會通知使用者執行緒**     
Sync  : **要等待結果返回或主動詢問結果**    


If Caller hangs 
- Yes -> blocking
- No -> non-blocking

If Kenrnel calls callback function to return result
- Yes -> Async
- No ->  sync 

## Blocking I/O (BIO): Synchronous / Blocking

![圖 5](images/caa22b2b51df558ef38e4b57a168b1afff3c4d09e2699e5078b6309c8410bc8a.png)  
Application hangs itself waiting for Kernel returning Source it needs

## Non-Blocking I/O (NIO): Synchronous / Non-Blocking
![圖 10](images/d7e3899d37760bded4c8bf919b146346a84991aad074e6917cccbcbd31903455.png)  

Application will not hang itself instead it keeps asking(`read()`) Kernel "Did Ya get my source?" via loop
- If Kernel haven't finished the tasks it will return `error`
- Else it copies the Sources application needed to user process and then returns

Efficiency of NIO sucks coz it consumes CPU time (keep calling non-stop)

## I/O Multiplexing: Asynchronous / Blocking
![圖 8](images/6e860a4cd7143e7174ddf593bd10ae45f691f9b827789d2fb650613bf73352d1.png)  

- I/O Multiplexing使用了兩次system call (select/read)，而Blocking I/O只需要一次system call (read)。
- I/O Multiplexing可同時處理多個connection，但Blocking I/O一次只能處理一個。
- 如果I/O Multiplexing要處理的數量沒有很多的話，效能不一定比Blocking I/O的Multi-Thread好，甚至有可能有比較高的latency

## Asynchronous I/O (AIO): Asynchronous / Non-Blocking
![圖 9](images/7f61eb4913801bad752659111546a7f68993d786be0dc82af5ae17b7aaf22e6a.png)  
Application will call kernel and then execute other processing 

- When kernel get the source that application needed it copies sources to Buffer and sends `signal` to user process to notify "Mission Completed" 
    - Signal是由aio_read指定的(deliver signal specified in aio_read)

---

Blocking I/O (BIO):
- Blocking: 你只能在電話那頭等待
- Synchronous: 服務生查完之後，跟你說有沒有訂位成功

Non-Blocking I/O (NIO):
- Non-Blocking: 你可以繼續做其他事情，如跟朋友聊天
- **Synchronous: 但服務生不會主動通知你，你要主動去問有沒有訂位成功**


I/O Multiplexing:
- Blocking: 你不能做其他事情，只能等待
- Asynchronous: 服務生查完會主動打電話通知你

Asynchronous I/O (AIO):
- Non-Blocking: 你打完電話就繼續做其他事情
- Asynchronous: 服務生查完會主動打電話通知你

