###### tags: `Angular`
# Reactive Programming  
- [Reactive Programming](#reactive-programming)
  - [Reference](#reference)
  - [Subject](#subject)
    - [Create The Subject](#create-the-subject)
  - [Combine FromOperators](#combine-fromoperators)
  - [Subscribe (LISTEN) the Subject](#subscribe-listen-the-subject)
## Reference 
- [Reactive Programming 簡介與教學(以 RxJS 為例)](https://blog.techbridge.cc/2016/05/28/reactive-programming-intro-by-rxjs/)
- [[DAY-12] Angular-RXJS非同步事件的好幫手2](https://ithelp.ithome.com.tw/articles/10222014)   




```typescript
-O-O-O-O-|  // each O is a Subject which is Observable
 ' ' ' ' '  
 Operators() // Each Operators combine observables to new observables
 ' ' ' ' '
-1-1-1-1-|-> // subscribe
```
![](https://i.imgur.com/1uzXLNR.png)   


## Subject

A Subject is `Observable`

The Subject have these default methods
- `next`：用來觸發新的事件資料，呼叫` next 並傳入新的事件資訊後，在訂閱步驟就會吃到有新事件發生了。
- `error` ：只有第一次呼叫會在訂閱步驟觸發，當整個流程發生錯誤時，可以呼叫 `error` 並傳入作物資訊，用來告知錯誤內容，同時整個 Observable 就算是結束了。
- `complete` ：只有第一次呼叫會在訂閱步驟觸發，當整個 `Observable` 結束時，用來通知已經結束了，由於是單純的結束了，`complete()` 方法不用帶入任何參數

### Create The Subject 

```typescript
// Operator `fromevent` create a observable Subject
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

Fetch the Data(Observables) from Data Stream

Default methods from Observable
```typescript
const observer = {
  next: (data) => console.log(data),
  error: (err) => console.log(err),
  complete: () => console.log('complete')
};

// Subscribe the observables
obsevables.subscribe({
  next: (data) => console.log(data),
  error: (err) => console.log(err),
  complete: () => console.log('complete')
});
```
