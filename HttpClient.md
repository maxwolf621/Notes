###### tags: `Angular`
# HttpClient

We can get the json type content via `Httpclient` instance
```json
{
  "results": [
    "Item 1",
    "Item 2",
  ]
}
```

```typescript
@Component(...)
export class MyComponent implements OnInit {
 
  results: string[];
 
  // Inject HttpClient into your component or service.
  constructor(private http: HttpClient) {}
 
  ngOnInit(): void {
    // Make the HTTP request:
    this.http.get('/api/items').subscribe(data => {
      // Read the result field from the JSON response.
      this.results = data['results'];
    });
  }
}
```


## 定義回傳格式
首先先建立一個要回傳的物件介面
```typescript
interface ItemsResponse {
  results: string[];
}
```
在get後面設定要回傳的資料的格式
```typescript
/**
  * response的資料類型為ItemResponse
  */
http.get<ItemsResponse>('/api/items').subscribe(data => {
  // data is now an instance of type ItemsResponse, so you can do this:
  this.results = data.results;
});
```

很多時候我們也會需要Http header的資料，而不光是回傳的body，這時候可以更改observe的值為response
```typescript
http
  .get<MyJsonData>('/data.json', {observe: 'response'})
  .subscribe(resp => {
    // Here, resp is of type HttpResponse<MyJsonData>.
    // You can inspect its headers:
    console.log(resp.headers.get('X-Custom-Header'));
    // And access the body directly, which is typed as MyJsonData as requested.
    console.log(resp.body.someField);
  });
```
處理錯誤訊息
可以在.subscribe()下面增加err函數來處理HTTP錯誤的狀況
```typescript
http
  .get<ItemsResponse>('/api/items')
  .subscribe(
    // Successful responses call the first callback.
    data => {...},
    // Errors will call this callback instead:
    err => {
      console.log('Something went wrong!');
    }
  );
```
err這個含數可以有傳入更詳細的HTTP錯誤訊息物件HttpErrorResponse
```
http
  .get<ItemsResponse>('/api/items')
  .subscribe(
    data => {...},
    (err: HttpErrorResponse) => {
      if (err.error instanceof Error) {
        // A client-side or network error occurred. Handle it accordingly.
        console.log('An error occurred:', err.error.message);
      } else {
        // The backend returned an unsuccessful response code.
        // The response body may contain clues as to what went wrong,
        console.log(`Backend returned code ${err.status}, body was: ${err.error}`);
      }
    }
  );
```

## `retry`遇到錯誤時自動重試
- RxJS有一個有用的運算符`.retry()`，可以在遇到錯誤時自動重新嘗試

```typescript
http
  // get<ResponseType>('後端位置')
  .get<ItemsResponse>('/api/items')
  // Retry this request up to 3 times.
  .retry(3)
  // Any errors after the 3rd retry will fall through to the app.
  .subscribe(...);
```

## 接收非JSON格式的資料
傳入`responseType`可以設定預期會接收到的資料格式，angular預設值是`json`，如果是其他格式則需要設定這個欄位。 可接受的項目有『`arraybuffer`』、『`blob`』、『`json`』、『`text`』。 下面是使用方式：

```typescript
http
  .get('/textfile.txt', {responseType: 'text'})
  // The Observable returned by get() is of type Observable<string>
  // because a text response was specified. There's no need to pass
  // a <string> type parameter to get().
  .subscribe(data => console.log(data));
```


## POST request

```typescript
const body = {name: 'Brad'};

http
  // post('後端位置', request_body)
  .post('/api/developers/add', body)
  .subscribe(...);
``` 

**所有rxjs的動作都會在有人subscribe後才會呼叫**，因此如果忽略`subscribe()`，http將不會做任何動作。
下面為一個簡單範例：
```typescript
const req = http.post('/api/items/add', body);

// 0 requests made - .subscribe() not called.
req.subscribe();

// 1 request made.
req.subscribe();

// 2 requests made.

// 設置request的header
http
  .post('/api/items/add', body, {
    headers: new HttpHeaders().set('Authorization', 'my-auth-token'),
  })
  .subscribe();
```
- 該HttpHeaders的內容是不變的，每次`set()`時會返回一個新的實體，並套用所設定的更改。

## 設置URL參數
如果想要發送Request至`/api/items/add?id=3`
```typescript
http
  .post('/api/items/add', body, {
    params: new HttpParams().set('id', '3'),
  })
  .subscribe();
```
