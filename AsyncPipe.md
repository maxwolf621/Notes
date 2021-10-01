# AsyncPipe 

[自動訂閱/退訂機制](https://ithelp.ithome.com.tw/articles/10209602)

[Note Taking From](https://ithelp.ithome.com.tw/articles/10209372)

使用`HttpClient`去呼叫一個API, 例如`http.get`的API從後端取得Observable,因此並透過`subscribe`方法，去訂閱結果，
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
    this.httpClient.get('https://jsonplaceholder.typicode.com/todos/').subscribe
      (
          (data: any[]) => {
              this.todos$ = data;
      });
  }
}
```

由於 Http Request 是非同步的程式， `observable` 也是，因此我們可以先把這個非同步的物件保留起來，而不是立刻呼叫 `subscribe` 方法：
```typescript
import { Observable } from 'rxjs';

export class AppComponent {
  todos$: Observable<any[]>;

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.todos$ = this.httpClient.get<any[]>('https://jsonplaceholder.typicode.com/todos/');
  }
}
```
- 改用宣告一個型別為 `Observable<any[]>` 的變數 `todos$`，並直接把 `HttpClient` 取得的物件指派給這個變數，而不再做訂閱的行為
- 在開發習慣中，我們會在變數後面加上一個 `$` 符號，代表他是一個 observable。


透過template來幫我們進行訂閱
```html
<li *ngFor="let todo of todos$ | async">{{ todo.title }}</li>
```

`AsyncPipe`也可以幫助我們自動處理`Promise`，不過在 Angular 中還是使用 RxJS 居多。

### 類延遲載入
由於我們現在把訂閱的工作交給樣板上的程式了，因此資料不會在元件開始的時候就載入，而是在樣板中有需要顯示的時候才載入，因此我們可以做出一個類似延遲載入的效果：

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

  loadTodos() {
    this.load = !this.load;
  }
}
```
在這段程式中，畫面一開始不會載入資料，因為 `todos$ | async` 的部分並沒有被載入，當按下按鈕把`this.load` 設為`true`時，才會進行載入的動作，透過這種方式，我們就可以在真正需要資料時才進行載入的動作，避免不必要的 API 呼叫

## 在`pipe`內使用`shareReplay`限制replay次數來避免不斷的重複載入
在上一段Code Snippet，我們可以透過設定`this.load`來決定資料是否要被載入，而當每次`this.load`被設定為 `true` 時，都會再次呼叫 API，若希望只有第一次要顯示時呼叫就好，可以使用 RxJS 的 shareReplay operator，這個 operator 會保留最近 N 次(看設定) 的內容，當 observable 被訂閱時，預設會先「重播」最新 N 次的紀錄！

```typescript
// import { shareReplay } from 'rxjs/operators'; 
ngOnInit() {
  this.todos$ = this.httpClient
    .get<any[]>('https://jsonplaceholder.typicode.com/todos/')
    // 重播最近一次的資料
    .pipe(shareReplay(1));
}
```

### 搭配 ngIf 使用
另外一種常見的情境是搭配`*ngIf`使用，也就是在真正呼叫 Http Request 時，我們的`observable`是沒資料的，因此 *ngIf 的宿主標籤不會被顯示，搭配 AsyncPipe 訂閱後，真正得到資料時才顯示內容，如下：
```html
<ng-container *ngIf="todos$ | async; else loading">>
  <ul>
    <li *ngFor="let todo of todos$ | async">{{ todo.title }}</li>
  </ul>
</ng-container>
<ng-template #loading>Loading...</ng-template>
```

這段程式可以運作得非常好，在 Http Request 還沒完成的期間顯示 Loading... 內容，直到有資料後才顯示內容，但需要使用兩次 AsyncPipe，雖然我們已經知道可以搭配 shareReplay 來避免重複呼叫，但是套用多個 AsyncPipe 還是感覺醜醜的，這時候可以加上 as ，把訂閱的結果存到另一個區域變數中，如下：

```html
<ng-container *ngIf="todos$ | async as todos; else loading">
  <ul>
    <!-- 在內部就不需要使用 todos$ | async，而是使用 as 後面的 todos 區域變數 -->
    <li *ngFor="let todo of todos">{{ todo.title }}</li>
  </ul>
</ng-container>
```