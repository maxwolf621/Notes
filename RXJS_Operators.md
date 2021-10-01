# RXJS operation

[`startwith`](https://stackoverflow.com/questions/48465361/what-does-startwith-typescript-code-do)

https://stackblitz.com/edit/angular-jk5usw-qb828e?file=src/app/chips-autocomplete-example.ts   

[Learn RXJS](https://www.learnrxjs.io/learn-rxjs/operators/conditional/defaultifempty)     
[Array.prototype](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Array/slice)   


[TOC]

[NOTE TAKING FROM 黃升煌 Mike](https://ithelp.ithome.com.tw/users/20120614/ironman/2685?page=1)  
## SwitchMap

`switchMap()` 可以在收到`observable`時，轉換成另外一個`observable`，

`switchMap()`用於有順序必要的巢狀式`subscribe()`
```typescript
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

利用`switchMap()`簡化上述的Code Snippet
```typescript
this.route.params.pipe(
  switchMap(params => this.httpClient.get(`.../post/${params['id']}`))
)
```

如果有一系列的轉換，且資料都要保存起來呢？可以再額外透過`map()`最終組成一個大物件：
```typescript 
this.postData$ = this.route.params.pipe(
  switchMap(params => this.httpClient
    .get(`.../post/${params['id']}`).pipe(
      map(post => ({ id: params['id'], post: post }))
  )),
  switchMap(post => this.httpClient
    .get(`.../comments/${post.id}`).pipe(
      map(comments => Object.assign(post, { comments: comments }))
  ))
)
```
- 除了 switchMap 外，另外還有常見的 concatMap、mergeMap 和 exhaustMap，都是用來把 observable 資料轉換成另外一個 observable

## combineLatest (e.g. 搜尋器)

當取得的`Observable`有順序時,利用`switchMap`，而當沒有順序時，希望平行的處理這些無序的`Observable`，並將所有`Observable`有資料後才進行後續處理，這時候就可以使用`combineLatest`來同時取得資料，不會有順序問題！
```typescript
const posts$ = this.httpClient.get('.../posts');
const tags$ = this.httpClient.get('.../tags');

this.data$ = combineLatest(posts$, tags$).pipe(
  map(([posts, tags]) => ({posts: posts, tags: tags}))
)
```

我們也可以整合畫面上各種事件最終得到結果，例如一個包含搜尋、排序和分頁的資料，我們可以將搜尋、排序和分頁都設計成單一個 observable，在使用`combineLatest`產生搜尋結果
```typescript
this.products$ = combineLatest(
  this.filterChange$, // observable 1 
  this.sortChange$,  // observable  2
  this.pageChange$  // observable 3
)
.pipe(
  exhaustMap(([keyword, sort, page]) =>
    this.httpClient
      .get(`.../products/?keyword=${keyword}&sort=${sort}&page=${page}`)
  )
);
```

## startWith (initialize observable)
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

`forkJoin` 與 `combineLatest` 類似，差別在於 `combineLatest` 在 RxJS 整個資料流有資料變更時都會發生(平行處理)，而 `forkJoin` 會在所有 `observable` 都完成(complete)後，才會取得最終的結果，所以對於 Http Request 的整合，我們可以直接使用 `forkJoin` 因為 Http Request 只會發生一次，然後就完成了！

```
const posts$ = this.httpClient.get('.../posts');
const tags$ = this.httpClient.get('.../tags');

this.data$ = forkJoin(posts$, tags$).pipe(
  map(([posts, tags]) => ({posts: posts, tags: tags}))
)
```


[NOTE TAKING FROM 黃升煌 Mike](https://ithelp.ithome.com.tw/articles/10209906)  
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

為了達到資料共享給多個Components我們可以利用`Subject`來存放資料，當資料變更時，在呼叫`next()`方法，通知所有Subscriber, for example
```typescript
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private _message = ['hello', 'world'];

  messages$ = new Subject();

  constructor() {
    // 推送Observable to subscriber
    this.messages$.next(this._message);
  }

  // 推送新的message
  addMessage(message) {
    this._message = [...this._message, message];
    this.messages$.next(this._message);
  }
}
```

單純使用`Subject`時，最大的問題是在訂閱時(`subscribe(..)`)若沒有發生任何的`next()`呼叫，會完全收不到過去的資料, for example
```typescript
const subject = new Subject<string>();

// 分別印出 1, 2 (start listening) -----------Sub1 開始監聽
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('1');

// 只會印出 2 (start listening) --------------Sub2 開始監聽
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});

