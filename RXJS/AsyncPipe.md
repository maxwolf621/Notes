# AsyncPipe 

- [AsyncPipe](#asyncpipe)
  - [Reference](#reference)
  - [AsyncPipe for Observable and Subscription](#asyncpipe-for-observable-and-subscription)
  - [類延遲載入(Lazy evaluation)](#類延遲載入lazy-evaluation)
  - [`shareReplay`](#sharereplay)
  - [搭配 `*ngIf` 使用](#搭配-ngif-使用)
    - [`as`](#as)
  - [使用 AsyncPipe 的自動退訂機制](#使用-asyncpipe-的自動退訂機制)
  - [AsyncPipe `ChangeDetectionStrategy.OnPush`](#asyncpipe-changedetectionstrategyonpush)
## Reference

[[Angular 大師之路] Day 27 - 認識 AsyncPipe (1) - 基本使用技巧](https://ithelp.ithome.com.tw/articles/10209372)   
[[Angular 大師之路] Day 28 - 認識 AsyncPipe (2) - 進階技巧](https://ithelp.ithome.com.tw/articles/10209602)   
## AsyncPipe for Observable and Subscription

我們通常使用`HttpClient`去呼叫一個API(`http.get(URL)`)取得Observables,並透過`.subscribe`，取得oberservable，例如
```typescript
@Component({
  selector: 'my-app',
  template: `
  <ul>
    <li *ngFor="let todo of todos$">{{ todo.title }}</li>
  </ul>
  `
})
export class AppComponent {
  todos$: any[];
  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    // create obsevables
    this.httpClient.get('https://jsonplaceholder.typicode.com/todos/').subscribe(
          (data: any[]) => {this.todos$ = data;});
    }}
```

由於Http Request以及Observable為非同步機制，因此我們可以先把這個非同步的物件保留起來，而不是立刻呼叫 `.subscribe`並透過Template(`html`)進行訂閱結果
```typescript
import { Observable } from 'rxjs';

export class AppComponent {
  todos$: Observable<any[]>;
  constructor(private httpClient: HttpClient) { }

  // keep the observable 
  ngOnInit() {
    this.todos$ = this.httpClient.get<any[]>('https://jsonplaceholder.typicode.com/todos/');
  }
}
```
- 上面Code snippets宣告一個型別為 `Observable<any[]>` 的變數 `todos$`，並直接把 `HttpClient`取得的物件指派給這個`todos$`，不做`.subscribe`行為
- 在開發習慣中，我們會在變數後面加上一個 `$` 符號，代表他是一個 Observable

```html
<li *ngFor="let todo of todos$ | async">{{ todo.title }}</li>
```
- `AsyncPipe`也可以幫助我們自動處理`Promise`，不過在Angular中還是使用 RxJS 居多。

## 類延遲載入(Lazy evaluation)

**Lazy evaluation : 資料被需要時再載入資料**

由於我們現在把訂閱的工作交給Template上的程式(`let todo of todos$ | async`)了，因此資料不會在元件(AppComponent)被使用時的時候就載入，而是在樣板中有需要顯示的時候才載入，因此我們可以做出一個類似延遲載入的效果：   
```typescript
@Component({
  selector: 'my-app',
  template: `
  <button (click)="loadTodos()">Load Todos</button>
  <ul *ngIf="load">
    <li *ngFor="let todo of todos$ | async">{{ todo.title }}</li>
  </ul>
  `
})
export class AppComponent {
  load: boolean;
  
  todos$: Observable<any[]>;

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.todos$ = this.httpClient.get<any[]>('https://jsonplaceholder.typicode.com/todos/');
  }

  // Lazy evaluation
  loadTodos() {
    this.load = !this.load;
  }
}
```
- 當`this.load` 設為`true`時，才會進行載入的動作，透過這種方式，我們就可以在真正需要資料時才進行載入的動作，避免不必要的 API 呼叫

## `shareReplay`

- [RxJS Multicast 類 Operator (1) - multicast / publish / refCount / share / shareReplay](https://ithelp.ithome.com.tw/articles/10253517)

```typescript
const source$ = interval(1000).pipe(
  shareReplay(2)
);

source$.subscribe(data => {
  console.log(`shareReplay 示範 第一次訂閱: ${data}`);
});

setTimeout(() => {
  source$.subscribe(data => {
    console.log(`shareReplay 示範 第二次訂閱: ${data}`);
  });
}, 5000);
// shareReplay 示範 第一次訂閱: 0
// shareReplay 示範 第一次訂閱: 1
// shareReplay 示範 第一次訂閱: 2
// shareReplay 示範 第一次訂閱: 3
// shareReplay 示範 第一次訂閱: 4

// (第二次訂閱發生時，先重播過去兩次的資料)
// shareReplay 示範 第二次訂閱: 3 <-------第二次訂閱時間點之前前兩個observables
// shareReplay 示範 第二次訂閱: 4 <------'
// shareReplay 示範 第一次訂閱: 5
// shareReplay 示範 第二次訂閱: 5
// shareReplay 示範 第一次訂閱: 6
// shareReplay 示範 第二次訂閱: 6
```

- 在`pipe`內使用`shareReplay`限制replay次數來避免不斷的重複載入

我們可以透過設定`this.load`來決定資料是否要被載入，而當每次`this.load`被設定為 `true` 時，都會再次呼叫 API，若希望只有第一次要顯示時呼叫就好，可以使用 RxJS 的`shareReplay` ，這個 operator 會保留最近`N`次的內容，當 observable 被訂閱時，預設會先**重播**最新`N`次的紀錄

```typescript
import { shareReplay } from 'rxjs/operators'; 
ngOnInit() {
  this.todos$ = this.httpClient
    .get<any[]>('https://jsonplaceholder.typicode.com/todos/')
    .pipe(shareReplay(1)); // replay last value 
}
```

## 搭配 `*ngIf` 使用

另外一種常見的情境是搭配`*ngIf`使用，也就是在真正呼叫 Http Request 時，我們的`observable`是沒資料的，因此`<ng-container *ngIf ...> ... </ng-container>`的宿主標籤不會被顯示，搭配 AsyncPipe 訂閱後，真正得到資料時才顯示內容
```html
<ng-container *ngIf="todos$ | async; else loading">>
  <ul>
    <li *ngFor="let todo of todos$ | async">{{ todo.title }}</li>
  </ul>
</ng-container>

<ng-template #loading>Loading...</ng-template>
```

### `as`

```html
<ng-container *ngIf="todos$ | async as todos; else loading">
  <ul>
    <!-- 
        在內部就不需要使用 todos$ | async，
        而是使用 as 後面的 todos 區域變數 
    -->
    <li *ngFor="let todo of todos">{{ todo.title }}</li>
  </ul>
</ng-container>
```

## 使用 AsyncPipe 的自動退訂機制

```typescript 
import { Component, OnInit, OnDestroy} from '@angular/core';
import { interval } from 'rxjs';

@Component({
  selector: 'app-counter',
  template: `{{ value }}`
})
export class CounterComponent implements OnInit, OnDestroy {
  value = 0

  ngOnInit() {
    // interval() 每1000/1000秒訂閱observable
    interval(1000).subscribe((counter) => {
      console.log(counter);
      this.value = counter;
    })
  }

  ngOnDestroy() {
    console.log('destroy');
  }
}

@Component({
  selector: 'my-app',
  template: `

    <!-- 
       當 display 為 false 時，<app-counter> 元件將會被銷毀，
       當 display 為 true  時，<app-counter> 元件將重新產生。
    -->
    <app-counter *ngIf="display"></app-counter>
    <button (click)="display = !display">Toggle</button>
  `,
})
export class AppComponent  {
  display = true;
}
```


雖然元件被摧毀了，但`interval()`的行為並沒有停止！這將會造成每次產生元件時，就會產生一段新的`interval()`，會佔據大量的記憶體，進而發生 memory leak 的問題；要避免這問題，最直覺的方式是當元件要被璀毀時，於 `ngOnDestroy`內使用`.unsubscribe`：
```typescript
export class CounterComponent implements OnInit, OnDestroy {
  value = 0
  subscription: Subscription;

  ngOnInit() {
    this.subscription = interval(1000).subscribe((counter) => {
      console.log(counter);
      this.value = counter;
    })
  }

  ngOnDestroy() {
    console.log('destroy');
    this.subscription.unsubscribe();
  }
}
```

從 AsyncPipe 的程式碼可以看到，當 AsyncPipe 處理 observable 時，會在`ngOnDestroy`時自動將 observable 退訂  
```typescript
@Component({
  selector: 'app-counter',
  template: `{{ value$ | async }}`
})
export class CounterComponent implements OnInit {
  value$: Observable<number>;

  ngOnInit() {
    this.value$ = interval(1000).pipe(
      tap(counter => console.log(counter))
    );
  }
}
```

## AsyncPipe `ChangeDetectionStrategy.OnPush`
```typescript
@Component({
    //..

    changeDetection: ChangeDetectionStrategy.OnPush
})
```

**AsyncPipe程式在資料變更時(Promise或RxJS)，會自動使用`ChangeDetectorRef`的`markForCheck()`方法，自動要求變更偵測發生**
- 因為 observable 的實體參考位置不會被更改，因此當元件的變更偵測策略為 OnPush時，使用AsyncPipe就會發生沒有進行變更偵測的問題！所以 AsyncPipe 在訂閱(或呼叫`.then()`)的同時，也會要求變更偵測需要處理！

**如果元件中只剩下 observable + AsyncPipe 時，我們就可以光明正大地把元件的 OnPush 策略打開**，無須手動去呼叫 `markForCheck` ，AsyncPipe 會在需要變更偵測時主動幫我們處理
```typescript
@Component({
  selector: 'app-counter',
  template: `{{ (data$ | async)?.value }}`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CounterComponent implements OnInit {
  @Input() data$: Observable<any>;
  ngOnInit() { }
}
```

