
# Error Handling

- [Error Handling](#error-handling)
  - [throwError](#throwerror)
  - [catchError](#catcherror)
  - [retry && retryWhen](#retry--retrywhen)
  - [finalize](#finalize)

一般使用`.subscribe`內取得`error`缺點是整個 `pipe` 中只要任一個`operator`內發生錯誤，整個訂閱 observable stream process 都會錯誤並結束**
**若希望不要中斷整個 `observable`可以使用RxJS 內有提供`catchError`來攔截錯誤**
```typescript
// Observable Stream process
subject.pipe(
    // ...
).subscribe(
  //...
  ,
  error => {
    console.log('error', error)
});
```


## throwError

使用`throwError`會中斷整個Observable Stream，或主動拋出錯誤，常常搭配`if` 

```typescript
this.httpClient.get(`.../posts`).pipe(
  tap(data => {
    if(data.length === 0) {
      // 主動丟出錯誤
      throwError('no data')
    }   
  }),
  catchError(error => {
    console.log(error);
    return of([]);
  })
)
```
- **在 Observable 中，不論是` throw new Error()` 還是回傳` throwError()` 都會產生錯誤並中斷資料流**


## catchError 

`catchError`可以攔截某個observable(event)發生的錯誤，並回傳另外一個`observable`，讓整個observable stream繼續運作，透過這種方式可以避免`observable`運作中斷，並記錄該observable錯誤訊息

```typescript
this.httpClient.get(`.../posts`).pipe(
  catchError(error => {
    // catch Error of the observable
    console.log(error);

    // return new observable
    return of([]);
  })
)
```


```typescript
interval(1000)
  .pipe(
    map(data => {
      if (data % 2 === 0) {
        return data;
      } else {
        // 中斷整個Observable Stream Process
        throw new Error('發生錯誤');
      }
    }),
    catchError(error => {
      return interval(1000);
    }),
  	map(data => data * 2)
  )
  .subscribe({
    next: data => {
      console.log(`catchError 示範 (2): ${data}`);
    },
    error: error => {
      console.log(`catchError 示範 (2): 錯誤 - ${error}`);
    }
  });
// catchError 示範 (2): 0
// (這時候來源 Observable 發生錯誤，用另一個 Observable 取代)
// (以下是錯誤處理後新的 Observable)
// catchError 示範 (2): 0
// catchError 示範 (2): 2
// catchError 示範 (2): 4
                   
           ---0---#  
catchError(---0---1---2...)
           ---0-------0----1----2...
                  ^ 發生錯誤，換成 catchError 內的 Observable
       map(data => data * 2)
           ---0-------0----2----4...
```


map + if可以用以下代替
```typescript
switchMap(data => iif(() => data % 2 === 0, of(data), throwError('發生錯誤')))
```

## retry && retryWhen

`retry(-1)` : 表示不斷重複

使用 retryWhen 需要設計一個 notifier callback function，retryWhen 會將錯誤資訊傳入 notifier function，同時需要回傳一個 Observable，retryWhen 會**訂閱**這個 Observable，每當有事件發生時，就進行重試，直到這個回傳的 Observable 結束，才停止重試。

以下程式在錯誤發生時，會每三秒重試一次，共重試三次：
```typescript
interval(1000)
  .pipe(
    switchMap(data => 
      iif(() => data % 2 === 0, of(data), throwError('發生錯誤'))),
    map(data => data + 1),
    retryWhen((error) => interval(3000).pipe(take(3)))
  )
  .subscribe({
    next: data => {
      console.log(`retryWhen 示範 (1): ${data}`);
    },
    error: error => {
      console.log(`retryWhen 示範 (1): 錯誤 - ${error}`);
    },
    complete: () => {
      console.log('retryWhen 示範 (1): 完成');
    }
  });
// retryWhen 示範 (1): 1
// retryWhen 示範 (1): 1
// retryWhen 示範 (1): 1
// (重試的 Observable 完成，因此整個 Observable 也完成)
// retryWhen 示範 (1): 完成
彈珠圖：

-1-#
retryWhen(---0---1---2|)
-1----1----1----1|
   ^ 發生錯誤，三秒後重試
                ^ 重試的 Observable 完成，因此整個 Observable 也完成
```

由於是讓重試的 Observable 完成，因此整個資料流也會當作「完成」，處理訂閱的 complete() callback。


throw Error after retry 3 times with failure
```typescript
const retryTimesThenThrowError = (every, times) => interval(every).pipe(
  tap(console.error("Retrying Process 3 times")),
  switchMap((value, index) => 
    iif(() => index === times, throwError(`throwing Error after retry ${times}`), of(value)))
  );

interval(1000)
  .pipe(
    switchMap(data => 
      iif(() => data % 2 === 0, of(data), throwError('發生錯誤'))),
    map(data => data + 1),
    retryWhen((error) => retryTimesThenThrowError(3000, 3))
  )
  .subscribe({
    next: data => {
      console.log(`next ${data}`);
    },
    error: error => {
      console.log(`Throwing Error ${error}`);
    }
});
// next : 1
// retry process 3 times
// next : 1
// next : 1
// next : 1
// Throwing Error throwing Error after retry 3
```

retry when event `click` happens
```typescript
const click$ = fromEvent(document, 'click');
interval(1000)
  .pipe(
    switchMap(data => 
      iif(() => data % 2 === 0, of(data), throwError('發生錯誤'))),
    map(data => data + 1),
    retryWhen((error) => click$)
  )
  .subscribe({
    next: data => {
      console.log(`retryWhen 示範 (3): ${data}`);
    },
    error: error => {
      console.log(`retryWhen 示範 (3): 錯誤 - ${error}`);
    },
    complete: () => {
      console.log('retryWhen 示範 (3): 結束');
    }
});
```

## finalize
finalize 會在整個來源 Observable 結束時(整個Process結束)，才進入處理，**為最後被呼叫**：
```  typescript 
interval(1000)
  .pipe(
    switchMap(data => 
      iif(() => data % 2 === 0, of(data), throwError('發生錯誤'))),
    // 當之前的 operator 發生錯誤時，資料流會中斷，但會進來 finalize
    finalize(() => {
      console.log('finalize 示範 (2): 在 pipe 內的 finalize 被呼叫了')
    }),
    // 當之前的 operator 發生錯誤時，這裏就不會呼叫了
    map(data => data + 1),
  )
  .subscribe({
    next: data => {
      console.log(`finalize 示範 (2): ${data}`);
    },
    error: error => {
      console.log(`finalize 示範 (2): 錯誤 - ${error}`);
    }
  });
// finalize 示範 (2): 1
// finalize 示範 (2): 錯誤 - 發生錯誤
// finalize 示範 (2): 在 pipe 內的 finalize 被呼叫了
```