subject.next('2');

```

我們可以利用`BehaviorSubject`以及`ReplaySubject`取得之前的`Observable`

`BehaviorSubject` 可以在資料被訂閱前，給予初始資料(**一定得給**)，這樣在任何`next()`發生前執行`subscribe()`都會得到初始資料
```typescript
// 建立 BehaviorSubject 時，給予初始資料
const subject = new BehaviorSubject<string>('1'); 

// 分別印出 1, 2, 3
subject.subscribe(data => {
    console.log(`Sub1 => ${data}`);
});


subject.next('2');

// 分別印出 2, 3
// 由於已經有 next() 取得新資料過了，因此初始資料就無法再訂閱得到
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});
subject.next('3');
```


`ReplaySubject`會記錄所有呼叫`next()`變更的資料，在被`subscribe()`時，重新播放所有紀錄(可設定紀錄最近的`N`筆)

```typescript
// 可以指定紀錄筆數
const subject = new ReplaySubject<string>(2);
subject.next('1');
// 分別印出 1, 2, 3, 4
subject.subscribe(data => {
  console.log(`Sub1 => ${data}`);
});

subject.next('2');
subject.next('3');

// 分別印出 2, 3, 4 (2, 3 是最近 2 次的重播)
subject.subscribe(data => {
  console.log(`Sub2 => ${data}`);
});
subject.next('4');
善用這兩種 Subject 類型，就不怕在訂閱時沒有初始資料啦！

AsyncSubject
另外還有一個比較不常用的 Subject 類型叫做 AsyncSubject 他只有在 complete() 方法被呼叫時，才能訂閱到「最後一次 next() 的資料」

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
錯誤處理
我們知道當 observable 錯誤時，可以從 subscribe() 時設定處理錯誤的訊息：

subject.subscribe(
  data => {
    console.log(`Sub2 => ${data}`);
  },
  error => {
    console.log('error', error)
  });
```

不過這有兩個小缺點：
在整個 `pipe` 中只要任一個`operator`內發生錯誤，整個 `observable` 都會錯誤並結束
- 若希望不要中斷整個 `observable，又能記錄錯誤，改怎麼做呢？在` RxJS 內有提供一些類似 try ... catch 的 `operator`，可以使用 `catchError` 來攔截錯誤

## catchError

```typescript
this.httpClient.get(`.../posts`).pipe(
  catchError(error => {
    // 紀錄error 返回一個空array預防程式被中斷
    console.log(error);
    return of([]);
  })
)
```
`catchError`可以攔截發生的錯誤，並回傳另外一個`observable`，讓整個`observable`可以順利繼續運作，透過這種方式可以避免`observable`運作中斷，也能記錄到錯誤訊息！

## throwError

如果希望錯誤時就整個中斷，或是主動拋出錯誤呢？
可以使用`throwError`
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


## finalize

在`try ... catch`的程式中，通常會提供一個`finally {}`來放置最後一定要執行的程式，在 RxJS 內有沒有呢？有的，就是 finalize 這個 operator：
```typescript
this.isLoading = true; // 進入讀取中狀態
this.httpClient.get(`.../posts`).pipe(

  finalize(() => {
    // 不管中間程式遇到任何錯誤，一定會進入 finalize 裡面
	this.isLoading = false;        
  })
)
```