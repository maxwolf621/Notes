###### tags: `Angular`
# [Reactive Programming](https://blog.techbridge.cc/2016/05/28/reactive-programming-intro-by-rxjs/)

 - [Angular RXJS](https://ithelp.ithome.com.tw/articles/10222014)



`Reactive Programming = Observer Pattern + Iterator pattern + Functional Programming`

![圖 1](images/d5969d2ade5869a5374d925c1ec5b53c668f772778f7a54d999bb024032d61c3.png)  
- Create : `Observable`
- Combine : `Operator`
![](https://i.imgur.com/KBLtKXd.png)
- Listen : `Subscribe`


```java
-O-O-O-O-|->  // each O is a Subject which is Observable
 ' ' ' ' '  
 Operators() // Each Operators Combine Subjects
 ' ' ' ' '
-0-0-0-0-|-> // subscribe
```
![](https://i.imgur.com/1uzXLNR.png)   


## Subject

A Subject (STREAM) is `Observable`

The Subject have these default methods
- `next`：用來觸發新的事件資料，呼叫` next 並傳入新的事件資訊後，在訂閱步驟就會吃到有新事件發生了。
- `error` ：只有第一次呼叫會在訂閱步驟觸發，當整個流程發生錯誤時，可以呼叫 `error` 並傳入作物資訊，用來告知錯誤內容，同時整個 Observable 就算是結束了。
- `complete` ：只有第一次呼叫會在訂閱步驟觸發，當整個 `Observable` 結束時，用來通知已經結束了，由於是單純的結束了，`complete()` 方法不用帶入任何參數


### Create The Subject 

Operator `fromevent` create a observable Subject

```typescript
import { fromEvent } from 'rxjs';
const source = fromEvent(document, 'click');

// same as 
import { Subject } from 'rxjs';

const source = new Subject();
document.addEventListener('click', (event) => {
  source.next(event);
});
```

## Combine FromOperators

1. 建立類 Creation Operators
2. 組合建立類 Join Creation Operators
3. 轉換類 Transformation Operators
4. 過濾類 Filtering Observables
5. 組合類 Join Operators
6. 多播類 Multicasting Operators
7. 錯誤處理類 Error Handling Operators
8. 工具類 Utility Operators
9. 條件/布林類 Conditional and Boolean Operators
10. 數學/聚合類 Mathematical and Aggregate Operators


## Subscribe (LISTEN) the Subject

Fetch the Data from Subject/Stream

Default methods from Observable
```typescript
const observer = {
  next: (data) => console.log(data),
  error: (err) => console.log(err),
  complete: () => console.log('complete')
};
```

````typescript
source.subscribe({
  next: (data) => console.log(data)
});
```