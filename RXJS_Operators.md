# RXJS operation

- [RXJS operation](#rxjs-operation)
  - [Reference](#reference)
  - [pipe](#pipe)
  - [Creator Operator](#creator-operator)
    - [of](#of)
    - [iff](#iff)
    - [throwError](#throwerror)
    - [from](#from)
    - [range](#range)
    - [Promise](#promise)
    - [fromEvent](#fromevent)
    - [fromEventPattern](#fromeventpattern)
  - [interval](#interval)
  - [setTimeout](#settimeout)
  - [timer](#timer)
  - [defer](#defer)
  - [fusionner/verschmelzen](#fusionnerverschmelzen)
    - [concat](#concat)
    - [merge](#merge)
    - [zip](#zip)
  - [map](#map)
  - [SwitchMap](#switchmap)
  - [combineLatest (e.g. 搜尋器)](#combinelatest-eg-搜尋器)
  - [startWith (initialize observable)](#startwith-initialize-observable)
  - [forkJoin](#forkjoin)
  - [filter](#filter)
  - [Subjekt/matière](#subjektmatière)
    - [Subject](#subject)
    - [`BehaviorSubject<TYPE>(initializedVal)`](#behaviorsubjecttypeinitializedval)
    - [`ReplaySubject<TYPE>(replayVal)`](#replaysubjecttypereplayval)
    - [AsyncSubject](#asyncsubject)
  - [Error Handling](#error-handling)
    - [catchError && throwError](#catcherror--throwerror)
    - [finalize](#finalize)
## Reference

[RXJS examples](https://stackblitz.com/edit/angular-jk5usw-qb828e?file=src/app/chips-autocomplete-example.ts)   
[angular university rxjs error handling](https://blog.angular-university.io/rxjs-error-handling/)   
[RxJS 轉換類型 Operators (1) - map / scan / pairwise](https://ithelp.ithome.com.tw/articles/10248366)
[讓 TypeScript 成為你全端開發的 ACE！ 系列](https://ithelp.ithome.com.tw/users/20120614/ironman/2685?page=1)  
[希望是最淺顯易懂的 RxJS 教學](https://blog.techbridge.cc/2017/12/08/rxjs/)  
**[learnrxjs.io](https://www.learnrxjs.io/)**         
**[rxjs.dev](https://rxjs.dev/api/operators/)**     
[RxJS 轉換類型 Operators (2) - switchMap / concatMap / mergeMap / exhaustMap](https://ithelp.ithome.com.tw/articles/10248745)   
[[Angular 大師之路] Day 30 - 在 Angular 中應用 RxJS 的 operators (2) - 進階應用](https://ithelp.ithome.com.tw/articles/10209906)    
[ReplaySubject](https://blog.angulartraining.com/how-to-cache-the-result-of-an-http-request-with-angular-f9aebd33ab3)


```typescript
stream.pipe(
  op1(data =>{ ... }),
  op2(data =>{.... }),
  op3(data =>{.... }),
).subscribe( obs => {
   //...
}
)
```

## pipe 
**Use the `pipe()` function to make new operators**    
```typescript
// Without pipe()
// when operations convolved together and it becomes unreadable
op4()(op3()(op2()(op1()(obs))))

// pipe() makes operations easier to read
obs.pipe(op1(), 
         op2(), 
         op3(), 
         op4());
```

## Creator Operator
- `EMPTY` ：Create A EMPTY subject/observable   
- `of` ：Create A Subject 
- `range` ：用一定範圍內的數值資料作為事件的資料。
- `iif`：依照第一個參數的條件，決定要使用不同的 Observable 資料流。
- `throwError` ：讓 Observable 發生錯誤。
- `from` ：使用陣列、Promise、Observable 等來源建立新的 Observable。
- `fromEvent` ：封裝 DOM 的 addEventListener 事件處理來建立 Observable。
- `fromEvenPattern` ：可依照自行定義的事件來建立 Observable。
- `interval` ：每隔指定的時間發出一次事件值。
- `timer` ：與 interval 相同，但可以設定起始的等待時間。
- `defer` ：用來延遲執行內部的 Observable。
- `concat`：用來「串接」數個 Observables，會依序執行每個 Observable，上一個 Observable 「完成」後才會執行下一個 Observable。
- `merge`：用來「同時執行」數個 Observables，所有 Observables 會同時執行，並只在一條新的 Observable 上發生事件。
- `zip`：一樣「同時執行」數個 Observables，差別是會將每個 Observable 的資料「組合」成一個新的事件值，在新的 Observable 上發生新事件。
- `partition` ：依照指定邏輯，將一條 Observable 拆成兩條 Observables。

### of
```typescript
// `.next()` is called automatically for each data
of(1, 2, 3, 4).subscribe(data => console.log(data));
// 1
// 2
// 3
// 4
```

### iff

Depending the `data` to create `Subject/Observable`
```typescript
// (condition, true expression, false expression)
const emitHelloIfEven = (data) => {
  return iif(() => data % 2 === 0, of('Hello'), EMPTY);
};

emitOneIfEven(1).subscribe(data => console.log(data)); 
emitOneIfEven(2).subscribe(data => console.log(data)); // Hello
```

### throwError
```typescript
const source$ = throwError('發生錯誤了');
source$.subscribe({
  next: (data) => console.log(`throwError 範例 (next): ${data}`),
  error: (error) => console.log(`throwError 範例 (error): ${error}`),
  complete: () => console.log('throwError 範例 (complete)'),
});
// throwError 範例 (error): 發生錯誤了
```

### from
similar with `of`

```typescript
import { from } from 'rxjs';

from([1, 2, 3, 4]).subscribe(data => {
  // emit each element from array
  console.log(data);
});
// 1
// 2
// 3
// 4
```

from 也可以把一個 Observable 當作參數，此時 from 會幫我們訂閱裡面的資料，重新組成新的 Observable：
```typescript
from(of(1, 2, 3, 4)).subscribe(data => {
  console.log(data);
});
// 1
// 2
// 3
// 4
```

### range 

```typescript
// 使用 generator 建立 iterable
function range(start, end) {
  for(let i = start; i <= end; ++i){
    yield i;
  }
}

from(range(1, 4)).subscribe(data => {
  console.log(`from 示範 (2): ${data}`);
});
// from 示範 (2): 1
// from 示範 (2): 2
// from 示範 (2): 3
// from 示範 (2): 4
```

### Promise

Promise 是前端處理非同步最常見的手段，搭配 `from` 將一個 Promise 物件建立為新的 Observable:
```typescript
// 傳入 Promise 當參數
from(Promise.resolve(1)).subscribe(data => {
  console.log(`from 示範 (3): ${data}`);
});
// from 示範 (3): 1
```

### fromEvent

`fromEvent` 能將事件包裝成 `Observable`
```typescript
/**
 * target：實際上要監聽事件的 DOM 元素
 * eventName：事件名稱
 */
fromEvent(document, 'click').subscribe(data => {
  console.log('滑鼠事件觸發了');
});
```

### fromEventPattern
fromEventPattern 可以根據自訂的邏輯解決複雜的監聽事件及退訂邏輯

有傳入兩個參數：
- `addHandler`   ：當 subscribe 時，呼叫此方法決定如何處理事件邏輯
- `removeHandler`：當 unsubscribe 時，呼叫次方法將原來的事件邏輯取消

`addHandler` 和 `removeHandler` 都是一個 function，串入一個 `handler`(callback function) 物件，這個物件其實就是一個被用來呼叫的方法
```typescript
// 自訂subscribe以及unsubscribe function
const addClickHandler = (handler) => {
  console.log('fromEventPattern 示範: 自定義註冊滑鼠事件')
  document.addEventListener('click', event => handler(event));
}

const removeClickHandler = (handler) => {
  console.log('fromEventPattern 示範: 自定義取消滑鼠事件')
  document.removeEventListener('click', handler);
};

// 使用 fromEventPattern 傳入這兩個 function，來完成一個 Observable；
// 當訂閱 (subscribe) 產生時，會產生 handler 並呼叫 addClickHandler，
// 這裡面的程式則是註冊 document 的 click，
// 並在事件發生時呼叫 handler callback function。
const source$ = fromEventPattern(
  addClickHandler,
  removeClickHandler
);
  
const subscription = source$
  .subscribe(event => console.log('fromEventPattern 示範: 滑鼠事件發生了', event));

// 接著三秒後呼叫 unsubscribe 來取消訂閱，此時就會呼叫 removeClickHandler 處理取消事件監聽
setTimeout(() => {
  subscription.unsubscribe();
}, 3000);
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

## timer
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
// ....
// ....
```

interval的缺點，就是一開始一定會先等待一個指定時間，才會發生第一個事件，但有時候我們會希望一開始就發生事件，這個問題可以透過 timer 解決，只要等待時間設為`0`
```typescript 
timer(0, 1000).subscribe(
      data => console.log(`timer 示範: ${data}`)
    );
```

timer 如果沒有設定第二個參數，代表在指定的時間發生第一次事件後，就不會再發生任何事件了。
```typescript
|~~~~~~~~~3~~~~~~~~~~~|
|--------------------0|

timer(3000).subscribe(
      data => console.log(data)
    );
//  0
```

## defer
defer 會將建立 Observable 的邏輯包裝起來，提供更一致的使用感覺，**使用 defer 時需要傳入一個 factory function 當作參數**，這個 function 裡面需要回傳一個 Observable (或 Promise )，當 defer 建立的 Observable 被訂閱時，會呼叫這個 factory function，並以裡面回傳的 Observer 當作Stream
```typescript
const factory = () => of(1, 2);
const source$ = defer(factory);

source$.subscribe(data => console.log(`defer 示範: ${data}`));
```
- `source$` 每次被訂閱時才會去呼叫裡面 factory function，這麼做的好處是建立 Observable 的邏輯被包裝起來了，同時也可以達成延遲執行的目標。

使用`defe`核心目標是「延遲執行」，如果今天產生 Observable 的邏輯希望在「訂閱」時才去執行的話，就很適合使用 defer，最常見的例子應該非 Promise   

Promise 雖然是非同步執行程式，但在 Promise 產生的一瞬間相關程式就已經在運作了：
```typescript
const p = new Promise((resolve) => {
  console.log('Promise 內被執行了');
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

##  fusionner/verschmelzen 

### concat

```typescript
sourceA$: 1------2------|
sourceB$: 3------4------|
sourceC$: 5------6------|
concat(sourceA$, sourceB$, sourceC$)
  .subscribe(data => {
    console.log(data);
}); 

1------2----'-3------4-----'-5------6------|
            ^ End of       ^ End of 
              sourceA$       sourceB$

```


### merge

```typescript
sourceA$: --A1--A2--A3--A4--A5--A6--....
sourceB$: ----------B1----------B2--...
sourceC$: ------------------C1------....
merge(sourceA$, sourceB$, sourceC$)
--A1--A2--(A3,B1)--A4--(A5,C1)--(A6,B2)----
```

### zip

```typescript 
sourceA$: --A1--A2--A3--A4--............
sourceB$:   ----B1  ----B2  ----B3--....
sourceC$:     ------C1    ------C2    ------C3......

zip(sourceA$, sourceB$, sourceC$).subscribe(data => {
  console.log(data)
}); 

              ------**    ------**    ------**.......
                [A1,B1,C1]  [A2,B2,C2]  [A3,B3,C3]
```
- `A4` is there are no corresponding observable in `sourceB$` and `sourceC$`


## map 


- Observable 的 map 是每次有事件發生時進行轉換。
- Array 的 map 會立刻把整個了陣列的資料勁行轉換。


```typescript
of(1, 2, 3, 4).pipe(
  map((value, index) => `第 ${index} 次事件資料為 ${value}`)
).subscribe(message => console.log(`map 示範 (2): ${message}`));
// map 示範 (2): 第 0 次事件資料為 1
// map 示範 (2): 第 1 次事件資料為 2
// map 示範 (2): 第 2 次事件資料為 3
// map 示範 (2): 第 3 次事件資料為 4
```

```typescript
const studentScore = [
  { name: '小明', score: 100 },
  { name: '小王', score: 49 },
  { name: '小李', score: 30 }
];

of(...studentScore).pipe(
  // 專注處理開根號邏輯
  map(student => ({...student, newScore: Math.sqrt(student.score)})),
  // 專注處理乘以十邏輯
  map(student => ({...student, newScore: student.newScore * 10})),
  // 專注處理取整數
  map(student => ({...student, newScore: Math.ceil(student.newScore)})),
  // 專注處理判斷是否及格
  map(student => ({...student, pass: student.newScore >= 60}))
).subscribe(student => {
  // 轉著處理如何顯示
  console.log(
    `map 示範 (3): ${student.name} 成績為 ${student.newScore} (${student.pass ? '及格': '不及格'})`);
});
```


## SwitchMap

1. `switchMap()`可以在收到`observable`時，轉換成另外一個`observable`，   
2. `switchMap()`用於有**順序必要的巢狀式**`subscribe()`

Returns an Observable that emits items based on applying a function that you supply to each item emitted by the source Observable, where that function returns an (so-called "inner") Observable. 
```typescript 
import { of, switchMap } from 'rxjs';
                // Source Observable
const switched = of(1, 2, 3).pipe(
              // Inner Observable
    switchMap(x => of(x, x ** 2, x ** 3))
  );

// output observable
switched.subscribe(x => console.log(x));

// outputs
// 1
// 1
// 1
// 2
// 4
// 8
// 3
// 9
// 27
```
Each time it observes one of these inner Observables, the output Observable begins emitting the items emitted by that inner Observable. 

**When a new inner Observable is emitted, `switchMap` stops emitting items from the earlier-emitted inner Observable and begins emitting items from the new one.** It continues to behave like this for subsequent inner Observables.
- For the case to get the leastest data we can use `switchMap`

利用`switchMap`簡化前後端巢狀式資料交換
```typescript
// 再沒有switchMap的情況下的巢狀式subscribe
constructor(private route: ActivatedRoute, private httpClient: HttpClient) { }

ngOnInit() {
  this.route.params.subscribe
  (
    params => {
        this.httpClient.get(`.../post/${params['id']}`).subscribe
        (
            post => {
                this.post = post;
            }
        )
  });
}
```
可以利用`switchMap()`來簡化上述的Code Snippet
```typescript
this.route.params.pipe(
  switchMap(params => this.httpClient.get(`.../post/${params['id']}`))
)
```

如果有一系列的轉換，且資料都要保存起來再額外透過`map()`最終組成一個大物件
```typescript 
this.postData$ = this.route.params.pipe(
  switchMap( params => this.httpClient
    .get(`.../post/${params['id']}`).pipe(
      map(post => ({ id: params['id'], post: post }))
  )),
  switchMap(post => this.httpClient
    .get(`.../comments/${post.id}`).pipe(
      map(comments => Object.assign(post, { comments: comments }))
  ))
)
```
- 除了`switchMap`外，另外還有常見的`concatMap`、`mergeMap` 和 `exhaustMap`，都是用來把 observable 資料轉換成另外一種新的 observable

`concatMap` 會等前面的 Observable 結束後，才會「接續」(concat)新產生的 Observable 資料流。

## combineLatest (e.g. 搜尋器)

當取得的`Observable`有順序時,利用`switchMap`，而當沒有順序時，希望平行的處理這些無序的`Observable`，且當所有Observables有資料後才進行後續處理，這時候就可以使用`combineLatest`來同時取得資料。

```typescript
sourceA$: --A1--A2--A3        --A4        --A5......           
sourceB$:   ----B1            --B2        ....
sourceC$:     ------C1                          

zip(sourceA$, sourceB$, sourceC$)
              ------**        --**        --**.......
                [A3,B1,C1]  [A4,B1,C1]  
                            [A4,B2,C1] (兩個來源 Observable 同時發生事件)
```

假設有兩個Observables(`posts$`跟`tag$`)利用`combineLatest`各別接收
```typescript
const posts$ = this.httpClient.get('.../posts');
const tags$ = this.httpClient.get('.../tags');

this.data$ = combineLatest(posts$, tags$).pipe(
  map(([posts, tags]) => ({posts: posts, tags: tags}))
)
```

利用`combineLatest`各種事件最終得到結果，例如一個包含搜尋、排序和分頁的資料，我們可以將搜尋、排序和分頁都設計成單一個 observable，在使用`combineLatest`產生搜尋結果
```typescript
this.products$ = combineLatest(
  this.filterChange$, // observable 1 
  this.sortChange$,   // observable 2
  this.pageChange$    // observable 3
).pipe(
  exhaustMap(([keyword, sort, page]) => this.httpClient
    	.get(`.../products/?keyword=${keyword}&sort=${sort}&page=${page}`)
  )
);
```

## startWith (initialize observable)

- [`startwith`](https://stackoverflow.com/questions/48465361/what-does-startwith-typescript-code-do)    

在使用 `combineLatest` 時，會在 `combineLatest` 內每個 `observable` 都有資料時才會最終取得新的結果，若是以剛才討論的搜尋程式，希望在程式一開始就給空的資料來產生搜尋結果時，就可以使用 `startWith` 來確保 `observable` 可以有起始的資料：
```typescript
this.products$ = combineLatest(
  this.filterChange$.pipe(startWith('')),
  this.sortChange$.pipe(startWith({})),
  this.pageChange$.pipe(startWith({}))
)
.pipe(
  exhaustMap(([keyword, sort, page]) =>
    this.httpClient
      .post(`.../products`, { keyword: keyword, sort: sort, page: page}))
);
```

## forkJoin

`forkJoin` 與 `combineLatest` 類似，差別在於 `combineLatest` 在 RxJS 整個資料流有資料變更時都會發生(平行處理)，而 `forkJoin` 會在所有 `observable` 都完成後，才會取得最終的結果，所以對於 Http Request 的整合，我們可以直接使用 `forkJoin` 因為 Http Request 只會發生一次

- forkJoin is an operator that takes any number of input observables which can be passed either as an array or a dictionary of input observables. If no input observables are provided (e.g. an empty array is passed), then the resulting stream will complete immediately.
- **forkJoin will wait for all passed observables to emit and complete and then it will emit an array or an object with last values from corresponding observables.**

If you pass an array of n observables to the operator, then the resulting array will have n values, where the first value is the last one emitted by the first observable, second value is the last one emitted by the second observable and so on.

If you pass a dictionary of observables to the operator, then the resulting objects will have the same keys as the dictionary passed, with their last values they have emitted located at the corresponding key.

If any given observable errors at some point, forkJoin will error as well and immediately unsubscribe from the other observables.


```typescript
sourceA$: --A1--A2--A3--A4--A5|
sourceB$: ----B1  ----B2  ----B3|
sourceC$:     ------C1    ------C2    ------C3|

forkJoin(sourceA$, sourceB$, sourceC)
              ------      ------      ------**|
                                        [A5,B3,C3]

/**
 *  --1--2--3--4|
 *  --------8|
 *  ----------5|
 */ 

// observable with dictionary
const observable = forkJoin({
  foo: of(1, 2, 3, 4),  // 4 
  bar: Promise.resolve(8), // 8
  baz: timer(4000) // 4
});
observable.subscribe({
 next: value => console.log(value),
 complete: () => console.log('This is how it ends!'),
});

// Logs:
// { foo: 4, bar: 8, baz: 0 } after 4 seconds
// 'This is how it ends!' immediately after

const observable = forkJoin([
  of(1, 2, 3, 4),
  Promise.resolve(8),
  timer(4000)
]);
observable.subscribe({
 next: value => console.log(value),
 complete: () => console.log('This is how it ends!'),
});
 
// Logs:
// [4, 8, 0] after 4 seconds
// 'This is how it ends!' immediately after
```

```typescript
const posts$ = this.httpClient.get('.../posts');
const tags$ = this.httpClient.get('.../tags');

this.data$ = forkJoin(posts$, tags$).pipe(
  map(([posts, tags]) => ({posts: posts, tags: tags}))
)
```

## filter


Rxjs的filter與array的filter非常類似
```typescript
data$ = this.searchControl.valueChanges.pipe(
  debounceTime(300), // 當 300 毫秒沒有新資料時，才進行搜尋
  distinctUntilChanged(), // 當「內容真正有變更」時，才進行搜尋
  filter(keyword => keyword.length >= 3), // 當關鍵字大於 3 個字時，才搜尋
  switchMap(keyword => this.httpClient.get(`localhost:4200/search/?q=${keyword}`))
);
```

## Subjekt/matière

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

單純使用`Subject`時，最大的問題是在訂閱時(`subscribe(..)`)若沒有發生任何的`next()`呼叫，會完全收不到過去的資料, For Example
```typescript
const subject = new Subject<string>();

// -----------Sub1 開始監聽
//分別印出 1, 2 
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('1');

// --------------Sub2 開始監聽
// 只會印出 2 
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});

subject.next('2');


// -----------Sub1 and Sub2 停止監聽
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

## Error Handling

我們知道當 observable 錯誤時，可以從`subscribe()`時設定處理錯誤的訊息
```typescript
subject.subscribe(
  data => {
    console.log(`Sub2 => ${data}`);
  },
  error => {
    console.log('error', error)
  });
```
- 缺點是在整個 `pipe` 中只要任一個`operator`內發生錯誤，整個 `observable` 都會錯誤並結束
  - 若希望不要中斷整個 `observable`可以使用RxJS 內有提供`catchError`來攔截錯誤


### catchError && throwError


`catchError`可以攔截發生的錯誤，並回傳另外一個`observable`，讓整個`observable`可以順利繼續運作，透過這種方式可以避免`observable`運作中斷，也能記錄到錯誤訊息

```typescript
this.httpClient.get(`.../posts`).pipe(
  catchError(error => {
    // 紀錄error 返回一個空array預防程式被中斷
    console.log(error);
    return of([]);
  })
)
```
使用`throwError`錯誤時就整個中斷，或主動拋出錯誤   
常常搭配`if` condition
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


### finalize

在`try ... catch`的程式中，通常會提供一個`finally {}`來放置最後一定要執行的程式，在RsJX中則使用`finalize`

```typescript
this.isLoading = true; // 進入讀取中狀態
this.httpClient.get(`.../posts`).pipe(

  finalize(() => {
    // 不管中間程式遇到任何錯誤，一定會進入 finalize 裡面
	  this.isLoading = false;        
  })
)
```