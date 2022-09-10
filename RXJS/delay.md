# time

- [time](#time)
    - [delay(x/1000s) && delayWhen(condition, ?event)](#delayx1000s--delaywhencondition-event)
  - [interval](#interval)
  - [setTimeout](#settimeout)
  - [timer(delay_X_timeToStart, ?interval)](#timerdelay_x_timetostart-interval)
  - [defer(observableFactory)](#deferobservablefactory)

### delay(x/1000s) && delayWhen(condition, ?event)

```typescript
const delayFn = (value) => {
  return of(value).pipe(delay(value % 2 * 2000));
}

interval(1000).pipe(
  take(3),
  delayWhen(value => delayFn(value))
).subscribe(data => {
  console.log(data);
});
// 0
// (原本應該發生事件 1，但被延遲了)
// 2
// 1

----0----1----2|
delayWhen(value => of(value).pipe(delay(value % 2 * 2000)))
----0---------2----1|
         ^ 1 is delayed
```



delayWhen 還有第二個參數(非必須)，是一個 subscriptionDelay Observable，delayWhen 可以透過這個 event 來決定來源 Observable 開始的時機點；   
當整個 Observable 訂閱開始時，delayWhen 會訂閱這個 subscriptionDelay Observable ，當事件發生時，才真正訂閱來源 Observable，然後退訂閱 subscriptionDelay Observable。
```typescript
interval(1000).pipe(
  take(3),
  delayWhen(
    value => delayFn(value),
    fromEvent(document, 'click')
  )
).subscribe(data => {
  console.log(`delayWhen 示範 (2): ${data}`);
});
// ...(當按下滑鼠時，才開始)
// delayWhen 示範 (1): 0
// (原本應該發生事件 1，但被延遲了)
// delayWhen 示範 (1): 2
// delayWhen 示範 (1): 1
click$   ------c...
source$  ----0----1----2|
delayWhen(
  value => delayFn(value),
  click$
)
         ----------0---------2----1|
               ^ click$ 事件發生
                   ^ 依照 delayFn 的邏輯決定資料延遲時間
```



## interval

interval 會依照的參數設定的時間(毫秒)來建議 Observable，當被訂閱時，就會每隔一段指定的時間發生一次資料流，資料流的值就是為事件是第幾次發生的 (從0開始)

建立一個每一秒發生一次的資料流
```typescript
|_1_|__1_|__1_|__1_|___........
----0----1----2----3----.......
interval(1000).subscribe(
          data => console.log(data)
        );
```


## setTimeout

在一段時間後取消訂閱
```typescript
const subscription = interval(1000).subscribe(
                      data => console.log(`interval 示範: ${data}`)
                    );

setTimeout( () => {
   ----0----1----2----3----4---|
   subscription.unsubscribe(); }, 
   5500
);
```

## timer(delay_X_timeToStart, ?interval)

- [code](https://stackblitz.com/edit/sqdvzz?devtoolsheight=50&file=index.ts)

`timer` 跟 `interval` 有點類似，但**它多一個參數**， 用來設定經過多久時間後開始依照指定的間隔時間計時

設定Observable在3秒後開始以每1秒一個新事件的頻率計時
```typescript
time   |~~~~~~~~~~3~~~~~~~~~|~~1~~|-~1~~|~~1~~|.....
stream |--------------------0-----1-----2-----......
 
timer(3000, 1000).subscribe(
      data => console.log(data)
    );
// 0
// 1
// 2
// 3
// 4
// 5


const source = of(1, 2, 3);
timer(3000,1000)
  .pipe(concatMap(() => source))
  .subscribe(console.log);

// 1
// 2
// 3

// 1
// 2
// 3
```

interval的缺點，就是一開始一定會先等待一個指定時間，才會發生第一個事件，但有時候我們會希望一開始就發生事件，這個問題可以透過 timer 解決，只要等待時間設為`0`
```typescript 
timer(0, 1000).subscribe(
      data => console.log(`timer 示範: ${data}`)
    );
```

timer如果沒有設定第二個參數，代表在指定的時間發生第一次事件後，就不會再發生任何事件了。
```typescript
|~~~~~~~~~3~~~~~~~~~~~|
|--------------------0|

timer(3000).subscribe(
      data => console.log(data)
    );
//  0
```

## defer(observableFactory)
defer 會將建立 Observable 的邏輯包裝起來，提供更一致的使用感覺，**使用 defer 時需要傳入一個 factory function 當作參數**，這個 function 裡面需要回傳一個 Observable，當 defer 建立的 Observable 被訂閱時，會呼叫這個 factory function，並以裡面回傳的 Observer 當作Stream
```typescript
const factory = () => of(1, 2);
const source$ = defer(factory); // or const source$ = defer(() => of(1, 2););

source$.subscribe(data => console.log(`defer : ${data}`));
```
- `source$` 每次被訂閱時才會去呼叫裡面 factory function，這麼做的好處是建立 Observable 的邏輯被包裝起來了，同時也可以達成延遲執行(lazily)的目標。

Promise 雖然是非同步執行程式，但在 Promise 產生的一瞬間相關程式就已經在運作了
```typescript
const p = new Promise((resolve) => {
  console.log('Promise is executing');
  setTimeout(() => {
    resolve(100);
  }, 1000);
});
// Promise 內被執行了 (就算還沒呼叫 .then，程式依然會被執行)

p.then(result => {
  console.log(`Promise 處理結果: ${result}`);
});
```

就算用 from 包起來變成 Observable，已經執行的程式依然已經被執行了，呼叫 `.then()` 只是再把 `resolve()` 的結果拿出來而已；在設計 Observable 時如果可以延遲執行，直到被訂閱時才真的去執行相關邏輯，通常會比較好釐清整個流程
```typescript
// 將 Promise 包成起來
// 因此在此 function 被呼叫前，都不會執行 Promise 內的程式
const promiseFactory = () => new Promise((resolve) => {
  console.log('Promise 內被執行了');
  setTimeout(() => {
    resolve(100);
  }, 1000);
});
const deferSource$ = defer(promiseFactory);
// 此時 Promise 內程式依然不會被呼叫
console.log('示範用 defer 解決 Promise 的問題:');
// 直到被訂閱了，才會呼叫裡面的 Promise 內的程式
deferSource$.subscribe(result => {
  console.log(`Promise 結果: ${result}`)
});
```