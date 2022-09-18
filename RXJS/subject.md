
## Subjekt/matière (推送Observable)

### multicast

```
const source$ = interval(1000).pipe(
  take(5),
  multicast(() => new Subject())
);
// srouce$ 變成一個 multicast 的 Observable
// 使用 Subject 作為多播的來源
```

### Subject

為了達到資料共享給多個Components我們可以利用`Subject`來存放資料，當資料變更時，在呼叫`next()`方法，通知所有Subscribers

```typescript
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private _message = ['hello', 'world'];

  messages$ = new Subject();

  constructor() {
    // 推送 Observable 給 subscriber
    this.messages$.next(this._message);
  }

  // 推送新的 message
  addMessage(message) {
    this._message = [...this._message, message];
    this.messages$.next(this._message);
  }
}
```

單純使用`Subject`時，最大的問題是在訂閱時(`subscribe(..)`)若沒有發生任何的`next()`呼叫，會完全收不到過去的資料(直播概念), 如
```typescript
const subject = new Subject<string>();

// -----------Sub1 開始訂閱
//分別印出 1, 2 
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('1');

// --------------Sub2 開始訂閱
// 只會印出 2 (過去資料收不到)
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});

subject.next('2');
```


### `BehaviorSubject<TYPE>(initializedVal)`

我們可以利用`BehaviorSubject`以及`ReplaySubject`取得之前的`Observable`

`BehaviorSubject` 可以在資料被訂閱前，給予初始資料(**一定得給**)，這樣在任何`next()`發生前執行`subscribe()`都會得到初始資料
```typescript
// 建立 BehaviorSubject 時，給予初始資料值

const subject = new BehaviorSubject<string>('1'); 

// 分別印出 1, 2, 3
subject.subscribe(data => {
    console.log(`Sub1 => ${data}`);
});

subject.next('2');

// 分別印出 2, 3
// 由於已經next()取得新資料過了，因此初始資料 '1' 就無法再訂閱
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});
subject.next('3');
```


### `ReplaySubject<TYPE>(replayVal)`

`ReplaySubject`會記錄所有呼叫`next()`變更的資料，在被`subscribe()`時，重新播放所有紀錄(可設定紀錄最近的`N`筆)

```typescript
// 可以指定紀錄筆數
const subject = new ReplaySubject<string>(2);

// --- subscriber 1
subject.next('1');
// 分別印出 1, 2, 3, 4
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('2');
subject.next('3');

// --- subscriber 2
// 分別印出 2, 3, 4 (2, 3 是最近 2 次的重播)
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});
subject.next('4');
```


### AsyncSubject

`AsyncSubject`比較不常用
他只有在 `complete()` 方法被呼叫時，才能訂閱到「`最後一次 next()` 的資料」

```typescript
const subject = new AsyncSubject();

// 只會在 complete() 後印出 3
// 如果沒有 complete() 結束整個 observable，不會收到任何資料
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('1');
subject.next('2');
subject.next('3');

subject.complete()
```